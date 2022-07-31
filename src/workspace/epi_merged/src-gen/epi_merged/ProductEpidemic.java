/**
 */
package epi_merged;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epi_merged.ProductEpidemic#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see epi_merged.Epi_mergedPackage#getProductEpidemic()
 * @model
 * @generated
 */
public interface ProductEpidemic extends Epidemic {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epi_merged.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epi_merged.Epi_mergedPackage#getProductEpidemic_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartment();

} // ProductEpidemic
