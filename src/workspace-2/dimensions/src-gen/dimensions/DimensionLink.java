/**
 */
package dimensions;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension Link</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensions.DimensionLink#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see dimensions.DimensionsPackage#getDimensionLink()
 * @model
 * @generated
 */
public interface DimensionLink extends Dimension {
	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' reference.
	 * @see #setDimension(Dimension)
	 * @see dimensions.DimensionsPackage#getDimensionLink_Dimension()
	 * @model
	 * @generated
	 */
	Dimension getDimension();

	/**
	 * Sets the value of the '{@link dimensions.DimensionLink#getDimension <em>Dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension</em>' reference.
	 * @see #getDimension()
	 * @generated
	 */
	void setDimension(Dimension value);

} // DimensionLink
