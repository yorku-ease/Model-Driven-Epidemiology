# Phase 4: Uncertainty quantification

Phase 4 turns **filled** compartmental models (from Phase 3) into **uncertainty-aware** outputs: typed parameter distributions, Monte Carlo ensemble simulation, one-at-a-time sensitivity analysis, plots, and per-paper reports.

## What it does

| Step | Task | Output |
|------|------|--------|
| 9.1 | Assign parameter distributions via `general_framework.json` | `parameter_distributions.json` |
| 9.2 | Monte Carlo simulation (500–1000 samples, 200 days) | `monte_carlo_results.json`, `uncertainty_bands.png` |
| 9.3 | One-at-a-time sensitivity (±20% perturbation, rank by peak + total cases) | `sensitivity_results.json`, `sensitivity_tornado.png` |
| 10 | Markdown report with provenance, tables, and plot links | `PHASE4_REPORT.md` |

## Input: Phase 3 filled models

Phase 3 now produces filled models under:
```
phase 3/showcase_gemini/both/<disease>_gemini_phase3/model_filled.compmodel
```
e.g. `covid1_gemini_phase3/model_filled.compmodel`, `influenza3_gemini_phase3/model_filled.compmodel`, etc. (30 papers total: 10 diseases × 3 papers each).

## Running Phase 4

```bash
cd "phase 4"

# Recommended: auto-picks the best fill mode per paper
python3 run_phase4.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode auto \
    --output reports \
    --samples 1000 \
    --days 200

# Use a specific fill mode for all papers
python3 run_phase4.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode both \
    --output reports

# Quick test — single paper, 100 samples
python3 run_phase4.py \
    --model "../phase 3/showcase_gemini/both/covid1_gemini_phase3/model_filled.compmodel" \
    --output reports/single \
    --samples 100
```

### How `--mode auto` works

For each paper, Phase 4 evaluates all three fill modes (`rag_only`, `llm_only`, `both`) and picks the one with the highest score:

```
score = comp_recall + flow_recall − 0.05 × param_gaps_after
```

| Term | Source | Weight |
|------|--------|--------|
| `comp_recall` | `structural_alignment.filled_vs_gold.compartments.recall` in `phase3_validation.json` | 1.0 |
| `flow_recall` | `structural_alignment.filled_vs_gold.flows.recall` in `phase3_validation.json` | 1.0 |
| `param_gaps_after` | `len(missing_parameters)` in `phase3_gaps.json` | −0.05 per gap |

The small gap penalty (0.05) means a mode needs to reduce parameter gaps by >20 to outweigh a 1-point recall advantage. The winning mode is recorded in the report's **Source model provenance** table. When all modes tie (common when scores are identical), `both` is chosen first by sort order.

## Populating selected_models/ (optional legacy layout)

If you need the old `selected_models/<disease>/model_filled.compmodel` layout:

```bash
python3 create_selected_models.py \
    --showcase-dir "../phase 3/showcase_gemini" \
    --mode both \
    --output "../phase 3/selected_models"

# Then use legacy mode
python3 run_phase4.py --selected-models "../phase 3/selected_models" --output reports
```

## Layout

```
phase 4/
├── run_phase4.py               # Main entry point
├── create_selected_models.py   # Helper: build selected_models/ from showcase
├── data/
│   └── general_framework.json  # Typed parameter priors and distribution families
├── src/
│   ├── distributions.py        # Task 9.1 — assign distributions
│   ├── monte_carlo.py          # Task 9.2 — ensemble simulation
│   ├── sensitivity.py          # Task 9.3 — OAT sensitivity
│   ├── visualization.py        # Task 10.1 — plots
│   └── report.py               # Task 10.2 — PHASE4_REPORT.md
└── reports/
    └── <disease_stem>/         # e.g. covid1/, influenza3/
        ├── PHASE4_REPORT.md
        ├── parameter_distributions.json
        ├── monte_carlo_results.json
        ├── sensitivity_results.json
        ├── uncertainty_bands.png
        └── sensitivity_tornado.png
```

## Results (30 papers, current run)

### Status summary

