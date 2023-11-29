/**
 */
package Epidemic;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import Epidemic.impl.*;

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
 * @see Epidemic.EpidemicFactory
 * @model kind="package"
 * @generated
 */
public interface EpidemicPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "Epidemic";

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
	EpidemicPackage eINSTANCE = EpidemicPackageImpl.init();

	/**
	 * The meta object id for the '{@link Epidemic.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.EpidemicImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getEpidemic()
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
	 * The meta object id for the '{@link Epidemic.impl.AbstractCompartmentImpl <em>Abstract Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.AbstractCompartmentImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getAbstractCompartment()
	 * @generated
	 */
	int ABSTRACT_COMPARTMENT = 7;

	/**
	 * The number of structural features of the '<em>Abstract Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_COMPARTMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link Epidemic.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.GroupImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 1;

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
	 * The meta object id for the '{@link Epidemic.impl.UnitCompartmentImpl <em>Unit Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.UnitCompartmentImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getUnitCompartment()
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
	int UNIT_COMPARTMENT__LABEL = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unit Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_COMPARTMENT_FEATURE_COUNT = ABSTRACT_COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link Epidemic.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.FlowImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getFlow()
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
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link Epidemic.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.ContactImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getContact()
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
	 * The meta object id for the '{@link Epidemic.impl.BatchImpl <em>Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.BatchImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getBatch()
	 * @generated
	 */
	int BATCH = 5;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__FROM = FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__TO = FLOW__TO;

	/**
	 * The feature id for the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__SOURCE_PARAMETERS = FLOW__SOURCE_PARAMETERS;

	/**
	 * The number of structural features of the '<em>Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_FEATURE_COUNT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link Epidemic.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.RateImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getRate()
	 * @generated
	 */
	int RATE = 6;

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
	 * The number of structural features of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FEATURE_COUNT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link Epidemic.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see Epidemic.impl.ProductImpl
	 * @see Epidemic.impl.EpidemicPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 8;

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
	 * Returns the meta object for class '{@link Epidemic.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see Epidemic.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the containment reference '{@link Epidemic.Epidemic#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compartment</em>'.
	 * @see Epidemic.Epidemic#getCompartment()
	 * @see #getEpidemic()
	 * @generated
	 */
	EReference getEpidemic_Compartment();

	/**
	 * Returns the meta object for class '{@link Epidemic.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see Epidemic.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link Epidemic.Group#getFlows <em>Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flows</em>'.
	 * @see Epidemic.Group#getFlows()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Flows();

	/**
	 * Returns the meta object for the containment reference list '{@link Epidemic.Group#getCompartments <em>Compartments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartments</em>'.
	 * @see Epidemic.Group#getCompartments()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Compartments();

	/**
	 * Returns the meta object for the reference list '{@link Epidemic.Group#getSink <em>Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sink</em>'.
	 * @see Epidemic.Group#getSink()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Sink();

	/**
	 * Returns the meta object for the reference list '{@link Epidemic.Group#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Source</em>'.
	 * @see Epidemic.Group#getSource()
	 * @see #getGroup()
	 * @generated
	 */
	EReference getGroup_Source();

	/**
	 * Returns the meta object for class '{@link Epidemic.UnitCompartment <em>Unit Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit Compartment</em>'.
	 * @see Epidemic.UnitCompartment
	 * @generated
	 */
	EClass getUnitCompartment();

	/**
	 * Returns the meta object for the attribute '{@link Epidemic.UnitCompartment#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see Epidemic.UnitCompartment#getLabel()
	 * @see #getUnitCompartment()
	 * @generated
	 */
	EAttribute getUnitCompartment_Label();

	/**
	 * Returns the meta object for class '{@link Epidemic.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see Epidemic.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the reference '{@link Epidemic.Flow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see Epidemic.Flow#getFrom()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_From();

	/**
	 * Returns the meta object for the reference '{@link Epidemic.Flow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see Epidemic.Flow#getTo()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_To();

	/**
	 * Returns the meta object for the attribute '{@link Epidemic.Flow#getSourceParameters <em>Source Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Parameters</em>'.
	 * @see Epidemic.Flow#getSourceParameters()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_SourceParameters();

	/**
	 * Returns the meta object for class '{@link Epidemic.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see Epidemic.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link Epidemic.Contact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see Epidemic.Contact#getContact()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact();

	/**
	 * Returns the meta object for the attribute '{@link Epidemic.Contact#getContactParameters <em>Contact Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact Parameters</em>'.
	 * @see Epidemic.Contact#getContactParameters()
	 * @see #getContact()
	 * @generated
	 */
	EAttribute getContact_ContactParameters();

	/**
	 * Returns the meta object for class '{@link Epidemic.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Batch</em>'.
	 * @see Epidemic.Batch
	 * @generated
	 */
	EClass getBatch();

	/**
	 * Returns the meta object for class '{@link Epidemic.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see Epidemic.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for class '{@link Epidemic.AbstractCompartment <em>Abstract Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Compartment</em>'.
	 * @see Epidemic.AbstractCompartment
	 * @generated
	 */
	EClass getAbstractCompartment();

	/**
	 * Returns the meta object for class '{@link Epidemic.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see Epidemic.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the containment reference list '{@link Epidemic.Product#getCompartments <em>Compartments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartments</em>'.
	 * @see Epidemic.Product#getCompartments()
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
	EpidemicFactory getEpidemicFactory();

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
		 * The meta object literal for the '{@link Epidemic.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.EpidemicImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getEpidemic()
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
		 * The meta object literal for the '{@link Epidemic.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.GroupImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getGroup()
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
		 * The meta object literal for the '{@link Epidemic.impl.UnitCompartmentImpl <em>Unit Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.UnitCompartmentImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getUnitCompartment()
		 * @generated
		 */
		EClass UNIT_COMPARTMENT = eINSTANCE.getUnitCompartment();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT_COMPARTMENT__LABEL = eINSTANCE.getUnitCompartment_Label();

		/**
		 * The meta object literal for the '{@link Epidemic.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.FlowImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getFlow()
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
		 * The meta object literal for the '{@link Epidemic.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.ContactImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getContact()
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
		 * The meta object literal for the '{@link Epidemic.impl.BatchImpl <em>Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.BatchImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getBatch()
		 * @generated
		 */
		EClass BATCH = eINSTANCE.getBatch();

		/**
		 * The meta object literal for the '{@link Epidemic.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.RateImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();

		/**
		 * The meta object literal for the '{@link Epidemic.impl.AbstractCompartmentImpl <em>Abstract Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.AbstractCompartmentImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getAbstractCompartment()
		 * @generated
		 */
		EClass ABSTRACT_COMPARTMENT = eINSTANCE.getAbstractCompartment();

		/**
		 * The meta object literal for the '{@link Epidemic.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see Epidemic.impl.ProductImpl
		 * @see Epidemic.impl.EpidemicPackageImpl#getProduct()
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

} //EpidemicPackage
