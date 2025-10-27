# EpiMDE: Epidemiological Model-Driven Engineering Framework

EpiMDE is a comprehensive framework for building, analyzing, and simulating epidemiological models using Model-Driven Engineering (MDE) principles. Built on Eclipse Modeling Framework (EMF) and Sirius, it supports both graphical and textual model editing with a focus on symbolic/parametric modeling.

## Key Features

- **Symbolic/Parametric Modeling** 🎯 **NEW!**
  - Define parameters with names, types (CONSTANT/VARIABLE/EXPRESSION), and descriptions
  - Use symbolic expressions (e.g., `beta = eta_S * IM`) instead of hardcoded numbers
  - Separate model structure from parameter values for reusability
  - Support for environmental dependencies (temperature-dependent transmission)

- **Graphical and Textual Editing**
  - Sirius-based graphical diagrams
  - Direct XML editing with EMF tree editor
  - Automatic diagram generation from models

- **Population Stratification**
  - Age groups, geographic locations, risk levels, vaccination status, etc.
  - Cartesian products of multiple groups
  - Stratum-specific rates and flows

- **Equation Generation** 🔧 **ENHANCED!**
  - Automatic generation of differential equations from models
  - Symbolic output matching mathematical papers
  - Support for both parametric and numeric models
  - Backward compatible with existing models

- **Python Simulation**
  - Numerical integration with customizable initial conditions
  - CSV output for analysis and visualization
  - Interactive compartment selection

- **Model Utilities**
  - Automatic model splitting for stratified populations
  - Group-aware decomposition tools
  - Target platform dependency management

---

## 📁 Project Structure

```
SEIR/
├── SEIRModel/                 # Core model plugin
│   ├── seir.ecore            # Ecore metamodel definition
│   ├── seir.genmodel         # EMF GenModel
│   ├── seir.aird             # Sirius representation instance
│   ├── covid.seirmodel       # COVID-19 stratified model example
│   ├── HIV.seirmodel         # HIV model example
│   ├── covid_*.seirmodel     # Age-specific COVID models
│   ├── simulation/           # Python simulation tools
│   │   └── simulation.py     # Numerical integration script
│   └── src/                  # Generated Java source
│       ├── seirmodel/        # Core model classes
│       └── seir/             # Utilities and equation generator
├── org.seir.targetplatform/   # Target platform project
│   └── seir.target           # Eclipse target definition
├── SEIRModel.design/          # Sirius graphical definition
│   └── description/SEIRModel.odesign
├── SEIRModel.edit/            # EMF edit plugin
├── SEIRModel.editor/          # EMF editor plugin
└── SEIRModel.tests/           # Unit tests
```

---

## 🚀 Setup Instructions

### 1. Install Required Eclipse
- **Eclipse Modeling** package
- OR install features:
  - EMF SDK
  - Sirius (from update site)

### 2. Import Projects
Use `File > Import > Existing Projects into Workspace`, and import all 6+ projects listed above.

### 3. Setup Target Platform
- Open `seir.target` in Eclipse
- Click **"Set as Active Target Platform"**

This will resolve dependencies like EMF, Sirius, and required runtimes.

---

## 🧠 SEIR Metamodel

The SEIR model is defined in `seir.ecore` and includes:

### Core Components
- **`SEIRModel`** (root) - Contains compartments, flows, parameters, groups, and products
- **`Compartment`** - Population states with `PrimaryName`, `SecondaryName`, `population`, and `outgoingFlows`
- **Flow Types**:
  - `RateFlow`: Rate-based transitions (e.g., recovery, progression)
  - `ContactFlow`: Contact-based transmission between compartments
  - `BirthSource`: Population inflows (recruitment, births)
  - `DeathSink`: Population outflows (mortality)

