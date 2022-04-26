/**
 */
package epimodelgroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodelgroup.DimensionRef#getReference <em>Reference</em>}</li>
 * </ul>
 *
 * @see epimodelgroup.EpimodelgroupPackage#getDimensionRef()
 * @model
 * @generated
 */
public interface DimensionRef extends IDimension {
	/**
	 * Returns the value of the '<em><b>Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' reference.
	 * @see #setReference(IDimension)
	 * @see epimodelgroup.EpimodelgroupPackage#getDimensionRef_Reference()
	 * @model
	 * @generated
	 */
	IDimension getReference();

	/**
	 * Sets the value of the '{@link epimodelgroup.DimensionRef#getReference <em>Reference</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' reference.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(IDimension value);

} // DimensionRef
