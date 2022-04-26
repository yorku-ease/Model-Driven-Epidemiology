/**
 */
package epidimensionsbase;

import epibase.Compartment;
import epibase.Flow;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.Epidemic#getFlows <em>Flows</em>}</li>
 *   <li>{@link epidimensionsbase.Epidemic#getCompartments <em>Compartments</em>}</li>
 * </ul>
 *
 * @see epidimensionsbase.EpidimensionsbasePackage#getEpidemic()
 * @model
 * @generated
 */
public interface Epidemic extends epibase.Epidemic {

	/**
	 * Returns the value of the '<em><b>Flows</b></em>' containment reference list.
	 * The list contents are of type {@link epibase.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flows</em>' containment reference list.
	 * @see epidimensionsbase.EpidimensionsbasePackage#getEpidemic_Flows()
	 * @model type="epidimensionsbase.Flow" containment="true"
	 * @generated
	 */
	EList<Flow> getFlows();

	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' containment reference list.
	 * The list contents are of type {@link epibase.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' containment reference list.
	 * @see epidimensionsbase.EpidimensionsbasePackage#getEpidemic_Compartments()
	 * @model type="epidimensionsbase.Compartment" containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartments();
} // Epidemic
