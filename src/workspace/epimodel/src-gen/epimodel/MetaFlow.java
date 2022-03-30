/**
 */
package epimodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Meta Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.MetaFlow#getId <em>Id</em>}</li>
 *   <li>{@link epimodel.MetaFlow#getFrom <em>From</em>}</li>
 *   <li>{@link epimodel.MetaFlow#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getMetaFlow()
 * @model abstract="true"
 * @generated
 */
public interface MetaFlow extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see epimodel.EpimodelPackage#getMetaFlow_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link epimodel.MetaFlow#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(IDimension)
	 * @see epimodel.EpimodelPackage#getMetaFlow_From()
	 * @model
	 * @generated
	 */
	IDimension getFrom();

	/**
	 * Sets the value of the '{@link epimodel.MetaFlow#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(IDimension value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(IDimension)
	 * @see epimodel.EpimodelPackage#getMetaFlow_To()
	 * @model
	 * @generated
	 */
	IDimension getTo();

	/**
	 * Sets the value of the '{@link epimodel.MetaFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(IDimension value);

} // MetaFlow
