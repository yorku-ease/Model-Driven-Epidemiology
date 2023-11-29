/**
 */
package Epidemic;

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
 *   <li>{@link Epidemic.Product#getCompartments <em>Compartments</em>}</li>
 * </ul>
 *
 * @see Epidemic.EpidemicPackage#getProduct()
 * @model
 * @generated
 */
public interface Product extends AbstractCompartment {
	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' containment reference list.
	 * The list contents are of type {@link Epidemic.AbstractCompartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' containment reference list.
	 * @see Epidemic.EpidemicPackage#getProduct_Compartments()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractCompartment> getCompartments();

} // Product
