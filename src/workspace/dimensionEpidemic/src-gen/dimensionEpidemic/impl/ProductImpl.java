/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.Product;
import dimensionEpidemic.util.CartesianProduct;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.Flow;
import epimodel.impl.CompartmentImpl;
import epimodel.impl.EpidemicImpl;
import epimodel.impl.FlowImpl;
import epimodel.util.PhysicalCompartment;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.impl.ProductImpl#getDimensions <em>Dimensions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProductImpl extends CompartmentImpl implements Product {

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return CartesianProduct
				.cartesianProduct(getDimensions().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getPhysicalCompartments).collect(Collectors.toList()))
				.stream().map(ps -> combinePhysicalCompartmentsIntoOne(ps)).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	protected PhysicalCompartment prependSelf(PhysicalCompartment p) {
		p.labels.add(0, this.getId());
		return p;
	}

	protected PhysicalCompartment combinePhysicalCompartmentsIntoOne(List<PhysicalCompartment> toCombine) {
		return new PhysicalCompartment(
				toCombine.stream().map(p -> p.labels).flatMap(List::stream).collect(Collectors.toList()));
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return CartesianProduct
				.cartesianProduct(getDimensions().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getSources).collect(Collectors.toList()))
				.stream().map(ps -> combinePhysicalCompartmentsIntoOne(ps)).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return CartesianProduct
				.cartesianProduct(getDimensions().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getSinks).collect(Collectors.toList()))
				.stream().map(ps -> combinePhysicalCompartmentsIntoOne(ps)).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<Flow> getFlows() {
		List<Compartment> dims = getDimensions().stream().map(CompartmentWrapper::getCompartment).collect(Collectors.toList());
		List<List<Flow>> flowsByDim = dims.stream().map(Compartment::getFlows).collect(Collectors.toList());
		List<Flow> expanded = new ArrayList<>();
		for (int i = 0; i < dims.size(); ++i) {
			Compartment dimension = dims.get(i);
			List<Flow> flowsOfDim = flowsByDim.get(i);
			List<Compartment> otherDimensions = getDimensionsExceptOne(dimension);
			List<String> ids = otherDimensions.stream().map(d -> d.getId()).collect(Collectors.toList());
			List<List<PhysicalCompartment>> whatFlowsWillSee = CartesianProduct.cartesianProduct(otherDimensions
					.stream()
					.map(d -> d.getPhysicalCompartments())
					.collect(Collectors.toList()));
			List<List<PhysicalCompartment>> whatFlowsWillSeeCombined = whatFlowsWillSee
					.stream()
					.collect(Collectors.toList());

			for (Flow f : flowsOfDim) {
				for (List<PhysicalCompartment> specifications : whatFlowsWillSeeCombined) {
					expanded.add(new FlowImpl() {
						@Override
						public List<Object> getEquations(Epidemic epidemic) {
							return f.getEquations((Epidemic) new EpidemicImpl() {
								
								@Override
								public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
									return matchingSpecification(epidemic.getPhysicalFor(c));
								}

								@Override
								public List<PhysicalCompartment> getPhysicalSourcesFor(Compartment c) {
									return matchingSpecification(epidemic.getPhysicalSourcesFor(c));
								}

								@Override
								public List<PhysicalCompartment> getPhysicalSinksFor(Compartment c) {
									return matchingSpecification(epidemic.getPhysicalSinksFor(c));
								}
								
								List<PhysicalCompartment> matchingSpecification(List<PhysicalCompartment> l) {
									return l.stream().filter(pc -> {
										for (int j = 0; j < ids.size(); ++j)
											// if you match a specification        you need all of its labels
											if (pc.labels.contains(ids.get(j)) && !pc.labels.containsAll(specifications.get(j).labels))
												return false;
										// no incorrect specifications
										return true;
									}).collect(Collectors.toList());
								}
							});
						}

						@Override
						public String getEquationType() {
							return f.getEquationType();
						}
						
						@Override
						public String getId() {
							String res = f.getId();
							for (int j = 0; j < ids.size(); ++j)
								res += specifications.get(j).labels;
							return res;
						}
					});
				}	
			}
		}
		
		return expanded;
	}
	
	PhysicalCompartment uniqueLabels(PhysicalCompartment p) {
		return new PhysicalCompartment(p.labels.stream().distinct().collect(Collectors.toList()));
	}
	
	List<Integer> range(int a, int b) {
		List<Integer> res = new ArrayList<>();
		for (int i = a; i < b; ++i)
			res.add(i);
		return res;
	}
	
	protected List<Compartment> getDimensionsExceptOne(Compartment dimensionToIgnore) {
		return getDimensions()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.filter(c -> c != dimensionToIgnore)
				.collect(Collectors.toList());
	}
	
	class Pair <T,U> {
		public final T first;
		public final U second;
		
		public Pair(T t, U u) {
			first = t;
			second = u;
		}
	}
	
	abstract class PartiallyBlindedFlow extends FlowImpl {
		protected final Flow decorated;
		protected final String idSuffix;
		
		public PartiallyBlindedFlow(Flow toDecorate, String idSuffix) {
			decorated = toDecorate;
			this.idSuffix = idSuffix;
		}

		@Override
		public EClass eClass() {
			return decorated.eClass();
		}

		@Override
		public Resource eResource() {
			return decorated.eResource();
		}

		@Override
		public EObject eContainer() {
			return decorated.eContainer();
		}

		@Override
		public EStructuralFeature eContainingFeature() {
			return decorated.eContainingFeature();
		}

		@Override
		public EReference eContainmentFeature() {
			return decorated.eContainmentFeature();
		}

		@Override
		public EList<EObject> eContents() {
			return decorated.eContents();
		}

		@Override
		public TreeIterator<EObject> eAllContents() {
			return decorated.eAllContents();
		}

		@Override
		public boolean eIsProxy() {
			return decorated.eIsProxy();
		}

		@Override
		public EList<EObject> eCrossReferences() {
			return decorated.eCrossReferences();
		}

		@Override
		public Object eGet(EStructuralFeature feature) {
			return decorated.eGet(feature);
		}

		@Override
		public Object eGet(EStructuralFeature feature, boolean resolve) {
			return decorated.eGet(feature, resolve);
		}

		@Override
		public void eSet(EStructuralFeature feature, Object newValue) {
			decorated.eSet(feature, newValue);
		}

		@Override
		public boolean eIsSet(EStructuralFeature feature) {
			return decorated.eIsSet(feature);
		}

		@Override
		public void eUnset(EStructuralFeature feature) {
			decorated.eIsSet(feature);
		}

		@Override
		public Object eInvoke(EOperation operation, EList<?> arguments) throws InvocationTargetException {
			return decorated.eInvoke(operation, arguments);
		}

		@Override
		public EList<Adapter> eAdapters() {
			return decorated.eAdapters();
		}

		@Override
		public boolean eDeliver() {
			return decorated.eDeliver();
		}

		@Override
		public void eSetDeliver(boolean deliver) {
			decorated.eSetDeliver(deliver);
		}

		@Override
		public void eNotify(Notification notification) {
			decorated.eNotify(notification);
		}

		@Override
		public String getId() {
			return decorated.getId() + idSuffix;
		}

		@Override
		public void setId(String value) {
			decorated.setId(value);
		}
		
	}

	/**
	 * The cached value of the '{@link #getDimensions() <em>Dimensions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimensions()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> dimensions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DimensionEpidemicPackage.Literals.PRODUCT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CompartmentWrapper> getDimensions() {
		if (dimensions == null) {
			dimensions = new EObjectContainmentEList<CompartmentWrapper>(CompartmentWrapper.class, this,
					DimensionEpidemicPackage.PRODUCT__DIMENSIONS);
		}
		return dimensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DimensionEpidemicPackage.PRODUCT__DIMENSIONS:
			return ((InternalEList<?>) getDimensions()).basicRemove(otherEnd, msgs);
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
		case DimensionEpidemicPackage.PRODUCT__DIMENSIONS:
			return getDimensions();
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
		case DimensionEpidemicPackage.PRODUCT__DIMENSIONS:
			getDimensions().clear();
			getDimensions().addAll((Collection<? extends CompartmentWrapper>) newValue);
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
		case DimensionEpidemicPackage.PRODUCT__DIMENSIONS:
			getDimensions().clear();
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
		case DimensionEpidemicPackage.PRODUCT__DIMENSIONS:
			return dimensions != null && !dimensions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProductImpl
