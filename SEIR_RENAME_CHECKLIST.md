# SEIR to Compartmental Model Rename - Action Checklist

This is a comprehensive checklist for systematically renaming all "SEIR" and "seir" references to "Compartmental" or "compartmental" throughout the codebase.

## 1. Directory and Project Renaming

### Top-level directories (require directory moves)
- [ ] `SEIR/` → `CompartmentalModel/`
- [ ] `SEIR/SEIRModel/` → `CompartmentalModel/CompartmentalModel/`
- [ ] `SEIR/SEIRModel.design/` → `CompartmentalModel/CompartmentalModel.design/`
- [ ] `SEIR/SEIRModel.edit/` → `CompartmentalModel/CompartmentalModel.edit/`
- [ ] `SEIR/SEIRModel.editor/` → `CompartmentalModel/CompartmentalModel.editor/`
- [ ] `SEIR/SEIRModel.tests/` → `CompartmentalModel/CompartmentalModel.tests/`
- [ ] `SEIR/org.seir.targetplatform/` → `CompartmentalModel/org.compartmentalmodel.targetplatform/`

### Source package directories (require directory moves)
- [ ] `*/src/seirmodel/` → `*/src/compartmentalmodel/`
- [ ] `*/src/seir/` → `*/src/compartmentalmodel/`
- [ ] `*/bin/seirmodel/` → `*/bin/compartmentalmodel/` (will be regenerated)
- [ ] `*/bin/seir/` → `*/bin/compartmentalmodel/` (will be regenerated)

## 2. Core Metamodel and GenModel Files

- [ ] Rename `seir.ecore` → `compartmentalmodel.ecore`
- [ ] Rename `seir.genmodel` → `compartmentalmodel.genmodel`
- [ ] Rename `seir.aird` → `compartmentalmodel.aird`
- [ ] Rename `seir.target` → `compartmentalmodel.target`

### Edit seir.ecore to update:
- [ ] Change `nsURI="http://example.com/seirmodel"` → `nsURI="http://example.com/compartmentalmodel"`
- [ ] Change `nsPrefix="seir"` → `nsPrefix="compartmental"`
- [ ] Change package `name="seirmodel"` → `name="compartmentalmodel"`

## 3. Model Instance Files

### Rename all .seirmodel files
- [ ] `covid.seirmodel` → `covid.compartmentalmodel`
- [ ] `covid_0_17.seirmodel` → `covid_0_17.compartmentalmodel`
- [ ] `covid_18_64.seirmodel` → `covid_18_64.compartmentalmodel`
- [ ] `covid_65_.seirmodel` → `covid_65_.compartmentalmodel`
- [ ] `HIV.seirmodel` → `HIV.compartmentalmodel`
- [ ] `HIV_Women.seirmodel` → `HIV_Women.compartmentalmodel`
- [ ] `HIV_Homosexual_Men.seirmodel` → `HIV_Homosexual_Men.compartmentalmodel`
- [ ] `HIV_Heterosexual_Men.seirmodel` → `HIV_Heterosexual_Men.compartmentalmodel`
- [ ] `malaria.seirmodel` → `malaria.compartmentalmodel`
- [ ] `sample.seirmodel` → `sample.compartmentalmodel`

## 4. Eclipse Configuration Files

### .project files (update project name references)
- [ ] Update `SEIR/.project` - change name and description
- [ ] Update `SEIRModel/.project` - change name references
- [ ] Update `SEIRModel.design/.project` - change name references
- [ ] Update `SEIRModel.edit/.project` - change name references
- [ ] Update `SEIRModel.editor/.project` - change name references
- [ ] Update `SEIRModel.tests/.project` - change name references

### plugin.xml files

#### CompartmentalModel/plugin.xml:
- [ ] Change `uri="http://example.com/seirmodel"` → `"http://example.com/compartmentalmodel"`
- [ ] Change `class="seirmodel.SeirmodelPackage"` → `"compartmentalmodel.CompartmentalModelPackage"`
- [ ] Change `genModel="seir.genmodel"` → `"compartmentalmodel.genmodel"`

#### CompartmentalModel.editor/plugin.xml:
- [ ] Change all `seirmodel.presentation.Seirmodel*` → `compartmentalmodel.presentation.CompartmentalModel*`
- [ ] Change `id="seirmodel.presentation.SeirmodelModelWizardID"` → `"compartmentalmodel.presentation.CompartmentalModelWizardID"`
- [ ] Change `extensions="seirmodel"` → `"compartmentalmodel"`
- [ ] Update wizard and editor class references

