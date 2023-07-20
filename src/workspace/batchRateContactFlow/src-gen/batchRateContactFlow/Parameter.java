/**
 */
package batchRateContactFlow;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.Parameter#getName <em>Name</em>}</li>
 *   <li>{@link batchRateContactFlow.Parameter#isDepends_on_source <em>Depends on source</em>}</li>
 *   <li>{@link batchRateContactFlow.Parameter#isDepends_on_sink <em>Depends on sink</em>}</li>
 * </ul>
 *
 * @see batchRateContactFlow.BatchRateContactFlowPackage#getParameter()
 * @model
 * @generated
 */
public interface Parameter extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getParameter_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.Parameter#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Depends on source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Depends on source</em>' attribute.
	 * @see #setDepends_on_source(boolean)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getParameter_Depends_on_source()
	 * @model required="true"
	 * @generated
	 */
	boolean isDepends_on_source();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.Parameter#isDepends_on_source <em>Depends on source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depends on source</em>' attribute.
	 * @see #isDepends_on_source()
	 * @generated
	 */
	void setDepends_on_source(boolean value);

	/**
	 * Returns the value of the '<em><b>Depends on sink</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Depends on sink</em>' attribute.
	 * @see #setDepends_on_sink(boolean)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getParameter_Depends_on_sink()
	 * @model required="true"
	 * @generated
	 */
	boolean isDepends_on_sink();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.Parameter#isDepends_on_sink <em>Depends on sink</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Depends on sink</em>' attribute.
	 * @see #isDepends_on_sink()
	 * @generated
	 */
	void setDepends_on_sink(boolean value);

} // Parameter
