/**
 */
package resource;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link resource.Folder#getResource <em>Resource</em>}</li>
 * </ul>
 *
 * @see resource.ResourcePackage#getFolder()
 * @model
 * @generated
 */
public interface Folder extends Resource {
	/**
	 * Returns the value of the '<em><b>Resource</b></em>' containment reference list.
	 * The list contents are of type {@link resource.Resource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource</em>' containment reference list.
	 * @see resource.ResourcePackage#getFolder_Resource()
	 * @model containment="true"
	 * @generated
	 */
	EList<Resource> getResource();

} // Folder
