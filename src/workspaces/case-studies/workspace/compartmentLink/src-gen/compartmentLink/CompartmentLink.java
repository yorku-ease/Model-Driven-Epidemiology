/**
 */
package compartmentLink;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compartment Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentLink.CompartmentLink#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see compartmentLink.CompartmentLinkPackage#getCompartmentLink()
 * @model
 * @generated
 */
public interface CompartmentLink extends Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' reference.
	 * @see #setCompartment(CompartmentWrapper)
	 * @see compartmentLink.CompartmentLinkPackage#getCompartmentLink_Compartment()
	 * @model
	 * @generated
	 */
	CompartmentWrapper getCompartment();

	/**
	 * Sets the value of the '{@link compartmentLink.CompartmentLink#getCompartment <em>Compartment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compartment</em>' reference.
	 * @see #getCompartment()
	 * @generated
	 */
	void setCompartment(CompartmentWrapper value);

} // CompartmentLink
