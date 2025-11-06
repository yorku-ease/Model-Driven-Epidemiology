# Complete Guide to the Sample Compartmental Model

## Table of Contents
1. [Overview](#overview)
2. [Understanding Groups and Products](#understanding-groups-and-products)
3. [Flow Types Explained](#flow-types-explained)
4. [Stratum-Specific Rates](#stratum-specific-rates)
5. [Complete Model Walkthrough](#complete-model-walkthrough)
6. [Birth and Death Mechanics](#birth-and-death-mechanics)
7. [How the Tools Process This Model](#how-the-tools-process-this-model)
8. [Common Patterns and Best Practices](#common-patterns-and-best-practices)

---

## Overview

The `sample.compartmentalmodel` demonstrates a **complete age and gender-stratified SEIR epidemic model**. It shows:
- How to create **Cartesian products** from multiple groups
- The difference between **ContactFlow** (transmission) and **RateFlow** (progression)
- How to use **multipliers** for relative rate adjustments
- Proper birth and death source configuration for stratified populations

**Model Structure**: Susceptible → Exposed → Infectious → (Recovered OR Death)

**Population**: 10,000 total individuals stratified by:
- **Age**: 3 groups (0-17, 18-65, 66+)
- **Gender**: 2 groups (Male, Female)
- **Total Strata**: 3 × 2 = **6 sub-populations** per compartment

---

## Understanding Groups and Products

### What is a Group?

A **Group** defines a **single dimension of stratification**. It's a category with specific values.

```xml
<groups name="Age" description="Age-based population stratification">
  <values>0-17</values>
  <values>18-65</values>
  <values>66+</values>
</groups>
```

**Think of it as**: A classification system. Age is a dimension with 3 possible categories.

**Another example**:
```xml
<groups name="Gender" description="Gender-based population stratification">
  <values>Male</values>
  <values>Female</values>
</groups>
```

**Key Point**: Groups are just **definitions**. They don't do anything by themselves.

---

### What is a Product?

A **Product** **applies** groups to compartments and creates the **Cartesian product** of all group values.

```xml
<products name="AgeAndGenderStratification"
          description="Combined age and gender stratification"
          groups="//@groups.0 //@groups.1"/>
```

**This references TWO groups**:
- `//@groups.0` → First group (Age)
- `//@groups.1` → Second group (Gender)

**What happens?**
The product creates all possible combinations:
1. 0-17, Male
2. 0-17, Female
3. 18-65, Male
4. 18-65, Female
5. 66+, Male
6. 66+, Female

**Total**: 3 age groups × 2 genders = **6 sub-populations**

---

### Why Separate Groups and Products?

**Flexibility and reusability**. You might want:

**Example 1**: Age-only stratification
```xml
<groups name="Age">...</groups>
<groups name="Gender">...</groups>

<products name="AgeOnly" groups="//@groups.0"/>
<!-- Creates 3 sub-populations: 0-17, 18-65, 66+ -->
```

**Example 2**: Age AND Gender stratification
```xml
<products name="AgeAndGender" groups="//@groups.0 //@groups.1"/>
<!-- Creates 6 sub-populations: all combinations -->
```

**Same groups, different products, different applications!**

---

### How Products Are Applied to Compartments

```xml
<compartments PrimaryName="Susceptible" population="9500" product="//@products.0">
```

**Without product**: 1 Susceptible compartment with 9500 people

**With product** (6 strata): The 9500 people are divided into 6 sub-populations:
- Susceptible (0-17, Male)
- Susceptible (0-17, Female)
- Susceptible (18-65, Male)
- Susceptible (18-65, Female)
- Susceptible (66+, Male)
- Susceptible (66+, Female)

Each sub-population **tracks independently** in the model equations.

---

## Flow Types Explained

### ContactFlow: Disease Transmission

**Purpose**: Models transmission that depends on contact between susceptible and infectious individuals.

**Formula**: `contactRate × Susceptible × Infectious / TotalPopulation`

```xml
<outgoingFlows xsi:type="seir:ContactFlow"
               contactRate="0.000003"
               contactCompartment="//@compartments.2"
               description="Disease transmission through contact with infectious individuals"
               target="//@compartments.1">
```

**Key Attributes**:
- `contactRate`: Base probability of transmission per contact (e.g., 0.000003)
- `contactCompartment`: Which compartment spreads the disease (usually "Infectious")
- `target`: Where people move after exposure (usually "Exposed")

**Why ContactFlow?**
- Transmission **depends on TWO populations**: susceptible AND infectious
- As more people become infectious, transmission accelerates (epidemic dynamics)
- Models diseases spread through contact (COVID, flu, HIV, etc.)

**Real-world meaning**: "A susceptible person has contact with infectious people, and may become exposed based on the contact rate."

---

### RateFlow: Fixed-Rate Transitions

**Purpose**: Models transitions that happen at a fixed rate, independent of other compartments.

**Formula**: `rate × SourceCompartment`

```xml
<outgoingFlows xsi:type="seir:RateFlow"
               rate="0.2"
               description="Progression from exposed to infectious (5 days average)"
               target="//@compartments.2">
```

**Key Attributes**:
- `rate`: Transition rate (often calculated as 1/duration in days)
- `target`: Destination compartment

**Why RateFlow?**
- Transition depends ONLY on time, not other populations
- Models biological processes: incubation, recovery, disease progression
- Rate = 1 / average_duration (e.g., 5-day recovery → rate = 0.2)

**Real-world meaning**: "After being exposed, people progress to infectious state at a fixed rate (e.g., after 5 days on average)."

---

### Key Difference: ContactFlow vs RateFlow

| Aspect | ContactFlow | RateFlow |
|--------|-------------|----------|
| **Depends on** | Two compartments (susceptible × infectious) | One compartment (source only) |
| **Use case** | Disease transmission | Disease progression, recovery |
| **Formula** | `rate × S × I / N` | `rate × S` |
| **Example** | S → E (exposure) | E → I (incubation), I → R (recovery) |

**Simple rule**:
- If it involves **contact/transmission** → ContactFlow
- If it's **time-based progression** → RateFlow

---

## Stratum-Specific Rates

### The Problem

Different populations have different characteristics:
- Children have more contacts than elderly
- Males have higher mortality than females
- Elderly recover slower than young adults

**Solution**: Stratum-specific rates allow different rates for each sub-population.

---

### Two Ways to Specify Rates

#### Option 1: Absolute Rate (Direct Values)

```xml
<stratumSpecificRates stratum="0-17,Male" rate="0.000005"/>
<stratumSpecificRates stratum="18-65,Male" rate="0.000003"/>
<stratumSpecificRates stratum="66+,Male" rate="0.000002"/>
```

**Meaning**: Each stratum gets an explicit, exact rate value.

**When to use**: When you have specific epidemiological data for each group.

---

#### Option 2: Multiplier (Relative Adjustments)

```xml
<outgoingFlows xsi:type="seir:ContactFlow"
               contactRate="0.000003"     <!-- BASE RATE -->
               ...>
  <stratumSpecificRates stratum="0-17,Male" multiplier="1.67"/>
  <!-- Actual rate: 0.000003 × 1.67 = 0.00000501 -->

  <stratumSpecificRates stratum="18-65,Male" multiplier="1.1"/>
  <!-- Actual rate: 0.000003 × 1.1 = 0.0000033 -->

  <stratumSpecificRates stratum="66+,Female" multiplier="0.67"/>
  <!-- Actual rate: 0.000003 × 0.67 = 0.00000201 -->
</outgoingFlows>
```

**Meaning**: Each stratum's rate is a **multiple of the base rate**.

**Advantages**:
1. **Easier to reason about**: "Children have 1.67× more contacts"
2. **Easy to adjust**: Change base rate once, all strata scale proportionally
3. **Clearer relative differences**: See at a glance that elderly have 0.67× (33% fewer) contacts

**When to use**: When you think in terms of relative differences between groups.

---

### Important: In sample.compartmentalmodel

The model uses **multiplier-only** approach (as of the latest version):

```xml
<stratumSpecificRates stratum="0-17,Male" multiplier="1.67"/>
```

**NO rate attribute**, only multiplier. This is cleaner and more maintainable.

---

### Stratum Naming Convention

For **single group** stratification:
```xml
stratum="0-17"
stratum="18-65"
```

For **multiple group** stratification (Cartesian product):
```xml
stratum="0-17,Male"
stratum="18-65,Female"
```

**Format**: `value1,value2,value3,...` (comma-separated, matching the order of groups in the product)

---

## Complete Model Walkthrough

Let's walk through `sample.compartmentalmodel` step by step.

### Step 1: Define Groups

```xml
<groups name="Age" description="Age-based population stratification">
  <values>0-17</values>
  <values>18-65</values>
  <values>66+</values>
</groups>

<groups name="Gender" description="Gender-based population stratification">
  <values>Male</values>
  <values>Female</values>
</groups>
```

**Result**: Two dimensions defined (Age, Gender).

---

### Step 2: Create Product

```xml
<products name="AgeAndGenderStratification"
          description="Combined age and gender stratification"
          groups="//@groups.0 //@groups.1"/>
```

**Result**: Cartesian product creates 6 combinations.

---

### Step 3: Susceptible Compartment

```xml
<compartments PrimaryName="Susceptible" population="9500" product="//@products.0">
  <outgoingFlows xsi:type="seir:ContactFlow"
                 contactRate="0.000003"
                 contactCompartment="//@compartments.2"
                 description="Disease transmission through contact with infectious individuals"
                 target="//@compartments.1">
    <!-- Children (0-17) - higher contact rates -->
    <stratumSpecificRates stratum="0-17,Male" multiplier="1.67"/>
    <stratumSpecificRates stratum="0-17,Female" multiplier="1.5"/>
    <!-- Adults (18-65) - baseline contact rates -->
    <stratumSpecificRates stratum="18-65,Male" multiplier="1.1"/>
    <stratumSpecificRates stratum="18-65,Female" multiplier="0.9"/>
    <!-- Elderly (66+) - lower contact rates -->
    <stratumSpecificRates stratum="66+,Male" multiplier="0.8"/>
    <stratumSpecificRates stratum="66+,Female" multiplier="0.67"/>
  </outgoingFlows>
</compartments>
```

**What's happening**:
1. **9500 susceptible people** split into 6 sub-populations
2. **ContactFlow** from Susceptible → Exposed
3. **contactCompartment="//@compartments.2"** means transmission depends on Infectious compartment (3rd compartment, 0-indexed)
4. **Base rate**: 0.000003
5. **Stratum-specific multipliers**:
   - Children have highest contacts (1.5-1.67×)
   - Adults near baseline (0.9-1.1×)
   - Elderly have lowest contacts (0.67-0.8×)
   - Males slightly higher than females in each age group

**Real-world interpretation**:
- Children (in school) have most contacts → higher transmission risk
- Elderly (retired) have fewer contacts → lower transmission risk
- Males tend to have slightly more contacts than females

---

### Step 4: Exposed Compartment

```xml
<compartments PrimaryName="Exposed" population="200" product="//@products.0">
  <outgoingFlows xsi:type="seir:RateFlow"
                 rate="0.2"
                 description="Progression from exposed to infectious (5 days average)"
                 target="//@compartments.2">
    <stratumSpecificRates stratum="0-17,Male" rate="0.25"/>
    <stratumSpecificRates stratum="0-17,Female" rate="0.25"/>
    <stratumSpecificRates stratum="18-65,Male" rate="0.2"/>
    <stratumSpecificRates stratum="18-65,Female" rate="0.2"/>
    <stratumSpecificRates stratum="66+,Male" rate="0.167"/>
    <stratumSpecificRates stratum="66+,Female" rate="0.167"/>
  </outgoingFlows>
</compartments>
```

**What's happening**:
1. **200 exposed people** (incubating disease)
2. **RateFlow** from Exposed → Infectious
3. **Incubation periods** (rate = 1/days):
   - Children: rate=0.25 → 4 days (faster immune response)
   - Adults: rate=0.2 → 5 days (standard)
   - Elderly: rate=0.167 → 6 days (slower immune response)
4. **No gender difference** in incubation (biologically plausible)

**Real-world interpretation**: After exposure, it takes 4-6 days to become infectious, depending on age and immune system strength.

---

### Step 5: Infectious Compartment

```xml
<compartments PrimaryName="Infectious" population="150" product="//@products.0">
  <!-- Recovery Flow -->
  <outgoingFlows xsi:type="seir:RateFlow"
                 rate="0.14"
                 description="Recovery from infection (7 days average)"
                 target="//@compartments.3">
    <stratumSpecificRates stratum="0-17,Male" rate="0.16"/>
    <stratumSpecificRates stratum="0-17,Female" rate="0.167"/>
    <stratumSpecificRates stratum="18-65,Male" rate="0.13"/>
    <stratumSpecificRates stratum="18-65,Female" rate="0.15"/>
    <stratumSpecificRates stratum="66+,Male" rate="0.09"/>
    <stratumSpecificRates stratum="66+,Female" rate="0.11"/>
  </outgoingFlows>

  <!-- Death Flow -->
  <outgoingFlows xsi:type="seir:RateFlow"
                 rate="0.01"
                 description="Disease-related death"
                 target="//@compartments.4">
    <stratumSpecificRates stratum="0-17,Male" rate="0.0012"/>
    <stratumSpecificRates stratum="0-17,Female" rate="0.0008"/>
    <stratumSpecificRates stratum="18-65,Male" rate="0.007"/>
    <stratumSpecificRates stratum="18-65,Female" rate="0.003"/>
    <stratumSpecificRates stratum="66+,Male" rate="0.06"/>
    <stratumSpecificRates stratum="66+,Female" rate="0.04"/>
  </outgoingFlows>
</compartments>
```

**What's happening**:
1. **150 infectious people** spreading disease
2. **TWO outgoing flows**: Recovery OR Death
3. **Recovery rates** (rate = 1/days):
   - Children: 6-6.25 days (fastest recovery)
   - Adults: 6.67-7.7 days
   - Elderly: 9-11 days (slowest recovery)
   - **Females recover faster** than males in each age group
4. **Mortality rates**:
   - Children: 0.08-0.12% (very low)
   - Adults: 0.3-0.7% (low to moderate)
   - Elderly: 4-6% (high!)
   - **Males die at higher rates** than females (epidemiologically accurate)

**Real-world interpretation**:
- Most people recover in 6-11 days
- Elderly and males face higher mortality risk
- Age is the strongest predictor of severe outcomes

---

### Step 6: Terminal Compartments

```xml
<compartments PrimaryName="Recovered" population="100" product="//@products.0"/>
<compartments PrimaryName="Death" population="50" product="//@products.0"/>
```

**What's happening**:
- Both are **terminal states** (no outgoing flows)
- Both are **stratified** (track which age/gender groups recovered or died)
- Initial populations: 100 recovered, 50 died

---

## Birth and Death Mechanics

### Birth Sources

```xml
<birthSources name="Natural births (Male)"
              rate="0.000015"
              targetCompartment="//@compartments.0"
              targetStratum="0-17,Male"/>

<birthSources name="Natural births (Female)"
              rate="0.000015"
              targetCompartment="//@compartments.0"
              targetStratum="0-17,Female"/>
```

**What's happening**:
1. New individuals enter the system (births, immigration, recruitment)
2. **rate="0.000015"**: Birth rate (per capita per day)
3. **targetCompartment**: Always Susceptible (compartment 0)
4. **targetStratum**: Newborns enter the 0-17 age group, split by gender
5. **Split 50/50**: Male and female birth rates are equal

**Formula**: `rate × totalPopulation = 0.000015 × 10,000 = 0.15 births/day`

**Why separate male/female?**
- Allows different birth rates if needed
- Maintains gender balance in age stratification

---

### Death Sinks

```xml
<deathSinks name="Natural death (Susceptible - 0-17,Male)"
            rate="0.000012"
            sourceCompartment="//@compartments.0"
            sourceStratum="0-17,Male"/>

<deathSinks name="Natural death (Susceptible - 66+,Male)"
            rate="0.0012"
            sourceCompartment="//@compartments.0"
            sourceStratum="66+,Male"/>
```

**What's happening**:
1. People exit the system due to **natural causes** (not disease-related)
2. **rate**: Natural mortality rate (age and gender specific)
3. **sourceCompartment**: Which compartment they leave from
4. **sourceStratum**: Which sub-population

**Mortality rates in model**:
- Children (0-17): 0.000008-0.000012 (very low)
- Adults (18-65): 0.000015-0.000025 (moderate)
- Elderly (66+): 0.0008-0.0012 (high!)
- **Males die at ~1.5× the rate of females** (realistic)

**Important**: These are NATURAL deaths (background mortality), separate from disease deaths which flow to Death compartment.

---

### Birth vs Death Sinks vs Death Compartment

**Three types of population exit/entry**:

1. **BirthSource**: Adds people to the system → Susceptible compartment
2. **DeathSink**: Removes people from system (natural causes, leaves no trace)
3. **Death Compartment**: People who died from DISEASE (tracked in model)

**Why track disease deaths separately?**
- Epidemiological interest: "How many people died from this disease?"
- Policy decisions: Hospital capacity, intervention effectiveness
- Death sinks are just population dynamics (not disease-specific)

---

## How the Tools Process This Model

### 1. Dynamic Diagram Generator

**Input**: `sample.compartmentalmodel` (with 2 groups, 6 strata)

**Process**:
1. Detects groups: Age (3 values), Gender (2 values)
2. Generates Cartesian product: 3 × 2 = 6 combinations
3. Creates 6 separate model files:
   - `sample_0_17_Male.compartmentalmodel`
   - `sample_0_17_Female.compartmentalmodel`
   - `sample_18_65_Male.compartmentalmodel`
   - `sample_18_65_Female.compartmentalmodel`
   - `sample_66__Male.compartmentalmodel`
   - `sample_66__Female.compartmentalmodel`

**For each file**:
- Extracts the stratum-specific rate (or multiplier) for that group
- Sets it as the base rate for all flows
- Removes all stratification (now a simple SEIR model)
- Keeps only birth/death sources matching that stratum

**Example**: For `sample_0_17_Male.compartmentalmodel`:
```xml
<!-- Original (stratified) -->
<outgoingFlows xsi:type="seir:ContactFlow"
               contactRate="0.000003"
               ...>
  <stratumSpecificRates stratum="0-17,Male" multiplier="1.67"/>
</outgoingFlows>

<!-- Becomes (flat) -->
<outgoingFlows xsi:type="seir:ContactFlow"
               contactRate="0.00000501"    <!-- 0.000003 × 1.67 -->
               ...>
  <!-- No stratum-specific rates -->
</outgoingFlows>
```

**Output**: 6 flat SEIR models, one per demographic group, ready for visualization.

---

### 2. Equation Generator

**Input**: `sample.compartmentalmodel`

**Process**:
1. Generates **aggregate equations** (treats all strata as one)
2. Generates **stratified equations** (separate equation for each stratum)

**Example Stratified Equation** for Susceptible_0_17_Male:

```
dSusceptible_0_17_Male/dt =
  + 0.000015                                          (births)
  - (0.00000501 * Susceptible_0_17_Male * Infectious_0_17_Male / 10000)  (transmission)
  - 0.000012 * Susceptible_0_17_Male                (natural death)
```

**Key features**:
- Uses **exact match** for stratum names: "0-17,Male"
- Calculates multiplier correctly: `0.000003 × 1.67 = 0.00000501`
- Only includes birth/death sources matching this stratum
- Each stratum interacts only with same stratum (0-17,Male can only infect 0-17,Male)

**Output**:
- `sample.txt`: Aggregate equations
- `sample_stratified.txt`: All 6 × 5 = 30 equations (6 strata × 5 compartments)

---

## Common Patterns and Best Practices

### Pattern 1: SEIR with Age Stratification

```
Groups: Age (0-17, 18-65, 65+)
Product: AgeStratification (single group)
Strata format: "0-17", "18-65", "65+"
Use case: Most infectious diseases (COVID, flu, measles)
```

**When to use**: Disease severity/transmission varies significantly by age.

---

### Pattern 2: SIR with Risk Groups

```
Groups: RiskBehavior (High, Medium, Low)
Product: RiskStratification (single group)
Strata format: "High", "Medium", "Low"
Use case: HIV, STIs, substance use epidemics
```

**When to use**: Transmission depends on behavioral risk factors.

---

### Pattern 3: SEIR with Age × Gender

```
Groups: Age (0-17, 18-65, 65+), Gender (Male, Female)
Product: AgeGenderStratification (Cartesian product)
Strata format: "0-17,Male", "18-65,Female", etc.
Use case: Diseases with strong age AND gender effects
```

**When to use**: Need to capture interaction effects (e.g., elderly males have highest mortality).

**Example**: sample.compartmentalmodel

---

### Pattern 4: Complex Multi-Dimensional

```
Groups: Age (3), Location (Urban/Rural), VaccineStatus (Vaccinated/Unvaccinated)
Product: ComplexStratification (3 groups)
Strata format: "0-17,Urban,Vaccinated"
Combinations: 3 × 2 × 2 = 12 strata
```

**When to use**: Intervention modeling, policy analysis.

**Warning**: Exponential explosion! 3 groups with 3 values each = 27 strata.

---

### Best Practice: Choosing Stratification

**Questions to ask**:
1. Does this factor significantly affect transmission or severity?
2. Do I have data for stratum-specific rates?
3. Can I justify the added complexity?
4. Is the model still interpretable with this many strata?

**Rule of thumb**:
- **Simple models** (1 group, 3-5 strata): Easier to interpret, fewer parameters
- **Complex models** (2+ groups, 6+ strata): More realistic, but harder to calibrate

**sample.compartmentalmodel uses 2 groups (6 strata)**: A good middle ground for demonstration.

---

### Best Practice: Rate Specification

**Use multipliers when**:
- You think in relative terms ("children have 1.5× more contacts")
- You want to easily adjust base rates
- Relative differences are more robust than absolute values

**Use absolute rates when**:
- You have precise epidemiological data
- Rates are measured independently for each group
- Different mechanisms apply (not just scaled versions)

**Never mix both**: Choose one approach per flow for clarity.

---

### Best Practice: Birth and Death Sources

**For stratified models, always**:
1. Specify `targetStratum` for birth sources (which group do newborns enter?)
2. Specify `sourceStratum` for death sinks (different mortality by age/gender)
3. Create birth/death sources for EACH stratum (6 strata → 6 birth sources, 6×N death sinks)

**Why?** Prevents duplication and ensures correct population dynamics.

---

### Best Practice: Terminal Compartments

**Recovered and Death compartments**:
- NO outgoing flows (terminal states)
- Apply same product as other compartments (track which groups recovered/died)
- Useful for outcomes: "How many elderly males died vs recovered?"

---

## Advanced Topics

### Multiple Products in One Model

You CAN have different compartments with different products:

```xml
<products name="AgeOnly" groups="//@groups.0"/>
<products name="AgeGender" groups="//@groups.0 //@groups.1"/>

<compartments PrimaryName="Susceptible" product="//@products.0"/>  <!-- 3 strata -->
<compartments PrimaryName="Hospitalized" product="//@products.1"/> <!-- 6 strata -->
```

**Use case**: Track hospitalizations more granularly than general population.

**Warning**: Complex to manage flows between different stratification schemes.

---

### Dynamic Stratification

**Current limitation**: Strata are fixed (people don't age, change gender, etc.)

**Future enhancement**: Add "transition flows" between strata
- 0-17,Male → 18-65,Male (aging)
- Unvaccinated → Vaccinated (vaccination)

**Not currently implemented** in sample.compartmentalmodel.

---

### Mixing Between Strata

**Current model**: Each stratum interacts only with itself
- 0-17,Male can only infect other 0-17,Male individuals

**Real world**: Cross-stratum mixing (children infect adults)

**How to model?**:
- Create separate ContactFlows for each source-target stratum pair
- Specify mixing matrices (contact rates between different groups)

**Not shown in sample.compartmentalmodel** (would be very verbose).

---

## Summary

**sample.compartmentalmodel demonstrates**:
1. ✅ Cartesian product stratification (Age × Gender)
2. ✅ ContactFlow vs RateFlow
3. ✅ Multiplier-based stratum-specific rates
4. ✅ Birth and death sources with stratum targeting
5. ✅ Complete SEIR structure with recovery and mortality

**Key takeaways**:
- **Groups** define dimensions, **Products** create combinations
- **ContactFlow** = transmission (depends on two compartments)
- **RateFlow** = progression (depends on one compartment)
- **Multipliers** = relative rate adjustments (cleaner than absolute rates)
- **Stratum format**: "value1,value2" for Cartesian products

**Tools process correctly**:
- DynamicDiagramGenerator: Creates 6 flat models
- EquationGenerator: Creates 30 stratified equations

**This model serves as a template** for building your own stratified epidemic models!

---

## Quick Reference

### File Structure Cheat Sheet

```xml
<seir:CompartmentalModel totalPopulation="10000">

  <!-- 1. Define dimensions -->
  <groups name="Age">
    <values>0-17</values>
    <values>18-65</values>
    <values>66+</values>
  </groups>

  <groups name="Gender">
    <values>Male</values>
    <values>Female</values>
  </groups>

  <!-- 2. Create combination -->
  <products name="Combined" groups="//@groups.0 //@groups.1"/>

  <!-- 3. Apply to compartments -->
  <compartments PrimaryName="Susceptible" product="//@products.0">
    <outgoingFlows xsi:type="seir:ContactFlow"
                   contactRate="0.000003"
                   contactCompartment="//@compartments.2"
                   target="//@compartments.1">
      <stratumSpecificRates stratum="0-17,Male" multiplier="1.67"/>
      <!-- ... all 6 combinations ... -->
    </outgoingFlows>
  </compartments>

  <!-- 4. Add birth/death -->
  <birthSources name="Births (Male)"
                rate="0.000015"
                targetCompartment="//@compartments.0"
                targetStratum="0-17,Male"/>

  <deathSinks name="Deaths (Male)"
              rate="0.000012"
              sourceCompartment="//@compartments.0"
              sourceStratum="0-17,Male"/>
</seir:CompartmentalModel>
```

### Stratum Naming Patterns

| Groups | Example Stratum | Use Case |
|--------|----------------|----------|
| Age | `"0-17"` | COVID, flu |
| Risk | `"High"` | HIV, STIs |
| Age + Gender | `"0-17,Male"` | Gender-specific diseases |
| Age + Location | `"18-64,Urban"` | Geographic spread |
| Age + Gender + Vaccine | `"65+,Female,Vaccinated"` | Intervention modeling |

---

**For more examples, see**:
- `covid.compartmentalmodel`: Age-stratified COVID model with quarantine
- `HIV.compartmentalmodel`: Risk-behavior stratified HIV model
- `IMPLEMENTATION_GUIDE.md`: Step-by-step modeling tutorial
