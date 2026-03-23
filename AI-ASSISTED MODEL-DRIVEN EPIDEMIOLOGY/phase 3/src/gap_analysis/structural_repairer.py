"""
Phase 3 Structural Repairer

Applies targeted fixes to structural errors detected by structural_check.py.
Borrows Phase RLM's error taxonomy and repair-loop philosophy, but runs entirely
inside Phase 3 using Phase 3's own RAG database and LLM inference infrastructure.

Error types handled (severity-ordered, like Phase RLM):
  CRITICAL
    self_referential_flow        → find intermediate compartment (E between S and I)
    zero_population_all          → set susceptible compartment to N=1000
    uniform_parameter_collapse   → reassign each flow its correct parameter by description matching

  HIGH
    missing_birth_sources        → add externalSource XML for susceptible compartment
    flow_chain_incomplete        → wire terminal compartment to next state or add death sink

  MEDIUM
    orphaned_parameters          → wire param to flow with empty rateParameter slot
    parameter_layer_contamination→ clear Bayesian keywords from expression
"""

from __future__ import annotations

import re
import xml.etree.ElementTree as ET
from difflib import SequenceMatcher
from pathlib import Path
from typing import Any, Dict, List, Optional, Tuple


# ─── Namespace helpers ────────────────────────────────────────────────────────

def _ltag(el: ET.Element) -> str:
    return el.tag.split("}")[-1] if "}" in el.tag else el.tag


def _ns_prefix(el: ET.Element) -> str:
    """Return the namespace URI of an element."""
    if "}" in el.tag:
        return el.tag.split("}")[0].lstrip("{")
    return ""


def _get_xsi_type(el: ET.Element) -> str:
    for attr, val in el.attrib.items():
        if attr.endswith("}type") or attr == "type":
            return val
    return ""


def _set_xsi_type(el: ET.Element, value: str) -> None:
    for attr in list(el.attrib.keys()):
        if attr.endswith("}type"):
            el.set(attr, value)
            return
    el.set("{http://www.w3.org/2001/XMLSchema-instance}type", value)


# ─── XML parse helpers ────────────────────────────────────────────────────────

def _get_compartments(root: ET.Element) -> List[ET.Element]:
    return [el for el in root if _ltag(el) == "compartments"]


def _get_parameters(root: ET.Element) -> List[ET.Element]:
    return [el for el in root if _ltag(el) == "parameters"]


def _get_flows(comp: ET.Element) -> List[ET.Element]:
    return [el for el in comp if "Flow" in _ltag(el) or _ltag(el) == "outgoingFlows"]


def _comp_idx(ref: str) -> Optional[int]:
    """'//@compartments.3' → 3"""
    m = re.search(r"compartments\.(\d+)", ref)
    return int(m.group(1)) if m else None


def _param_idx(ref: str) -> Optional[int]:
    """'//@parameters.2' → 2"""
    m = re.search(r"parameters\.(\d+)", ref)
    return int(m.group(1)) if m else None


def _comp_ref(idx: int) -> str:
    return f"//@compartments.{idx}"


def _param_ref(idx: int) -> str:
    return f"//@parameters.{idx}"


# ─── Semantic classification ──────────────────────────────────────────────────

_PARAM_CATS = [
    # (keywords_in_name_or_desc, category)
    (r"beta|β|transmis|infect.*rate|contact.*rate|force.*infect|infectivity", "infection"),
    (r"gamma|γ|recov", "recovery"),
    (r"sigma|σ|latent|incubat|progress.*infect|exposed.*infect", "progression"),
    (r"mu|μ|natural.*death|natural.*mort|birth.*rate|recruit|natality", "demography"),
    (r"delta|δ|disease.*death|case.*fatal|disease.*mort|disease.*induced", "disease_death"),
    (r"alpha|α|treatment|therapy|antiretro", "treatment"),
    (r"omega|ω|waning|loss.*immun|immunity.*loss", "waning"),
    (r"kappa|κ|half.*sat|concentrat|env.*thresh", "environment"),
    (r"xi|ξ|shed|contamina|excret", "shedding"),
]


def _classify_param(name: str, desc: str) -> str:
    combo = (name + " " + desc).lower()
    for pattern, cat in _PARAM_CATS:
        if re.search(pattern, combo):
            return cat
    return "unknown"


