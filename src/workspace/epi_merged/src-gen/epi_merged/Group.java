/**
 */
package epi_merged;

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
 *   <li>{@link epi_merged.Group#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epi_merged.Group#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epi_merged.Epi_mergedPackage#getGroup()
 * @model
 * @generated
 */
public interface Group extends Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epi_merged.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epi_merged.Epi_mergedPackage#getGroup_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epi_merged.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epi_merged.Epi_mergedPackage#getGroup_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getFlow();

} // Group