| Step | Status | Notes |
|------|--------|-------|
| 9.1 Distributions | ✅ All 30 papers | 5–37 parameters per paper |
| 9.2 Monte Carlo | ✅ All 30 papers | 1000 samples × 200 days per paper |
| 9.3 Sensitivity | ✅ All 30 papers | OAT ±20% perturbation, ranked by combined peak + total cases impact |
| 10 Reports | ✅ All 30 papers | `PHASE4_REPORT.md` per paper under `reports/<stem>/` |

### Parameter distribution summary

The general framework classifies extracted parameters into two families:

| Disease group | Typical param count | Dominant family |
|---------------|--------------------|-----------------| 
| Simple SIR-type (Cholera P1, COVID P2, Zika P3) | 5–10 | lognormal (rates) |
| Complex intervention models (COVID P1, Tuberculosis P3, Cholera P2) | 33–37 | mixed (uniform for coverage/efficacy, lognormal for rates) |

**Lognormal** is assigned to rate-type parameters (transmission, recovery, mortality, progression) — rates are strictly positive and right-skewed.  
**Uniform** is assigned to coverage fractions, efficacy bounds, and parameters where only a literature range is known without a central estimate.

### Monte Carlo — uncertainty spread

The table below shows the **peak epidemic size** range across 1000 simulations for the primary infectious compartment of each paper (P05 = pessimistic / low-transmission run, P50 = median, P95 = high-transmission run):

| Paper | Infectious compartment | P05 peak | P50 peak | P95 peak | Spread |
|-------|----------------------|----------|----------|----------|--------|
| COVID-19 P1 | Infectious Presymptomatic | 140 | 258 | 361 | **86%** |
| COVID-19 P2 | Infectious | 612 | 881 | 915 | 34% |
| Ebola P2 | Infectious (community) | 333 | 645 | 1220 | **138%** |
| Ebola P3 | Infectious | 72 | 109 | 165 | 86% |
| HIV P2 | Pre-AIDS | 38,665 | 98,864 | 263,720 | **228%** |
| Influenza P1 | Infectious | 338 | 547 | 27,571 | **4,982%** |
| Influenza P2 | Infectious | 856 | 924 | 945 | 10% |
| Malaria P1 | Infectious Humans | 275 | 329 | 377 | 31% |
| Measles P2 | Infectious | 179 | 766 | 926 | **98%** |
| Tuberculosis P2 | Infectious TB | 811 | 861 | 970 | 18% |
| Zika P1 | Infectious Humans | 5.4M | 10.3M | 14.5M | **88%** |

*Papers not listed produced flat or non-convergent trajectories (see note below).*

**Spread %** = (P95 peak − P05 peak) / P50 peak × 100. A spread of 100% means the high-end trajectory produces double the peak cases of the low-end trajectory given parameter uncertainty.

**Note — flat trajectories (0% spread):** Several models (Cholera P1/P3, Dengue P1, Ebola P1, HIV P1/P3, Malaria P2, Zika P2/P3) show identical P05/P50/P95 values. This happens when the Phase 1 ODE simulator's initial conditions saturate to a fixed point regardless of parameter variation — typically because the model immediately reaches its population ceiling (e.g. S₀=N, I₀=N). These papers still have valid sensitivity rankings but their uncertainty bands are not interpretable without adjusting initial conditions.

### Sensitivity analysis — top influential parameter per paper

Sensitivity is measured as the normalized change in **peak infections** and **total cases** when a parameter is perturbed ±20% from its point estimate. Parameters are ranked by combined importance.