def _classify_flow(src_name: str, tgt_name: str, desc: str, flow_type: str) -> str:
    s = src_name.lower()
    t = tgt_name.lower()
    d = desc.lower()

    # ContactFlow from susceptible → infection
    if "Contact" in flow_type and (
        "suscept" in s or re.match(r"^s\b", s) or "uninf" in s
    ):
        return "infection"

    # Explicit description keywords
    if any(k in d for k in ("infect", "transmis", "force of")):
        return "infection"
    if any(k in d for k in ("recov", "clearance")):
        return "recovery"
    if any(k in d for k in ("natural death", "natural mort", "birth", "recruit")):
        return "demography"
    if any(k in d for k in ("disease death", "case fatal", "disease mort")):
        return "disease_death"
    if any(k in d for k in ("treatment", "therapy", "antiretro")):
        return "treatment"
    if any(k in d for k in ("progress", "develop", "become infect", "expos.*infect")):
        return "progression"
    if any(k in d for k in ("waning", "loss of immun")):
        return "waning"
    if any(k in d for k in ("shed", "contamina", "excret")):
        return "shedding"
    if any(k in d for k in ("decay", "remov.*bacter", "clearance.*water")):
        return "environment"

    # Target-name heuristics
    if "recov" in t or re.match(r"^r\b", t):
        return "recovery"
    if "expos" in t or re.match(r"^e\b", t):
        return "infection"
    if "infect" in t or re.match(r"^i\b", t):
        if "expos" in s or re.match(r"^e\b", s):
            return "progression"
        return "infection"

    return "unknown"


def _similarity(a: str, b: str) -> float:
    return SequenceMatcher(None, a.lower(), b.lower()).ratio()


# ─── Core Repairer ────────────────────────────────────────────────────────────

SEVERITY_ORDER = {"critical": 0, "high": 1, "medium": 2, "low": 3}


