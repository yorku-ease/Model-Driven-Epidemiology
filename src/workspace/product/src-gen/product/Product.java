/**
 */
package product;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.FlowWrapper;

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
 *   <li>{@link product.Product#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link product.Product#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see product.ProductPackage#getProduct()
 * @model
 * @generated
 */
public interface Product extends Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see product.ProductPackage#getProduct_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompartmentWrapper> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.FlowWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see product.ProductPackage#getProduct_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

} // Product
