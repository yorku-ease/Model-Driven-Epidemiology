# SEIR to Compartmental Model - Comprehensive Search Results

## Executive Summary

This document provides a complete audit of all occurrences of "SEIR" and "seir" (case-insensitive) across the Model-Driven Epidemiology codebase. The search reveals approximately **1000+ references** across multiple categories, requiring systematic refactoring to rename to "Compartmental" or "CompartmentalModel".

---

## Key Statistics

- **Main Project Directories**: 7 (SEIR, SEIRModel, SEIRModel.edit, SEIRModel.editor, SEIRModel.design, SEIRModel.tests, org.seir.targetplatform)
- **Package Directories**: 13+ (seirmodel, seir.*)
- **Java Source Files**: 20+
- **Configuration Files**: 12+
- **Model Instance Files**: 10
- **Documentation Files**: 3+
- **Icon/Resource Files**: 40+

---

## 1. DIRECTORY STRUCTURE BREAKDOWN

### Root Directory Level
```
/home/fatemeh/Documents/york/Code/Model/Model-Driven-Epidemiology/SEIR/
└── All projects organized under SEIR/ root
```

### Project Directories (6 main projects + 1 target platform)
1. `SEIRModel/` - Core EMF model plugin
2. `SEIRModel.edit/` - EMF edit provider plugin
3. `SEIRModel.editor/` - EMF editor plugin
4. `SEIRModel.design/` - Sirius diagram design plugin
5. `SEIRModel.tests/` - Unit tests
6. `org.seir.targetplatform/` - Eclipse target platform
7. `.metadata/` - Eclipse workspace metadata

### Package Hierarchy (Source Code)
```
*/src/
  ├── seirmodel/               (core model package)
  │   ├── impl/               (implementations)
  │   ├── util/               (utilities)
  │   └── provider/           (edit providers)
  └── seir/                   (utilities/generators)
      ├── equationgenerator/
      └── utilities/

*/bin/                         (compiled classes - regenerated)
  ├── seirmodel/
  └── seir/
```

---

## 2. FILE NAMES CONTAINING "SEIR" OR "seir"

### Metamodel Files (4 files)
| Current Name | Category | Purpose |
|--|--|--|
| `seir.ecore` | Metamodel | Ecore metamodel definition with nsPrefix="seir" |
| `seir.genmodel` | Code Generation | EMF GenModel for code generation |
| `seir.aird` | Visualization | Sirius representation instance file |
| `seir.target` | Build | Eclipse target platform definition |

### Model Instance Files (10 files) - All use `.seirmodel` extension
- `covid.seirmodel` - COVID-19 age-stratified model
- `covid_0_17.seirmodel` - COVID age group 0-17
- `covid_18_64.seirmodel` - COVID age group 18-64
- `covid_65_.seirmodel` - COVID age group 65+
- `HIV.seirmodel` - HIV transmission model
- `HIV_Women.seirmodel` - HIV model for women
- `HIV_Homosexual_Men.seirmodel` - HIV model for homosexual men
- `HIV_Heterosexual_Men.seirmodel` - HIV model for heterosexual men
- `malaria.seirmodel` - Malaria transmission model
- `sample.seirmodel` - Sample model for testing

### Diagram Definition Files (1 file)
| Current Name | Category |
|--|--|
| `SEIRModel.odesign` | Sirius diagram definition |

---

## 3. JAVA PACKAGES

### Package Naming Convention
```
Lowercase: seirmodel, seir.*
Reason: Java package naming conventions (lowercase)
Currently: seirmodel is NOT a reversed domain name
```

### Complete Package List

**Core Model Packages:**
- `seirmodel` - Main model package
- `seirmodel.impl` - Implementation classes
- `seirmodel.util` - Utility classes
- `seirmodel.provider` - Edit provider adapter factory

**Supporting Packages:**
- `seirmodel.tests` - Unit tests
- `seirmodel.presentation` - Editor presentation

**Utility Packages:**
- `seir.equationgenerator` - Equation generation
- `seir.utilities` - Model utilities (splitter, diagram generator)

**Design Package:**
- `SEIRModel.design` - Sirius design package

---

## 4. CLASS NAMES

### EMF-Generated Classes (Will require regeneration)

**Main Model Class:**
- `SEIRModel` (interface) - Root container for compartmental models

**Factory & Package Classes:**
- `SeirmodelFactory` (interface)
- `SeirmodelPackage` (interface)
- `SeirmodelFactoryImpl` (implementation)
- `SeirmodelPackageImpl` (implementation)

**Utility Classes:**
- `SeirmodelAdapterFactory`
- `SeirmodelSwitch`

