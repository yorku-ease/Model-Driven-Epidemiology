/**
 */
package couplingFlow;

import epimodel.CompartmentWrapper;
import epimodel.Flow;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Coupling Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link couplingFlow.CouplingFlow#getFirstFrom <em>First From</em>}</li>
 *   <li>{@link couplingFlow.CouplingFlow#getSecondFrom <em>Second From</em>}</li>
 *   <li>{@link couplingFlow.CouplingFlow#getSecondTo <em>Second To</em>}</li>
 *   <li>{@link couplingFlow.CouplingFlow#getFirstTo <em>First To</em>}</li>
 * </ul>
 *
 * @see couplingFlow.CouplingFlowPackage#getCouplingFlow()
 * @model
 * @generated
 */
public interface CouplingFlow extends Flow {
	/**
	 * Returns the value of the '<em><b>First From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First From</em>' reference.
	 * @see #setFirstFrom(CompartmentWrapper)
	 * @see couplingFlow.CouplingFlowPackage#getCouplingFlow_FirstFrom()
	 * @model
	 * @generated
	 */
	CompartmentWrapper getFirstFrom();

	/**
	 * Sets the value of the '{@link couplingFlow.CouplingFlow#getFirstFrom <em>First From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First From</em>' reference.
	 * @see #getFirstFrom()
	 * @generated
	 */
	void setFirstFrom(CompartmentWrapper value);

	/**
	 * Returns the value of the '<em><b>Second From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second From</em>' reference.
	 * @see #setSecondFrom(CompartmentWrapper)
	 * @see couplingFlow.CouplingFlowPackage#getCouplingFlow_SecondFrom()
	 * @model
	 * @generated
	 */
	CompartmentWrapper getSecondFrom();

	/**
	 * Sets the value of the '{@link couplingFlow.CouplingFlow#getSecondFrom <em>Second From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second From</em>' reference.
	 * @see #getSecondFrom()
	 * @generated
	 */
	void setSecondFrom(CompartmentWrapper value);

	/**
	 * Returns the value of the '<em><b>Second To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Second To</em>' reference.
	 * @see #setSecondTo(CompartmentWrapper)
	 * @see couplingFlow.CouplingFlowPackage#getCouplingFlow_SecondTo()
	 * @model
	 * @generated
	 */
	CompartmentWrapper getSecondTo();

	/**
	 * Sets the value of the '{@link couplingFlow.CouplingFlow#getSecondTo <em>Second To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Second To</em>' reference.
	 * @see #getSecondTo()
	 * @generated
	 */
	void setSecondTo(CompartmentWrapper value);

	/**
	 * Returns the value of the '<em><b>First To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First To</em>' reference.
	 * @see #setFirstTo(CompartmentWrapper)
	 * @see couplingFlow.CouplingFlowPackage#getCouplingFlow_FirstTo()
	 * @model
	 * @generated
	 */
	CompartmentWrapper getFirstTo();

	/**
	 * Sets the value of the '{@link couplingFlow.CouplingFlow#getFirstTo <em>First To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First To</em>' reference.
	 * @see #getFirstTo()
	 * @generated
	 */
	void setFirstTo(CompartmentWrapper value);

} // CouplingFlow
