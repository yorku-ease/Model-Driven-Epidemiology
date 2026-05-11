"""
Model Repair Module — Phase 4 (LLM-based model validation error fixing).

Fixes structural and semantic errors in `.compmodel` files using LLM prompts.
Designed to be called iteratively: one error at a time, preserving existing
valid elements.

Two modes:
  1. Structural repair (no LLM needed): fixes missing xsi:type, missing rate
     placeholders, invalid self-references.
  2. Semantic repair (LLM-based): fixes garbled names, wrong flow types,
     invalid parameter references.
"""

import importlib.util
import os
import re
from pathlib import Path
from typing import Optional

PHASE3_ROOT = Path(__file__).resolve().parent.parent.parent
PHASE2_ROOT = PHASE3_ROOT.parent / "phase 2"
PROMPT_PATH = PHASE3_ROOT / "data" / "prompts" / "model_repair_prompt.md"

# Load LLMClient from Phase 2
_llm_client_path = PHASE2_ROOT / "src" / "utils" / "llm_client.py"
_api_key_file = PHASE2_ROOT / ".api_key.txt"

LLMClient = None
if _llm_client_path.exists():
    spec = importlib.util.spec_from_file_location("llm_client_phase2", _llm_client_path)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    LLMClient = mod.LLMClient


def _fix_tag(tag_content: str) -> str:
    """Add missing xsi:type (RateFlow) and rate="0.0" to an outgoingFlows tag."""
    if 'xsi:type' not in tag_content:
        tag_content += ' xsi:type="compartmental:RateFlow"'
    if 'RateFlow' in tag_content or 'xsi:type' in tag_content:
        if 'rate=' not in tag_content and 'rateParameter=' not in tag_content \
           and 'contactRate=' not in tag_content and 'contactRateParameter=' not in tag_content:
            tag_content += ' rate="0.0"'
    return tag_content


def _repair_flow_tags(xml_str: str) -> str:
    """Add missing xsi:type and rate to outgoingFlows elements.
    
    Handles both self-closing (<... />) and open (<...>) tags.
    All outgoingFlows must have an xsi:type of either compartmental:RateFlow
    or compartmental:ContactFlow. This repair adds RateFlow as default since
    ContactFlow requires additional attributes (contactCompartment) that we
    cannot infer without LLM.
    """
    pattern = r'(<outgoingFlows\b)([^>]*?)(\s*/?\s*>)'
    def _fix(m):
        tag_name = m.group(1)
        attrs = m.group(2)
        closer = m.group(3)
        if 'xsi:type' in attrs:
            return m.group(0)
        return tag_name + _fix_tag(attrs) + closer
    return re.sub(pattern, _fix, xml_str, flags=re.DOTALL)


def _repair_self_loops(xml_str: str, compartments: list) -> str:
    """Remove outgoingFlows whose target points to their own compartment index.
    
    Returns the repaired XML and a count of removed flows.
    """
    count = 0
    # Find each compartment and check its flows
    comp_pattern = re.compile(
        r'(<compartments\s[^>]*?)(?:\s*(<outgoingFlows[^>]*?>)\s*)*?</compartments>',
        re.DOTALL
    )
    # Simpler approach: parse the XML to find self-referencing flows
    lines = xml_str.split('\n')
    result = []
    current_comp_idx = -1
    in_comp = False
    
    for line in lines:
        # Detect compartment start
        m = re.search(r'<compartments\s', line)
        if m:
            in_comp = True
            current_comp_idx += 1
        
        # Check for self-referencing flow
        if in_comp:
            m = re.search(r'target="//@compartments\.(\d+)"', line)
            if m and int(m.group(1)) == current_comp_idx and 'outgoingFlows' in line:
                count += 1
                continue  # Skip this line (remove the self-loop)
        
        result.append(line)
        
        # Detect compartment end
        if '</compartments>' in line:
            in_comp = False
    
    return '\n'.join(result), count


def structural_repair(xml_str: str) -> tuple:
    """Apply all structural repairs (no LLM needed).
    
    Returns (repaired_xml, stats_dict) where stats_dict contains counts
    of each repair type applied.
    """
    stats = {'xsi_type_added': 0, 'rate_added': 0, 'self_loops_removed': 0}
    
    # Count missing attributes before applying fix
    for m in re.finditer(r'<outgoingFlows\b[^>]*?>', xml_str):
        tag = m.group(0)
        if 'xsi:type' not in tag:
            stats['xsi_type_added'] += 1
        if 'rate=' not in tag and 'rateParameter=' not in tag \
           and 'contactRate=' not in tag and 'contactRateParameter=' not in tag:
            stats['rate_added'] += 1
    
    # Apply all structural fixes in one pass
    xml_str = _repair_flow_tags(xml_str)
    comp_count = len(re.findall(r'<compartments\s', xml_str))
    xml_str, removed = _repair_self_loops(xml_str, list(range(comp_count)))
    stats['self_loops_removed'] = removed
    
    return xml_str, stats


