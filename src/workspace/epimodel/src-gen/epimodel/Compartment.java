/**
 */
package epimodel;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import epimodel.util.PhysicalCompartment;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.Compartment#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getCompartment()
 * @model
 * @generated
 */
public interface Compartment extends EObject {

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute list.
	 * @see epimodel.EpimodelPackage#getCompartment_Label()
	 * @model
	 * @generated
	 */
	EList<String> getLabel();
	
	List<PhysicalCompartment> getPhysicalCompartments();

	List<PhysicalCompartment> getSources();

	List<PhysicalCompartment> getSinks();

	List<Flow> getFlows();
	
	Map<String, List<Compartment>> getAllBranches();
	
} // Compartment
