/**
 */
package epibase;

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
 *   <li>{@link epibase.Dimension#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epibase.Dimension#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epibase.EpibasePackage#getDimension()
 * @model abstract="true"
 * @generated
 */
public interface Dimension extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epibase.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epibase.EpibasePackage#getDimension_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epibase.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epibase.EpibasePackage#getDimension_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getFlow();

} // Dimension
