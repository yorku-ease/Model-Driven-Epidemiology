/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.MatchResult;
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
	Difference compare(Composable other, MatchResult matches);
	Difference compareWithSameClass(Composable other, MatchResult matches);
	
	List<String> getLabels();

	List<PhysicalCompartment> getPhysicalCompartments();

	List<PhysicalFlow> getPhysicalFlows();

	List<PhysicalCompartment> getSources();

	List<PhysicalCompartment> getSinks();

	List<Flow> getFlows();
} // Composable
