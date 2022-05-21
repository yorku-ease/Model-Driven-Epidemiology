/**
 */
package epidemicDimension.impl;

import epidemicDimension.Dimension;
import epidemicDimension.EpidemicDimensionFactory;
import epidemicDimension.EpidemicDimensionPackage;

import epimodel.EpimodelPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class EpidemicDimensionPackageImpl extends EPackageImpl implements EpidemicDimensionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionEClass = null;

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
	 * @see epidemicDimension.EpidemicDimensionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EpidemicDimensionPackageImpl() {
		super(eNS_URI, EpidemicDimensionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EpidemicDimensionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EpidemicDimensionPackage init() {
		if (isInited)
			return (EpidemicDimensionPackage) EPackage.Registry.INSTANCE.getEPackage(EpidemicDimensionPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEpidemicDimensionPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EpidemicDimensionPackageImpl theEpidemicDimensionPackage = registeredEpidemicDimensionPackage instanceof EpidemicDimensionPackageImpl
				? (EpidemicDimensionPackageImpl) registeredEpidemicDimensionPackage
				: new EpidemicDimensionPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theEpidemicDimensionPackage.createPackageContents();

		// Initialize created meta-data
		theEpidemicDimensionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEpidemicDimensionPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EpidemicDimensionPackage.eNS_URI, theEpidemicDimensionPackage);
		return theEpidemicDimensionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDimension() {
		return dimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimension_Compartment() {
		return (EReference) dimensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimension_Flow() {
		return (EReference) dimensionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EpidemicDimensionFactory getEpidemicDimensionFactory() {
		return (EpidemicDimensionFactory) getEFactoryInstance();
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
		dimensionEClass = createEClass(DIMENSION);
		createEReference(dimensionEClass, DIMENSION__COMPARTMENT);
		createEReference(dimensionEClass, DIMENSION__FLOW);
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

		// Obtain other dependent packages
		EpimodelPackage theEpimodelPackage = (EpimodelPackage) EPackage.Registry.INSTANCE
				.getEPackage(EpimodelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dimensionEClass.getESuperTypes().add(theEpimodelPackage.getCompartment());

		// Initialize classes, features, and operations; add parameters
		initEClass(dimensionEClass, Dimension.class, "Dimension", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimension_Compartment(), theEpimodelPackage.getCompartment(), null, "compartment", null, 0,
				-1, Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDimension_Flow(), theEpimodelPackage.getFlow(), null, "flow", null, 0, -1, Dimension.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EpidemicDimensionPackageImpl
