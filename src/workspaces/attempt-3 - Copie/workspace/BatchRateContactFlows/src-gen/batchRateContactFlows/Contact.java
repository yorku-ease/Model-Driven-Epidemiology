/**
 */
package batchRateContactFlows;

import epimodel.Compartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlows.Contact#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @see batchRateContactFlows.BatchRateContactFlowsPackage#getContact()
 * @model
 * @generated
 */
public interface Contact extends FromToFlow {
	/**
	 * Returns the value of the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' reference.
	 * @see #setContact(Compartment)
	 * @see batchRateContactFlows.BatchRateContactFlowsPackage#getContact_Contact()
	 * @model
	 * @generated
	 */
	Compartment getContact();

	/**
	 * Sets the value of the '{@link batchRateContactFlows.Contact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(Compartment value);

} // Contact
