/**
 */
package epidimensionsrefgroup;

import epidimensionsbase.EpidimensionsbasePackage;

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
 * @see epidimensionsrefgroup.EpidimensionsrefgroupFactory
 * @model kind="package"
 * @generated
 */
public interface EpidimensionsrefgroupPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epidimensionsrefgroup";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epidimensionsrefgroup";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epidimensionsrefgroup";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpidimensionsrefgroupPackage eINSTANCE = epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl.init();

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.EpidemicImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 0;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__COMPARTMENT = EpidimensionsbasePackage.EPIDEMIC__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__FLOW = EpidimensionsbasePackage.EPIDEMIC__FLOW;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__ID = EpidimensionsbasePackage.EPIDEMIC__ID;

	/**
	 * The feature id for the '<em><b>Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__DIMENSION = EpidimensionsbasePackage.EPIDEMIC__DIMENSION;

	/**
	 * The feature id for the '<em><b>Idimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__IDIMENSION = EpidimensionsbasePackage.EPIDEMIC_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = EpidimensionsbasePackage.EPIDEMIC_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_OPERATION_COUNT = EpidimensionsbasePackage.EPIDEMIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.IDimension <em>IDimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.IDimension
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getIDimension()
	 * @generated
	 */
	int IDIMENSION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION__ID = EpidimensionsbasePackage.DIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION__COMPARTMENT = EpidimensionsbasePackage.DIMENSION__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION__FLOW = EpidimensionsbasePackage.DIMENSION__FLOW;

	/**
	 * The number of structural features of the '<em>IDimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION_FEATURE_COUNT = EpidimensionsbasePackage.DIMENSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IDimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDIMENSION_OPERATION_COUNT = EpidimensionsbasePackage.DIMENSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.DimensionImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = IDIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__COMPARTMENT = IDIMENSION__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW = IDIMENSION__FLOW;

	/**
	 * The feature id for the '<em><b>Flow 2</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__FLOW_2 = IDIMENSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Idimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__IDIMENSION = IDIMENSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = IDIMENSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = IDIMENSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.DimensionRefImpl <em>Dimension Ref</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.DimensionRefImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getDimensionRef()
	 * @generated
	 */
	int DIMENSION_REF = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF__ID = IDIMENSION__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF__COMPARTMENT = IDIMENSION__COMPARTMENT;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF__FLOW = IDIMENSION__FLOW;

	/**
	 * The feature id for the '<em><b>Idimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF__IDIMENSION = IDIMENSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Dimension Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF_FEATURE_COUNT = IDIMENSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Dimension Ref</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_REF_OPERATION_COUNT = IDIMENSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.CompartmentImpl <em>Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.CompartmentImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getCompartment()
	 * @generated
	 */
	int COMPARTMENT = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__ID = EpidimensionsbasePackage.COMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__COMPARTMENT = EpidimensionsbasePackage.COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__FLOW = EpidimensionsbasePackage.COMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = EpidimensionsbasePackage.COMPARTMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_OPERATION_COUNT = EpidimensionsbasePackage.COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.FlowImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__ID = EpidimensionsbasePackage.FLOW__ID;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__FROM = EpidimensionsbasePackage.FLOW__FROM;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__TO = EpidimensionsbasePackage.FLOW__TO;

	/**
	 * The feature id for the '<em><b>From dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__FROM_DIMENSION = EpidimensionsbasePackage.FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__TO_DIMENSION = EpidimensionsbasePackage.FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = EpidimensionsbasePackage.FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = EpidimensionsbasePackage.FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.RateImpl <em>Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.RateImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getRate()
	 * @generated
	 */
	int RATE = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__ID = FLOW__ID;

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
	 * The feature id for the '<em><b>From dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__FROM_DIMENSION = FLOW__FROM_DIMENSION;

	/**
	 * The feature id for the '<em><b>To dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE__TO_DIMENSION = FLOW__TO_DIMENSION;

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
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.BatchImpl <em>Batch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.BatchImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getBatch()
	 * @generated
	 */
	int BATCH = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__ID = FLOW__ID;

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
	 * The feature id for the '<em><b>From dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__FROM_DIMENSION = FLOW__FROM_DIMENSION;

	/**
	 * The feature id for the '<em><b>To dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH__TO_DIMENSION = FLOW__TO_DIMENSION;

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
	 * The meta object id for the '{@link epidimensionsrefgroup.impl.ContactImpl <em>Contact</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epidimensionsrefgroup.impl.ContactImpl
	 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getContact()
	 * @generated
	 */
	int CONTACT = 8;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__ID = FLOW__ID;

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
	 * The feature id for the '<em><b>From dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__FROM_DIMENSION = FLOW__FROM_DIMENSION;

	/**
	 * The feature id for the '<em><b>To dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__TO_DIMENSION = FLOW__TO_DIMENSION;

	/**
	 * The feature id for the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contact dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT__CONTACT_DIMENSION = FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FEATURE_COUNT = FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Contact</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epidimensionsrefgroup.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the containment reference list '{@link epidimensionsrefgroup.Epidemic#getIdimension <em>Idimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Idimension</em>'.
	 * @see epidimensionsrefgroup.Epidemic#getIdimension()
	 * @see #getEpidemic()
	 * @generated
	 */
	EReference getEpidemic_Idimension();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.IDimension <em>IDimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDimension</em>'.
	 * @see epidimensionsrefgroup.IDimension
	 * @generated
	 */
	EClass getIDimension();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see epidimensionsrefgroup.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the containment reference list '{@link epidimensionsrefgroup.Dimension#getFlow_2 <em>Flow 2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Flow 2</em>'.
	 * @see epidimensionsrefgroup.Dimension#getFlow_2()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Flow_2();

	/**
	 * Returns the meta object for the containment reference list '{@link epidimensionsrefgroup.Dimension#getIdimension <em>Idimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Idimension</em>'.
	 * @see epidimensionsrefgroup.Dimension#getIdimension()
	 * @see #getDimension()
	 * @generated
	 */
	EReference getDimension_Idimension();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.DimensionRef <em>Dimension Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension Ref</em>'.
	 * @see epidimensionsrefgroup.DimensionRef
	 * @generated
	 */
	EClass getDimensionRef();

	/**
	 * Returns the meta object for the reference '{@link epidimensionsrefgroup.DimensionRef#getIdimension <em>Idimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Idimension</em>'.
	 * @see epidimensionsrefgroup.DimensionRef#getIdimension()
	 * @see #getDimensionRef()
	 * @generated
	 */
	EReference getDimensionRef_Idimension();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment</em>'.
	 * @see epidimensionsrefgroup.Compartment
	 * @generated
	 */
	EClass getCompartment();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see epidimensionsrefgroup.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the reference '{@link epidimensionsrefgroup.Flow#getFrom_dimension <em>From dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From dimension</em>'.
	 * @see epidimensionsrefgroup.Flow#getFrom_dimension()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_From_dimension();

	/**
	 * Returns the meta object for the reference '{@link epidimensionsrefgroup.Flow#getTo_dimension <em>To dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To dimension</em>'.
	 * @see epidimensionsrefgroup.Flow#getTo_dimension()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_To_dimension();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate</em>'.
	 * @see epidimensionsrefgroup.Rate
	 * @generated
	 */
	EClass getRate();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Batch</em>'.
	 * @see epidimensionsrefgroup.Batch
	 * @generated
	 */
	EClass getBatch();

	/**
	 * Returns the meta object for class '{@link epidimensionsrefgroup.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact</em>'.
	 * @see epidimensionsrefgroup.Contact
	 * @generated
	 */
	EClass getContact();

	/**
	 * Returns the meta object for the reference '{@link epidimensionsrefgroup.Contact#getContact_dimension <em>Contact dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact dimension</em>'.
	 * @see epidimensionsrefgroup.Contact#getContact_dimension()
	 * @see #getContact()
	 * @generated
	 */
	EReference getContact_Contact_dimension();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpidimensionsrefgroupFactory getEpidimensionsrefgroupFactory();

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
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.EpidemicImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();

		/**
		 * The meta object literal for the '<em><b>Idimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPIDEMIC__IDIMENSION = eINSTANCE.getEpidemic_Idimension();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.IDimension <em>IDimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.IDimension
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getIDimension()
		 * @generated
		 */
		EClass IDIMENSION = eINSTANCE.getIDimension();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.DimensionImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '<em><b>Flow 2</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__FLOW_2 = eINSTANCE.getDimension_Flow_2();

		/**
		 * The meta object literal for the '<em><b>Idimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION__IDIMENSION = eINSTANCE.getDimension_Idimension();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.DimensionRefImpl <em>Dimension Ref</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.DimensionRefImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getDimensionRef()
		 * @generated
		 */
		EClass DIMENSION_REF = eINSTANCE.getDimensionRef();

		/**
		 * The meta object literal for the '<em><b>Idimension</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DIMENSION_REF__IDIMENSION = eINSTANCE.getDimensionRef_Idimension();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.CompartmentImpl <em>Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.CompartmentImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getCompartment()
		 * @generated
		 */
		EClass COMPARTMENT = eINSTANCE.getCompartment();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.FlowImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();

		/**
		 * The meta object literal for the '<em><b>From dimension</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__FROM_DIMENSION = eINSTANCE.getFlow_From_dimension();

		/**
		 * The meta object literal for the '<em><b>To dimension</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__TO_DIMENSION = eINSTANCE.getFlow_To_dimension();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.RateImpl <em>Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.RateImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getRate()
		 * @generated
		 */
		EClass RATE = eINSTANCE.getRate();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.BatchImpl <em>Batch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.BatchImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getBatch()
		 * @generated
		 */
		EClass BATCH = eINSTANCE.getBatch();

		/**
		 * The meta object literal for the '{@link epidimensionsrefgroup.impl.ContactImpl <em>Contact</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epidimensionsrefgroup.impl.ContactImpl
		 * @see epidimensionsrefgroup.impl.EpidimensionsrefgroupPackageImpl#getContact()
		 * @generated
		 */
		EClass CONTACT = eINSTANCE.getContact();

		/**
		 * The meta object literal for the '<em><b>Contact dimension</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT__CONTACT_DIMENSION = eINSTANCE.getContact_Contact_dimension();

	}

} //EpidimensionsrefgroupPackage
