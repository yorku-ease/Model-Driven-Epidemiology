/**
 */
package src_ph.PhysicalEpidemicRoot;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Equation Template</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemicRoot.EquationTemplate#getSourceParameters <em>Source Parameters</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.EquationTemplate#getContactParameters <em>Contact Parameters</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.EquationTemplate#getContactCompartment <em>Contact Compartment</em>}</li>
 * </ul>
 *
 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getEquationTemplate()
 * @model
 * @generated
 */
public interface EquationTemplate extends EObject {

	/**
	 * Returns the value of the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Parameters</em>' attribute.
	 * @see #setSourceParameters(String)
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getEquationTemplate_SourceParameters()
	 * @model
	 * @generated
	 */
	String getSourceParameters();

	/**
	 * Sets the value of the '{@link PhysicalEpidemicRoot.EquationTemplate#getSourceParameters <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Parameters</em>' attribute.
	 * @see #getSourceParameters()
	 * @generated
	 */
	void setSourceParameters(String value);

	/**
	 * Returns the value of the '<em><b>Contact Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Parameters</em>' attribute.
	 * @see #setContactParameters(String)
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getEquationTemplate_ContactParameters()
	 * @model
	 * @generated
	 */
	String getContactParameters();

	/**
	 * Sets the value of the '{@link PhysicalEpidemicRoot.EquationTemplate#getContactParameters <em>Contact Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Parameters</em>' attribute.
	 * @see #getContactParameters()
	 * @generated
	 */
	void setContactParameters(String value);

	/**
	 * Returns the value of the '<em><b>Contact Compartment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Compartment</em>' attribute.
	 * @see #setContactCompartment(String)
	 * @see PhysicalEpidemicRoot.PhysicalEpidemicRootPackage#getEquationTemplate_ContactCompartment()
	 * @model
	 * @generated
	 */
	String getContactCompartment();

	/**
	 * Sets the value of the '{@link PhysicalEpidemicRoot.EquationTemplate#getContactCompartment <em>Contact Compartment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Compartment</em>' attribute.
	 * @see #getContactCompartment()
	 * @generated
	 */
	void setContactCompartment(String value);
} // EquationTemplate
