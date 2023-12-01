/**
 */
package PhysicalEpidemic.impl;

import PhysicalEpidemic.EquationTemplate;
import PhysicalEpidemic.Label;
import PhysicalEpidemic.PhysicalCompartment;
import PhysicalEpidemic.PhysicalEpidemicPackage;
import PhysicalEpidemic.PhysicalFlow;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Physical Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemic.impl.PhysicalFlowImpl#getFrom <em>From</em>}</li>
 *   <li>{@link PhysicalEpidemic.impl.PhysicalFlowImpl#getTo <em>To</em>}</li>
 *   <li>{@link PhysicalEpidemic.impl.PhysicalFlowImpl#getEquationtemplate <em>Equationtemplate</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhysicalFlowImpl extends EObjectImpl implements PhysicalFlow {
	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected PhysicalCompartment from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected PhysicalCompartment to;

	/**
	 * The cached value of the '{@link #getEquationtemplate() <em>Equationtemplate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquationtemplate()
	 * @generated
	 * @ordered
	 */
	protected EquationTemplate equationtemplate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalFlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PhysicalEpidemicPackage.Literals.PHYSICAL_FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PhysicalCompartment getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject)from;
			from = (PhysicalCompartment)eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PhysicalEpidemicPackage.PHYSICAL_FLOW__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalCompartment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrom(PhysicalCompartment newFrom) {
		PhysicalCompartment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhysicalEpidemicPackage.PHYSICAL_FLOW__FROM, oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PhysicalCompartment getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject)to;
			to = (PhysicalCompartment)eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, PhysicalEpidemicPackage.PHYSICAL_FLOW__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalCompartment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTo(PhysicalCompartment newTo) {
		PhysicalCompartment oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhysicalEpidemicPackage.PHYSICAL_FLOW__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EquationTemplate getEquationtemplate() {
		return equationtemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEquationtemplate(EquationTemplate newEquationtemplate, NotificationChain msgs) {
		EquationTemplate oldEquationtemplate = equationtemplate;
		equationtemplate = newEquationtemplate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE, oldEquationtemplate, newEquationtemplate);
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
	public void setEquationtemplate(EquationTemplate newEquationtemplate) {
		if (newEquationtemplate != equationtemplate) {
			NotificationChain msgs = null;
			if (equationtemplate != null)
				msgs = ((InternalEObject)equationtemplate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE, null, msgs);
			if (newEquationtemplate != null)
				msgs = ((InternalEObject)newEquationtemplate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE, null, msgs);
			msgs = basicSetEquationtemplate(newEquationtemplate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE, newEquationtemplate, newEquationtemplate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE:
				return basicSetEquationtemplate(null, msgs);
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
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__FROM:
				if (resolve) return getFrom();
				return basicGetFrom();
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__TO:
				if (resolve) return getTo();
				return basicGetTo();
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE:
				return getEquationtemplate();
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
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__FROM:
				setFrom((PhysicalCompartment)newValue);
				return;
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__TO:
				setTo((PhysicalCompartment)newValue);
				return;
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE:
				setEquationtemplate((EquationTemplate)newValue);
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
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__FROM:
				setFrom((PhysicalCompartment)null);
				return;
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__TO:
				setTo((PhysicalCompartment)null);
				return;
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE:
				setEquationtemplate((EquationTemplate)null);
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
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__FROM:
				return from != null;
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__TO:
				return to != null;
			case PhysicalEpidemicPackage.PHYSICAL_FLOW__EQUATIONTEMPLATE:
				return equationtemplate != null;
		}
		return super.eIsSet(featureID);
	}

} //PhysicalFlowImpl
