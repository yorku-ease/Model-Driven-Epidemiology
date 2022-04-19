/**
 */
package epidimensionsrefgroup;

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
 *   <li>{@link epidimensionsrefgroup.Dimension#getFlow_2 <em>Flow 2</em>}</li>
 *   <li>{@link epidimensionsrefgroup.Dimension#getIdimension <em>Idimension</em>}</li>
 * </ul>
 *
 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends IDimension {

	/**
	 * Returns the value of the '<em><b>Flow 2</b></em>' containment reference list.
	 * The list contents are of type {@link epidimensionsrefgroup.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow 2</em>' containment reference list.
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getDimension_Flow_2()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getFlow_2();

	/**
	 * Returns the value of the '<em><b>Idimension</b></em>' containment reference list.
	 * The list contents are of type {@link epidimensionsrefgroup.IDimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Idimension</em>' containment reference list.
	 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage#getDimension_Idimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<IDimension> getIdimension();
} // Dimension
