/**
 */
package epidimensionsbase;

import epibase.Compartment;
import epibase.Flow;
import org.eclipse.emf.common.util.EList;

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
 * @model superTypes="epibase.Compartment epidimensionsbase.Compartment"
 * @generated
 */
public interface Dimension extends Compartment {
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
	 * The list contents are of type {@link epibase.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epidimensionsbase.EpidimensionsbasePackage#getDimension_Flow()
	 * @model type="epidimensionsbase.Flow" containment="true"
	 * @generated
	 */
	EList<Flow> getFlow();

} // Dimension
