# Compartmental Model Taxonomies

Structured taxonomies for compartmental epidemiological models.

## Compartment Types

Taxonomy of compartment types found in epidemiological models

### Disease States

Core disease progression compartments

**Examples:**

- **Susceptible**: Individuals not yet exposed to disease
- **Exposed**: Individuals exposed but not yet infectious
- **Infectious**: Individuals capable of transmitting disease
- **Recovered**: Individuals who have recovered from disease
- **Vaccinated**: Individuals who have been vaccinated

**Required:** True

### Healthcare

Compartments related to healthcare system

**Examples:**

- **Hospital**: Individuals hospitalized
- **ICU**: Individuals in intensive care
- **Quarantine**: Individuals in quarantine
- **Isolated**: Individuals in isolation

**Required:** False
**Conditional:** Only if paper models healthcare system or interventions

### Outcomes

Final state compartments

**Examples:**

- **Death**: Disease-induced deaths
- **Recovered**: Recovered individuals
- **HIV Deaths**: HIV/AIDS-related deaths

**Required:** False
**Conditional:** Only if paper tracks deaths or specific outcomes

### Vectors

Vector compartments for vector-borne diseases

**Examples:**

- **Susceptible_Mosquito**: Susceptible mosquito population
- **Exposed_Mosquito**: Exposed mosquito population
- **Infectious_Mosquito**: Infectious mosquito population

**Required:** False
**Conditional:** ONLY if vector-borne disease (malaria, dengue, zika, yellow fever)

### Stages

Disease stage compartments

**Examples:**

- **Presymptomatic**: Infectious but not yet showing symptoms
- **Mild**: Mild disease symptoms
- **Severe**: Severe disease symptoms
- **Asymptomatic**: Infected but asymptomatic

**Required:** False
**Conditional:** ONLY if paper describes disease stages or substages

### Treatment

Treatment-related compartments

**Examples:**

- **Treated**: Individuals receiving treatment
- **Untreated**: Untreated infected individuals
- **On ART**: Individuals on antiretroviral therapy (HIV)

**Required:** False
**Conditional:** ONLY if paper is about treatment or interventions

## Flow Types

Taxonomy of flow types in compartmental models

### ContactFlow

Transmission flows that depend on contact between compartments

**Examples:**

- S → E: Susceptible to Exposed via contact with Infectious
- SH → EH: Susceptible Human to Exposed Human via contact with Infectious Mosquito

**Pattern:** β*S*I/N or contactRate*Source*ContactCompartment/TotalPopulation

**Characteristics:**

- Involves two compartments (source and contact)
- Rate depends on product of two populations
- Represents transmission/infection
- Usually normalized by total population

**Required:** False
**Conditional:** Required if model includes transmission

### RateFlow

Fixed rate transitions between compartments

**Examples:**

- E → I: Exposed to Infectious (incubation/progression)
- I → R: Infectious to Recovered (recovery)
- I → H: Infectious to Hospital (hospitalization)

**Pattern:** rate*Compartment

**Characteristics:**

- Single compartment with fixed rate
- Represents progression, recovery, or transition
- Rate is constant (not dependent on other compartments)
- Can have stratum-specific rates

**Required:** True

### ExternalSource

External inflows (births, recruitment, inputs)

**Examples:**

- Births: ExternalSource to Susceptible
- Recruitment: ExternalSource to specific compartment
- Onramp: ExternalSource to traffic network node

**Pattern:** + rate*TotalPopulation or + constant

**Characteristics:**

- Positive term not from another compartment
- Represents births, recruitment, or external input
- May target specific stratum
- OPTIONAL for short-term models

**Required:** False
**Conditional:** ONLY if long-term (>1 year) simulation or paper mentions demography

### ExternalSink

External outflows (deaths, outputs)

**Examples:**

- Natural Death: ExternalSink from all compartments
- Disease Death: ExternalSink from Infectious/Hospital
- Offramp: ExternalSink from traffic network node

**Pattern:** - rate*Compartment

**Characteristics:**

- Negative term not going to another compartment
- Represents deaths or external output
- May be from specific stratum
- OPTIONAL for short-term models

**Required:** False
**Conditional:** ONLY if long-term (>1 year) simulation or paper mentions demography

## Parameter Types

Taxonomy of parameter types in compartmental models

### CONSTANT

Fixed values with names

**Examples:**

- **β**: Transmission rate
- **γ**: Recovery rate
- **μ**: Natural death rate

**Required:** True

### VARIABLE

Runtime inputs (temperature, intervention level, etc.)

**Examples:**

- **T**: Temperature
- **intervention_level**: Level of intervention

**Required:** False
**Conditional:** ONLY if model has environmental dependencies or time-varying inputs

### EXPRESSION

Computed from other parameters/compartments

**Examples:**

- **β₁a(T)**: Temperature-dependent contact rate
- **β2**: Force of infection

**Required:** False
**Conditional:** ONLY if model has derived parameters or complex relationships

## Stratification Types

Taxonomy of population stratification types

**Note:** Stratification is CONDITIONAL - only if paper mentions it

### Age

Age-based stratification

**Examples:**

- Values: 0-17, 18-64, 65+ - COVID-19 age groups
- Values: <5, 5-14, 15-64, 65+ - Malaria age groups
- Values: Children, Adults, Elderly - General age categories

**Required:** False
**Conditional:** ONLY if paper says "age-stratified" or mentions age groups

### Gender

Gender-based stratification

**Examples:**

- Values: Male, Female - Binary gender classification

**Required:** False
**Conditional:** ONLY if paper mentions gender-specific patterns

### Risk

Risk-based stratification

**Examples:**

- Values: High, Medium, Low - Risk level categories
- Values: Homosexual Men, Women, Heterosexual Men - HIV risk groups

**Required:** False
**Conditional:** ONLY if paper mentions risk groups

### Location

Geographic or spatial stratification

**Examples:**

- Values: Urban, Rural - Urban-rural classification
- Values: Region1, Region2, Region3 - Geographic regions

**Required:** False
**Conditional:** ONLY if paper mentions geographic stratification

### Vaccination Status

Vaccination-based stratification

**Examples:**

- Values: Unvaccinated, Vaccinated - Vaccination status
- Values: Unvaccinated, Partially Vaccinated, Fully Vaccinated - Vaccination levels

**Required:** False
**Conditional:** ONLY if paper is about vaccination

