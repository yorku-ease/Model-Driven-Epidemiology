/**
 */
package EpidemicRoot;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link EpidemicRoot.Epidemic#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see EpidemicRoot.EpidemicRootPackage#getEpidemic()
 * @model
 * @generated
 */
public interface Epidemic extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference.
	 * @see #setCompartment(AbstractCompartment)
	 * @see EpidemicRoot.EpidemicRootPackage#getEpidemic_Compartment()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AbstractCompartment getCompartment();

	/**
	 * Sets the value of the '{@link EpidemicRoot.Epidemic#getCompartment <em>Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compartment</em>' containment reference.
	 * @see #getCompartment()
	 * @generated
	 */
	void setCompartment(AbstractCompartment value);

} // Epidemic