### Parametric Modeling 🆕
- **`Parameter`** - Named parameters with three types:
  - **CONSTANT**: Fixed values with names (e.g., `pi = 0.012`, `mu1 = 0.0002`)
  - **VARIABLE**: Placeholders for simulation-time values (e.g., `temperature`, `intervention_level`)
  - **EXPRESSION**: Computed from other parameters/compartments (e.g., `beta2 = eta_S * IM`)
- **`ParameterType`** - Enum: `CONSTANT`, `VARIABLE`, `EXPRESSION`
- **Parameter References**: All flows can reference parameters instead of using numeric values:
  - `rateParameter` in RateFlow
  - `contactRateParameter` in ContactFlow
  - `rateParameter` in BirthSource/DeathSink
  - `rateParameter` and `multiplierParameter` in StratumSpecificRate

### Stratification Components
- **`Group`** - Defines population categories (e.g., age groups: 0-17, 18-64, 65+)
- **`Product`** - Creates Cartesian products of groups for multi-dimensional stratification
- **`StratumSpecificRate`** - Allows different rates/multipliers for each population segment

### Backward Compatibility
- All numeric attributes (`rate`, `contactRate`, `multiplier`) are preserved
- Old models without parameters continue to work exactly as before
- Parameter references take precedence when both are present

---

## ✏️ Creating and Editing Models

### Textual
- Right-click → `New → Other → Example EMF Model Creation Wizards → SEIR Model`
- Save with `.seirmodel` extension
- You can edit via the generated tree editor (`SEIRModel.editor`) or directly via XML

### Graphical (Sirius)

#### Step-by-Step:
1. Open `SEIRModel.odesign` (inside `SEIRModel.design/description/`)
2. Ensure `plugin.xml` contains:
```xml
<extension point="org.eclipse.sirius.componentization">
  <component name="SEIRModel" id="SEIRModel.design" class="org.eclipse.sirius.business.api.componentization.ViewpointRegistry$ViewpointComponent">
    <viewpoints>
      <viewpoint path="/description/SEIRModel.odesign"/>
    </viewpoints>
  </component>
</extension>
```
3. Register viewpoint in `MANIFEST.MF` (bundle activator optional)
4. Run as `Eclipse Application`
5. In the runtime workspace:
   - Open `.aird` file
   - Right-click on `.seirmodel` → `Viewpoints Selection` → enable **MyViewpoint**
   - Right-click → `New Representation → SEIRDiagram`
   - Compartments will appear as nodes, and flows as arrows

#### Label Flows with Rates
In the `.odesign`, under `FlowEdge → CenterLabelStyle`, ensure:
- Label Expression: `aql:self.rate`
- Label Size: `12`

If it's showing as `self.rate` literally, check that AQL interpreter is selected in Sirius preferences.

---

## ➕ Examples

### Example 1: Numeric Model (Legacy Style)
```xml
<seir:SEIRModel totalPopulation="10000">
  <compartments PrimaryName="Susceptible" population="9900">
    <outgoingFlows xsi:type="seir:ContactFlow"
                   contactRate="0.0003"
                   contactCompartment="//@compartments.1"
                   target="//@compartments.1"/>
  </compartments>
  <compartments PrimaryName="Infectious" population="100">
    <outgoingFlows xsi:type="seir:RateFlow"
                   rate="0.1"
                   target="//@compartments.2"/>
  </compartments>
  <compartments PrimaryName="Recovered" population="0"/>

  <deathSinks name="Natural_Death" rate="0.00005" sourceCompartment="//@compartments.0"/>
</seir:SEIRModel>
```