| Paper | Most influential parameter | Type | Interpretation |
|-------|--------------------------|------|----------------|
| Cholera P1 | β (transmission rate) | transmission | Classic SIR: transmission dominates peak size |
| Cholera P2 | Initial vaccine protection | efficacy | Vaccine model: coverage/efficacy drives outcomes |
| Cholera P3 | H (human population size) | structural | Scaling parameter governs absolute case counts |
| COVID-19 P1 | Latent period | progression | SEIR dynamics: incubation length shifts epidemic timing |
| COVID-19 P2 | r (relative infectiousness) | transmission | Variant-specific transmissibility is the key lever |
| COVID-19 P3 | R_t (effective reproduction number) | transmission | R_t directly controls whether epidemic grows or decays |
| Dengue P1 | B (mosquito birth rate) | vector | Vector dynamics dominate in vector-borne models |
| Dengue P2 | Per-exposure vaccine protection (seroneg.) | efficacy | Vaccine trial model: seronega. protection is uncertain |
| Dengue P3 | β_hv (human→vector transmission) | transmission | Cross-species transmission rate is the critical link |
| Ebola P1 | ψ1 (isolation success rate) | intervention | Ebola: effectiveness of isolation drives outbreak size |
| Ebola P2 | b (baseline contact rate) | transmission | Contact rate determines community spread |
| Ebola P3 | alpha (incubation rate) | progression | Incubation length shapes serial interval |
| HIV P1 | C (sexual contact rate) | transmission | Sexual network contact rate dominates HIV spread |
| HIV P2 | ART transmission reduction | intervention | Treatment as prevention: ART efficacy is the key policy lever |
| HIV P3 | r (progression rate) | progression | Rate of moving to AIDS stage drives long-term burden |
| Influenza P1 | σ (incubation rate) | progression | Fast incubation creates rapid epidemic growth |
| Influenza P2 | Generation time | progression | Generation time sets epidemic speed |
| Influenza P3 | κ (waning immunity rate) | immunity | Seasonal models are highly sensitive to immunity decay |
| Malaria P1 | γ (recovery rate) | recovery | Recovery rate determines infectious duration |
| Malaria P2 | φ (relapse rate) | progression | Relapse/dormancy is the defining feature of P. vivax |
| Malaria P3 | Drug efficacy (ACT) | intervention | ACT effectiveness drives treatment outcome uncertainty |
| Measles P1 | L (birth rate / susceptible inflow) | demographic | Demographic turnover replenishes susceptible pool |
| Measles P2 | Infection rate | transmission | Core transmission parameter |
| Measles P3 | Force of infection | transmission | Aggregate transmission pressure dominates |
| Tuberculosis P1 | A (fast progression rate) | progression | Fast vs slow TB progression is the main bifurcation |
| Tuberculosis P2 | Change in contact rate | intervention | Policy-driven contact reduction is the key uncertainty |
| Tuberculosis P3 | β (transmission rate) | transmission | Baseline transmission dominates long-term TB burden |
| Zika P1 | M (mosquito density) | vector | Mosquito density determines exposure intensity |
| Zika P2 | a (biting rate) | vector | Biting rate links vector abundance to transmission |
| Zika P3 | r (mosquito development rate) | vector | Vector lifecycle speed controls epidemic potential |

### Cross-disease patterns

Three dominant patterns emerge:

1. **Transmission-dominated** (Cholera P1, Dengue P3, Influenza P1–P3, Measles, Tuberculosis P3, COVID P2/P3): β or R_t is the top parameter. Reducing transmission uncertainty gives the biggest model improvement.

2. **Intervention/policy-dominated** (Ebola P1, HIV P2, Malaria P3, Tuberculosis P2): An intervention parameter (isolation rate, ART efficacy, drug efficacy, contact reduction) tops the ranking. These models are sensitive to the exact value of a policy lever — small changes in intervention effectiveness lead to large changes in outcomes.

3. **Vector-dominated** (Dengue P1, Zika P1/P2/P3, Malaria P1): Mosquito biology parameters (birth rate, biting rate, density) drive uncertainty. Improving vector surveillance data would reduce model uncertainty most for these diseases.

## Key design choices

- **General framework:** `data/general_framework.json` maps parameter type keywords (transmission, recovery, mortality, …) to distribution families (lognormal/uniform) and plausible CV ranges — the same methodology applies to all diseases.
- **Phase 1 simulator:** `monte_carlo.py` and `sensitivity.py` import `GenericModelSimulator` from `phase 1/utils/` to actually run the ODE model.
- **Report provenance:** `PHASE4_REPORT.md` now includes which Phase 2 extractor was best for that paper and which Phase 3 fill mode was used.

## Documentation

| File | Purpose |
|------|---------|
| **[INSTRUCTIONS.md](INSTRUCTIONS.md)** | Commands, CLI flags, dependencies. |