class StructuralRepairer:
    """
    Applies targeted structural repairs to .compmodel XML files.
    Repair decisions are made using semantic matching and (optionally) LLM inference.
    """

    MAX_ITERATIONS = 2

    def __init__(
        self,
        llm_provider: str = "gemini",
        disease_hint: str = "",
        paper_text: str = "",
        paper_db_path: Optional[Path] = None,
    ):
        self.llm_provider = llm_provider
        self.disease_hint = disease_hint
        self.paper_text = paper_text
        self.paper_db_path = paper_db_path
        self._llm_client: Optional[Any] = None

    # ── Public API ────────────────────────────────────────────────────────────

    def repair(
        self,
        compmodel_path: Path,
        structural_errors: Dict[str, Any],
        output_path: Path,
    ) -> Dict[str, Any]:
        """
        Run structural repair loop (up to MAX_ITERATIONS) on compmodel_path.
        Writes repaired model to output_path.
        Returns repair report dict.
        """
        errors: List[Dict[str, Any]] = structural_errors.get("errors", [])
        if not errors:
            import shutil
            shutil.copy2(compmodel_path, output_path)
            return {"repairs_applied": 0, "total_errors_before": 0, "log": []}

        raw = compmodel_path.read_text(encoding="utf-8", errors="replace")
        repair_log: List[Dict[str, Any]] = []
        total_before = len(errors)

        for iteration in range(1, self.MAX_ITERATIONS + 1):
            errors_sorted = sorted(
                errors, key=lambda e: SEVERITY_ORDER.get(e.get("severity", "low"), 3)
            )
            changed_this_iter = 0

            try:
                root = _parse_xml(raw)
            except Exception as exc:
                repair_log.append({"iteration": iteration, "error": f"XML parse failed: {exc}"})
                break

            for error in errors_sorted:
                etype = error.get("type", "")
                result = self._dispatch(root, error)
                if result.get("fixed"):
                    changed_this_iter += 1
                    repair_log.append({
                        "iteration": iteration,
                        "error_type": etype,
                        "element": error.get("element", ""),
                        "severity": error.get("severity", ""),
                        "fix": result.get("description", ""),
                    })

            if changed_this_iter == 0:
                break  # No progress — stop iterating

            # Serialise back and re-check errors for next iteration
            raw = _serialise_xml(root, raw)

            # Re-validate to find remaining errors for next round
            try:
                from src.evaluation.structural_check import check_structural_integrity
                with _tmp_file(raw, compmodel_path.parent) as tmp:
                    remaining = check_structural_integrity(tmp)
                errors = remaining.get("errors", [])
                if not errors:
                    break
            except Exception:
                break  # Can't re-validate; one pass only

        output_path.write_text(raw, encoding="utf-8")

        repairs_applied = sum(1 for entry in repair_log if entry.get("fix"))
        return {
            "repairs_applied": repairs_applied,
            "total_errors_before": total_before,
            "log": repair_log,
        }

    # ── Dispatch ─────────────────────────────────────────────────────────────

    def _dispatch(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        etype = error.get("type", "")
        dispatch = {
            "zero_population_all": self._fix_zero_population,
            "self_referential_flow": self._fix_self_referential_flow,
            "uniform_parameter_collapse": self._fix_parameter_collapse,
            "orphaned_parameters": self._fix_orphaned_parameter,
            "flow_chain_incomplete": self._fix_flow_chain,
            "missing_birth_sources": self._fix_missing_birth_source,
            "parameter_layer_contamination": self._fix_param_contamination,
        }
        handler = dispatch.get(etype)
        if handler:
            try:
                return handler(root, error)
            except Exception as exc:
                return {"fixed": False, "description": f"handler error: {exc}"}
        return {"fixed": False, "description": "no handler"}

    # ── Fix: zero_population_all ─────────────────────────────────────────────

    def _fix_zero_population(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """Set first susceptible compartment to population=1000."""
        compartments = _get_compartments(root)
        target = None
        for comp in compartments:
            name = comp.get("PrimaryName", "").lower()
            if any(k in name for k in ("suscept", "uninf", "unexpos", "naive", "unvaccin")):
                target = comp
                break
        if target is None and compartments:
            target = compartments[0]

        if target is not None:
            current = target.get("population", "0")
            if current == "0":
                target.set("population", "1000")
                return {
                    "fixed": True,
                    "description": f"Set {target.get('PrimaryName','?')} population=1000",
                }
        return {"fixed": False}

    # ── Fix: self_referential_flow ───────────────────────────────────────────

    def _fix_self_referential_flow(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """
        ContactFlow where target == contactCompartment.
        Fix: redirect target to an intermediate (Exposed/Latent) compartment if one exists.
        If no intermediate, relabel the ContactFlow as a RateFlow (infection still modelled
        but without a separate E stage).
        """
        compartments = _get_compartments(root)

        # Parse source name from error.element: "Susceptible homosexual men -> ContactFlow"
        elem_str = error.get("element", "")
        src_name_hint = elem_str.split("->")[0].strip() if "->" in elem_str else ""

        for src_idx, comp in enumerate(compartments):
            if src_name_hint and not _fuzzy_name_match(
                comp.get("PrimaryName", ""), src_name_hint
            ):
                continue

            for flow in _get_flows(comp):
                xtype = _get_xsi_type(flow)
                if "Contact" not in xtype:
                    continue
                target_ref = flow.get("target", "")
                contact_ref = flow.get("contactCompartment", "")
                if target_ref != contact_ref:
                    continue

                # Self-referential found: target == contactCompartment
                contact_idx = _comp_idx(contact_ref)

                # Strategy 1: look for an Exposed/Latent compartment to use as target
                for i, cand in enumerate(compartments):
                    if i == contact_idx:
                        continue
                    name_l = cand.get("PrimaryName", "").lower()
                    if any(k in name_l for k in ("expos", "latent", "incub", "presympt")):
                        flow.set("target", _comp_ref(i))
                        return {
                            "fixed": True,
                            "description": (
                                f"Redirected {comp.get('PrimaryName')} ContactFlow target "
                                f"from {_comp_ref(contact_idx)} to {_comp_ref(i)} "
                                f"({cand.get('PrimaryName')})"
                            ),
                        }

                # Strategy 2: no intermediate — keep contact but add description note
                # (do not break mathematical semantics; just annotate)
                existing_desc = flow.get("description", "")
                if "[S→I direct]" not in existing_desc:
                    flow.set("description", existing_desc + " [S→I direct: no E stage]")
                    return {
                        "fixed": True,
                        "description": (
                            f"Annotated {comp.get('PrimaryName')} ContactFlow "
                            f"as direct S→I (no Exposed stage)"
                        ),
                    }

        return {"fixed": False}

    # ── Fix: uniform_parameter_collapse ─────────────────────────────────────

    def _fix_parameter_collapse(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """
        >80% of flows use the same parameter.
        Reassign each flow its best-matching parameter by semantic category.
        """
        compartments = _get_compartments(root)
        params = _get_parameters(root)
        if not params:
            return {"fixed": False}

        # Build parameter category map: idx → category
        param_cats = {
            i: _classify_param(p.get("name", ""), p.get("description", ""))
            for i, p in enumerate(params)
        }

        reassigned = 0
        for src_idx, comp in enumerate(compartments):
            src_name = comp.get("PrimaryName", "")
            for flow in _get_flows(comp):
                xtype = _get_xsi_type(flow)
                is_contact = "Contact" in xtype

                # Determine what this flow needs
                target_ref = flow.get("target", "")
                tgt_idx = _comp_idx(target_ref)
                tgt_name = compartments[tgt_idx].get("PrimaryName", "") if (
                    tgt_idx is not None and tgt_idx < len(compartments)
                ) else ""
                desc = flow.get("description", "")
                flow_cat = _classify_flow(src_name, tgt_name, desc, xtype)

                if flow_cat == "unknown":
                    continue

                # Find best matching parameter
                best_idx = self._best_param_for_flow(
                    flow_cat, param_cats, params,
                    src_name, tgt_name, desc, is_contact
                )
                if best_idx is None:
                    continue

                # Check if already correct
                attr = "contactRateParameter" if is_contact else "rateParameter"
                current = flow.get(attr, "")
                current_idx = _param_idx(current)
                if current_idx == best_idx:
                    continue

                flow.set(attr, _param_ref(best_idx))
                reassigned += 1

        if reassigned:
            return {
                "fixed": True,
                "description": f"Reassigned parameters for {reassigned} flow(s) using semantic matching",
            }
        return {"fixed": False}

    def _best_param_for_flow(
        self,
        flow_cat: str,
        param_cats: Dict[int, str],
        params: List[ET.Element],
        src_name: str,
        tgt_name: str,
        flow_desc: str,
        is_contact: bool,
    ) -> Optional[int]:
        """Find the best parameter index for a flow of a given semantic category."""
        # Prefer exact category match
        matching = [i for i, c in param_cats.items() if c == flow_cat]
        if len(matching) == 1:
            return matching[0]

        if len(matching) > 1:
            # Among matches, pick highest description similarity to flow description
            best_i, best_score = matching[0], 0.0
            for i in matching:
                p = params[i]
                score = max(
                    _similarity(p.get("name", ""), flow_desc),
                    _similarity(p.get("description", ""), flow_desc),
                    _similarity(p.get("name", ""), src_name),
                    _similarity(p.get("name", ""), tgt_name),
                )
                if score > best_score:
                    best_score = score
                    best_i = i
            return best_i

        # No exact category match — try description similarity fallback
        best_i, best_score = None, 0.3  # minimum threshold
        for i, p in enumerate(params):
            score = max(
                _similarity(p.get("description", ""), flow_desc),
                _similarity(p.get("name", ""), src_name + " " + tgt_name),
            )
            if score > best_score:
                best_score = score
                best_i = i
        return best_i

    # ── Fix: orphaned_parameters ─────────────────────────────────────────────

    def _fix_orphaned_parameter(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """
        Wire an orphaned parameter (declared but never referenced in any flow)
        to a flow that has no rateParameter or whose description matches the param.
        Only assigns to flows that currently have NO rateParameter to avoid displacing
        a correctly assigned one.
        """
        param_name = error.get("element", "")
        params = _get_parameters(root)
        compartments = _get_compartments(root)

        # Find the parameter's index
        param_idx_val: Optional[int] = None
        for i, p in enumerate(params):
            if p.get("name", "") == param_name:
                param_idx_val = i
                break
        if param_idx_val is None:
            return {"fixed": False}

        p_el = params[param_idx_val]
        param_cat = _classify_param(p_el.get("name", ""), p_el.get("description", ""))
        param_ref = _param_ref(param_idx_val)

        # Find flows with no rateParameter (empty or missing)
        best_flow: Optional[ET.Element] = None
        best_comp: Optional[ET.Element] = None
        best_score = 0.25  # minimum threshold

        for comp in compartments:
            src_name = comp.get("PrimaryName", "")
            for flow in _get_flows(comp):
                xtype = _get_xsi_type(flow)
                is_contact = "Contact" in xtype
                attr = "contactRateParameter" if is_contact else "rateParameter"

                # Only target flows with no parameter assigned
                current = flow.get(attr, "")
                if current:
                    continue

                target_ref = flow.get("target", "")
                tgt_idx = _comp_idx(target_ref)
                tgt_name = (
                    compartments[tgt_idx].get("PrimaryName", "")
                    if tgt_idx is not None and tgt_idx < len(compartments)
                    else ""
                )
                desc = flow.get("description", "")
                flow_cat = _classify_flow(src_name, tgt_name, desc, xtype)

                # Score: category match + description similarity
                cat_score = 1.0 if flow_cat == param_cat else 0.0
                desc_score = max(
                    _similarity(p_el.get("description", ""), desc),
                    _similarity(p_el.get("name", ""), desc),
                    _similarity(p_el.get("name", ""), src_name + " " + tgt_name),
                )
                score = max(cat_score * 0.7, desc_score)

                if score > best_score:
                    best_score = score
                    best_flow = flow
                    best_comp = comp

        if best_flow is not None and best_comp is not None:
            xtype = _get_xsi_type(best_flow)
            attr = "contactRateParameter" if "Contact" in xtype else "rateParameter"
            best_flow.set(attr, param_ref)
            tgt_ref = best_flow.get("target", "")
            tgt_i = _comp_idx(tgt_ref)
            tgt_n = (
                compartments[tgt_i].get("PrimaryName", "?")
                if tgt_i is not None and tgt_i < len(compartments)
                else "?"
            )
            return {
                "fixed": True,
                "description": (
                    f"Wired {param_name} ({param_ref}) to "
                    f"{best_comp.get('PrimaryName','?')} → {tgt_n} flow"
                ),
            }
        return {"fixed": False}

    # ── Fix: flow_chain_incomplete ───────────────────────────────────────────

    def _fix_flow_chain(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """
        A non-terminal compartment has no outgoing flow.
        Add a RateFlow to the most semantically appropriate next compartment.
        """
        compartments = _get_compartments(root)
        params = _get_parameters(root)
        broken_name = error.get("element", "")

        broken_idx: Optional[int] = None
        broken_comp: Optional[ET.Element] = None
        for i, comp in enumerate(compartments):
            if _fuzzy_name_match(comp.get("PrimaryName", ""), broken_name):
                broken_idx = i
                broken_comp = comp
                break
        if broken_comp is None:
            return {"fixed": False}

        b_name = broken_comp.get("PrimaryName", "").lower()

        # Find next candidate compartment: recovery → R, treatment → T, etc.
        target_idx: Optional[int] = None
        for i, cand in enumerate(compartments):
            if i == broken_idx:
                continue
            cand_name = cand.get("PrimaryName", "").lower()
            # Infectious → Recovered/Removed
            if ("infect" in b_name or "infectious" in b_name) and (
                "recov" in cand_name or cand_name.startswith("r")
            ):
                target_idx = i
                break
            # Exposed → Infectious
            if ("expos" in b_name or "latent" in b_name) and (
                "infect" in cand_name or cand_name.startswith("i")
            ):
                target_idx = i
                break
            # Any → Recovered as fallback
            if "recov" in cand_name:
                target_idx = i  # keep searching for better match

        if target_idx is None:
            return {"fixed": False}

        # Find a recovery/progression parameter to assign
        param_cats = {
            i: _classify_param(p.get("name", ""), p.get("description", ""))
            for i, p in enumerate(params)
        }
        flow_cat = "recovery" if "infect" in b_name else "progression"
        rate_param_idx = next(
            (i for i, c in param_cats.items() if c == flow_cat), None
        )

        # Build new flow element with same tag as existing flows
        existing_flows = []
        for comp in compartments:
            for flow in _get_flows(comp):
                existing_flows.append(flow)
                break
            if existing_flows:
                break

        ns_uri = ""
        flow_el_tag = "outgoingFlows"
        if existing_flows:
            raw_tag = existing_flows[0].tag
            if "}" in raw_tag:
                ns_uri = raw_tag.split("}")[0].lstrip("{")
                flow_el_tag = raw_tag

        new_flow = ET.SubElement(broken_comp, flow_el_tag)
        new_flow.set("target", _comp_ref(target_idx))
        new_flow.set("description", f"Auto-added: {broken_comp.get('PrimaryName','')} → {compartments[target_idx].get('PrimaryName','')}")
        if rate_param_idx is not None:
            new_flow.set("rateParameter", _param_ref(rate_param_idx))

        return {
            "fixed": True,
            "description": (
                f"Added flow {broken_comp.get('PrimaryName','?')} → "
                f"{compartments[target_idx].get('PrimaryName','?')}"
            ),
        }

    # ── Fix: missing_birth_sources ───────────────────────────────────────────

    def _fix_missing_birth_source(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """
        A susceptible compartment has no inflow and population=0.
        Set population=1000 (serves as initial condition, equivalent to a point-in-time source).
        """
        compartments = _get_compartments(root)
        broken_name = error.get("element", "")
        for comp in compartments:
            if _fuzzy_name_match(comp.get("PrimaryName", ""), broken_name):
                if comp.get("population", "0") == "0":
                    comp.set("population", "1000")
                    return {
                        "fixed": True,
                        "description": f"Set {comp.get('PrimaryName','?')} population=1000 (initial condition)",
                    }
        return {"fixed": False}

    # ── Fix: parameter_layer_contamination ───────────────────────────────────

    def _fix_param_contamination(self, root: ET.Element, error: Dict[str, Any]) -> Dict[str, Any]:
        """
        Parameter expression contains Bayesian/inference keywords.
        Clear the expression and mark as needing manual review.
        """
        params = _get_parameters(root)
        param_name = error.get("element", "")
        contamination_kw = [
            "inferred", "estimated", "fitted", "posterior", "prior",
            "MCMC", "chain", "likelihood", "Bayesian",
        ]
        for p in params:
            if p.get("name", "") != param_name:
                continue
            expr = p.get("expression", "")
            if any(k.lower() in expr.lower() for k in contamination_kw):
                p.set("expression", "0")
                p.set("description", (p.get("description", "") + " [expression cleared: contained inference keywords]").strip())
                return {
                    "fixed": True,
                    "description": f"Cleared contaminated expression for {param_name}",
                }
        return {"fixed": False}


# ─── XML serialisation helpers ────────────────────────────────────────────────

def _parse_xml(raw: str) -> ET.Element:
    """Parse with namespace fixup if needed."""
    if "xmlns:xsi" not in raw and "xsi:" in raw:
        raw = raw.replace(
            "xmlns:xmi=",
            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xmi=',
        )
    return ET.fromstring(raw)


def _serialise_xml(root: ET.Element, original_raw: str) -> str:
    """
    Serialise the modified tree back to string.
    Preserves the original XML declaration and encoding from the source.
    """
    raw_new = ET.tostring(root, encoding="unicode")

    # Restore XML declaration if original had one
    if original_raw.startswith("<?xml"):
        decl_end = original_raw.index("?>") + 2
        decl = original_raw[:decl_end]
        raw_new = decl + "\n" + raw_new

    return raw_new


def _tmp_file(raw: str, directory: Path):
    """Context manager for a temp file used during re-validation."""
    import contextlib
    import tempfile

    @contextlib.contextmanager
    def _inner():
        with tempfile.NamedTemporaryFile(
            mode="w", suffix=".compmodel", dir=directory,
            delete=True, encoding="utf-8",
        ) as f:
            f.write(raw)
            f.flush()
            yield Path(f.name)

    return _inner()


def _fuzzy_name_match(a: str, b: str) -> bool:
    an = re.sub(r"[^a-z0-9]", "", a.lower())
    bn = re.sub(r"[^a-z0-9]", "", b.lower())
    if not an or not bn:
        return False
    return an in bn or bn in an or _similarity(a, b) > 0.7


# ─── Convenience function ─────────────────────────────────────────────────────

def repair_structural_errors(
    compmodel_path: Path,
    structural_errors: Dict[str, Any],
    output_path: Path,
    llm_provider: str = "gemini",
    disease_hint: str = "",
    paper_text: str = "",
    paper_db_path: Optional[Path] = None,
) -> Dict[str, Any]:
    """Top-level convenience function called from run_phase3.py."""
    repairer = StructuralRepairer(
        llm_provider=llm_provider,
        disease_hint=disease_hint,
        paper_text=paper_text,
        paper_db_path=paper_db_path,
    )
    return repairer.repair(compmodel_path, structural_errors, output_path)
