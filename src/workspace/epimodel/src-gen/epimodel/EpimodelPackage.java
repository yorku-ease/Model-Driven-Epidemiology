/**
 */
package epimodel;

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
 * @see epimodel.EpimodelFactory
 * @model kind="package"
 * @generated
 */
public interface EpimodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "epimodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/epimodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "epimodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	EpimodelPackage eINSTANCE = epimodel.impl.EpimodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link epimodel.impl.EpidemicWrapperImpl <em>Epidemic Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.EpidemicWrapperImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getEpidemicWrapper()
	 * @generated
	 */
	int EPIDEMIC_WRAPPER = 0;

	/**
	 * The feature id for the '<em><b>Epidemic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_WRAPPER__EPIDEMIC = 0;

	/**
	 * The number of structural features of the '<em>Epidemic Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_WRAPPER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Epidemic Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_WRAPPER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.EpidemicImpl <em>Epidemic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.EpidemicImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getEpidemic()
	 * @generated
	 */
	int EPIDEMIC = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC__ID = 0;

	/**
	 * The number of structural features of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Epidemic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EPIDEMIC_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.CompartmentWrapperImpl <em>Compartment Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.CompartmentWrapperImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getCompartmentWrapper()
	 * @generated
	 */
	int COMPARTMENT_WRAPPER = 2;

	/**
	 * The feature id for the '<em><b>Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_WRAPPER__COMPARTMENT = 0;

	/**
	 * The number of structural features of the '<em>Compartment Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_WRAPPER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Compartment Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_WRAPPER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.CompartmentImpl <em>Compartment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.CompartmentImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getCompartment()
	 * @generated
	 */
	int COMPARTMENT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT__ID = 0;

	/**
	 * The number of structural features of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Compartment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARTMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.FlowWrapperImpl <em>Flow Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.FlowWrapperImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getFlowWrapper()
	 * @generated
	 */
	int FLOW_WRAPPER = 4;

	/**
	 * The feature id for the '<em><b>Flow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_WRAPPER__FLOW = 0;

	/**
	 * The number of structural features of the '<em>Flow Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_WRAPPER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Flow Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_WRAPPER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link epimodel.impl.FlowImpl <em>Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see epimodel.impl.FlowImpl
	 * @see epimodel.impl.EpimodelPackageImpl#getFlow()
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
	int FLOW__ID = 0;

	/**
	 * The number of structural features of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOW_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class '{@link epimodel.EpidemicWrapper <em>Epidemic Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic Wrapper</em>'.
	 * @see epimodel.EpidemicWrapper
	 * @generated
	 */
	EClass getEpidemicWrapper();

	/**
	 * Returns the meta object for the containment reference '{@link epimodel.EpidemicWrapper#getEpidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Epidemic</em>'.
	 * @see epimodel.EpidemicWrapper#getEpidemic()
	 * @see #getEpidemicWrapper()
	 * @generated
	 */
	EReference getEpidemicWrapper_Epidemic();

	/**
	 * Returns the meta object for class '{@link epimodel.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Epidemic</em>'.
	 * @see epimodel.Epidemic
	 * @generated
	 */
	EClass getEpidemic();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.Epidemic#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.Epidemic#getId()
	 * @see #getEpidemic()
	 * @generated
	 */
	EAttribute getEpidemic_Id();

	/**
	 * Returns the meta object for class '{@link epimodel.CompartmentWrapper <em>Compartment Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment Wrapper</em>'.
	 * @see epimodel.CompartmentWrapper
	 * @generated
	 */
	EClass getCompartmentWrapper();

	/**
	 * Returns the meta object for the containment reference '{@link epimodel.CompartmentWrapper#getCompartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Compartment</em>'.
	 * @see epimodel.CompartmentWrapper#getCompartment()
	 * @see #getCompartmentWrapper()
	 * @generated
	 */
	EReference getCompartmentWrapper_Compartment();

	/**
	 * Returns the meta object for class '{@link epimodel.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compartment</em>'.
	 * @see epimodel.Compartment
	 * @generated
	 */
	EClass getCompartment();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.Compartment#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.Compartment#getId()
	 * @see #getCompartment()
	 * @generated
	 */
	EAttribute getCompartment_Id();

	/**
	 * Returns the meta object for class '{@link epimodel.FlowWrapper <em>Flow Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow Wrapper</em>'.
	 * @see epimodel.FlowWrapper
	 * @generated
	 */
	EClass getFlowWrapper();

	/**
	 * Returns the meta object for the containment reference '{@link epimodel.FlowWrapper#getFlow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Flow</em>'.
	 * @see epimodel.FlowWrapper#getFlow()
	 * @see #getFlowWrapper()
	 * @generated
	 */
	EReference getFlowWrapper_Flow();

	/**
	 * Returns the meta object for class '{@link epimodel.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Flow</em>'.
	 * @see epimodel.Flow
	 * @generated
	 */
	EClass getFlow();

	/**
	 * Returns the meta object for the attribute '{@link epimodel.Flow#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see epimodel.Flow#getId()
	 * @see #getFlow()
	 * @generated
	 */
	EAttribute getFlow_Id();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	EpimodelFactory getEpimodelFactory();

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
		 * The meta object literal for the '{@link epimodel.impl.EpidemicWrapperImpl <em>Epidemic Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.EpidemicWrapperImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getEpidemicWrapper()
		 * @generated
		 */
		EClass EPIDEMIC_WRAPPER = eINSTANCE.getEpidemicWrapper();

		/**
		 * The meta object literal for the '<em><b>Epidemic</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EPIDEMIC_WRAPPER__EPIDEMIC = eINSTANCE.getEpidemicWrapper_Epidemic();

		/**
		 * The meta object literal for the '{@link epimodel.impl.EpidemicImpl <em>Epidemic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.EpidemicImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getEpidemic()
		 * @generated
		 */
		EClass EPIDEMIC = eINSTANCE.getEpidemic();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EPIDEMIC__ID = eINSTANCE.getEpidemic_Id();

		/**
		 * The meta object literal for the '{@link epimodel.impl.CompartmentWrapperImpl <em>Compartment Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.CompartmentWrapperImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getCompartmentWrapper()
		 * @generated
		 */
		EClass COMPARTMENT_WRAPPER = eINSTANCE.getCompartmentWrapper();

		/**
		 * The meta object literal for the '<em><b>Compartment</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARTMENT_WRAPPER__COMPARTMENT = eINSTANCE.getCompartmentWrapper_Compartment();

		/**
		 * The meta object literal for the '{@link epimodel.impl.CompartmentImpl <em>Compartment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.CompartmentImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getCompartment()
		 * @generated
		 */
		EClass COMPARTMENT = eINSTANCE.getCompartment();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARTMENT__ID = eINSTANCE.getCompartment_Id();

		/**
		 * The meta object literal for the '{@link epimodel.impl.FlowWrapperImpl <em>Flow Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.FlowWrapperImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getFlowWrapper()
		 * @generated
		 */
		EClass FLOW_WRAPPER = eINSTANCE.getFlowWrapper();

		/**
		 * The meta object literal for the '<em><b>Flow</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FLOW_WRAPPER__FLOW = eINSTANCE.getFlowWrapper_Flow();

		/**
		 * The meta object literal for the '{@link epimodel.impl.FlowImpl <em>Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see epimodel.impl.FlowImpl
		 * @see epimodel.impl.EpimodelPackageImpl#getFlow()
		 * @generated
		 */
		EClass FLOW = eINSTANCE.getFlow();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOW__ID = eINSTANCE.getFlow_Id();

	}

} //EpimodelPackage
