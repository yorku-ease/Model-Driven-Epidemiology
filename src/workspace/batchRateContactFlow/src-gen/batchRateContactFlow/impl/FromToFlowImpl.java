/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.FromToFlow;

import batchRateContactFlow.Parameter;
import epimodel.Compartment;

import epimodel.impl.FlowImpl;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>From To Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getFrom <em>From</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getTo <em>To</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getParameter <em>Parameter</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FromToFlowImpl extends FlowImpl implements FromToFlow {

	@Override
	public void edit(Shell shell, List<Control> controls) {
		System.out.println(getId());
//		shell.setText("Edit Flow " + getId() + " for compartment " + target.getLabel());
//		shell.setLayout(new GridLayout(2, false));
//		for (EReference ref : flowRefs()) {
//			epimodel.util.Edit.addText(shell, controls, ref.getName());
//			epimodel.util.Edit.addBtn(shell, controls, "Set '" + ref.getName() + "' to " + target.getLabel(), () -> {
//				epimodel.util.Edit.transact(this, () -> eSet(ref, target));
//				shell.close();
//			});
//		}
	}

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Compartment from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Compartment to;

	/**
	 * The cached value of the '{@link #getParameter() <em>Parameter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameter()
	 * @generated
	 * @ordered
	 */
	protected EList<Parameter> parameter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FromToFlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.FROM_TO_FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject) from;
			from = (Compartment) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BatchRateContactFlowPackage.FROM_TO_FLOW__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrom(Compartment newFrom) {
		Compartment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.FROM_TO_FLOW__FROM,
					oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject) to;
			to = (Compartment) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BatchRateContactFlowPackage.FROM_TO_FLOW__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTo(Compartment newTo) {
		Compartment oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.FROM_TO_FLOW__TO, oldTo,
					to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameter() {
		if (parameter == null) {
			parameter = new EObjectContainmentEList<Parameter>(Parameter.class, this,
					BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETER);
		}
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETER:
			return ((InternalEList<?>) getParameter()).basicRemove(otherEnd, msgs);
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
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			if (resolve)
				return getFrom();
			return basicGetFrom();
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			if (resolve)
				return getTo();
			return basicGetTo();
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETER:
			return getParameter();
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
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			setFrom((Compartment) newValue);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			setTo((Compartment) newValue);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETER:
			getParameter().clear();
			getParameter().addAll((Collection<? extends Parameter>) newValue);
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
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			setFrom((Compartment) null);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			setTo((Compartment) null);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETER:
			getParameter().clear();
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
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			return from != null;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			return to != null;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETER:
			return parameter != null && !parameter.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FromToFlowImpl
