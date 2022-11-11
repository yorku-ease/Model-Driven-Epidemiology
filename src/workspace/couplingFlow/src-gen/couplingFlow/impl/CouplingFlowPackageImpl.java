/**
 */
package couplingFlow.impl;

import couplingFlow.CouplingFlow;
import couplingFlow.CouplingFlowFactory;
import couplingFlow.CouplingFlowPackage;

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
public class CouplingFlowPackageImpl extends EPackageImpl implements CouplingFlowPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass couplingFlowEClass = null;

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
	 * @see couplingFlow.CouplingFlowPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CouplingFlowPackageImpl() {
		super(eNS_URI, CouplingFlowFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CouplingFlowPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CouplingFlowPackage init() {
		if (isInited)
			return (CouplingFlowPackage) EPackage.Registry.INSTANCE.getEPackage(CouplingFlowPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredCouplingFlowPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CouplingFlowPackageImpl theCouplingFlowPackage = registeredCouplingFlowPackage instanceof CouplingFlowPackageImpl
				? (CouplingFlowPackageImpl) registeredCouplingFlowPackage
				: new CouplingFlowPackageImpl();

		isInited = true;

		// Initialize simple dependencies
//		Manually removed
//		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCouplingFlowPackage.createPackageContents();

		// Initialize created meta-data
		theCouplingFlowPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCouplingFlowPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CouplingFlowPackage.eNS_URI, theCouplingFlowPackage);
		return theCouplingFlowPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCouplingFlow() {
		return couplingFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCouplingFlow_FirstFrom() {
		return (EReference) couplingFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCouplingFlow_SecondFrom() {
		return (EReference) couplingFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCouplingFlow_SecondTo() {
		return (EReference) couplingFlowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCouplingFlow_FirstTo() {
		return (EReference) couplingFlowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CouplingFlowFactory getCouplingFlowFactory() {
		return (CouplingFlowFactory) getEFactoryInstance();
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
		couplingFlowEClass = createEClass(COUPLING_FLOW);
		createEReference(couplingFlowEClass, COUPLING_FLOW__FIRST_FROM);
		createEReference(couplingFlowEClass, COUPLING_FLOW__SECOND_FROM);
		createEReference(couplingFlowEClass, COUPLING_FLOW__SECOND_TO);
		createEReference(couplingFlowEClass, COUPLING_FLOW__FIRST_TO);
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
		couplingFlowEClass.getESuperTypes().add(theEpimodelPackage.getFlow());

		// Initialize classes, features, and operations; add parameters
		initEClass(couplingFlowEClass, CouplingFlow.class, "CouplingFlow", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCouplingFlow_FirstFrom(), theEpimodelPackage.getCompartmentWrapper(), null, "firstFrom", null,
				0, 1, CouplingFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCouplingFlow_SecondFrom(), theEpimodelPackage.getCompartmentWrapper(), null, "secondFrom",
				null, 0, 1, CouplingFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCouplingFlow_SecondTo(), theEpimodelPackage.getCompartmentWrapper(), null, "secondTo", null,
				0, 1, CouplingFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCouplingFlow_FirstTo(), theEpimodelPackage.getCompartmentWrapper(), null, "firstTo", null, 0,
				1, CouplingFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CouplingFlowPackageImpl
