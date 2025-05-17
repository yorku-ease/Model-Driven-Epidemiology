/**
 */
package seirmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import seirmodel.SeirmodelPackage;
import seirmodel.SuperInfectious;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Super Infectious</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link seirmodel.impl.SuperInfectiousImpl#isIsIsolated <em>Is Isolated</em>}</li>
 *   <li>{@link seirmodel.impl.SuperInfectiousImpl#getSymptomStatus <em>Symptom Status</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SuperInfectiousImpl extends CompartmentImpl implements SuperInfectious {
	/**
	 * The default value of the '{@link #isIsIsolated() <em>Is Isolated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsIsolated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_ISOLATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsIsolated() <em>Is Isolated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsIsolated()
	 * @generated
	 * @ordered
	 */
	protected boolean isIsolated = IS_ISOLATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getSymptomStatus() <em>Symptom Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymptomStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String SYMPTOM_STATUS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSymptomStatus() <em>Symptom Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymptomStatus()
	 * @generated
	 * @ordered
	 */
	protected String symptomStatus = SYMPTOM_STATUS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SuperInfectiousImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SeirmodelPackage.Literals.SUPER_INFECTIOUS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isIsIsolated() {
		return isIsolated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIsIsolated(boolean newIsIsolated) {
		boolean oldIsIsolated = isIsolated;
		isIsolated = newIsIsolated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.SUPER_INFECTIOUS__IS_ISOLATED, oldIsIsolated, isIsolated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSymptomStatus() {
		return symptomStatus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSymptomStatus(String newSymptomStatus) {
		String oldSymptomStatus = symptomStatus;
		symptomStatus = newSymptomStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.SUPER_INFECTIOUS__SYMPTOM_STATUS, oldSymptomStatus, symptomStatus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SeirmodelPackage.SUPER_INFECTIOUS__IS_ISOLATED:
				return isIsIsolated();
			case SeirmodelPackage.SUPER_INFECTIOUS__SYMPTOM_STATUS:
				return getSymptomStatus();
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
			case SeirmodelPackage.SUPER_INFECTIOUS__IS_ISOLATED:
				setIsIsolated((Boolean)newValue);
				return;
			case SeirmodelPackage.SUPER_INFECTIOUS__SYMPTOM_STATUS:
				setSymptomStatus((String)newValue);
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
			case SeirmodelPackage.SUPER_INFECTIOUS__IS_ISOLATED:
				setIsIsolated(IS_ISOLATED_EDEFAULT);
				return;
			case SeirmodelPackage.SUPER_INFECTIOUS__SYMPTOM_STATUS:
				setSymptomStatus(SYMPTOM_STATUS_EDEFAULT);
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
			case SeirmodelPackage.SUPER_INFECTIOUS__IS_ISOLATED:
				return isIsolated != IS_ISOLATED_EDEFAULT;
			case SeirmodelPackage.SUPER_INFECTIOUS__SYMPTOM_STATUS:
				return SYMPTOM_STATUS_EDEFAULT == null ? symptomStatus != null : !SYMPTOM_STATUS_EDEFAULT.equals(symptomStatus);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (IsIsolated: ");
		result.append(isIsolated);
		result.append(", symptomStatus: ");
		result.append(symptomStatus);
		result.append(')');
		return result.toString();
	}

} //SuperInfectiousImpl
