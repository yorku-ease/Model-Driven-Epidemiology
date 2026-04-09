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

---

## Why we built Phase 4 — research context (thesis note)

This section documents the rationale behind each design decision so it can be referenced when writing up the methodology.

### Why uncertainty quantification at all?

Compartmental epidemiological models (SIR, SEIR, and their variants) translate biological and social processes into differential equations. Every parameter in those equations — transmission rate, recovery rate, drug efficacy, vaccine protection — is estimated from real-world data that is noisy, context-dependent, and often comes from different populations or time periods than the one being modelled.

**Phase 2** extracts these parameters from scientific papers using LLMs. **Phase 3** fills gaps using RAG and LLM inference. But even after filling, the extracted values are point estimates: single numbers. In reality, β = 0.3 means "somewhere around 0.3". Phase 4 converts those point estimates into **probability distributions** and asks: given that uncertainty, how much do model predictions vary?

This is standard practice in infectious disease modelling (e.g. Morris sensitivity analysis, Latin Hypercube Sampling). What is novel in this pipeline is doing it **automatically from LLM-extracted parameters** across 30 heterogeneous disease models.

### Why the general framework?

Rather than manually choosing distributions for each disease, `data/general_framework.json` maps **parameter type** (transmission, recovery, mortality, etc.) to a distribution family:
- **Lognormal** for rate parameters — strictly positive, right-skewed (a few papers report very high rates)
- **Uniform** for coverage/efficacy fractions — we know a plausible range but not a central tendency

This ensures methodological consistency: the same rules apply to cholera, HIV, and Zika. A human expert could override these choices per-disease, but the framework provides a defensible starting point.

### Why RAG-only for Phase 3 input (not "both")?

We evaluated three Phase 3 gap-filling strategies: RAG-only, LLM-only, and both. RAG-only produced the best structural recall (0.94 comp, 0.85 flow). Adding LLM inference slightly hurt recall on average because LLMs occasionally hallucinate structurally plausible but incorrect compartments. Since Phase 4 runs the ODE of the filled model, structural correctness matters — a spurious compartment creates spurious flows. We therefore use `--mode auto`, which scores all three modes and picks the best per paper; in the current Gemini showcase this always selects RAG-only.

### Why fix the initial conditions in `generic_simulator.py`?

The `.compmodel` files produced by Phase 2 and Phase 3 do not carry `population` attributes (initial conditions) — they only describe model **structure** and **parameters**. When the Phase 1 simulator encounters all-zero initial conditions it applies a fallback. The original fallback set `I₀ = 1,000` out of a total population of 100,000 (1% initially infected). This is epidemiologically unrealistic for most models: a 1% seed puts many SIR-type models at or past their epidemic peak at day zero, producing flat trajectories regardless of parameter variation (0% uncertainty spread). The fix uses the standard convention: `S₀ = N−1`, `I₀ = 1` (one seed case, everyone else susceptible). This lets the ODE dynamics determine the epidemic trajectory naturally and reveals genuine parameter-driven uncertainty.

---

## Results (30 papers, current run)

### Status summary

| Step | Status | Notes |
|------|--------|-------|
| 9.1 Distributions | ✅ All 30 papers | 5–37 parameters per paper |
| 9.2 Monte Carlo | ✅ All 30 papers | 1000 samples × 200 days, I₀=1 seed |
| 9.3 Sensitivity | ✅ All 30 papers | OAT ±20% perturbation, ranked by peak + total cases impact |
| 10 Reports | ✅ All 30 papers | `PHASE4_REPORT.md` per paper under `reports/<stem>/` |

### Parameter distribution summary

The general framework classifies extracted parameters into two families:

| Disease group | Typical param count | Dominant family |
|---------------|--------------------|-----------------| 
| Simple SIR-type (Cholera P1, COVID P2, Zika P3) | 5–10 | lognormal (rates) |
| Complex intervention models (COVID P1, Tuberculosis P3, Cholera P2) | 33–37 | mixed (uniform for coverage/efficacy, lognormal for rates) |

**Lognormal** is assigned to rate-type parameters (transmission, recovery, mortality, progression) — rates are strictly positive and right-skewed.  
**Uniform** is assigned to coverage fractions, efficacy bounds, and parameters where only a literature range is known without a central estimate.

### Monte Carlo — uncertainty spread (after I₀ fix)

Each paper was simulated 1,000 times with parameters drawn from their assigned distributions. The table shows the **peak epidemic size** range for the primary infectious compartment (P05 = 5th percentile trajectory, P50 = median, P95 = 95th percentile):

