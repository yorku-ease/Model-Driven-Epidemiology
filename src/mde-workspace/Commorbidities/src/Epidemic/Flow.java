/**
 */
package Epidemic;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Epidemic.Flow#getFrom <em>From</em>}</li>
 *   <li>{@link Epidemic.Flow#getTo <em>To</em>}</li>
 *   <li>{@link Epidemic.Flow#getSourceParameters <em>Source Parameters</em>}</li>
 * </ul>
 *
 * @see Epidemic.EpidemicPackage#getFlow()
 * @model
 * @generated
 */
public interface Flow extends EObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(AbstractCompartment)
	 * @see Epidemic.EpidemicPackage#getFlow_From()
	 * @model
	 * @generated
	 */
	AbstractCompartment getFrom();

	/**
	 * Sets the value of the '{@link Epidemic.Flow#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(AbstractCompartment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(AbstractCompartment)
	 * @see Epidemic.EpidemicPackage#getFlow_To()
	 * @model
	 * @generated
	 */
	AbstractCompartment getTo();

	/**
	 * Sets the value of the '{@link Epidemic.Flow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(AbstractCompartment value);

	/**
	 * Returns the value of the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Parameters</em>' attribute.
	 * @see #setSourceParameters(String)
	 * @see Epidemic.EpidemicPackage#getFlow_SourceParameters()
	 * @model
	 * @generated
	 */
	String getSourceParameters();

	/**
	 * Sets the value of the '{@link Epidemic.Flow#getSourceParameters <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Parameters</em>' attribute.
	 * @see #getSourceParameters()
	 * @generated
	 */
	void setSourceParameters(String value);

} // Flow
