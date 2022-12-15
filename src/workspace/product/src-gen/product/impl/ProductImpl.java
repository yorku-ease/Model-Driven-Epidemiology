/**
 */
package product.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import product.util.CartesianProduct;
import product.Product;
import product.ProductPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link product.impl.ProductImpl#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link product.impl.ProductImpl#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProductImpl extends CompartmentImpl implements Product {
	
	@Override
	public void edit(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Edit Product " + getLabel());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Labels", () -> {
			controls.forEach(Control::dispose);
			controls.clear();
			super.edit(dom, shell, controls); // labels window
			shell.pack(true);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Modify compartments", () -> {
			editCompartments(dom, shell, controls);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Modify compartments", () -> {
			editFlows(dom, shell, controls);
		});
	}

	void editCompartments(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(Control::dispose);
		controls.clear();
		shell.setLayout(new GridLayout(2, false));
		List<Compartment> l = getCompartment().stream().map(CompartmentWrapper::getCompartment)
				.collect(Collectors.toList());

		for (Compartment e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getLabel().toString());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getLabel(), () -> {
				epimodel.util.Edit.transact(dom, () -> {
					controls.forEach(Control::dispose);
					controls.clear();
					epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getLabel());
					epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
						epimodel.util.Edit.transact(dom, () -> getCompartment().remove(e.eContainer()));
						shell.close();
					});
					shell.pack(true);
				});
			});
		}
		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Dimension", () -> epimodel.util.Edit.addCompartmentWindow(dom,
				shell, controls, w -> epimodel.util.Edit.transact(dom, () -> getCompartment().add(w))));
		shell.pack(true);
	}

	void editFlows(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(Control::dispose);
		controls.clear();
		shell.setLayout(new GridLayout(2, false));
		List<Flow> l = getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());

		for (Flow e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getId().toString());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getId(), () -> {
				epimodel.util.Edit.transact(dom, () -> {
					controls.forEach(Control::dispose);
					controls.clear();
					epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getId());
					epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
						epimodel.util.Edit.transact(dom, () -> getCompartment().remove(e.eContainer()));
						shell.close();
					});
					shell.pack(true);
				});
			});
		}

		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Flow", () -> epimodel.util.Edit.addFlowWindow(shell, controls,
				w -> epimodel.util.Edit.transact(dom, () -> getFlow().add(w))));
		shell.pack(true);
	}

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return CartesianProduct
				.cartesianProduct(getCompartment().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getPhysicalCompartments).collect(Collectors.toList()))
				.stream().map(ps -> combinePhysicalCompartmentsIntoOne(ps)).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	protected PhysicalCompartment prependSelf(PhysicalCompartment p) {
		PhysicalCompartment p2 = new PhysicalCompartment(new ArrayList<>(p.labels));
		p2.labels.addAll(0, getLabel());
		return p2;
	}

	protected static PhysicalCompartment combinePhysicalCompartmentsIntoOne(List<PhysicalCompartment> toCombine) {
		return new PhysicalCompartment(
				toCombine.stream().map(p -> p.labels).flatMap(List::stream).collect(Collectors.toList()));
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return CartesianProduct
				.cartesianProduct(getCompartment().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getSources).collect(Collectors.toList()))
				.stream().map(ProductImpl::combinePhysicalCompartmentsIntoOne).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return CartesianProduct
				.cartesianProduct(getCompartment().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getSinks).collect(Collectors.toList()))
				.stream().map(ProductImpl::combinePhysicalCompartmentsIntoOne).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<PhysicalFlow> getPhysicalFlows() {
		return new ArrayList<>();
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
		return ProductPackage.Literals.PRODUCT;
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
					ProductPackage.PRODUCT__COMPARTMENT);
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
			flow = new EObjectContainmentEList<FlowWrapper>(FlowWrapper.class, this, ProductPackage.PRODUCT__FLOW);
		}
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ProductPackage.PRODUCT__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
		case ProductPackage.PRODUCT__FLOW:
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
		case ProductPackage.PRODUCT__COMPARTMENT:
			return getCompartment();
		case ProductPackage.PRODUCT__FLOW:
			return getFlow();
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
		case ProductPackage.PRODUCT__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends CompartmentWrapper>) newValue);
			return;
		case ProductPackage.PRODUCT__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
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
		case ProductPackage.PRODUCT__COMPARTMENT:
			getCompartment().clear();
			return;
		case ProductPackage.PRODUCT__FLOW:
			getFlow().clear();
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
		case ProductPackage.PRODUCT__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		case ProductPackage.PRODUCT__FLOW:
			return flow != null && !flow.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProductImpl
