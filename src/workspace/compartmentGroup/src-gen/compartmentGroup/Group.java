/**
 */
package compartmentGroup;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;

import epimodel.FlowWrapper;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentGroup.Group#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link compartmentGroup.Group#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see compartmentGroup.CompartmentGroupPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see compartmentGroup.CompartmentGroupPackage#getGroup_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompartmentWrapper> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.FlowWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see compartmentGroup.CompartmentGroupPackage#getGroup_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

} // Group
