/**
 */
package EpidemicRoot;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Compartment</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link EpidemicRoot.AbstractCompartment#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @see EpidemicRoot.EpidemicRootPackage#getAbstractCompartment()
 * @model abstract="true"
 * @generated
 */
public interface AbstractCompartment extends EObject {

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see EpidemicRoot.EpidemicRootPackage#getAbstractCompartment_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link EpidemicRoot.AbstractCompartment#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);
} // AbstractCompartment
