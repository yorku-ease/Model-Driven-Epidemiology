SEIR Model Editor (Graphical + Code)

This project is a graphical and textual editor for SEIR epidemiological models, based on Eclipse Modeling Framework (EMF) and Sirius. The SEIR model (Susceptible, Exposed, Infectious, Recovered) supports customization through a metamodel defined in seir.ecore, and users can define instances using the .seirmodel file format.

It supports:

Graphical editing (via Sirius diagrams)

Model definition and generation (via EMF)

Equation generation

Target platform resolution for dependency management

📁 Project Structure

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

🚀 Setup Instructions

1. Install Required Eclipse

Eclipse Modeling package

OR install features:

EMF SDK

Sirius (from update site)

2. Import Projects

Use File > Import > Existing Projects into Workspace, and import all 6+ projects listed above.

3. Setup Target Platform

Open seir.target in Eclipse

Click "Set as Active Target Platform"

This will resolve dependencies like EMF, Sirius, and required runtimes.

🧠 SEIR Metamodel

The SEIR model is defined in seir.ecore and includes:

SEIRModel (root)

Compartment (abstract, with name, population, and outgoingFlows)

Subtypes of Compartment:

Susceptible, Exposed, ExposedIsolated, ExposedNonIsolated, Infectious, InfectiousSymptomatic, InfectiousAsymptomatic, Recovered

Flow: has a rate, optional description, and a target reference

✏️ Creating and Editing Models

Textual

Right-click → New → Other → Example EMF Model Creation Wizards → SEIR Model

Save with .seirmodel extension

You can edit via the generated tree editor (SEIRModel.editor) or directly via XML

Graphical (Sirius)

Step-by-Step:

Open SEIRModel.odesign (inside SEIRModel.design/description/)

Ensure plugin.xml contains:

<extension point="org.eclipse.sirius.componentization">
  <component name="SEIRModel" id="SEIRModel.design" class="org.eclipse.sirius.business.api.componentization.ViewpointRegistry$ViewpointComponent">
    <viewpoints>
      <viewpoint path="/description/SEIRModel.odesign"/>
    </viewpoints>
  </component>
</extension>

Register viewpoint in MANIFEST.MF (bundle activator optional)

Run as Eclipse Application

In the runtime workspace:

Open .aird file

Right-click on .seirmodel → Viewpoints Selection → enable MyViewpoint

Right-click → New Representation → SEIRDiagram

Compartments will appear as nodes, and flows as arrows

Label Flows with Rates

In the .odesign, under FlowEdge → CenterLabelStyle, ensure:

Label Expression: aql:self.rate

Label Size: 12

If it's showing as self.rate literally, check that AQL interpreter is selected in Sirius preferences.

➕ Example: Sample.seirmodel

<seir:SEIRModel>
  <compartments xsi:type="seir:Susceptible" name="S" population="1000">
    <outgoingFlows rate="0.002" target="//@compartments.2"/>
  </compartments>
  <compartments xsi:type="seir:ExposedNonIsolated" name="E_N" population="10">
    <outgoingFlows rate="0.6" target="//@compartments.4"/>
  </compartments>
  <compartments xsi:type="seir:Recovered" name="R"/>
</seir:SEIRModel>

🧮 Equation Generation

Implemented in SEIREquationGenerator.java. Generates text equations from .seirmodel files.

To run:

Open .seirmodel file

Right-click or trigger the generator to output to SEIR_Equations.txt

Example output:

dS/dt = -0.002 * S

❓ Troubleshooting

Flows Not Showing in Diagram?

Check FlowEdge mapping:

Domain Class: seirmodel.Flow

Semantic Candidates: aql:self.compartments.outgoingFlows

Source Mapping: CompartmentNode

Target Finder Expression: aql:self.target

Label Showing self.rate Instead of Value?

Use AQL (not Acceleo) interpreter

Ensure Sirius Preferences > Interpreter uses AQL

Viewpoints Don’t Appear?

Make sure .odesign is registered correctly in plugin.xml

Open .aird and enable the viewpoint from Viewpoint Selection

📦 Exporting Diagrams

Use toolbar buttons to export as image (PNG/SVG)

Or right-click the canvas → Export as Image

📌 Requirements

Eclipse 2023-09 or later

Java 17+

Sirius 7.4.7 or later (configured via .target)

