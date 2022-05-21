/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.Dimension;
import dimensionEpidemic.DimensionEpidemicPackage;

import epimodel.CompartmentWrapper;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.impl.DimensionImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensionEpidemic.impl.DimensionImpl#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionImpl extends CompartmentImpl implements Dimension {
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
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> compartment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DimensionEpidemicPackage.Literals.DIMENSION;
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
					DimensionEpidemicPackage.DIMENSION__FLOW);
		}
		return flow;
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
					DimensionEpidemicPackage.DIMENSION__COMPARTMENT);
		}
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DimensionEpidemicPackage.DIMENSION__FLOW:
			return ((InternalEList<?>) getFlow()).basicRemove(otherEnd, msgs);
		case DimensionEpidemicPackage.DIMENSION__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
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
		case DimensionEpidemicPackage.DIMENSION__FLOW:
			return getFlow();
		case DimensionEpidemicPackage.DIMENSION__COMPARTMENT:
			return getCompartment();
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
		case DimensionEpidemicPackage.DIMENSION__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
			return;
		case DimensionEpidemicPackage.DIMENSION__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends CompartmentWrapper>) newValue);
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
		case DimensionEpidemicPackage.DIMENSION__FLOW:
			getFlow().clear();
			return;
		case DimensionEpidemicPackage.DIMENSION__COMPARTMENT:
			getCompartment().clear();
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
		case DimensionEpidemicPackage.DIMENSION__FLOW:
			return flow != null && !flow.isEmpty();
		case DimensionEpidemicPackage.DIMENSION__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DimensionImpl
