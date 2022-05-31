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
import java.security.InvalidParameterException;
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
	
	List<DimensionPhysicalCompartment> physicalCompartments;
	List<Dimension> dims;
	
	public List<JSONObject> compile() throws JSONException {
		List<JSONObject> files = new ArrayList<>();
		dims = getDimension().stream().map(DimensionWrapper::getDimension).collect(Collectors.toList());
		{
			List<List<Compartment>> compartmentsForProduct = dims.stream().map(Dimension::getCompartment).map(l ->l.stream().map(CompartmentWrapper::getCompartment).collect(Collectors.toList())).collect(Collectors.toList());
			List<List<Compartment>> cartesianProductOfGraphs = CartesianProduct.cartesianProduct(compartmentsForProduct);
			physicalCompartments = cartesianProductOfGraphs.stream().map(l -> new DimensionPhysicalCompartment(l, "")).collect(Collectors.toList());
			JSONObject compartments = new JSONObject();
			compartments.put("filename", "compartments.json");
			compartments.put("compartments", new JSONArray(physicalCompartments.stream().map(c -> c.id).collect(Collectors.toList())));
			files.add(compartments);
		}
		{
			List<List<Flow>> flowsByDimension = dims.stream().map(Dimension::getFlow).map(l ->l.stream().map(FlowWrapper::getFlow).collect(Collectors.toList())).collect(Collectors.toList());
			List<Flow> allFlows = flowsByDimension.stream().flatMap(List::stream).collect(Collectors.toList());
			List<List<Object>> equations = allFlows.stream().map(f -> f.getEquations(this)).collect(Collectors.toList());
			
			JSONObject flows = new JSONObject();
			flows.put("filename", "equations.json");
			flows.put("equations", new JSONArray(equations.stream().map(Object::toString).collect(Collectors.toList())));
			files.add(flows);
		}
		return files;
	}
	
	public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> pc.originalLabels.contains(c)).collect(Collectors.toList());
	}
	
	public List<PhysicalCompartment> getPhysicalHeadsFor(Compartment compartment) {
		for (Dimension dim : dims) {
			// todo compartment.physicalHeads???
			List<Compartment> dimcompartments = dim.getCompartment().stream().map(CompartmentWrapper::getCompartment).collect(Collectors.toList());
			if (dimcompartments.contains(compartment)) {
				List<Flow> dimflows = dim.getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());
				
				for (Flow f : dimflows) {
					try {
						Method toMethod = f.getClass().getMethod("getTo");
						Compartment to = (Compartment) toMethod.invoke(f);
						dimcompartments = dimcompartments.stream().filter(c -> c != to).collect(Collectors.toList());
					} catch (Exception e) {
						// pass
					}
				}
				
				return dimcompartments.stream().map(c -> getPhysicalFor(c)).flatMap(List::stream).collect(Collectors.toList());
			}
		}
		throw new InvalidParameterException();
	}

	public List<PhysicalCompartment> getPhysicalSinksFor(Compartment compartment) {
		for (Dimension dim : dims) {
			// todo compartment.physicalHeads???
			List<Compartment> dimcompartments = dim.getCompartment().stream().map(CompartmentWrapper::getCompartment).collect(Collectors.toList());
			if (dimcompartments.contains(compartment)) {
				List<Flow> dimflows = dim.getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());
				
				for (Flow f : dimflows) {
					try {
						Method toMethod = f.getClass().getMethod("getFrom");
						Compartment from = (Compartment) toMethod.invoke(f);
						dimcompartments = dimcompartments.stream().filter(c -> c != from).collect(Collectors.toList());
					} catch (Exception e) {
						// pass
					}
				}
				
				return dimcompartments.stream().map(c -> getPhysicalFor(c)).flatMap(List::stream).collect(Collectors.toList());
			}
		}
		throw new InvalidParameterException();
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
