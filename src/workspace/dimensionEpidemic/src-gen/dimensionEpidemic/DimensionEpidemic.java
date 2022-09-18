/**
 */
package dimensionEpidemic;

import epimodel.CompartmentWrapper;
import epimodel.Epidemic;

import epimodel.FlowWrapper;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.DimensionEpidemic#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensionEpidemic.DimensionEpidemic#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionEpidemic()
 * @model
 * @generated
 */
public interface DimensionEpidemic extends Epidemic {

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.FlowWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionEpidemic_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<FlowWrapper> getFlow();

	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.CompartmentWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionEpidemic_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<CompartmentWrapper> getDimension();

} // DimensionEpidemic
