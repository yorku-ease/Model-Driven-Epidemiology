/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import epimodel.util.PhysicalCompartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.Epidemic#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getEpidemic()
 * @model abstract="true"
 * @generated
 */
public interface Epidemic extends EObject {
	
	List<PhysicalCompartment> getPhysicalFor(Compartment c);
	
	List<PhysicalCompartment> getPhysicalHeadsFor(Compartment c);
	
	List<PhysicalCompartment> getPhysicalSinksFor(Compartment c);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see epimodel.EpimodelPackage#getEpidemic_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link epimodel.Epidemic#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);
} // Epidemic
