/**
 */
package batchRateContactFlows;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see batchRateContactFlows.BatchRateContactFlowsPackage
 * @generated
 */
public interface BatchRateContactFlowsFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BatchRateContactFlowsFactory eINSTANCE = batchRateContactFlows.impl.BatchRateContactFlowsFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Batch</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Batch</em>'.
	 * @generated
	 */
	Batch createBatch();

	/**
	 * Returns a new object of class '<em>Rate</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rate</em>'.
	 * @generated
	 */
	Rate createRate();

	/**
	 * Returns a new object of class '<em>Contact</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Contact</em>'.
	 * @generated
	 */
	Contact createContact();

	/**
	 * Returns a new object of class '<em>From To Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>From To Flow</em>'.
	 * @generated
	 */
	FromToFlow createFromToFlow();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BatchRateContactFlowsPackage getBatchRateContactFlowsPackage();

} //BatchRateContactFlowsFactory
