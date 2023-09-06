/**
 */
package mortality;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mortality</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mortality.Mortality#getMortality <em>Mortality</em>}</li>
 * </ul>
 *
 * @see mortality.MortalityPackage#getMortality()
 * @model
 * @generated
 */
public interface Mortality extends Compartment {
	/**
	 * Returns the value of the '<em><b>Mortality</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mortality</em>' containment reference.
	 * @see #setMortality(CompartmentWrapper)
	 * @see mortality.MortalityPackage#getMortality_Mortality()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CompartmentWrapper getMortality();

	/**
	 * Sets the value of the '{@link mortality.Mortality#getMortality <em>Mortality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mortality</em>' containment reference.
	 * @see #getMortality()
	 * @generated
	 */
	void setMortality(CompartmentWrapper value);

} // Mortality
