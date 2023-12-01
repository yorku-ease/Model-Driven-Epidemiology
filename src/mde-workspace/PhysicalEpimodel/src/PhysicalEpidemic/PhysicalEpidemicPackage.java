/**
 */
package PhysicalEpidemic;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see PhysicalEpidemic.PhysicalEpidemicFactory
 * @model kind="package"
 * @generated
 */
public interface PhysicalEpidemicPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "PhysicalEpidemic";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "geodes.sms.phmde";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "phep";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PhysicalEpidemicPackage eINSTANCE = PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl.init();

	/**
	 * The meta object id for the '{@link PhysicalEpidemic.impl.PhysicalEpidemicImpl <em>Physical Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see PhysicalEpidemic.impl.PhysicalEpidemicImpl
	 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getPhysicalEpidemic()
	 * @generated
	 */
	int PHYSICAL_EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Compartments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_EPIDEMIC__COMPARTMENTS = 0;

	/**
	 * The feature id for the '<em><b>Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_EPIDEMIC__FLOWS = 1;

	/**
	 * The number of structural features of the '<em>Physical Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_EPIDEMIC_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link PhysicalEpidemic.impl.LabelImpl <em>Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see PhysicalEpidemic.impl.LabelImpl
	 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getLabel()
	 * @generated
	 */
	int LABEL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL__NAME = 0;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link PhysicalEpidemic.impl.EquationTemplateImpl <em>Equation Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see PhysicalEpidemic.impl.EquationTemplateImpl
	 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getEquationTemplate()
	 * @generated
	 */
	int EQUATION_TEMPLATE = 2;

	/**
	 * The number of structural features of the '<em>Equation Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EQUATION_TEMPLATE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link PhysicalEpidemic.impl.PhysicalCompartmentImpl <em>Physical Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see PhysicalEpidemic.impl.PhysicalCompartmentImpl
	 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getPhysicalCompartment()
	 * @generated
	 */
	int PHYSICAL_COMPARTMENT = 3;

	/**
	 * The feature id for the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_COMPARTMENT__TEMPLATE = 0;

	/**
	 * The number of structural features of the '<em>Physical Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_COMPARTMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link PhysicalEpidemic.impl.PhysicalFlowImpl <em>Physical Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see PhysicalEpidemic.impl.PhysicalFlowImpl
	 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getPhysicalFlow()
	 * @generated
	 */
	int PHYSICAL_FLOW = 4;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_FLOW__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_FLOW__TO = 1;

	/**
	 * The feature id for the '<em><b>Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_FLOW__LABELS = 2;

	/**
	 * The number of structural features of the '<em>Physical Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_FLOW_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link PhysicalEpidemic.PhysicalEpidemic <em>Physical Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Physical Epidemic</em>'.
	 * @see PhysicalEpidemic.PhysicalEpidemic
	 * @generated
	 */
	EClass getPhysicalEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link PhysicalEpidemic.PhysicalEpidemic#getCompartments <em>Compartments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartments</em>'.
	 * @see PhysicalEpidemic.PhysicalEpidemic#getCompartments()
	 * @see #getPhysicalEpidemic()
	 * @generated
	 */
	EReference getPhysicalEpidemic_Compartments();

	/**
	 * Returns the meta object for the containment reference list '{@link PhysicalEpidemic.PhysicalEpidemic#getFlows <em>Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flows</em>'.
	 * @see PhysicalEpidemic.PhysicalEpidemic#getFlows()
	 * @see #getPhysicalEpidemic()
	 * @generated
	 */
	EReference getPhysicalEpidemic_Flows();

	/**
	 * Returns the meta object for class '{@link PhysicalEpidemic.Label <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label</em>'.
	 * @see PhysicalEpidemic.Label
	 * @generated
	 */
	EClass getLabel();

	/**
	 * Returns the meta object for the attribute '{@link PhysicalEpidemic.Label#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see PhysicalEpidemic.Label#getName()
	 * @see #getLabel()
	 * @generated
	 */
	EAttribute getLabel_Name();

	/**
	 * Returns the meta object for class '{@link PhysicalEpidemic.EquationTemplate <em>Equation Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Equation Template</em>'.
	 * @see PhysicalEpidemic.EquationTemplate
	 * @generated
	 */
	EClass getEquationTemplate();

	/**
	 * Returns the meta object for class '{@link PhysicalEpidemic.PhysicalCompartment <em>Physical Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Physical Compartment</em>'.
	 * @see PhysicalEpidemic.PhysicalCompartment
	 * @generated
	 */
	EClass getPhysicalCompartment();

	/**
	 * Returns the meta object for the containment reference '{@link PhysicalEpidemic.PhysicalCompartment#getTemplate <em>Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Template</em>'.
	 * @see PhysicalEpidemic.PhysicalCompartment#getTemplate()
	 * @see #getPhysicalCompartment()
	 * @generated
	 */
	EReference getPhysicalCompartment_Template();

	/**
	 * Returns the meta object for class '{@link PhysicalEpidemic.PhysicalFlow <em>Physical Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Physical Flow</em>'.
	 * @see PhysicalEpidemic.PhysicalFlow
	 * @generated
	 */
	EClass getPhysicalFlow();

	/**
	 * Returns the meta object for the reference '{@link PhysicalEpidemic.PhysicalFlow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see PhysicalEpidemic.PhysicalFlow#getFrom()
	 * @see #getPhysicalFlow()
	 * @generated
	 */
	EReference getPhysicalFlow_From();

	/**
	 * Returns the meta object for the reference '{@link PhysicalEpidemic.PhysicalFlow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see PhysicalEpidemic.PhysicalFlow#getTo()
	 * @see #getPhysicalFlow()
	 * @generated
	 */
	EReference getPhysicalFlow_To();

	/**
	 * Returns the meta object for the containment reference list '{@link PhysicalEpidemic.PhysicalFlow#getLabels <em>Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Labels</em>'.
	 * @see PhysicalEpidemic.PhysicalFlow#getLabels()
	 * @see #getPhysicalFlow()
	 * @generated
	 */
	EReference getPhysicalFlow_Labels();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PhysicalEpidemicFactory getPhysicalEpidemicFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link PhysicalEpidemic.impl.PhysicalEpidemicImpl <em>Physical Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see PhysicalEpidemic.impl.PhysicalEpidemicImpl
		 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getPhysicalEpidemic()
		 * @generated
		 */
		EClass PHYSICAL_EPIDEMIC = eINSTANCE.getPhysicalEpidemic();

		/**
		 * The meta object literal for the '<em><b>Compartments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_EPIDEMIC__COMPARTMENTS = eINSTANCE.getPhysicalEpidemic_Compartments();

		/**
		 * The meta object literal for the '<em><b>Flows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_EPIDEMIC__FLOWS = eINSTANCE.getPhysicalEpidemic_Flows();

		/**
		 * The meta object literal for the '{@link PhysicalEpidemic.impl.LabelImpl <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see PhysicalEpidemic.impl.LabelImpl
		 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getLabel()
		 * @generated
		 */
		EClass LABEL = eINSTANCE.getLabel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LABEL__NAME = eINSTANCE.getLabel_Name();

		/**
		 * The meta object literal for the '{@link PhysicalEpidemic.impl.EquationTemplateImpl <em>Equation Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see PhysicalEpidemic.impl.EquationTemplateImpl
		 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getEquationTemplate()
		 * @generated
		 */
		EClass EQUATION_TEMPLATE = eINSTANCE.getEquationTemplate();

		/**
		 * The meta object literal for the '{@link PhysicalEpidemic.impl.PhysicalCompartmentImpl <em>Physical Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see PhysicalEpidemic.impl.PhysicalCompartmentImpl
		 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getPhysicalCompartment()
		 * @generated
		 */
		EClass PHYSICAL_COMPARTMENT = eINSTANCE.getPhysicalCompartment();

		/**
		 * The meta object literal for the '<em><b>Template</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_COMPARTMENT__TEMPLATE = eINSTANCE.getPhysicalCompartment_Template();

		/**
		 * The meta object literal for the '{@link PhysicalEpidemic.impl.PhysicalFlowImpl <em>Physical Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see PhysicalEpidemic.impl.PhysicalFlowImpl
		 * @see PhysicalEpidemic.impl.PhysicalEpidemicPackageImpl#getPhysicalFlow()
		 * @generated
		 */
		EClass PHYSICAL_FLOW = eINSTANCE.getPhysicalFlow();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_FLOW__FROM = eINSTANCE.getPhysicalFlow_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_FLOW__TO = eINSTANCE.getPhysicalFlow_To();

		/**
		 * The meta object literal for the '<em><b>Labels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_FLOW__LABELS = eINSTANCE.getPhysicalFlow_Labels();

	}

} //PhysicalEpidemicPackage
