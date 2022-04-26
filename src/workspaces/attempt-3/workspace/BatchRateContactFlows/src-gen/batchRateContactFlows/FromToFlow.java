/**
 */
package batchRateContactFlows;

import epimodel.Compartment;
import epimodel.Flow;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>From To Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlows.FromToFlow#getFrom <em>From</em>}</li>
 *   <li>{@link batchRateContactFlows.FromToFlow#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see batchRateContactFlows.BatchRateContactFlowsPackage#getFromToFlow()
 * @model
 * @generated
 */
public interface FromToFlow extends Flow {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Compartment)
	 * @see batchRateContactFlows.BatchRateContactFlowsPackage#getFromToFlow_From()
	 * @model
	 * @generated
	 */
	Compartment getFrom();

	/**
	 * Sets the value of the '{@link batchRateContactFlows.FromToFlow#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Compartment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Compartment)
	 * @see batchRateContactFlows.BatchRateContactFlowsPackage#getFromToFlow_To()
	 * @model
	 * @generated
	 */
	Compartment getTo();

	/**
	 * Sets the value of the '{@link batchRateContactFlows.FromToFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Compartment value);

} // FromToFlow
