/**
 */
package compartmentGroup;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>End</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentGroup.End#getLink <em>Link</em>}</li>
 * </ul>
 *
 * @see compartmentGroup.CompartmentGroupPackage#getEnd()
 * @model
 * @generated
 */
public interface End extends EObject {
	/**
	 * Returns the value of the '<em><b>Link</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentGroup.Link}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Link</em>' containment reference list.
	 * @see compartmentGroup.CompartmentGroupPackage#getEnd_Link()
	 * @model containment="true"
	 * @generated
	 */
	EList<Link> getLink();

} // End
