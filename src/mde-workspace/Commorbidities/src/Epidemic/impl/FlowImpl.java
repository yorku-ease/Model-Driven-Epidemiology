/**
 */
package Epidemic.impl;

import Epidemic.AbstractCompartment;
import Epidemic.EpidemicPackage;
import Epidemic.Flow;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Epidemic.impl.FlowImpl#getFrom <em>From</em>}</li>
 *   <li>{@link Epidemic.impl.FlowImpl#getTo <em>To</em>}</li>
 *   <li>{@link Epidemic.impl.FlowImpl#getSourceParameters <em>Source Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FlowImpl extends EObjectImpl implements Flow {
	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected AbstractCompartment from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected AbstractCompartment to;

	/**
	 * The default value of the '{@link #getSourceParameters() <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_PARAMETERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceParameters() <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceParameters()
	 * @generated
	 * @ordered
	 */
	protected String sourceParameters = SOURCE_PARAMETERS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidemicPackage.Literals.FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractCompartment getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject)from;
			from = (AbstractCompartment)eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EpidemicPackage.FLOW__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractCompartment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrom(AbstractCompartment newFrom) {
		AbstractCompartment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidemicPackage.FLOW__FROM, oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AbstractCompartment getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject)to;
			to = (AbstractCompartment)eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EpidemicPackage.FLOW__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractCompartment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTo(AbstractCompartment newTo) {
		AbstractCompartment oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidemicPackage.FLOW__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSourceParameters() {
		return sourceParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceParameters(String newSourceParameters) {
		String oldSourceParameters = sourceParameters;
		sourceParameters = newSourceParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidemicPackage.FLOW__SOURCE_PARAMETERS, oldSourceParameters, sourceParameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EpidemicPackage.FLOW__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
			case EpidemicPackage.FLOW__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case EpidemicPackage.FLOW__SOURCE_PARAMETERS:
				return getSourceParameters();
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
			case EpidemicPackage.FLOW__FROM:
				setFrom((AbstractCompartment)newValue);
				return;
			case EpidemicPackage.FLOW__TO:
				setTo((AbstractCompartment)newValue);
				return;
			case EpidemicPackage.FLOW__SOURCE_PARAMETERS:
				setSourceParameters((String)newValue);
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
			case EpidemicPackage.FLOW__FROM:
				setFrom((AbstractCompartment)null);
				return;
			case EpidemicPackage.FLOW__TO:
				setTo((AbstractCompartment)null);
				return;
			case EpidemicPackage.FLOW__SOURCE_PARAMETERS:
				setSourceParameters(SOURCE_PARAMETERS_EDEFAULT);
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
			case EpidemicPackage.FLOW__FROM:
				return from != null;
			case EpidemicPackage.FLOW__TO:
				return to != null;
			case EpidemicPackage.FLOW__SOURCE_PARAMETERS:
				return SOURCE_PARAMETERS_EDEFAULT == null ? sourceParameters != null : !SOURCE_PARAMETERS_EDEFAULT.equals(sourceParameters);
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
		result.append(" (sourceParameters: ");
		result.append(sourceParameters);
		result.append(')');
		return result.toString();
	}

} //FlowImpl
