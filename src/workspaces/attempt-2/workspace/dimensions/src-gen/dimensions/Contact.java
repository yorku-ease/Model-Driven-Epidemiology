/**
 */
package dimensions;

import epibase.Compartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensions.Contact#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @see dimensions.DimensionsPackage#getContact()
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
	 * @see dimensions.DimensionsPackage#getContact_Contact()
	 * @model type="dimensions.Compartment"
	 * @generated
	 */
	Compartment getContact();

	/**
	 * Sets the value of the '{@link dimensions.Contact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(Compartment value);

} // Contact
