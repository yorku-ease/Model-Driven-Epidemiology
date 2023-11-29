/**
 */
package Epidemic.impl;

import Epidemic.AbstractCompartment;
import Epidemic.EpidemicPackage;
import Epidemic.Flow;
import Epidemic.Group;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link Epidemic.impl.GroupImpl#getFlows <em>Flows</em>}</li>
 *   <li>{@link Epidemic.impl.GroupImpl#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link Epidemic.impl.GroupImpl#getSink <em>Sink</em>}</li>
 *   <li>{@link Epidemic.impl.GroupImpl#getSource <em>Source</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupImpl extends AbstractCompartmentImpl implements Group {
	/**
	 * The cached value of the '{@link #getFlows() <em>Flows</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlows()
	 * @generated
	 * @ordered
	 */
	protected EList<Flow> flows;

	/**
	 * The cached value of the '{@link #getCompartments() <em>Compartments</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartments()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractCompartment> compartments;

	/**
	 * The cached value of the '{@link #getSink() <em>Sink</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSink()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractCompartment> sink;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractCompartment> source;

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
		return EpidemicPackage.Literals.GROUP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Flow> getFlows() {
		if (flows == null) {
			flows = new EObjectContainmentEList<Flow>(Flow.class, this, EpidemicPackage.GROUP__FLOWS);
		}
		return flows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractCompartment> getCompartments() {
		if (compartments == null) {
			compartments = new EObjectContainmentEList<AbstractCompartment>(AbstractCompartment.class, this, EpidemicPackage.GROUP__COMPARTMENTS);
		}
		return compartments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractCompartment> getSink() {
		if (sink == null) {
			sink = new EObjectResolvingEList<AbstractCompartment>(AbstractCompartment.class, this, EpidemicPackage.GROUP__SINK);
		}
		return sink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AbstractCompartment> getSource() {
		if (source == null) {
			source = new EObjectResolvingEList<AbstractCompartment>(AbstractCompartment.class, this, EpidemicPackage.GROUP__SOURCE);
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EpidemicPackage.GROUP__FLOWS:
				return ((InternalEList<?>)getFlows()).basicRemove(otherEnd, msgs);
			case EpidemicPackage.GROUP__COMPARTMENTS:
				return ((InternalEList<?>)getCompartments()).basicRemove(otherEnd, msgs);
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
			case EpidemicPackage.GROUP__FLOWS:
				return getFlows();
			case EpidemicPackage.GROUP__COMPARTMENTS:
				return getCompartments();
			case EpidemicPackage.GROUP__SINK:
				return getSink();
			case EpidemicPackage.GROUP__SOURCE:
				return getSource();
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
			case EpidemicPackage.GROUP__FLOWS:
				getFlows().clear();
				getFlows().addAll((Collection<? extends Flow>)newValue);
				return;
			case EpidemicPackage.GROUP__COMPARTMENTS:
				getCompartments().clear();
				getCompartments().addAll((Collection<? extends AbstractCompartment>)newValue);
				return;
			case EpidemicPackage.GROUP__SINK:
				getSink().clear();
				getSink().addAll((Collection<? extends AbstractCompartment>)newValue);
				return;
			case EpidemicPackage.GROUP__SOURCE:
				getSource().clear();
				getSource().addAll((Collection<? extends AbstractCompartment>)newValue);
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
			case EpidemicPackage.GROUP__FLOWS:
				getFlows().clear();
				return;
			case EpidemicPackage.GROUP__COMPARTMENTS:
				getCompartments().clear();
				return;
			case EpidemicPackage.GROUP__SINK:
				getSink().clear();
				return;
			case EpidemicPackage.GROUP__SOURCE:
				getSource().clear();
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
			case EpidemicPackage.GROUP__FLOWS:
				return flows != null && !flows.isEmpty();
			case EpidemicPackage.GROUP__COMPARTMENTS:
				return compartments != null && !compartments.isEmpty();
			case EpidemicPackage.GROUP__SINK:
				return sink != null && !sink.isEmpty();
			case EpidemicPackage.GROUP__SOURCE:
				return source != null && !source.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GroupImpl
