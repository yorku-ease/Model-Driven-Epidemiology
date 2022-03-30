/**
 */
package epimodel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.DimensionRef#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getDimensionRef()
 * @model
 * @generated
 */
public interface DimensionRef extends IDimension {
	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' reference.
	 * @see #setDimension(Dimension)
	 * @see epimodel.EpimodelPackage#getDimensionRef_Dimension()
	 * @model
	 * @generated
	 */
	Dimension getDimension();

	/**
	 * Sets the value of the '{@link epimodel.DimensionRef#getDimension <em>Dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension</em>' reference.
	 * @see #getDimension()
	 * @generated
	 */
	void setDimension(Dimension value);

} // DimensionRef
