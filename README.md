# SEIR Model Editor (Graphical + Code)

This project is a graphical and textual editor for SEIR epidemiological models, based on Eclipse Modeling Framework (EMF) and Sirius. The SEIR model (Susceptible, Exposed, Infectious, Recovered) supports customization through a metamodel defined in `seir.ecore`, and users can define instances using the `.seirmodel` file format.

It supports:
- **Graphical editing** (via Sirius diagrams)
- **Model definition and generation** (via EMF)
- **Population stratification** with group products (age, location, risk, vaccination status, etc.)
- **Stratum-specific rates** for heterogeneous populations
- **Automatic equation generation** for stratified models
- **Target platform resolution** for dependency management

---

## 📁 Project Structure

```
SEIRModel/
├── seir.ecore                 # Ecore metamodel definition
├── seir.genmodel              # EMF GenModel
├── seir.aird                  # Sirius representation instance
├── Sample.seirmodel           # Sample SEIR model instance
├── SEIR_Equations.txt         # Output from equation generation
├── org.seir.targetplatform/   # Target platform project
├── SEIRModel.design/          # Sirius graphical definition (.odesign)
├── SEIRModel.edit/            # EMF edit plugin
├── SEIRModel.editor/          # EMF editor plugin
├── SEIRModel.tests/           # Unit tests
└── SEIRModel/                 # Core model plugin
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
- `Flow`: has a `rate`, optional `description`, and a `target` reference
- **New Stratification Components**:
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
- Eclipse 2023-09 or later
- Java 17+
- Sirius 7.4.7 or later (configured via `.target`)
