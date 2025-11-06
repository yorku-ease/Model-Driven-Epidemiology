/**
 */
package compartmentalmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentalmodel.Compartment#getPrimaryName <em>Primary Name</em>}</li>
 *   <li>{@link compartmentalmodel.Compartment#getPopulation <em>Population</em>}</li>
 *   <li>{@link compartmentalmodel.Compartment#getOutgoingFlows <em>Outgoing Flows</em>}</li>
 *   <li>{@link compartmentalmodel.Compartment#getSecondaryName <em>Secondary Name</em>}</li>
 *   <li>{@link compartmentalmodel.Compartment#getProduct <em>Product</em>}</li>
 * </ul>
 *
 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartment()
 * @model
 * @generated
 */
public interface Compartment extends EObject {
	/**
	 * Returns the value of the '<em><b>Primary Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primary Name</em>' attribute.
	 * @see #setPrimaryName(String)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartment_PrimaryName()
	 * @model ordered="false"
	 * @generated
	 */
	String getPrimaryName();

	/**
	 * Sets the value of the '{@link compartmentalmodel.Compartment#getPrimaryName <em>Primary Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primary Name</em>' attribute.
	 * @see #getPrimaryName()
	 * @generated
	 */
	void setPrimaryName(String value);

	/**
	 * Returns the value of the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Population</em>' attribute.
	 * @see #setPopulation(int)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartment_Population()
	 * @model
	 * @generated
	 */
	int getPopulation();

	/**
	 * Sets the value of the '{@link compartmentalmodel.Compartment#getPopulation <em>Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Population</em>' attribute.
	 * @see #getPopulation()
	 * @generated
	 */
	void setPopulation(int value);

	/**
	 * Returns the value of the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Flows</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartment_OutgoingFlows()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getOutgoingFlows();

	/**
	 * Returns the value of the '<em><b>Secondary Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Secondary Name</em>' attribute.
	 * @see #setSecondaryName(String)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartment_SecondaryName()
	 * @model ordered="false"
	 * @generated
	 */
	String getSecondaryName();

	/**
	 * Sets the value of the '{@link compartmentalmodel.Compartment#getSecondaryName <em>Secondary Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Secondary Name</em>' attribute.
	 * @see #getSecondaryName()
	 * @generated
	 */
	void setSecondaryName(String value);

	/**
	 * Returns the value of the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Product</em>' reference.
	 * @see #setProduct(Product)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartment_Product()
	 * @model
	 * @generated
	 */
	Product getProduct();

	/**
	 * Sets the value of the '{@link compartmentalmodel.Compartment#getProduct <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Product</em>' reference.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(Product value);

} // Compartment
