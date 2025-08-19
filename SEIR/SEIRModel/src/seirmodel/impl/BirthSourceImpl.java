/**
 */
package seirmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import seirmodel.BirthSource;
import seirmodel.Compartment;
import seirmodel.SeirmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Birth Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link seirmodel.impl.BirthSourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link seirmodel.impl.BirthSourceImpl#getRate <em>Rate</em>}</li>
 *   <li>{@link seirmodel.impl.BirthSourceImpl#getTargetCompartment <em>Target Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BirthSourceImpl extends MinimalEObjectImpl.Container implements BirthSource {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRate() <em>Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRate()
	 * @generated
	 * @ordered
	 */
	protected static final double RATE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRate() <em>Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRate()
	 * @generated
	 * @ordered
	 */
	protected double rate = RATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTargetCompartment() <em>Target Compartment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetCompartment()
	 * @generated
	 * @ordered
	 */
	protected Compartment targetCompartment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BirthSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SeirmodelPackage.Literals.BIRTH_SOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.BIRTH_SOURCE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getRate() {
		return rate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRate(double newRate) {
		double oldRate = rate;
		rate = newRate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.BIRTH_SOURCE__RATE, oldRate, rate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getTargetCompartment() {
		if (targetCompartment != null && targetCompartment.eIsProxy()) {
			InternalEObject oldTargetCompartment = (InternalEObject)targetCompartment;
			targetCompartment = (Compartment)eResolveProxy(oldTargetCompartment);
			if (targetCompartment != oldTargetCompartment) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, SeirmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT, oldTargetCompartment, targetCompartment));
			}
		}
		return targetCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetTargetCompartment() {
		return targetCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetCompartment(Compartment newTargetCompartment) {
		Compartment oldTargetCompartment = targetCompartment;
		targetCompartment = newTargetCompartment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SeirmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT, oldTargetCompartment, targetCompartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case SeirmodelPackage.BIRTH_SOURCE__NAME:
				return getName();
			case SeirmodelPackage.BIRTH_SOURCE__RATE:
				return getRate();
			case SeirmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				if (resolve) return getTargetCompartment();
				return basicGetTargetCompartment();
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
			case SeirmodelPackage.BIRTH_SOURCE__NAME:
				setName((String)newValue);
				return;
			case SeirmodelPackage.BIRTH_SOURCE__RATE:
				setRate((Double)newValue);
				return;
			case SeirmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				setTargetCompartment((Compartment)newValue);
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
			case SeirmodelPackage.BIRTH_SOURCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SeirmodelPackage.BIRTH_SOURCE__RATE:
				setRate(RATE_EDEFAULT);
				return;
			case SeirmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				setTargetCompartment((Compartment)null);
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
			case SeirmodelPackage.BIRTH_SOURCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SeirmodelPackage.BIRTH_SOURCE__RATE:
				return rate != RATE_EDEFAULT;
			case SeirmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				return targetCompartment != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", rate: ");
		result.append(rate);
		result.append(')');
		return result.toString();
	}

} //BirthSourceImpl
