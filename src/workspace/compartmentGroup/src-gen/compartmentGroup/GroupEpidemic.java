/**
 */
package compartmentGroup;

import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.FlowWrapper;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentGroup.GroupEpidemic#getGroupSinks <em>Group Sinks</em>}</li>
 *   <li>{@link compartmentGroup.GroupEpidemic#getGroupSources <em>Group Sources</em>}</li>
 *   <li>{@link compartmentGroup.GroupEpidemic#getFlow <em>Flow</em>}</li>
 *   <li>{@link compartmentGroup.GroupEpidemic#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see compartmentGroup.CompartmentGroupPackage#getGroupEpidemic()
 * @model
 * @generated
 */
public interface GroupEpidemic extends Epidemic {
	/**
	 * Returns the value of the '<em><b>Group Sinks</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Sinks</em>' containment reference.
	 * @see #setGroupSinks(GroupSinks)
	 * @see compartmentGroup.CompartmentGroupPackage#getGroupEpidemic_GroupSinks()
	 * @model containment="true"
	 * @generated
	 */
	GroupSinks getGroupSinks();

	/**
	 * Sets the value of the '{@link compartmentGroup.GroupEpidemic#getGroupSinks <em>Group Sinks</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Sinks</em>' containment reference.
	 * @see #getGroupSinks()
	 * @generated
	 */
	void setGroupSinks(GroupSinks value);

	/**
	 * Returns the value of the '<em><b>Group Sources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group Sources</em>' containment reference.
	 * @see #setGroupSources(GroupSources)
	 * @see compartmentGroup.CompartmentGroupPackage#getGroupEpidemic_GroupSources()
	 * @model containment="true"
	 * @generated
	 */
	GroupSources getGroupSources();

	/**
	 * Sets the value of the '{@link compartmentGroup.GroupEpidemic#getGroupSources <em>Group Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group Sources</em>' containment reference.
	 * @see #getGroupSources()
	 * @generated
	 */
	void setGroupSources(GroupSources value);

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.FlowWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see compartmentGroup.CompartmentGroupPackage#getGroupEpidemic_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see compartmentGroup.CompartmentGroupPackage#getGroupEpidemic_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompartmentWrapper> getCompartment();

} // GroupEpidemic
