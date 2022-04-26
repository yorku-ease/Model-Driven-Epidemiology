/**
 */
package epimodelgroup;

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
 *   <li>{@link epimodelgroup.Dimension#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epimodelgroup.Dimension#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epimodelgroup.EpimodelgroupPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends IDimension {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodelgroup.IDimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epimodelgroup.EpimodelgroupPackage#getDimension_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<IDimension> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodelgroup.MetaFlow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epimodelgroup.EpimodelgroupPackage#getDimension_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<MetaFlow> getFlow();

} // Dimension
