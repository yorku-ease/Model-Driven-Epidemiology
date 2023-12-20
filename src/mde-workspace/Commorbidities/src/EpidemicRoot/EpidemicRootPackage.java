/**
 */
package EpidemicRoot;

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
 * @see EpidemicRoot.EpidemicRootFactory
 * @model kind="package"
 * @generated
 */
public interface EpidemicRootPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "EpidemicRoot";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "geodes.sms.mde";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "ep";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpidemicRootPackage eINSTANCE = EpidemicRoot.impl.EpidemicRootPackageImpl.init();

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.EpidemicImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__COMPARTMENT = 0;

	/**
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.AbstractCompartmentImpl <em>Abstract Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.AbstractCompartmentImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getAbstractCompartment()
	 * @generated
	 */
	int ABSTRACT_COMPARTMENT = 6;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPARTMENT__LABEL = 0;

	/**
	 * The number of structural features of the '<em>Abstract Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPARTMENT_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.GroupImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__LABEL = ABSTRACT_COMPARTMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__FLOWS = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Compartments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__COMPARTMENTS = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sink</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__SINK = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__SOURCE = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.UnitCompartmentImpl <em>Unit Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.UnitCompartmentImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getUnitCompartment()
	 * @generated
	 */
	int UNIT_COMPARTMENT = 2;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_COMPARTMENT__LABEL = ABSTRACT_COMPARTMENT__LABEL;

	/**
	 * The number of structural features of the '<em>Unit Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_COMPARTMENT_FEATURE_COUNT = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.FlowImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 3;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__TO = 1;

	/**
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__SOURCE_PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__ID = 3;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.ContactImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 4;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__FROM = FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__TO = FLOW__TO;

	/**
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__SOURCE_PARAMETERS = FLOW__SOURCE_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__ID = FLOW__ID;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contact Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT_PARAMETERS = FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = FLOW_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.RateImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getRate()
	 * @generated
	 */
	int RATE = 5;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__FROM = FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__TO = FLOW__TO;

	/**
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__SOURCE_PARAMETERS = FLOW__SOURCE_PARAMETERS;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__ID = FLOW__ID;

	/**
	 * The number of structural features of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FEATURE_COUNT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link EpidemicRoot.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see EpidemicRoot.impl.ProductImpl
	 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 7;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__LABEL = ABSTRACT_COMPARTMENT__LABEL;

	/**
	 * The feature id for the '<em><b>Compartments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__COMPARTMENTS = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 1;


	/**
	 * Returns the meta object for class '{@link EpidemicRoot.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see EpidemicRoot.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the containment reference '{@link EpidemicRoot.Epidemic#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compartment</em>'.
	 * @see EpidemicRoot.Epidemic#getCompartment()
	 * @see #getEpidemic()
	 * @generated
	 */
	EReference getEpidemic_Compartment();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see EpidemicRoot.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link EpidemicRoot.Group#getFlows <em>Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flows</em>'.
	 * @see EpidemicRoot.Group#getFlows()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Flows();

	/**
	 * Returns the meta object for the containment reference list '{@link EpidemicRoot.Group#getCompartments <em>Compartments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartments</em>'.
	 * @see EpidemicRoot.Group#getCompartments()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Compartments();

	/**
	 * Returns the meta object for the reference list '{@link EpidemicRoot.Group#getSink <em>Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sink</em>'.
	 * @see EpidemicRoot.Group#getSink()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Sink();

	/**
	 * Returns the meta object for the reference list '{@link EpidemicRoot.Group#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source</em>'.
	 * @see EpidemicRoot.Group#getSource()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Source();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.UnitCompartment <em>Unit Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit Compartment</em>'.
	 * @see EpidemicRoot.UnitCompartment
	 * @generated
	 */
	EClass getUnitCompartment();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see EpidemicRoot.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the reference '{@link EpidemicRoot.Flow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see EpidemicRoot.Flow#getFrom()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_From();

	/**
	 * Returns the meta object for the reference '{@link EpidemicRoot.Flow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see EpidemicRoot.Flow#getTo()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_To();

	/**
	 * Returns the meta object for the attribute '{@link EpidemicRoot.Flow#getSourceParameters <em>Source Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Parameters</em>'.
	 * @see EpidemicRoot.Flow#getSourceParameters()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_SourceParameters();

	/**
	 * Returns the meta object for the attribute '{@link EpidemicRoot.Flow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see EpidemicRoot.Flow#getId()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Id();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see EpidemicRoot.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link EpidemicRoot.Contact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see EpidemicRoot.Contact#getContact()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact();

	/**
	 * Returns the meta object for the attribute '{@link EpidemicRoot.Contact#getContactParameters <em>Contact Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact Parameters</em>'.
	 * @see EpidemicRoot.Contact#getContactParameters()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_ContactParameters();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see EpidemicRoot.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.AbstractCompartment <em>Abstract Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Compartment</em>'.
	 * @see EpidemicRoot.AbstractCompartment
	 * @generated
	 */
	EClass getAbstractCompartment();

	/**
	 * Returns the meta object for the attribute '{@link EpidemicRoot.AbstractCompartment#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see EpidemicRoot.AbstractCompartment#getLabel()
	 * @see #getAbstractCompartment()
	 * @generated
	 */
	EAttribute getAbstractCompartment_Label();

	/**
	 * Returns the meta object for class '{@link EpidemicRoot.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see EpidemicRoot.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the containment reference list '{@link EpidemicRoot.Product#getCompartments <em>Compartments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartments</em>'.
	 * @see EpidemicRoot.Product#getCompartments()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Compartments();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpidemicRootFactory getEpidemicRootFactory();

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
		 * The meta object literal for the '{@link EpidemicRoot.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.EpidemicImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPIDEMIC__COMPARTMENT = eINSTANCE.getEpidemic_Compartment();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.GroupImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>Flows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__FLOWS = eINSTANCE.getGroup_Flows();

		/**
		 * The meta object literal for the '<em><b>Compartments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__COMPARTMENTS = eINSTANCE.getGroup_Compartments();

		/**
		 * The meta object literal for the '<em><b>Sink</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__SINK = eINSTANCE.getGroup_Sink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GROUP__SOURCE = eINSTANCE.getGroup_Source();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.UnitCompartmentImpl <em>Unit Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.UnitCompartmentImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getUnitCompartment()
		 * @generated
		 */
		EClass UNIT_COMPARTMENT = eINSTANCE.getUnitCompartment();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.FlowImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__FROM = eINSTANCE.getFlow_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__TO = eINSTANCE.getFlow_To();

		/**
		 * The meta object literal for the '<em><b>Source Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__SOURCE_PARAMETERS = eINSTANCE.getFlow_SourceParameters();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__ID = eINSTANCE.getFlow_Id();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.ContactImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getContact()
		 * @generated
		 */
		EClass CONTACT = eINSTANCE.getContact();

		/**
		 * The meta object literal for the '<em><b>Contact</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT__CONTACT = eINSTANCE.getContact_Contact();

		/**
		 * The meta object literal for the '<em><b>Contact Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT__CONTACT_PARAMETERS = eINSTANCE.getContact_ContactParameters();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.RateImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.AbstractCompartmentImpl <em>Abstract Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.AbstractCompartmentImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getAbstractCompartment()
		 * @generated
		 */
		EClass ABSTRACT_COMPARTMENT = eINSTANCE.getAbstractCompartment();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_COMPARTMENT__LABEL = eINSTANCE.getAbstractCompartment_Label();

		/**
		 * The meta object literal for the '{@link EpidemicRoot.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see EpidemicRoot.impl.ProductImpl
		 * @see EpidemicRoot.impl.EpidemicRootPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '<em><b>Compartments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__COMPARTMENTS = eINSTANCE.getProduct_Compartments();

	}

} //EpidemicRootPackage
