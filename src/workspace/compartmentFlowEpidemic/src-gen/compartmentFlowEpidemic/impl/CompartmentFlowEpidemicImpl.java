/**
 */
package compartmentFlowEpidemic.impl;

import compartmentFlowEpidemic.CompartmentFlowEpidemic;
import compartmentFlowEpidemic.CompartmentFlowEpidemicPackage;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.FlowWrapper;

import epimodel.impl.EpidemicImpl;
import epimodel.util.PhysicalCompartment;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment Flow Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compartmentFlowEpidemic.impl.CompartmentFlowEpidemicImpl#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link compartmentFlowEpidemic.impl.CompartmentFlowEpidemicImpl#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentFlowEpidemicImpl extends EpidemicImpl implements CompartmentFlowEpidemic {
	
	@Override
	public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
		throw new NullPointerException("getPhysicalFor Not Implemented for CompartmentFlowEpidemicImpl");
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSourcesFor(Compartment c) {
		throw new NullPointerException("getPhysicalSourcesFor Not Implemented for CompartmentFlowEpidemicImpl");
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSinksFor(Compartment c) {
		throw new NullPointerException("getPhysicalSinksFor Not Implemented for CompartmentFlowEpidemicImpl");
	}
	
	/**
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> compartment;

	/**
	 * The cached value of the '{@link #getFlow() <em>Flow</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlow()
	 * @generated
	 * @ordered
	 */
	protected EList<FlowWrapper> flow;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentFlowEpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompartmentFlowEpidemicPackage.Literals.COMPARTMENT_FLOW_EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CompartmentWrapper> getCompartment() {
		if (compartment == null) {
			compartment = new EObjectContainmentEList<CompartmentWrapper>(CompartmentWrapper.class, this,
					CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT);
		}
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FlowWrapper> getFlow() {
		if (flow == null) {
			flow = new EObjectContainmentEList<FlowWrapper>(FlowWrapper.class, this,
					CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__FLOW);
		}
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__FLOW:
			return ((InternalEList<?>) getFlow()).basicRemove(otherEnd, msgs);
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
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT:
			return getCompartment();
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__FLOW:
			return getFlow();
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
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends CompartmentWrapper>) newValue);
			return;
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
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
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT:
			getCompartment().clear();
			return;
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__FLOW:
			getFlow().clear();
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
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		case CompartmentFlowEpidemicPackage.COMPARTMENT_FLOW_EPIDEMIC__FLOW:
			return flow != null && !flow.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CompartmentFlowEpidemicImpl