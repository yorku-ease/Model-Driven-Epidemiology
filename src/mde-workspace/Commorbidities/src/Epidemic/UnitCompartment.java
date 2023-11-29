/**
 */
package Epidemic;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unit Compartment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link Epidemic.UnitCompartment#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see Epidemic.EpidemicPackage#getUnitCompartment()
 * @model
 * @generated
 */
public interface UnitCompartment extends AbstractCompartment {
	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see Epidemic.EpidemicPackage#getUnitCompartment_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link Epidemic.UnitCompartment#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

} // UnitCompartment
