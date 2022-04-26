/**
 */
package a;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>New EClass1</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link a.NewEClass1#getNeweclass2 <em>Neweclass2</em>}</li>
 *   <li>{@link a.NewEClass1#getNeweclass3 <em>Neweclass3</em>}</li>
 * </ul>
 *
 * @see a.APackage#getNewEClass1()
 * @model
 * @generated
 */
public interface NewEClass1 extends EObject {
	/**
	 * Returns the value of the '<em><b>Neweclass2</b></em>' containment reference list.
	 * The list contents are of type {@link a.NewEClass2}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Neweclass2</em>' containment reference list.
	 * @see a.APackage#getNewEClass1_Neweclass2()
	 * @model containment="true"
	 * @generated
	 */
	EList<NewEClass2> getNeweclass2();

	/**
	 * Returns the value of the '<em><b>Neweclass3</b></em>' containment reference list.
	 * The list contents are of type {@link a.NewEClass3}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Neweclass3</em>' containment reference list.
	 * @see a.APackage#getNewEClass1_Neweclass3()
	 * @model containment="true"
	 * @generated
	 */
	EList<NewEClass3> getNeweclass3();

} // NewEClass1
