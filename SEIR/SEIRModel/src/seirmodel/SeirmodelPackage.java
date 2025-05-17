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
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__NAME = 0;

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
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = 3;

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
	 * The feature id for the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__RATE = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW__TARGET = 2;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.SusceptibleImpl <em>Susceptible</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SusceptibleImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSusceptible()
	 * @generated
	 */
	int SUSCEPTIBLE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUSCEPTIBLE__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUSCEPTIBLE__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUSCEPTIBLE__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Susceptible</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUSCEPTIBLE_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Susceptible</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUSCEPTIBLE_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.TreatedImpl <em>Treated</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.TreatedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getTreated()
	 * @generated
	 */
	int TREATED = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREATED__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREATED__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREATED__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Treated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREATED_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Treated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TREATED_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.ExposedImpl <em>Exposed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.ExposedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getExposed()
	 * @generated
	 */
	int EXPOSED = 12;

	/**
	 * The meta object id for the '{@link seirmodel.impl.SEIRModelImpl <em>SEIR Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SEIRModelImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSEIRModel()
	 * @generated
	 */
	int SEIR_MODEL = 4;

	/**
	 * The feature id for the '<em><b>Compartments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL__COMPARTMENTS = 0;

	/**
	 * The number of structural features of the '<em>SEIR Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>SEIR Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEIR_MODEL_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link seirmodel.impl.AIDSImpl <em>AIDS</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.AIDSImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getAIDS()
	 * @generated
	 */
	int AIDS = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AIDS__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AIDS__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AIDS__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>AIDS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AIDS_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>AIDS</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AIDS_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.HospitalizedImpl <em>Hospitalized</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.HospitalizedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getHospitalized()
	 * @generated
	 */
	int HOSPITALIZED = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSPITALIZED__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSPITALIZED__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSPITALIZED__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Hospitalized</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSPITALIZED_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Hospitalized</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSPITALIZED_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.DeathsImpl <em>Deaths</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.DeathsImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getDeaths()
	 * @generated
	 */
	int DEATHS = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATHS__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATHS__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATHS__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Deaths</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATHS_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Deaths</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEATHS_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.UntreatedInfectiousImpl <em>Untreated Infectious</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.UntreatedInfectiousImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getUntreatedInfectious()
	 * @generated
	 */
	int UNTREATED_INFECTIOUS = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTREATED_INFECTIOUS__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTREATED_INFECTIOUS__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTREATED_INFECTIOUS__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Untreated Infectious</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTREATED_INFECTIOUS_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Untreated Infectious</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNTREATED_INFECTIOUS_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.VaccinatedImpl <em>Vaccinated</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.VaccinatedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getVaccinated()
	 * @generated
	 */
	int VACCINATED = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VACCINATED__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VACCINATED__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VACCINATED__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Vaccinated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VACCINATED_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Vaccinated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VACCINATED_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.NoAccessImpl <em>No Access</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.NoAccessImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getNoAccess()
	 * @generated
	 */
	int NO_ACCESS = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NO_ACCESS__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NO_ACCESS__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NO_ACCESS__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>No Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NO_ACCESS_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>No Access</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NO_ACCESS_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.IsolatedAfterTestingPositiveImpl <em>Isolated After Testing Positive</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.IsolatedAfterTestingPositiveImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getIsolatedAfterTestingPositive()
	 * @generated
	 */
	int ISOLATED_AFTER_TESTING_POSITIVE = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATED_AFTER_TESTING_POSITIVE__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATED_AFTER_TESTING_POSITIVE__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATED_AFTER_TESTING_POSITIVE__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Isolated After Testing Positive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATED_AFTER_TESTING_POSITIVE_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Isolated After Testing Positive</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISOLATED_AFTER_TESTING_POSITIVE_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Exposed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Exposed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link seirmodel.impl.PostAcuteImpl <em>Post Acute</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.PostAcuteImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getPostAcute()
	 * @generated
	 */
	int POST_ACUTE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_ACUTE__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_ACUTE__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_ACUTE__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Post Acute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_ACUTE_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Post Acute</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POST_ACUTE_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;


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
	 * Returns the meta object for the attribute '{@link seirmodel.Compartment#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see seirmodel.Compartment#getName()
	 * @see #getCompartment()
	 * @generated
	 */
	EAttribute getCompartment_Name();

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
	 * Returns the meta object for class '{@link seirmodel.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see seirmodel.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the attribute '{@link seirmodel.Flow#getRate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate</em>'.
	 * @see seirmodel.Flow#getRate()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Rate();

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
	 * Returns the meta object for class '{@link seirmodel.Susceptible <em>Susceptible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Susceptible</em>'.
	 * @see seirmodel.Susceptible
	 * @generated
	 */
	EClass getSusceptible();

	/**
	 * Returns the meta object for class '{@link seirmodel.Treated <em>Treated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Treated</em>'.
	 * @see seirmodel.Treated
	 * @generated
	 */
	EClass getTreated();

	/**
	 * Returns the meta object for class '{@link seirmodel.Exposed <em>Exposed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exposed</em>'.
	 * @see seirmodel.Exposed
	 * @generated
	 */
	EClass getExposed();

	/**
	 * Returns the meta object for class '{@link seirmodel.PostAcute <em>Post Acute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Post Acute</em>'.
	 * @see seirmodel.PostAcute
	 * @generated
	 */
	EClass getPostAcute();

	/**
	 * Returns the meta object for class '{@link seirmodel.Vaccinated <em>Vaccinated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vaccinated</em>'.
	 * @see seirmodel.Vaccinated
	 * @generated
	 */
	EClass getVaccinated();

	/**
	 * Returns the meta object for class '{@link seirmodel.NoAccess <em>No Access</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>No Access</em>'.
	 * @see seirmodel.NoAccess
	 * @generated
	 */
	EClass getNoAccess();

	/**
	 * Returns the meta object for class '{@link seirmodel.IsolatedAfterTestingPositive <em>Isolated After Testing Positive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Isolated After Testing Positive</em>'.
	 * @see seirmodel.IsolatedAfterTestingPositive
	 * @generated
	 */
	EClass getIsolatedAfterTestingPositive();

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
	 * Returns the meta object for class '{@link seirmodel.AIDS <em>AIDS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>AIDS</em>'.
	 * @see seirmodel.AIDS
	 * @generated
	 */
	EClass getAIDS();

	/**
	 * Returns the meta object for class '{@link seirmodel.Hospitalized <em>Hospitalized</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hospitalized</em>'.
	 * @see seirmodel.Hospitalized
	 * @generated
	 */
	EClass getHospitalized();

	/**
	 * Returns the meta object for class '{@link seirmodel.Deaths <em>Deaths</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deaths</em>'.
	 * @see seirmodel.Deaths
	 * @generated
	 */
	EClass getDeaths();

	/**
	 * Returns the meta object for class '{@link seirmodel.UntreatedInfectious <em>Untreated Infectious</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Untreated Infectious</em>'.
	 * @see seirmodel.UntreatedInfectious
	 * @generated
	 */
	EClass getUntreatedInfectious();

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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARTMENT__NAME = eINSTANCE.getCompartment_Name();

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
		 * The meta object literal for the '{@link seirmodel.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.FlowImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();

		/**
		 * The meta object literal for the '<em><b>Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__RATE = eINSTANCE.getFlow_Rate();

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
		 * The meta object literal for the '{@link seirmodel.impl.SusceptibleImpl <em>Susceptible</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.SusceptibleImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getSusceptible()
		 * @generated
		 */
		EClass SUSCEPTIBLE = eINSTANCE.getSusceptible();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.TreatedImpl <em>Treated</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.TreatedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getTreated()
		 * @generated
		 */
		EClass TREATED = eINSTANCE.getTreated();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.ExposedImpl <em>Exposed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.ExposedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getExposed()
		 * @generated
		 */
		EClass EXPOSED = eINSTANCE.getExposed();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.PostAcuteImpl <em>Post Acute</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.PostAcuteImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getPostAcute()
		 * @generated
		 */
		EClass POST_ACUTE = eINSTANCE.getPostAcute();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.VaccinatedImpl <em>Vaccinated</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.VaccinatedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getVaccinated()
		 * @generated
		 */
		EClass VACCINATED = eINSTANCE.getVaccinated();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.NoAccessImpl <em>No Access</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.NoAccessImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getNoAccess()
		 * @generated
		 */
		EClass NO_ACCESS = eINSTANCE.getNoAccess();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.IsolatedAfterTestingPositiveImpl <em>Isolated After Testing Positive</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.IsolatedAfterTestingPositiveImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getIsolatedAfterTestingPositive()
		 * @generated
		 */
		EClass ISOLATED_AFTER_TESTING_POSITIVE = eINSTANCE.getIsolatedAfterTestingPositive();

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
		 * The meta object literal for the '{@link seirmodel.impl.AIDSImpl <em>AIDS</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.AIDSImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getAIDS()
		 * @generated
		 */
		EClass AIDS = eINSTANCE.getAIDS();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.HospitalizedImpl <em>Hospitalized</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.HospitalizedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getHospitalized()
		 * @generated
		 */
		EClass HOSPITALIZED = eINSTANCE.getHospitalized();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.DeathsImpl <em>Deaths</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.DeathsImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getDeaths()
		 * @generated
		 */
		EClass DEATHS = eINSTANCE.getDeaths();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.UntreatedInfectiousImpl <em>Untreated Infectious</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.UntreatedInfectiousImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getUntreatedInfectious()
		 * @generated
		 */
		EClass UNTREATED_INFECTIOUS = eINSTANCE.getUntreatedInfectious();

	}

} //SeirmodelPackage
