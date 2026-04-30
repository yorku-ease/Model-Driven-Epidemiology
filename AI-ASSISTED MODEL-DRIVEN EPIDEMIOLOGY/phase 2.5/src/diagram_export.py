"""Graphviz DOT export for feature_tree.json."""

from __future__ import annotations

import json
from pathlib import Path
from typing import Any, Dict, List

CLUSTER_FILL = ["#daf0fc", "#d4f5e8", "#f9f5d9", "#fae8fb", "#ede8fc"]

CLUSTER_EDGE = "#6b8299"

# One-line hints aligned with comments in ``epi_features.py`` (SVG tooltip; PNG legend clarifies globally).
_LEAF_HINT: Dict[str, str] = {
    "route_vector_arthropod": "Transmission via arthropod / vector compartments or wording (e.g. mosquito).",
    "route_sexual_or_partner_network": "Partnership sexual network or analogous route cues in flows.",
    "route_airborne_or_respiratory_droplet": "Respiratory aerosol droplet airborne wording or lung-focused flows.",
    "route_fecal_oral_or_waterborne": "Fecal-oral, oral, water/food-associated transmission wording.",
    "route_bloodborne_vertical_or_parenteral": "Blood, syringe, maternal-fetal vertical, parental cues.",
    "route_healthcare_general_contact": "Hospital ward, HCW, nonspecific institutional contact wording.",
    "latent_or_exposed_class": "Latent, exposed, Eh, incubating, chronic carrier style compartment classes.",
    "multiple_infectious_stages_or_chronic": "Multiple sequential infectious strata or staged chronic progression.",
    "hospitalized_or_severity_stratification": "Hospitalized ICU severe mild stratified clinical severity strata.",
    "treatment_or_art_intervention": "Treatment ART therapy drug compartments or explicit therapy flows.",
    "vaccination_route_compartment": "Vaccinated vaccine immune compartment wording.",
    "recovered_or_immune_endpoint": "Recovered immune endpoint compartment naming.",
    "stratification_demographic_roles": "Age sex risk group MSM strata demographic roles in compartments.",
    "spatial_or_patch_like_naming": "Patch metropolis multiple regions geographic spatial structuring.",
    "vector_or_intermediate_species_present": "Non-human vector snail aquatic intermediate reservoir species compartments.",
    "zoonotic_or_animal_compartment": "Zoonotic non-human vertebrate mammal bird reservoir compartments.",
    "recruitment_birth_or_immigration_named": "Birth influx immigration recruitment into susceptible explicit naming.",
}


def _esc(s: str) -> str:
    """Escape for Graphviz quoted labels (plain string, not HTML)."""

    return str(s).replace("\\", "\\\\").replace('"', '\\"').replace("&", "\\&")


def _pretty_leaf_feature_id(leaf_id: str, max_chars: int = 32) -> str:
    """Readable title-ish label from snake_case; wrap long phrases across lines."""

    parts: List[str] = []
    for p in leaf_id.replace("__", "_").split("_"):
        if not p:
            continue
        parts.append(p[0].upper() + p[1:] if len(p) > 1 else p.upper())
    line = " ".join(parts)

    # Soft-wrap at spaces by target width (narrow columns read better).
    words = line.split()
    if len(line) <= max_chars:
        return line
    out: List[str] = []
    cur = ""
    for w in words:
        tentative = cur + (" " if cur else "") + w
        if len(tentative) <= max_chars + 8:
            cur = tentative
        else:
            if cur:
                out.append(cur)
            cur = w
    if cur:
        out.append(cur)

    lines = "\\n".join(out)
    return lines


def _group_label_plain(full: str) -> str:
    """Subgroup banner: keep first line short, rest on second line."""

    s = full.strip().replace("\u2014", "-")
    bracket = s.find("(")
    if bracket >= 0:
        return _esc(s[:bracket].strip()) + "\\n" + _esc(s[bracket:].strip())
    if len(s) > 50:
        cut = max(s.rfind(" ", 40, len(s)), 40)
        return _esc(s[:cut].strip() + "...") + "\\n" + _esc(s[cut:].strip())
    return _esc(s)


