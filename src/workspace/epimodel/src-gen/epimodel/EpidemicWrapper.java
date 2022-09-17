/**
 */
package epimodel;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Epidemic Wrapper</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link epimodel.EpidemicWrapper#getEpidemic <em>Epidemic</em>}</li>
 * </ul>
 *
 * @see epimodel.EpimodelPackage#getEpidemicWrapper()
 * @model
 * @generated
 */
public interface EpidemicWrapper extends EObject {
	/**
	 * Returns the value of the '<em><b>Epidemic</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Epidemic</em>' containment reference.
	 * @see #setEpidemic(Epidemic)
	 * @see epimodel.EpimodelPackage#getEpidemicWrapper_Epidemic()
	 * @model containment="true"
	 * @generated
	 */
	Epidemic getEpidemic();

	/**
	 * Sets the value of the '{@link epimodel.EpidemicWrapper#getEpidemic <em>Epidemic</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Epidemic</em>' containment reference.
	 * @see #getEpidemic()
	 * @generated
	 */
	void setEpidemic(Epidemic value);
	
	void edit(EObject dom, Shell shell, List<Control> controls);

} // EpidemicWrapper
