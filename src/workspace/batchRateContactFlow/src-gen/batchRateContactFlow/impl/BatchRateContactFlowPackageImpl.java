/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.Batch;
import batchRateContactFlow.BatchRateContactFlowFactory;
import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Contact;
import batchRateContactFlow.FromToFlow;
import batchRateContactFlow.Rate;

import epimodel.EpimodelPackage;

import org.eclipse.emf.ecore.EAttribute;
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
public class BatchRateContactFlowPackageImpl extends EPackageImpl implements BatchRateContactFlowPackage {
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
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BatchRateContactFlowPackageImpl() {
		super(eNS_URI, BatchRateContactFlowFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BatchRateContactFlowPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BatchRateContactFlowPackage init() {
		if (isInited)
			return (BatchRateContactFlowPackage) EPackage.Registry.INSTANCE
					.getEPackage(BatchRateContactFlowPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBatchRateContactFlowPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BatchRateContactFlowPackageImpl theBatchRateContactFlowPackage = registeredBatchRateContactFlowPackage instanceof BatchRateContactFlowPackageImpl
				? (BatchRateContactFlowPackageImpl) registeredBatchRateContactFlowPackage
				: new BatchRateContactFlowPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		// manually removed
		// EpimodelPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theBatchRateContactFlowPackage.createPackageContents();

		// Initialize created meta-data
		theBatchRateContactFlowPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBatchRateContactFlowPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BatchRateContactFlowPackage.eNS_URI, theBatchRateContactFlowPackage);
		return theBatchRateContactFlowPackage;
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
	public EAttribute getContact_ContactParameters() {
		return (EAttribute) contactEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getContact_ContactAndSourceParameters() {
		return (EAttribute) contactEClass.getEStructuralFeatures().get(2);
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
	public EAttribute getFromToFlow_SourceParameters() {
		return (EAttribute) fromToFlowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFromToFlow_SinkParameters() {
		return (EAttribute) fromToFlowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFromToFlow_Parameters() {
		return (EAttribute) fromToFlowEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BatchRateContactFlowFactory getBatchRateContactFlowFactory() {
		return (BatchRateContactFlowFactory) getEFactoryInstance();
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
		contactEClass = createEClass(CONTACT);
		createEReference(contactEClass, CONTACT__CONTACT);
		createEAttribute(contactEClass, CONTACT__CONTACT_PARAMETERS);
		createEAttribute(contactEClass, CONTACT__CONTACT_AND_SOURCE_PARAMETERS);

		batchEClass = createEClass(BATCH);

		rateEClass = createEClass(RATE);

		fromToFlowEClass = createEClass(FROM_TO_FLOW);
		createEReference(fromToFlowEClass, FROM_TO_FLOW__FROM);
		createEReference(fromToFlowEClass, FROM_TO_FLOW__TO);
		createEAttribute(fromToFlowEClass, FROM_TO_FLOW__SOURCE_PARAMETERS);
		createEAttribute(fromToFlowEClass, FROM_TO_FLOW__SINK_PARAMETERS);
		createEAttribute(fromToFlowEClass, FROM_TO_FLOW__PARAMETERS);
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
		contactEClass.getESuperTypes().add(this.getFromToFlow());
		batchEClass.getESuperTypes().add(this.getFromToFlow());
		rateEClass.getESuperTypes().add(this.getFromToFlow());
		fromToFlowEClass.getESuperTypes().add(theEpimodelPackage.getFlow());

		// Initialize classes, features, and operations; add parameters
		initEClass(contactEClass, Contact.class, "Contact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContact_Contact(), theEpimodelPackage.getCompartment(), null, "contact", null, 0, 1,
				Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getContact_ContactParameters(), ecorePackage.getEString(), "contactParameters", null, 0, 1,
				Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getContact_ContactAndSourceParameters(), ecorePackage.getEString(), "contactAndSourceParameters",
				null, 0, 1, Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(batchEClass, Batch.class, "Batch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(rateEClass, Rate.class, "Rate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(fromToFlowEClass, FromToFlow.class, "FromToFlow", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFromToFlow_From(), theEpimodelPackage.getCompartment(), null, "from", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFromToFlow_To(), theEpimodelPackage.getCompartment(), null, "to", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getFromToFlow_SourceParameters(), ecorePackage.getEString(), "sourceParameters", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getFromToFlow_SinkParameters(), ecorePackage.getEString(), "sinkParameters", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getFromToFlow_Parameters(), ecorePackage.getEString(), "parameters", null, 0, 1,
				FromToFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //BatchRateContactFlowPackageImpl
