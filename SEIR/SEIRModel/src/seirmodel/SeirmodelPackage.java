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
	 * The meta object id for the '{@link seirmodel.impl.SEIRModelImpl <em>SEIR Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see seirmodel.impl.SEIRModelImpl
	 * @see seirmodel.impl.SeirmodelPackageImpl#getSEIRModel()
	 * @generated
	 */
	int SEIR_MODEL = 2;

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

	}

} //SeirmodelPackage
