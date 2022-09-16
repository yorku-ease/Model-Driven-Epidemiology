/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.EpidemicWrapper;
import epimodel.EpimodelFactory;
import epimodel.EpimodelPackage;

import epimodel.Flow;
import epimodel.FlowWrapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EpimodelPackageImpl extends EPackageImpl implements EpimodelPackage {
	
	public static List<EClass> eclasses = null;
	
    static public List<EPackage> getEpimodelPackages() {
	    final EPackage.Registry reg = EPackage.Registry.INSTANCE;
	    List<EPackage> allPackages = reg
	    		.values()
	    		.stream()
	    		.filter(pkg -> pkg instanceof EPackage)
	    		.map(pkg -> (EPackage) pkg)
	    		.collect(Collectors.toList());
	    List<EPackage> epimodelPackages = new ArrayList<>();
	    
	    do {
	    	int size = epimodelPackages.size();
		    for (int i = allPackages.size() - 1; i  >= 0; --i) {
		    	EPackage pkg = allPackages.get(i);
		    	if (EPkgRefersToAtLeastOnePkgOrEpimodel(pkg, epimodelPackages)) {
		    		epimodelPackages.add(pkg);
		    		allPackages.remove(i);
		    	}
		    }
		    if (epimodelPackages.size() == size)
		    	break;
	    } while (true);
		return epimodelPackages;
    }
	
    public static List<EClass> collectEClasses() {
    	if (eclasses != null)
    		return eclasses;
    	
		eclasses = new ArrayList<>();
	    List<EPackage> epimodelPackages = EpimodelPackageImpl.getEpimodelPackages();
	    
	    for (EPackage pkg : epimodelPackages) {
	    	System.out.println(pkg.getName());
	    	EList<EClassifier> eclassifiers = pkg.getEClassifiers();
	    	for (EClassifier classifier : eclassifiers) {
	    		if (!(classifier instanceof EClass)) {
		    		System.out.println("\tnon class " + classifier.getName() + "type = " + classifier.getClass());
		    		continue;
	    		}
    			EClass cl = (EClass) classifier;
    			eclasses.add(cl);
    			if (cl.isAbstract())
    				if (cl.isInterface())
    					System.out.println("\tinterface " + classifier.getName());
    				else
    					System.out.println("\tabstract class " + classifier.getName());
    			else
		    		System.out.println("\tclass " + classifier.getName());
    			
    			for (EAttribute a : cl.getEAllAttributes())
    				System.out.println("\t\tattribute " + a.getName());
    			for (EReference c : cl.getEAllContainments())
    				System.out.println("\t\tcontainement " + c.getName());
    			for (EReference r : cl.getEAllReferences())
    				System.out.println("\t\treference " + r.getName());
	    	}
	    }
		return eclasses;
	}
    
    public static boolean isModelType(EClass T) {
    	return EpimodelPackage.Literals.COMPARTMENT.isSuperTypeOf(T)
    			|| EpimodelPackage.Literals.FLOW.isSuperTypeOf(T)
    			|| EpimodelPackage.Literals.EPIDEMIC.isSuperTypeOf(T);
    }
    
    static boolean EPkgRefersToAtLeastOnePkgOrEpimodel(EPackage pkg, List<EPackage> pkgs) {
    	OutputStream output = new OutputStream() {
    	    StringBuilder sb = new StringBuilder();

    	    @Override
    	    public void write(int b) throws IOException {
    	        sb.append((char) b);
    	    }

    	    @Override
    	    public String toString() {
    	        return sb.toString();
    	    }
    	};
    	try {
			pkg.eResource().save(output, null); // pkg.ecore as string
		} catch (Exception e) { }
    	
    	List<String> pkgsStrToFindInXMI = pkgs
    			.stream()
    			.map(p -> p.getName() + "#")
    			.collect(Collectors.toList());
    	
    	String ecoreStr = output.toString();
    	return ecoreStr
    			.contains("http://www.example.org/epimodel") 
    			|| pkgsStrToFindInXMI
    				.stream()
    				.filter(uri -> ecoreStr.contains(uri))
    				.findFirst()
    				.isPresent();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass epidemicWrapperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass epidemicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compartmentWrapperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compartmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowWrapperEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see epimodel.EpimodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EpimodelPackageImpl() {
		super(eNS_URI, EpimodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link EpimodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @throws Exception 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated NOT
	 */
	public static EpimodelPackage init() {
		if (isInited)
			return (EpimodelPackage) EPackage.Registry.INSTANCE.getEPackage(EpimodelPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEpimodelPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EpimodelPackageImpl theEpimodelPackage = registeredEpimodelPackage instanceof EpimodelPackageImpl
				? (EpimodelPackageImpl) registeredEpimodelPackage
				: new EpimodelPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theEpimodelPackage.createPackageContents();

		// Initialize created meta-data
		theEpimodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEpimodelPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EpimodelPackage.eNS_URI, theEpimodelPackage);
		
		// Added Manually
		try {
			loadExtensions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return theEpimodelPackage;
	}
	
	public static void loadExtensions() throws Exception {
		
		{
			String env = "epimodelJars";
			String value = System.getenv(env);
			if (value == null || value.toLowerCase().equals("false"))
				return;
		}
		
		String env = "epimodelExtensionsFolder";
		String folder_path = System.getenv(env);
		
		if (folder_path == null)
			throw new Exception(
				"Required Environment Variable '" + env +
				"' not found, set the variable and restart the program");
		
		if (folder_path.endsWith("/") || folder_path.endsWith("\\"))
			folder_path = folder_path.substring(0, folder_path.length() - 1);

		File folder = new File(folder_path);
		
		if (!folder.exists() || folder.isFile())
			throw new Exception(
				"Environment Variable '" + env +
				"' does not point to an existing folder, update the variable and restart the program");
			
		File[] listOfFiles = folder.listFiles();
		
		for (File jar : listOfFiles)
			addEPackageToRegistry(folder_path + "/" + jar.getName());
	}
	
	static void addEPackageToRegistry(String jar) throws Exception {
        for (Class<?> c : loadClassesFromJar(jar))
        	if (c.getName().endsWith("Package"))
        		EPackage.Registry.INSTANCE.put(
            		(String) getStaticFieldByName(c, "eNS_URI"),
            		getStaticFieldByName(c, "eINSTANCE")
            	);
	}
	
	protected static List<Class<?>> loadClassesFromJar(String pathToJar) throws IOException, ClassNotFoundException {
		JarFile jarFile = new JarFile(pathToJar);
		Enumeration<JarEntry> entry = jarFile.entries();
		List<Class<?>> classes = new ArrayList<>();

		URL[] urls = { new URL("jar:file:" + pathToJar+"!/") };
		
		URLClassLoader cl = URLClassLoader.newInstance(urls, EpimodelPackageImpl.class.getClassLoader());

		while (entry.hasMoreElements()) {
		    JarEntry je = entry.nextElement();
		    if (je.isDirectory() || !je.getName().endsWith(".class"))
		        continue;
		    String className = je.getName().substring(0, je.getName().indexOf(".class")).replace('/', '.');
		    Class<?> c = cl.loadClass(className);
		    classes.add(c);
		}
		jarFile.close();
		return classes;
	}
	
	protected static Object getStaticFieldByName(Class<?> c, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Field field = c.getField(fieldName);
		field.setAccessible(true);
		return field.get(new Object());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEpidemicWrapper() {
		return epidemicWrapperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getEpidemicWrapper_Epidemic() {
		return (EReference) epidemicWrapperEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEpidemic() {
		return epidemicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEpidemic_Id() {
		return (EAttribute) epidemicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompartmentWrapper() {
		return compartmentWrapperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompartmentWrapper_Compartment() {
		return (EReference) compartmentWrapperEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompartment() {
		return compartmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getCompartment_Label() {
		return (EAttribute) compartmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFlowWrapper() {
		return flowWrapperEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFlowWrapper_Flow() {
		return (EReference) flowWrapperEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFlow() {
		return flowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFlow_Id() {
		return (EAttribute) flowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EpimodelFactory getEpimodelFactory() {
		return (EpimodelFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		epidemicWrapperEClass = createEClass(EPIDEMIC_WRAPPER);
		createEReference(epidemicWrapperEClass, EPIDEMIC_WRAPPER__EPIDEMIC);

		epidemicEClass = createEClass(EPIDEMIC);
		createEAttribute(epidemicEClass, EPIDEMIC__ID);

		compartmentWrapperEClass = createEClass(COMPARTMENT_WRAPPER);
		createEReference(compartmentWrapperEClass, COMPARTMENT_WRAPPER__COMPARTMENT);

		compartmentEClass = createEClass(COMPARTMENT);
		createEAttribute(compartmentEClass, COMPARTMENT__LABEL);

		flowWrapperEClass = createEClass(FLOW_WRAPPER);
		createEReference(flowWrapperEClass, FLOW_WRAPPER__FLOW);

		flowEClass = createEClass(FLOW);
		createEAttribute(flowEClass, FLOW__ID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(epidemicWrapperEClass, EpidemicWrapper.class, "EpidemicWrapper", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEpidemicWrapper_Epidemic(), this.getEpidemic(), null, "epidemic", null, 0, 1,
				EpidemicWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(epidemicEClass, Epidemic.class, "Epidemic", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEpidemic_Id(), ecorePackage.getEString(), "id", null, 0, 1, Epidemic.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compartmentWrapperEClass, CompartmentWrapper.class, "CompartmentWrapper", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompartmentWrapper_Compartment(), this.getCompartment(), null, "compartment", null, 0, 1,
				CompartmentWrapper.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compartmentEClass, Compartment.class, "Compartment", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompartment_Label(), ecorePackage.getEString(), "label", null, 0, -1, Compartment.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(flowWrapperEClass, FlowWrapper.class, "FlowWrapper", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFlowWrapper_Flow(), this.getFlow(), null, "flow", null, 0, 1, FlowWrapper.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(flowEClass, Flow.class, "Flow", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFlow_Id(), ecorePackage.getEString(), "id", null, 0, 1, Flow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EpimodelPackageImpl
