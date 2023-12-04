/**
 */
package src_ph.PhysicalEpidemicRoot.impl;

import src_ph.PhysicalEpidemicRoot.PhysicalCompartment;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemic;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemicRootPackage;
import src_ph.PhysicalEpidemicRoot.PhysicalFlow;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Physical Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemicRoot.impl.PhysicalEpidemicImpl#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.impl.PhysicalEpidemicImpl#getFlows <em>Flows</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PhysicalEpidemicImpl extends EObjectImpl implements PhysicalEpidemic {
	/**
	 * The cached value of the '{@link #getCompartments() <em>Compartments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartments()
	 * @generated
	 * @ordered
	 */
	protected EList<PhysicalCompartment> compartments;

	/**
	 * The cached value of the '{@link #getFlows() <em>Flows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlows()
	 * @generated
	 * @ordered
	 */
	protected EList<PhysicalFlow> flows;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PhysicalEpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PhysicalEpidemicRootPackage.Literals.PHYSICAL_EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PhysicalCompartment> getCompartments() {
		if (compartments == null) {
			compartments = new EObjectContainmentEList<PhysicalCompartment>(PhysicalCompartment.class, this, PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__COMPARTMENTS);
		}
		return compartments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<PhysicalFlow> getFlows() {
		if (flows == null) {
			flows = new EObjectContainmentEList<PhysicalFlow>(PhysicalFlow.class, this, PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__FLOWS);
		}
		return flows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__COMPARTMENTS:
				return ((InternalEList<?>)getCompartments()).basicRemove(otherEnd, msgs);
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__FLOWS:
				return ((InternalEList<?>)getFlows()).basicRemove(otherEnd, msgs);
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
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__COMPARTMENTS:
				return getCompartments();
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__FLOWS:
				return getFlows();
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
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__COMPARTMENTS:
				getCompartments().clear();
				getCompartments().addAll((Collection<? extends PhysicalCompartment>)newValue);
				return;
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__FLOWS:
				getFlows().clear();
				getFlows().addAll((Collection<? extends PhysicalFlow>)newValue);
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
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__COMPARTMENTS:
				getCompartments().clear();
				return;
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__FLOWS:
				getFlows().clear();
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
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__COMPARTMENTS:
				return compartments != null && !compartments.isEmpty();
			case PhysicalEpidemicRootPackage.PHYSICAL_EPIDEMIC__FLOWS:
				return flows != null && !flows.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PhysicalEpidemicImpl
