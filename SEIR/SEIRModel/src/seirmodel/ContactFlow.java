/**
 */
package seirmodel;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link seirmodel.ContactFlow#getContactCompartment <em>Contact Compartment</em>}</li>
 *   <li>{@link seirmodel.ContactFlow#getContactRate <em>Contact Rate</em>}</li>
 *   <li>{@link seirmodel.ContactFlow#getContactRateParameter <em>Contact Rate Parameter</em>}</li>
 *   <li>{@link seirmodel.ContactFlow#getContactParameters <em>Contact Parameters</em>}</li>
 *   <li>{@link seirmodel.ContactFlow#getStratumSpecificRates <em>Stratum Specific Rates</em>}</li>
 * </ul>
 *
 * @see seirmodel.SeirmodelPackage#getContactFlow()
 * @model
 * @generated
 */
public interface ContactFlow extends Flow {
	/**
	 * Returns the value of the '<em><b>Contact Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Compartment</em>' reference.
	 * @see #setContactCompartment(Compartment)
	 * @see seirmodel.SeirmodelPackage#getContactFlow_ContactCompartment()
	 * @model
	 * @generated
	 */
	Compartment getContactCompartment();

	/**
	 * Sets the value of the '{@link seirmodel.ContactFlow#getContactCompartment <em>Contact Compartment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Compartment</em>' reference.
	 * @see #getContactCompartment()
	 * @generated
	 */
	void setContactCompartment(Compartment value);

	/**
	 * Returns the value of the '<em><b>Contact Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Rate</em>' attribute.
	 * @see #setContactRate(double)
	 * @see seirmodel.SeirmodelPackage#getContactFlow_ContactRate()
	 * @model
	 * @generated
	 */
	double getContactRate();

	/**
	 * Sets the value of the '{@link seirmodel.ContactFlow#getContactRate <em>Contact Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Rate</em>' attribute.
	 * @see #getContactRate()
	 * @generated
	 */
	void setContactRate(double value);

	/**
	 * Returns the value of the '<em><b>Contact Rate Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Rate Parameter</em>' reference.
	 * @see #setContactRateParameter(Parameter)
	 * @see seirmodel.SeirmodelPackage#getContactFlow_ContactRateParameter()
	 * @model
	 * @generated
	 */
	Parameter getContactRateParameter();

	/**
	 * Sets the value of the '{@link seirmodel.ContactFlow#getContactRateParameter <em>Contact Rate Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Rate Parameter</em>' reference.
	 * @see #getContactRateParameter()
	 * @generated
	 */
	void setContactRateParameter(Parameter value);

	/**
	 * Returns the value of the '<em><b>Contact Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact Parameters</em>' attribute.
	 * @see #setContactParameters(String)
	 * @see seirmodel.SeirmodelPackage#getContactFlow_ContactParameters()
	 * @model
	 * @generated
	 */
	String getContactParameters();

	/**
	 * Sets the value of the '{@link seirmodel.ContactFlow#getContactParameters <em>Contact Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contact Parameters</em>' attribute.
	 * @see #getContactParameters()
	 * @generated
	 */
	void setContactParameters(String value);

	/**
	 * Returns the value of the '<em><b>Stratum Specific Rates</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.StratumSpecificRate}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * Defines rates for specific strata (age groups, locations, risk levels, etc.).
	 * Each rate applies to compartments matching the stratum value.
	 * Examples: stratum="0-17" for age, stratum="Urban" for location, stratum="High-Risk" for risk level.
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stratum Specific Rates</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getContactFlow_StratumSpecificRates()
	 * @model containment="true"
	 * @generated
	 */
	EList<StratumSpecificRate> getStratumSpecificRates();

} // ContactFlow