#### CompartmentalModel.design/plugin.xml:
- [ ] Change `id="SEIRModel.design"` → `"CompartmentalModel.design"`
- [ ] Change `name="SEIRModel Design"` → `"CompartmentalModel Design"`
- [ ] Change path reference to odesign file

#### CompartmentalModel.edit/plugin.xml:
- [ ] Change `uri="http://example.com/seirmodel"` → `"http://example.com/compartmentalmodel"`
- [ ] Change `class="seirmodel.provider.SeirmodelItemProviderAdapterFactory"` → appropriate path

### MANIFEST.MF files

#### CompartmentalModel/META-INF/MANIFEST.MF:
- [ ] Change `Bundle-SymbolicName: SEIRModel;singleton:=true` → `CompartmentalModel;singleton:=true`
- [ ] Change `Automatic-Module-Name: SEIRModel` → `CompartmentalModel`
- [ ] Change `Export-Package: seirmodel, seirmodel.impl, seirmodel.util` → `compartmentalmodel, compartmentalmodel.impl, compartmentalmodel.util`

#### CompartmentalModel.editor/META-INF/MANIFEST.MF:
- [ ] Change `Bundle-SymbolicName: SEIRModel.editor;singleton:=true` → `CompartmentalModel.editor;singleton:=true`
- [ ] Change `Automatic-Module-Name: SEIRModel.editor` → `CompartmentalModel.editor`
- [ ] Change `Bundle-Activator: seirmodel.presentation.SeirEditorPlugin$Implementation` → `compartmentalmodel.presentation.CompartmentalEditorPlugin$Implementation`
- [ ] Change `Export-Package: seirmodel.presentation` → `compartmentalmodel.presentation`

#### CompartmentalModel.edit/META-INF/MANIFEST.MF:
- [ ] Change `Bundle-SymbolicName: SEIRModel.edit;singleton:=true` → `CompartmentalModel.edit;singleton:=true`
- [ ] Change `Automatic-Module-Name: SEIRModel.edit` → `CompartmentalModel.edit`
- [ ] Change `Bundle-Activator: seirmodel.provider.SeirEditPlugin$Implementation` → `compartmentalmodel.provider.CompartmentalEditPlugin$Implementation`
- [ ] Change `Export-Package: seirmodel.provider` → `compartmentalmodel.provider`

#### CompartmentalModel.design/META-INF/MANIFEST.MF:
- [ ] Change `Bundle-SymbolicName: SEIRModel.design;singleton:=true` → `CompartmentalModel.design;singleton:=true`
- [ ] Change `Automatic-Module-Name: SEIRModel.design` → `CompartmentalModel.design`

#### CompartmentalModel.tests/META-INF/MANIFEST.MF:
- [ ] Change `Bundle-SymbolicName: SEIRModel.tests;singleton:=true` → `CompartmentalModel.tests;singleton:=true`
- [ ] Change `Automatic-Module-Name: SEIRModel.tests` → `CompartmentalModel.tests`
- [ ] Change `Export-Package: seirmodel.tests` → `compartmentalmodel.tests`

#### org.compartmentalmodel.targetplatform/META-INF/MANIFEST.MF:
- [ ] Change `Bundle-SymbolicName: org.seir.targetplatform` → `org.compartmentalmodel.targetplatform`
- [ ] Change `Automatic-Module-Name: org.seir.targetplatform` → `org.compartmentalmodel.targetplatform`

### plugin.properties files

#### CompartmentalModel/plugin.properties:
- [ ] Change `pluginName = SEIRModel` → `pluginName = CompartmentalModel`

#### CompartmentalModel.editor/plugin.properties:
- [ ] Change `pluginName = SEIRModel Editor` → `pluginName = CompartmentalModel Editor`
- [ ] Change `_UI_SeirmodelEditor_menu = &Seirmodel Editor` → `_UI_CompartmentalModelEditor_menu = &CompartmentalModel Editor`
- [ ] Change `_UI_SeirmodelModelWizard_label = Seirmodel Model` → `_UI_CompartmentalModelWizard_label = CompartmentalModel`
- [ ] Change `_UI_SeirmodelModelWizard_description = Create a new Seirmodel model` → appropriate text
- [ ] Change `_UI_SeirmodelEditor_label = Seirmodel Model Editor` → `_UI_CompartmentalModelEditor_label = CompartmentalModel Editor`
- [ ] Change `_UI_SeirmodelEditorFilenameExtensions = seirmodel` → `_UI_CompartmentalModelEditorFilenameExtensions = compartmentalmodel`

