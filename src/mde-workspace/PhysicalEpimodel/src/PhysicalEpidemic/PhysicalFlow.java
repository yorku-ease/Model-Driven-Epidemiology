/**
 */
package PhysicalEpidemic;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Physical Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemic.PhysicalFlow#getFrom <em>From</em>}</li>
 *   <li>{@link PhysicalEpidemic.PhysicalFlow#getTo <em>To</em>}</li>
 *   <li>{@link PhysicalEpidemic.PhysicalFlow#getLabels <em>Labels</em>}</li>
 * </ul>
 *
 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalFlow()
 * @model
 * @generated
 */
public interface PhysicalFlow extends EObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(PhysicalCompartment)
	 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalFlow_From()
	 * @model
	 * @generated
	 */
	PhysicalCompartment getFrom();

	/**
	 * Sets the value of the '{@link PhysicalEpidemic.PhysicalFlow#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(PhysicalCompartment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(PhysicalCompartment)
	 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalFlow_To()
	 * @model
	 * @generated
	 */
	PhysicalCompartment getTo();

	/**
	 * Sets the value of the '{@link PhysicalEpidemic.PhysicalFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(PhysicalCompartment value);

	/**
	 * Returns the value of the '<em><b>Labels</b></em>' containment reference list.
	 * The list contents are of type {@link PhysicalEpidemic.Label}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Labels</em>' containment reference list.
	 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalFlow_Labels()
	 * @model containment="true"
	 * @generated
	 */
	EList<Label> getLabels();

} // PhysicalFlow
