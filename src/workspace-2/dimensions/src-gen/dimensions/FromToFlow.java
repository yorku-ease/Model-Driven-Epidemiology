/**
 */
package dimensions;

import epibase.Compartment;
import epibase.Flow;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>From To Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensions.FromToFlow#getFrom <em>From</em>}</li>
 *   <li>{@link dimensions.FromToFlow#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see dimensions.DimensionsPackage#getFromToFlow()
 * @model superTypes="dimensions.Flow"
 * @generated
 */
public interface FromToFlow extends EObject, Flow {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Compartment)
	 * @see dimensions.DimensionsPackage#getFromToFlow_From()
	 * @model type="dimensions.Compartment"
	 * @generated
	 */
	Compartment getFrom();

	/**
	 * Sets the value of the '{@link dimensions.FromToFlow#getFrom <em>From</em>}' reference.
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
	 * @see dimensions.DimensionsPackage#getFromToFlow_To()
	 * @model type="dimensions.Compartment"
	 * @generated
	 */
	Compartment getTo();

	/**
	 * Sets the value of the '{@link dimensions.FromToFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Compartment value);

} // FromToFlow
