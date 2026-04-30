# Phase 2.5 — What it does

Phase 2.5 sits between the LLM-based model extraction pipeline (**Phase 2**) and downstream work (retrieval, analysis, thesis reporting). Its job is to check whether the compartmental epidemic models that Phase 2 extracted automatically from papers structurally match the hand-curated "gold" models we have for each benchmark disease.

Operational commands live in **[INSTRUCTIONS.md](INSTRUCTIONS.md)**.

---

## The core idea

Any compartmental epidemic model — SIR, SEIR, or more complex variants — can be described as a checklist of yes/no questions:

- Does it have a latent/exposed class? A vaccination compartment? Hospitalization? Birth and death flows? Mosquito or animal hosts? What transmission routes does it use (waterborne, airborne, sexual, bloodborne, healthcare contact)?

Phase 2.5 turns that checklist into a structure called **`EpiFeatureVector`** — 17 true/false flags, one per epidemiological trait:

| Theme | Flags |
|--------|-------|
| **Transmission routes** | Vector/arthropod, sexual/partner, airborne/respiratory, fecal–oral, bloodborne/vertical, healthcare contact |
| **Natural history** | Latent/exposed, staged/chronic progression, hospitalized/severity, treatment/ART, vaccination, recovered/immune endpoint |
| **Population & space** | Demographic stratification, spatial/patch structure |
| **Non-human hosts** | Vector or intermediate species, zoonotic/animal compartments |
| **Demography** | Explicit birth/recruitment/immigration |

To fill in those flags, Phase 2.5 reads the text inside the `.compmodel` XML file — compartment names, flow descriptions, sink labels — and runs keyword patterns over it. If the text mentions "mosquito" or "anopheles", the vector-route flag turns on. If it mentions "latent" or "LTBI", the latent-class flag turns on. And so on for all 17 flags.

**Gold benchmarks** are hand-curated `.compmodel` files stored under `phase 2/data/diseases/<disease>/`, often several per disease. The **canonical profile** for a disease is the logical OR across all its gold files: if any one gold model has a flag set to true, that flag is considered expected for that disease. This merged profile is saved to `reports/disease_feature_models/<slug>.json` and is what Phase 2 drafts are compared against.

---

## The 17 features — what they are and where they come from

### Step 1 — Reading the `.compmodel` file (`src/compmodel_parse.py`)

The function `harvest_compmodel_text` reads the XML and pulls text from four places:

| XML element | What is read | What it contributes |
|-------------|--------------|---------------------|
| `<compartments>` | `PrimaryName`, `SecondaryName` | Compartment labels (e.g. `S`, `E`, `Ih`, `Vaccinated`) and their descriptions |
| `<outgoingFlows>` / `<incomingFlows>` | `description`, `xsi:type` | Flow labels and types (infection rate, recovery, …) |
| `<externalSinks>` | `name` | Death and removal sinks |

All of that text is joined into one big string (called the **blob**) and lowercased. Compartment primary names are also kept as a separate list for the vector-label check described below.

### Step 2 — Checking keywords (`src/inference_rules.py`)

Each of the 17 flags is turned on if a keyword pattern matches anywhere in the blob. The tables below show the main trigger words for each flag; the full patterns are in `_RULES` inside `inference_rules.py`.

**Transmission routes**

| Flag | Trigger words |
|------|---------------|
| `route_vector_arthropod` | mosquito, vector, anopheles, aedes, arthropod, larva, sporozoite, oocyst, bite, snail |
| `route_sexual_or_partner_network` | homosexual, heterosexual, sexual, partner, condom, msm, commercial sex, men who have |
| `route_airborne_or_respiratory_droplet` | droplet, airborne, respiratory, aerosol, cough, covid, influenza, measles |
| `route_fecal_oral_or_waterborne` | cholera, waterborne, fecal, ingestion, aquatic, sanitation, environmental reservoir |
| `route_bloodborne_vertical_or_parenteral` | bloodborne, needle, vertical transmission, mother-to-child, perinatal, congenital transmission, transfusion, hiv transmission |
| `route_healthcare_general_contact` | hospital, icu, nosocomial, health-care, isolation ward |

**Natural history / clinical**

| Flag | Trigger words |
|------|---------------|
| `latent_or_exposed_class` | latent, LTBI, exposed, incubate, Eh1/Eh2-style labels, exposed class |
| `multiple_infectious_stages_or_chronic` | AIDS, HIV stage, infectious stage, chronic stage, chronic phase, tuberculosis latent, undiagnosed, disease stage, clinical stage, progressive |
| `hospitalized_or_severity_stratification` | hospitalized, hospital, icu, severe, severity, clinical progression |
| `treatment_or_art_intervention` | ART, therapy, antiretroviral, antibiotic, chemotherapy, trimethoprim, isoniazid, rifamp, treatment, treated |
| `vaccination_route_compartment` | vaccinat\*, immunization, VH, preventive dose |
| `recovered_or_immune_endpoint` | recovered, recovery, immune, waned immunity, lifelong |

**Population structure**

| Flag | Trigger words |
|------|---------------|
| `stratification_demographic_roles` | adults, children, homosexual, heterosexual men/women, risk group, stratif, age group, age-structured, pregnant women |
| `spatial_or_patch_like_naming` | patch, region, district, spatial, metapop |

**Non-human hosts**

| Flag | Trigger words |
|------|---------------|
| `vector_or_intermediate_species_present` | mosquito, snail, aquatic stage, vector population, vector compartment — plus a compartment-name check (see below) |
| `zoonotic_or_animal_compartment` | animal, zoonotic, wildlife, primate, cattle |

For `vector_or_intermediate_species_present` there is a second check after the blob regex: `PrimaryName` values are matched exactly (case-sensitive) against a curated set of mosquito/snail/vector shorthand used in vector ODE literature (`Sm`, `Em`, `Im`, `Am`, `Rm`, `Lm`, `Pm`, snail stages `Ss`/`Es`/`Is`, and `Sv`/`Ev`/`Iv`). This is done on the primary name list only so that human-host abbreviations with the same letters (e.g. `Im` = immune, `Sm` = susceptible males) are not confused with vector-population compartments.

**Demography**

| Flag | Trigger words |
|------|---------------|
| `recruitment_birth_or_immigration_named` | recruitment, birth, immigration, carrying capacity, incoming flow |

---

### Step 3 — Disease-name safety net (`src/inference_rules.py`)

Some gold model files are sparse — a malaria model might not mention "mosquito" anywhere in its markup. To keep benchmark expectations stable, a second pass reads the **disease folder slug** and force-sets a small curated set of flags that encode consensus traits for that disease, independent of the XML text.

For example, folders named `malaria`, `dengue`, `zika`, or `yellowfever` always get `route_vector_arthropod` and `vector_or_intermediate_species_present` set to true even if absent from the text. Full table:

| Disease folder name | Flags always set true |
|---------------------|-----------------------|
| malaria, dengue, zika, yellowfever | `route_vector_arthropod`, `vector_or_intermediate_species_present` |
| hiv | `route_sexual_or_partner_network`, `route_bloodborne_vertical_or_parenteral`, `multiple_infectious_stages_or_chronic`, `treatment_or_art_intervention` |
| covid, influenza, measles, tuberculosis, tb | `route_airborne_or_respiratory_droplet` |
| cholera | `route_fecal_oral_or_waterborne` |
| ebola | `route_healthcare_general_contact`, `hospitalized_or_severity_stratification` |
| tuberculosis, tb | `latent_or_exposed_class` |

Every flag set this way is tagged `[prior]` in `matched_signals` and listed separately under `prior_signals` in the per-model profile entries, keeping regex evidence and prior knowledge fully transparent.

---

## The pipeline (step by step)

1. **Build the gold canonical profiles** — For each benchmark disease, read all gold `.compmodel` files in its folder, run the keyword rules, apply the disease-name safety net, then merge everything with a logical OR. The result is one canonical feature vector per disease, saved to `canonical_profiles.json`, `canonical_by_disease/<slug>.json`, and `disease_feature_models/<slug>.json`. Each per-model record separately lists which signals came from keyword matches (`matched_signals`) and which came from the disease-name safety net (`prior_signals`).

2. **Draw feature diagrams (`src/diagram_export.py`)** — Two tiers of Graphviz table-style diagrams are produced. The **shared vocabulary diagram** (`reports/fm_diagram/feature_tree.{dot,png,svg}`) shows all 17 flags grouped equally — it is a reference legend, not tied to any disease. For each disease, a **per-disease profile diagram** (`reports/fm_diagram/per_disease/<slug>/feature_profile.{dot,png,svg}`) highlights only the true flags for that disease's canonical profile; false flags are greyed out.

3. **Check logical consistency (SPL)** — A set of cross-tree rules in `cross_tree_constraints.json` describes logical dependencies between flags. The three active rules are: vector route implies vector species present; vaccination implies recovered/immune endpoint; zoonotic animal compartment implies non-human host present. Each disease's canonical vector is checked against these rules and the result is saved to `spl_configurator/<slug>_spl_report.json`.

4. **Compare Phase 2 drafts against the canonical** — For each `model_draft.compmodel` produced by Phase 2, the same keyword rules are run without the disease-name safety net, so the score reflects what the LLM actually wrote. The result is compared flag by flag against the canonical and saved to `draft_validations/<disease>_<phase2_run>_phase25_validation.json`. Each report contains precision, recall, F1, and text-grounded recall and F1 (explained in the results section below), along with the full lists of missing and extra flags and which canonical flags came from priors.

Finally, `phase25_run_summary.json` records the full pipeline run and optionally bundles all validation results into one file.

---

## Scoring — standard vs text-grounded metrics

Each validation report contains two sets of alignment scores.

**Standard recall, precision, and F1** compare the draft directly against the full canonical profile, including flags that were set by the disease-name safety net rather than by actual text in a gold model. These scores answer: how well does the draft cover everything we expect from this disease?

**Text-grounded recall and F1** use the same formula but exclude prior-only canonical flags from the recall denominator. Only flags that were confirmed by actual regex matches on gold `.compmodel` text count as expected. These scores answer: how well does the draft cover what the gold models explicitly described in their text?

The difference matters in practice. Because text-grounded recall removes prior-only flags from the denominator but keeps the same numerator (total hits), it is always **greater than or equal to** standard recall. The gap between the two tells you how much of the canonical profile is prior-driven — a large gap means the gold models were sparse and many expected flags came from the disease name alone rather than actual text.

A concrete example: a sparse covid3 gold model might not mention airborne anywhere, so that flag is set entirely by the disease-name prior. A draft that misses it would be penalised under standard recall, even though neither the gold model nor the draft had that information in their text. Text-grounded recall removes it from the denominator so the draft is only judged on what the gold text actually described.

As a rule of thumb: use **text-grounded recall** as the primary measure of LLM extraction quality — it reflects how well the draft covers what the gold text explicitly described. Use **standard recall** when you care about overall canonical coverage including priors.

The report fields are:

| Field | Meaning |
|-------|---------|
| `recall` | Hits / canonical true count (includes prior flags in denominator) |
| `precision` | Hits / draft true count |
| `f1` | Harmonic mean of recall and precision |
| `text_grounded_recall` | Hits / text-evidenced canonical count (prior flags excluded from denominator) |
| `text_grounded_f1` | Harmonic mean of precision and text-grounded recall |
| `canonical_text_grounded_count` | How many canonical flags were set by actual gold text (not priors) |
| `text_grounded_canonical_flags` | Which specific flags count toward text-grounded recall |
| `prior_influenced_canonical_flags` | Which canonical flags were set by the disease-name safety net |

---

## What you get — results (benchmark run snapshot)

The numbers below come from one full `run_phase25.py` run over 10 benchmark diseases × 3 gold papers × 3 LLM backends = **90 Phase 2 draft validations**.

### Output artefacts

