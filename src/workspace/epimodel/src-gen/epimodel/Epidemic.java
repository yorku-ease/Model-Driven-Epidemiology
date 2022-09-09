/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;

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

	List<PhysicalCompartment> getPhysicalSourcesFor(Compartment c);

	List<PhysicalCompartment> getPhysicalSinksFor(Compartment c);

	List<PhysicalCompartment> getPhysicalCompartments();

	List<PhysicalFlow> getPhysicalFlows();
	
	void edit(Shell shell, List<Control> controls);

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
