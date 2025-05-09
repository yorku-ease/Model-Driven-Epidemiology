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
	 * The meta object id for the '{@link seirmodel.impl.ExposedImpl <em>Exposed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.ExposedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getExposed()
	 * @generated
	 */
	int EXPOSED = 3;

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
	 * The meta object id for the '{@link seirmodel.impl.ExposedNonIsolatedImpl <em>Exposed Non Isolated</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.ExposedNonIsolatedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getExposedNonIsolated()
	 * @generated
	 */
	int EXPOSED_NON_ISOLATED = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_NON_ISOLATED__NAME = EXPOSED__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_NON_ISOLATED__POPULATION = EXPOSED__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_NON_ISOLATED__OUTGOING_FLOWS = EXPOSED__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Exposed Non Isolated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_NON_ISOLATED_FEATURE_COUNT = EXPOSED_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Exposed Non Isolated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_NON_ISOLATED_OPERATION_COUNT = EXPOSED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.InfectiousImpl <em>Infectious</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.InfectiousImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getInfectious()
	 * @generated
	 */
	int INFECTIOUS = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFECTIOUS__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFECTIOUS__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFECTIOUS__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Infectious</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFECTIOUS_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Infectious</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INFECTIOUS_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.SymptomaticImpl <em>Symptomatic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SymptomaticImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSymptomatic()
	 * @generated
	 */
	int SYMPTOMATIC = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOMATIC__NAME = INFECTIOUS__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOMATIC__POPULATION = INFECTIOUS__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOMATIC__OUTGOING_FLOWS = INFECTIOUS__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Symptomatic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOMATIC_FEATURE_COUNT = INFECTIOUS_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Symptomatic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYMPTOMATIC_OPERATION_COUNT = INFECTIOUS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.AsymptomaticImpl <em>Asymptomatic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.AsymptomaticImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getAsymptomatic()
	 * @generated
	 */
	int ASYMPTOMATIC = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMPTOMATIC__NAME = INFECTIOUS__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMPTOMATIC__POPULATION = INFECTIOUS__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMPTOMATIC__OUTGOING_FLOWS = INFECTIOUS__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Asymptomatic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMPTOMATIC_FEATURE_COUNT = INFECTIOUS_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Asymptomatic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASYMPTOMATIC_OPERATION_COUNT = INFECTIOUS_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.RecoveredImpl <em>Recovered</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.RecoveredImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getRecovered()
	 * @generated
	 */
	int RECOVERED = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOVERED__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOVERED__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOVERED__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Recovered</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOVERED_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Recovered</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RECOVERED_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.ExposedIsolatedImpl <em>Exposed Isolated</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.ExposedIsolatedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getExposedIsolated()
	 * @generated
	 */
	int EXPOSED_ISOLATED = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_ISOLATED__NAME = EXPOSED__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_ISOLATED__POPULATION = EXPOSED__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_ISOLATED__OUTGOING_FLOWS = EXPOSED__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Exposed Isolated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_ISOLATED_FEATURE_COUNT = EXPOSED_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Exposed Isolated</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPOSED_ISOLATED_OPERATION_COUNT = EXPOSED_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link seirmodel.impl.SEIRModelImpl <em>SEIR Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SEIRModelImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSEIRModel()
	 * @generated
	 */
	int SEIR_MODEL = 10;

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
	 * The meta object id for the '{@link seirmodel.impl.PreclinicalImpl <em>Preclinical</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.PreclinicalImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getPreclinical()
	 * @generated
	 */
	int PRECLINICAL = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECLINICAL__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECLINICAL__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECLINICAL__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Preclinical</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECLINICAL_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Preclinical</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRECLINICAL_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.MildImpl <em>Mild</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.MildImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getMild()
	 * @generated
	 */
	int MILD = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MILD__NAME = SYMPTOMATIC__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MILD__POPULATION = SYMPTOMATIC__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MILD__OUTGOING_FLOWS = SYMPTOMATIC__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Mild</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MILD_FEATURE_COUNT = SYMPTOMATIC_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Mild</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MILD_OPERATION_COUNT = SYMPTOMATIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.SevereImpl <em>Severe</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SevereImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSevere()
	 * @generated
	 */
	int SEVERE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEVERE__NAME = SYMPTOMATIC__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEVERE__POPULATION = SYMPTOMATIC__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEVERE__OUTGOING_FLOWS = SYMPTOMATIC__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Severe</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEVERE_FEATURE_COUNT = SYMPTOMATIC_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Severe</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SEVERE_OPERATION_COUNT = SYMPTOMATIC_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link seirmodel.impl.HospitalizedImpl <em>Hospitalized</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.HospitalizedImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getHospitalized()
	 * @generated
	 */
	int HOSPITALIZED = 14;

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
	int DEATHS = 15;

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
	 * The meta object id for the '{@link seirmodel.impl.TestImpl <em>Test</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.TestImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getTest()
	 * @generated
	 */
	int TEST = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST__NAME = COMPARTMENT__NAME;

	/**
	 * The feature id for the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST__POPULATION = COMPARTMENT__POPULATION;

	/**
	 * The feature id for the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST__OUTGOING_FLOWS = COMPARTMENT__OUTGOING_FLOWS;

	/**
	 * The number of structural features of the '<em>Test</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_FEATURE_COUNT = COMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Test</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_OPERATION_COUNT = COMPARTMENT_OPERATION_COUNT + 0;


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
	 * Returns the meta object for class '{@link seirmodel.Exposed <em>Exposed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exposed</em>'.
	 * @see seirmodel.Exposed
	 * @generated
	 */
	EClass getExposed();

	/**
	 * Returns the meta object for class '{@link seirmodel.ExposedNonIsolated <em>Exposed Non Isolated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exposed Non Isolated</em>'.
	 * @see seirmodel.ExposedNonIsolated
	 * @generated
	 */
	EClass getExposedNonIsolated();

	/**
	 * Returns the meta object for class '{@link seirmodel.Symptomatic <em>Symptomatic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Symptomatic</em>'.
	 * @see seirmodel.Symptomatic
	 * @generated
	 */
	EClass getSymptomatic();

	/**
	 * Returns the meta object for class '{@link seirmodel.Asymptomatic <em>Asymptomatic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Asymptomatic</em>'.
	 * @see seirmodel.Asymptomatic
	 * @generated
	 */
	EClass getAsymptomatic();

	/**
	 * Returns the meta object for class '{@link seirmodel.Recovered <em>Recovered</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Recovered</em>'.
	 * @see seirmodel.Recovered
	 * @generated
	 */
	EClass getRecovered();

	/**
	 * Returns the meta object for class '{@link seirmodel.ExposedIsolated <em>Exposed Isolated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Exposed Isolated</em>'.
	 * @see seirmodel.ExposedIsolated
	 * @generated
	 */
	EClass getExposedIsolated();

	/**
	 * Returns the meta object for class '{@link seirmodel.Infectious <em>Infectious</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Infectious</em>'.
	 * @see seirmodel.Infectious
	 * @generated
	 */
	EClass getInfectious();

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
	 * Returns the meta object for class '{@link seirmodel.Preclinical <em>Preclinical</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Preclinical</em>'.
	 * @see seirmodel.Preclinical
	 * @generated
	 */
	EClass getPreclinical();

	/**
	 * Returns the meta object for class '{@link seirmodel.Mild <em>Mild</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mild</em>'.
	 * @see seirmodel.Mild
	 * @generated
	 */
	EClass getMild();

	/**
	 * Returns the meta object for class '{@link seirmodel.Severe <em>Severe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Severe</em>'.
	 * @see seirmodel.Severe
	 * @generated
	 */
	EClass getSevere();

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
	 * Returns the meta object for class '{@link seirmodel.Test <em>Test</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test</em>'.
	 * @see seirmodel.Test
	 * @generated
	 */
	EClass getTest();

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
		 * The meta object literal for the '{@link seirmodel.impl.ExposedImpl <em>Exposed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.ExposedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getExposed()
		 * @generated
		 */
		EClass EXPOSED = eINSTANCE.getExposed();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.ExposedNonIsolatedImpl <em>Exposed Non Isolated</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.ExposedNonIsolatedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getExposedNonIsolated()
		 * @generated
		 */
		EClass EXPOSED_NON_ISOLATED = eINSTANCE.getExposedNonIsolated();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.SymptomaticImpl <em>Symptomatic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.SymptomaticImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getSymptomatic()
		 * @generated
		 */
		EClass SYMPTOMATIC = eINSTANCE.getSymptomatic();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.AsymptomaticImpl <em>Asymptomatic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.AsymptomaticImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getAsymptomatic()
		 * @generated
		 */
		EClass ASYMPTOMATIC = eINSTANCE.getAsymptomatic();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.RecoveredImpl <em>Recovered</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.RecoveredImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getRecovered()
		 * @generated
		 */
		EClass RECOVERED = eINSTANCE.getRecovered();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.ExposedIsolatedImpl <em>Exposed Isolated</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.ExposedIsolatedImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getExposedIsolated()
		 * @generated
		 */
		EClass EXPOSED_ISOLATED = eINSTANCE.getExposedIsolated();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.InfectiousImpl <em>Infectious</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.InfectiousImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getInfectious()
		 * @generated
		 */
		EClass INFECTIOUS = eINSTANCE.getInfectious();

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
		 * The meta object literal for the '{@link seirmodel.impl.PreclinicalImpl <em>Preclinical</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.PreclinicalImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getPreclinical()
		 * @generated
		 */
		EClass PRECLINICAL = eINSTANCE.getPreclinical();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.MildImpl <em>Mild</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.MildImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getMild()
		 * @generated
		 */
		EClass MILD = eINSTANCE.getMild();

		/**
		 * The meta object literal for the '{@link seirmodel.impl.SevereImpl <em>Severe</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.SevereImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getSevere()
		 * @generated
		 */
		EClass SEVERE = eINSTANCE.getSevere();

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
		 * The meta object literal for the '{@link seirmodel.impl.TestImpl <em>Test</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see seirmodel.impl.TestImpl
		 * @see seirmodel.impl.SeirmodelPackageImpl#getTest()
		 * @generated
		 */
		EClass TEST = eINSTANCE.getTest();

	}

} //SeirmodelPackage
