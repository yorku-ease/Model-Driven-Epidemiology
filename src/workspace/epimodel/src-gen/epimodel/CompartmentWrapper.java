/**
 */
package epimodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compartment Wrapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.CompartmentWrapper#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getCompartmentWrapper()
 * @model
 * @generated
 */
public interface CompartmentWrapper extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference.
	 * @see #setCompartment(Compartment)
	 * @see epimodel.EpimodelPackage#getCompartmentWrapper_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	Compartment getCompartment();

	/**
	 * Sets the value of the '{@link epimodel.CompartmentWrapper#getCompartment <em>Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compartment</em>' containment reference.
	 * @see #getCompartment()
	 * @generated
	 */
	void setCompartment(Compartment value);

} // CompartmentWrapper
