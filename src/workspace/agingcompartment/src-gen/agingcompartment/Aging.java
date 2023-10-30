/**
 */
package agingcompartment;

import epimodel.Compartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Aging</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link agingcompartment.Aging#getAgeGroups <em>Age Groups</em>}</li>
 * </ul>
 *
 * @see agingcompartment.agingcompartmentPackage#getAging()
 * @model
 * @generated
 */
public interface Aging extends Compartment {
	/**
	 * Returns the value of the '<em><b>Age Groups</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Age Groups</em>' attribute.
	 * @see #setAgeGroups(String)
	 * @see agingcompartment.agingcompartmentPackage#getAging_AgeGroups()
	 * @model
	 * @generated
	 */
	String getAgeGroups();

	/**
	 * Sets the value of the '{@link agingcompartment.Aging#getAgeGroups <em>Age Groups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Age Groups</em>' attribute.
	 * @see #getAgeGroups()
	 * @generated
	 */
	void setAgeGroups(String value);

} // Aging
