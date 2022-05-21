/**
 */
package dimensionEpidemic;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.FlowWrapper;

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
 *   <li>{@link dimensionEpidemic.Dimension#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensionEpidemic.Dimension#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends Compartment {
	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.FlowWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimension_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimension_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompartmentWrapper> getCompartment();

} // Dimension
