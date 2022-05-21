/**
 */
package couplingFlow;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see couplingFlow.CouplingFlowPackage
 * @generated
 */
public interface CouplingFlowFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CouplingFlowFactory eINSTANCE = couplingFlow.impl.CouplingFlowFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Coupling Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Coupling Flow</em>'.
	 * @generated
	 */
	CouplingFlow createCouplingFlow();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	CouplingFlowPackage getCouplingFlowPackage();

} //CouplingFlowFactory
