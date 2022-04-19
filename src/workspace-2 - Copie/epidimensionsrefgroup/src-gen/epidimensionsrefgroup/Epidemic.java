/**
 */
package epidimensionsrefgroup;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.Epidemic#getIdimension <em>Idimension</em>}</li>
 * </ul>
 *
 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getEpidemic()
 * @model
 * @generated
 */
public interface Epidemic extends epidimensionsbase.Epidemic {
	/**
	 * Returns the value of the '<em><b>Idimension</b></em>' containment reference list.
	 * The list contents are of type {@link epidimensionsrefgroup.IDimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Idimension</em>' containment reference list.
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getEpidemic_Idimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<IDimension> getIdimension();

} // Epidemic
