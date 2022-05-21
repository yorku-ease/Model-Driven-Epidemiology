/**
 */
package batchRateContactFlows.impl;

import batchRateContactFlows.Batch;
import batchRateContactFlows.BatchRateContactFlowsFactory;
import batchRateContactFlows.BatchRateContactFlowsPackage;
import batchRateContactFlows.Contact;
import batchRateContactFlows.FromToFlow;
import batchRateContactFlows.Rate;

import epidemicDimension.EpidemicDimensionPackage;
import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import transformedEpidemic.TransformedEpidemicPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BatchRateContactFlowsPackageImpl extends EPackageImpl implements BatchRateContactFlowsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass batchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass fromToFlowEClass = null;

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
	 * @see batchRateContactFlows.BatchRateContactFlowsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BatchRateContactFlowsPackageImpl() {
		super(eNS_URI, BatchRateContactFlowsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BatchRateContactFlowsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BatchRateContactFlowsPackage init() {
		if (isInited)
			return (BatchRateContactFlowsPackage) EPackage.Registry.INSTANCE
					.getEPackage(BatchRateContactFlowsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBatchRateContactFlowsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BatchRateContactFlowsPackageImpl theBatchRateContactFlowsPackage = registeredBatchRateContactFlowsPackage instanceof BatchRateContactFlowsPackageImpl
				? (BatchRateContactFlowsPackageImpl) registeredBatchRateContactFlowsPackage
				: new BatchRateContactFlowsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		TransformedEpidemicPackage.eINSTANCE.eClass();
		EpidemicDimensionPackage.eINSTANCE.eClass();
		EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theBatchRateContactFlowsPackage.createPackageContents();

		// Initialize created meta-data
		theBatchRateContactFlowsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBatchRateContactFlowsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BatchRateContactFlowsPackage.eNS_URI, theBatchRateContactFlowsPackage);
		return theBatchRateContactFlowsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getBatch() {
		return batchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRate() {
		return rateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getContact() {
		return contactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getContact_Contact() {
		return (EReference) contactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFromToFlow() {
		return fromToFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFromToFlow_From() {
		return (EReference) fromToFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFromToFlow_To() {
		return (EReference) fromToFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BatchRateContactFlowsFactory getBatchRateContactFlowsFactory() {
		return (BatchRateContactFlowsFactory) getEFactoryInstance();
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
		batchEClass = createEClass(BATCH);

		rateEClass = createEClass(RATE);

		contactEClass = createEClass(CONTACT);
		createEReference(contactEClass, CONTACT__CONTACT);

		fromToFlowEClass = createEClass(FROM_TO_FLOW);
		createEReference(fromToFlowEClass, FROM_TO_FLOW__FROM);
		createEReference(fromToFlowEClass, FROM_TO_FLOW__TO);
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
		batchEClass.getESuperTypes().add(this.getFromToFlow());
		rateEClass.getESuperTypes().add(this.getFromToFlow());
		contactEClass.getESuperTypes().add(this.getFromToFlow());
		fromToFlowEClass.getESuperTypes().add(theEpimodelPackage.getFlow());

		// Initialize classes, features, and operations; add parameters
		initEClass(batchEClass, Batch.class, "Batch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rateEClass, Rate.class, "Rate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(contactEClass, Contact.class, "Contact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContact_Contact(), theEpimodelPackage.getCompartment(), null, "contact", null, 0, 1,
				Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(fromToFlowEClass, FromToFlow.class, "FromToFlow", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFromToFlow_From(), theEpimodelPackage.getCompartment(), null, "from", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFromToFlow_To(), theEpimodelPackage.getCompartment(), null, "to", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //BatchRateContactFlowsPackageImpl
