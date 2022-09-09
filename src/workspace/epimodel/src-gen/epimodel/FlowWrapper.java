/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow Wrapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.FlowWrapper#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getFlowWrapper()
 * @model
 * @generated
 */
public interface FlowWrapper extends EObject {
	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference.
	 * @see #setFlow(Flow)
	 * @see epimodel.EpimodelPackage#getFlowWrapper_Flow()
	 * @model containment="true"
	 * @generated
	 */
	Flow getFlow();

	/**
	 * Sets the value of the '{@link epimodel.FlowWrapper#getFlow <em>Flow</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Flow</em>' containment reference.
	 * @see #getFlow()
	 * @generated
	 */
	void setFlow(Flow value);
	
	void edit(Shell shell, List<Control> controls);

} // FlowWrapper
