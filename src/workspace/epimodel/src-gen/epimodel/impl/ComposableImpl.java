/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.Composable;
import epimodel.Epidemic;
import epimodel.EpimodelPackage;
import epimodel.Flow;
import epimodel.util.Difference;
import epimodel.util.PhysicalCompartment;

import java.util.List;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composable</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ComposableImpl extends MinimalEObjectImpl.Container implements Composable {

	@Override
	public final Difference diff(Composable other) {
		if (other.getClass().equals(getClass()))
			return compareWithSameClass(other);
		else if (other.getClass().equals(getBaseClass()))
			return compareWithBaseClass(other);
		else
			return compareWithDifferentClass(other);
	}

	@Override
	public Class<?> getBaseClass() {
		return null;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.COMPOSABLE;
	}

} //ComposableImpl