### Example 2: Parametric Model (New Style) 🆕
```xml
<seir:SEIRModel totalPopulation="10000">
  <!-- Define parameters -->
  <parameters name="beta" type="CONSTANT"
              expression="0.0003"
              description="Transmission rate"
              unit="per day"/>
  <parameters name="gamma" type="CONSTANT"
              expression="0.1"
              description="Recovery rate"
              unit="per day"/>
  <parameters name="mu" type="CONSTANT"
              expression="0.00005"
              description="Natural death rate"
              unit="per day"/>

  <!-- Reference parameters in flows -->
  <compartments PrimaryName="Susceptible" population="9900">
    <outgoingFlows xsi:type="seir:ContactFlow"
                   contactRateParameter="//@parameters.0"
                   contactCompartment="//@compartments.1"
                   target="//@compartments.1"/>
  </compartments>
  <compartments PrimaryName="Infectious" population="100">
    <outgoingFlows xsi:type="seir:RateFlow"
                   rateParameter="//@parameters.1"
                   target="//@compartments.2"/>
  </compartments>
  <compartments PrimaryName="Recovered" population="0"/>

  <deathSinks name="Natural_Death"
              rateParameter="//@parameters.2"
              sourceCompartment="//@compartments.0"/>
</seir:SEIRModel>
```

**Output difference:**
- **Numeric model**: `dS/dt = - 0.0003 * S * I / 10000`
- **Parametric model**: `dS/dt = - beta * S * I / 10000`

### Example 3: Expression Parameters (Advanced) 🆕
```xml
<seir:SEIRModel totalPopulation="935">
  <!-- Basic transmission parameters -->
  <parameters name="eta_S" type="CONSTANT"
              expression="0.0125"
              description="Susceptible contact rate with mosquitoes"
              unit="per day"/>

  <!-- Composite expression -->
  <parameters name="beta2" type="EXPRESSION"
              expression="eta_S * IM"
              description="Force of infection on susceptibles (vector transmission)"
              unit="per day"/>

  <!-- Use expression in flow -->
  <compartments PrimaryName="Susceptible_Human" population="800">
    <outgoingFlows xsi:type="seir:ContactFlow"
                   contactRateParameter="//@parameters.1"
                   contactCompartment="//@compartments.9"
                   target="//@compartments.2"/>
  </compartments>
  <compartments PrimaryName="Infectious_Mosquito" population="5"/>
</seir:SEIRModel>
```

**Output**: `dSusceptible_Human/dt = - (eta_S * IM) * Susceptible_Human * Infectious_Mosquito / 935`

## 🎯 Population Stratification Example

The system now supports population stratification. Example from `covid.seirmodel`:

```xml
<!-- Define age groups -->
<groups name="AgeGroup" description="Age-based population groups">
  <values>0-17</values>   <!-- Children -->
  <values>18-64</values>  <!-- Adults -->
  <values>65+</values>    <!-- Elderly -->
</groups>

<!-- Create age-stratified product -->
<products name="AgeStratified" description="Age-based stratification" groups="//@groups.0"/>

<!-- Stratified compartment -->
<compartments PrimaryName="Susceptible" population="990000" product="//@products.0">
  <outgoingFlows xsi:type="seir:ContactFlow" 
                 description="Infection" 
                 contactRate="0.0001" 
                 target="//@compartments.1">
    <!-- Age-specific rates -->
    <stratumSpecificRates stratum="0-17" rate="0.000006" multiplier="0.7"/>
    <stratumSpecificRates stratum="18-64" rate="0.000015" multiplier="1.0"/>
    <stratumSpecificRates stratum="65+" rate="0.000008" multiplier="1.3"/>
  </outgoingFlows>
</compartments>
```

This creates separate compartments for each age group (Susceptible_0-17, Susceptible_18-64, Susceptible_65+) with different transmission rates.

### Birth Sources and Death Sinks with Stratification

**Birth Sources** target specific population strata:
```xml
<birthSources name="Population Birth" rate="3.0E-5"
              targetCompartment="//@compartments.0"
              targetStratum="0-17"/>
```

**Death Sinks** handle natural mortality by stratum:
```xml
<deathSinks name="Child Natural Death (0-17)" rate="0.0000005"
            sourceCompartment="//@compartments.0"
            sourceStratum="0-17"/>
<deathSinks name="Adult Natural Death (18-64)" rate="0.00002"
            sourceCompartment="//@compartments.0"
            sourceStratum="18-64"/>
```

