/**
 */
package epidimensionsrefgroup;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.Flow#getFrom_dimension <em>From dimension</em>}</li>
 *   <li>{@link epidimensionsrefgroup.Flow#getTo_dimension <em>To dimension</em>}</li>
 * </ul>
 *
 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getFlow()
 * @model abstract="true"
 * @generated
 */
public interface Flow extends epidimensionsbase.Flow {
	/**
	 * Returns the value of the '<em><b>From dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From dimension</em>' reference.
	 * @see #setFrom_dimension(IDimension)
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getFlow_From_dimension()
	 * @model
	 * @generated
	 */
	IDimension getFrom_dimension();

	/**
	 * Sets the value of the '{@link epidimensionsrefgroup.Flow#getFrom_dimension <em>From dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From dimension</em>' reference.
	 * @see #getFrom_dimension()
	 * @generated
	 */
	void setFrom_dimension(IDimension value);

	/**
	 * Returns the value of the '<em><b>To dimension</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To dimension</em>' reference.
	 * @see #setTo_dimension(IDimension)
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getFlow_To_dimension()
	 * @model
	 * @generated
	 */
	IDimension getTo_dimension();

	/**
	 * Sets the value of the '{@link epidimensionsrefgroup.Flow#getTo_dimension <em>To dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To dimension</em>' reference.
	 * @see #getTo_dimension()
	 * @generated
	 */
	void setTo_dimension(IDimension value);

} // Flow
