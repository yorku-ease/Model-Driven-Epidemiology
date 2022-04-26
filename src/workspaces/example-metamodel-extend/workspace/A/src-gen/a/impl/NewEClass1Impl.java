/**
 */
package a.impl;

import a.APackage;
import a.NewEClass1;
import a.NewEClass2;
import a.NewEClass3;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>New EClass1</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link a.impl.NewEClass1Impl#getNeweclass2 <em>Neweclass2</em>}</li>
 *   <li>{@link a.impl.NewEClass1Impl#getNeweclass3 <em>Neweclass3</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NewEClass1Impl extends MinimalEObjectImpl.Container implements NewEClass1 {
	/**
	 * The cached value of the '{@link #getNeweclass2() <em>Neweclass2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNeweclass2()
	 * @generated
	 * @ordered
	 */
	protected EList<NewEClass2> neweclass2;

	/**
	 * The cached value of the '{@link #getNeweclass3() <em>Neweclass3</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNeweclass3()
	 * @generated
	 * @ordered
	 */
	protected EList<NewEClass3> neweclass3;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NewEClass1Impl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return APackage.Literals.NEW_ECLASS1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NewEClass2> getNeweclass2() {
		if (neweclass2 == null) {
			neweclass2 = new EObjectContainmentEList<NewEClass2>(NewEClass2.class, this,
					APackage.NEW_ECLASS1__NEWECLASS2);
		}
		return neweclass2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<NewEClass3> getNeweclass3() {
		if (neweclass3 == null) {
			neweclass3 = new EObjectContainmentEList<NewEClass3>(NewEClass3.class, this,
					APackage.NEW_ECLASS1__NEWECLASS3);
		}
		return neweclass3;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case APackage.NEW_ECLASS1__NEWECLASS2:
			return ((InternalEList<?>) getNeweclass2()).basicRemove(otherEnd, msgs);
		case APackage.NEW_ECLASS1__NEWECLASS3:
			return ((InternalEList<?>) getNeweclass3()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case APackage.NEW_ECLASS1__NEWECLASS2:
			return getNeweclass2();
		case APackage.NEW_ECLASS1__NEWECLASS3:
			return getNeweclass3();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case APackage.NEW_ECLASS1__NEWECLASS2:
			getNeweclass2().clear();
			getNeweclass2().addAll((Collection<? extends NewEClass2>) newValue);
			return;
		case APackage.NEW_ECLASS1__NEWECLASS3:
			getNeweclass3().clear();
			getNeweclass3().addAll((Collection<? extends NewEClass3>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case APackage.NEW_ECLASS1__NEWECLASS2:
			getNeweclass2().clear();
			return;
		case APackage.NEW_ECLASS1__NEWECLASS3:
			getNeweclass3().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case APackage.NEW_ECLASS1__NEWECLASS2:
			return neweclass2 != null && !neweclass2.isEmpty();
		case APackage.NEW_ECLASS1__NEWECLASS3:
			return neweclass3 != null && !neweclass3.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NewEClass1Impl
