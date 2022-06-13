/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.Dimension;
import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.DimensionWrapper;
import epimodel.CompartmentWrapper;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;
import epimodel.util.ListForwardDecorator;

import java.util.ArrayList;
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
 * An implementation of the model object '<em><b>Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.impl.DimensionImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensionEpidemic.impl.DimensionImpl#getCoreCompartment <em>Core Compartment</em>}</li>
 *   <li>{@link dimensionEpidemic.impl.DimensionImpl#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionImpl extends CompartmentImpl implements Dimension {

	@Override
	public List<String> getDeclaredLabels() {
		return null;/*getCompartment().stream().map(CompartmentWrapper::getCompartment).map(Compartment::getDeclaredLabels)
					.flatMap(List::stream).collect(Collectors.toList());*/
	}

	@Override
	public List<List<String>> extend(List<List<String>> current) {
		
		List<List<String>> core = this.getCoreCompartment().getCompartment().extend(new ArrayList<>());

//		final List<List<String>> myCompartments = getDimension().stream().reduce(
//				core,
//				(l, dim) -> dim.getDimension().extend(l), (l, what) -> {
//					System.out.println(l);
//					System.out.println(what);
//					return l;
//				});

		List<List<String>> extended = core;
		final List<List<String>> extended2 = extended;

		for (Dimension d : getDimension().stream().map(DimensionWrapper::getDimension).collect(Collectors.toList()))
			extended = d.extend(extended);

		String extstr = extended.toString();

		List<List<String>> next = new ListForwardDecorator<List<String>>(new ArrayList<>()) {
			@Override
			public boolean add(List<String> e) {
				boolean res = true;
				for (List<String> labels : extended2) {
					List<String> modified = new ArrayList<>(e);
					modified.addAll(labels);
					res &= super.add(modified);
				}
				return res;
			}
		};

		next.addAll(current);

		System.out.println(String.format("Dimension %s Extend: current: %s, myCompartments: %s, next: %s", getId(),
				current, extstr, next));

		return next;
	}

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
	 * The cached value of the '{@link #getCoreCompartment() <em>Core Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreCompartment()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper coreCompartment;

	/**
	 * The cached value of the '{@link #getDimension() <em>Dimension</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimension()
	 * @generated
	 * @ordered
	 */
	protected EList<DimensionWrapper> dimension;

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
	public CompartmentWrapper getCoreCompartment() {
		return coreCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCoreCompartment(CompartmentWrapper newCoreCompartment, NotificationChain msgs) {
		CompartmentWrapper oldCoreCompartment = coreCompartment;
		coreCompartment = newCoreCompartment;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT, oldCoreCompartment, newCoreCompartment);
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
	public void setCoreCompartment(CompartmentWrapper newCoreCompartment) {
		if (newCoreCompartment != coreCompartment) {
			NotificationChain msgs = null;
			if (coreCompartment != null)
				msgs = ((InternalEObject) coreCompartment).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT, null, msgs);
			if (newCoreCompartment != null)
				msgs = ((InternalEObject) newCoreCompartment).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT, null, msgs);
			msgs = basicSetCoreCompartment(newCoreCompartment, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT,
					newCoreCompartment, newCoreCompartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<DimensionWrapper> getDimension() {
		if (dimension == null) {
			dimension = new EObjectContainmentEList<DimensionWrapper>(DimensionWrapper.class, this,
					DimensionEpidemicPackage.DIMENSION__DIMENSION);
		}
		return dimension;
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
		case DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT:
			return basicSetCoreCompartment(null, msgs);
		case DimensionEpidemicPackage.DIMENSION__DIMENSION:
			return ((InternalEList<?>) getDimension()).basicRemove(otherEnd, msgs);
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
		case DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT:
			return getCoreCompartment();
		case DimensionEpidemicPackage.DIMENSION__DIMENSION:
			return getDimension();
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
		case DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT:
			setCoreCompartment((CompartmentWrapper) newValue);
			return;
		case DimensionEpidemicPackage.DIMENSION__DIMENSION:
			getDimension().clear();
			getDimension().addAll((Collection<? extends DimensionWrapper>) newValue);
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
		case DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT:
			setCoreCompartment((CompartmentWrapper) null);
			return;
		case DimensionEpidemicPackage.DIMENSION__DIMENSION:
			getDimension().clear();
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
		case DimensionEpidemicPackage.DIMENSION__CORE_COMPARTMENT:
			return coreCompartment != null;
		case DimensionEpidemicPackage.DIMENSION__DIMENSION:
			return dimension != null && !dimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DimensionImpl
