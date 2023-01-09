/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import epimodel.util.PhysicalCompartment;
import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.MatchResult;
import epimodel.util.FlowEquation;

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

	List<String> getLabels();

	List<PhysicalCompartment> getPhysicalCompartments();

	List<FlowEquation> getPhysicalFlows();

	List<PhysicalCompartment> getSources();

	List<PhysicalCompartment> getSinks();

	Difference compare(Compartment other, MatchResult matches);

	Difference compareWithSameClass(Compartment other, MatchResult matches);

	Difference compareWithDifferentClass(Compartment other, MatchResult matches);

	void edit(EObject dom, Shell shell, List<Control> controls);
} // Compartment