def llm_repair(
    current_model_xml: str,
    validation_error: str,
    provider: str = "gemini",
) -> Optional[str]:
    """Call LLM to fix one specific validation error.
    
    Args:
        current_model_xml: The current .compmodel XML content.
        validation_error: Description of the specific validation error to fix.
        provider: LLM provider (gemini or openai).
        
    Returns:
        Corrected XML string, or None on failure.
    """
    if not PROMPT_PATH.exists():
        raise FileNotFoundError(f"Prompt template not found: {PROMPT_PATH}")
    
    prompt_template = PROMPT_PATH.read_text(encoding="utf-8")
    prompt = prompt_template.replace("{CURRENT_MODEL_XML}", current_model_xml)
    prompt = prompt.replace("{VALIDATION_ERROR}", validation_error.strip())
    
    if LLMClient is None:
        raise RuntimeError("LLMClient not available (phase 2 llm_client.py not found)")
    
    client = LLMClient(provider=provider, api_key_file=str(_api_key_file))
    if not client.is_available():
        raise RuntimeError(f"LLM not available for provider: {provider}")
    
    # Use direct Gemini call since we need XML output, not JSON
    try:
        import google.generativeai as genai
        genai.configure(api_key=client.api_key)
        
        model = genai.GenerativeModel(
            os.getenv("GEMINI_MODEL", "gemini-2.5-flash")
        )
        response = model.generate_content(
            prompt,
            generation_config={
                "temperature": 0.1,
                "max_output_tokens": 4096,
            },
            safety_settings=[
                {"category": "HARM_CATEGORY_HARASSMENT", "threshold": "BLOCK_NONE"},
                {"category": "HARM_CATEGORY_HATE_SPEECH", "threshold": "BLOCK_NONE"},
                {"category": "HARM_CATEGORY_SEXUALLY_EXPLICIT", "threshold": "BLOCK_NONE"},
                {"category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold": "BLOCK_NONE"},
            ],
            request_options={"timeout": 120},
        )
        
        result_text = response.text.strip()
        
        # Extract XML from markdown code block
        if "```xml" in result_text:
            xml_part = result_text.split("```xml")[1].split("```")[0].strip()
        elif "```" in result_text:
            xml_part = result_text.split("```")[1].split("```")[0].strip()
        else:
            xml_part = result_text
        
        # Quick validation: check it looks like XML
        if not xml_part.startswith("<?xml") and not xml_part.startswith("<compartmental"):
            print(f"Warning: LLM response doesn't look like XML. First 200 chars: {xml_part[:200]}")
            return None
        
        return xml_part
        
    except ImportError:
        raise RuntimeError("google-generativeai package not installed")
    except Exception as e:
        print(f"LLM repair error: {e}")
        return None


def run_repair_pipeline(
    model_path: Path,
    provider: str = "gemini",
    max_iterations: int = 10,
) -> Path:
    """Run the complete structural + semantic repair pipeline on a model.
    
    1. Apply structural fixes (no LLM): xsi:type, rates, self-loops.
    2. For remaining issues, call LLM iteratively (one error per call).
    
    Args:
        model_path: Path to the .compmodel file.
        provider: LLM provider.
        max_iterations: Maximum number of LLM repair iterations.
        
    Returns:
        Path to the repaired model (same as input, overwritten).
    """
    print(f"[Repair] Starting repair pipeline: {model_path}")
    
    xml_str = model_path.read_text(encoding="utf-8")
    
    # Step 1: Structural repair (no LLM)
    xml_str, stats = structural_repair(xml_str)
    if any(v > 0 for v in stats.values()):
        print(f"[Repair] Structural fixes applied:")
        for k, v in stats.items():
            if v > 0:
                print(f"  - {k}: {v}")
        model_path.write_text(xml_str, encoding="utf-8")
    
    # Step 2: LLM repair for remaining issues
    # Build a list of known validation errors in the model
    errors = _detect_remaining_errors(xml_str)
    
    if not errors:
        print(f"[Repair] No remaining errors detected.")
        return model_path
    
    print(f"[Repair] {len(errors)} error(s) detected for LLM repair.")
    
    iteration = 0
    while errors and iteration < max_iterations:
        iteration += 1
        error = errors[0]  # Fix one at a time
        
        print(f"[Repair] Iteration {iteration}: {error['description'][:80]}...")
        
        repaired = llm_repair(xml_str, error['description'], provider=provider)
        if repaired is None:
            print(f"[Repair] LLM repair failed for error: {error['description'][:60]}")
            errors.pop(0)
            continue
        
        xml_str = repaired
        model_path.write_text(xml_str, encoding="utf-8")
        
        # Re-detect errors
        errors = _detect_remaining_errors(xml_str)
        if errors:
            print(f"[Repair] {len(errors)} error(s) remaining.")
    
    print(f"[Repair] Pipeline complete after {iteration} iteration(s).")
    return model_path


