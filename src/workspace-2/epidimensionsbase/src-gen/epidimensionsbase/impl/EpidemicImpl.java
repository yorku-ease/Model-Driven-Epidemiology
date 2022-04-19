/**
 */
package epidimensionsbase.impl;

import epibase.Compartment;
import epibase.Flow;
import epidimensionsbase.Epidemic;
import epidimensionsbase.EpidimensionsbasePackage;
import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.json.JSONObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.impl.EpidemicImpl#getFlows <em>Flows</em>}</li>
 *   <li>{@link epidimensionsbase.impl.EpidemicImpl#getCompartments <em>Compartments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicImpl extends epibase.impl.EpidemicImpl implements Epidemic {
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
	protected EList<Compartment> compartments;

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
		return EpidimensionsbasePackage.Literals.EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Flow> getFlows() {
		if (flows == null) {
			flows = new EObjectContainmentEList<Flow>(Flow.class, this, EpidimensionsbasePackage.EPIDEMIC__FLOWS);
		}
		return flows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Compartment> getCompartments() {
		if (compartments == null) {
			compartments = new EObjectContainmentEList<Compartment>(Compartment.class, this,
					EpidimensionsbasePackage.EPIDEMIC__COMPARTMENTS);
		}
		return compartments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EpidimensionsbasePackage.EPIDEMIC__FLOWS:
			return ((InternalEList<?>) getFlows()).basicRemove(otherEnd, msgs);
		case EpidimensionsbasePackage.EPIDEMIC__COMPARTMENTS:
			return ((InternalEList<?>) getCompartments()).basicRemove(otherEnd, msgs);
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
		case EpidimensionsbasePackage.EPIDEMIC__FLOWS:
			return getFlows();
		case EpidimensionsbasePackage.EPIDEMIC__COMPARTMENTS:
			return getCompartments();
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
		case EpidimensionsbasePackage.EPIDEMIC__FLOWS:
			getFlows().clear();
			getFlows().addAll((Collection<? extends Flow>) newValue);
			return;
		case EpidimensionsbasePackage.EPIDEMIC__COMPARTMENTS:
			getCompartments().clear();
			getCompartments().addAll((Collection<? extends Compartment>) newValue);
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
		case EpidimensionsbasePackage.EPIDEMIC__FLOWS:
			getFlows().clear();
			return;
		case EpidimensionsbasePackage.EPIDEMIC__COMPARTMENTS:
			getCompartments().clear();
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
		case EpidimensionsbasePackage.EPIDEMIC__FLOWS:
			return flows != null && !flows.isEmpty();
		case EpidimensionsbasePackage.EPIDEMIC__COMPARTMENTS:
			return compartments != null && !compartments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public JSONObject compileCompartments() throws Exception {
		JSONObject res = new JSONObject();
		//		JSONArray dims = new JSONArray();
		//		
		//		for (epibase.Compartment :)
		//
		//		for (epidimensionsbase.Dimension dim : getDimension()) {
		//			JSONObject dimJSON = new JSONObject();
		//			dimJSON.put("size", dim.getCompartment().size());
		//			dimJSON.put("id", dim.getId());
		//			JSONArray compartments = new JSONArray();
		//			for (epibase.Compartment compartment : dim.getCompartment()) {
		//				JSONObject compartmentJSON = new JSONObject();
		//				compartmentJSON.put("id", compartment.getId());
		//				compartments.put(compartmentJSON);
		//			}
		//			dimJSON.put("compartments", compartments);
		//			dims.put(dimJSON);
		//		}
		//
		//		res.put("dimensions", dims);
		//		compartments = res;
		return res;
	}

	@Override
	public JSONObject compileFlows() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

} //EpidemicImpl
