# Protocol for Building Compartmental Models from Research Papers

**Version:** 1.0

Step-by-step guide for extracting epidemiological models from academic papers

## Step 1: Initial Paper Reading

Read paper abstract and introduction to identify disease and model type

### Actions:

1. Read abstract to understand the research question
2. Read introduction to identify the disease being modeled
3. Identify model type (SEIR, SEIRS, SIR, etc.)
4. Note if paper mentions stratification, interventions, or special features

### Output:

Disease name, model type, special features mentioned

## Step 2: Locate Model Description

Find Methods section and look for "Mathematical Model" or "Model Structure"

### Actions:

1. Navigate to Methods/Modeling section
2. Look for headings: "Mathematical Model", "Model Structure", "Compartmental Model"
3. Find model diagram (usually Figure 1 or Figure 2)
4. Locate equation list or model equations

### Output:

Model diagram reference, equation section location

## Step 3: Extract Compartments

Identify all compartments from diagram, equations, or text

### Actions:

1. List all compartment names
2. Note primary names (e.g., "Susceptible")
3. Note secondary names if present (e.g., "presymptomatic", "quarantined")
4. Record initial population values if given

### Sources:

- **Model Diagram (Figure X)**: Look for boxes/circles representing population states
  - Example: S (Susceptible)
  - Example: E (Exposed)
  - Example: I (Infectious)
  - Example: R (Recovered)
- **Equation List**: Each differential equation typically represents one compartment
  - Example: dS/dt = ... → S compartment
  - Example: dI/dt = ... → I compartment
- **Text Description**: Look for phrases like "The model includes compartments for..."
  - Example: "Susceptible individuals (S)"
  - Example: "Infected individuals (I)"

### Output:

List of compartments with names and initial values

## Step 4: Extract Flows

Identify flows between compartments by reading equations

### Actions:

1. Read each differential equation
2. Identify positive terms (inflows)
3. Identify negative terms (outflows)
4. Classify each term as ContactFlow, RateFlow, ExternalSource, or ExternalSink
5. Note the rate parameter or value for each flow

### Flow Types:

- **ContactFlow**:
  - Pattern: Terms with S*I/N or β*S*I/N
  - Example: dS/dt = -β*S*I/N → ContactFlow from S to E
  - Characteristics:
    - Involves two compartments (source and contact)
    - Rate depends on product of two populations
    - Usually represents transmission/infection
- **RateFlow**:
  - Pattern: Terms with rate*Compartment
  - Example: dE/dt = ... - σ*E → RateFlow from E to I
  - Characteristics:
    - Single compartment with fixed rate
    - Represents progression, recovery, or transition
    - Rate is constant (not dependent on other compartments)
- **ExternalSource**:
  - Pattern: Terms with + (inflow from outside)
  - Example: dS/dt = + π*N → ExternalSource to S
  - Characteristics:
    - Positive term not from another compartment
    - Represents births, recruitment, or external input
    - May be optional for short-term models
- **ExternalSink**:
  - Pattern: Terms with - (outflow not to another compartment)
  - Example: dS/dt = ... - μ*S → ExternalSink from S
  - Characteristics:
    - Negative term not going to another compartment
    - Represents deaths or external output
    - May be optional for short-term models

### Output:

List of flows with source, target, type, and rate

## Step 5: Extract Parameters

Find all parameter values from tables, text, or supplementary materials

### Actions:

1. Locate parameter table (usually Table 1)
2. Extract parameter name, symbol, value, unit, description
3. Check for uncertainty information (ranges, confidence intervals)
4. Note if parameter is constant, variable, or expression
5. Record source (table, text, supplementary, reference)

### Sources:

- **Table 1: Model Parameters**: Most common location - look for parameter table
- **Inline Text**: Look for phrases like "transmission rate β = 0.3"
- **Supplementary Materials**: Check supplementary files for additional parameters
- **References to Other Papers**: Parameters may cite values from previous studies

### Output:

List of parameters with values, units, and sources

## Step 6: Extract Stratification

Identify population stratification if mentioned

### Actions:

1. Search text for stratification keywords
2. Identify group categories (age, gender, risk, location)
3. List group values (e.g., "0-17", "18-64", "65+")
4. Note if parameters differ by stratum
5. Check if flows have stratum-specific rates

### Output:

Stratification groups and values (if present)

**Note:** Stratification is OPTIONAL - only extract if paper mentions it

## Step 7: Record Initial Conditions

Extract initial population values for each compartment

### Actions:

1. Find initial condition statement
2. Extract population value for each compartment
3. Calculate total population if not given
4. Note if initial conditions are stratified

### Sources:

- Text: "We initialized with S(0) = X, I(0) = Y..."
- Table: Initial conditions table
- Figure caption: Initial values in simulation description

### Output:

Initial population values for each compartment

## Step 8: Document Missing Information

Identify what information is missing or uncertain

### Actions:

1. List all mentioned but undefined parameters
2. Note vague parameter descriptions
3. Record inaccessible references
4. Mark uncertain values with confidence level
5. Document assumptions made

