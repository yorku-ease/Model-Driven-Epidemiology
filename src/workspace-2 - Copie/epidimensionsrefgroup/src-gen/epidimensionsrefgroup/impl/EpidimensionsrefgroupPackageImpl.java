/**
 */
package epidimensionsrefgroup.impl;

import epibase.EpibasePackage;

import epibase.impl.EpibasePackageImpl;

import epidimensionsbase.EpidimensionsbasePackage;

import epidimensionsbase.impl.EpidimensionsbasePackageImpl;

import epidimensionsrefgroup.Batch;
import epidimensionsrefgroup.Compartment;
import epidimensionsrefgroup.Contact;
import epidimensionsrefgroup.Dimension;
import epidimensionsrefgroup.DimensionRef;
import epidimensionsrefgroup.Epidemic;
import epidimensionsrefgroup.EpidimensionsrefgroupFactory;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.Flow;
import epidimensionsrefgroup.IDimension;
import epidimensionsrefgroup.Rate;

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
public class EpidimensionsrefgroupPackageImpl extends EPackageImpl implements EpidimensionsrefgroupPackage {
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
	private EClass dimensionRefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compartmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass flowEClass = null;

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
	private EClass batchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass contactEClass = null;

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
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EpidimensionsrefgroupPackageImpl() {
		super(eNS_URI, EpidimensionsrefgroupFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link EpidimensionsrefgroupPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EpidimensionsrefgroupPackage init() {
		if (isInited)
			return (EpidimensionsrefgroupPackage) EPackage.Registry.INSTANCE
					.getEPackage(EpidimensionsrefgroupPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredEpidimensionsrefgroupPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		EpidimensionsrefgroupPackageImpl theEpidimensionsrefgroupPackage = registeredEpidimensionsrefgroupPackage instanceof EpidimensionsrefgroupPackageImpl
				? (EpidimensionsrefgroupPackageImpl) registeredEpidimensionsrefgroupPackage
				: new EpidimensionsrefgroupPackageImpl();

		isInited = true;

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(EpidimensionsbasePackage.eNS_URI);
		EpidimensionsbasePackageImpl theEpidimensionsbasePackage = (EpidimensionsbasePackageImpl) (registeredPackage instanceof EpidimensionsbasePackageImpl
				? registeredPackage
				: EpidimensionsbasePackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(EpibasePackage.eNS_URI);
		EpibasePackageImpl theEpibasePackage = (EpibasePackageImpl) (registeredPackage instanceof EpibasePackageImpl
				? registeredPackage
				: EpibasePackage.eINSTANCE);

		// Create package meta-data objects
		theEpidimensionsrefgroupPackage.createPackageContents();
		theEpidimensionsbasePackage.createPackageContents();
		theEpibasePackage.createPackageContents();

		// Initialize created meta-data
		theEpidimensionsrefgroupPackage.initializePackageContents();
		theEpidimensionsbasePackage.initializePackageContents();
		theEpibasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEpidimensionsrefgroupPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EpidimensionsrefgroupPackage.eNS_URI, theEpidimensionsrefgroupPackage);
		return theEpidimensionsrefgroupPackage;
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
	public EReference getEpidemic_Idimension() {
		return (EReference) epidemicEClass.getEStructuralFeatures().get(0);
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
	public EClass getDimension() {
		return dimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimension_Flow_2() {
		return (EReference) dimensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getDimension_Idimension() {
		return (EReference) dimensionEClass.getEStructuralFeatures().get(1);
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
	public EReference getDimensionRef_Idimension() {
		return (EReference) dimensionRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompartment() {
		return compartmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFlow() {
		return flowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFlow_From_dimension() {
		return (EReference) flowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFlow_To_dimension() {
		return (EReference) flowEClass.getEStructuralFeatures().get(1);
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
	public EClass getBatch() {
		return batchEClass;
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
	public EReference getContact_Contact_dimension() {
		return (EReference) contactEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EpidimensionsrefgroupFactory getEpidimensionsrefgroupFactory() {
		return (EpidimensionsrefgroupFactory) getEFactoryInstance();
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
		createEReference(epidemicEClass, EPIDEMIC__IDIMENSION);

		iDimensionEClass = createEClass(IDIMENSION);

		dimensionEClass = createEClass(DIMENSION);
		createEReference(dimensionEClass, DIMENSION__FLOW_2);
		createEReference(dimensionEClass, DIMENSION__IDIMENSION);

		dimensionRefEClass = createEClass(DIMENSION_REF);
		createEReference(dimensionRefEClass, DIMENSION_REF__IDIMENSION);

		compartmentEClass = createEClass(COMPARTMENT);

		flowEClass = createEClass(FLOW);
		createEReference(flowEClass, FLOW__FROM_DIMENSION);
		createEReference(flowEClass, FLOW__TO_DIMENSION);

		rateEClass = createEClass(RATE);

		batchEClass = createEClass(BATCH);

		contactEClass = createEClass(CONTACT);
		createEReference(contactEClass, CONTACT__CONTACT_DIMENSION);
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
		EpidimensionsbasePackage theEpidimensionsbasePackage = (EpidimensionsbasePackage) EPackage.Registry.INSTANCE
				.getEPackage(EpidimensionsbasePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		epidemicEClass.getESuperTypes().add(theEpidimensionsbasePackage.getEpidemic());
		iDimensionEClass.getESuperTypes().add(theEpidimensionsbasePackage.getDimension());
		iDimensionEClass.getESuperTypes().add(theEpidimensionsbasePackage.getCompartment());
		dimensionEClass.getESuperTypes().add(this.getIDimension());
		dimensionRefEClass.getESuperTypes().add(this.getIDimension());
		compartmentEClass.getESuperTypes().add(theEpidimensionsbasePackage.getCompartment());
		compartmentEClass.getESuperTypes().add(this.getIDimension());
		flowEClass.getESuperTypes().add(theEpidimensionsbasePackage.getFlow());
		rateEClass.getESuperTypes().add(this.getFlow());
		rateEClass.getESuperTypes().add(theEpidimensionsbasePackage.getRate());
		batchEClass.getESuperTypes().add(this.getFlow());
		batchEClass.getESuperTypes().add(theEpidimensionsbasePackage.getBatch());
		contactEClass.getESuperTypes().add(this.getFlow());
		contactEClass.getESuperTypes().add(theEpidimensionsbasePackage.getContact());

		// Initialize classes, features, and operations; add parameters
		initEClass(epidemicEClass, Epidemic.class, "Epidemic", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEpidemic_Idimension(), this.getIDimension(), null, "idimension", null, 0, -1, Epidemic.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iDimensionEClass, IDimension.class, "IDimension", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(dimensionEClass, Dimension.class, "Dimension", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimension_Flow_2(), this.getFlow(), null, "flow_2", null, 0, -1, Dimension.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDimension_Idimension(), this.getIDimension(), null, "idimension", null, 0, -1,
				Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dimensionRefEClass, DimensionRef.class, "DimensionRef", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDimensionRef_Idimension(), this.getIDimension(), null, "idimension", null, 0, 1,
				DimensionRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compartmentEClass, Compartment.class, "Compartment", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(flowEClass, Flow.class, "Flow", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getFlow_From_dimension(), this.getIDimension(), null, "from_dimension", null, 0, 1, Flow.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFlow_To_dimension(), this.getIDimension(), null, "to_dimension", null, 0, 1, Flow.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(rateEClass, Rate.class, "Rate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(batchEClass, Batch.class, "Batch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(contactEClass, Contact.class, "Contact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getContact_Contact_dimension(), this.getIDimension(), null, "contact_dimension", null, 0, 1,
				Contact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //EpidimensionsrefgroupPackageImpl
