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
 * A representation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.Flow#getId <em>Id</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getFlow()
 * @model abstract="true"
 * @generated
 */
public interface Flow extends EObject {

	List<PhysicalFlow> getPhysicalFlows(Epidemic epidemic);

	List<PhysicalCompartment> getPhysicalFor(Epidemic epidemic, Compartment c);

	List<PhysicalCompartment> getPhysicalSourcesFor(Epidemic epidemic, Compartment c);

	List<PhysicalCompartment> getPhysicalSinksFor(Epidemic epidemic, Compartment c);

	List<EObject> getTargetObjects();
	
	List<String> getTargetLabels();
	
	void edit(Shell shell, List<Control> controls);

	String getTargetRelation(EObject target);
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see epimodel.EpimodelPackage#getFlow_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link epimodel.Flow#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);
} // Flow
