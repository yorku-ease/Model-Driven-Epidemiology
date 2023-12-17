/**
 */
package src_ph.PhysicalEpidemicRoot.impl;

import src_ph.PhysicalEpidemicRoot.EquationTemplate;
import src_ph.PhysicalEpidemicRoot.Label;
import src_ph.PhysicalEpidemicRoot.PhysicalCompartment;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemic;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemicRootFactory;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemicRootPackage;
import src_ph.PhysicalEpidemicRoot.PhysicalFlow;

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
public class PhysicalEpidemicRootPackageImpl extends EPackageImpl implements PhysicalEpidemicRootPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass physicalEpidemicEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass labelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass equationTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass physicalCompartmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass physicalFlowEClass = null;

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
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PhysicalEpidemicRootPackageImpl() {
		super(eNS_URI, PhysicalEpidemicRootFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PhysicalEpidemicRootPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PhysicalEpidemicRootPackage init() {
		if (isInited) return (PhysicalEpidemicRootPackage)EPackage.Registry.INSTANCE.getEPackage(PhysicalEpidemicRootPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredPhysicalEpidemicRootPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PhysicalEpidemicRootPackageImpl thePhysicalEpidemicRootPackage = registeredPhysicalEpidemicRootPackage instanceof PhysicalEpidemicRootPackageImpl ? (PhysicalEpidemicRootPackageImpl)registeredPhysicalEpidemicRootPackage : new PhysicalEpidemicRootPackageImpl();

		isInited = true;

		// Create package meta-data objects
		thePhysicalEpidemicRootPackage.createPackageContents();

		// Initialize created meta-data
		thePhysicalEpidemicRootPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePhysicalEpidemicRootPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PhysicalEpidemicRootPackage.eNS_URI, thePhysicalEpidemicRootPackage);
		return thePhysicalEpidemicRootPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPhysicalEpidemic() {
		return physicalEpidemicEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPhysicalEpidemic_Compartments() {
		return (EReference)physicalEpidemicEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPhysicalEpidemic_Flows() {
		return (EReference)physicalEpidemicEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLabel() {
		return labelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getLabel_Name() {
		return (EAttribute)labelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEquationTemplate() {
		return equationTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEquationTemplate_SourceParameters() {
		return (EAttribute)equationTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEquationTemplate_ContactParameters() {
		return (EAttribute)equationTemplateEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getEquationTemplate_ContactCompartment() {
		return (EAttribute)equationTemplateEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPhysicalCompartment() {
		return physicalCompartmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPhysicalCompartment_Labels() {
		return (EReference)physicalCompartmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getPhysicalFlow() {
		return physicalFlowEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPhysicalFlow_From() {
		return (EReference)physicalFlowEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPhysicalFlow_To() {
		return (EReference)physicalFlowEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getPhysicalFlow_Equationtemplate() {
		return (EReference)physicalFlowEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getPhysicalFlow_Id() {
		return (EAttribute)physicalFlowEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PhysicalEpidemicRootFactory getPhysicalEpidemicRootFactory() {
		return (PhysicalEpidemicRootFactory)getEFactoryInstance();
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
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		physicalEpidemicEClass = createEClass(PHYSICAL_EPIDEMIC);
		createEReference(physicalEpidemicEClass, PHYSICAL_EPIDEMIC__COMPARTMENTS);
		createEReference(physicalEpidemicEClass, PHYSICAL_EPIDEMIC__FLOWS);

		labelEClass = createEClass(LABEL);
		createEAttribute(labelEClass, LABEL__NAME);

		equationTemplateEClass = createEClass(EQUATION_TEMPLATE);
		createEAttribute(equationTemplateEClass, EQUATION_TEMPLATE__SOURCE_PARAMETERS);
		createEAttribute(equationTemplateEClass, EQUATION_TEMPLATE__CONTACT_PARAMETERS);
		createEAttribute(equationTemplateEClass, EQUATION_TEMPLATE__CONTACT_COMPARTMENT);

		physicalCompartmentEClass = createEClass(PHYSICAL_COMPARTMENT);
		createEReference(physicalCompartmentEClass, PHYSICAL_COMPARTMENT__LABELS);

		physicalFlowEClass = createEClass(PHYSICAL_FLOW);
		createEReference(physicalFlowEClass, PHYSICAL_FLOW__FROM);
		createEReference(physicalFlowEClass, PHYSICAL_FLOW__TO);
		createEReference(physicalFlowEClass, PHYSICAL_FLOW__EQUATIONTEMPLATE);
		createEAttribute(physicalFlowEClass, PHYSICAL_FLOW__ID);
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
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(physicalEpidemicEClass, PhysicalEpidemic.class, "PhysicalEpidemic", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPhysicalEpidemic_Compartments(), this.getPhysicalCompartment(), null, "compartments", null, 0, -1, PhysicalEpidemic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPhysicalEpidemic_Flows(), this.getPhysicalFlow(), null, "flows", null, 0, -1, PhysicalEpidemic.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(labelEClass, Label.class, "Label", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLabel_Name(), ecorePackage.getEString(), "name", null, 0, 1, Label.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(equationTemplateEClass, EquationTemplate.class, "EquationTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEquationTemplate_SourceParameters(), ecorePackage.getEString(), "sourceParameters", null, 0, 1, EquationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEquationTemplate_ContactParameters(), ecorePackage.getEString(), "contactParameters", null, 0, 1, EquationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEquationTemplate_ContactCompartment(), ecorePackage.getEString(), "contactCompartment", null, 0, 1, EquationTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(physicalCompartmentEClass, PhysicalCompartment.class, "PhysicalCompartment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPhysicalCompartment_Labels(), this.getLabel(), null, "labels", null, 0, -1, PhysicalCompartment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(physicalFlowEClass, PhysicalFlow.class, "PhysicalFlow", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPhysicalFlow_From(), this.getPhysicalCompartment(), null, "from", null, 0, 1, PhysicalFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPhysicalFlow_To(), this.getPhysicalCompartment(), null, "to", null, 0, 1, PhysicalFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPhysicalFlow_Equationtemplate(), this.getEquationTemplate(), null, "equationtemplate", null, 1, 1, PhysicalFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPhysicalFlow_Id(), ecorePackage.getEString(), "id", null, 0, 1, PhysicalFlow.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //PhysicalEpidemicRootPackageImpl
