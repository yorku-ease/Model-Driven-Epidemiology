# Gap Analysis Report: Malaria

**Analysis Date:** 1767670997.1366704

**Total Gaps Found:** 7

## Summary

- **Critical Gaps (High Severity):** 0
- **Medium Gaps:** 2
- **Low Gaps:** 3

## Structural Gaps

### Missing Asymptomatic Carriers (AH)

**Description:** Asymptomatic individuals who contribute to transmission

**Why It Matters:** Asymptomatic carriers can significantly contribute to malaria transmission

**Severity:** MEDIUM

**Found In:**
- Akowe et al. (2025) - mentions asymptomatic carriers
- Multiple malaria modeling papers include asymptomatic compartments

**How to Add:** Add compartment: IH → AH with proportion p_asymp

**Suggested Action:** Review literature for asymptomatic proportion values (typically 0.2-0.5)

### Missing Severe Disease Compartment

**Description:** Compartment for individuals with severe malaria

**Why It Matters:** Severe malaria has different mortality and treatment rates

**Severity:** LOW

**Found In:**
- WHO malaria modeling guidelines
- Age-stratified models often include severe cases

**How to Add:** Add compartment: IH → Severe with age-specific rates

**Suggested Action:** Only add if modeling severe outcomes explicitly

### Missing Drug Resistance Compartment

**Description:** Compartment for drug-resistant infections

**Why It Matters:** Drug resistance is a major concern in malaria

**Severity:** LOW

**Found In:**
- Models studying treatment efficacy
- Models of drug resistance evolution

**How to Add:** Add compartment for resistant strains if modeling resistance

**Suggested Action:** Only add if paper focuses on drug resistance

## Parameter Gaps

### Missing Asymptomatic Proportion (p_asymp)

**Description:** Parameter for proportion of infections that are asymptomatic

**Current Value:** Not in model

**Found in Literature:** 0.2-0.5 (varies by region and study)

**Uncertainty:** Medium (range varies across studies)

**Severity:** MEDIUM

**Suggested Value:** 0.3 (mid-range estimate)

**Suggested Range:** [0.2, 0.5]

**Sources:**
- Akowe et al. (2025) - may have region-specific values
- WHO malaria epidemiology reports

### Missing Bed Net Coverage Parameter

**Description:** Parameter for insecticide-treated bed net coverage

**Current Value:** Not in model

**Found in Literature:** Varies by region (0.0-0.8)

**Uncertainty:** High (varies significantly by location)

**Severity:** LOW

**Suggested Value:** 0.5 (example value)

**Suggested Range:** [0.0, 0.8]

## Stratification Gaps

### Missing Age Stratification

**Description:** No age groups defined in model

**Why It Matters:** Children <5 years have higher malaria risk and different severity

**Severity:** HIGH

**Note:** Only required if paper mentions age groups

