/**
 */
package epimodelgroup;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodelgroup.Epidemic#getId <em>Id</em>}</li>
 *   <li>{@link epimodelgroup.Epidemic#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see epimodelgroup.EpimodelgroupPackage#getEpidemic()
 * @model
 * @generated
 */
public interface Epidemic extends EObject {
	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link epimodelgroup.IDimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see epimodelgroup.EpimodelgroupPackage#getEpidemic_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<IDimension> getDimension();

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see epimodelgroup.EpimodelgroupPackage#getEpidemic_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link epimodelgroup.Epidemic#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // Epidemic