**Disease-Induced Deaths** flow to death compartments:
```xml
<compartments PrimaryName="ICU" population="400" product="//@products.0">
  <outgoingFlows xsi:type="seir:RateFlow"
                 description="Death from COVID"
                 target="//@compartments.14" rate="0.4">
    <!-- Age-specific COVID mortality rates -->
    <stratumSpecificRates stratum="0-17" rate="0.0"/>
    <stratumSpecificRates stratum="18-64" rate="0.20"/>
    <stratumSpecificRates stratum="65+" rate="0.58"/>
  </outgoingFlows>
</compartments>
<compartments PrimaryName="COVID Deaths" population="100" product="//@products.0"/>
```

---

## 🧮 Equation Generation

Implemented in `SEIREquationGenerator.java`. Generates differential equations from `.seirmodel` files with support for both **numeric** and **parametric** models.

### Running the Generator

1. Right-click `SEIRModel/src/seir/equationgenerator/SEIREquationGenerator.java`
2. Select **"Run As" → "Java Application"**
3. Enter the model filename when prompted (e.g., `malaria.seirmodel`, `HIV.seirmodel`)
4. Equations are displayed in console and saved to `.txt` files

### Output Modes

#### Numeric Models (Legacy)
Models using numeric attributes output numeric values:
```
dSusceptible/dt = - (0.0003 * Susceptible * Infectious / 10000)
dInfectious/dt = + (0.0003 * Susceptible * Infectious / 10000) - 0.1 * Infectious
```

#### Parametric Models (New) 🆕
Models using parameters output symbolic equations:
```
dSusceptible/dt = - (beta) * Susceptible * Infectious / 10000
dInfectious/dt = + (beta) * Susceptible * Infectious / 10000 - gamma * Infectious
```

#### Expression Parameters 🆕
EXPRESSION type parameters are expanded inline:
```
dSusceptible_Human/dt = + pi * 935
                        + theta * Vaccinated_Human
                        + gamma * Recovered_Human
                        - (eta_S * IM) * Susceptible_Human * Infectious_Mosquito / 935
                        - (xi_S * IH) * Susceptible_Human * Infectious_Human / 935
                        - mu1 * Susceptible_Human
```

### Stratified Models
The generator automatically creates equations for each population segment:

```
=== Age-Stratified Model ===
dSusceptible_0-17/dt = - (beta_child) * Susceptible_0-17 * Infectious_0-17 / N
dSusceptible_18-64/dt = - (beta_adult) * Susceptible_18-64 * Infectious_18-64 / N
dSusceptible_65+/dt = - (beta_elderly) * Susceptible_65+ * Infectious_65+ / N
```

### How It Works

The generator intelligently handles parameters:
- **CONSTANT parameters**: Outputs parameter name (e.g., `pi`, `mu1`, `gamma`)
- **EXPRESSION parameters**: Outputs the expression (e.g., `eta_S * IM`)
- **Numeric attributes**: Outputs numeric value (e.g., `0.0003`)
- **Priority**: Parameter references take precedence over numeric attributes

**Backward Compatible**: Old models without parameters continue to work unchanged.

---

## 🐍 Python Simulation

The project includes Python simulation capabilities in `SEIRModel/simulation/simulation.py`.

### Running Simulations:

1. **Generate equations** first using `SEIREquationGenerator.java`
2. **Navigate to simulation directory**:
   ```bash
   cd SEIR/SEIRModel/simulation/
   ```
3. **Run the simulation script**:
   ```bash
   python simulation.py
   ```
4. **Follow the interactive prompts**:
   - Enter the equation file name (e.g., "HIV.txt")
   - Select which compartments to include in simulation
   - Provide initial population values
   - Specify simulation duration in years

