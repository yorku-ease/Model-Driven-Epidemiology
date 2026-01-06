# Pattern Library: Common Compartmental Model Structures

Common patterns found in epidemiological compartmental models.

## Standard SEIR

Basic SEIR model with four compartments

### Structure

**Compartments:**

- S (Susceptible)
- E (Exposed)
- I (Infectious)
- R (Recovered)

**Flows:**

- S → E (ContactFlow, β*S*I/N)
- E → I (RateFlow, σ*E)
- I → R (RateFlow, γ*I)

**Examples:** COVID-19, Malaria

## SEIR with Hospitalization

SEIR model extended with healthcare system

### Structure

**Compartments:**

- S
- E
- I
- H (Hospital)
- R
- D (Death)

**Flows:**

- S → E (ContactFlow)
- E → I (RateFlow)
- I → H (RateFlow, hospitalization rate)
- H → R (RateFlow, recovery)
- H → D (RateFlow, mortality)

**Examples:** COVID-19

## Vector-Borne (Malaria)

Dual population model with human and vector compartments

### Structure

**Compartments:**

- SH (Susceptible Human)
- EH (Exposed Human)
- IH (Infectious Human)
- RH (Recovered Human)
- SM (Susceptible Mosquito)
- EM (Exposed Mosquito)
- IM (Infectious Mosquito)

**Flows:**

- SH → EH (ContactFlow with IM)
- EH → IH (RateFlow)
- IH → RH (RateFlow)
- SM → EM (ContactFlow with IH)
- EM → IM (RateFlow)

**Examples:** Malaria

## With Treatment

Model includes treatment compartment

### Structure

**Compartments:**

- S
- I (Untreated)
- T (Treated)
- R

**Flows:**

- S → I (ContactFlow)
- I → T (RateFlow, treatment rate)
- T → R (RateFlow, recovery)

## Stratified Model

Model with population stratification

### Structure

**Examples:** COVID-19, HIV

## Model Analyses

### COVID-19

**Detected Patterns:**

- Standard SEIR (high confidence)
- SEIR with Hospitalization (high confidence)
- Stratified Model (high confidence)

### Malaria

**Detected Patterns:**

- Standard SEIR (high confidence)
- Vector-Borne (Malaria) (high confidence)

### HIV

**Detected Patterns:**

- With Treatment Compartment (medium confidence)
- Stratified Model (high confidence)

