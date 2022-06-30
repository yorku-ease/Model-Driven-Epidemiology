/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.DimensionEpidemic;
import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.util.CartesianProduct;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.impl.EpidemicImpl;
import epimodel.util.PhysicalCompartment;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dimension Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.impl.DimensionEpidemicImpl#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionEpidemicImpl extends EpidemicImpl implements DimensionEpidemic {

	List<PhysicalCompartment> physicalCompartments;
	List<Compartment> dims;

	public List<JSONObject> compile() throws JSONException {

		dims = getDimension().stream().map(CompartmentWrapper::getCompartment).collect(Collectors.toList());

		List<List<PhysicalCompartment>> compartmentsForProduct = dims.stream().map(Compartment::getPhysicalCompartments)
				.collect(Collectors.toList());
		List<List<PhysicalCompartment>> cartesianProductOfGraphs = CartesianProduct
				.cartesianProduct(compartmentsForProduct);
		physicalCompartments = cartesianProductOfGraphs.stream().flatMap(List::stream).collect(Collectors.toList());

		List<Flow> flows = dims.stream().map(Compartment::getFlows).flatMap(List::stream).collect(Collectors.toList());

		List<List<Object>> physicalFlows = flows.stream().map(f -> f.getEquations(this)).collect(Collectors.toList());

		List<JSONObject> files = new ArrayList<>();

		{
			JSONObject structure = new JSONObject();
			structure.put("filename", getId() + " model");
			structure.put("key", "model");
			structure.put("model", new JSONObject());
			structure.getJSONObject("model").put("compartments",
					new JSONArray(physicalCompartments.stream().map(c -> c.labels).collect(Collectors.toList())));

			JSONArray flowsById = new JSONArray();
			for (int i = 0; i < flows.size(); ++i) {
				Flow flow = flows.get(i);
				JSONObject flowjson = new JSONObject();
				flowjson.put("id", flow.getId());
				flowjson.put("type", flow.getEquationType());
				flowjson.put("flows", physicalFlows.get(i));
				flowsById.put(flowjson);
			}
			structure.getJSONObject("model").put("flows", flowsById);
			files.add(structure);

			structure.getJSONObject("model").put("metadata", new JSONArray(dims.stream().map(d -> {
				try {
					JSONObject dimensionMetadata = new JSONObject();
					dimensionMetadata.put("id", d.getId());
					dimensionMetadata.put("labels", new JSONArray(d.getDeclaredLabels()));
					dimensionMetadata.put("size", dimensionMetadata.getJSONArray("labels").length());
					return dimensionMetadata;
				} catch (JSONException e) {
					throw new NullPointerException(e.toString());
				}
			}).collect(Collectors.toList())));
		}
		{
			JSONObject flowParameters = new JSONObject();
			JSONArray compartmentParameters = new JSONArray();

			for (PhysicalCompartment c : physicalCompartments) {
				JSONObject vals = new JSONObject();
				vals.put("labels", c.labels);
				vals.put("susceptibility", 1);
				vals.put("contagiousness", 1);
				vals.put("initialConditions", 1);
				compartmentParameters.put(vals);
			}

			for (int j = 0; j < flows.size(); ++j) {
				Flow flow = flows.get(j);
				JSONArray valuesForFlow = new JSONArray();
				int m = physicalFlows.get(j).size();
				for (int k = 0; k < m; ++k)
					valuesForFlow.put(0.1);
				flowParameters.put(flow.getId(), valuesForFlow);
			}

			JSONObject parameters = new JSONObject();
			parameters.put("flows", flowParameters);
			parameters.put("compartments", compartmentParameters);

			JSONObject parameterswrap = new JSONObject();
			parameterswrap.put("filename", getId() + " parameters");
			parameterswrap.put("key", "parameters");
			parameterswrap.put("parameters", parameters);
			files.add(parameterswrap);
		}
		return files;
	}

	public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> pc.labels.contains(c.getId())).collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSourcesFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> {
			for (PhysicalCompartment filter : c.getSources())
				if (pc.labels.containsAll(filter.labels))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSinksFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> {
			for (PhysicalCompartment filter : c.getSinks())
				if (pc.labels.containsAll(filter.labels))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

	/**
	 * The cached value of the '{@link #getDimension() <em>Dimension</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimension()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> dimension;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DimensionEpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DimensionEpidemicPackage.Literals.DIMENSION_EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CompartmentWrapper> getDimension() {
		if (dimension == null) {
			dimension = new EObjectContainmentEList<CompartmentWrapper>(CompartmentWrapper.class, this,
					DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
			getDimension().clear();
			getDimension().addAll((Collection<? extends CompartmentWrapper>) newValue);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
			return dimension != null && !dimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DimensionEpidemicImpl
