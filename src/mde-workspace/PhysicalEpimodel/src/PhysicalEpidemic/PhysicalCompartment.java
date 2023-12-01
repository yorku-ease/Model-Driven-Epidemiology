/**
 */
package PhysicalEpidemic;

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
 *   <li>{@link PhysicalEpidemic.PhysicalCompartment#getTemplate <em>Template</em>}</li>
 * </ul>
 *
 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalCompartment()
 * @model
 * @generated
 */
public interface PhysicalCompartment extends EObject {
	/**
	 * Returns the value of the '<em><b>Template</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template</em>' containment reference.
	 * @see #setTemplate(EquationTemplate)
	 * @see PhysicalEpidemic.PhysicalEpidemicPackage#getPhysicalCompartment_Template()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EquationTemplate getTemplate();

	/**
	 * Sets the value of the '{@link PhysicalEpidemic.PhysicalCompartment#getTemplate <em>Template</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template</em>' containment reference.
	 * @see #getTemplate()
	 * @generated
	 */
	void setTemplate(EquationTemplate value);

} // PhysicalCompartment
