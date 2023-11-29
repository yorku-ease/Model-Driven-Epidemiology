/**
 */
package Epidemic.impl;

import Epidemic.AbstractCompartment;
import Epidemic.Epidemic;
import Epidemic.EpidemicPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Epidemic.impl.EpidemicImpl#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicImpl extends EObjectImpl implements Epidemic {
	/**
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected AbstractCompartment compartment;

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
		return EpidemicPackage.Literals.EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractCompartment getCompartment() {
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompartment(AbstractCompartment newCompartment, NotificationChain msgs) {
		AbstractCompartment oldCompartment = compartment;
		compartment = newCompartment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EpidemicPackage.EPIDEMIC__COMPARTMENT, oldCompartment, newCompartment);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompartment(AbstractCompartment newCompartment) {
		if (newCompartment != compartment) {
			NotificationChain msgs = null;
			if (compartment != null)
				msgs = ((InternalEObject)compartment).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EpidemicPackage.EPIDEMIC__COMPARTMENT, null, msgs);
			if (newCompartment != null)
				msgs = ((InternalEObject)newCompartment).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EpidemicPackage.EPIDEMIC__COMPARTMENT, null, msgs);
			msgs = basicSetCompartment(newCompartment, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidemicPackage.EPIDEMIC__COMPARTMENT, newCompartment, newCompartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EpidemicPackage.EPIDEMIC__COMPARTMENT:
				return basicSetCompartment(null, msgs);
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
			case EpidemicPackage.EPIDEMIC__COMPARTMENT:
				return getCompartment();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case EpidemicPackage.EPIDEMIC__COMPARTMENT:
				setCompartment((AbstractCompartment)newValue);
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
			case EpidemicPackage.EPIDEMIC__COMPARTMENT:
				setCompartment((AbstractCompartment)null);
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
			case EpidemicPackage.EPIDEMIC__COMPARTMENT:
				return compartment != null;
		}
		return super.eIsSet(featureID);
	}

} //EpidemicImpl
