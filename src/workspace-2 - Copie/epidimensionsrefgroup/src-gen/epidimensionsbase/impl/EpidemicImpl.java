/**
 */
package epidimensionsbase.impl;

import epidimensionsbase.Dimension;
import epidimensionsbase.Epidemic;
import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsbase.FlowEquationVisitor;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.json.JSONArray;
import org.json.JSONObject;

import epibase.Flow;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.impl.EpidemicImpl#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicImpl extends epibase.impl.EpidemicImpl implements Epidemic {

	JSONObject compartments;

	@Override
	public JSONObject compileCompartments() throws Exception {
		JSONObject res = new JSONObject();
		JSONArray dims = new JSONArray();

		for (Dimension dim : getDimension()) {
			JSONObject dimJSON = new JSONObject();
			dimJSON.put("size", dim.getCompartment().size());
			dimJSON.put("id", dim.getId());
			JSONArray compartments = new JSONArray();
			for (epibase.Compartment compartment : dim.getCompartment()) {
				JSONObject compartmentJSON = new JSONObject();
				compartmentJSON.put("id", compartment.getId());
				compartments.put(compartmentJSON);
			}
			dimJSON.put("compartments", compartments);
			dims.put(dimJSON);
		}

		res.put("dimensions", dims);
		compartments = res;
		return res;
	}

	@Override
	public JSONObject compileFlows() throws Exception {
		JSONObject res = new JSONObject();
		JSONArray flows = new JSONArray();
		FlowEquationVisitor v = new FlowEquationVisitor(compartments);
		for (Dimension dim : getDimension()) {
			JSONArray dimensionflows = new JSONArray();
			for (Flow f : dim.getFlow()) {
				f.accept(v);
				dimensionflows.put(v.data());
			}
			flows.put(dimensionflows);
		}
		res.put("flows_by_dimension", flows);
		return res;
	}

	/**
	 * The cached value of the '{@link #getDimension() <em>Dimension</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimension()
	 * @generated
	 * @ordered
	 */
	protected EList<Dimension> dimension;

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
	public EList<Dimension> getDimension() {
		if (dimension == null) {
			dimension = new EObjectContainmentEList<Dimension>(Dimension.class, this,
					EpidimensionsbasePackage.EPIDEMIC__DIMENSION);
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
		case EpidimensionsbasePackage.EPIDEMIC__DIMENSION:
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
		case EpidimensionsbasePackage.EPIDEMIC__DIMENSION:
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
		case EpidimensionsbasePackage.EPIDEMIC__DIMENSION:
			getDimension().clear();
			getDimension().addAll((Collection<? extends Dimension>) newValue);
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
		case EpidimensionsbasePackage.EPIDEMIC__DIMENSION:
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
		case EpidimensionsbasePackage.EPIDEMIC__DIMENSION:
			return dimension != null && !dimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EpidemicImpl
