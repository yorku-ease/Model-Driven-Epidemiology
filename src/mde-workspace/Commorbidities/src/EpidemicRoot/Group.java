/**
 */
package EpidemicRoot;

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
 *   <li>{@link EpidemicRoot.Group#getFlows <em>Flows</em>}</li>
 *   <li>{@link EpidemicRoot.Group#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link EpidemicRoot.Group#getSink <em>Sink</em>}</li>
 *   <li>{@link EpidemicRoot.Group#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @see EpidemicRoot.EpidemicRootPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends AbstractCompartment {
	/**
	 * Returns the value of the '<em><b>Flows</b></em>' containment reference list.
	 * The list contents are of type {@link EpidemicRoot.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flows</em>' containment reference list.
	 * @see EpidemicRoot.EpidemicRootPackage#getGroup_Flows()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getFlows();

	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' containment reference list.
	 * The list contents are of type {@link EpidemicRoot.AbstractCompartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' containment reference list.
	 * @see EpidemicRoot.EpidemicRootPackage#getGroup_Compartments()
	 * @model containment="true"
	 * @generated
	 */
	EList<AbstractCompartment> getCompartments();

	/**
	 * Returns the value of the '<em><b>Sink</b></em>' reference list.
	 * The list contents are of type {@link EpidemicRoot.AbstractCompartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sink</em>' reference list.
	 * @see EpidemicRoot.EpidemicRootPackage#getGroup_Sink()
	 * @model
	 * @generated
	 */
	EList<AbstractCompartment> getSink();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference list.
	 * The list contents are of type {@link EpidemicRoot.AbstractCompartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference list.
	 * @see EpidemicRoot.EpidemicRootPackage#getGroup_Source()
	 * @model
	 * @generated
	 */
	EList<AbstractCompartment> getSource();

} // Group
