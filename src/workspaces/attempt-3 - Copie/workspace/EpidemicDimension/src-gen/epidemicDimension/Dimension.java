/**
 */
package epidemicDimension;

import epimodel.Compartment;
import epimodel.Flow;

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
 *   <li>{@link epidemicDimension.Dimension#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epidemicDimension.Dimension#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @see epidemicDimension.EpidemicDimensionPackage#getDimension()
 * @model
 * @generated
 */
public interface Dimension extends Compartment {
	/**
	 * Returns the value of the '<em><b>Compartment</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartment</em>' containment reference list.
	 * @see epidemicDimension.EpidemicDimensionPackage#getDimension_Compartment()
	 * @model containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartment();

	/**
	 * Returns the value of the '<em><b>Flow</b></em>' containment reference list.
	 * The list contents are of type {@link epimodel.Flow}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Flow</em>' containment reference list.
	 * @see epidemicDimension.EpidemicDimensionPackage#getDimension_Flow()
	 * @model containment="true"
	 * @generated
	 */
	EList<Flow> getFlow();

} // Dimension
