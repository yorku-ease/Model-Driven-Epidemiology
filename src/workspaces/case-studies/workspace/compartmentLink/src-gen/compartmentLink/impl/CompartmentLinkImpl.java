/**
 */
package compartmentLink.impl;

import compartmentLink.CompartmentLink;
import compartmentLink.CompartmentLinkPackage;

import epimodel.CompartmentWrapper;

import epimodel.impl.CompartmentImpl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compartmentLink.impl.CompartmentLinkImpl#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentLinkImpl extends CompartmentImpl implements CompartmentLink {
	/**
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper compartment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompartmentLinkPackage.Literals.COMPARTMENT_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getCompartment() {
		if (compartment != null && compartment.eIsProxy()) {
			InternalEObject oldCompartment = (InternalEObject) compartment;
			compartment = (CompartmentWrapper) eResolveProxy(oldCompartment);
			if (compartment != oldCompartment) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CompartmentLinkPackage.COMPARTMENT_LINK__COMPARTMENT, oldCompartment, compartment));
			}
		}
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentWrapper basicGetCompartment() {
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setCompartment(CompartmentWrapper newCompartment) {
		CompartmentWrapper oldCompartment = compartment;
		compartment = newCompartment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentLinkPackage.COMPARTMENT_LINK__COMPARTMENT,
					oldCompartment, compartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case CompartmentLinkPackage.COMPARTMENT_LINK__COMPARTMENT:
			if (resolve)
				return getCompartment();
			return basicGetCompartment();
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
		case CompartmentLinkPackage.COMPARTMENT_LINK__COMPARTMENT:
			setCompartment((CompartmentWrapper) newValue);
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
		case CompartmentLinkPackage.COMPARTMENT_LINK__COMPARTMENT:
			setCompartment((CompartmentWrapper) null);
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
		case CompartmentLinkPackage.COMPARTMENT_LINK__COMPARTMENT:
			return compartment != null;
		}
		return super.eIsSet(featureID);
	}

} //CompartmentLinkImpl
