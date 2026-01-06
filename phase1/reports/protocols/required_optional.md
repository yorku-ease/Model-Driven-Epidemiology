# Required vs Optional Model Elements

**Version:** 1.0

Definitions of what is always required vs conditional in compartmental models

**Key Principle:** Only flag as missing if paper promises it but model does not have it

## Always Required Elements

Elements that every model MUST have

### Compartments

At least 2 compartments are required

**Minimum:** 2

**Examples:**

- S and I (minimal SIR)
- S, E, I, R (SEIR)

**Validation:** Count compartments - must be >= 2

### Flows

At least 1 flow is required to connect compartments

**Minimum:** 1

**Examples:**

- S → I (transmission)
- I → R (recovery)

**Validation:** Count flows - must be >= 1

### Flow Types

Each flow must have a defined type

**Validation:** Every flow must have a type

### Rate Values or Parameters

Each flow must have a rate value or parameter reference

**Examples:**

- rate="0.1"
- rateParameter="//@parameters.0"

**Validation:** Every flow must have rate or rateParameter

### Total Population OR Initial Conditions

Model must specify total population or initial conditions for each compartment

**Examples:**

- totalPopulation="10000"
- population="9900" for each compartment

**Validation:** Check for totalPopulation attribute or population values in compartments

## Conditional/Optional Elements

Elements that are only required if paper mentions them or context requires them

### Stratification

**Required If:**

- Paper says "stratified"
- Paper mentions "age groups"
- Paper mentions "risk groups"
- Parameters have subscripts (β_child, β_adult)
- Separate equations for different groups

**Not Required If:**

- Paper says "simple SEIR model"
- No mention of stratification
- Single set of equations for entire population

**Examples:**

- Age groups: 0-17, 18-64, 65+
- Risk groups: High, Medium, Low
- Gender: Male, Female

**Detection Rules:**

- Search for keywords: "stratified", "age group", "age-specific", "risk group", "gender"
- Check if parameters have subscripts
- Look for multiple parallel compartments with same name

### Groups/Products

**Required If:**

- S
- t
- r
- a
- t
- i
- f
- i
- c
- a
- t
- i
- o
- n
-  
- i
- s
-  
- p
- r
- e
- s
- e
- n
- t

**Not Required If:**

- N
- o
-  
- s
- t
- r
- a
- t
- i
- f
- i
- c
- a
- t
- i
- o
- n
-  
- m
- e
- n
- t
- i
- o
- n
- e
- d

### Births/Deaths (Demography)

**Required If:**

- Simulation duration > 1 year
- Paper mentions "long-term"
- Paper mentions "demography" or "demographic"
- Paper models population growth

**Not Required If:**

- Short-term simulation (< 1 year)
- Paper says "short-term" or "outbreak"
- No mention of births/deaths

**Examples:**

- ExternalSource for births
- ExternalSink for natural deaths

**Detection Rules:**

- Extract simulation duration from paper
- Search for keywords: "birth", "death", "demography", "recruitment"
- Check if model includes population growth

### Vector Compartments

**Required If:**

- Disease is vector-borne (malaria, dengue, zika, yellow fever)
- Paper mentions "mosquito" or "vector"
- Model includes vector transmission

**Not Required If:**

- Disease is not vector-borne
- Direct transmission only

**Examples:**

- Susceptible_Mosquito, Exposed_Mosquito, Infectious_Mosquito

**Detection Rules:**

- Identify disease type
- Check if vector-borne disease list includes disease
- Search for "mosquito", "vector", "Anopheles", "Aedes" keywords

### Temperature Parameters

**Required If:**

- Vector-borne disease
- Paper mentions "temperature-dependent"
- Parameters include temperature (T)

**Not Required If:**

- Not vector-borne
- No temperature dependence mentioned

**Examples:**

- Parameter T (VARIABLE type)
- Expression parameters with T: a(T), μᵥ(T)

**Detection Rules:**

- Check if vector compartments present
- Search for "temperature" keyword
- Look for parameters with T in expression

### Interventions

**Required If:**

- Paper is about vaccination
- Paper is about treatment
- Paper mentions "intervention"
- Paper studies control measures

**Not Required If:**

- Paper is about natural disease dynamics only
- No intervention mentioned

**Examples:**

- Vaccinated compartment
- Treated compartment
- Quarantine compartment

**Detection Rules:**

- Search for keywords: "vaccination", "vaccine", "treatment", "intervention"
- Check if paper title/abstract mentions interventions
- Look for intervention compartments

### Secondary Names

**Required If:**

- Paper describes substages
- Paper mentions "presymptomatic", "asymptomatic"
- Paper has multiple stages with same primary name

**Not Required If:**

- Simple compartment structure
- No substages mentioned

**Examples:**

- Infectious (presymptomatic)
- Infectious (isolated)
- Exposed (quarantined)

**Detection Rules:**

- Look for parenthetical descriptions in compartment names
- Search for substage keywords
- Check if multiple compartments share primary name

## Detection Rules for AI

### IF paper says "simple SEIR model" → DON'T expect stratification

**Action:** Do not flag absence of age groups as gap

**Example:** Simple SEIR with no stratification → ✓ Valid, complete model!

### IF paper says "age-stratified" → REQUIRE age groups

**Action:** Flag absence of age groups as gap

**Example:** Paper mentions "age-stratified" but no groups → ✗ Gap

### IF malaria/dengue → REQUIRE mosquito compartments

**Action:** Flag absence of vector compartments as gap

**Example:** Malaria without mosquitoes → ✗ Gap (vector-borne needs vectors!)

### IF COVID short-term → births/deaths OPTIONAL

**Action:** Do not flag absence of births/deaths for short-term models

**Example:** COVID short-term without births → ✓ Valid (not needed for short-term)

### IF simulation > 1 year → births/deaths REQUIRED

**Action:** Flag absence of demography as gap for long-term models

**Example:** 10-year simulation without births → ✗ Gap

### IF intervention study → intervention compartments REQUIRED

**Action:** Flag absence of intervention compartments if paper is about interventions

**Example:** Vaccination study without vaccinated compartment → ✗ Gap

## Validation Checklist

### Always Check:

- [ ] At least 2 compartments exist
- [ ] At least 1 flow exists
- [ ] All flows have types
- [ ] All flows have rates or rateParameters
- [ ] Total population or initial conditions specified

### Conditional Check:

- [ ] If stratification mentioned → groups and products exist
- [ ] If vector-borne disease → vector compartments exist
- [ ] If long-term simulation → births/deaths exist
- [ ] If intervention study → intervention compartments exist
- [ ] If temperature-dependent → temperature parameters exist
