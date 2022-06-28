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
 *   <li>{@link dimensionEpidemic.Dimension#getCoreCompartment <em>Core Compartment</em>}</li>
 *   <li>{@link dimensionEpidemic.Dimension#getDimension <em>Dimension</em>}</li>
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
	 * Returns the value of the '<em><b>Core Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Core Compartment</em>' containment reference.
	 * @see #setCoreCompartment(CompartmentWrapper)
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimension_CoreCompartment()
	 * @model containment="true"
	 * @generated
	 */
	CompartmentWrapper getCoreCompartment();

	/**
	 * Sets the value of the '{@link dimensionEpidemic.Dimension#getCoreCompartment <em>Core Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Core Compartment</em>' containment reference.
	 * @see #getCoreCompartment()
	 * @generated
	 */
	void setCoreCompartment(CompartmentWrapper value);

	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link dimensionEpidemic.DimensionWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimension_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<DimensionWrapper> getDimension();

} // Dimension