def fm_tree_to_dot(tree: Dict[str, Any]) -> str:
    rnd = tree["root"]
    rid = rnd["id"]
    rlab = rnd.get("label", rid)
    nodes: Dict[str, Any] = tree.get("nodes", {})
    ordered_groups: List[str] = list(rnd.get("children", []))

    lines: List[str] = [
        "digraph EpidemiologicalFeatureTree {",
        "  graph [",
        '    bgcolor="white",',
        "    fontname=\"Helvetica\",",
        "    fontsize=12,",
        "    margin=0.25,",
        "    pad=0.5,",
        "    nodesep=0.28,",
        "    ranksep=0.75,",
        "    splines=spline,",
        "    compound=false,",
        "    label=<<TABLE BORDER=\"0\" CELLBORDER=\"0\" CELLSPACING=\"0\">",
        "      <TR><TD ALIGN=\"LEFT\"><B>What this picture is</B></TD></TR>",
        "      <TR><TD ALIGN=\"LEFT\"><FONT POINT-SIZE=\"9.8\" COLOR=\"#333333\">"
        "<B>Each box</B> names one Yes/No <B>EpiFeatureVector</B> signal: did we infer "
        "(from compartments/flows) that gold models use this <B>structural choice?</B>"
        "</FONT></TD></TR>",
        "      <TR><TD ALIGN=\"LEFT\"><FONT POINT-SIZE=\"9.5\" COLOR=\"#444444\">"
        "• This is <B>not</B> infection biology or a causal timeline; arrows only group related booleans.<BR ALIGN=\"LEFT\"/>"
        "• Coloured sections = themes (transmission routes, clinical course, ...).<BR ALIGN=\"LEFT\"/>"
        "• For one disease we OR-merge gold papers into one canonical profile; SPL checks booleans versus cross-tree logic.<BR ALIGN=\"LEFT\"/>"
        "• Hover tooltips on leaves in <B>.svg</B> for one-line meanings."
        "</FONT></TD></TR>",
        "    </TABLE>>,",
        "    labelloc=t,",
        "    labeljust=l,",
        "  ];",
        "  node [fontname=\"Helvetica\", fontsize=10.5, color=\"#333333\"];",
        f'  edge [color="{CLUSTER_EDGE}", penwidth=0.85, arrowsize=0.75];',
        "  rankdir=TB;",
        f'  "{rid}" [',
        '    shape=box,',
        '    style="filled,rounded",',
        '    fillcolor="#e8edf3",',
        '    penwidth=1.2,',
        "    fontsize=12,",
        f'    label="{_esc(rlab)}",',
        "    width=1.6,",
        "  ];",
        "",
    ]

    for idx, gid in enumerate(ordered_groups):
        gv = nodes.get(gid)
        if not gv:
            continue
        cid = f"cluster_{gid}"
        hue = CLUSTER_FILL[idx % len(CLUSTER_FILL)]
        full_label = gv.get("label", gid)
        banner = _group_label_plain(full_label)
        leaf_ids = [str(x) for x in gv.get("children", [])]

        lines.append(f"  subgraph {cid} {{")
        lines.append("    graph [")
        lines.append(f'      label="{banner}",')
        lines.append("      labelloc=t,")
        lines.append("      fontsize=11.5,")
        lines.append('      fontname="Helvetica",')
        lines.append(f'      style="rounded,filled",')
        lines.append(f'      fillcolor="{hue}",')
        lines.append(f'      color="{CLUSTER_EDGE}",')
        lines.append("      penwidth=1.0,")
        lines.append("      margin=14,")
        lines.append("    ];")

        short_header = full_label.split("(")[0].strip()
        if len(short_header) > 36:
            short_header = short_header[:33] + "..."
        lines.append(
            f'    "{gid}" [shape=tab, style="filled,rounded", fillcolor="#ffffff", '
            f' fontsize=10.5, label="{_esc(short_header)}"];'
        )

        for lf in leaf_ids:
            pll = _pretty_leaf_feature_id(lf)
            hint = _LEAF_HINT.get(str(lf), "")
            tooltip_attr = ""
            if hint:
                tooltip_attr = f', tooltip="{_esc(hint)}"'
            lines.append(
                f'    "{lf}" [shape=box, style="rounded,filled", fillcolor="#ffffff", '
                f' fontsize=9.5, margin=0.1, label="{_esc(pll)}"'
                + tooltip_attr
                + "];"
            )
        # Vertical chain of leaves (narrower subgraph than hub-and-spokes).
        if leaf_ids:
            lines.append(f'    "{gid}" -> "{leaf_ids[0]}" [ penwidth=1.05 ];')
            for a, b in zip(leaf_ids, leaf_ids[1:]):
                lines.append(f'    "{a}" -> "{b}" [ color="#aab8c6", penwidth=0.75, constraint=true ];')

        lines.append("  }")
        lines.append("")
        if idx == 0:
            lines.append(f'  "{rid}" -> "{gid}" [ penwidth=1.2 ];')
            lines.append("")

    ordered_valid = [g for g in ordered_groups if g in nodes]
    for i in range(len(ordered_valid) - 1):
        g_curr, g_next = ordered_valid[i], ordered_valid[i + 1]
        last_leaf = str(nodes[g_curr]["children"][-1])
        lines.append(
            f'  "{last_leaf}" -> "{g_next}" [ style=invis, weight=999, minlen=2, constraint=true ];'
        )

    lines.append("}")
    return "\n".join(lines) + "\n"


def export_dot(tree_path: Path, out_path: Path) -> None:
    tree = json.loads(tree_path.read_text(encoding="utf-8"))
    out_path.parent.mkdir(parents=True, exist_ok=True)
    out_path.write_text(fm_tree_to_dot(tree), encoding="utf-8")
