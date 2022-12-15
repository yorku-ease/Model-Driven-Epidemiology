/**
 */
package group;

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
 *   <li>{@link group.Group#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link group.Group#getFlow <em>Flow</em>}</li>
 *   <li>{@link group.Group#getSource <em>Source</em>}</li>
 *   <li>{@link group.Group#getSink <em>Sink</em>}</li>
 * </ul>
 *
 * @see group.GroupPackage#getGroup()
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
	 * @see group.GroupPackage#getGroup_Compartment()
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
	 * @see group.GroupPackage#getGroup_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference list.
	 * @see group.GroupPackage#getGroup_Source()
	 * @model
	 * @generated
	 */
	EList<CompartmentWrapper> getSource();

	/**
	 * Returns the value of the '<em><b>Sink</b></em>' reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sink</em>' reference list.
	 * @see group.GroupPackage#getGroup_Sink()
	 * @model
	 * @generated
	 */
	EList<CompartmentWrapper> getSink();

} // Group
