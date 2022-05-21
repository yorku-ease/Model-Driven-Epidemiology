/**
 */
package link;

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
 *   <li>{@link link.Link#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see link.LinkPackage#getLink()
 * @model
 * @generated
 */
public interface Link extends Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' reference.
	 * @see #setCompartment(Compartment)
	 * @see link.LinkPackage#getLink_Compartment()
	 * @model
	 * @generated
	 */
	Compartment getCompartment();

	/**
	 * Sets the value of the '{@link link.Link#getCompartment <em>Compartment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compartment</em>' reference.
	 * @see #getCompartment()
	 * @generated
	 */
	void setCompartment(Compartment value);

} // Link
