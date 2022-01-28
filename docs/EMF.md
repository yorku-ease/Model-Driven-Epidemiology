# Loading a model

the metamodel is named `epidemiologyJan11` and the model is named `MyEpidemiologyJan11.epidemiologyJan11`

```java
// could be program argument for example, this is the path to your model to be loaded
String fn = "D:\\Downloads\\ObeoDesigner-Community-11.5-win32.win32.x86_64\\ObeoDesigner-Community\\runtime-EclipseApplication\\epi111\\MyEpidemiologyJan11.epidemiologyJan11";

// register an factory for XMI format
// your model is an XML file but has the metamodel extension (.epidemiologyJan11, not .xml)
// we simply map that extension to the XMI factory
Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
Map<String, Object> m = reg.getExtensionToFactoryMap();
m.put(
  "epidemiologyJan11",
  new XMIResourceFactoryImpl()
);

// link the metamodel to let the factory know which package the classes referred to in your model actually represent
ResourceSet resSet = new ResourceSetImpl();
resSet.getPackageRegistry().put(
  epidemiologyJan11.EpidemiologyJan11Package.eNS_URI,
  epidemiologyJan11.EpidemiologyJan11Package.eINSTANCE
);

// create a uri using the path to the model file and use it to load the resource
URI uri = URI.createFileURI(fn);
Resource resource = resSet.getResource(uri, true);

// our example resource has a single top level node of type Epidemic
Epidemic myEpi = (Epidemic) resource.getContents().get(0);

// then we can make use of the loaded object using a combination of EMF utilities and our defined Model classes
EList<PopulationDimension> dimensions = myEpi.getPopulationdimension();
```
