/**
 */
package product.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	public void edit(Shell shell, List<Control> controls) {
		shell.setText("Edit Product " + getLabel());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Labels", () -> {
			controls.forEach(Control::dispose);
			controls.clear();
			super.edit(shell, controls); // labels window
			shell.pack(true);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Modify compartments", () -> {
			editCompartments(this, shell, controls);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Modify compartments", () -> {
			editFlows(this, shell, controls);
		});
	}

	void editCompartments(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(Control::dispose);
		controls.clear();
		shell.setLayout(new GridLayout(2, false));

		for (CompartmentWrapper w : getCompartment()) {
			Compartment e = w.getCompartment();
			epimodel.util.Edit.addText(shell, controls, e.getLabel().toString());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getLabel(), () -> {
				controls.forEach(Control::dispose);
				controls.clear();
				epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getLabel());
				epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
					epimodel.util.Edit.transact(dom, () -> getCompartment().remove(e.eContainer()));
					shell.close();
				});
				shell.pack(true);
			});
		}
		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Dimension",
			() -> epimodel.util.Edit.addCompartmentWindow(
				dom,
				shell,
				controls,
				w -> getCompartment().add(w)
			)
		);
		shell.pack(true);
	}

	void editFlows(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(Control::dispose);
		controls.clear();
		shell.setLayout(new GridLayout(2, false));
		List<Flow> l = getFlow().stream().map(FlowWrapper::getFlow).toList();

		for (Flow e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getId().toString());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getId(), () -> {
				controls.forEach(Control::dispose);
				controls.clear();
				epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getId());
				epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
					epimodel.util.Edit.transact(dom, () -> getCompartment().remove(e.eContainer()));
					shell.close();
				});
				shell.pack(true);
			});
		}

		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Flow",
			() -> epimodel.util.Edit.addFlowWindow(
				this,
				shell,
				controls,
				w -> getFlow().add(w)
			)
		);
		shell.pack(true);
	}

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return CartesianProduct.cartesianProduct(
					getCompartment()
						.stream()
						.map(CompartmentWrapper::getCompartment)
						.map(Compartment::getPhysicalCompartments)
						.toList()
				)
				.stream()
				.map(ps -> combinePhysicalCompartmentsIntoOne(ps))
				.map(p -> prependSelf(p))
				.toList();
	}

	public PhysicalCompartment prependSelf(PhysicalCompartment p) {
		return prependLabelsToPC(p, getLabel());
	}

	public PhysicalCompartment prependLabelsToPC(PhysicalCompartment p, List<String> labels) {
		PhysicalCompartment p2 = new PhysicalCompartment(new ArrayList<>(p.labels));
		p2.labels.addAll(0, labels);
		return p2;
	}

	public static PhysicalCompartment combinePhysicalCompartmentsIntoOne(List<PhysicalCompartment> toCombine) {
		return new PhysicalCompartment(
			toCombine
				.stream()
				.map(p -> p.labels)
				.flatMap(List::stream)
				.toList()
		);
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return CartesianProduct.cartesianProduct(
				getCompartment()
					.stream()
					.map(CompartmentWrapper::getCompartment)
					.map(Compartment::getSources)
					.toList()
				)
				.stream()
				.map(ProductImpl::combinePhysicalCompartmentsIntoOne)
				.map(p -> prependSelf(p))
				.toList();
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return CartesianProduct.cartesianProduct(
				getCompartment()
					.stream()
					.map(CompartmentWrapper::getCompartment)
					.map(Compartment::getSinks)
					.toList()
				)
				.stream()
				.map(ProductImpl::combinePhysicalCompartmentsIntoOne)
				.map(p -> prependSelf(p))
				.toList();
	}
	
	@Override
	public List<PhysicalFlow> getEquations() {
		List<PhysicalFlow> flowsDefinedInChildren = new ArrayList<>(getProductOfFlowsDefinedInChildrenExpandedAlongOtherDimensions());
		List<PhysicalFlow> flowsDefinedHere = new ArrayList<>(getProductOfFlowsDefinedHereBelongingToOtherDimensionsResolved());

		flowsDefinedInChildren.addAll(flowsDefinedHere);
		return flowsDefinedInChildren
				.stream()
				.map(eq -> prependSelf(eq))
				.toList();
	}
	
	public List<PhysicalFlow> getProductOfFlowsDefinedInChildrenExpandedAlongOtherDimensions() {
		
		List<PhysicalFlow> res = new ArrayList<>();
		
		for (int dimensionIndex = 0; dimensionIndex < compartment.size(); ++dimensionIndex) {
			Compartment dimension = getCompartment().get(dimensionIndex).getCompartment();
			List<PhysicalFlow> dimensionFlows = dimension.getEquations();
			int dim = dimensionIndex;
			res.addAll(dimensionFlows.stream().map(eq -> expandFlowAsPartOfDimensionByIndex(eq, dim)).flatMap(List::stream).toList());
		}
		
		return res;
	}
	
	// based on dimension of flow index produces
	// a list of each other dimension compartments
	// to product with
	public List<PhysicalFlow> expandFlowAsPartOfDimensionByIndex(PhysicalFlow eq, int dimensionIndex) {
		List<List<PhysicalCompartment>> compartmentsOfOtherDimensions = new ArrayList<>();
		
		for (int otherDimensionIndex = 0; otherDimensionIndex < compartment.size(); ++otherDimensionIndex)
			if (dimensionIndex != otherDimensionIndex)
				compartmentsOfOtherDimensions.add(getCompartment().get(otherDimensionIndex).getCompartment().getPhysicalCompartments());
		
		return expand(eq, compartmentsOfOtherDimensions);
	}
	
	public List<PhysicalCompartment> expandPCByDimensionIndex(PhysicalCompartment pc, int dimensionIndex) {
		List<List<PhysicalCompartment>> compartmentsOfOtherDimensions = new ArrayList<>();
		
		for (int otherDimensionIndex = 0; otherDimensionIndex < compartment.size(); ++otherDimensionIndex)
			if (dimensionIndex != otherDimensionIndex)
				compartmentsOfOtherDimensions.add(getCompartment().get(otherDimensionIndex).getCompartment().getPhysicalCompartments());
		
		return expand(pc, compartmentsOfOtherDimensions).stream().map(p->prependSelf(p)).toList();
	}
	
	public List<PhysicalFlow> getProductOfFlowsDefinedHereBelongingToOtherDimensionsResolved()  {
		List<PhysicalFlow> flowsDefinedHere = getFlow()
				.stream()
				.map(FlowWrapper::getFlow)
				.map(Flow::getEquations)
				.flatMap(List::stream)
				.toList();
		
		List<List<PhysicalCompartment>> compartmentsOfEachDimension = getCompartment()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getPhysicalCompartments)
				.toList();
		
		return flowsDefinedHere
			.stream()
			.map(eq -> {
				int dim = getPartOfWhichDimIndex(eq, compartmentsOfEachDimension);
				return replicateEquationToMatchPCs(eq, getCompartment().get(dim).getCompartment())
						.stream()
						.map(e -> expandFlowAsPartOfDimensionByIndex(e, dim))
						.flatMap(List::stream)
						.toList();
			})
			.flatMap(List::stream)
			.toList();
	}
	
	public int getPartOfWhichDimIndex(PhysicalFlow equationDefinedHere, List<List<PhysicalCompartment>> compartmentsOfEachDimension) {
		for (int i = 0; i < getCompartment().size(); ++i)
			for (PhysicalCompartment pc : compartmentsOfEachDimension.get(i)) {
				for (String label : equationDefinedHere.source.labels)
					if (pc.labels.contains(label))
						return i;
				for (String label : equationDefinedHere.sink.labels)
					if (pc.labels.contains(label))
						return i;
			}
		throw new RuntimeException("Failed to find which child the flow should be applied to");
	}
	
