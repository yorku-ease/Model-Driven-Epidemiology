/**
 */
package epi_merged;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epi_merged.Contact#getContact <em>Contact</em>}</li>
 *   <li>{@link epi_merged.Contact#getFrom <em>From</em>}</li>
 *   <li>{@link epi_merged.Contact#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see epi_merged.Epi_mergedPackage#getContact()
 * @model
 * @generated
 */
public interface Contact extends Flow {
	/**
	 * Returns the value of the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' reference.
	 * @see #setContact(Compartment)
	 * @see epi_merged.Epi_mergedPackage#getContact_Contact()
	 * @model
	 * @generated
	 */
	Compartment getContact();

	/**
	 * Sets the value of the '{@link epi_merged.Contact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(Compartment value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Compartment)
	 * @see epi_merged.Epi_mergedPackage#getContact_From()
	 * @model
	 * @generated
	 */
	Compartment getFrom();

	/**
	 * Sets the value of the '{@link epi_merged.Contact#getFrom <em>From</em>}' reference.
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
	 * @see epi_merged.Epi_mergedPackage#getContact_To()
	 * @model
	 * @generated
	 */
	Compartment getTo();

	/**
	 * Sets the value of the '{@link epi_merged.Contact#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Compartment value);

} // Contact
