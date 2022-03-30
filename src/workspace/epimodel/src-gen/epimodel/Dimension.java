/**
 */
package epimodel;

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
 *   <li>{@link epimodel.Dimension#getId <em>Id</em>}</li>
 *   <li>{@link epimodel.Dimension#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epimodel.Dimension#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends IDimension {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see epimodel.EpimodelPackage#getDimension_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link epimodel.Dimension#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.IDimension}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epimodel.EpimodelPackage#getDimension_Compartment()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<IDimension> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.MetaFlow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epimodel.EpimodelPackage#getDimension_Flow()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<MetaFlow> getFlow();

} // Dimension