### Output:
- **CSV file** with timestamped population values
- **Numerical integration** using Euler's method (dt=0.01)
- **Customizable** compartment selection and initial conditions

### Example Usage:
```
Enter .txt file name: HIV
Choose compartments to simulate:
Include 'Susceptible'? (y/n): y
Include 'Infectious'? (y/n): y
...
Initial value for 'Susceptible': 10000
Initial value for 'Infectious': 100
...
Simulate how many years?: 10
Results saved to HIV.csv
```

---

## 🔧 Model Utilities

### Automatic Model Splitting
Located in `src/seir/utilities/`, these tools help manage large stratified models:

- **`DynamicDiagramGenerator.java`**: **[RECOMMENDED]** Advanced group-aware model splitter that:
  - Automatically detects all group values in your model
  - Creates separate stratified models for each group (e.g., age-specific COVID models)
  - Properly filters birth sources and death sinks by target/source stratum
  - Handles stratum-specific rates and flows correctly
  - Generates clean, executable models for each population group

- **Legacy utilities** (deprecated):
  - `AutomaticModelSplitter.java`: Basic model splitting
  - `SimpleSplitter.java`: Simple decomposition utilities

### Running Model Splitting:
Use the provided scripts to run DynamicDiagramGenerator:
- **Windows**: `run_splitter.bat covid.seirmodel`
- **Linux/Mac**: `./run_splitter.sh covid.seirmodel`

This will generate separate models like `covid_0-17.seirmodel`, `covid_18-64.seirmodel`, etc.

---

## 🔬 Parameter System Guide

### Why Use Parameters?

The parameter system allows you to:
- **Match published papers**: Use the same mathematical notation as papers (e.g., `π`, `β`, `γ`)
- **Separate structure from values**: One model structure, multiple parameter sets
- **Enable sensitivity analysis**: Change parameters without modifying model
- **Support environmental dependencies**: Temperature-dependent transmission rates
- **Self-document models**: Parameters include descriptions and units

### Parameter Types

| Type | Purpose | Example |
|------|---------|---------|
| **CONSTANT** | Fixed values with names | `pi = 0.012` (birth rate) |
| **VARIABLE** | Simulation-time inputs | `temperature` (to be specified) |
| **EXPRESSION** | Computed from others | `beta2 = eta_S * IM` |

### Creating Parameters

```xml
<!-- CONSTANT: Fixed value -->
<parameters name="mu1" type="CONSTANT"
            expression="0.0002"
            description="Human natural death rate"
            unit="per day"/>

<!-- VARIABLE: To be assigned during simulation -->
<parameters name="T" type="VARIABLE"
            description="Temperature in degrees Celsius"
            unit="°C"/>

<!-- EXPRESSION: Computed from other parameters/compartments -->
<parameters name="beta2" type="EXPRESSION"
            expression="eta_S * IM"
            description="Force of infection on susceptibles"
            unit="per day"/>
```

### Using Parameters in Flows

Reference parameters using XMI paths:
```xml
<!-- RateFlow with parameter -->
<outgoingFlows xsi:type="seir:RateFlow"
               rateParameter="//@parameters.0"
               target="//@compartments.2"/>

<!-- ContactFlow with parameter -->
<outgoingFlows xsi:type="seir:ContactFlow"
               contactRateParameter="//@parameters.5"
               contactCompartment="//@compartments.9"
               target="//@compartments.2"/>
```

**Index reference**: `//@parameters.N` refers to the (N+1)th parameter (0-indexed)

### Equation Output

The equation generator intelligently outputs:
- **CONSTANT/VARIABLE**: Parameter name → `pi`, `mu1`, `gamma`
- **EXPRESSION**: Full expression → `eta_S * IM`, `xi_V * IH`
- **Numeric (legacy)**: Numeric value → `0.0003`, `0.1`

### Migration from Numeric to Parametric

