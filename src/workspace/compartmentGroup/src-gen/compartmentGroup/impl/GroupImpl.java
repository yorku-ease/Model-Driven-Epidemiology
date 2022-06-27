/**
 */
package compartmentGroup.impl;

import compartmentGroup.CompartmentGroupPackage;
import compartmentGroup.Group;
import compartmentGroup.GroupSinks;
import compartmentGroup.GroupSources;
import compartmentGroup.Link;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
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
 *   <li>{@link compartmentGroup.impl.GroupImpl#getGroupSinks <em>Group Sinks</em>}</li>
 *   <li>{@link compartmentGroup.impl.GroupImpl#getGroupSources <em>Group Sources</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupImpl extends CompartmentImpl implements Group {

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return getCompartment().stream().map(CompartmentWrapper::getCompartment)
				.map(Compartment::getPhysicalCompartments).flatMap(List::stream).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		if (getGroupSources() != null && getGroupSources().getLink().size() > 0)
			return getCompartment()
					.stream()
					.map(CompartmentWrapper::getCompartment)
					.filter(c -> getGroupSources()
									.getLink()
									.stream()
									.map(Link::getCompartment)
									.collect(Collectors.toList())
									.contains(c))
					.map(Compartment::getSources)
					.flatMap(List::stream)
					.collect(Collectors.toList());
		else
			return new ArrayList<PhysicalCompartment>(Arrays.asList(new PhysicalCompartment(Arrays.asList(getId()))));
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		if (getGroupSinks() != null && getGroupSinks().getLink().size() > 0)
			return getCompartment()
					.stream()
					.map(CompartmentWrapper::getCompartment)
					.filter(c -> getGroupSinks()
									.getLink()
									.stream()
									.map(Link::getCompartment)
									.collect(Collectors.toList())
									.contains(c))
					.map(Compartment::getSinks)
					.flatMap(List::stream)
					.collect(Collectors.toList());
		else
			return new ArrayList<PhysicalCompartment>(Arrays.asList(new PhysicalCompartment(Arrays.asList(getId()))));
	}

	@Override
	public List<Flow> getFlows() {
		return getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());
	}

	protected PhysicalCompartment prependSelf(PhysicalCompartment p) {
		p.labels.add(0, this.getId());
		return p;
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
	 * The cached value of the '{@link #getGroupSinks() <em>Group Sinks</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupSinks()
	 * @generated
	 * @ordered
	 */
	protected GroupSinks groupSinks;
	/**
	 * The cached value of the '{@link #getGroupSources() <em>Group Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupSources()
	 * @generated
	 * @ordered
	 */
	protected GroupSources groupSources;

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
	public GroupSinks getGroupSinks() {
		return groupSinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupSinks(GroupSinks newGroupSinks, NotificationChain msgs) {
		GroupSinks oldGroupSinks = groupSinks;
		groupSinks = newGroupSinks;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CompartmentGroupPackage.GROUP__GROUP_SINKS, oldGroupSinks, newGroupSinks);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGroupSinks(GroupSinks newGroupSinks) {
		if (newGroupSinks != groupSinks) {
			NotificationChain msgs = null;
			if (groupSinks != null)
				msgs = ((InternalEObject) groupSinks).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP__GROUP_SINKS, null, msgs);
			if (newGroupSinks != null)
				msgs = ((InternalEObject) newGroupSinks).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP__GROUP_SINKS, null, msgs);
			msgs = basicSetGroupSinks(newGroupSinks, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentGroupPackage.GROUP__GROUP_SINKS,
					newGroupSinks, newGroupSinks));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroupSources getGroupSources() {
		return groupSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupSources(GroupSources newGroupSources, NotificationChain msgs) {
		GroupSources oldGroupSources = groupSources;
		groupSources = newGroupSources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CompartmentGroupPackage.GROUP__GROUP_SOURCES, oldGroupSources, newGroupSources);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGroupSources(GroupSources newGroupSources) {
		if (newGroupSources != groupSources) {
			NotificationChain msgs = null;
			if (groupSources != null)
				msgs = ((InternalEObject) groupSources).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP__GROUP_SOURCES, null, msgs);
			if (newGroupSources != null)
				msgs = ((InternalEObject) newGroupSources).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP__GROUP_SOURCES, null, msgs);
			msgs = basicSetGroupSources(newGroupSources, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentGroupPackage.GROUP__GROUP_SOURCES,
					newGroupSources, newGroupSources));
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
		case CompartmentGroupPackage.GROUP__GROUP_SINKS:
			return basicSetGroupSinks(null, msgs);
		case CompartmentGroupPackage.GROUP__GROUP_SOURCES:
			return basicSetGroupSources(null, msgs);
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
		case CompartmentGroupPackage.GROUP__GROUP_SINKS:
			return getGroupSinks();
		case CompartmentGroupPackage.GROUP__GROUP_SOURCES:
			return getGroupSources();
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
		case CompartmentGroupPackage.GROUP__GROUP_SINKS:
			setGroupSinks((GroupSinks) newValue);
			return;
		case CompartmentGroupPackage.GROUP__GROUP_SOURCES:
			setGroupSources((GroupSources) newValue);
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
		case CompartmentGroupPackage.GROUP__GROUP_SINKS:
			setGroupSinks((GroupSinks) null);
			return;
		case CompartmentGroupPackage.GROUP__GROUP_SOURCES:
			setGroupSources((GroupSources) null);
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
		case CompartmentGroupPackage.GROUP__GROUP_SINKS:
			return groupSinks != null;
		case CompartmentGroupPackage.GROUP__GROUP_SOURCES:
			return groupSources != null;
		}
		return super.eIsSet(featureID);
	}

} //GroupImpl
