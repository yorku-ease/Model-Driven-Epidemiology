/**
 */
package epidimensionsrefgroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.Contact#getContact_dimension <em>Contact dimension</em>}</li>
 * </ul>
 *
 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getContact()
 * @model
 * @generated
 */
public interface Contact extends Flow, epidimensionsbase.Contact {
	/**
	 * Returns the value of the '<em><b>Contact dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact dimension</em>' reference.
	 * @see #setContact_dimension(IDimension)
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getContact_Contact_dimension()
	 * @model
	 * @generated
	 */
	IDimension getContact_dimension();

	/**
	 * Sets the value of the '{@link epidimensionsrefgroup.Contact#getContact_dimension <em>Contact dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact dimension</em>' reference.
	 * @see #getContact_dimension()
	 * @generated
	 */
	void setContact_dimension(IDimension value);

} // Contact
