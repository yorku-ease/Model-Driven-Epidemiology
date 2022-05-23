/**
 */
package couplingFlow;

import epimodel.EpimodelPackage;

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
 * @see couplingFlow.CouplingFlowFactory
 * @model kind="package"
 * @generated
 */
public interface CouplingFlowPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "couplingFlow";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/couplingFlow";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "couplingFlow";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CouplingFlowPackage eINSTANCE = couplingFlow.impl.CouplingFlowPackageImpl.init();

	/**
	 * The meta object id for the '{@link couplingFlow.impl.CouplingFlowImpl <em>Coupling Flow</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see couplingFlow.impl.CouplingFlowImpl
	 * @see couplingFlow.impl.CouplingFlowPackageImpl#getCouplingFlow()
	 * @generated
	 */
	int COUPLING_FLOW = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW__ID = EpimodelPackage.FLOW__ID;

	/**
	 * The feature id for the '<em><b>First From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW__FIRST_FROM = EpimodelPackage.FLOW_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Second From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW__SECOND_FROM = EpimodelPackage.FLOW_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Second To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW__SECOND_TO = EpimodelPackage.FLOW_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>First To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW__FIRST_TO = EpimodelPackage.FLOW_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Coupling Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW_FEATURE_COUNT = EpimodelPackage.FLOW_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Coupling Flow</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COUPLING_FLOW_OPERATION_COUNT = EpimodelPackage.FLOW_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link couplingFlow.CouplingFlow <em>Coupling Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Coupling Flow</em>'.
	 * @see couplingFlow.CouplingFlow
	 * @generated
	 */
	EClass getCouplingFlow();

	/**
	 * Returns the meta object for the reference '{@link couplingFlow.CouplingFlow#getFirstFrom <em>First From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>First From</em>'.
	 * @see couplingFlow.CouplingFlow#getFirstFrom()
	 * @see #getCouplingFlow()
	 * @generated
	 */
	EReference getCouplingFlow_FirstFrom();

	/**
	 * Returns the meta object for the reference '{@link couplingFlow.CouplingFlow#getSecondFrom <em>Second From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Second From</em>'.
	 * @see couplingFlow.CouplingFlow#getSecondFrom()
	 * @see #getCouplingFlow()
	 * @generated
	 */
	EReference getCouplingFlow_SecondFrom();

	/**
	 * Returns the meta object for the reference '{@link couplingFlow.CouplingFlow#getSecondTo <em>Second To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Second To</em>'.
	 * @see couplingFlow.CouplingFlow#getSecondTo()
	 * @see #getCouplingFlow()
	 * @generated
	 */
	EReference getCouplingFlow_SecondTo();

	/**
	 * Returns the meta object for the reference '{@link couplingFlow.CouplingFlow#getFirstTo <em>First To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>First To</em>'.
	 * @see couplingFlow.CouplingFlow#getFirstTo()
	 * @see #getCouplingFlow()
	 * @generated
	 */
	EReference getCouplingFlow_FirstTo();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CouplingFlowFactory getCouplingFlowFactory();

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
		 * The meta object literal for the '{@link couplingFlow.impl.CouplingFlowImpl <em>Coupling Flow</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see couplingFlow.impl.CouplingFlowImpl
		 * @see couplingFlow.impl.CouplingFlowPackageImpl#getCouplingFlow()
		 * @generated
		 */
		EClass COUPLING_FLOW = eINSTANCE.getCouplingFlow();

		/**
		 * The meta object literal for the '<em><b>First From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUPLING_FLOW__FIRST_FROM = eINSTANCE.getCouplingFlow_FirstFrom();

		/**
		 * The meta object literal for the '<em><b>Second From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUPLING_FLOW__SECOND_FROM = eINSTANCE.getCouplingFlow_SecondFrom();

		/**
		 * The meta object literal for the '<em><b>Second To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUPLING_FLOW__SECOND_TO = eINSTANCE.getCouplingFlow_SecondTo();

		/**
		 * The meta object literal for the '<em><b>First To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COUPLING_FLOW__FIRST_TO = eINSTANCE.getCouplingFlow_FirstTo();

	}

} //CouplingFlowPackage
