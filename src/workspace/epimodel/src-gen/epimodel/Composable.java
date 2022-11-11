/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import epimodel.util.Difference;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composable</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see epimodel.EpimodelPackage#getComposable()
 * @model
 * @generated
 */
public interface Composable extends EObject {
	Difference diff(Composable other);
	Class<?> getBaseClass();
	Difference compareWithSameClass(Composable other);
	Difference compareWithDifferentClass(Composable other);
	Difference compareWithBaseClass(Composable other);
	
	List<String> getLabels();

	List<PhysicalCompartment> getPhysicalCompartments();

	List<PhysicalFlow> getPhysicalFlows();

	List<PhysicalCompartment> getSources();

	List<PhysicalCompartment> getSinks();

	List<Flow> getFlows();
} // Composable