#### CompartmentalModel.design/plugin.properties:
- [ ] Change `pluginName = SEIRModel.design` → `pluginName = CompartmentalModel.design`

## 5. Java Source Code Files - Class Names

**CRITICAL: Most of these are EMF-generated. Regenerate from .genmodel after metamodel changes.**

### Core Model Classes

#### CompartmentalModel/src/compartmentalmodel/:
- [ ] `SEIRModel.java` → `CompartmentalModel.java` (interface)
- [ ] `SeirmodelFactory.java` → `CompartmentalModelFactory.java` (interface)
- [ ] `SeirmodelPackage.java` → `CompartmentalModelPackage.java` (interface)

#### CompartmentalModel/src/compartmentalmodel/impl/:
- [ ] `SEIRModelImpl.java` → `CompartmentalModelImpl.java`
- [ ] `SeirmodelFactoryImpl.java` → `CompartmentalModelFactoryImpl.java`
- [ ] `SeirmodelPackageImpl.java` → `CompartmentalModelPackageImpl.java`

#### CompartmentalModel/src/compartmentalmodel/util/:
- [ ] `SeirmodelAdapterFactory.java` → `CompartmentalModelAdapterFactory.java`
- [ ] `SeirmodelSwitch.java` → `CompartmentalModelSwitch.java`

### Utility Classes

#### CompartmentalModel/src/compartmentalmodel/equationgenerator/:
- [ ] `SEIREquationGenerator.java` → `CompartmentalEquationGenerator.java`
  - [ ] Update class name
  - [ ] Update method: `loadSEIRModel()` → `loadCompartmentalModel()`
  - [ ] Update string literals: "SEIR" → "Compartmental"
  - [ ] Update file extension: ".seirmodel" → ".compartmentalmodel"
  - [ ] Update output messages

#### CompartmentalModel/src/compartmentalmodel/utilities/:
- [ ] `DynamicDiagramGenerator.java` (update references to file extensions and types)
  - [ ] Update ".seirmodel" → ".compartmentalmodel"
  - [ ] Update system messages with "SEIR" → "Compartmental"
  - [ ] Update class type references

### Editor Classes

#### CompartmentalModel.editor/src/compartmentalmodel/presentation/:
- [ ] `SeirmodelEditor.java` → `CompartmentalModelEditor.java`
  - [ ] Update class name and all internal references
  - [ ] Update "SEIRModel.editor" string references
  
- [ ] `SeirmodelModelWizard.java` → `CompartmentalModelWizard.java`
  - [ ] Update class name
  - [ ] Update package imports
  - [ ] Update factory/package references
  
- [ ] `SeirmodelActionBarContributor.java` → `CompartmentalModelActionBarContributor.java`
  - [ ] Update class name
  - [ ] Update menu ID references
  - [ ] Update toolbar manager IDs

- [ ] `SeirEditorPlugin.java` → `CompartmentalEditorPlugin.java`
  - [ ] Update class name
  - [ ] Update plugin ID references

### Edit Provider Classes

#### CompartmentalModel.edit/src/compartmentalmodel/provider/:
- [ ] `SeirmodelItemProviderAdapterFactory.java` → `CompartmentalModelItemProviderAdapterFactory.java`
  - [ ] Update class name and all references

- [ ] `SeirEditPlugin.java` → `CompartmentalEditPlugin.java`
  - [ ] Update class name

- [ ] `SEIRModelItemProvider.java` → `CompartmentalModelItemProvider.java`
  - [ ] Update class name

### Test Classes

#### CompartmentalModel.tests/src/compartmentalmodel/tests/:
- [ ] `SeirmodelTests.java` → `CompartmentalModelTests.java`
- [ ] `SeirmodelExample.java` → `CompartmentalModelExample.java`
- [ ] `SeirAllTests.java` → `CompartmentalAllTests.java`
- [ ] `SEIRModelTest.java` → `CompartmentalModelTest.java`

