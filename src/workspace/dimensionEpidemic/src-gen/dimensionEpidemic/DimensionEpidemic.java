/**
 */
package dimensionEpidemic;

import epimodel.CompartmentWrapper;
import epimodel.Epidemic;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Dimension Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.DimensionEpidemic#getDimension <em>Dimension</em>}</li>
 *   <li>{@link dimensionEpidemic.DimensionEpidemic#getCoreCompartment <em>Core Compartment</em>}</li>
 * </ul>
 *
 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionEpidemic()
 * @model
 * @generated
 */
public interface DimensionEpidemic extends Epidemic {

	List<JSONObject> compile() throws JSONException;

	/**
	 * Returns the value of the '<em><b>Dimension</b></em>' containment reference list.
	 * The list contents are of type {@link dimensionEpidemic.DimensionWrapper}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimension</em>' containment reference list.
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionEpidemic_Dimension()
	 * @model containment="true"
	 * @generated
	 */
	EList<DimensionWrapper> getDimension();

	/**
	 * Returns the value of the '<em><b>Core Compartment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Core Compartment</em>' containment reference.
	 * @see #setCoreCompartment(CompartmentWrapper)
	 * @see dimensionEpidemic.DimensionEpidemicPackage#getDimensionEpidemic_CoreCompartment()
	 * @model containment="true"
	 * @generated
	 */
	CompartmentWrapper getCoreCompartment();

	/**
	 * Sets the value of the '{@link dimensionEpidemic.DimensionEpidemic#getCoreCompartment <em>Core Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Core Compartment</em>' containment reference.
	 * @see #getCoreCompartment()
	 * @generated
	 */
	void setCoreCompartment(CompartmentWrapper value);

} // DimensionEpidemic
