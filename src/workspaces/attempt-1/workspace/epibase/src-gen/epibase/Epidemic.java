/**
 */
package epibase;

import org.eclipse.emf.common.util.EList;

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
 *   <li>{@link epibase.Epidemic#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see epibase.EpibasePackage#getEpidemic()
 * @model abstract="true"
 * @generated
 */
public interface Epidemic extends EObject {
	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link epibase.Dimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see epibase.EpibasePackage#getEpidemic_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dimension> getDimension();

} // Epidemic
