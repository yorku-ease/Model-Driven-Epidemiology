/**
 */
package seirmodel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Super Infectious</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link seirmodel.SuperInfectious#isIsIsolated <em>Is Isolated</em>}</li>
 *   <li>{@link seirmodel.SuperInfectious#getSymptomStatus <em>Symptom Status</em>}</li>
 * </ul>
 *
 * @see seirmodel.SeirmodelPackage#getSuperInfectious()
 * @model abstract="true"
 * @generated
 */
public interface SuperInfectious extends Compartment {
	/**
	 * Returns the value of the '<em><b>Is Isolated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Isolated</em>' attribute.
	 * @see #setIsIsolated(boolean)
	 * @see seirmodel.SeirmodelPackage#getSuperInfectious_IsIsolated()
	 * @model
	 * @generated
	 */
	boolean isIsIsolated();

	/**
	 * Sets the value of the '{@link seirmodel.SuperInfectious#isIsIsolated <em>Is Isolated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Isolated</em>' attribute.
	 * @see #isIsIsolated()
	 * @generated
	 */
	void setIsIsolated(boolean value);

	/**
	 * Returns the value of the '<em><b>Symptom Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symptom Status</em>' attribute.
	 * @see #setSymptomStatus(String)
	 * @see seirmodel.SeirmodelPackage#getSuperInfectious_SymptomStatus()
	 * @model
	 * @generated
	 */
	String getSymptomStatus();

	/**
	 * Sets the value of the '{@link seirmodel.SuperInfectious#getSymptomStatus <em>Symptom Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symptom Status</em>' attribute.
	 * @see #getSymptomStatus()
	 * @generated
	 */
	void setSymptomStatus(String value);

} // SuperInfectious
