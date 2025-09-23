# SEIR Model Editor (Graphical + Code)

This project is a graphical and textual editor for SEIR epidemiological models, based on Eclipse Modeling Framework (EMF) and Sirius. The SEIR model (Susceptible, Exposed, Infectious, Recovered) supports customization through a metamodel defined in `seir.ecore`, and users can define instances using the `.seirmodel` file format.

It supports:
- **Graphical editing** (via Sirius diagrams)
- **Model definition and generation** (via EMF)
- **Population stratification** with group products (age, location, risk, vaccination status, etc.)
- **Stratum-specific rates** for heterogeneous populations
- **Automatic equation generation** for stratified models
- **Python simulation** for numerical integration and CSV output
- **Automatic model splitting** utilities for large stratified models
- **Target platform resolution** for dependency management

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
- `SEIRModel` (root)
- `Compartment` (abstract, with `name`, `population`, and `outgoingFlows`)
- Subtypes of Compartment:
  - `Susceptible`, `Exposed`, `ExposedIsolated`, `ExposedNonIsolated`, `Infectious`, `InfectiousSymptomatic`, `InfectiousAsymptomatic`, `Recovered`
- **Flow Types**:
  - `RateFlow`: basic rate-based transitions (includes flows to death compartments for disease-induced mortality)
  - `ContactFlow`: transmission flows based on contact rates
  - `BirthSource`: population input flows with stratum-specific targeting
  - `DeathSink`: population output flows for natural mortality (background death rates)
- **Stratification Components**:
  - `Group`: defines population categories (e.g., age groups, locations)
  - `Product`: creates combinations of groups (Cartesian products)
  - `StratumSpecificRate`: allows different rates for each population segment

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

## ➕ Example: Basic Model
```xml
<seir:SEIRModel>
  <compartments xsi:type="seir:Susceptible" name="S" population="1000">
    <outgoingFlows rate="0.002" target="//@compartments.2"/>
  </compartments>
  <compartments xsi:type="seir:ExposedNonIsolated" name="E_N" population="10">
    <outgoingFlows rate="0.6" target="//@compartments.4"/>
  </compartments>
  <compartments xsi:type="seir:Recovered" name="R"/>
</seir:SEIRModel>
```

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
Implemented in `SEIREquationGenerator.java`. Generates text equations from `.seirmodel` files.

**To run:**
1. Navigate to `SEIRModel/src/seirmodel/SEIREquationGenerator.java`
2. Right-click → "Run As" → "Java Application"
3. Generated equations will be displayed in console and saved to output files

**For stratified models**, the generator automatically creates equations for each population segment:

Example output from COVID age-stratified model:
```
=== Age-Stratified SEIR Model ===
dSusceptible_0-17/dt = - (0.0000042 * Susceptible_0-17 * Infectious_0-17 / N)
dSusceptible_18-64/dt = - (0.000015 * Susceptible_18-64 * Infectious_18-64 / N)  
dSusceptible_65+/dt = - (0.0000104 * Susceptible_65+ * Infectious_65+ / N)

dInfectious_0-17/dt = + (0.0000042 * Susceptible_0-17 * Infectious_0-17 / N) - (0.1 * Infectious_0-17)
dInfectious_18-64/dt = + (0.000015 * Susceptible_18-64 * Infectious_18-64 / N) - (0.1 * Infectious_18-64)
dInfectious_65+/dt = + (0.0000104 * Susceptible_65+ * Infectious_65+ / N) - (0.1 * Infectious_65+)
```

The system supports any stratification type: age groups, geographic locations, risk levels, vaccination status, occupation, etc.

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
  - **`covid.seirmodel`** - COVID-19 age-stratified model (3 age groups: 0-17, 18-64, 65+)
    - Based on published research (Tuite et al., 2020)
    - No natural death rates (follows paper methodology - only COVID deaths in ICU)
    - Age-specific transmission, hospitalization, and ICU mortality rates
  - **`HIV.seirmodel`** - HIV transmission model with sexual behavior stratification
    - Based on published research (Espitia et al., 2022)
    - 3 sexual behavior groups: Homosexual Men, Women, Heterosexual Men
    - Natural death rates for all compartments + AIDS-induced deaths to HIV Deaths compartment
    - Complex transmission patterns including bisexual contacts
  - **`covid_*.seirmodel`** - Auto-generated age-specific models from DynamicDiagramGenerator
