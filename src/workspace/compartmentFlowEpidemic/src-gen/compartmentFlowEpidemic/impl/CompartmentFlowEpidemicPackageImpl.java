/**
 */
package compartmentFlowEpidemic.impl;

import compartmentFlowEpidemic.CompartmentFlowEpidemic;
import compartmentFlowEpidemic.CompartmentFlowEpidemicFactory;
import compartmentFlowEpidemic.CompartmentFlowEpidemicPackage;

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
public class CompartmentFlowEpidemicPackageImpl extends EPackageImpl implements CompartmentFlowEpidemicPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compartmentFlowEpidemicEClass = null;

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
	 * @see compartmentFlowEpidemic.CompartmentFlowEpidemicPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CompartmentFlowEpidemicPackageImpl() {
		super(eNS_URI, CompartmentFlowEpidemicFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CompartmentFlowEpidemicPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CompartmentFlowEpidemicPackage init() {
		if (isInited)
			return (CompartmentFlowEpidemicPackage) EPackage.Registry.INSTANCE
					.getEPackage(CompartmentFlowEpidemicPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCompartmentFlowEpidemicPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CompartmentFlowEpidemicPackageImpl theCompartmentFlowEpidemicPackage = registeredCompartmentFlowEpidemicPackage instanceof CompartmentFlowEpidemicPackageImpl
				? (CompartmentFlowEpidemicPackageImpl) registeredCompartmentFlowEpidemicPackage
				: new CompartmentFlowEpidemicPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCompartmentFlowEpidemicPackage.createPackageContents();

		// Initialize created meta-data
		theCompartmentFlowEpidemicPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCompartmentFlowEpidemicPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CompartmentFlowEpidemicPackage.eNS_URI, theCompartmentFlowEpidemicPackage);
		return theCompartmentFlowEpidemicPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompartmentFlowEpidemic() {
		return compartmentFlowEpidemicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompartmentFlowEpidemic_Compartment() {
		return (EReference) compartmentFlowEpidemicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompartmentFlowEpidemic_Flow() {
		return (EReference) compartmentFlowEpidemicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentFlowEpidemicFactory getCompartmentFlowEpidemicFactory() {
		return (CompartmentFlowEpidemicFactory) getEFactoryInstance();
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
		compartmentFlowEpidemicEClass = createEClass(COMPARTMENT_FLOW_EPIDEMIC);
		createEReference(compartmentFlowEpidemicEClass, COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT);
		createEReference(compartmentFlowEpidemicEClass, COMPARTMENT_FLOW_EPIDEMIC__FLOW);
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
		compartmentFlowEpidemicEClass.getESuperTypes().add(theEpimodelPackage.getEpidemic());

		// Initialize classes, features, and operations; add parameters
		initEClass(compartmentFlowEpidemicEClass, CompartmentFlowEpidemic.class, "CompartmentFlowEpidemic",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompartmentFlowEpidemic_Compartment(), theEpimodelPackage.getCompartmentWrapper(), null,
				"compartment", null, 0, -1, CompartmentFlowEpidemic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompartmentFlowEpidemic_Flow(), theEpimodelPackage.getFlowWrapper(), null, "flow", null, 0,
				-1, CompartmentFlowEpidemic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CompartmentFlowEpidemicPackageImpl
