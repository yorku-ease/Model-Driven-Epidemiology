/**
 */
package epidimensionsbase.impl;

import epidimensionsbase.Compartment;
import epidimensionsbase.EpidimensionsbasePackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CompartmentImpl extends epibase.impl.CompartmentImpl implements Compartment {

	@Override
	public void accept(Object visitor) throws Exception {
		throw new Exception("Not Implemented");
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsbasePackage.Literals.COMPARTMENT;
	}

} //CompartmentImpl
