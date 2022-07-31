/**
 */
package epi_merged;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rate</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epi_merged.Rate#getFrom <em>From</em>}</li>
 *   <li>{@link epi_merged.Rate#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see epi_merged.Epi_mergedPackage#getRate()
 * @model
 * @generated
 */
public interface Rate extends Flow {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Compartment)
	 * @see epi_merged.Epi_mergedPackage#getRate_From()
	 * @model
	 * @generated
	 */
	Compartment getFrom();

	/**
	 * Sets the value of the '{@link epi_merged.Rate#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Compartment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Compartment)
	 * @see epi_merged.Epi_mergedPackage#getRate_To()
	 * @model
	 * @generated
	 */
	Compartment getTo();

	/**
	 * Sets the value of the '{@link epi_merged.Rate#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Compartment value);

} // Rate
