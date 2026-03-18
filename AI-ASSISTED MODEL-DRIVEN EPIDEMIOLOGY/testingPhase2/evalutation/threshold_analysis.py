"""
Threshold Analysis Script
Compares measles gold standard against all measles model variants
and other diseases to validate cosine similarity threshold.
"""

import json
import numpy as np
from pathlib import Path
from sentence_transformers import SentenceTransformer, util
from scipy.optimize import linear_sum_assignment
from collections import defaultdict
import os

os.environ["TOKENIZERS_PARALLELISM"] = "false"

BASE_DIR = Path(
    "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/embeddings"
)
GOLD_DIR = BASE_DIR / "gold_embeddings"
MEASLES_DIR = BASE_DIR / "measles_embeddings"

THRESHOLDS = [0.5, 0.6, 0.7, 0.72, 0.75, 0.8, 0.85, 0.9]

print("Loading embedding model...")
model = SentenceTransformer("sentence-transformers/all-MiniLM-L6-v2")


def load_gold():
    with open(GOLD_DIR / "measles_faithful_gold_strings.json") as f:
        strings = json.load(f)
    embeddings = np.load(GOLD_DIR / "measles_faithful_gold_embeddings.npy")
    return strings, embeddings


def load_model_embeddings(model_dir, variant_name):
    emb_file = model_dir / f"{variant_name}_parsed_embeddings.npy"
    str_file = model_dir / f"{variant_name}_parsed_strings.json"

    if not emb_file.exists() or not str_file.exists():
        return None, None

    embeddings = np.load(emb_file)
    with open(str_file) as f:
        strings = json.load(f)
    return strings, embeddings


def compute_similarity(gold_embs, model_embs):
    return util.cos_sim(gold_embs, model_embs).numpy()


def find_best_matches(similarity_matrix, threshold):
    if similarity_matrix.size == 0:
        return [], [], []

    cost_matrix = 1 - similarity_matrix
    gold_indices, model_indices = linear_sum_assignment(cost_matrix)

    matches = []
    unmatched_gold = set(range(similarity_matrix.shape[0]))
    unmatched_model = set(range(similarity_matrix.shape[1]))

    for g_idx, m_idx in zip(gold_indices, model_indices):
        score = float(similarity_matrix[g_idx, m_idx])
        if score >= threshold:
            matches.append((g_idx, m_idx, score))
            unmatched_gold.discard(g_idx)
            unmatched_model.discard(m_idx)

    return matches, sorted(unmatched_gold), sorted(unmatched_model)


def categorize_strings(strings):
    comps = strings.get("comp", [])
    flows = strings.get("flow", [])
    params = strings.get("param", [])
    return comps, flows, params