**Item Provider Classes:**
- `SeirmodelItemProviderAdapterFactory`
- `SEIRModelItemProvider`

**Editor Classes:**
- `SeirmodelEditor`
- `SeirmodelModelWizard`
- `SeirmodelActionBarContributor`
- `SeirEditorPlugin` (with inner class `Implementation`)
- `SeirEditPlugin` (with inner class `Implementation`)

**Test Classes:**
- `SeirmodelTests`
- `SeirmodelExample`
- `SeirAllTests`
- `SEIRModelTest`
- `ContactFlowTest`
- `BirthSourceTest`
- `CompartmentTest`
- `StratumSpecificRateTest`

**Utility/Generator Classes (Hand-written):**
- `SEIREquationGenerator`
- `DynamicDiagramGenerator`

**Design Classes:**
- `Services`
- `Activator`

---

## 5. CONFIGURATION & METADATA FILES

### plugin.xml Files (5 files)
Each contains references to seirmodel packages and class names

### MANIFEST.MF Files (6 files)
- Bundle symbolic names: `SEIRModel*`, `org.seir.targetplatform`
- Export packages: `seirmodel*`, `seir.*`
- Bundle activators: `Seirmodel*.presentation.SeirEditorPlugin`, `seirmodel.provider.SeirEditPlugin`

### plugin.properties Files (3 files)
Contains UI strings referencing SEIR/Seirmodel

### .project Files (6 files)
Eclipse project descriptors with project names

---

## 6. XML NAMESPACE REFERENCES

### In seir.ecore:
```xml
<ecore:EPackage 
    name="seirmodel" 
    nsURI="http://example.com/seirmodel" 
    nsPrefix="seir">
```

### In Model Files (.seirmodel):
```xml
<seir:SEIRModel totalPopulation="10000">
    <compartments ...>
        <outgoingFlows xsi:type="seir:ContactFlow" .../>
        <outgoingFlows xsi:type="seir:RateFlow" .../>
    </compartments>
</seir:SEIRModel>
```

All instances of `<seir:*` type references will need updating

---

## 7. RESOURCE FILES

### Icon Files (40+ files)
Pattern: `CreateSEIRModel_*.gif`, `SEIRModel.gif`, `NewSeirmodel.gif`, `SeirmodelModelFile.gif`

### Compiled Binary Files
All `.class` files in `bin/` directories (auto-regenerated after rename)

---

## 8. DOCUMENTATION

### Main Documentation Files (3)
1. **README.md** - Contains extensive SEIR references:
   - Directory structure examples
   - Class names (SEIRModel, SEIREquationGenerator)
   - File extensions (.seirmodel)
   - XML namespace examples
   - Code snippets with seir: prefix
   
2. **ECLIPSE_MODELING_GUIDE.md** - Development guide (may contain references)

3. **SAMPLE_MODEL_EXPLANATION.md** - Model explanation (may contain references)

### Inline Documentation
- Comments in Java files marked with `@generated seir`
- JavaDoc comments referencing SEIR concepts
- Method signatures with SEIR/Seirmodel types

---

## 9. CRITICAL CODE REFERENCES

### SEIREquationGenerator.java
**Location:** `SEIR/SEIRModel/src/seir/equationgenerator/SEIREquationGenerator.java`

Key references:
- Class name: `SEIREquationGenerator`
- File extension: `.seirmodel`
- System output messages: "SEIR Model Equations"
- Methods: `loadSEIRModel()`, `generateEquations(SEIRModel model)`
- String literals: "SEIR" used in output messages

### DynamicDiagramGenerator.java
**Location:** `SEIR/SEIRModel/src/seir/utilities/DynamicDiagramGenerator.java`

Key references:
- File extension: `.seirmodel`
- System messages: "Dynamic SEIR Model Splitter"
- Type references: `SEIRModel` class
- File handling: References to `.seirmodel` extension

### Editor Classes
All editor classes heavily reference:
- Package imports: `import seirmodel.*`
- Factory instances: `SeirmodelFactory`, `SeirmodelPackage`
- Adapter factory: `SeirmodelItemProviderAdapterFactory`
- Menu/toolbar IDs with "seirmodel" prefix

---

## 10. BACKWARD COMPATIBILITY CONSIDERATIONS

### Breaking Changes
1. **File Extension**: `.seirmodel` → `.compartmentalmodel`
   - All existing model files will need migration or remapping
   
2. **Namespace URI**: `http://example.com/seirmodel` → `http://example.com/compartmentalmodel`
   - Existing XML files will have mismatched namespaces
   
