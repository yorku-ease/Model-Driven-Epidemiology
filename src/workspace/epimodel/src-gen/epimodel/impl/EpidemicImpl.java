/**
 */
package epimodel.impl;

import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.EpimodelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.EpidemicImpl#getCompartmentwrapper <em>Compartmentwrapper</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicImpl extends MinimalEObjectImpl.Container implements Epidemic {
	/**
	 * The cached value of the '{@link #getCompartmentwrapper() <em>Compartmentwrapper</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartmentwrapper()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper compartmentwrapper;

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
		return EpimodelPackage.Literals.EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getCompartmentwrapper() {
		return compartmentwrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompartmentwrapper(CompartmentWrapper newCompartmentwrapper,
			NotificationChain msgs) {
		CompartmentWrapper oldCompartmentwrapper = compartmentwrapper;
		compartmentwrapper = newCompartmentwrapper;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER, oldCompartmentwrapper, newCompartmentwrapper);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompartmentwrapper(CompartmentWrapper newCompartmentwrapper) {
		if (newCompartmentwrapper != compartmentwrapper) {
			NotificationChain msgs = null;
			if (compartmentwrapper != null)
				msgs = ((InternalEObject) compartmentwrapper).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER, null, msgs);
			if (newCompartmentwrapper != null)
				msgs = ((InternalEObject) newCompartmentwrapper).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER, null, msgs);
			msgs = basicSetCompartmentwrapper(newCompartmentwrapper, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER,
					newCompartmentwrapper, newCompartmentwrapper));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER:
			return basicSetCompartmentwrapper(null, msgs);
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
		case EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER:
			return getCompartmentwrapper();
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
		case EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER:
			setCompartmentwrapper((CompartmentWrapper) newValue);
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
		case EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER:
			setCompartmentwrapper((CompartmentWrapper) null);
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
		case EpimodelPackage.EPIDEMIC__COMPARTMENTWRAPPER:
			return compartmentwrapper != null;
		}
		return super.eIsSet(featureID);
	}

} //EpidemicImpl