| Output | What it contains |
|--------|-----------------|
| **10 canonical profiles + 10 FM bundles** — `canonical_by_disease/` and `disease_feature_models/` | One merged gold feature assignment per disease, with per-model signal and prior breakdown. |
| **Diagrams** — `reports/fm_diagram/feature_tree.*` and `reports/fm_diagram/per_disease/<slug>/feature_profile.*` | Shared vocabulary legend plus ten per-disease gold assignment diagrams. |
| **10 SPL reports** — `spl_configurator/*_spl_report.json` | All 10 canonical vectors pass all three active consistency rules. |
| **90 Phase 2 validations** — `draft_validations/*.json` | Full per-run comparison including both standard and text-grounded scores. |

### Scores by LLM

| LLM | Recall | Precision | F1 | Text-grounded recall | Text-grounded F1 |
|-----|--------|-----------|----|----------------------|-----------------|
| Claude | 0.596 | 0.930 | 0.689 | 0.757 | 0.796 |
| Gemini | 0.544 | 0.962 | 0.657 | 0.711 | 0.781 |
| OpenAI | 0.541 | 0.972 | 0.651 | 0.721 | 0.779 |

All three LLMs have high precision (0.93–0.97) — flags they extract almost always match the gold profile, meaning they rarely hallucinate traits that have no basis in the gold. The recall gap shows they are conservative: they tend to miss flags rather than invent them. Claude leads on both recall and F1. The gap between standard and text-grounded recall (~+0.16–0.18) reflects how much of the canonical profile is prior-driven across the benchmark.

### Scores by paper and LLM

Each row is one specific paper. Canon = number of canonical true flags for that disease; TG = how many of those were set by actual gold text (not priors). The three recall columns show how each LLM did on that specific paper.

| Paper | Canon | TG | Claude | Claude TG | Gemini | Gemini TG | OpenAI | OpenAI TG |
|-------|-------|----|--------|-----------|--------|-----------|--------|-----------|
| cholera1 | 5 | 4 | 0.600 | 0.750 | 0.400 | 0.500 | 0.200 | 0.250 |
| cholera2 | 5 | 4 | 0.600 | 0.750 | 0.600 | 0.750 | 0.400 | 0.500 |
| cholera3 | 5 | 4 | 0.400 | 0.500 | 0.200 | 0.250 | 0.400 | 0.500 |
| covid1 | 5 | 4 | 0.800 | 1.000 | 0.800 | 1.000 | 0.800 | 1.000 |
| covid2 | 5 | 4 | 0.400 | 0.500 | 0.400 | 0.500 | 0.400 | 0.500 |
| covid3 | 5 | 4 | 0.000 | 0.000 | 0.000 | 0.000 | 0.000 | 0.000 |
| dengue1 | 5 | 3 | 0.800 | 1.000 | 0.800 | 1.000 | 0.800 | 1.000 |
| dengue2 | 5 | 3 | 0.600 | 1.000 | 0.600 | 1.000 | 0.600 | 1.000 |
| dengue3 | 5 | 3 | 1.000 | 1.000 | 0.800 | 1.000 | 0.600 | 1.000 |
| ebola1 | 4 | 2 | 0.250 | 0.500 | 0.250 | 0.500 | 0.250 | 0.500 |
| ebola2 | 4 | 2 | 0.750 | 1.000 | 0.750 | 1.000 | 0.750 | 1.000 |
| ebola3 | 4 | 2 | 1.000 | 1.000 | 0.750 | 1.000 | 1.000 | 1.000 |
| hiv1 | 6 | 2 | 0.667 | 1.000 | 0.667 | 1.000 | 0.667 | 1.000 |
| hiv2 | 6 | 2 | 0.667 | 1.000 | 0.167 | 0.500 | 0.500 | 1.000 |
| hiv3 | 6 | 2 | 0.500 | 1.000 | 0.167 | 0.500 | 0.167 | 0.500 |
| influenza1 | 6 | 5 | 0.333 | 0.400 | 0.333 | 0.400 | 0.500 | 0.600 |
| influenza2 | 6 | 5 | 0.333 | 0.400 | 0.500 | 0.600 | 0.167 | 0.200 |
| influenza3 | 6 | 5 | 0.667 | 0.800 | 0.667 | 0.800 | 0.833 | 1.000 |
| malaria1 | 6 | 4 | 1.000 | 1.000 | 1.000 | 1.000 | 1.000 | 1.000 |
| malaria2 | 6 | 4 | 0.833 | 1.000 | 0.833 | 1.000 | 0.667 | 1.000 |
| malaria3 | 6 | 4 | 0.833 | 1.000 | 0.833 | 1.000 | 0.667 | 1.000 |
| measles1 | 5 | 4 | 0.800 | 1.000 | 0.800 | 1.000 | 0.800 | 1.000 |
| measles2 | 5 | 4 | 0.800 | 1.000 | 0.600 | 0.750 | 0.800 | 1.000 |
| measles3 | 5 | 4 | 0.400 | 0.500 | 0.400 | 0.500 | 0.400 | 0.500 |
| tuberculosis1 | 7 | 5 | 0.286 | 0.400 | 0.286 | 0.400 | 0.286 | 0.400 |
| tuberculosis2 | 7 | 5 | 0.429 | 0.600 | 0.429 | 0.600 | 0.286 | 0.400 |
| tuberculosis3 | 7 | 5 | 0.429 | 0.600 | 0.429 | 0.600 | 0.429 | 0.600 |
| zika1 | 7 | 6 | 0.429 | 0.500 | 0.571 | 0.667 | 0.429 | 0.500 |
| zika2 | 7 | 6 | 0.714 | 0.833 | 0.714 | 0.833 | 0.857 | 1.000 |
| zika3 | 7 | 6 | 0.571 | 0.667 | 0.571 | 0.667 | 0.571 | 0.667 |

