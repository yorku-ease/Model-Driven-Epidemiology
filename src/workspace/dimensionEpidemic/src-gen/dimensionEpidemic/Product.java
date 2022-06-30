/**
 */
package dimensionEpidemic;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.Product#getDimensions <em>Dimensions</em>}</li>
 * </ul>
 *
 * @see dimensionEpidemic.DimensionEpidemicPackage#getProduct()
 * @model
 * @generated
 */
public interface Product extends Compartment {
	/**
	 * Returns the value of the '<em><b>Dimensions</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimensions</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getProduct_Dimensions()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompartmentWrapper> getDimensions();

} // Product
