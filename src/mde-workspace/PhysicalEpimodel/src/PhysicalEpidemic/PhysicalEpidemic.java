/**
 */
package PhysicalEpidemic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Physical Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemic.PhysicalEpidemic#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link PhysicalEpidemic.PhysicalEpidemic#getFlows <em>Flows</em>}</li>
 * </ul>
 *
 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalEpidemic()
 * @model
 * @generated
 */
public interface PhysicalEpidemic extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' containment reference list.
	 * The list contents are of type {@link PhysicalEpidemic.PhysicalCompartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' containment reference list.
	 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalEpidemic_Compartments()
	 * @model containment="true"
	 * @generated
	 */
	EList<PhysicalCompartment> getCompartments();

	/**
	 * Returns the value of the '<em><b>Flows</b></em>' containment reference list.
	 * The list contents are of type {@link PhysicalEpidemic.PhysicalFlow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flows</em>' containment reference list.
	 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalEpidemic_Flows()
	 * @model containment="true"
	 * @generated
	 */
	EList<PhysicalFlow> getFlows();

} // PhysicalEpidemic
