/**
 */
package epimodelgroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodelgroup.MetaContact#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @see epimodelgroup.EpimodelgroupPackage#getMetaContact()
 * @model
 * @generated
 */
public interface MetaContact extends MetaFlow {
	/**
	 * Returns the value of the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' reference.
	 * @see #setContact(IDimension)
	 * @see epimodelgroup.EpimodelgroupPackage#getMetaContact_Contact()
	 * @model
	 * @generated
	 */
	IDimension getContact();

	/**
	 * Sets the value of the '{@link epimodelgroup.MetaContact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(IDimension value);

} // MetaContact
