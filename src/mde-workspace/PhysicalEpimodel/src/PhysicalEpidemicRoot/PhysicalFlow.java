/**
 */
package PhysicalEpidemicRoot;

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
 *   <li>{@link PhysicalEpidemicRoot.PhysicalFlow#getFrom <em>From</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.PhysicalFlow#getTo <em>To</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.PhysicalFlow#getEquationtemplate <em>Equationtemplate</em>}</li>
 * </ul>
 *
 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getPhysicalFlow()
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
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getPhysicalFlow_From()
	 * @model
	 * @generated
	 */
	PhysicalCompartment getFrom();

	/**
	 * Sets the value of the '{@link PhysicalEpidemicRoot.PhysicalFlow#getFrom <em>From</em>}' reference.
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
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getPhysicalFlow_To()
	 * @model
	 * @generated
	 */
	PhysicalCompartment getTo();

	/**
	 * Sets the value of the '{@link PhysicalEpidemicRoot.PhysicalFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(PhysicalCompartment value);

	/**
	 * Returns the value of the '<em><b>Equationtemplate</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Equationtemplate</em>' containment reference.
	 * @see #setEquationtemplate(EquationTemplate)
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getPhysicalFlow_Equationtemplate()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EquationTemplate getEquationtemplate();

	/**
	 * Sets the value of the '{@link PhysicalEpidemicRoot.PhysicalFlow#getEquationtemplate <em>Equationtemplate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Equationtemplate</em>' containment reference.
	 * @see #getEquationtemplate()
	 * @generated
	 */
	void setEquationtemplate(EquationTemplate value);

} // PhysicalFlow
