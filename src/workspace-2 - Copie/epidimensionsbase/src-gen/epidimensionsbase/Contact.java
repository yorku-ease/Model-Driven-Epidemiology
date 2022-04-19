/**
 */
package epidimensionsbase;

import epibase.Compartment;
import epibase.Flow;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.Contact#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @see epidimensionsbase.EpidimensionsbasePackage#getContact()
 * @model superTypes="epidimensionsbase.Flow"
 * @generated
 */
public interface Contact extends EObject, Flow {
	/**
	 * Returns the value of the '<em><b>Contact</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' reference.
	 * @see #setContact(Compartment)
	 * @see epidimensionsbase.EpidimensionsbasePackage#getContact_Contact()
	 * @model type="epidimensionsbase.Compartment"
	 * @generated
	 */
	Compartment getContact();

	/**
	 * Sets the value of the '{@link epidimensionsbase.Contact#getContact <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact</em>' reference.
	 * @see #getContact()
	 * @generated
	 */
	void setContact(Compartment value);

} // Contact
