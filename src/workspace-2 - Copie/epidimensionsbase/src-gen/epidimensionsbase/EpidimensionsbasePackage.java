/**
 */
package epidimensionsbase;

import epibase.EpibasePackage;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see epidimensionsbase.EpidimensionsbaseFactory
 * @model kind="package"
 * @generated
 */
public interface EpidimensionsbasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epidimensionsbase";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epidimensionsbase";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epidimensionsbase";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpidimensionsbasePackage eINSTANCE = epidimensionsbase.impl.EpidimensionsbasePackageImpl.init();

	/**
	 * The meta object id for the '{@link epidimensionsbase.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsbase.impl.EpidemicImpl
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 6;

	/**
	 * The meta object id for the '{@link epidimensionsbase.impl.CompartmentImpl <em>Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsbase.impl.CompartmentImpl
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getCompartment()
	 * @generated
	 */
	int COMPARTMENT = 0;

	/**
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epidimensionsbase.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsbase.impl.DimensionImpl
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = EpibasePackage.COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__COMPARTMENT = EpibasePackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = EpibasePackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = EpibasePackage.COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = EpibasePackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epibase.Flow <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epibase.Flow
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 2;

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
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epidimensionsbase.impl.BatchImpl <em>Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsbase.impl.BatchImpl
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getBatch()
	 * @generated
	 */
	int BATCH = 3;

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
	 * The number of structural features of the '<em>Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_FEATURE_COUNT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Batch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsbase.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsbase.impl.RateImpl
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getRate()
	 * @generated
	 */
	int RATE = 4;

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
	 * The number of structural features of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FEATURE_COUNT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsbase.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsbase.impl.ContactImpl
	 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 5;

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
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__COMPARTMENT = EpibasePackage.EPIDEMIC__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__FLOW = EpibasePackage.EPIDEMIC__FLOW;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__ID = EpibasePackage.EPIDEMIC__ID;

	/**
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = EpibasePackage.EPIDEMIC_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_OPERATION_COUNT = EpibasePackage.EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link epidimensionsbase.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epidimensionsbase.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for class '{@link epibase.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment</em>'.
	 * @see epibase.Compartment
	 * @model instanceClass="epibase.Compartment"
	 * @generated
	 */
	EClass getCompartment();

	/**
	 * Returns the meta object for class '{@link epidimensionsbase.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see epidimensionsbase.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link epidimensionsbase.Dimension#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartment</em>'.
	 * @see epidimensionsbase.Dimension#getCompartment()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Compartment();

	/**
	 * Returns the meta object for the containment reference list '{@link epidimensionsbase.Dimension#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow</em>'.
	 * @see epidimensionsbase.Dimension#getFlow()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow();

	/**
	 * Returns the meta object for class '{@link epibase.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see epibase.Flow
	 * @model instanceClass="epibase.Flow"
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the reference '{@link epibase.Flow#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see epibase.Flow#getFrom()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_From();

	/**
	 * Returns the meta object for the reference '{@link epibase.Flow#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see epibase.Flow#getTo()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_To();

	/**
	 * Returns the meta object for class '{@link epidimensionsbase.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Batch</em>'.
	 * @see epidimensionsbase.Batch
	 * @generated
	 */
	EClass getBatch();

	/**
	 * Returns the meta object for class '{@link epidimensionsbase.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see epidimensionsbase.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for class '{@link epidimensionsbase.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see epidimensionsbase.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link epidimensionsbase.Contact#getContact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact</em>'.
	 * @see epidimensionsbase.Contact#getContact()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpidimensionsbaseFactory getEpidimensionsbaseFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link epidimensionsbase.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsbase.impl.EpidemicImpl
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();
		/**
		 * The meta object literal for the '{@link epidimensionsbase.impl.CompartmentImpl <em>Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsbase.impl.CompartmentImpl
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getCompartment()
		 * @generated
		 */
		EClass COMPARTMENT = eINSTANCE.getCompartment();
		/**
		 * The meta object literal for the '{@link epidimensionsbase.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsbase.impl.DimensionImpl
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();
		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__COMPARTMENT = eINSTANCE.getDimension_Compartment();
		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__FLOW = eINSTANCE.getDimension_Flow();
		/**
		 * The meta object literal for the '{@link epibase.Flow <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epibase.Flow
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getFlow()
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
		 * The meta object literal for the '{@link epidimensionsbase.impl.BatchImpl <em>Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsbase.impl.BatchImpl
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getBatch()
		 * @generated
		 */
		EClass BATCH = eINSTANCE.getBatch();
		/**
		 * The meta object literal for the '{@link epidimensionsbase.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsbase.impl.RateImpl
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();
		/**
		 * The meta object literal for the '{@link epidimensionsbase.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsbase.impl.ContactImpl
		 * @see epidimensionsbase.impl.EpidimensionsbasePackageImpl#getContact()
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

	}

} //EpidimensionsbasePackage
