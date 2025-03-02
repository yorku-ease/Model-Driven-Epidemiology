# **SEIR Model Implementation using Eclipse EMF & Java**
### **A Compartmental SEIR Model with Dynamic Equation Generation**

---

## **📝 Project Overview**
This project is an **SEIR (Susceptible-Exposed-Infectious-Recovered) compartmental model**, implemented using the **Eclipse Modeling Framework (EMF) and Java 17**.

### **✨ Features**
✅ Define an **SEIR meta-model** using **Ecore in Eclipse EMF**.  
✅ Automatically **generate differential equations** for compartment transitions.  
✅ Allow **users to modify compartments and flow rates** dynamically.  
✅ Enable easy **equation extraction for simulations or further analysis**.  
✅ Uses **Eclipse Target Definitions** to manage dependencies (no Maven or Tycho required).

### **📂 Project Structure**
```
SEIR/
│── org.seir.targetplatform/  # Target definition for dependencies
│   ├── seir.target           # Eclipse Target Platform configuration
│── SEIRModel/                # Main SEIR Model implementation
│   ├── seirmodel.ecore       # The Ecore definition of the SEIR model
│   ├── seirmodel.genmodel    # The generator model
│── SEIRModel.editor/         # Eclipse Editor Plugin
│── SEIR_Equations.txt        # Output file containing generated equations
│── README.md                 # Project documentation
```

---

# **🛠️ Setup & Installation**
This guide will walk you through **how to set up and run the project step by step**.

## **📌 Step 1: Install Prerequisites**
### **🔹 Required Software**
| Software | Version | Download Link |
|----------|---------|--------------|
| **Java JDK** | 17+ | [OpenJDK](https://adoptium.net/) |
| **Eclipse IDE** | Latest | [Eclipse Download](https://www.eclipse.org/downloads/) |
| **EMF Modeling Tools** | Installed via Eclipse | `Help` → `Eclipse Marketplace` |

---

## **📌 Step 2: Open the Eclipse Workspace**
### **🔹 Open the SEIR Project as a Workspace**
1. Open **Eclipse**.
2. When prompted for a workspace, **choose the `SEIR/` folder**.
3. Eclipse will load all projects in this workspace, including:
   - `SEIRModel`
   - `SEIRModel.editor`
   - `SEIRModel.edit`
   - `SEIRModel.tests`

---

## **📌 Step 3: Configure Eclipse Target Platform**
> **Eclipse Target Platform ensures that all required dependencies are properly resolved.**
### **🔹 Setup Target Platform**
1. Open the file `org.seir.targetplatform/seir.target`.
2. Click **Set as Active Target Platform**.
3. Wait for dependencies to resolve.

✅ **Now all required Eclipse dependencies should be available.**  
You **do not need Maven or Tycho**, as all dependencies are managed by the **target definition**.

---

## **📌 Step 4: Run the SEIR Model Editor**
1. **Open `SEIRModel.editor` in Eclipse**.
2. **Right-click on `SEIRModel.editor`** → `Run As > Eclipse Application`.
3. **A new Eclipse instance will open**. This is the runtime workspace where you can create and modify SEIR models.

---

## **📌 Step 5: Add SEIR Model in the New Eclipse Instance**
1. **In the new Eclipse instance**, go to `File > Import > Existing Projects into Workspace`.
2. Select the **`SEIRModel`** project.
3. Click **Finish**.

Now, the `SEIRModel` project is available in the new Eclipse instance, allowing you to modify the SEIR model.

---

## **📌 Step 6: Create and Modify a SEIR Model**
1. **In the new Eclipse instance, go to** `File > New > Other…`.
2. **Search for `"SEIR Model"`** and **create a new model**.
3. **Save the file as** `My.seirmodel`.

### **🛠️ What is `My.seirmodel`?**
- This file **stores the SEIR model definition** in a structured format.
- It **contains compartments** (e.g., **S, E, I, R**) and **flows** between them.

### **🔹 Modify the Model**
1. **Double-click `My.seirmodel`** to open it in the editor.
2. **Right-click** → `New Child` → Add **compartments** (Susceptible, Exposed, Infectious, etc.).
3. **Right-click** on a compartment → `New Child` → Add **Flows**.
4. **Set the target of the flow** and define the **flow rate**.

### **🔹 Save and Validate**
1. **Click `File > Save`** (`Ctrl+S`).
2. **Right-click `My.seirmodel`** → `Validate`.
3. If validation **fails**, ensure all compartments and flows are correctly linked.

---

## **📌 Step 7: Generate Equations using `SEIREquationGenerator.java`**
1. **Switch back to the first Eclipse instance** (your development workspace).
2. **Locate `SEIREquationGenerator.java`**:
   ```
   src/seir/equationgenerator/SEIREquationGenerator.java
   ```
3. **Right-click** → `Run As > Java Application`.

🚀 **This will extract the SEIR model equations and print them to the console.**  
✅ The equations will also be saved in `SEIR_Equations.txt`.


---

# **📌 Troubleshooting**
| Error | Solution |
|-------|----------|
| `Missing dependencies` | Make sure **seir.target** is **set as the active target platform**. |
| `PackageNotFoundException: http://example.com/seirmodel` | Run `SEIREquationGenerator.java` to register the model. |
| `Exception in thread "main" java.io.FileNotFoundException` | Ensure **`My.seirmodel`** exists in the project root. |
| `NoClassDefFoundError: org/eclipse/emf/ecore/resource` | **Set the target definition** and restart Eclipse. |

---

## **🚀 Conclusion**
This guide ensures **anyone can successfully set up, run, and test** your SEIR Model implementation. By **using an Eclipse Target Definition instead of Maven/Tycho**, you simplify dependency management and make it easier for new users to get started.

---
