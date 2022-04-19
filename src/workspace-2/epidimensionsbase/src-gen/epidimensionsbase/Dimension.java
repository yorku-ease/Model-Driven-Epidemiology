/**
 */
package epidimensionsbase;

import epibase.Compartment;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.Dimension#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epidimensionsbase.Dimension#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epidimensionsbase.EpidimensionsbasePackage#getDimension()
 * @model superTypes="epidimensionsbase.Compartment"
 * @generated
 */
public interface Dimension extends EObject, Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epibase.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epidimensionsbase.EpidimensionsbasePackage#getDimension_Compartment()
	 * @model type="epidimensionsbase.Compartment" containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epidimensionsbase.SimpleFlow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epidimensionsbase.EpidimensionsbasePackage#getDimension_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<SimpleFlow> getFlow();

} // Dimension