### Design Classes

#### CompartmentalModel.design/src/CompartmentalModel/design/:
- [ ] Update package references if any

## 6. Design and Diagram Files

- [ ] Rename `SEIRModel.odesign` → `CompartmentalModel.odesign`
- [ ] Update internal references in the odesign file (if it uses package names)
- [ ] Rename `seir.aird` → `compartmentalmodel.aird`
- [ ] Update references to odesign file path in .aird

## 7. Icon and Resource Files

These reference model names in their paths:
- [ ] Rename icon files `CreateSEIRModel_*.gif` → `CreateCompartmentalModel_*.gif`
- [ ] Rename icon files `SEIRModel.gif` → `CompartmentalModel.gif`
- [ ] Rename icon file `NewSeirmodel.gif` → `NewCompartmentalModel.gif`
- [ ] Rename icon file `SeirmodelModelFile.gif` → `CompartmentalModelFile.gif`

## 8. Documentation Files

### README.md
- [ ] Update directory structure section (SEIR/ → CompartmentalModel/)
- [ ] Update file references (seir.ecore → compartmentalmodel.ecore, etc.)
- [ ] Update class name references (SEIRModel → CompartmentalModel, etc.)
- [ ] Update XML namespace prefix examples (seir: → compartmental:)
- [ ] Update file extension examples (.seirmodel → .compartmentalmodel)
- [ ] Update command examples and paths

### ECLIPSE_MODELING_GUIDE.md
- [ ] Update any references to seir/SEIR/seirmodel

### SAMPLE_MODEL_EXPLANATION.md
- [ ] Update any references to seir/SEIR/seirmodel

## 9. Python Simulation Code

- [ ] Check `simulation.py` for any hardcoded references to ".seirmodel" extension
- [ ] Update file extension references if present

## 10. Build and Configuration

- [ ] Update any build scripts (.sh or .bat files)
  - [ ] `run_splitter.sh` - update references to .seirmodel extension
  - [ ] `run_splitter.bat` - update references to .seirmodel extension
- [ ] Update any Maven pom.xml files if present
- [ ] Update any Gradle build files if present

## 11. Code Generation and Regeneration

**IMPORTANT: After modifying the metamodel and genmodel:**

1. [ ] Open `compartmentalmodel.genmodel` in EMF Codegen
2. [ ] Regenerate Model code
3. [ ] Regenerate Edit code  
4. [ ] Regenerate Editor code
5. [ ] Regenerate Tests code
6. [ ] Review and manually update any handwritten code affected by regeneration

## 12. Model File Content Updates

For each .compartmentalmodel file:
- [ ] Update XML namespace prefix: `<seir:SEIRModel>` → `<compartmental:CompartmentalModel>`
- [ ] Update all nested type references: `xsi:type="seir:*"` → `xsi:type="compartmental:*"`
- [ ] Verify model still loads after namespace changes

## 13. Testing and Verification

After all changes:
- [ ] [ ] Rebuild all projects in Eclipse
- [ ] [ ] Fix any compilation errors
- [ ] [ ] Run unit tests
- [ ] [ ] Test model loading and saving
- [ ] [ ] Test equation generation
- [ ] [ ] Test diagram creation
- [ ] [ ] Verify existing models can be migrated/loaded
- [ ] [ ] Create new model files to verify extension and wizard work

## 14. Git and Version Control

- [ ] Create a new branch for this refactoring
- [ ] Make systematic commits per section
- [ ] Update .gitignore if needed
- [ ] Create pull request with detailed change summary

## 15. Documentation and Communication

- [ ] Update any internal documentation
- [ ] Create migration guide for users
- [ ] Document breaking changes
- [ ] Update any API documentation
- [ ] Consider backward compatibility strategy (if needed)

---

## Notes

- **EMF-Generated Code**: The majority of Java classes are EMF-generated from the .genmodel file. After updating the metamodel (.ecore) and genmodel, these should be regenerated.
- **File Extensions**: Changing from .seirmodel to .compartmentalmodel is a breaking change - consider a migration strategy.
- **Namespace Changes**: XML files will need to be updated to use the new namespace prefix.
- **Plugin IDs**: Bundle symbolic names affect Eclipse plugin loading - ensure consistency across all MANIFEST.MF and plugin.xml files.

