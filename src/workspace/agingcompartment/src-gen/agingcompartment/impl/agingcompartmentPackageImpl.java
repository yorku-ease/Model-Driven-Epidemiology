/**
 */
package agingcompartment.impl;

import agingcompartment.Aging;
import agingcompartment.agingcompartmentFactory;
import agingcompartment.agingcompartmentPackage;

import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class agingcompartmentPackageImpl extends EPackageImpl implements agingcompartmentPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agingEClass = null;

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
	 * @see agingcompartment.agingcompartmentPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private agingcompartmentPackageImpl() {
		super(eNS_URI, agingcompartmentFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link agingcompartmentPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static agingcompartmentPackage init() {
		if (isInited)
			return (agingcompartmentPackage) EPackage.Registry.INSTANCE.getEPackage(agingcompartmentPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredagingcompartmentPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		agingcompartmentPackageImpl theagingcompartmentPackage = registeredagingcompartmentPackage instanceof agingcompartmentPackageImpl
				? (agingcompartmentPackageImpl) registeredagingcompartmentPackage
				: new agingcompartmentPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		//manually removed
//		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theagingcompartmentPackage.createPackageContents();

		// Initialize created meta-data
		theagingcompartmentPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theagingcompartmentPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(agingcompartmentPackage.eNS_URI, theagingcompartmentPackage);
		return theagingcompartmentPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAging() {
		return agingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAging_AgeGroups() {
		return (EAttribute) agingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public agingcompartmentFactory getagingcompartmentFactory() {
		return (agingcompartmentFactory) getEFactoryInstance();
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
		agingEClass = createEClass(AGING);
		createEAttribute(agingEClass, AGING__AGE_GROUPS);
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
		agingEClass.getESuperTypes().add(theEpimodelPackage.getCompartment());

		// Initialize classes, features, and operations; add parameters
		initEClass(agingEClass, Aging.class, "Aging", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAging_AgeGroups(), ecorePackage.getEString(), "AgeGroups", null, 0, 1, Aging.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //agingcompartmentPackageImpl
