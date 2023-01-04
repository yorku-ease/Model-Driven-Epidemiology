/**
 */
package epimodel;

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
 *   <li>{@link epimodel.Epidemic#getCompartmentwrapper <em>Compartmentwrapper</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getEpidemic()
 * @model
 * @generated
 */
public interface Epidemic extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartmentwrapper</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartmentwrapper</em>' containment reference.
	 * @see #setCompartmentwrapper(CompartmentWrapper)
	 * @see epimodel.EpimodelPackage#getEpidemic_Compartmentwrapper()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CompartmentWrapper getCompartmentwrapper();

	/**
	 * Sets the value of the '{@link epimodel.Epidemic#getCompartmentwrapper <em>Compartmentwrapper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Compartmentwrapper</em>' containment reference.
	 * @see #getCompartmentwrapper()
	 * @generated
	 */
	void setCompartmentwrapper(CompartmentWrapper value);

} // Epidemic
