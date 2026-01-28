"""Evaluation: Calculate metrics and scores

Calculates traceability coverage, faithfulness, gap metrics, and optionally
compares to gold standard for precision/recall.
"""

import json
import xml.etree.ElementTree as ET
from difflib import SequenceMatcher
from pathlib import Path
from typing import Dict, List, Any, Optional, Tuple, Set


class Evaluator:
    """Evaluate extraction quality with metrics"""
    
    def __init__(self, gold_standard_path: Optional[str] = None):
        """
        Initialize evaluator.
        
        Args:
            gold_standard_path: Path to gold standard JSON or .compmodel file (optional)
        """
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
                 traceability: Dict[str, Any],
                 gaps: Dict[str, Any]) -> Dict[str, Any]:
        """
        Evaluate extraction quality.
        
        Returns:
            Dictionary with evaluation metrics
        """
        metrics = {
            "traceability_coverage": self._calculate_traceability_coverage(traceability),
            "faithfulness": self._calculate_faithfulness(traceability),
            "gap_analysis": self._analyze_gaps(gaps),
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
    
    def _analyze_gaps(self, gaps: Dict[str, Any]) -> Dict[str, Any]:
        """Analyze gap metrics"""
        summary = gaps.get("summary", {})
        return {
            "total_gaps": summary.get("total_gaps", 0),
            "critical_gaps": summary.get("critical_gaps", 0),
            "high_gaps": summary.get("high_gaps", 0),
            "medium_gaps": summary.get("medium_gaps", 0),
            "gap_breakdown": {
                "missing_compartments": len(gaps.get("missing_compartments", [])),
                "missing_parameters": len(gaps.get("missing_parameters", [])),
                "missing_stratifications": len(gaps.get("missing_stratifications", [])),
                "missing_interventions": len(gaps.get("missing_interventions", []))
            }
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
        """
        if not name:
            return ""
        
        import unicodedata
        
        # Normalize Unicode (handles different representations of same characters)
        name = unicodedata.normalize('NFKD', name)
        
        name = name.strip().lower()
        
        # Remove common prefixes/suffixes
        name = name.replace('compartment', '').replace('state', '').replace('class', '')
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
        
        # Remove plural 's' at the end (simple heuristic)
        if name.endswith('s') and len(name) > 3:
            name = name[:-1]
        
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
        
        # Also try substring matching for cases like "Susceptible" vs "Susceptibles"
        if norm1 in norm2 or norm2 in norm1:
            similarity = max(similarity, 0.9)
        
        is_match = similarity >= threshold
        return is_match, similarity
    
    def _match_entities_fuzzy(self, extracted: Set[str], gold: Set[str], 
                             threshold: float = 0.8) -> Tuple[Set[Tuple[str, str, float]], Set[str], Set[str]]:
        """
        Match extracted entities to gold standard using fuzzy matching.
        
        Args:
            extracted: Set of extracted entity names
            gold: Set of gold standard entity names
            threshold: Similarity threshold for matching
            
        Returns:
            Tuple of (matched_pairs, unmatched_extracted, unmatched_gold)
            matched_pairs: Set of (extracted_name, gold_name, similarity) tuples
        """
        matched_pairs = set()
        matched_extracted = set()
        matched_gold = set()
        
        # Try to match each extracted entity to a gold entity
        for ext_name in extracted:
            best_match = None
            best_similarity = 0.0
            
            for gold_name in gold:
                if gold_name in matched_gold:
                    continue  # Already matched
                
                is_match, similarity = self._fuzzy_match(ext_name, gold_name, threshold)
                if is_match and similarity > best_similarity:
                    best_match = gold_name
                    best_similarity = similarity
            
            if best_match:
                matched_pairs.add((ext_name, best_match, best_similarity))
                matched_extracted.add(ext_name)
                matched_gold.add(best_match)
        
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
        
        # Use fuzzy matching with threshold of 0.75 (allows for some variation)
        comp_matches, comp_unmatched_ext, comp_unmatched_gold = self._match_entities_fuzzy(
            extracted_comps, gold_comps, threshold=0.75
        )
        param_matches, param_unmatched_ext, param_unmatched_gold = self._match_entities_fuzzy(
            extracted_params, gold_params, threshold=0.75
        )
        
        # Build mapping from extracted to gold compartment names (for flow matching)
        comp_extracted_to_gold = {ext: gold for ext, gold, _ in comp_matches}
        # Also add exact matches for compartments that matched exactly
        for ext_comp in extracted_comps:
            if ext_comp in gold_comps:
                comp_extracted_to_gold[ext_comp] = ext_comp
        
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
        # (in case compartment names still don't match after normalization)
        unmatched_extracted_flows = normalized_extracted_flows - gold_flows
        unmatched_gold_flows = gold_flows - normalized_extracted_flows
        
        # Try to fuzzy match remaining flows
        flow_fuzzy_matches = set()
        for ext_flow in list(unmatched_extracted_flows):
            ext_source, ext_target = ext_flow.split('->', 1) if '->' in ext_flow else ('', '')
            for gold_flow in list(unmatched_gold_flows):
                gold_source, gold_target = gold_flow.split('->', 1) if '->' in gold_flow else ('', '')
                # Check if both source and target match (fuzzy)
                source_match, source_sim = self._fuzzy_match(ext_source.strip(), gold_source.strip(), threshold=0.75)
                target_match, target_sim = self._fuzzy_match(ext_target.strip(), gold_target.strip(), threshold=0.75)
                if source_match and target_match:
                    flow_fuzzy_matches.add((ext_flow, gold_flow, (source_sim + target_sim) / 2))
                    unmatched_extracted_flows.remove(ext_flow)
                    unmatched_gold_flows.remove(gold_flow)
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
            "similarity_threshold": 0.75
        }
    
    def save_evaluation(self, evaluation: Dict[str, Any], output_path: str):
        """Save evaluation results to JSON"""
        output_path = Path(output_path)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        
        with open(output_path, 'w') as f:
            json.dump(evaluation, f, indent=2)
