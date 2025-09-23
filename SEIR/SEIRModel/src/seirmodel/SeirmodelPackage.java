/**
 */
package seirmodel;

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
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see seirmodel.SeirmodelFactory
 * @model kind="package"
 * @generated
 */
public interface SeirmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "seirmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://example.com/seirmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "seir";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SeirmodelPackage eINSTANCE = seirmodel.impl.SeirmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link seirmodel.impl.CompartmentImpl <em>Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.CompartmentImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getCompartment()
	 * @generated
	 */
	int COMPARTMENT = 0;

	/**
	 * The feature id for the '<em><b>Primary Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__PRIMARY_NAME = 0;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__POPULATION = 1;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__OUTGOING_FLOWS = 2;

	/**
	 * The feature id for the '<em><b>Secondary Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__SECONDARY_NAME = 3;

	/**
	 * The feature id for the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__PRODUCT = 4;

	/**
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.FlowImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getFlow()
	 * @generated
	 */
	int FLOW = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__TARGET = 1;

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
	 * The meta object id for the '{@link seirmodel.impl.RateFlowImpl <em>Rate Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.RateFlowImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getRateFlow()
	 * @generated
	 */
	int RATE_FLOW = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FLOW__DESCRIPTION = FLOW__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FLOW__TARGET = FLOW__TARGET;

	/**
	 * The feature id for the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FLOW__RATE = FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stratum Specific Rates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FLOW__STRATUM_SPECIFIC_RATES = FLOW_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Rate Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FLOW_FEATURE_COUNT = FLOW_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Rate Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATE_FLOW_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.ContactFlowImpl <em>Contact Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.ContactFlowImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getContactFlow()
	 * @generated
	 */
	int CONTACT_FLOW = 3;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW__DESCRIPTION = FLOW__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW__TARGET = FLOW__TARGET;

	/**
	 * The feature id for the '<em><b>Contact Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW__CONTACT_COMPARTMENT = FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contact Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW__CONTACT_RATE = FLOW_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Contact Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW__CONTACT_PARAMETERS = FLOW_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Stratum Specific Rates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW__STRATUM_SPECIFIC_RATES = FLOW_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Contact Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW_FEATURE_COUNT = FLOW_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Contact Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTACT_FLOW_OPERATION_COUNT = FLOW_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.BirthSourceImpl <em>Birth Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.BirthSourceImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getBirthSource()
	 * @generated
	 */
	int BIRTH_SOURCE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE__RATE = 1;

	/**
	 * The feature id for the '<em><b>Target Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE__TARGET_COMPARTMENT = 2;

	/**
	 * The feature id for the '<em><b>Target Stratum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE__TARGET_STRATUM = 3;

	/**
	 * The feature id for the '<em><b>Fixed Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE__FIXED_RATE = 4;

	/**
	 * The number of structural features of the '<em>Birth Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Birth Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BIRTH_SOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.DeathSinkImpl <em>Death Sink</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.DeathSinkImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getDeathSink()
	 * @generated
	 */
	int DEATH_SINK = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATH_SINK__NAME = 0;

	/**
	 * The feature id for the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATH_SINK__RATE = 1;

	/**
	 * The feature id for the '<em><b>Source Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATH_SINK__SOURCE_COMPARTMENT = 2;


	/**
	 * The feature id for the '<em><b>Source Stratum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATH_SINK__SOURCE_STRATUM = 3;

	/**
	 * The number of structural features of the '<em>Death Sink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATH_SINK_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Death Sink</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATH_SINK_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.StratumSpecificRateImpl <em>Stratum Specific Rate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.StratumSpecificRateImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getStratumSpecificRate()
	 * @generated
	 */
	int STRATUM_SPECIFIC_RATE = 6;

	/**
	 * The feature id for the '<em><b>Stratum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRATUM_SPECIFIC_RATE__STRATUM = 0;

	/**
	 * The feature id for the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRATUM_SPECIFIC_RATE__RATE = 1;

	/**
	 * The feature id for the '<em><b>Multiplier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRATUM_SPECIFIC_RATE__MULTIPLIER = 2;

	/**
	 * The number of structural features of the '<em>Stratum Specific Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRATUM_SPECIFIC_RATE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Stratum Specific Rate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRATUM_SPECIFIC_RATE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.GroupImpl <em>Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.GroupImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getGroup()
	 * @generated
	 */
	int GROUP = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP__VALUES = 2;

	/**
	 * The number of structural features of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.ProductImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getProduct()
	 * @generated
	 */
	int PRODUCT = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT__GROUPS = 2;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRODUCT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.SEIRModelImpl <em>SEIR Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SEIRModelImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSEIRModel()
	 * @generated
	 */
	int SEIR_MODEL = 9;

	/**
	 * The feature id for the '<em><b>Compartments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__COMPARTMENTS = 0;

	/**
	 * The feature id for the '<em><b>Birth Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__BIRTH_SOURCES = 1;

	/**
	 * The feature id for the '<em><b>Death Sinks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__DEATH_SINKS = 2;

	/**
	 * The feature id for the '<em><b>Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__GROUPS = 3;

	/**
	 * The feature id for the '<em><b>Products</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__PRODUCTS = 4;

	/**
	 * The feature id for the '<em><b>Total Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__TOTAL_POPULATION = 5;

	/**
	 * The feature id for the '<em><b>Global Birth Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__GLOBAL_BIRTH_RATE = 6;

	/**
	 * The feature id for the '<em><b>Global Death Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__GLOBAL_DEATH_RATE = 7;

	/**
	 * The number of structural features of the '<em>SEIR Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>SEIR Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link seirmodel.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment</em>'.
	 * @see seirmodel.Compartment
	 * @generated
	 */
	EClass getCompartment();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Compartment#getPrimaryName <em>Primary Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primary Name</em>'.
	 * @see seirmodel.Compartment#getPrimaryName()
	 * @see #getCompartment()
	 * @generated
	 */
	EAttribute getCompartment_PrimaryName();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Compartment#getPopulation <em>Population</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Population</em>'.
	 * @see seirmodel.Compartment#getPopulation()
	 * @see #getCompartment()
	 * @generated
	 */
	EAttribute getCompartment_Population();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.Compartment#getOutgoingFlows <em>Outgoing Flows</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outgoing Flows</em>'.
	 * @see seirmodel.Compartment#getOutgoingFlows()
	 * @see #getCompartment()
	 * @generated
	 */
	EReference getCompartment_OutgoingFlows();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Compartment#getSecondaryName <em>Secondary Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Secondary Name</em>'.
	 * @see seirmodel.Compartment#getSecondaryName()
	 * @see #getCompartment()
	 * @generated
	 */
	EAttribute getCompartment_SecondaryName();

	/**
	 * Returns the meta object for the reference '{@link seirmodel.Compartment#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Product</em>'.
	 * @see seirmodel.Compartment#getProduct()
	 * @see #getCompartment()
	 * @generated
	 */
	EReference getCompartment_Product();

	/**
	 * Returns the meta object for class '{@link seirmodel.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see seirmodel.Flow
	 * @generated
	 */
	EClass getFlow();


	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Flow#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see seirmodel.Flow#getDescription()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Description();

	/**
	 * Returns the meta object for the reference '{@link seirmodel.Flow#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see seirmodel.Flow#getTarget()
	 * @see #getFlow()
	 * @generated
	 */
	EReference getFlow_Target();

	/**
	 * Returns the meta object for class '{@link seirmodel.RateFlow <em>Rate Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rate Flow</em>'.
	 * @see seirmodel.RateFlow
	 * @generated
	 */
	EClass getRateFlow();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.RateFlow#getRate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate</em>'.
	 * @see seirmodel.RateFlow#getRate()
	 * @see #getRateFlow()
	 * @generated
	 */
	EAttribute getRateFlow_Rate();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.RateFlow#getStratumSpecificRates <em>Stratum Specific Rates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Stratum Specific Rates</em>'.
	 * @see seirmodel.RateFlow#getStratumSpecificRates()
	 * @see #getRateFlow()
	 * @generated
	 */
	EReference getRateFlow_StratumSpecificRates();

	/**
	 * Returns the meta object for class '{@link seirmodel.ContactFlow <em>Contact Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Contact Flow</em>'.
	 * @see seirmodel.ContactFlow
	 * @generated
	 */
	EClass getContactFlow();

	/**
	 * Returns the meta object for the reference '{@link seirmodel.ContactFlow#getContactCompartment <em>Contact Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Contact Compartment</em>'.
	 * @see seirmodel.ContactFlow#getContactCompartment()
	 * @see #getContactFlow()
	 * @generated
	 */
	EReference getContactFlow_ContactCompartment();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.ContactFlow#getContactRate <em>Contact Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact Rate</em>'.
	 * @see seirmodel.ContactFlow#getContactRate()
	 * @see #getContactFlow()
	 * @generated
	 */
	EAttribute getContactFlow_ContactRate();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.ContactFlow#getContactParameters <em>Contact Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contact Parameters</em>'.
	 * @see seirmodel.ContactFlow#getContactParameters()
	 * @see #getContactFlow()
	 * @generated
	 */
	EAttribute getContactFlow_ContactParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.ContactFlow#getStratumSpecificRates <em>Stratum Specific Rates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Stratum Specific Rates</em>'.
	 * @see seirmodel.ContactFlow#getStratumSpecificRates()
	 * @see #getContactFlow()
	 * @generated
	 */
	EReference getContactFlow_StratumSpecificRates();

	/**
	 * Returns the meta object for class '{@link seirmodel.BirthSource <em>Birth Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Birth Source</em>'.
	 * @see seirmodel.BirthSource
	 * @generated
	 */
	EClass getBirthSource();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.BirthSource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see seirmodel.BirthSource#getName()
	 * @see #getBirthSource()
	 * @generated
	 */
	EAttribute getBirthSource_Name();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.BirthSource#getRate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate</em>'.
	 * @see seirmodel.BirthSource#getRate()
	 * @see #getBirthSource()
	 * @generated
	 */
	EAttribute getBirthSource_Rate();

	/**
	 * Returns the meta object for the reference '{@link seirmodel.BirthSource#getTargetCompartment <em>Target Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Compartment</em>'.
	 * @see seirmodel.BirthSource#getTargetCompartment()
	 * @see #getBirthSource()
	 * @generated
	 */
	EReference getBirthSource_TargetCompartment();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.BirthSource#getTargetStratum <em>Target Stratum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Stratum</em>'.
	 * @see seirmodel.BirthSource#getTargetStratum()
	 * @see #getBirthSource()
	 * @generated
	 */
	EAttribute getBirthSource_TargetStratum();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.BirthSource#isFixedRate <em>Fixed Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fixed Rate</em>'.
	 * @see seirmodel.BirthSource#isFixedRate()
	 * @see #getBirthSource()
	 * @generated
	 */
	EAttribute getBirthSource_FixedRate();

	/**
	 * Returns the meta object for class '{@link seirmodel.DeathSink <em>Death Sink</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Death Sink</em>'.
	 * @see seirmodel.DeathSink
	 * @generated
	 */
	EClass getDeathSink();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.DeathSink#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see seirmodel.DeathSink#getName()
	 * @see #getDeathSink()
	 * @generated
	 */
	EAttribute getDeathSink_Name();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.DeathSink#getRate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate</em>'.
	 * @see seirmodel.DeathSink#getRate()
	 * @see #getDeathSink()
	 * @generated
	 */
	EAttribute getDeathSink_Rate();

	/**
	 * Returns the meta object for the reference '{@link seirmodel.DeathSink#getSourceCompartment <em>Source Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Compartment</em>'.
	 * @see seirmodel.DeathSink#getSourceCompartment()
	 * @see #getDeathSink()
	 * @generated
	 */
	EReference getDeathSink_SourceCompartment();


	/**
	 * Returns the meta object for the attribute '{@link seirmodel.DeathSink#getSourceStratum <em>Source Stratum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Stratum</em>'.
	 * @see seirmodel.DeathSink#getSourceStratum()
	 * @see #getDeathSink()
	 * @generated
	 */
	EAttribute getDeathSink_SourceStratum();

	/**
	 * Returns the meta object for class '{@link seirmodel.StratumSpecificRate <em>Stratum Specific Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stratum Specific Rate</em>'.
	 * @see seirmodel.StratumSpecificRate
	 * @generated
	 */
	EClass getStratumSpecificRate();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.StratumSpecificRate#getStratum <em>Stratum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stratum</em>'.
	 * @see seirmodel.StratumSpecificRate#getStratum()
	 * @see #getStratumSpecificRate()
	 * @generated
	 */
	EAttribute getStratumSpecificRate_Stratum();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.StratumSpecificRate#getRate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate</em>'.
	 * @see seirmodel.StratumSpecificRate#getRate()
	 * @see #getStratumSpecificRate()
	 * @generated
	 */
	EAttribute getStratumSpecificRate_Rate();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.StratumSpecificRate#getMultiplier <em>Multiplier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiplier</em>'.
	 * @see seirmodel.StratumSpecificRate#getMultiplier()
	 * @see #getStratumSpecificRate()
	 * @generated
	 */
	EAttribute getStratumSpecificRate_Multiplier();

	/**
	 * Returns the meta object for class '{@link seirmodel.SEIRModel <em>SEIR Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SEIR Model</em>'.
	 * @see seirmodel.SEIRModel
	 * @generated
	 */
	EClass getSEIRModel();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.SEIRModel#getCompartments <em>Compartments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compartments</em>'.
	 * @see seirmodel.SEIRModel#getCompartments()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EReference getSEIRModel_Compartments();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.SEIRModel#getBirthSources <em>Birth Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Birth Sources</em>'.
	 * @see seirmodel.SEIRModel#getBirthSources()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EReference getSEIRModel_BirthSources();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.SEIRModel#getDeathSinks <em>Death Sinks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Death Sinks</em>'.
	 * @see seirmodel.SEIRModel#getDeathSinks()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EReference getSEIRModel_DeathSinks();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.SEIRModel#getTotalPopulation <em>Total Population</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Total Population</em>'.
	 * @see seirmodel.SEIRModel#getTotalPopulation()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EAttribute getSEIRModel_TotalPopulation();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.SEIRModel#getGlobalBirthRate <em>Global Birth Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Global Birth Rate</em>'.
	 * @see seirmodel.SEIRModel#getGlobalBirthRate()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EAttribute getSEIRModel_GlobalBirthRate();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.SEIRModel#getGlobalDeathRate <em>Global Death Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Global Death Rate</em>'.
	 * @see seirmodel.SEIRModel#getGlobalDeathRate()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EAttribute getSEIRModel_GlobalDeathRate();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.SEIRModel#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Groups</em>'.
	 * @see seirmodel.SEIRModel#getGroups()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EReference getSEIRModel_Groups();

	/**
	 * Returns the meta object for the containment reference list '{@link seirmodel.SEIRModel#getProducts <em>Products</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Products</em>'.
	 * @see seirmodel.SEIRModel#getProducts()
	 * @see #getSEIRModel()
	 * @generated
	 */
	EReference getSEIRModel_Products();

	/**
	 * Returns the meta object for class '{@link seirmodel.Group <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Group</em>'.
	 * @see seirmodel.Group
	 * @generated
	 */
	EClass getGroup();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Group#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see seirmodel.Group#getName()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Name();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Group#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see seirmodel.Group#getDescription()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Description();

	/**
	 * Returns the meta object for the attribute list '{@link seirmodel.Group#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see seirmodel.Group#getValues()
	 * @see #getGroup()
	 * @generated
	 */
	EAttribute getGroup_Values();

	/**
	 * Returns the meta object for class '{@link seirmodel.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see seirmodel.Product
	 * @generated
	 */
	EClass getProduct();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Product#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see seirmodel.Product#getName()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Name();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Product#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see seirmodel.Product#getDescription()
	 * @see #getProduct()
	 * @generated
	 */
	EAttribute getProduct_Description();

	/**
	 * Returns the meta object for the reference list '{@link seirmodel.Product#getGroups <em>Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Groups</em>'.
	 * @see seirmodel.Product#getGroups()
	 * @see #getProduct()
	 * @generated
	 */
	EReference getProduct_Groups();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SeirmodelFactory getSeirmodelFactory();

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
		 * The meta object literal for the '{@link seirmodel.impl.CompartmentImpl <em>Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.CompartmentImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getCompartment()
		 * @generated
		 */
		EClass COMPARTMENT = eINSTANCE.getCompartment();

		/**
		 * The meta object literal for the '<em><b>Primary Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARTMENT__PRIMARY_NAME = eINSTANCE.getCompartment_PrimaryName();

		/**
		 * The meta object literal for the '<em><b>Population</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARTMENT__POPULATION = eINSTANCE.getCompartment_Population();

		/**
		 * The meta object literal for the '<em><b>Outgoing Flows</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARTMENT__OUTGOING_FLOWS = eINSTANCE.getCompartment_OutgoingFlows();

		/**
		 * The meta object literal for the '<em><b>Secondary Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARTMENT__SECONDARY_NAME = eINSTANCE.getCompartment_SecondaryName();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARTMENT__PRODUCT = eINSTANCE.getCompartment_Product();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.FlowImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();


		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__DESCRIPTION = eINSTANCE.getFlow_Description();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW__TARGET = eINSTANCE.getFlow_Target();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.RateFlowImpl <em>Rate Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.RateFlowImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getRateFlow()
		 * @generated
		 */
		EClass RATE_FLOW = eINSTANCE.getRateFlow();

		/**
		 * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RATE_FLOW__RATE = eINSTANCE.getRateFlow_Rate();

		/**
		 * The meta object literal for the '<em><b>Stratum Specific Rates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RATE_FLOW__STRATUM_SPECIFIC_RATES = eINSTANCE.getRateFlow_StratumSpecificRates();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.ContactFlowImpl <em>Contact Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.ContactFlowImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getContactFlow()
		 * @generated
		 */
		EClass CONTACT_FLOW = eINSTANCE.getContactFlow();

		/**
		 * The meta object literal for the '<em><b>Contact Compartment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT_FLOW__CONTACT_COMPARTMENT = eINSTANCE.getContactFlow_ContactCompartment();

		/**
		 * The meta object literal for the '<em><b>Contact Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_FLOW__CONTACT_RATE = eINSTANCE.getContactFlow_ContactRate();

		/**
		 * The meta object literal for the '<em><b>Contact Parameters</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONTACT_FLOW__CONTACT_PARAMETERS = eINSTANCE.getContactFlow_ContactParameters();

		/**
		 * The meta object literal for the '<em><b>Stratum Specific Rates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTACT_FLOW__STRATUM_SPECIFIC_RATES = eINSTANCE.getContactFlow_StratumSpecificRates();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.BirthSourceImpl <em>Birth Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.BirthSourceImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getBirthSource()
		 * @generated
		 */
		EClass BIRTH_SOURCE = eINSTANCE.getBirthSource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BIRTH_SOURCE__NAME = eINSTANCE.getBirthSource_Name();

		/**
		 * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BIRTH_SOURCE__RATE = eINSTANCE.getBirthSource_Rate();

		/**
		 * The meta object literal for the '<em><b>Target Compartment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BIRTH_SOURCE__TARGET_COMPARTMENT = eINSTANCE.getBirthSource_TargetCompartment();

		/**
		 * The meta object literal for the '<em><b>Target Stratum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BIRTH_SOURCE__TARGET_STRATUM = eINSTANCE.getBirthSource_TargetStratum();

		/**
		 * The meta object literal for the '<em><b>Fixed Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BIRTH_SOURCE__FIXED_RATE = eINSTANCE.getBirthSource_FixedRate();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.DeathSinkImpl <em>Death Sink</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.DeathSinkImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getDeathSink()
		 * @generated
		 */
		EClass DEATH_SINK = eINSTANCE.getDeathSink();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEATH_SINK__NAME = eINSTANCE.getDeathSink_Name();

		/**
		 * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEATH_SINK__RATE = eINSTANCE.getDeathSink_Rate();

		/**
		 * The meta object literal for the '<em><b>Source Compartment</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEATH_SINK__SOURCE_COMPARTMENT = eINSTANCE.getDeathSink_SourceCompartment();


		/**
		 * The meta object literal for the '<em><b>Source Stratum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEATH_SINK__SOURCE_STRATUM = eINSTANCE.getDeathSink_SourceStratum();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.StratumSpecificRateImpl <em>Stratum Specific Rate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.StratumSpecificRateImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getStratumSpecificRate()
		 * @generated
		 */
		EClass STRATUM_SPECIFIC_RATE = eINSTANCE.getStratumSpecificRate();

		/**
		 * The meta object literal for the '<em><b>Stratum</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRATUM_SPECIFIC_RATE__STRATUM = eINSTANCE.getStratumSpecificRate_Stratum();

		/**
		 * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRATUM_SPECIFIC_RATE__RATE = eINSTANCE.getStratumSpecificRate_Rate();

		/**
		 * The meta object literal for the '<em><b>Multiplier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRATUM_SPECIFIC_RATE__MULTIPLIER = eINSTANCE.getStratumSpecificRate_Multiplier();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.SEIRModelImpl <em>SEIR Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.SEIRModelImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getSEIRModel()
		 * @generated
		 */
		EClass SEIR_MODEL = eINSTANCE.getSEIRModel();

		/**
		 * The meta object literal for the '<em><b>Compartments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEIR_MODEL__COMPARTMENTS = eINSTANCE.getSEIRModel_Compartments();

		/**
		 * The meta object literal for the '<em><b>Birth Sources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEIR_MODEL__BIRTH_SOURCES = eINSTANCE.getSEIRModel_BirthSources();

		/**
		 * The meta object literal for the '<em><b>Death Sinks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEIR_MODEL__DEATH_SINKS = eINSTANCE.getSEIRModel_DeathSinks();

		/**
		 * The meta object literal for the '<em><b>Total Population</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEIR_MODEL__TOTAL_POPULATION = eINSTANCE.getSEIRModel_TotalPopulation();

		/**
		 * The meta object literal for the '<em><b>Global Birth Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEIR_MODEL__GLOBAL_BIRTH_RATE = eINSTANCE.getSEIRModel_GlobalBirthRate();

		/**
		 * The meta object literal for the '<em><b>Global Death Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SEIR_MODEL__GLOBAL_DEATH_RATE = eINSTANCE.getSEIRModel_GlobalDeathRate();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEIR_MODEL__GROUPS = eINSTANCE.getSEIRModel_Groups();

		/**
		 * The meta object literal for the '<em><b>Products</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SEIR_MODEL__PRODUCTS = eINSTANCE.getSEIRModel_Products();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.GroupImpl <em>Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.GroupImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getGroup()
		 * @generated
		 */
		EClass GROUP = eINSTANCE.getGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__NAME = eINSTANCE.getGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__DESCRIPTION = eINSTANCE.getGroup_Description();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GROUP__VALUES = eINSTANCE.getGroup_Values();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.ProductImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__NAME = eINSTANCE.getProduct_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__DESCRIPTION = eINSTANCE.getProduct_Description();

		/**
		 * The meta object literal for the '<em><b>Groups</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__GROUPS = eINSTANCE.getProduct_Groups();

	}

} //SeirmodelPackage
