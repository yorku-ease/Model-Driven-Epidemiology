/**
 */
package dimensions.impl;

import dimensions.Contact;
import dimensions.Dimension;
import dimensions.DimensionsPackage;
import dimensions.FromToFlow;
import epibase.Compartment;
import epibase.Epidemic;
import epibase.Flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensions.impl.EpidemicImpl#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link dimensions.impl.EpidemicImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensions.impl.EpidemicImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicImpl extends MinimalEObjectImpl.Container implements Epidemic {

	@Override
	public JSONObject compile() throws Exception {
		JSONObject compartments = compileCompartments();
		JSONObject flows = compileFlows();
		JSONObject result = new JSONObject();
		result.put("compartments", compartments);
		result.put("flows", flows);
		result.put("filename", getId() + ".json");
		return result;
	}

	@Override
	public JSONObject compileCompartments() throws Exception {
		JSONObject res = new JSONObject();
		JSONArray dims = new JSONArray();

		for (Compartment c : getCompartment()) {
			List<Compartment> l = expand(c);
			JSONObject dimJSON = new JSONObject();
			dimJSON.put("size", l.size());
			dimJSON.put("id", c.getId());
			JSONArray compartments = new JSONArray();
			for (Compartment compartment : l) {
				JSONObject compartmentJSON = new JSONObject();
				compartmentJSON.put("id", compartment.getId());
				compartments.put(compartmentJSON);
			}
			dimJSON.put("compartments", compartments);
			dims.put(dimJSON);
		}

		res.put("dimensions", dims);
		return res;
	}

	@Override
	public JSONObject compileFlows() throws Exception {
		JSONObject res = new JSONObject();
		JSONArray flows = new JSONArray();
		for (Compartment c : getCompartment()) {
			JSONArray dimensionflows = new JSONArray();
			if (c instanceof Dimension)
				for (Flow f : getListOfExpandedFlowsRecursivelyForDimension((Dimension) c)) {
					JSONObject flowJSON = new JSONObject();
					flowJSON.put("type", f.getClass().getTypeName());
					if (f instanceof FromToFlow) {
						if (f instanceof Contact)
							flowJSON.put("contact", ((Contact) f).getContact().getId());
						flowJSON.put("from", ((FromToFlow) f).getFrom().getId());
						flowJSON.put("to", ((FromToFlow) f).getTo().getId());
					}
					dimensionflows.put(flowJSON);
				}
			flows.put(dimensionflows);
		}
		res.put("flowsByDimension", flows);
		return res;
	}

	protected List<Compartment> expand(Compartment c) {
		if (c instanceof Dimension)
			return getListOfCompartmentsRecursivelyForDimension((Dimension) c);
		else
			return Arrays.asList(c);
	}

	protected List<Compartment> getListOfCompartmentsRecursivelyForDimension(Dimension d) {
		List<Compartment> l = new ArrayList<>();
		for (Compartment c : d.getCompartment())
			if (c instanceof Dimension)
				l.addAll(getListOfCompartmentsRecursivelyForDimension((Dimension) c));
			else
				l.add(c);
		if (l.size() == 0)
			throw new NullPointerException(); // empty dimension would lead to unexpected uploading behavior
		return l;
	}

	protected List<Flow> getListOfExpandedFlowsRecursivelyForDimension(Dimension d) {
		return expandDimensionFlows(getListOfNotExpandedFlowsRecursivelyForDimension(d));
	}

	protected List<Flow> getListOfNotExpandedFlowsRecursivelyForDimension(Dimension d) {
		List<Flow> fl = new ArrayList<>();
		for (Flow f : d.getFlow())
			fl.add(f);
		for (Compartment c : d.getCompartment())
			if (c instanceof Dimension)
				fl.addAll(getListOfNotExpandedFlowsRecursivelyForDimension((Dimension) c));
		return fl;
	}

	protected List<Flow> expandDimensionFlows(List<Flow> fl) {
		List<Flow> expanded = new ArrayList<>();
		for (Flow f : fl) {
			if (f instanceof FromToFlow) {
				List<Compartment> from = getExpandedFromList((FromToFlow) f);
				List<Compartment> to = getExpandedToList((FromToFlow) f);
				if (f instanceof Contact) {
					List<Compartment> contact = getExpandedContactList((Contact) f);
					for (Compartment c1 : from)
						for (Compartment c2 : to)
							for (Compartment c3 : contact) {
								Contact cp = EcoreUtil.copy((Contact) f);
								cp.setFrom(c1);
								cp.setTo(c2);
								cp.setContact(c3);
								expanded.add(cp);
							}
				} else {
					for (Compartment c1 : from)
						for (Compartment c2 : to) {
							FromToFlow cp = EcoreUtil.copy((FromToFlow) f);
							cp.setFrom(c1);
							cp.setTo(c2);
							expanded.add(cp);
						}
				}
			}
		}
		return expanded;
	}

	protected List<Compartment> getExpandedFromList(FromToFlow f) {
		return getExpandedList(f, new Function<FromToFlow, Compartment>() {
			@Override
			public Compartment apply(FromToFlow f) {
				return f.getFrom();
			}
		});
	}

	protected List<Compartment> getExpandedToList(FromToFlow f) {
		return getExpandedList(f, new Function<FromToFlow, Compartment>() {
			@Override
			public Compartment apply(FromToFlow f) {
				return f.getTo();
			}
		});
	}

	protected List<Compartment> getExpandedContactList(Contact f) {
		return expand(f.getContact());
	}

	protected List<Compartment> getExpandedList(FromToFlow f, Function<FromToFlow, Compartment> axis) {
		if (!(axis.apply(f) instanceof Dimension))
			return Arrays.asList(axis.apply(f));
		else {
			Dimension d = (Dimension) axis.apply(f);
			List<Compartment> cl = getListOfCompartmentsRecursivelyForDimension(d);
			List<Flow> fl = getListOfExpandedFlowsRecursivelyForDimension(d);

			List<Compartment> l = new ArrayList<>();

			for (Compartment c : cl) {
				boolean ok = true;
				for (Flow _f : fl) {
					if (_f instanceof FromToFlow && axis.apply((FromToFlow) _f).equals(c)) {
						ok = false;
						break;
					}
				}
				if (ok)
					l.add(c);
			}

			return l;
		}
	}

	/**
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected EList<Compartment> compartment;

	/**
	 * The cached value of the '{@link #getFlow() <em>Flow</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlow()
	 * @generated
	 * @ordered
	 */
	protected EList<Flow> flow;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DimensionsPackage.Literals.EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Compartment> getCompartment() {
		if (compartment == null) {
			compartment = new EObjectContainmentEList<Compartment>(Compartment.class, this,
					DimensionsPackage.EPIDEMIC__COMPARTMENT);
		}
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Flow> getFlow() {
		if (flow == null) {
			flow = new EObjectContainmentEList<Flow>(Flow.class, this, DimensionsPackage.EPIDEMIC__FLOW);
		}
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DimensionsPackage.EPIDEMIC__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DimensionsPackage.EPIDEMIC__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
		case DimensionsPackage.EPIDEMIC__FLOW:
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
		case DimensionsPackage.EPIDEMIC__COMPARTMENT:
			return getCompartment();
		case DimensionsPackage.EPIDEMIC__FLOW:
			return getFlow();
		case DimensionsPackage.EPIDEMIC__ID:
			return getId();
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
		case DimensionsPackage.EPIDEMIC__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends Compartment>) newValue);
			return;
		case DimensionsPackage.EPIDEMIC__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends Flow>) newValue);
			return;
		case DimensionsPackage.EPIDEMIC__ID:
			setId((String) newValue);
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
		case DimensionsPackage.EPIDEMIC__COMPARTMENT:
			getCompartment().clear();
			return;
		case DimensionsPackage.EPIDEMIC__FLOW:
			getFlow().clear();
			return;
		case DimensionsPackage.EPIDEMIC__ID:
			setId(ID_EDEFAULT);
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
		case DimensionsPackage.EPIDEMIC__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		case DimensionsPackage.EPIDEMIC__FLOW:
			return flow != null && !flow.isEmpty();
		case DimensionsPackage.EPIDEMIC__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //EpidemicImpl
