/**
 */
package compartmentalmodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import compartmentalmodel.BirthSource;
import compartmentalmodel.Compartment;
import compartmentalmodel.Parameter;
import compartmentalmodel.CompartmentalmodelPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Birth Source</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compartmentalmodel.impl.BirthSourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link compartmentalmodel.impl.BirthSourceImpl#getRate <em>Rate</em>}</li>
 *   <li>{@link compartmentalmodel.impl.BirthSourceImpl#getRateParameter <em>Rate Parameter</em>}</li>
 *   <li>{@link compartmentalmodel.impl.BirthSourceImpl#getTargetCompartment <em>Target Compartment</em>}</li>
 *   <li>{@link compartmentalmodel.impl.BirthSourceImpl#getTargetStratum <em>Target Stratum</em>}</li>
 *   <li>{@link compartmentalmodel.impl.BirthSourceImpl#isFixedRate <em>Fixed Rate</em>}</li>
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
	 * The cached value of the '{@link #getRateParameter() <em>Rate Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRateParameter()
	 * @generated
	 * @ordered
	 */
	protected Parameter rateParameter;

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
	 * The default value of the '{@link #getTargetStratum() <em>Target Stratum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetStratum()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_STRATUM_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetStratum() <em>Target Stratum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetStratum()
	 * @generated
	 * @ordered
	 */
	protected String targetStratum = TARGET_STRATUM_EDEFAULT;

	/**
	 * The default value of the '{@link #isFixedRate() <em>Fixed Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedRate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FIXED_RATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFixedRate() <em>Fixed Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFixedRate()
	 * @generated
	 * @ordered
	 */
	protected boolean fixedRate = FIXED_RATE_EDEFAULT;

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
		return CompartmentalmodelPackage.Literals.BIRTH_SOURCE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.BIRTH_SOURCE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.BIRTH_SOURCE__RATE, oldRate, rate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Parameter getRateParameter() {
		if (rateParameter != null && rateParameter.eIsProxy()) {
			InternalEObject oldRateParameter = (InternalEObject)rateParameter;
			rateParameter = (Parameter)eResolveProxy(oldRateParameter);
			if (rateParameter != oldRateParameter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompartmentalmodelPackage.BIRTH_SOURCE__RATE_PARAMETER, oldRateParameter, rateParameter));
			}
		}
		return rateParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter basicGetRateParameter() {
		return rateParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRateParameter(Parameter newRateParameter) {
		Parameter oldRateParameter = rateParameter;
		rateParameter = newRateParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.BIRTH_SOURCE__RATE_PARAMETER, oldRateParameter, rateParameter));
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
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT, oldTargetCompartment, targetCompartment));
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
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT, oldTargetCompartment, targetCompartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTargetStratum() {
		return targetStratum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetStratum(String newTargetStratum) {
		String oldTargetStratum = targetStratum;
		targetStratum = newTargetStratum;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_STRATUM, oldTargetStratum, targetStratum));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isFixedRate() {
		return fixedRate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFixedRate(boolean newFixedRate) {
		boolean oldFixedRate = fixedRate;
		fixedRate = newFixedRate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentalmodelPackage.BIRTH_SOURCE__FIXED_RATE, oldFixedRate, fixedRate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CompartmentalmodelPackage.BIRTH_SOURCE__NAME:
				return getName();
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE:
				return getRate();
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE_PARAMETER:
				if (resolve) return getRateParameter();
				return basicGetRateParameter();
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				if (resolve) return getTargetCompartment();
				return basicGetTargetCompartment();
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_STRATUM:
				return getTargetStratum();
			case CompartmentalmodelPackage.BIRTH_SOURCE__FIXED_RATE:
				return isFixedRate();
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
			case CompartmentalmodelPackage.BIRTH_SOURCE__NAME:
				setName((String)newValue);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE:
				setRate((Double)newValue);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE_PARAMETER:
				setRateParameter((Parameter)newValue);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				setTargetCompartment((Compartment)newValue);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_STRATUM:
				setTargetStratum((String)newValue);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__FIXED_RATE:
				setFixedRate((Boolean)newValue);
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
			case CompartmentalmodelPackage.BIRTH_SOURCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE:
				setRate(RATE_EDEFAULT);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE_PARAMETER:
				setRateParameter((Parameter)null);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				setTargetCompartment((Compartment)null);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_STRATUM:
				setTargetStratum(TARGET_STRATUM_EDEFAULT);
				return;
			case CompartmentalmodelPackage.BIRTH_SOURCE__FIXED_RATE:
				setFixedRate(FIXED_RATE_EDEFAULT);
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
			case CompartmentalmodelPackage.BIRTH_SOURCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE:
				return rate != RATE_EDEFAULT;
			case CompartmentalmodelPackage.BIRTH_SOURCE__RATE_PARAMETER:
				return rateParameter != null;
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_COMPARTMENT:
				return targetCompartment != null;
			case CompartmentalmodelPackage.BIRTH_SOURCE__TARGET_STRATUM:
				return TARGET_STRATUM_EDEFAULT == null ? targetStratum != null : !TARGET_STRATUM_EDEFAULT.equals(targetStratum);
			case CompartmentalmodelPackage.BIRTH_SOURCE__FIXED_RATE:
				return fixedRate != FIXED_RATE_EDEFAULT;
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
		result.append(", targetStratum: ");
		result.append(targetStratum);
		result.append(", fixedRate: ");
		result.append(fixedRate);
		result.append(')');
		return result.toString();
	}

} //BirthSourceImpl
