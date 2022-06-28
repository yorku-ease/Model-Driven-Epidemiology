/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.Product;
import dimensionEpidemic.util.CartesianProduct;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;
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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.impl.ProductImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensionEpidemic.impl.ProductImpl#getDimensions <em>Dimensions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProductImpl extends CompartmentImpl implements Product {

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return CartesianProduct.cartesianProduct(
				getDimensions()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getPhysicalCompartments)
				.collect(Collectors.toList())
			).stream()
				.map(ps -> combinePhysicalCompartmentsIntoOne(ps))
				.map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}
	
	protected PhysicalCompartment prependSelf(PhysicalCompartment p) {
		p.labels.add(0, this.getId());
		return p;
	}
	
	protected PhysicalCompartment combinePhysicalCompartmentsIntoOne(List<PhysicalCompartment> toCombine) {
		return new PhysicalCompartment(toCombine.stream().map(p -> p.labels).flatMap(List::stream).collect(Collectors.toList()));
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return CartesianProduct.cartesianProduct(
				getDimensions()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getSources)
				.collect(Collectors.toList())
			)
				.stream()
				.map(ps -> combinePhysicalCompartmentsIntoOne(ps))
				.map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return CartesianProduct.cartesianProduct(
				getDimensions()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getSinks)
				.collect(Collectors.toList())
			)
				.stream()
				.map(ps -> combinePhysicalCompartmentsIntoOne(ps))
				.map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<Flow> getFlows() {
		return new ArrayList<>();
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
	public EList<FlowWrapper> getFlow() {
		if (flow == null) {
			flow = new EObjectContainmentEList<FlowWrapper>(FlowWrapper.class, this,
					DimensionEpidemicPackage.PRODUCT__FLOW);
		}
		return flow;
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
		case DimensionEpidemicPackage.PRODUCT__FLOW:
			return ((InternalEList<?>) getFlow()).basicRemove(otherEnd, msgs);
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
		case DimensionEpidemicPackage.PRODUCT__FLOW:
			return getFlow();
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
		case DimensionEpidemicPackage.PRODUCT__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
			return;
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
		case DimensionEpidemicPackage.PRODUCT__FLOW:
			getFlow().clear();
			return;
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
		case DimensionEpidemicPackage.PRODUCT__FLOW:
			return flow != null && !flow.isEmpty();
		case DimensionEpidemicPackage.PRODUCT__DIMENSIONS:
			return dimensions != null && !dimensions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProductImpl
