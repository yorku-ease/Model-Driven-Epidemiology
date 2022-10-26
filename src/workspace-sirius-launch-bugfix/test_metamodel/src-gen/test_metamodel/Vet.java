/**
 */
package test_metamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Vet</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link test_metamodel.Vet#getCat <em>Cat</em>}</li>
 *   <li>{@link test_metamodel.Vet#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see test_metamodel.Test_metamodelPackage#getVet()
 * @model
 * @generated
 */
public interface Vet extends EObject {
	/**
	 * Returns the value of the '<em><b>Cat</b></em>' containment reference list.
	 * The list contents are of type {@link test_metamodel.Cat}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cat</em>' containment reference list.
	 * @see test_metamodel.Test_metamodelPackage#getVet_Cat()
	 * @model containment="true"
	 * @generated
	 */
	EList<Cat> getCat();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see test_metamodel.Test_metamodelPackage#getVet_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link test_metamodel.Vet#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Vet
