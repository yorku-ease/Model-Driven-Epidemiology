/**
 */
package seirmodel;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rate Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link seirmodel.RateFlow#getRate <em>Rate</em>}</li>
 *   <li>{@link seirmodel.RateFlow#getRateParameter <em>Rate Parameter</em>}</li>
 *   <li>{@link seirmodel.RateFlow#getStratumSpecificRates <em>Stratum Specific Rates</em>}</li>
 * </ul>
 *
 * @see seirmodel.SeirmodelPackage#getRateFlow()
 * @model
 * @generated
 */
public interface RateFlow extends Flow {
	/**
	 * Returns the value of the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rate</em>' attribute.
	 * @see #setRate(double)
	 * @see seirmodel.SeirmodelPackage#getRateFlow_Rate()
	 * @model
	 * @generated
	 */
	double getRate();

	/**
	 * Sets the value of the '{@link seirmodel.RateFlow#getRate <em>Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate</em>' attribute.
	 * @see #getRate()
	 * @generated
	 */
	void setRate(double value);

	/**
	 * Returns the value of the '<em><b>Rate Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rate Parameter</em>' reference.
	 * @see #setRateParameter(Parameter)
	 * @see seirmodel.SeirmodelPackage#getRateFlow_RateParameter()
	 * @model
	 * @generated
	 */
	Parameter getRateParameter();

	/**
	 * Sets the value of the '{@link seirmodel.RateFlow#getRateParameter <em>Rate Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate Parameter</em>' reference.
	 * @see #getRateParameter()
	 * @generated
	 */
	void setRateParameter(Parameter value);

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
	 * @see seirmodel.SeirmodelPackage#getRateFlow_StratumSpecificRates()
	 * @model containment="true"
	 * @generated
	 */
	EList<StratumSpecificRate> getStratumSpecificRates();

} // RateFlow
