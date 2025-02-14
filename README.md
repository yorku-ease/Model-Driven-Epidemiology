# **SEIR Model Implementation using Eclipse EMF & Java**
### **A Compartmental SEIR Model with Dynamic Equation Generation**

---

## **📝 Project Overview**
This project is an **SEIR (Susceptible-Exposed-Infectious-Recovered) compartmental model**, implemented using the **Eclipse Modeling Framework (EMF) and Java**.

### **✨ Features**
✅ Define an **SEIR meta-model** using **Ecore in Eclipse EMF**.  
✅ Automatically **generate differential equations** for compartment transitions.  
✅ Allow **users to modify compartments and flow rates** dynamically.  
✅ Enable easy **equation extraction for simulations or further analysis**.

### **📂 Project Structure**
```
SEIRModel/
│── model/
│   ├── seirmodel.ecore       # The Ecore definition of the SEIR model
│   ├── seirmodel.genmodel    # The generator model
│── src/
│   ├── seir/equationgenerator/
│       ├── SEIREquationGenerator.java  # Java code for extracting equations
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
| **Java JDK** | 11+ | [OpenJDK](https://adoptium.net/) |
| **Eclipse IDE** | Latest | [Eclipse Download](https://www.eclipse.org/downloads/) |
| **EMF Modeling Tools** | Installed via Eclipse | `Help` → `Eclipse Marketplace` |

---

## **📌 Step 2: Clone & Open the Project**
### **🔹 Clone the Repository**
Run the following command to clone the GitHub repository:
```bash
git clone https://github.com/yorku-ease/Model-Driven-Epidemiology.git
```

### **🔹 Open the Project in Eclipse**
1. Open **Eclipse**.
2. Go to `File` → `Import...` → `Existing Projects into Workspace`.
3. Select the **`SEIRModel`** project.
4. Click **Finish**.

---

## **📌 Step 3: Run the SEIR Model**
### **🔹 Run the SEIR Equation Generator**
1. **Open `SEIREquationGenerator.java`**.
2. **Right-click the file** → `Run As` → `Java Application`.
3. The system **automatically extracts equations** and **saves them in** `SEIR_Equations.txt`.

#### **🔹 Sample Expected Console Output**
```plaintext
🌟 Generated SEIR Model Equations:
dS/dt = - 0.002 * S - 0.001 * S
dE_N/dt = 0.001 * S - 0.4 * E_N
dE_I/dt = 0.002 * S - 0.5 * E_I
dI_S/dt = 0.5 * E_I + 0.4 * E_N - 0.2 * I_S
dI_A/dt = 0.5 * E_I + 0.4 * E_N - 0.25 * I_A
dR/dt = 0.2 * I_S + 0.25 * I_A
🌟 Equations saved to SEIR_Equations.txt
```

### **🔹 Verify the Output**
1. Open **`SEIR_Equations.txt`**.
2. **Check that the generated equations match expectations**.

---

# **📌 Troubleshooting**
### **🚨 Common Errors & Fixes**
| Error | Solution |
|-------|----------|
| `PackageNotFoundException: http://example.com/seirmodel` | Run `SEIREquationGenerator.java` to register the model. |
| `Exception in thread "main" java.io.FileNotFoundException` | Ensure **`My.seirmodel`** exists in the project root. |
| `NoClassDefFoundError: org/eclipse/emf/ecore/resource` | **Add EMF JARs** to the classpath in `Java Build Path`. |

---