### Output:

List of gaps and uncertainties

## Step 1: Initial Paper Reading

Read paper abstract and introduction to identify disease and model type

### Actions:

1. Read abstract to understand the research question
2. Read introduction to identify the disease being modeled
3. Identify model type (SEIR, SEIRS, SIR, etc.)
4. Note if paper mentions stratification, interventions, or special features

### Output:

Disease name, model type, special features mentioned

## Step 2: Locate Model Description

Find Methods section and look for "Mathematical Model" or "Model Structure"

### Actions:

1. Navigate to Methods/Modeling section
2. Look for headings: "Mathematical Model", "Model Structure", "Compartmental Model"
3. Find model diagram (usually Figure 1 or Figure 2)
4. Locate equation list or model equations

### Output:

Model diagram reference, equation section location

## Step 3: Extract Compartments

Identify all compartments from diagram, equations, or text

### Actions:

1. List all compartment names
2. Note primary names (e.g., "Susceptible")
3. Note secondary names if present (e.g., "presymptomatic", "quarantined")
4. Record initial population values if given

### Sources:

- **Model Diagram (Figure X)**: Look for boxes/circles representing population states
  - Example: S (Susceptible)
  - Example: E (Exposed)
  - Example: I (Infectious)
  - Example: R (Recovered)
- **Equation List**: Each differential equation typically represents one compartment
  - Example: dS/dt = ... → S compartment
  - Example: dI/dt = ... → I compartment
- **Text Description**: Look for phrases like "The model includes compartments for..."
  - Example: "Susceptible individuals (S)"
  - Example: "Infected individuals (I)"

### Output:

List of compartments with names and initial values

## Step 4: Extract Flows

Identify flows between compartments by reading equations

### Actions:

1. Read each differential equation
2. Identify positive terms (inflows)
3. Identify negative terms (outflows)
4. Classify each term as ContactFlow, RateFlow, ExternalSource, or ExternalSink
5. Note the rate parameter or value for each flow

### Flow Types:

- **ContactFlow**:
  - Pattern: Terms with S*I/N or β*S*I/N
  - Example: dS/dt = -β*S*I/N → ContactFlow from S to E
  - Characteristics:
    - Involves two compartments (source and contact)
    - Rate depends on product of two populations
    - Usually represents transmission/infection
- **RateFlow**:
  - Pattern: Terms with rate*Compartment
  - Example: dE/dt = ... - σ*E → RateFlow from E to I
  - Characteristics:
    - Single compartment with fixed rate
    - Represents progression, recovery, or transition
    - Rate is constant (not dependent on other compartments)
- **ExternalSource**:
  - Pattern: Terms with + (inflow from outside)
  - Example: dS/dt = + π*N → ExternalSource to S
  - Characteristics:
    - Positive term not from another compartment
    - Represents births, recruitment, or external input
    - May be optional for short-term models
- **ExternalSink**:
  - Pattern: Terms with - (outflow not to another compartment)
  - Example: dS/dt = ... - μ*S → ExternalSink from S
  - Characteristics:
    - Negative term not going to another compartment
    - Represents deaths or external output
    - May be optional for short-term models

### Output:

List of flows with source, target, type, and rate

## Step 5: Extract Parameters

Find all parameter values from tables, text, or supplementary materials

### Actions:

1. Locate parameter table (usually Table 1)
2. Extract parameter name, symbol, value, unit, description
3. Check for uncertainty information (ranges, confidence intervals)
4. Note if parameter is constant, variable, or expression
5. Record source (table, text, supplementary, reference)

### Sources:

- **Table 1: Model Parameters**: Most common location - look for parameter table
- **Inline Text**: Look for phrases like "transmission rate β = 0.3"
- **Supplementary Materials**: Check supplementary files for additional parameters
- **References to Other Papers**: Parameters may cite values from previous studies

### Output:

List of parameters with values, units, and sources

## Step 6: Extract Stratification

Identify population stratification if mentioned

### Actions:

1. Search text for stratification keywords
2. Identify group categories (age, gender, risk, location)
3. List group values (e.g., "0-17", "18-64", "65+")
4. Note if parameters differ by stratum
5. Check if flows have stratum-specific rates

### Output:

Stratification groups and values (if present)

**Note:** Stratification is OPTIONAL - only extract if paper mentions it

## Step 7: Record Initial Conditions

Extract initial population values for each compartment

### Actions:

1. Find initial condition statement
2. Extract population value for each compartment
3. Calculate total population if not given
4. Note if initial conditions are stratified

### Sources:

- Text: "We initialized with S(0) = X, I(0) = Y..."
- Table: Initial conditions table
- Figure caption: Initial values in simulation description

### Output:

Initial population values for each compartment

## Step 8: Document Missing Information

Identify what information is missing or uncertain

### Actions:

1. List all mentioned but undefined parameters
2. Note vague parameter descriptions
3. Record inaccessible references
4. Mark uncertain values with confidence level
5. Document assumptions made

### Output:

List of gaps and uncertainties