3. **Package Names**: `seirmodel` → `compartmentalmodel`
   - Import statements throughout codebase will break
   
4. **Bundle IDs**: `SEIRModel` → `CompartmentalModel`
   - Plugin loading will reference new names

### Required Mitigation
- Document migration path for existing models
- Consider namespace aliasing or transformation
- Provide conversion utilities or update scripts
- Create compatibility layer if needed

---

## 11. COMPREHENSIVE FILE COUNT BY CATEGORY

### Source Code Files
- Java source files: 20+
- Generated classes: 50+

### Configuration Files
- plugin.xml: 5
- MANIFEST.MF: 6
- plugin.properties: 3
- .project: 6

### Metamodel Files
- .ecore: 1
- .genmodel: 1
- .odesign: 1
- .aird: 1
- .target: 1

### Model Instance Files
- .seirmodel files: 10

### Documentation
- Markdown files: 3
- Inline documentation: Extensive

### Resources
- Icon files: 40+
- Binary class files: 100+ (regenerated)

---

## 12. RECOMMENDED RENAMING STRATEGY

### Phase 1: Metamodel Update (Low Impact)
1. Rename `seir.ecore` → `compartmentalmodel.ecore`
2. Update namespace properties within ecore file
3. Regenerate code from updated ecore

### Phase 2: Project Structure (High Impact)
1. Rename project directories
2. Update Eclipse project files
3. Update package directories (requires moving source)

### Phase 3: Configuration Updates (Medium Impact)
1. Update all plugin.xml files
2. Update all MANIFEST.MF files
3. Update plugin.properties

### Phase 4: Code Regeneration
1. Regenerate all EMF code from updated genmodel
2. Manually fix any broken handwritten code

### Phase 5: Manual Code Updates
1. Update non-EMF generated files
2. Update utility classes and generators
3. Fix string literals and messages

### Phase 6: Documentation & Testing
1. Update README and guides
2. Update model instance files
3. Test model loading, generation, and diagram creation

---

## 13. AUTOMATION POSSIBILITIES

### Can Be Automated
- Directory/file renaming (script-based)
- Bulk text replacement in source files
- EMF code regeneration
- XML namespace updates in model files

### Should Be Manual
- Careful review of namespace changes
- Testing of model loading
- Verification of diagram generation
- Review of generated code for errors

---

## 14. TIME ESTIMATES

| Activity | Complexity | Estimated Time |
|--|--|--|
| Planning & preparation | Low | 1-2 hours |
| Metamodel updates | Medium | 1-2 hours |
| Directory renaming | Low | 30 minutes |
| Project file updates | Medium | 1-2 hours |
| Configuration updates | Medium | 1-2 hours |
| Code regeneration | Low | 30 minutes |
| Manual code fixes | High | 2-4 hours |
| Documentation updates | Medium | 1-2 hours |
| Testing & verification | High | 2-3 hours |
| **Total Estimated** | | **11-18 hours** |

---

## 15. DELIVERABLES FROM THIS AUDIT

1. **Comprehensive Reference Audit** - This document
2. **Detailed Checklist** - See accompanying checklist document
3. **File Mappings** - Complete mapping of old→new names
4. **Risk Assessment** - Backward compatibility issues identified
5. **Implementation Guide** - Step-by-step procedure

---

## 16. NEXT STEPS

1. Review this audit with the team
2. Confirm naming convention (Compartmental vs CompartmentalModel)
3. Decide on file extension (.compartmentalmodel vs .cmodel)
4. Plan migration strategy for existing models
5. Create a detailed implementation timeline
6. Assign team members to different phases
7. Begin with Phase 1 (Metamodel update)
8. Execute phases in sequence with testing after each phase

---

## 17. QUESTIONS FOR CLARIFICATION

Before proceeding with the rename, clarify:

1. **Naming Choice**: Should it be "Compartmental" or "CompartmentalModel"?
2. **File Extension**: Keep `.compartmentalmodel` or use shorter `.cmodel`?
3. **Namespace**: Keep `http://example.com/compartmentalmodel` or update domain?
4. **Backward Compatibility**: Should we support auto-conversion of old .seirmodel files?
5. **Timeline**: Can this be done in a single sprint or split across multiple sprints?
6. **Branch Strategy**: Should this be done in a feature branch or directly on main?

---

## References

- **Codebase Root**: `/home/fatemeh/Documents/york/Code/Model/Model-Driven-Epidemiology/`
- **Current Branch**: `seir`
- **Main Branch**: `main`
- **Search Date**: 2025-11-05
- **Search Scope**: Entire codebase (SEIR/)