def _detect_remaining_errors(xml_str: str) -> list:
    """Detect remaining structural and semantic issues in the model XML.
    
    Returns a list of error dicts with 'description' key.
    """
    errors = []
    
    lines = xml_str.split('\n')
    current_comp_idx = -1
    current_comp_name = ""
    
    for i, line in enumerate(lines):
        # Track compartment context
        m = re.search(r'<compartments\s[^>]*?PrimaryName="([^"]*)"', line)
        if m:
            current_comp_idx += 1
            current_comp_name = m.group(1)
        
        # Detect outgoingFlows without xsi:type
        if 'outgoingFlows' in line and 'xsi:type' not in line:
            target = re.search(r'target="(//@compartments\.\d+)"', line)
            target_str = target.group(1) if target else "?"
            errors.append({
                'line': i + 1,
                'description': (
                    f"outgoingFlows missing xsi:type attribute.\n"
                    f"Line {i+1}: {line.strip()}\n"
                    f"Compartment {current_comp_idx} \"{current_comp_name}\" "
                    f"-> {target_str}\n"
                    f"Add xsi:type=\"compartmental:RateFlow\" (or ContactFlow "
                    f"if this is an infection transmission).\n"
                    f"Also add rate=\"0.0\" or rateParameter=\"//@parameters.X\" "
                    f"if not present."
                )
            })
            continue
        
        # Detect RateFlow without rate or rateParameter
        if 'outgoingFlows' in line and 'xsi:type' in line:
            if 'rate=' not in line and 'rateParameter=' not in line \
               and 'contactRate=' not in line and 'contactRateParameter=' not in line:
                target = re.search(r'target="(//@compartments\.\d+)"', line)
                target_str = target.group(1) if target else "?"
                errors.append({
                    'line': i + 1,
                    'description': (
                        f"outgoingFlows missing rate/rateParameter.\n"
                        f"Line {i+1}: {line.strip()}\n"
                        f"Compartment {current_comp_idx} \"{current_comp_name}\" "
                        f"-> {target_str}\n"
                        f"Add rate=\"0.0\" or rateParameter=\"//@parameters.X\"."
                    )
                })
        
        # Detect self-referencing flows
        if 'outgoingFlows' in line:
            m = re.search(r'target="//@compartments\.(\d+)"', line)
            if m and int(m.group(1)) == current_comp_idx:
                errors.append({
                    'line': i + 1,
                    'description': (
                        f"Self-referencing flow: compartment {current_comp_idx} "
                        f"\"{current_comp_name}\" targets itself.\n"
                        f"Line {i+1}: {line.strip()}\n"
                        f"This flow should be removed or retargeted to the "
                        f"correct downstream compartment."
                    )
                })
                continue
        
        # Detect ContactFlow from non-susceptible compartment (suspicious)
        if 'outgoingFlows' in line and 'xsi:type' in line \
           and 'ContactFlow' in line and 'compartmental:ContactFlow' in line:
            if current_comp_name.lower() not in ('susceptible',):
                # This might be wrong — ContactFlow should typically originate from Susceptible
                pass  # Don't flag as error, just note
        
        # Detect RateFlow from susceptible -> exposed (should be ContactFlow)
        if 'outgoingFlows' in line and 'RateFlow' in line \
           and current_comp_name.lower() == 'susceptible':
            errors.append({
                'line': i + 1,
                'description': (
                    f"Flow type mismatch: susceptible -> exposed should be "
                    f"ContactFlow, not RateFlow.\n"
                    f"Line {i+1}: {line.strip()}\n"
                    f"Compartment \"{current_comp_name}\" (index {current_comp_idx})\n"
                    f"The infection transmission from Susceptible to Exposed "
                    f"depends on contact with Infectious individuals. "
                    f"Change xsi:type to \"compartmental:ContactFlow\", "
                    f"add contactCompartment pointing to the Infectious "
                    f"compartment, and add contactRateParameter or contactRate."
                )
            })
    
    return errors
