/**
 */
package mortality.impl;

import epimodel.EpimodelPackage;

import mortality.Mortality;
import mortality.MortalityFactory;
import mortality.MortalityPackage;

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
public class MortalityPackageImpl extends EPackageImpl implements MortalityPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mortalityEClass = null;

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
	 * @see mortality.MortalityPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MortalityPackageImpl() {
		super(eNS_URI, MortalityFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MortalityPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MortalityPackage init() {
		if (isInited)
			return (MortalityPackage) EPackage.Registry.INSTANCE.getEPackage(MortalityPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredMortalityPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		MortalityPackageImpl theMortalityPackage = registeredMortalityPackage instanceof MortalityPackageImpl
				? (MortalityPackageImpl) registeredMortalityPackage
				: new MortalityPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		// manually removed
		// EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMortalityPackage.createPackageContents();

		// Initialize created meta-data
		theMortalityPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMortalityPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MortalityPackage.eNS_URI, theMortalityPackage);
		return theMortalityPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMortality() {
		return mortalityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMortality_Mortality() {
		return (EReference) mortalityEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MortalityFactory getMortalityFactory() {
		return (MortalityFactory) getEFactoryInstance();
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
		mortalityEClass = createEClass(MORTALITY);
		createEReference(mortalityEClass, MORTALITY__MORTALITY);
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
		mortalityEClass.getESuperTypes().add(theEpimodelPackage.getCompartment());

		// Initialize classes, features, and operations; add parameters
		initEClass(mortalityEClass, Mortality.class, "Mortality", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMortality_Mortality(), theEpimodelPackage.getCompartmentWrapper(), null, "mortality", null, 1,
				1, Mortality.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //MortalityPackageImpl