A few things stand out from the paper-level breakdown. **When all three LLMs score the same on a paper**, the difficulty is coming from the paper itself — its compartment labels and flow descriptions simply do not contain the keywords the rules expect. covid3, ebola1, measles3, and all three tuberculosis papers show this pattern. **When LLMs diverge on the same paper** (e.g. hiv2, hiv3, dengue3), the paper is extractable but some LLMs produced clearer compartment labels or flow descriptions than others. **Dengue is the clearest case** where standard recall understates real performance: all nine dengue drafts reach text-grounded recall of 1.0, meaning every LLM consistently extracted everything the gold text described — the standard recall shortfall is entirely prior-driven flags.

**covid3 scores 0.0 on both metrics across all three LLMs.** This is the one genuine failure case: the gold model for that paper is so sparse that all 4 text-grounded flags were set by prior and nothing was left to measure, yet the LLM drafts also produced 0 matching flags. It warrants manual inspection.

**Tuberculosis is consistently the hardest disease across all papers and LLMs**, with text-grounded recall capped at 0.4–0.6. Its 7-flag canonical profile and 5 text-grounded flags include several that require specific clinical terminology (isoniazid, rifamp, LTBI, spatial/patch structure) that LLM-extracted models often describe in plain language instead.

### What the LLMs tend to miss vs add

The most commonly missed text-grounded flags are vaccination (36/90), demographic stratification (30/90), and latent/exposed class (18/90). These are structurally important but often described in the paper narrative rather than encoded explicitly in compartment names or flow labels that the keyword rules can catch.

LLMs almost never add flags that are not in the gold profile. The extra-flag rate is very low: staged/chronic infection (7/90), birth/recruitment (6/90), and bloodborne/vertical (5/90) are the only spurious flags, all from broad trigger words that occasionally match incidental phrasing.

Perfect text-grounded recall (1.0) was achieved by **40 of 90 drafts**, compared to only 6 under the standard metric. The difference is explained by prior-only canonical flags — once removed from the denominator, the LLMs' actual extraction quality on text-evidenced flags is considerably stronger than standard recall suggests.

**In short:** Phase 2.5 turns the vague question "is this LLM-extracted model correct?" into a concrete checklist comparison across 17 epidemiological traits, grounded in expert-curated gold models. The missing and extra flag lists tell you exactly what is structurally different, not just that a score dropped. The text-grounded metrics tell you how much of the difference is a genuine extraction gap versus an artefact of how the canonical profile was constructed.