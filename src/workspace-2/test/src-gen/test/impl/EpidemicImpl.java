/**
 */
package test.impl;

import epibase.Compartment;
import epibase.Epidemic;
import epibase.Flow;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import test.TestPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class EpidemicImpl extends MinimalEObjectImpl.Container implements Epidemic {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestPackage.Literals.EPIDEMIC;
	}

	@Override
	public EList<Compartment> getCompartment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Flow> getFlow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String value) {
		// TODO Auto-generated method stub

	}

} //EpidemicImpl
