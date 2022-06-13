/**
 */
package compartmentGroup.impl;

import compartmentGroup.CompartmentGroupPackage;
import compartmentGroup.Group;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;

import epimodel.FlowWrapper;
import epimodel.impl.CompartmentImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compartmentGroup.impl.GroupImpl#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link compartmentGroup.impl.GroupImpl#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupImpl extends CompartmentImpl implements Group {

	@Override
	public List<List<String>> extend(List<List<String>> current) {

		String c1 = current.toString();

		//		final List<List<String>> myCompartments = getCompartment().stream().reduce(
		//				(List<List<String>>) new ArrayList<List<String>>(),
		//				(l, cmpt) -> cmpt.getCompartment().extend(l),
		//				(l, what) -> {
		//					System.out.println(l);
		//					System.out.println(what);
		//					return l;
		//				}
		//			);

		List<List<String>> extended = new ArrayList<>();

		for (Compartment c : getCompartment().stream().map(CompartmentWrapper::getCompartment)
				.collect(Collectors.toList()))
			extended = c.extend(extended);

		current.addAll(extended);

		System.out.println(String.format("Group %s Extend: current: %s, myCompartments: %s, current: %s", getId(), c1,
				extended, current));

		return current;
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
	protected GroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompartmentGroupPackage.Literals.GROUP;
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
					CompartmentGroupPackage.GROUP__COMPARTMENT);
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
					CompartmentGroupPackage.GROUP__FLOW);
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
		case CompartmentGroupPackage.GROUP__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
		case CompartmentGroupPackage.GROUP__FLOW:
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
		case CompartmentGroupPackage.GROUP__COMPARTMENT:
			return getCompartment();
		case CompartmentGroupPackage.GROUP__FLOW:
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
		case CompartmentGroupPackage.GROUP__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends CompartmentWrapper>) newValue);
			return;
		case CompartmentGroupPackage.GROUP__FLOW:
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
		case CompartmentGroupPackage.GROUP__COMPARTMENT:
			getCompartment().clear();
			return;
		case CompartmentGroupPackage.GROUP__FLOW:
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
		case CompartmentGroupPackage.GROUP__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		case CompartmentGroupPackage.GROUP__FLOW:
			return flow != null && !flow.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GroupImpl