def analyze_variant(
    gold_strings, gold_embeddings, model_strings, model_embeddings, variant_name
):
    gold_comps, gold_flows, gold_params = categorize_strings(gold_strings)
    model_comps, model_flows, model_params = categorize_strings(model_strings)

    n_gold_comp = len(gold_comps)
    n_gold_flow = len(gold_flows)
    n_gold_param = len(gold_params)

    n_model_comp = len(model_comps)
    n_model_flow = len(model_flows)
    n_model_param = len(model_params)

    similarity_matrix = compute_similarity(gold_embeddings, model_embeddings)

    results = {}
    all_boundary_cases = []

    for threshold in THRESHOLDS:
        total_tp, total_fp, total_fn = 0, 0, 0
        all_matches = []
        boundary_cases = []

        # Compartments
        if n_gold_comp > 0 and n_model_comp > 0:
            comp_sim = similarity_matrix[:n_gold_comp, :n_model_comp]
            matches, ug, um = find_best_matches(comp_sim, threshold)
            total_tp += len(matches)
            total_fp += len(um)
            total_fn += len(ug)
            for g_idx, m_idx, score in matches:
                b = "YES" if abs(score - threshold) < 0.05 else "NO"
                all_matches.append(
                    {
                        "type": "comp",
                        "gold": gold_comps[g_idx],
                        "model": model_comps[m_idx],
                        "score": round(score, 4),
                        "boundary": b,
                    }
                )
                if b == "YES":
                    boundary_cases.append(
                        {
                            "type": "comp",
                            "gold": gold_comps[g_idx],
                            "model": model_comps[m_idx],
                            "score": round(score, 4),
                            "threshold": threshold,
                        }
                    )

        # Flows
        if n_gold_flow > 0 and n_model_flow > 0:
            flow_sim = similarity_matrix[
                n_gold_comp : n_gold_comp + n_gold_flow,
                n_model_comp : n_model_comp + n_model_flow,
            ]
            matches, ug, um = find_best_matches(flow_sim, threshold)
            total_tp += len(matches)
            total_fp += len(um)
            total_fn += len(ug)
            for g_idx, m_idx, score in matches:
                b = "YES" if abs(score - threshold) < 0.05 else "NO"
                all_matches.append(
                    {
                        "type": "flow",
                        "gold": gold_flows[g_idx],
                        "model": model_flows[m_idx],
                        "score": round(score, 4),
                        "boundary": b,
                    }
                )
                if b == "YES":
                    boundary_cases.append(
                        {
                            "type": "flow",
                            "gold": gold_flows[g_idx],
                            "model": model_flows[m_idx],
                            "score": round(score, 4),
                            "threshold": threshold,
                        }
                    )

        # Parameters
        if n_gold_param > 0 and n_model_param > 0:
            param_offset_gold = n_gold_comp + n_gold_flow
            param_offset_model = n_model_comp + n_model_flow
            param_sim = similarity_matrix[
                param_offset_gold : param_offset_gold + n_gold_param,
                param_offset_model : param_offset_model + n_model_param,
            ]
            matches, ug, um = find_best_matches(param_sim, threshold)
            total_tp += len(matches)
            total_fp += len(um)
            total_fn += len(ug)
            for g_idx, m_idx, score in matches:
                b = "YES" if abs(score - threshold) < 0.05 else "NO"
                all_matches.append(
                    {
                        "type": "param",
                        "gold": gold_params[g_idx],
                        "model": model_params[m_idx],
                        "score": round(score, 4),
                        "boundary": b,
                    }
                )
                if b == "YES":
                    boundary_cases.append(
                        {
                            "type": "param",
                            "gold": gold_params[g_idx],
                            "model": model_params[m_idx],
                            "score": round(score, 4),
                            "threshold": threshold,
                        }
                    )

        precision = total_tp / (total_tp + total_fp) if (total_tp + total_fp) > 0 else 0
        recall = total_tp / (total_tp + total_fn) if (total_tp + total_fn) > 0 else 0
        f1 = (
            2 * precision * recall / (precision + recall)
            if (precision + recall) > 0
            else 0
        )

        results[threshold] = {
            "tp": total_tp,
            "fp": total_fp,
            "fn": total_fn,
            "precision": round(precision, 4),
            "recall": round(recall, 4),
            "f1": round(f1, 4),
            "total_gold": n_gold_comp + n_gold_flow + n_gold_param,
            "total_model": n_model_comp + n_model_flow + n_model_param,
            "num_compartments": {"gold": n_gold_comp, "model": n_model_comp},
            "num_flows": {"gold": n_gold_flow, "model": n_model_flow},
            "num_parameters": {"gold": n_gold_param, "model": n_model_param},
            "sample_matches": all_matches[:15],
            "boundary_cases": boundary_cases,
        }

        all_boundary_cases.extend(boundary_cases)

    return results, all_boundary_cases


def analyze_other_diseases(gold_strings, gold_embeddings):
    """Compare measles gold to other diseases"""
    diseases = ["dengue", "ebola", "hiv", "malaria", "tuberculosis", "zika"]
    other_disease_dir = BASE_DIR

    results = {}

    for disease in diseases:
        disease_dir = other_disease_dir / f"{disease}_embeddings"
        if not disease_dir.exists():
            continue

        variant_files = list(disease_dir.glob(f"{disease}_*_parsed_embeddings.npy"))
        if not variant_files:
            continue

        variant_name = variant_files[0].stem.replace("_parsed_embeddings", "")
        model_strings, model_embeddings = load_model_embeddings(
            disease_dir, variant_name
        )

        if model_strings is None:
            continue

        sim_matrix = compute_similarity(gold_embeddings, model_embeddings)

        threshold_results = {}
        for threshold in THRESHOLDS:
            matches, ug, um = find_best_matches(sim_matrix, threshold)
            threshold_results[threshold] = {
                "matches": len(matches),
                "unmatched_gold": len(ug),
                "unmatched_model": len(um),
                "avg_score": round(
                    np.mean([m[2] for m in matches]) if matches else 0, 4
                )
                if matches
                else 0,
                "max_score": round(max([m[2] for m in matches]) if matches else 0, 4)
                if matches
                else 0,
                "min_matched_score": round(
                    min([m[2] for m in matches]) if matches else 0, 4
                )
                if matches
                else 0,
            }

        results[disease] = {"variant": variant_name, "thresholds": threshold_results}

    return results