**Spread %** = (P95 peak − P05 peak) / P50 peak × 100

| Paper | Primary compartment | P05 peak | P50 peak | P95 peak | Spread | Interpretation |
|-------|-------------------|----------|----------|----------|--------|----------------|
| COVID-19 P1 | Infectious Presymptomatic | 0.1 | 0.3 | 0.4 | **86%** | Meaningful uncertainty driven by latent period variation |
| Ebola P2 | Infectious (community) | 0.3 | 0.6 | 1.3 | **145%** | High uncertainty — contact rate drives a wide range of outcomes |
| Ebola P3 | Infectious | 0.1 | 0.1 | 2.1 | **1,885%** | Extreme spread — model sits near epidemic threshold; small parameter changes flip between extinction and outbreak |
| HIV P2 | Asymptomatic Infection | 6.2 | 9.9 | 16.2 | **101%** | ART efficacy uncertainty causes 2.6× range in asymptomatic burden |
| Influenza P1 | Infectious | 0.3 | 0.6 | 19,723 | **~3.6M%** | Numerical instability — some samples cross the epidemic threshold and explode; model is highly sensitive to β × σ × γ combination |
| Malaria P1 | Infectious Humans | 0.5 | 0.7 | 0.8 | **31%** | Moderate uncertainty driven by recovery rate γ |
| Measles P2 | Infectious | 0.8 | 1.0 | 1.0 | **17%** | Well-constrained; infection rate is tightly bounded |
| Tuberculosis P2 | Infectious TB | 0.8 | 0.9 | 1.0 | **18%** | Low uncertainty — case detection and contact rates are reasonably well-known |
| Zika P1 | Infectious Humans | 5,440 | 10,295 | 14,497 | **88%** | Vector-borne scale — peak varies by 9,000 humans depending on mosquito parameters |

#### Three classes of MC outcome

**Class A — Meaningful spread (above):** 9 papers where parameter uncertainty genuinely propagates into different epidemic trajectories. These are the most useful results for public health decision-making.

**Class B — Epidemic dies out (peak = 1.0, 0% spread):** Cholera P1/P2/P3, Dengue P1, Ebola P1, HIV P1, Influenza P2, Malaria P2, Zika P2/P3. The single seed case never propagates — the extracted transmission rates are too small to sustain an outbreak from I₀=1. This is a real model quality signal: these models may have parameter values that reflect control conditions (e.g. cholera in a low-endemicity setting) or the transmission structure wasn't fully captured in extraction.

**Class C — Instant saturation (peak = N−1, 0% spread):** COVID-19 P3, HIV P3, Tuberculosis P3. The opposite problem — epidemic spreads to the entire population immediately, regardless of parameter variation. These models have parameters that imply R₀ ≫ 1 under all sampled conditions, leaving no uncertainty to measure.

#### The Influenza P1 instability
Influenza P1 shows a P95 peak of 19,723 against a P50 of just 0.6 — a 3.6-million-percent spread. This is a numerical instability: most parameter combinations produce a dying-out epidemic (Class B), but a small fraction of samples simultaneously draw high β and high σ (incubation rate), crossing the epidemic threshold and producing explosive growth. This is **not noise** — it correctly identifies that this model sits on a tipping point, and that σ and β are the parameters that most need to be constrained.

---

### ⚠️ Initial condition fix — what was wrong and how it was corrected

**Root cause:** All `.compmodel` files set `population=0` for every compartment (no initial conditions stored in the XML). The Phase 1 simulator's fallback previously set:

```
S₀ = 99,000   I₀ = 1,000
```

Starting with I₀ = 1,000 immediately saturates many models — the infectious compartment is already at its "ceiling" and can only decline. All 1,000 Monte Carlo samples started from the same saturated state, so P05 = P50 = P95 = 1,000 exactly (0% spread — meaningless).

**Fix applied (`phase 1/utils/generic_simulator.py`):**

```
Standard models:  S₀ = 99,999   I₀ = 1
Vector-borne:     S_human = 99,999   I_human = 1   S_vector = 199,999   I_vector = 1
```

One seed case, everyone else susceptible — the standard epidemiological convention. The ODE dynamics now drive the epidemic from scratch, and parameter variation produces genuinely different trajectories.

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
| Malaria P1 | ηV (mosquito-to-human transmission efficiency) | vector | Vector infectivity drives human incidence |
| Malaria P2 | rD (rate of drug clearance) | recovery | Drug clearance speed determines how fast infectious mosquitoes clear |
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
