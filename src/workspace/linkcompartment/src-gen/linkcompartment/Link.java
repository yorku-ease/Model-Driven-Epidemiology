/**
 */
package linkcompartment;

import epimodel.Compartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link linkcompartment.Link#getLink <em>Link</em>}</li>
 * </ul>
 *
 * @see linkcompartment.LinkcompartmentPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends Compartment {
	/**
	 * Returns the value of the '<em><b>Link</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' reference.
	 * @see #setLink(Compartment)
	 * @see linkcompartment.LinkcompartmentPackage#getLink_Link()
	 * @model
	 * @generated
	 */
	Compartment getLink();

	/**
	 * Sets the value of the '{@link linkcompartment.Link#getLink <em>Link</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Link</em>' reference.
	 * @see #getLink()
	 * @generated
	 */
	void setLink(Compartment value);

} // Link