def main():
    print("=" * 80)
    print("THRESHOLD ANALYSIS FOR COSINE SIMILARITY")
    print("=" * 80)

    print("\n[1/3] Loading measles gold standard...")
    gold_strings, gold_embeddings = load_gold()
    gold_comps, gold_flows, gold_params = categorize_strings(gold_strings)
    print(
        f"  Gold: {len(gold_comps)} comps, {len(gold_flows)} flows, {len(gold_params)} params"
    )

    print("\n[2/3] Analyzing all measles model variants...")
    variant_files = list(MEASLES_DIR.glob("*_parsed_embeddings.npy"))
    variants = sorted(
        set([f.stem.replace("_parsed_embeddings", "") for f in variant_files])
    )
    print(f"  Found {len(variants)} variants")

    all_results = {}
    all_variant_boundaries = {}

    for i, variant in enumerate(variants):
        print(f"  [{i + 1}/{len(variants)}] {variant}...", end=" ")
        model_strings, model_embeddings = load_model_embeddings(MEASLES_DIR, variant)
        if model_strings is None:
            print("SKIPPED (no files)")
            continue

        results, boundaries = analyze_variant(
            gold_strings, gold_embeddings, model_strings, model_embeddings, variant
        )
        all_results[variant] = results
        all_variant_boundaries[variant] = boundaries
        print(f"done")

    print("\n[3/3] Comparing measles gold to other diseases...")
    other_disease_results = analyze_other_diseases(gold_strings, gold_embeddings)

    # Generate detailed report
    print("\n" + "=" * 80)
    print("DETAILED RESULTS")
    print("=" * 80)

    for variant in variants:
        print(f"\n{'=' * 60}")
        print(f"VARIANT: {variant}")
        print(f"{'=' * 60}")

        variant_results = all_results[variant]

        # Print metrics at each threshold
        print("\nMetrics by Threshold:")
        print(
            f"{'Thresh':<8} {'TP':<5} {'FP':<5} {'FN':<5} {'Prec':<8} {'Rec':<8} {'F1':<8}"
        )
        print("-" * 55)
        for th in THRESHOLDS:
            r = variant_results[th]
            print(
                f"{th:<8} {r['tp']:<5} {r['fp']:<5} {r['fn']:<5} {r['precision']:<8} {r['recall']:<8} {r['f1']:<8}"
            )

        # Print counts
        r0 = variant_results[0.72]
        print(
            f"\nItem counts: {r0['num_compartments']} comps, {r0['num_flows']} flows, {r0['num_parameters']} params"
        )

        # Sample matches
        print(f"\nSample Matches at threshold=0.72:")
        for m in r0["sample_matches"][:5]:
            print(
                f"  [{m['type']:<5}] '{m['gold']}' -> '{m['model']}' (score: {m['score']}, boundary: {m['boundary']})"
            )

    # Boundary cases summary
    print("\n" + "=" * 80)
    print("BOUNDARY CASES SUMMARY (score within 0.05 of threshold)")
    print("=" * 80)

    for th in THRESHOLDS:
        print(f"\n--- Threshold {th} ---")
        count = 0
        for variant in variants:
            boundaries = all_variant_boundaries.get(variant, [])
            th_boundaries = [b for b in boundaries if b.get("threshold") == th]
            for b in th_boundaries:
                print(
                    f"  [{variant}] {b['type']}: '{b['gold']}' -> '{b['model']}' (score: {b['score']})"
                )
                count += 1
        if count == 0:
            print("  (none)")

    # Other diseases comparison
    print("\n" + "=" * 80)
    print("CROSS-DISEASE COMPARISON (measles gold vs other diseases)")
    print("=" * 80)

    for disease, data in other_disease_results.items():
        print(f"\n{disease.upper()} (using {data['variant']}):")
        print(
            f"  {'Thresh':<8} {'Matches':<10} {'Unmatched':<10} {'Avg Score':<12} {'Max Score':<12}"
        )
        print("  " + "-" * 55)
        for th in THRESHOLDS:
            r = data["thresholds"][th]
            print(
                f"  {th:<8} {r['matches']:<10} {r['unmatched_gold']:<10} {r['avg_score']:<12} {r['max_score']:<12}"
            )

    # Save full report
    full_report = {
        "analysis_type": "threshold_validation",
        "gold_standard": "measles_faithful_gold",
        "thresholds_tested": THRESHOLDS,
        "total_variants": len(variants),
        "variants": variants,
        "variant_results": all_results,
        "variant_boundaries": all_variant_boundaries,
        "cross_disease_comparison": other_disease_results,
        "observations": {
            "optimal_threshold": "To be determined based on manual review",
            "recommendation": "Review boundary cases and cross-disease matches",
        },
    }

    output_path = "/home/daedalus/git/Model-Driven-Epidemiology/AI-ASSISTED MODEL-DRIVEN EPIDEMIOLOGY/newPhase3/evalutation/threshold_analysis_report.json"
    with open(output_path, "w") as f:
        json.dump(full_report, f, indent=2, default=str)

    print(f"\n\nFull report saved to: {output_path}")


if __name__ == "__main__":
    main()
