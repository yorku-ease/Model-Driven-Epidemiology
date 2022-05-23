/**
 */
package compartmentFlowEpidemic;

import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.FlowWrapper;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compartment Flow Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentFlowEpidemic.CompartmentFlowEpidemic#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link compartmentFlowEpidemic.CompartmentFlowEpidemic#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see compartmentFlowEpidemic.CompartmentFlowEpidemicPackage#getCompartmentFlowEpidemic()
 * @model
 * @generated
 */
public interface CompartmentFlowEpidemic extends Epidemic {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see compartmentFlowEpidemic.CompartmentFlowEpidemicPackage#getCompartmentFlowEpidemic_Compartment()
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
	 * @see compartmentFlowEpidemic.CompartmentFlowEpidemicPackage#getCompartmentFlowEpidemic_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

} // CompartmentFlowEpidemic