//	// tested
//	public List<PhysicalFlow> expand(PhysicalFlow eq, List<List<PhysicalCompartment>> compartmentsOfEachDimension) {
//		
//		int n = eq.equationCompartments.size();
//		
//		if (n == 0 || compartmentsOfEachDimension.size() == 0)
//			return new ArrayList<>(Arrays.asList(eq));
//		
//		List<List<PhysicalCompartment>> specializations = CartesianProduct.cartesianProduct(compartmentsOfEachDimension);
//		List<List<List<PhysicalCompartment>>> specializationsForEachEquationCompartment = 
//				CartesianProduct.selfCartesianProduct(specializations, n);
//		
//		List<PhysicalFlow> expanded = new ArrayList<>();
//		
//		for (List<List<PhysicalCompartment>> spec : specializationsForEachEquationCompartment) {
//			List<String> flatSpec = spec.stream().flatMap(List::stream).map(pc -> pc.labels).flatMap(List::stream).toList();
//			for (int i = 0; i < n; ++i) {
//				PhysicalFlow temp = eq.deepCopy();
//				
//				PhysicalCompartment pc = temp.equationCompartments.get(i);
//				if (temp.source.equals(pc) || temp.sink.equals(pc)) {
//					temp.source.labels.addAll(flatSpec);
//					temp.sink.labels.addAll(flatSpec);
//				}
//				temp.equationCompartments.set(i, prependLabelsToPC(temp.equationCompartments.get(i), flatSpec));
//				expanded.add(temp);
//			}
//		}
//		
//		return expanded;
//	}
	
	public List<PhysicalFlow> expand(PhysicalFlow eq, List<List<PhysicalCompartment>> compartmentsOfEachDimension) {
		
		List<List<PhysicalCompartment>> specializations = CartesianProduct.cartesianProduct(compartmentsOfEachDimension);
		
		List<PhysicalFlow> expanded = new ArrayList<>();
		
		for (List<PhysicalCompartment> spec : specializations) {
			PhysicalFlow neq = eq.deepCopy();
			for (PhysicalCompartment spec_pc : spec) {
				neq.source.labels.addAll(spec_pc.labels);
				neq.sink.labels.addAll(spec_pc.labels);
			}
			expanded.add(neq);
		}
		return expanded;
	}
	
	// tested
	public List<PhysicalCompartment> expand(PhysicalCompartment pc, List<List<PhysicalCompartment>> compartmentsOfOtherDimensions) {
		List<List<PhysicalCompartment>> product = CartesianProduct.cartesianProduct(compartmentsOfOtherDimensions);
		
		return product
				.stream()
				.map(arrangement -> new PhysicalCompartment(
					arrangement.stream().map(a -> a.labels).flatMap(List::stream).toList()
				))
				.map(p -> prependLabelsToPC(p, pc.labels))
				.toList();
	}
	
	public PhysicalFlow prependSelf(PhysicalFlow eq) {
		return prepend(eq, getLabel());
	}
	
	public PhysicalFlow prepend(PhysicalFlow eq, List<String> labels) {
		eq.source.labels.addAll(0, labels);
		eq.sink.labels.addAll(0, labels);
		return eq;
	}

	@Override
	public List<PhysicalCompartment> getSourcesFor(PhysicalCompartment child) {
		int n = getCompartment().size();
		for  (int i = 0; i < n; ++i) {
			int dim = i;
			Compartment c = getCompartment().get(i).getCompartment();
			if (c.getLabels().equals(child.labels))
				return c.getSources()
						.stream()
						.map(pc->expandPCByDimensionIndex(pc, dim))
						.flatMap(List::stream).toList();
			for (PhysicalCompartment pc : c.getPhysicalCompartments())
				if (pc.labels.containsAll(child.labels))
					return c.getSourcesFor(child)
							.stream()
							.map(pc2->expandPCByDimensionIndex(pc2, dim))
							.flatMap(List::stream)
							.toList();
		}
		throw new RuntimeException("Product has no child matching " + child);
	}

	@Override
	public List<PhysicalCompartment> getSinksFor(PhysicalCompartment child) {
		int n = getCompartment().size();
		for  (int i = 0; i < n; ++i) {
			int dim = i;
			Compartment c = getCompartment().get(i).getCompartment();
			if (c.getLabels().equals(child.labels))
				return c.getSinks().stream().map(pc->expandPCByDimensionIndex(pc, dim)).flatMap(List::stream).toList();
			for (PhysicalCompartment pc : c.getPhysicalCompartments())
				if (pc.labels.containsAll(child.labels))
					return c.getSinksFor(child).stream().map(pc2->expandPCByDimensionIndex(pc2, dim)).flatMap(List::stream).toList();
		}
		throw new RuntimeException("Product has no child matching " + child);
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
	public ProductImpl() {
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
