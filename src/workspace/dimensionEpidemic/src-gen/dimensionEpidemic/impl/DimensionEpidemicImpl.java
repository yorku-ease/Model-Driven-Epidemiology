/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.Dimension;
import dimensionEpidemic.DimensionEpidemic;
import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.DimensionWrapper;
import dimensionEpidemic.util.CartesianProduct;
import dimensionEpidemic.util.DimensionPhysicalCompartment;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.EpidemicImpl;
import epimodel.util.PhysicalCompartment;

import java.lang.reflect.Method;
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
 *   <li>{@link dimensionEpidemic.impl.DimensionEpidemicImpl#getCoreCompartment <em>Core Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionEpidemicImpl extends EpidemicImpl implements DimensionEpidemic {

	List<DimensionPhysicalCompartment> physicalCompartments;
	List<Dimension> dims;

	public List<JSONObject> compile() throws JSONException {

		final List<List<String>> extended = getDimension().stream().reduce(
				this.getCoreCompartment().getCompartment().extend(new ArrayList<>()),
				(l, cmpt) -> cmpt.getDimension().extend(l),
				(l1, l2) -> l1
			);

		dims = getDimension().stream().map(DimensionWrapper::getDimension).collect(Collectors.toList());

		List<List<Compartment>> compartmentsForProduct = null;/*dims.stream().map(Dimension::getCompartment)
																.map(l -> l.stream().map(CompartmentWrapper::getCompartment).collect(Collectors.toList()))
																.collect(Collectors.toList());*/
		List<List<Compartment>> cartesianProductOfGraphs = CartesianProduct.cartesianProduct(compartmentsForProduct);
		physicalCompartments = new ArrayList<>();
		
		for (List<Compartment> arrangement : cartesianProductOfGraphs) {
			List<List<String>> physicalArrangementsNotExpanded = arrangement.stream()
					.map(Compartment::getDeclaredLabels).collect(Collectors.toList());
			List<List<String>> physicalArrangementsExpanded = CartesianProduct
					.cartesianProduct(physicalArrangementsNotExpanded);
			for (List<String> physicalArrangement : physicalArrangementsExpanded)
				physicalCompartments.add(new DimensionPhysicalCompartment(physicalArrangement));
		}

		List<List<Flow>> flowsByDimension = dims.stream().map(Dimension::getFlow)
				.map(l -> l.stream().map(FlowWrapper::getFlow).collect(Collectors.toList()))
				.collect(Collectors.toList());
		List<List<List<Object>>> physicalFlowsByDimension = flowsByDimension.stream()
				.map(lf -> lf.stream().map(f -> f.getEquations(this)).collect(Collectors.toList()))
				.collect(Collectors.toList());

		List<JSONObject> files = new ArrayList<>();

		{
			JSONObject flows = new JSONObject();
			flows.put("filename", "flows");
			JSONArray flowsByDimById = new JSONArray();
			for (int i = 0; i < dims.size(); ++i) {
				JSONArray flowsOfDimById = new JSONArray();
				List<Flow> flowsOfDim = flowsByDimension.get(i);
				List<List<Object>> physicalFlowsOfDim = physicalFlowsByDimension.get(i);
				for (int j = 0; j < flowsOfDim.size(); ++j) {
					Flow flow = flowsOfDim.get(j);
					JSONObject flowjson = new JSONObject();
					flowjson.put("id", flow.getId());
					flowjson.put("flows", physicalFlowsOfDim.get(j));
					flowsOfDimById.put(flowjson);
				}
				flowsByDimById.put(flowsOfDimById);
			}
			flows.put("flows", flowsByDimById);
			files.add(flows);
		}
		{
			JSONObject compartments = new JSONObject();
			compartments.put("filename", "compartments");
			compartments.put("compartments",
					new JSONArray(physicalCompartments.stream().map(c -> c.id).collect(Collectors.toList())));
			files.add(compartments);
		}
		{
			JSONObject flowParameters = new JSONObject();
			JSONObject initialConditions = new JSONObject();
			JSONObject susceptibility = new JSONObject();
			JSONObject contagiousness = new JSONObject();

			for (PhysicalCompartment c : physicalCompartments) {
				susceptibility.put(c.id, 0.1);
				contagiousness.put(c.id, 0.1);
				initialConditions.put(c.id, 1);
			}

			for (int i = 0; i < dims.size(); ++i) {
				List<Flow> flowsOfDim = flowsByDimension.get(i);
				List<List<Object>> physicalFlowsOfDim = physicalFlowsByDimension.get(i);
				for (int j = 0; j < flowsOfDim.size(); ++j) {
					Flow flow = flowsOfDim.get(j);
					JSONArray valuesForFlow = new JSONArray();
					int m = physicalFlowsOfDim.get(j).size();
					for (int k = 0; k < m; ++k)
						valuesForFlow.put(0.1);
					flowParameters.put(flow.getId(), valuesForFlow);
				}
			}

			JSONObject parameters = new JSONObject();
			parameters.put("flows", flowParameters);
			parameters.put("initial_conditions", initialConditions);
			parameters.put("susceptibility", susceptibility);
			parameters.put("contagiousness", contagiousness);

			JSONObject parameterswrap = new JSONObject();
			parameterswrap.put("filename", "parameters");
			parameterswrap.put("parameters", parameters);
			files.add(parameterswrap);
		}
		{
			JSONObject metadata = new JSONObject();
			metadata.put("filename", "metadata");
			metadata.put("metadata", new JSONArray(dims.stream().map(d -> {
				try {
					JSONObject dimensionMetadata = new JSONObject();
					dimensionMetadata.put("id", d.getId());
					dimensionMetadata.put("labels", new JSONArray((Object) null/*d.getCompartment().stream().map(CompartmentWrapper::getCompartment)
																				.map(Compartment::getDeclaredLabels).flatMap(List::stream)
																				.collect(Collectors.toList())*/));
					dimensionMetadata.put("size", dimensionMetadata.getJSONArray("labels").length());
					return dimensionMetadata;
				} catch (JSONException e) {
					throw new NullPointerException(e.toString());
				}
			}).collect(Collectors.toList())));
			files.add(metadata);
		}
		return files;
	}

	public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> {
			for (String label : c.getDeclaredLabels())
				if (pc.originalLabels.contains(label))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

	public List<PhysicalCompartment> getPhysicalHeadsFor(Compartment compartment) {
		if (compartment instanceof Dimension) {
			Dimension dim = (Dimension) compartment;
			List<Compartment> dimcompartments = null;/*dim.getCompartment().stream().map(CompartmentWrapper::getCompartment)
														.collect(Collectors.toList());*/
			List<Flow> dimflows = dim.getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());

			for (Flow f : dimflows)
				try {
					Method toMethod = f.getClass().getMethod("getTo");
					Compartment to = (Compartment) toMethod.invoke(f);
					dimcompartments = dimcompartments.stream().filter(c -> c != to).collect(Collectors.toList());
				} catch (Exception e) {
					// pass
				}

			return dimcompartments.stream().map(c -> getPhysicalFor(c)).flatMap(List::stream)
					.collect(Collectors.toList());
		}
		return getPhysicalFor(compartment);
	}

	public List<PhysicalCompartment> getPhysicalSinksFor(Compartment compartment) {
		if (compartment instanceof Dimension) {
			Dimension dim = (Dimension) compartment;
			List<Compartment> dimcompartments = null;/*dim.getCompartment().stream().map(CompartmentWrapper::getCompartment)
														.collect(Collectors.toList());*/
			List<Flow> dimflows = dim.getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());

			for (Flow f : dimflows)
				try {
					Method fromMethod = f.getClass().getMethod("getFrom");
					Compartment from = (Compartment) fromMethod.invoke(f);
					dimcompartments = dimcompartments.stream().filter(c -> c != from).collect(Collectors.toList());
				} catch (Exception e) {
					// pass
				}

			return dimcompartments.stream().map(c -> getPhysicalFor(c)).flatMap(List::stream)
					.collect(Collectors.toList());
		}
		return getPhysicalFor(compartment);
	}

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
	 * The cached value of the '{@link #getCoreCompartment() <em>Core Compartment</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCoreCompartment()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper coreCompartment;

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
	public EList<DimensionWrapper> getDimension() {
		if (dimension == null) {
			dimension = new EObjectContainmentEList<DimensionWrapper>(DimensionWrapper.class, this,
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
					DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT, oldCoreCompartment,
					newCoreCompartment);
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
						EOPPOSITE_FEATURE_BASE - DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT, null,
						msgs);
			if (newCoreCompartment != null)
				msgs = ((InternalEObject) newCoreCompartment).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT, null,
						msgs);
			msgs = basicSetCoreCompartment(newCoreCompartment, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT, newCoreCompartment,
					newCoreCompartment));
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT:
			return basicSetCoreCompartment(null, msgs);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT:
			return getCoreCompartment();
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
			getDimension().addAll((Collection<? extends DimensionWrapper>) newValue);
			return;
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT:
			setCoreCompartment((CompartmentWrapper) newValue);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT:
			setCoreCompartment((CompartmentWrapper) null);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__CORE_COMPARTMENT:
			return coreCompartment != null;
		}
		return super.eIsSet(featureID);
	}

} //DimensionEpidemicImpl
