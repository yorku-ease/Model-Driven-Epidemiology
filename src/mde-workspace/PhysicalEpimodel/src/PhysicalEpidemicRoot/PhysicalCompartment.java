/**
 */
package PhysicalEpidemicRoot;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Physical Compartment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemicRoot.PhysicalCompartment#getLabels <em>Labels</em>}</li>
 * </ul>
 *
 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getPhysicalCompartment()
 * @model
 * @generated
 */
public interface PhysicalCompartment extends EObject {
	/**
	 * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
	 * The list contents are of type {@link PhysicalEpidemicRoot.Label}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' containment reference list.
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getPhysicalCompartment_Labels()
	 * @model containment="true"
	 * @generated
	 */
	EList<Label> getLabels();

} // PhysicalCompartment
