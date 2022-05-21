/**
 */
package dimensionEpidemic;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension Wrapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.DimensionWrapper#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionWrapper()
 * @model
 * @generated
 */
public interface DimensionWrapper extends EObject {
	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference.
	 * @see #setDimension(Dimension)
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionWrapper_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	Dimension getDimension();

	/**
	 * Sets the value of the '{@link dimensionEpidemic.DimensionWrapper#getDimension <em>Dimension</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimension</em>' containment reference.
	 * @see #getDimension()
	 * @generated
	 */
	void setDimension(Dimension value);

} // DimensionWrapper
