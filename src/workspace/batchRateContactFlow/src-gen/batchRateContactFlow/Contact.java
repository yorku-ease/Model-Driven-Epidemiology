/**
 */
package batchRateContactFlow;

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
 *   <li>{@link batchRateContactFlow.Contact#getContact <em>Contact</em>}</li>
 *   <li>{@link batchRateContactFlow.Contact#getContactParameters <em>Contact Parameters</em>}</li>
 * </ul>
 *
 * @see batchRateContactFlow.BatchRateContactFlowPackage#getContact()
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
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getContact_Contact()
	 * @model
	 * @generated
	 */
	Compartment getContact();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.Contact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(Compartment value);

	/**
	 * Returns the value of the '<em><b>Contact Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Parameters</em>' attribute.
	 * @see #setContactParameters(String)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getContact_ContactParameters()
	 * @model
	 * @generated
	 */
	String getContactParameters();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.Contact#getContactParameters <em>Contact Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Parameters</em>' attribute.
	 * @see #getContactParameters()
	 * @generated
	 */
	void setContactParameters(String value);

} // Contact