**Step 1**: Define parameters at the top of your model
```xml
<parameters name="beta" type="CONSTANT" expression="0.0003" unit="per day"/>
```

**Step 2**: Change flow attributes from `rate=` to `rateParameter=`
```xml
<!-- Before -->
<outgoingFlows xsi:type="seir:RateFlow" rate="0.0003" .../>

<!-- After -->
<outgoingFlows xsi:type="seir:RateFlow" rateParameter="//@parameters.0" .../>
```

**Step 3**: Test equation generation to verify symbolic output

### Documentation

See these files for complete details:
- **`PARAMETER_SYSTEM_DESIGN.md`** - Technical design and rationale
- **`MALARIA_MODEL_DOCUMENTATION.md`** - Complete malaria model walkthrough
- **`PARAMETER_NAME_OUTPUT_UPDATE.md`** - Equation generator behavior
- **`BACKWARD_COMPATIBILITY_VERIFICATION.md`** - Compatibility guarantees

---

## ❓ Troubleshooting

### Flows Not Showing in Diagram?
- Check `FlowEdge` mapping:
  - Domain Class: `seirmodel.Flow`
  - Semantic Candidates: `aql:self.compartments.outgoingFlows`
  - Source Mapping: `CompartmentNode`
  - Target Finder Expression: `aql:self.target`

### Label Showing `self.rate` Instead of Value?
- Use AQL (not Acceleo) interpreter
- Ensure Sirius Preferences > Interpreter uses **AQL**

### Viewpoints Don’t Appear?
- Make sure `.odesign` is registered correctly in `plugin.xml`
- Open `.aird` and enable the viewpoint from `Viewpoint Selection`

---

## 📦 Exporting Diagrams
- Use toolbar buttons to export as image (PNG/SVG)
- Or right-click the canvas → `Export as Image`

---

## 📌 Requirements

### Eclipse Development:
- Eclipse 2023-09 or later (Eclipse Modeling Distribution recommended)
- Java 17+
- Sirius 7.4.7 or later (configured via `.target`)
- EMF (Eclipse Modeling Framework) SDK

### Python Simulation:
- Python 3.7+ for running simulations
- No external Python dependencies required (uses standard library only)

### Model Examples:
- **Epidemiological Models Included**:
  - **`malaria.seirmodel`** 🆕 - Malaria transmission model with dual pathways (NEW!)
    - Based on Akowe et al. (2025) BMC Infectious Diseases 25:322
    - **First fully parametric model** demonstrating new parameter system
    - 24 parameters (19 CONSTANT + 5 EXPRESSION types)
    - 10 compartments: 7 human (SH, VH, EH1, EH2, IH, TH, RH) + 3 mosquito (SM, EM, IM)
    - Dual transmission: Vector-borne (mosquito) + Non-vector (blood transfusion, congenital)
    - Vaccination dynamics with waning immunity
    - Treatment and recovery pathways
    - See `MALARIA_MODEL_DOCUMENTATION.md` for complete details

  - **`covid.seirmodel`** - COVID-19 age-stratified model (3 age groups: 0-17, 18-64, 65+)
    - Based on published research (Tuite et al., 2020)
    - Uses **numeric values** (legacy style)
    - No natural death rates (follows paper methodology - only COVID deaths in ICU)
    - Age-specific transmission, hospitalization, and ICU mortality rates

  - **`HIV.seirmodel`** - HIV transmission model with sexual behavior stratification
    - Based on published research (Espitia et al., 2022)
    - Uses **numeric values** (legacy style)
    - 3 sexual behavior groups: Homosexual Men, Women, Heterosexual Men
    - Natural death rates for all compartments + AIDS-induced deaths to HIV Deaths compartment
    - Complex transmission patterns including bisexual contacts

  - **`covid_*.seirmodel`** - Auto-generated age-specific models from DynamicDiagramGenerator
