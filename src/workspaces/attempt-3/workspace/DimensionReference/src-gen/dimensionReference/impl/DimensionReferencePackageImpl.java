/**
 */
package dimensionReference.impl;

import dimensionReference.DimensionLink;
import dimensionReference.DimensionReferenceFactory;
import dimensionReference.DimensionReferencePackage;

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
public class DimensionReferencePackageImpl extends EPackageImpl implements DimensionReferencePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionLinkEClass = null;

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
	 * @see dimensionReference.DimensionReferencePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DimensionReferencePackageImpl() {
		super(eNS_URI, DimensionReferenceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DimensionReferencePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DimensionReferencePackage init() {
		if (isInited)
			return (DimensionReferencePackage) EPackage.Registry.INSTANCE
					.getEPackage(DimensionReferencePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDimensionReferencePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DimensionReferencePackageImpl theDimensionReferencePackage = registeredDimensionReferencePackage instanceof DimensionReferencePackageImpl
				? (DimensionReferencePackageImpl) registeredDimensionReferencePackage
				: new DimensionReferencePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EpidemicDimensionPackage.eINSTANCE.eClass();
		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDimensionReferencePackage.createPackageContents();

		// Initialize created meta-data
		theDimensionReferencePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDimensionReferencePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DimensionReferencePackage.eNS_URI, theDimensionReferencePackage);
		return theDimensionReferencePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDimensionLink() {
		return dimensionLinkEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimensionLink_Dimension() {
		return (EReference) dimensionLinkEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DimensionReferenceFactory getDimensionReferenceFactory() {
		return (DimensionReferenceFactory) getEFactoryInstance();
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
		dimensionLinkEClass = createEClass(DIMENSION_LINK);
		createEReference(dimensionLinkEClass, DIMENSION_LINK__DIMENSION);
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
		EpidemicDimensionPackage theEpidemicDimensionPackage = (EpidemicDimensionPackage) EPackage.Registry.INSTANCE
				.getEPackage(EpidemicDimensionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		dimensionLinkEClass.getESuperTypes().add(theEpidemicDimensionPackage.getDimension());

		// Initialize classes, features, and operations; add parameters
		initEClass(dimensionLinkEClass, DimensionLink.class, "DimensionLink", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimensionLink_Dimension(), theEpidemicDimensionPackage.getDimension(), null, "dimension",
				null, 0, 1, DimensionLink.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //DimensionReferencePackageImpl
