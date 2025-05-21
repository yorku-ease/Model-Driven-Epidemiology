/**
 */
package seirmodel;

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
 *   <li>{@link seirmodel.Compartment#getName <em>Name</em>}</li>
 *   <li>{@link seirmodel.Compartment#getPopulation <em>Population</em>}</li>
 *   <li>{@link seirmodel.Compartment#getOutgoingFlows <em>Outgoing Flows</em>}</li>
 * </ul>
 *
 * @see seirmodel.SeirmodelPackage#getCompartment()
 * @model
 * @generated
 */
public interface Compartment extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see seirmodel.SeirmodelPackage#getCompartment_Name()
	 * @model ordered="false"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link seirmodel.Compartment#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Population</em>' attribute.
	 * @see #setPopulation(int)
	 * @see seirmodel.SeirmodelPackage#getCompartment_Population()
	 * @model
	 * @generated
	 */
	int getPopulation();

	/**
	 * Sets the value of the '{@link seirmodel.Compartment#getPopulation <em>Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Population</em>' attribute.
	 * @see #getPopulation()
	 * @generated
	 */
	void setPopulation(int value);

	/**
	 * Returns the value of the '<em><b>Outgoing Flows</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Flows</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getCompartment_OutgoingFlows()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getOutgoingFlows();

} // Compartment
