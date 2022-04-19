/**
 */
package epimodelgroup.impl;

import epimodelgroup.Dimension;
import epimodelgroup.DimensionRef;
import epimodelgroup.Epidemic;
import epimodelgroup.EpimodelgroupFactory;
import epimodelgroup.EpimodelgroupPackage;
import epimodelgroup.IDimension;
import epimodelgroup.MetaBatch;
import epimodelgroup.MetaCompartment;
import epimodelgroup.MetaContact;
import epimodelgroup.MetaFlow;
import epimodelgroup.MetaRate;

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
public class EpimodelgroupPackageImpl extends EPackageImpl implements EpimodelgroupPackage {
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
	private EClass iDimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaCompartmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaFlowEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaContactEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaRateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaBatchEClass = null;

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
	 * @see epimodelgroup.EpimodelgroupPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EpimodelgroupPackageImpl() {
		super(eNS_URI, EpimodelgroupFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EpimodelgroupPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EpimodelgroupPackage init() {
		if (isInited)
			return (EpimodelgroupPackage) EPackage.Registry.INSTANCE.getEPackage(EpimodelgroupPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEpimodelgroupPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EpimodelgroupPackageImpl theEpimodelgroupPackage = registeredEpimodelgroupPackage instanceof EpimodelgroupPackageImpl
				? (EpimodelgroupPackageImpl) registeredEpimodelgroupPackage
				: new EpimodelgroupPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theEpimodelgroupPackage.createPackageContents();

		// Initialize created meta-data
		theEpimodelgroupPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEpimodelgroupPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EpimodelgroupPackage.eNS_URI, theEpimodelgroupPackage);
		return theEpimodelgroupPackage;
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
	public EReference getEpidemic_Dimension() {
		return (EReference) epidemicEClass.getEStructuralFeatures().get(1);
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
	public EClass getIDimension() {
		return iDimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getIDimension_Id() {
		return (EAttribute) iDimensionEClass.getEStructuralFeatures().get(0);
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
	public EClass getMetaCompartment() {
		return metaCompartmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetaFlow() {
		return metaFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaFlow_From() {
		return (EReference) metaFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaFlow_To() {
		return (EReference) metaFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getMetaFlow_Id() {
		return (EAttribute) metaFlowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDimensionRef() {
		return dimensionRefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimensionRef_Reference() {
		return (EReference) dimensionRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetaContact() {
		return metaContactEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getMetaContact_Contact() {
		return (EReference) metaContactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetaRate() {
		return metaRateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getMetaBatch() {
		return metaBatchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EpimodelgroupFactory getEpimodelgroupFactory() {
		return (EpimodelgroupFactory) getEFactoryInstance();
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
		epidemicEClass = createEClass(EPIDEMIC);
		createEAttribute(epidemicEClass, EPIDEMIC__ID);
		createEReference(epidemicEClass, EPIDEMIC__DIMENSION);

		iDimensionEClass = createEClass(IDIMENSION);
		createEAttribute(iDimensionEClass, IDIMENSION__ID);

		dimensionEClass = createEClass(DIMENSION);
		createEReference(dimensionEClass, DIMENSION__COMPARTMENT);
		createEReference(dimensionEClass, DIMENSION__FLOW);

		metaCompartmentEClass = createEClass(META_COMPARTMENT);

		metaFlowEClass = createEClass(META_FLOW);
		createEReference(metaFlowEClass, META_FLOW__FROM);
		createEReference(metaFlowEClass, META_FLOW__TO);
		createEAttribute(metaFlowEClass, META_FLOW__ID);

		dimensionRefEClass = createEClass(DIMENSION_REF);
		createEReference(dimensionRefEClass, DIMENSION_REF__REFERENCE);

		metaContactEClass = createEClass(META_CONTACT);
		createEReference(metaContactEClass, META_CONTACT__CONTACT);

		metaRateEClass = createEClass(META_RATE);

		metaBatchEClass = createEClass(META_BATCH);
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
		dimensionEClass.getESuperTypes().add(this.getIDimension());
		metaCompartmentEClass.getESuperTypes().add(this.getIDimension());
		dimensionRefEClass.getESuperTypes().add(this.getIDimension());
		metaContactEClass.getESuperTypes().add(this.getMetaFlow());
		metaRateEClass.getESuperTypes().add(this.getMetaFlow());
		metaBatchEClass.getESuperTypes().add(this.getMetaFlow());

		// Initialize classes, features, and operations; add parameters
		initEClass(epidemicEClass, Epidemic.class, "Epidemic", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEpidemic_Id(), ecorePackage.getEString(), "id", null, 0, 1, Epidemic.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEpidemic_Dimension(), this.getIDimension(), null, "dimension", null, 0, -1, Epidemic.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iDimensionEClass, IDimension.class, "IDimension", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIDimension_Id(), ecorePackage.getEString(), "id", null, 0, 1, IDimension.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dimensionEClass, Dimension.class, "Dimension", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimension_Compartment(), this.getIDimension(), null, "compartment", null, 0, -1,
				Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDimension_Flow(), this.getMetaFlow(), null, "flow", null, 0, -1, Dimension.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaCompartmentEClass, MetaCompartment.class, "MetaCompartment", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(metaFlowEClass, MetaFlow.class, "MetaFlow", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetaFlow_From(), this.getIDimension(), null, "from", null, 0, 1, MetaFlow.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMetaFlow_To(), this.getIDimension(), null, "to", null, 0, 1, MetaFlow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getMetaFlow_Id(), ecorePackage.getEString(), "id", null, 0, 1, MetaFlow.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dimensionRefEClass, DimensionRef.class, "DimensionRef", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimensionRef_Reference(), this.getIDimension(), null, "reference", null, 0, 1,
				DimensionRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaContactEClass, MetaContact.class, "MetaContact", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetaContact_Contact(), this.getIDimension(), null, "contact", null, 0, 1, MetaContact.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaRateEClass, MetaRate.class, "MetaRate", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(metaBatchEClass, MetaBatch.class, "MetaBatch", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //EpimodelgroupPackageImpl
