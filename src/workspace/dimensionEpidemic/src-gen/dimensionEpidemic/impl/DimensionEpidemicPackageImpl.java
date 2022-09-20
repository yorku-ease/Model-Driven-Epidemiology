/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.DimensionEpidemic;
import dimensionEpidemic.DimensionEpidemicFactory;
import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.Product;
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
public class DimensionEpidemicPackageImpl extends EPackageImpl implements DimensionEpidemicPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionEpidemicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass productEClass = null;

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
	 * @see dimensionEpidemic.DimensionEpidemicPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DimensionEpidemicPackageImpl() {
		super(eNS_URI, DimensionEpidemicFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link DimensionEpidemicPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated NOT
	 */
	public static DimensionEpidemicPackage init() {
		if (isInited)
			return (DimensionEpidemicPackage) EPackage.Registry.INSTANCE.getEPackage(DimensionEpidemicPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredDimensionEpidemicPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		DimensionEpidemicPackageImpl theDimensionEpidemicPackage = registeredDimensionEpidemicPackage instanceof DimensionEpidemicPackageImpl
				? (DimensionEpidemicPackageImpl) registeredDimensionEpidemicPackage
				: new DimensionEpidemicPackageImpl();

		isInited = true;

		// Initialize simple dependencies
//		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theDimensionEpidemicPackage.createPackageContents();

		// Initialize created meta-data
		theDimensionEpidemicPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDimensionEpidemicPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DimensionEpidemicPackage.eNS_URI, theDimensionEpidemicPackage);
		return theDimensionEpidemicPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDimensionEpidemic() {
		return dimensionEpidemicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimensionEpidemic_Flow() {
		return (EReference) dimensionEpidemicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimensionEpidemic_Dimension() {
		return (EReference) dimensionEpidemicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProduct() {
		return productEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProduct_Flow() {
		return (EReference) productEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProduct_Dimensions() {
		return (EReference) productEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DimensionEpidemicFactory getDimensionEpidemicFactory() {
		return (DimensionEpidemicFactory) getEFactoryInstance();
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
		dimensionEpidemicEClass = createEClass(DIMENSION_EPIDEMIC);
		createEReference(dimensionEpidemicEClass, DIMENSION_EPIDEMIC__FLOW);
		createEReference(dimensionEpidemicEClass, DIMENSION_EPIDEMIC__DIMENSION);

		productEClass = createEClass(PRODUCT);
		createEReference(productEClass, PRODUCT__FLOW);
		createEReference(productEClass, PRODUCT__DIMENSIONS);
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
		dimensionEpidemicEClass.getESuperTypes().add(theEpimodelPackage.getEpidemic());
		productEClass.getESuperTypes().add(theEpimodelPackage.getCompartment());

		// Initialize classes, features, and operations; add parameters
		initEClass(dimensionEpidemicEClass, DimensionEpidemic.class, "DimensionEpidemic", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimensionEpidemic_Flow(), theEpimodelPackage.getFlowWrapper(), null, "flow", null, 0, -1,
				DimensionEpidemic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDimensionEpidemic_Dimension(), theEpimodelPackage.getCompartmentWrapper(), null, "dimension",
				null, 0, -1, DimensionEpidemic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(productEClass, Product.class, "Product", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProduct_Flow(), theEpimodelPackage.getFlowWrapper(), null, "flow", null, 0, -1, Product.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProduct_Dimensions(), theEpimodelPackage.getCompartmentWrapper(), null, "dimensions", null, 0,
				-1, Product.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //DimensionEpidemicPackageImpl
