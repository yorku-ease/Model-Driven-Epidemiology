"""Phase 2 evaluation: fuzzy string matching (no embeddings).

Precision/recall/F1 vs gold using ``difflib.SequenceMatcher``-style matching.
Does not require sentence-transformers.
"""

import json
import xml.etree.ElementTree as ET
from difflib import SequenceMatcher
from pathlib import Path
from typing import Dict, List, Any, Optional, Tuple, Set


class Evaluator:
    """Evaluate extraction quality with metrics"""
    
    def __init__(
        self,
        gold_standard_path: Optional[str] = None,
        threshold: float = 0.75,
    ):
        """
        Initialize evaluator.

        Args:
            gold_standard_path: Path to gold standard JSON or .compmodel file (optional)
            threshold: Minimum fuzzy similarity (0–1) for compartment/parameter/flow name matching
        """
        self._entity_threshold = float(threshold)
        self.gold_standard = None
        if gold_standard_path:
            self._load_gold_standard(gold_standard_path)
    
    def _load_gold_standard(self, gold_path: str):
        """Load gold standard for comparison (supports both JSON and .compmodel files)"""
        gold_path_obj = Path(gold_path)
        if not gold_path_obj.exists():
            print(f"Warning: Gold standard file not found: {gold_path}")
            return
        
        try:
            # Check if it's a .compmodel file
            if gold_path_obj.suffix == '.compmodel':
                self.gold_standard = self._convert_compmodel_to_gold_standard(gold_path)
            else:
                # Assume it's JSON
                with open(gold_path, 'r') as f:
                    self.gold_standard = json.load(f)
        except Exception as e:
            print(f"Warning: Failed to load gold standard: {e}")
    
    def _convert_compmodel_to_gold_standard(self, compmodel_path: str) -> Dict[str, Any]:
        """
        Convert .compmodel XML file to gold standard JSON format.
        
        Args:
            compmodel_path: Path to .compmodel file
            
        Returns:
            Dictionary in gold standard format
        """
        try:
            # Parse XML with proper namespace handling
            # Some .compmodel files may have namespace issues, so we'll handle them gracefully
            parser = ET.XMLParser()
            try:
                tree = ET.parse(compmodel_path, parser=parser)
                root = tree.getroot()
            except ET.ParseError as e:
                # Try to fix common XML namespace issues
                if "unbound prefix" in str(e) or "prefix" in str(e).lower():
                    # Read file and fix namespace declarations
                    with open(compmodel_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    # Ensure xsi namespace is declared if used
                    if 'xsi:type' in content and 'xmlns:xsi' not in content:
                        # Add xsi namespace declaration
                        content = content.replace(
                            'xmlns:compartmental=',
                            'xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:compartmental=',
                            1
                        )
                    
                    # Parse the fixed content
                    root = ET.fromstring(content)
                else:
                    raise
            
            # Extract compartments
            compartments = []
            for comp in root.findall('.//{http://example.com/compartmentalmodel}compartments'):
                comp_name = comp.get('PrimaryName', '')
                if comp_name:
                    compartments.append(comp_name)
            
            # Also try without namespace (for compatibility)
            if not compartments:
                for comp in root.findall('.//compartments'):
                    comp_name = comp.get('PrimaryName', '')
                    if comp_name:
                        compartments.append(comp_name)
            
            # Extract parameters
            parameters = []
            for param in root.findall('.//{http://example.com/compartmentalmodel}parameters'):
                param_name = param.get('name', '')
                if param_name and param_name.lower() not in ['none', 'n/a', '']:
                    parameters.append(param_name)
            
            # Also try without namespace (for compatibility)
            if not parameters:
                for param in root.findall('.//parameters'):
                    param_name = param.get('name', '')
                    if param_name and param_name.lower() not in ['none', 'n/a', '']:
                        parameters.append(param_name)
            
            # Extract flows
            flows = []
            namespace = "{http://example.com/compartmentalmodel}"
            
            # First, get all compartments with their indices for reference
            comp_elements = root.findall(f'.//{namespace}compartments')
            if not comp_elements:
                comp_elements = root.findall('.//compartments')
            
            # Iterate through compartments and get their outgoing flows
            for comp in comp_elements:
                source_comp = comp.get('PrimaryName', '')
                if not source_comp:
                    continue
                
                # Get outgoing flows for this compartment
                outgoing_flows = comp.findall(f'{namespace}outgoingFlows')
                if not outgoing_flows:
                    outgoing_flows = comp.findall('outgoingFlows')
                
                for flow in outgoing_flows:
                    target_comp = None
                    
                    # Get target compartment from target reference
                    target_ref = flow.get('target', '')
                    if target_ref:
                        # Handle references like "//@compartments.2" or "//@compartments.0"
                        if 'compartments.' in target_ref:
                            try:
                                import re
                                match = re.search(r'compartments\.(\d+)', target_ref)
                                if match:
                                    comp_index = int(match.group(1))
                                    if comp_index < len(comp_elements):
                                        target_comp = comp_elements[comp_index].get('PrimaryName', '')
                            except (ValueError, IndexError, AttributeError):
                                pass
                    
                    if target_comp:
                        flow_key = f"{source_comp}->{target_comp}"
                        if flow_key not in flows:
                            flows.append(flow_key)
            
            return {
                "gold_entities": {
                    "compartments": compartments,
                    "parameters": parameters,
                    "flows": flows
                },
                "source": str(compmodel_path),
                "source_type": "compmodel"
            }
        except Exception as e:
            print(f"Warning: Failed to parse .compmodel file {compmodel_path}: {e}")
            return {"gold_entities": {"compartments": [], "parameters": [], "flows": []}}
    
    def evaluate(self, extracted_entities: Dict[str, Any],
                 traceability: Dict[str, Any]) -> Dict[str, Any]:
        """
        Evaluate extraction quality.
        
        Returns:
            Dictionary with evaluation metrics
        """
        metrics = {
            "traceability_coverage": self._calculate_traceability_coverage(traceability),
            "faithfulness": self._calculate_faithfulness(traceability),
            "gold_standard_comparison": None
        }
        
        # Gold standard comparison if available
        if self.gold_standard:
            metrics["gold_standard_comparison"] = self._compare_to_gold_standard(
                extracted_entities, self.gold_standard
            )
        
        return metrics
    
    def _calculate_traceability_coverage(self, traceability: Dict[str, Any]) -> Dict[str, Any]:
        """Calculate traceability coverage metrics"""
        metrics = traceability.get("coverage_metrics", {})
        return {
            "total_items": metrics.get("total_items", 0),
            "items_with_evidence": metrics.get("items_with_evidence", 0),
            "coverage_percentage": metrics.get("coverage_percentage", 0.0)
        }
    
    def _calculate_faithfulness(self, traceability: Dict[str, Any]) -> Dict[str, Any]:
        """Calculate faithfulness metrics"""
        metrics = traceability.get("coverage_metrics", {})
        return {
            "paper_backed_items": metrics.get("paper_backed_items", 0),
            "faithfulness_percentage": metrics.get("faithfulness_percentage", 0.0)
        }
    
    def _normalize_for_comparison(self, name: str) -> str:
        """
        Normalize name for comparison (handles common variations).
        
        This handles:
        - Case insensitivity
        - Common synonyms (Infected vs Infectious, etc.)
        - Greek letter variations (β vs beta, etc.) - bidirectional
        - Parameter semantic synonyms (beta = transmission_rate = contact_rate)
        - Plural/singular variations
        - Common typos and spacing
        - Unicode normalization
        - "Longer but same meaning": strip trailing population/host suffixes
          (e.g. "Susceptible humans" vs "Susceptible", "Eggs(non-infectious)" vs "Eggs")
        - Parameter subscripts: strip trailing _h, _v, _letter for comparison
          (e.g. beta_h vs beta, gamma_h vs gamma)
        """
        if not name:
            return ""
        
        import unicodedata
        import re
        
        # Normalize Unicode (handles different representations of same characters)
        name = unicodedata.normalize('NFKD', name)
        
        name = name.strip().lower()
        
        # Remove common prefixes/suffixes (compartment/state/class)
        name = name.replace('compartment', '').replace('state', '').replace('class', '')
        name = name.strip()

        # Strip parenthetical qualifiers first (e.g. "Eggs(non-infectious)" -> "Eggs")
        name = re.sub(r'\s*\([^)]*\)\s*', '', name, flags=re.IGNORECASE).strip()

        # Strip leading qualifiers (e.g. "Vector Eggs" -> "Eggs", "Total Infected" -> "Infected")
        name = re.sub(r'^(vector|total|cumulative|net|new)\s+', '', name, flags=re.IGNORECASE).strip()

        # Strip trailing population/host suffixes so "Susceptible humans" matches "Susceptible"
        trailing_phrases = [
            r'\s+humans?$', r'\s+mosquitoes?$', r'\s+vectors?$', r'\s+adults?$',
            r'\s+children$', r'\s+juveniles?$',
            r'\s+female\s*$', r'\s+male\s*$', r'\s+human\s*$',
            r'\s+mosquito\s*$', r'\s+vector\s*$', r'\s+adult\s*$',
        ]
        for pat in trailing_phrases:
            name = re.sub(pat, '', name, flags=re.IGNORECASE)
        name = name.strip()

        # Parameter subscripts: strip trailing _letter or _digits so beta_h, gamma_h, mu_1 match beta, gamma, mu
        name = re.sub(r'_[a-z]\b', '', name, flags=re.IGNORECASE)
        name = re.sub(r'_\d+\b', '', name)
        name = name.strip()
        
        # Greek letter mapping (bidirectional: both β→beta and beta→β)
        # First, convert Greek letters to their names
        greek_to_name = {
            'α': 'alpha', 'β': 'beta', 'γ': 'gamma', 'δ': 'delta',
            'ε': 'epsilon', 'ζ': 'zeta', 'η': 'eta', 'θ': 'theta',
            'ι': 'iota', 'κ': 'kappa', 'λ': 'lambda', 'μ': 'mu',
            'ν': 'nu', 'ξ': 'xi', 'ο': 'omicron', 'π': 'pi',
            'ρ': 'rho', 'σ': 'sigma', 'τ': 'tau', 'υ': 'upsilon',
            'φ': 'phi', 'χ': 'chi', 'ψ': 'psi', 'ω': 'omega'
        }
        
        # Replace Greek letters with their names
        for greek_char, greek_name in greek_to_name.items():
            name = name.replace(greek_char, greek_name)
        
        # Compartment synonyms (map to canonical forms)
        compartment_synonyms = {
            'infected': 'infectious',
            'removed': 'recovered',
            'deceased': 'dead',
            'death': 'dead',
        }
        
        # Parameter semantic synonym groups (all map to same canonical form)
        # Group 1: Transmission parameters
        transmission_synonyms = ['beta', 'transmission', 'transmissionrate', 'contact', 
                                'contactrate', 'infectious', 'infectionrate', 'spread']
        # Group 2: Recovery parameters
        recovery_synonyms = ['gamma', 'recovery', 'recoveryrate', 'cure', 'cure rate']
        # Group 3: Death/Mortality parameters
        death_synonyms = ['mu', 'death', 'deathrate', 'mortality', 'mortalityrate', 'die']
        # Group 4: Progression/Incubation parameters
        progression_synonyms = ['sigma', 'progression', 'progressionrate', 'incubation', 
                                'incubationrate', 'latent', 'latentrate']
        # Group 5: Birth parameters
        birth_synonyms = ['birth', 'birthrate', 'recruitment', 'recruitmentrate']
        # Group 6: Natural death (vs disease death)
        natural_death_synonyms = ['mu_h', 'naturaldeath', 'naturaldeathrate', 'baseline_mortality']
        
        # Check if name contains any synonym from each group and normalize to canonical form
        name_lower = name.lower()
        
        # Check compartment synonyms first
        for synonym, canonical in compartment_synonyms.items():
            if synonym in name_lower:
                name = name.replace(synonym, canonical)
                break
        
        # Check parameter synonym groups
        if any(syn in name_lower for syn in transmission_synonyms):
            name = name.replace('beta', 'transmission').replace('transmissionrate', 'transmission')
            name = name.replace('contact', 'transmission').replace('contactrate', 'transmission')
            name = name.replace('infectious', 'transmission').replace('infectionrate', 'transmission')
            name = name.replace('spread', 'transmission')
        elif any(syn in name_lower for syn in recovery_synonyms):
            name = name.replace('gamma', 'recovery').replace('recoveryrate', 'recovery')
            name = name.replace('cure', 'recovery')
        elif any(syn in name_lower for syn in death_synonyms):
            name = name.replace('mu', 'death').replace('deathrate', 'death')
            name = name.replace('mortality', 'death').replace('mortalityrate', 'death')
            name = name.replace('die', 'death')
        elif any(syn in name_lower for syn in progression_synonyms):
            name = name.replace('sigma', 'progression').replace('progressionrate', 'progression')
            name = name.replace('incubation', 'progression').replace('incubationrate', 'progression')
            name = name.replace('latent', 'progression').replace('latentrate', 'progression')
        elif any(syn in name_lower for syn in birth_synonyms):
            name = name.replace('birthrate', 'birth').replace('recruitment', 'birth')
            name = name.replace('recruitmentrate', 'birth')
        elif any(syn in name_lower for syn in natural_death_synonyms):
            name = name.replace('mu_h', 'naturaldeath').replace('naturaldeathrate', 'naturaldeath')
            name = name.replace('baseline_mortality', 'naturaldeath')
        
        # Remove spaces, underscores, hyphens, dots for comparison
        name = name.replace(' ', '').replace('_', '').replace('-', '').replace('.', '')

        # Strip trailing digits (e.g. e1→e, i2→i — compartment numbering)
        name = re.sub(r'\d+$', '', name)
        
        # Remove plural 's' at the end (simple heuristic)
        if name.endswith('s') and len(name) > 3:
            name = name[:-1]

        # Expand common single/double-letter epidemiology abbreviations so that
        # short names like "s", "e", "i", "r", "di" can match full gold-standard names.
        _COMP_ABBREV = {
            's': 'susceptible', 'e': 'exposed', 'i': 'infectious',
            'r': 'recovered', 'd': 'dead', 'v': 'vaccinated',
            'h': 'hospitalized', 'q': 'quarantined', 'a': 'asymptomatic',
            'l': 'latent',
            'di': 'infectiousdeceased',
            'dh': 'deadhuman', 'dm': 'deadmosquito',
            'sh': 'susceptible', 'eh': 'exposed', 'ih': 'infectious',
            'rh': 'recovered', 'ah': 'asymptomatic',
            'sv': 'susceptible', 'iv': 'infectious', 'ev': 'exposed',
        }
        if len(name) <= 2:
            name = _COMP_ABBREV.get(name, name)
        
        return name
    
    def _fuzzy_match(self, name1: str, name2: str, threshold: float = 0.8) -> Tuple[bool, float]:
        """
        Check if two names match using fuzzy matching.
        
        Args:
            name1: First name to compare
            name2: Second name to compare
            threshold: Similarity threshold (0.0 to 1.0)
            
        Returns:
            Tuple of (is_match, similarity_score)
        """
        # Normalize both names
        norm1 = self._normalize_for_comparison(name1)
        norm2 = self._normalize_for_comparison(name2)
        
        # Exact match after normalization
        if norm1 == norm2:
            return True, 1.0
        
        # Calculate similarity using SequenceMatcher
        similarity = SequenceMatcher(None, norm1, norm2).ratio()
        
        # Substring matching for cases like "Susceptible" vs "Susceptibles"
        # Only apply when the shorter string is at least 4 chars to prevent
        # single-letter names like "s" from matching everything containing 's'
        min_len = min(len(norm1), len(norm2))
        if min_len >= 4 and (norm1 in norm2 or norm2 in norm1):
            similarity = max(similarity, 0.9)
        
        is_match = similarity >= threshold
        return is_match, similarity
    
    def _match_entities_fuzzy(self, extracted: Set[str], gold: Set[str], 
                             threshold: float = 0.8) -> Tuple[Set[Tuple[str, str, float]], Set[str], Set[str]]:
        """
        Match extracted entities to gold standard using fuzzy matching.
        
        Uses global-optimal greedy matching: compute ALL pairwise similarities,
        then assign from highest to lowest. This prevents a lower-quality match
        from "stealing" a gold entity that a better candidate needs.
        
        Args:
            extracted: Set of extracted entity names
            gold: Set of gold standard entity names
            threshold: Similarity threshold for matching
            
        Returns:
            Tuple of (matched_pairs, unmatched_extracted, unmatched_gold)
            matched_pairs: Set of (extracted_name, gold_name, similarity) tuples
        """
        # 1. Compute all pairwise similarities above threshold
        candidates = []
        for ext_name in extracted:
            for gold_name in gold:
                is_match, similarity = self._fuzzy_match(ext_name, gold_name, threshold)
                if is_match:
                    candidates.append((similarity, ext_name, gold_name))
        
        # 2. Sort by similarity descending — best matches are assigned first
        candidates.sort(key=lambda x: -x[0])
        
        # 3. Greedy assignment from highest to lowest similarity
        matched_pairs = set()
        matched_extracted = set()
        matched_gold = set()
        
        for similarity, ext_name, gold_name in candidates:
            if ext_name in matched_extracted or gold_name in matched_gold:
                continue
            matched_pairs.add((ext_name, gold_name, similarity))
            matched_extracted.add(ext_name)
            matched_gold.add(gold_name)
        
        unmatched_extracted = extracted - matched_extracted
        unmatched_gold = gold - matched_gold
        
        return matched_pairs, unmatched_extracted, unmatched_gold
    
    def _compare_to_gold_standard(self, extracted: Dict[str, Any], 
                                  gold: Dict[str, Any]) -> Dict[str, Any]:
        """
        Compare extracted entities to gold standard using fuzzy matching.
        
        This handles:
        - Typos and spelling variations
        - Different naming conventions (camelCase vs snake_case)
        - Plural vs singular forms
        - Synonyms (Infected vs Infectious)
        - Greek letter variations (β vs beta)
        """
        if not self.gold_standard:
            return None
        
        # Extract entity lists (keep original names for reporting)
        extracted_comps = {c['normalized_name'] for c in extracted.get('compartments', [])}
        extracted_params = {p['normalized_name'] for p in extracted.get('parameters', [])}
        # Extract flows as "source->target" strings
        extracted_flows = {f"{f['source']}->{f['target']}" for f in extracted.get('flows', [])}
        
        gold_comps = set(gold.get('gold_entities', {}).get('compartments', []))
        gold_params = set(gold.get('gold_entities', {}).get('parameters', []))
        gold_flows = set(gold.get('gold_entities', {}).get('flows', []))
        
        comp_matches, comp_unmatched_ext, comp_unmatched_gold = self._match_entities_fuzzy(
            extracted_comps, gold_comps, threshold=self._entity_threshold
        )
        param_matches, param_unmatched_ext, param_unmatched_gold = self._match_entities_fuzzy(
            extracted_params, gold_params, threshold=self._entity_threshold
        )
        
        # Build mapping from extracted to gold compartment names (for flow matching)
        comp_extracted_to_gold = {ext: gold for ext, gold, _ in comp_matches}
        # Also add exact matches for compartments that matched exactly
        for ext_comp in extracted_comps:
            if ext_comp in gold_comps:
                comp_extracted_to_gold[ext_comp] = ext_comp

        # Second pass: description-based mapping for unmatched extracted compartments.
        # When the LLM extracts finer-grained compartments (e.g. "Symptomatic" and
        # "Asymptomatic" instead of a single "Infectious"), the names don't fuzzy-match.
        # But the descriptions usually mention the gold compartment name or a synonym.
        # This pass creates many-to-one mappings so that flow normalization still works.

        # Build search terms for each gold compartment (name + common synonyms)
        _epi_synonyms = {
            'infectious': ['infected', 'infection', 'infect', 'infectious'],
            'infected': ['infectious', 'infection', 'infect'],
            'susceptible': ['susceptible', 'suscept'],
            'exposed': ['exposed', 'latent', 'incubat'],
            'recovered': ['recovered', 'recovery', 'recover', 'immune', 'immunity'],
            'dead': ['dead', 'death', 'died', 'deceased', 'mortality', 'fatal'],
            'vaccinated': ['vaccinated', 'vaccination', 'vaccine', 'immunized'],
        }

        def _gold_search_terms(gold_name):
            """Generate search terms for a gold compartment name."""
            terms = set()
            glow = gold_name.lower().strip()
            # Add the name itself and its stem
            terms.add(glow)
            if glow.endswith('s') and len(glow) > 4:
                terms.add(glow[:-1])
            # Add words from multi-word names
            for word in glow.split():
                if len(word) >= 4:
                    terms.add(word)
                    if word.endswith('s') and len(word) > 4:
                        terms.add(word[:-1])
                    # Add synonyms for each word
                    for key, syns in _epi_synonyms.items():
                        if word.startswith(key[:5]) or key.startswith(word[:5]):
                            terms.update(syns)
            return terms

        for comp in extracted.get('compartments', []):
            comp_name = comp.get('normalized_name', '')
            if comp_name and comp_name not in comp_extracted_to_gold:
                desc = (comp.get('description') or comp.get('text_span') or '').lower()
                if not desc:
                    continue
                best_gold = None
                best_len = 0
                for gold_name in gold_comps:
                    for term in _gold_search_terms(gold_name):
                        if len(term) >= 4 and term in desc and len(term) > best_len:
                            best_gold = gold_name
                            best_len = len(term)
                if best_gold:
                    comp_extracted_to_gold[comp_name] = best_gold
                    # If this maps to a previously-unmatched gold compartment,
                    # count it as a compartment match (improves comp precision/recall)
                    if best_gold in comp_unmatched_gold:
                        comp_matches.add((comp_name, best_gold, 0.9))
                        comp_unmatched_ext.discard(comp_name)
                        comp_unmatched_gold.discard(best_gold)
        
        # Third pass: normalization-based mapping for numbered/variant compartments.
        # E.g. "Exposed 1" and "Exposed 2" both normalize to "exposed" and should
        # map to gold "Exposed" for flow normalization purposes.
        for comp in extracted.get('compartments', []):
            comp_name = comp.get('normalized_name', '')
            if comp_name and comp_name not in comp_extracted_to_gold:
                comp_norm = self._normalize_for_comparison(comp_name)
                if not comp_norm:
                    continue
                for gold_name in gold_comps:
                    gold_norm = self._normalize_for_comparison(gold_name)
                    if comp_norm == gold_norm:
                        comp_extracted_to_gold[comp_name] = gold_name
                        break

        # For flows, use fuzzy-matched compartment names
        # Convert extracted flows using matched compartment names
        normalized_extracted_flows = set()
        for flow_str in extracted_flows:
            if '->' in flow_str:
                source, target = flow_str.split('->', 1)
                # Use gold standard name if we have a match, otherwise use original
                norm_source = comp_extracted_to_gold.get(source.strip(), source.strip())
                norm_target = comp_extracted_to_gold.get(target.strip(), target.strip())
                normalized_extracted_flows.add(f"{norm_source}->{norm_target}")
            else:
                normalized_extracted_flows.add(flow_str)
        
        # Match flows using normalized names
        flow_tp = len(normalized_extracted_flows & gold_flows)
        flow_fp = len(normalized_extracted_flows - gold_flows)
        flow_fn = len(gold_flows - normalized_extracted_flows)
        
        # Also try fuzzy matching for flows that didn't match exactly
        # (compartment normalization handles "longer but same meaning" e.g. Susceptible humans vs Susceptible)
        unmatched_extracted_flows = normalized_extracted_flows - gold_flows
        unmatched_gold_flows = gold_flows - normalized_extracted_flows

        def _flow_key_normalized(flow_str: str) -> str:
            """Build a normalized flow key (source->target) for comparison."""
            if '->' not in flow_str:
                return flow_str
            src, tgt = flow_str.split('->', 1)
            return f"{self._normalize_for_comparison(src)}->{self._normalize_for_comparison(tgt)}"

        flow_fuzzy_matches = set()
        # Try exact match on normalized flow keys (e.g. susceptible->exposed vs susceptible humans->exposed humans)
        gold_flow_by_norm = {_flow_key_normalized(g): g for g in gold_flows}
        for ext_flow in list(unmatched_extracted_flows):
            norm_key = _flow_key_normalized(ext_flow)
            if norm_key in gold_flow_by_norm:
                gold_flow = gold_flow_by_norm[norm_key]
                if gold_flow in unmatched_gold_flows:
                    flow_fuzzy_matches.add((ext_flow, gold_flow, 1.0))
                    unmatched_extracted_flows.discard(ext_flow)
                    unmatched_gold_flows.discard(gold_flow)
                    del gold_flow_by_norm[norm_key]

        # Try to fuzzy match remaining flows (source/target fuzzy)
        for ext_flow in list(unmatched_extracted_flows):
            ext_source, ext_target = ext_flow.split('->', 1) if '->' in ext_flow else ('', '')
            for gold_flow in list(unmatched_gold_flows):
                gold_source, gold_target = gold_flow.split('->', 1) if '->' in gold_flow else ('', '')
                source_match, source_sim = self._fuzzy_match(
                    ext_source.strip(), gold_source.strip(), threshold=self._entity_threshold
                )
                target_match, target_sim = self._fuzzy_match(
                    ext_target.strip(), gold_target.strip(), threshold=self._entity_threshold
                )
                if source_match and target_match:
                    flow_fuzzy_matches.add((ext_flow, gold_flow, (source_sim + target_sim) / 2))
                    unmatched_extracted_flows.discard(ext_flow)
                    unmatched_gold_flows.discard(gold_flow)
                    break
        
        # Update flow metrics with fuzzy matches
        flow_tp += len(flow_fuzzy_matches)
        flow_fp = len(unmatched_extracted_flows)
        flow_fn = len(unmatched_gold_flows)
        
        # Calculate metrics
        comp_tp = len(comp_matches)
        comp_fp = len(comp_unmatched_ext)
        comp_fn = len(comp_unmatched_gold)
        
        param_tp = len(param_matches)
        param_fp = len(param_unmatched_ext)
        param_fn = len(param_unmatched_gold)
        
        comp_precision = comp_tp / (comp_tp + comp_fp) if (comp_tp + comp_fp) > 0 else 0
        comp_recall = comp_tp / (comp_tp + comp_fn) if (comp_tp + comp_fn) > 0 else 0
        
        param_precision = param_tp / (param_tp + param_fp) if (param_tp + param_fp) > 0 else 0
        param_recall = param_tp / (param_tp + param_fn) if (param_tp + param_fn) > 0 else 0
        
        flow_precision = flow_tp / (flow_tp + flow_fp) if (flow_tp + flow_fp) > 0 else 0
        flow_recall = flow_tp / (flow_tp + flow_fn) if (flow_tp + flow_fn) > 0 else 0
        
        # Prepare match details for debugging
        comp_match_details = [
            {"extracted": ext, "gold": gold, "similarity": sim}
            for ext, gold, sim in comp_matches
        ]
        param_match_details = [
            {"extracted": ext, "gold": gold, "similarity": sim}
            for ext, gold, sim in param_matches
        ]
        # Flow matches (exact + fuzzy matches)
        flow_matches = list(normalized_extracted_flows & gold_flows)
        # Add fuzzy matches
        flow_match_details = [{"extracted": ext, "gold": gold, "similarity": sim} 
                              for ext, gold, sim in flow_fuzzy_matches]
        # Add exact matches
        for flow in flow_matches:
            flow_match_details.append({"extracted": flow, "gold": flow, "similarity": 1.0})
        
        return {
            "compartments": {
                "precision": comp_precision,
                "recall": comp_recall,
                "f1": 2 * comp_precision * comp_recall / (comp_precision + comp_recall) if (comp_precision + comp_recall) > 0 else 0,
                "tp": comp_tp,
                "fp": comp_fp,
                "fn": comp_fn,
                "matches": comp_match_details,
                "unmatched_extracted": list(comp_unmatched_ext),
                "unmatched_gold": list(comp_unmatched_gold)
            },
            "parameters": {
                "precision": param_precision,
                "recall": param_recall,
                "f1": 2 * param_precision * param_recall / (param_precision + param_recall) if (param_precision + param_recall) > 0 else 0,
                "tp": param_tp,
                "fp": param_fp,
                "fn": param_fn,
                "matches": param_match_details,
                "unmatched_extracted": list(param_unmatched_ext),
                "unmatched_gold": list(param_unmatched_gold)
            },
            "flows": {
                "precision": flow_precision,
                "recall": flow_recall,
                "f1": 2 * flow_precision * flow_recall / (flow_precision + flow_recall) if (flow_precision + flow_recall) > 0 else 0,
                "tp": flow_tp,
                "fp": flow_fp,
                "fn": flow_fn,
                "matches": flow_match_details,
                "unmatched_extracted": list(unmatched_extracted_flows),
                "unmatched_gold": list(unmatched_gold_flows)
            },
            "matching_method": "fuzzy",
            "similarity_threshold": self._entity_threshold,
        }
    
    def save_evaluation(self, evaluation: Dict[str, Any], output_path: str):
        """Save evaluation results to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(evaluation, f, indent=2)
