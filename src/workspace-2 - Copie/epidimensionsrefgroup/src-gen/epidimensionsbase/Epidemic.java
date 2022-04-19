/**
 */
package epidimensionsbase;

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
 *   <li>{@link epidimensionsbase.Epidemic#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see epidimensionsbase.EpidimensionsbasePackage#getEpidemic()
 * @model
 * @generated
 */
public interface Epidemic extends epibase.Epidemic {

	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link epidimensionsbase.Dimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see epidimensionsbase.EpidimensionsbasePackage#getEpidemic_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<Dimension> getDimension();
} // Epidemic
