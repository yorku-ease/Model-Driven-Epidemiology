# Eclipse Compartmental Model Implementation Guide

## Overview

This guide shows you how to create epidemiological models using Eclipse with the SEIR modeling framework. Unlike direct XML editing, Eclipse provides a visual modeling environment using EMF (Eclipse Modeling Framework) and Sirius diagrams.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Getting Started](#getting-started)
3. [Creating Your First Model](#creating-your-first-model)
4. [Working with the Tree Editor](#working-with-the-tree-editor)
5. [Working with Sirius Diagrams](#working-with-sirius-diagrams)
6. [Building a Simple Flu Model (Step-by-Step)](#building-a-simple-flu-model-step-by-step)
7. [Building an Age-Stratified Model](#building-an-age-stratified-model)
8. [Tips and Best Practices](#tips-and-best-practices)

---

## Prerequisites

### Required Eclipse Components

You need Eclipse with the following installed:

1. **Eclipse Modeling Tools** (recommended distribution)
2. **EMF (Eclipse Modeling Framework)**
3. **Sirius** (for graphical diagrams)

### Your Workspace Setup

Your workspace should contain:

- **CompartmentalModel**: Core metamodel project (contains `.ecore` and `.genmodel`)
- **CompartmentalModel.edit**: Model editing support
- **CompartmentalModel.editor**: Tree-based editor
- **CompartmentalModel.design**: Sirius diagram definitions (`.odesign` file)

---

## Getting Started

### Understanding Eclipse Modeling Components

**EMF Tree Editor**: Text-based hierarchical view
- Edit properties directly
- Add/remove model elements
- See complete model structure
- Good for precise edits

**Sirius Diagram Editor**: Visual graphical view
- Drag-and-drop compartments
- Draw flows between compartments
- Visual representation of your model
- Good for understanding model structure

**Model Files**:
- `.compartmentalmodel`: Your actual model data (XML format)
- `.aird`: Sirius representation file (diagram layout)

---

## Creating Your First Model

### Step 1: Create a New Model File

1. **Right-click** on the `CompartmentalModel` project in Project Explorer
2. Select **New → Other...**
3. Expand **Example EMF Model Creation Wizards**
4. Select **Seirmodel Model**
5. Click **Next**

6. **Enter file name**: e.g., `my_flu_model.compartmentalmodel`
7. Click **Next**

8. **Select Model Object**: Choose **CompartmentalModel** (this is the root element)
9. Click **Finish**

### Step 2: Choose Your Editor

After creating the file, Eclipse will ask which editor to use:

**Option 1: Tree Editor** (recommended for beginners)
- Opens `.compartmentalmodel` file directly
- Shows hierarchical tree structure
- Use for detailed property editing

**Option 2: Sirius Diagram**
- Right-click `.compartmentalmodel` → **New → Representations → Diagram**
- Select **Diagram** viewpoint
- Use for visual modeling

You can use BOTH editors on the same model file!

---

## Working with the Tree Editor

The tree editor shows your model as a hierarchical structure.

### Opening Tree Editor

1. **Double-click** your `.compartmentalmodel` file in Project Explorer
2. The model opens showing the root `CompartmentalModel` element

### Tree Editor Layout

```
▼ platform:/resource/.../my_flu_model.compartmentalmodel
  ▼ CompartmentalModel
    ▶ Compartments
    ▶ Birth Sources
    ▶ Death Sinks
    ▶ Groups
    ▶ Products
    □ Total Population: 0
    □ Global Birth Rate: 0.0
    □ Global Death Rate: 0.0
```

### Adding Elements

#### Adding a Compartment

1. **Right-click** on `CompartmentalModel`
2. Select **New Child → Compartments**
3. A new compartment appears in the tree
4. **Click** on the compartment to select it
5. In the **Properties view** (bottom panel), set:
   - **Primary Name**: `Susceptible`
   - **Population**: `99000`
   - **Secondary Name**: (leave empty for now)
   - **Product**: (leave empty for now)

#### Adding a Flow to a Compartment

1. **Expand** the compartment in the tree (click ▶)
2. **Right-click** on the compartment
3. Select **New Child → Outgoing Flows**
4. Choose flow type:
   - **Rate Flow**: For progression/recovery
   - **Contact Flow**: For transmission

**For a Rate Flow:**
1. Select the newly created `RateFlow` in the tree
2. In **Properties view**, set:
   - **Rate**: `0.2` (e.g., 1/5 days = recovery in 5 days)
   - **Description**: `Recovery from infection`
   - **Target**: Click **...** button and select target compartment

**For a Contact Flow:**
1. Select the newly created `ContactFlow` in the tree
2. In **Properties view**, set:
   - **Contact Rate**: `0.000005`
   - **Contact Compartment**: Click **...** button, select infectious compartment
   - **Description**: `Transmission through contact`
   - **Target**: Click **...** button, select target compartment

#### Adding Birth Sources

1. **Right-click** on `CompartmentalModel`
2. Select **New Child → Birth Sources**
3. In **Properties view**, set:
   - **Name**: `Natural births`
   - **Rate**: `0.00003`
   - **Target Compartment**: Click **...** button, select compartment (usually Susceptible)
   - **Target Stratum**: (leave empty if not using groups)
   - **Fixed Rate**: `false`

#### Adding Death Sinks

1. **Right-click** on `CompartmentalModel`
2. Select **New Child → Death Sinks**
3. In **Properties view**, set:
   - **Name**: `Natural death from Susceptible`
   - **Rate**: `0.00002`
   - **Source Compartment**: Click **...** button, select source compartment
   - **Source Stratum**: (leave empty if not using groups)

#### Adding Groups (for stratification)

1. **Right-click** on `CompartmentalModel`
2. Select **New Child → Groups**
3. In **Properties view**, set:
   - **Name**: `AgeGroup`
   - **Description**: `Age-based stratification`
4. **Right-click** on the new `Group`
5. Select **New Child → Values**
6. **Double-click** on the value in the tree to edit
7. Enter value name: `Children`, `Adults`, `Elderly`, etc.
8. Repeat for each value in the group

#### Adding Products (combine groups)

1. **Right-click** on `CompartmentalModel`
2. Select **New Child → Products**
3. In **Properties view**, set:
   - **Name**: `AgeStratification`
   - **Description**: `Age-based compartment stratification`
   - **Groups**: Click **...** button, select groups to include

#### Linking Compartments to Products

1. **Select** a compartment in the tree
2. In **Properties view**, find **Product**
3. Click **...** button
4. Select the product you created
5. The compartment is now stratified!

#### Adding Stratum-Specific Rates

When you have compartments with products, you can specify different rates for each stratum:

1. **Expand** a flow (RateFlow or ContactFlow) in the tree
2. **Right-click** on the flow
3. Select **New Child → Stratum Specific Rates**
4. In **Properties view**, set:
   - **Stratum**: Enter exact stratum name (e.g., `Children`)
   - **Rate**: Enter rate for this stratum
   - **Multiplier**: (optional, default 1.0)
5. Repeat for each stratum in your product

### Properties View Tips

**Location**: Properties view is typically at the bottom of Eclipse
- If not visible: **Window → Show View → Properties**

**Editing Values**:
- **Text fields**: Type directly
- **Numeric fields**: Type numbers (use decimals with `.`)
- **Boolean fields**: Check/uncheck boxes
- **Reference fields** (Target, Product, Groups): Click **...** button to browse

**Saving**:
- **Ctrl+S** or **File → Save**
- Red asterisk (*) on file tab indicates unsaved changes

---

## Working with Sirius Diagrams

Sirius provides a visual, drag-and-drop interface for modeling.

### Creating a Diagram

1. **Right-click** your `.compartmentalmodel` file
2. Select **New → Representation**
3. Choose **Diagram** from the viewpoint
4. Enter diagram name (e.g., `Flu Model Diagram`)
5. Click **OK**

The diagram editor opens with a blank canvas.

### Diagram Layout

**Palette** (right side): Tools for creating elements
**Canvas** (center): Your visual model
**Properties** (bottom): Selected element properties
**Outline** (usually right): Minimap of diagram

### Adding Elements via Diagram

#### Adding Compartments

1. In **Palette**, click **Compartment Node**
2. **Click** on the canvas where you want to place it
3. A compartment box appears
4. **Double-click** the box to edit the name
   - Or use **Properties view** to set Primary/Secondary Name

**Compartment Colors**: Automatically colored by first letter:
- **S** (Susceptible): Blue
- **E** (Exposed): Light orange
- **I** (Infectious): Dark red
- **R** (Recovered): Dark green
- **H** (Hospitalized): Purple
- **T** (Treated): Yellow
- etc.

#### Adding Flows Between Compartments

**Option 1: Using Palette**
1. In **Palette**, click **Flow Edge**
2. **Click** on source compartment
3. **Click** on target compartment
4. An arrow appears connecting them
5. **Click** on the arrow to select it
6. In **Properties view**, set:
   - Change type to **Rate Flow** or **Contact Flow** if needed
   - Set **Rate** or **Contact Rate**
   - Set **Description**

**Option 2: Using Context Menu**
1. **Right-click** on source compartment
2. Select **New → Flow Edge**
3. Follow steps above

#### Setting Flow Properties

After creating a flow edge:

1. **Click** the arrow/edge to select it
2. **Properties view** shows flow properties
3. For **RateFlow**:
   - Set **Rate**: numeric value
   - Set **Description**: explain the flow
4. For **ContactFlow**:
   - Set **Contact Rate**: transmission probability
   - Set **Contact Compartment**: click **...**, choose infectious compartment
   - Set **Description**

#### Adding Birth Sources

1. In **Palette**, click **Birth Source Node**
2. **Click** on canvas to place it (appears as green circle)
3. In **Palette**, click **Birth Edge**
4. **Click** birth source node (green circle)
5. **Click** target compartment
6. Select the birth source node
7. In **Properties view**, set:
   - **Name**: `Natural births`
   - **Rate**: `0.00003`
   - **Target Stratum**: (if using groups)

#### Adding Death Sinks

1. In **Palette**, click **Death Sink Node**
2. **Click** on canvas to place it (appears as red circle)
3. In **Palette**, click **Death Edge**
4. **Click** source compartment
5. **Click** death sink node (red circle)
6. Select the death sink node
7. In **Properties view**, set:
   - **Name**: `Natural death`
   - **Rate**: `0.00002`
   - **Source Stratum**: (if using groups)

### Diagram Organization Tips

**Arranging Elements**:
- **Drag** elements to reposition
- Use **Arrange All** (right-click on canvas → Format → Arrange All)

**Zooming**:
- **Ctrl + Mouse Wheel**: Zoom in/out
- **View → Zoom**: Set specific zoom level

**Auto-Layout**:
- **Right-click** on canvas
- **Format → Arrange All**: Automatic layout
- **Format → Arrange Selection**: Layout selected elements

**Saving Diagram**:
- Diagrams are saved in `.aird` file
- **Ctrl+S** saves both diagram and model

---

## Building a Simple Flu Model (Step-by-Step)

Let's create a basic SEIR flu model: Susceptible → Exposed → Infectious → Recovered

### Using Tree Editor

1. **Create new model**: `flu_simple.compartmentalmodel`
2. Open in **Tree Editor**

3. **Set global parameters**:
   - Click on `CompartmentalModel` root
   - In Properties:
     - Total Population: `100000`
     - Global Birth Rate: `0.00003`
     - Global Death Rate: `0.00002`

4. **Add Susceptible compartment**:
   - Right-click `CompartmentalModel` → **New Child → Compartments**
   - Properties:
     - Primary Name: `Susceptible`
     - Population: `99000`

5. **Add Exposed compartment**:
   - Right-click `CompartmentalModel` → **New Child → Compartments**
   - Properties:
     - Primary Name: `Exposed`
     - Population: `500`

6. **Add Infectious compartment**:
   - Right-click `CompartmentalModel` → **New Child → Compartments**
   - Properties:
     - Primary Name: `Infectious`
     - Population: `300`

7. **Add Recovered compartment**:
   - Right-click `CompartmentalModel` → **New Child → Compartments**
   - Properties:
     - Primary Name: `Recovered`
     - Population: `200`

8. **Add transmission flow** (Susceptible → Exposed):
   - Expand `Susceptible` compartment in tree
   - Right-click `Susceptible` → **New Child → Outgoing Flows → Contact Flow**
   - Properties:
     - Contact Rate: `0.0000015`
     - Contact Compartment: Browse → Select `Infectious`
     - Description: `Transmission through contact`
     - Target: Browse → Select `Exposed`

9. **Add progression flow** (Exposed → Infectious):
   - Right-click `Exposed` → **New Child → Outgoing Flows → Rate Flow**
   - Properties:
     - Rate: `0.5` (2-day incubation)
     - Description: `Progression to infectious`
     - Target: Browse → Select `Infectious`

10. **Add recovery flow** (Infectious → Recovered):
    - Right-click `Infectious` → **New Child → Outgoing Flows → Rate Flow**
    - Properties:
      - Rate: `0.2` (5-day recovery)
      - Description: `Recovery from infection`
      - Target: Browse → Select `Recovered`

11. **Add birth source**:
    - Right-click `CompartmentalModel` → **New Child → Birth Sources**
    - Properties:
      - Name: `Natural Birth`
      - Rate: `0.00003`
      - Target Compartment: Browse → Select `Susceptible`

12. **Add death sinks** (one for each compartment):
    - Right-click `CompartmentalModel` → **New Child → Death Sinks** (repeat 4 times)
    - For Susceptible:
      - Name: `Natural Death (Susceptible)`
      - Rate: `0.00002`
      - Source Compartment: Browse → Select `Susceptible`
    - Repeat for Exposed, Infectious, Recovered

13. **Save**: **Ctrl+S**

Your simple SEIR model is complete!

### Using Sirius Diagram

1. **Right-click** `flu_simple.compartmentalmodel`
2. **New → Representation → Diagram**
3. Name: `Flu Diagram`, click **OK**

4. **Add compartments**:
   - Click **Compartment Node** in Palette
   - Click on canvas 4 times to create 4 compartments
   - Double-click each to name: `Susceptible`, `Exposed`, `Infectious`, `Recovered`
   - Arrange left to right

5. **Add flows**:
   - Click **Flow Edge** in Palette
   - Click `Susceptible`, then `Exposed` (contact flow)
   - Click `Exposed`, then `Infectious` (rate flow)
   - Click `Infectious`, then `Recovered` (rate flow)

6. **Set flow properties**:
   - Click first arrow (Susceptible → Exposed)
   - Properties:
     - Change to **Contact Flow** in XSI Type dropdown
     - Contact Rate: `0.0000015`
     - Contact Compartment: Browse → `Infectious`

   - Click second arrow (Exposed → Infectious)
   - Properties:
     - Ensure **Rate Flow** type
     - Rate: `0.5`

   - Click third arrow (Infectious → Recovered)
   - Properties:
     - Rate: `0.2`

7. **Add birth source**:
   - Click **Birth Source Node** in Palette
   - Click on canvas (left of Susceptible)
   - Click **Birth Edge** in Palette
   - Click birth source, then Susceptible
   - Select birth source, set Properties:
     - Name: `Natural Birth`
     - Rate: `0.00003`

8. **Add death sinks**:
   - Click **Death Sink Node** 4 times on canvas
   - Place one below each compartment
   - Click **Death Edge** in Palette
   - Connect each compartment to its death sink
   - For each death sink, set rate: `0.00002`

9. **Save**: **Ctrl+S**

Your visual SEIR model is complete!

---

## Building an Age-Stratified Model

Now let's add age groups to make the model more realistic.

### Step 1: Define Groups

**Tree Editor**:
1. Right-click `CompartmentalModel` → **New Child → Groups**
2. Properties:
   - Name: `AgeGroup`
   - Description: `Age-based stratification`
3. Right-click the group → **New Child → Values** (3 times)
4. Edit each value:
   - Value 1: `Children`
   - Value 2: `Adults`
   - Value 3: `Elderly`

### Step 2: Create Product

1. Right-click `CompartmentalModel` → **New Child → Products**
2. Properties:
   - Name: `AgeStratification`
   - Description: `Age-based compartment stratification`
   - Groups: Click **...**, select `AgeGroup`, click **OK**

### Step 3: Link Compartments to Product

For each compartment you want to stratify:

1. Click on compartment in tree
2. Properties → **Product**: Click **...**
3. Select `AgeStratification`
4. Click **OK**

Do this for: Susceptible, Exposed, Infectious, Recovered

### Step 4: Add Stratum-Specific Rates

Now specify different rates for each age group.

**For Susceptible → Exposed (ContactFlow)**:

1. Expand `Susceptible` → Expand its `ContactFlow`
2. Right-click `ContactFlow` → **New Child → Stratum Specific Rates** (3 times)
3. First rate:
   - Stratum: `Children`
   - Rate: `0.000012`
   - Multiplier: `1.5`
4. Second rate:
   - Stratum: `Adults`
   - Rate: `0.0000015`
   - Multiplier: `1.0`
5. Third rate:
   - Stratum: `Elderly`
   - Rate: `0.000002`
   - Multiplier: `1.3`

**For Exposed → Infectious (RateFlow)**:

1. Expand `Exposed` → Expand its `RateFlow`
2. Right-click `RateFlow` → **New Child → Stratum Specific Rates** (3 times)
3. Set rates:
   - Children: Rate `0.6` (faster progression)
   - Adults: Rate `0.5` (standard)
   - Elderly: Rate `0.4` (slower progression)

**For Infectious → Recovered (RateFlow)**:

1. Expand `Infectious` → Expand its `RateFlow`
2. Add stratum-specific rates:
   - Children: Rate `0.25` (4 days)
   - Adults: Rate `0.2` (5 days)
   - Elderly: Rate `0.14` (7 days)

### Step 5: Stratify Birth/Death Sources

**Births** (all go to Children):

1. Click on `Birth Source`
2. Properties:
   - Target Stratum: `Children`

**Deaths** (different rates by age):

For each death sink:

1. Click death sink
2. Set **Source Stratum**: `Children`, `Adults`, or `Elderly`
3. Adjust **Rate** by age:
   - Children: `0.0001`
   - Adults: `0.0002`
   - Elderly: `0.001`

Create separate death sinks for each (compartment × stratum) combination.

---

## Tips and Best Practices

### Working Efficiently

**Use Both Editors**:
- **Diagram**: Visual overview, adding compartments, drawing flows
- **Tree Editor**: Precise property editing, stratum-specific rates, verification

**Switch Between Editors**:
- Both edit the same `.compartmentalmodel` file
- Changes in one are reflected in the other
- Save frequently with **Ctrl+S**

### Common Workflows

**Quick Modeling**:
1. Draw structure in diagram (compartments + flows)
2. Switch to tree editor
3. Fill in precise values (rates, populations, etc.)

**Detailed Configuration**:
1. Create groups/products in tree editor
2. Link compartments to products
3. Add stratum-specific rates in tree editor
4. View result in diagram

### Properties View Shortcuts

**Browsing References**:
- **...** button: Opens selection dialog
- **X** button: Clears reference
- Dropdown: Sometimes available for quick selection

**Multi-line Text** (descriptions):
- Press **Ctrl+Enter** for new line in text fields

### Validation

**Check Your Model**:
1. Look for error markers (red X) in tree editor
2. Common errors:
   - Missing required fields (Target, Rate, etc.)
   - Invalid references
   - Duplicate names

**Error Markers**:
- Appear on problematic elements in tree
- Hover for error message
- Fix in Properties view

### Organizing Your Diagram

**Layer Elements**:
- Source compartments (left)
- Intermediate compartments (middle)
- Terminal compartments (right)
- Birth sources (top left)
- Death sinks (bottom)

**Flow Routing**:
- Drag flow midpoint to adjust curve
- Avoid crossing flows when possible
- Use **Format → Arrange All** for auto-layout

**Visual Clarity**:
- Keep similar compartments near each other
- Use consistent spacing
- Group related elements

### Saving and Exporting

**Save Often**:
- **Ctrl+S**: Saves both model and diagram
- Unsaved changes: Red asterisk on file tab

**File Organization**:
- `.compartmentalmodel`: Model data (can be opened in XML editor too)
- `.aird`: Diagram representation (don't edit manually)
- Both files needed for diagram view

**Version Control**:
- Commit both `.compartmentalmodel` and `.aird` files
- `.aird` files can be large; they store layout info

### Troubleshooting

**Diagram Not Showing Elements**:
1. Check if viewpoint is enabled:
   - Right-click `.compartmentalmodel` → **Viewpoints Selection**
   - Ensure **MyViewpoint** is checked
2. Refresh: Right-click diagram → **Refresh**

**Elements Not Appearing in Palette**:
- Make sure you're in the correct layer
- Try closing and reopening diagram

**Properties View Empty**:
- Click on element in tree or diagram
- If still empty: **Window → Show View → Properties**

**Can't Set Reference (Target, Product, etc.)**:
- Ensure target element exists first
- Create target compartment/group before referencing it

**Changes Not Saving**:
- Check file permissions
- Close and reopen file
- Check Eclipse error log: **Window → Show View → Error Log**

---

## Example Workflow Summary

### For Beginners

1. **Create model** using wizard
2. **Open in Tree Editor**
3. **Add compartments** one by one
4. **Set properties** for each compartment
5. **Add flows** to compartments
6. **Set flow properties** (rates, targets)
7. **Add births/deaths**
8. **Save and validate**
9. (Optional) **Create diagram** for visualization

### For Visual Modelers

1. **Create model** using wizard
2. **Create diagram representation**
3. **Draw compartments** on canvas
4. **Draw flows** between compartments
5. **Add birth/death nodes**
6. **Switch to Tree Editor**
7. **Set precise properties** (rates, populations, etc.)
8. **Add stratification** (groups, products, stratum-specific rates)
9. **Switch back to diagram** to verify
10. **Save**

### For Complex Models (with Stratification)

1. **Start in Tree Editor**
2. **Define groups** first (age, gender, risk, etc.)
3. **Create products** (combinations of groups)
4. **Add compartments**
5. **Link compartments to products**
6. **Add flows**
7. **Add stratum-specific rates** to each flow
8. **Add stratified births/deaths**
9. **Create diagram** for visualization
10. **Validate and save**

---

## Next Steps

Once you've created your model:

1. **Validate**: Check for errors in tree editor
2. **Export**: Your `.compartmentalmodel` can be used with code generators or simulators
3. **Iterate**: Refine rates, add compartments, adjust stratification
4. **Document**: Use flow descriptions to document model logic

## Getting Help

- **Eclipse Help**: **Help → Help Contents → Sirius User Manual**
- **EMF Documentation**: **Help → Help Contents → EMF Documentation**
- **Example Models**: Look at `covid.compartmentalmodel` and `HIV.compartmentalmodel` in the project
- **Properties**: Hover over properties in Properties view for tooltips

---

Good luck with your modeling in Eclipse!
