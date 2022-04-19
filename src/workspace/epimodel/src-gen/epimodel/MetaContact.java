/**
 */
package epimodel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.MetaContact#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getMetaContact()
 * @model
 * @generated
 */
public interface MetaContact extends MetaFlow {
	/**
	 * Returns the value of the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' reference.
	 * @see #setContact(MetaCompartment)
	 * @see epimodel.EpimodelPackage#getMetaContact_Contact()
	 * @model
	 * @generated
	 */
	MetaCompartment getContact();

	/**
	 * Sets the value of the '{@link epimodel.MetaContact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(MetaCompartment value);

} // MetaContact
