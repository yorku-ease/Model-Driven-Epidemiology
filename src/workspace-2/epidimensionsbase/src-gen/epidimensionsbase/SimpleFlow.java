/**
 */
package epidimensionsbase;

import epibase.Compartment;
import epibase.Flow;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.SimpleFlow#getFrom <em>From</em>}</li>
 *   <li>{@link epidimensionsbase.SimpleFlow#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see epidimensionsbase.EpidimensionsbasePackage#getSimpleFlow()
 * @model superTypes="epidimensionsbase.Flow"
 * @generated
 */
public interface SimpleFlow extends EObject, Flow {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Compartment)
	 * @see epidimensionsbase.EpidimensionsbasePackage#getSimpleFlow_From()
	 * @model type="epidimensionsbase.Compartment"
	 * @generated
	 */
	Compartment getFrom();

	/**
	 * Sets the value of the '{@link epidimensionsbase.SimpleFlow#getFrom <em>From</em>}' reference.
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
	 * @see epidimensionsbase.EpidimensionsbasePackage#getSimpleFlow_To()
	 * @model type="epidimensionsbase.Compartment"
	 * @generated
	 */
	Compartment getTo();

	/**
	 * Sets the value of the '{@link epidimensionsbase.SimpleFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Compartment value);

} // SimpleFlow
