/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.EpimodelPackage;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment Wrapper</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.CompartmentWrapperImpl#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentWrapperImpl extends MinimalEObjectImpl.Container implements CompartmentWrapper {

	@Override
	public void edit(EObject dom, Shell shell, List<Control> controls) {
		if (getCompartment() == null)
			displayCreateOption(dom, shell, controls);
	}
	
	void displayCreateOption(EObject dom, Shell shell, List<Control> controls) {
		epimodel.util.Edit.addCompartmentWindow(dom, shell, controls, wrapper -> {
			setCompartment(wrapper.getCompartment());
		});
	}

	/**
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected Compartment compartment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentWrapperImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.COMPARTMENT_WRAPPER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getCompartment() {
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompartment(Compartment newCompartment, NotificationChain msgs) {
		Compartment oldCompartment = compartment;
		compartment = newCompartment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT, oldCompartment, newCompartment);
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
	public void setCompartment(Compartment newCompartment) {
		if (newCompartment != compartment) {
			NotificationChain msgs = null;
			if (compartment != null)
				msgs = ((InternalEObject) compartment).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT, null, msgs);
			if (newCompartment != null)
				msgs = ((InternalEObject) newCompartment).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT, null, msgs);
			msgs = basicSetCompartment(newCompartment, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT,
					newCompartment, newCompartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT:
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
		case EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT:
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
		case EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT:
			setCompartment((Compartment) newValue);
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
		case EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT:
			setCompartment((Compartment) null);
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
		case EpimodelPackage.COMPARTMENT_WRAPPER__COMPARTMENT:
			return compartment != null;
		}
		return super.eIsSet(featureID);
	}

} //CompartmentWrapperImpl
