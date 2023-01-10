/**
 */
package product.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;
import epimodel.util.FlowEquation;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
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
		return CartesianProduct.cartesianProduct(
					getCompartment()
						.stream()
						.map(CompartmentWrapper::getCompartment)
						.map(Compartment::getPhysicalCompartments)
						.collect(Collectors.toList())
				)
				.stream()
				.map(ps -> combinePhysicalCompartmentsIntoOne(ps))
				.map(p -> prependSelf(p))
				.collect(Collectors.toList());
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
				.collect(Collectors.toList())
		);
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return CartesianProduct.cartesianProduct(
				getCompartment()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getSources)
				.collect(Collectors.toList()))
				.stream()
				.map(ProductImpl::combinePhysicalCompartmentsIntoOne)
				.map(p -> prependSelf(p))
				.collect(Collectors.toList()
		);
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return CartesianProduct.cartesianProduct(
				getCompartment()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getSinks)
				.collect(Collectors.toList()))
				.stream()
				.map(ProductImpl::combinePhysicalCompartmentsIntoOne)
				.map(p -> prependSelf(p))
				.collect(Collectors.toList()
		);
	}
	
	@Override
	public List<FlowEquation> getPhysicalFlows() {
		List<FlowEquation> flowsDefinedInChildren = getProductOfFlowsDefinedInChildrenExpandedAlongOtherDimensions();
//		List<FlowEquation> flowsDefinedHere = getProductOfFlowsDefinedHereExpandedAlongAllDimensions();
		List<FlowEquation> flowsDefinedHere = getProductOfFlowsDefinedHereBelongingToOtherDimensionsResolved();
		
		flowsDefinedInChildren.addAll(flowsDefinedHere);
		return flowsDefinedInChildren
				.stream()
				.map(eq -> prependSelf(eq))
				.collect(Collectors.toList());
	}
	
	public List<FlowEquation> getProductOfFlowsDefinedInChildrenExpandedAlongOtherDimensions() {
		
		List<FlowEquation> res = new ArrayList<>();
		
		for (int dimensionIndex = 0; dimensionIndex < compartment.size(); ++dimensionIndex) {
			Compartment dimension = getCompartment().get(dimensionIndex).getCompartment();
			List<FlowEquation> dimensionFlows = dimension.getPhysicalFlows();
			int dim = dimensionIndex;
			res.addAll(dimensionFlows.stream().map(eq -> expandFlowAsPartOfDimensionByIndex(eq, dim)).flatMap(List::stream).collect(Collectors.toList()));
		}
		
		return res;
	}
	
	public List<FlowEquation> expandFlowAsPartOfDimensionByIndex(FlowEquation eq, int dimensionIndex) {
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
		
		return expand(pc, compartmentsOfOtherDimensions).stream().map(p->prependSelf(p)).collect(Collectors.toList());
	}
	
	public List<FlowEquation> getProductOfFlowsDefinedHereBelongingToOtherDimensionsResolved()  {
		List<FlowEquation> flowsDefinedHere = getFlow()
				.stream()
				.map(FlowWrapper::getFlow)
				.map(Flow::getEquations)
				.flatMap(List::stream)
				.collect(Collectors.toList());
		
		List<List<PhysicalCompartment>> compartmentsOfEachDimension = getCompartment()
				.stream()
				.map(CompartmentWrapper::getCompartment)
				.map(Compartment::getPhysicalCompartments)
				.collect(Collectors.toList());
		
		return flowsDefinedHere
			.stream()
			.map(eq -> {
				int dim = getPartOfWhichDimIndex(eq, compartmentsOfEachDimension);
				return replicateEquationToMatchPCs(eq, getCompartment().get(dim).getCompartment())
						.stream()
						.map(e -> expandFlowAsPartOfDimensionByIndex(e, dim))
						.flatMap(List::stream)
						.collect(Collectors.toList());
			})
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}
	
	// create one copy per association of sink and source in the compartment
	public List<FlowEquation> replicateEquationToMatchPCs(FlowEquation eq, Compartment dim) {
		return CartesianProduct.cartesianProduct(Arrays.asList(
			dim.getSinksFor(eq.source), // the source of the flow is a sink
			dim.getSourcesFor(eq.sink)  // and the sink of the flow is a source
		)).stream().map(sources_sinks -> {
			FlowEquation cp = eq.deepCopy();
			cp.source.labels.addAll(
					sources_sinks
						.get(0)
						.labels
						.stream()
						.filter(label -> !cp.source.labels.contains(label))
						.collect(Collectors.toList()));
			cp.sink.labels.addAll(
					sources_sinks
						.get(1)
						.labels
						.stream()
						.filter(label -> !cp.sink.labels.contains(label))
						.collect(Collectors.toList()));
			return cp;
		}).collect(Collectors.toList());
	}
	
	public int getPartOfWhichDimIndex(FlowEquation equationDefinedHere, List<List<PhysicalCompartment>> compartmentsOfEachDimension) {
		for (int i = 0; i < getCompartment().size(); ++i)
			for (List<PhysicalCompartment> pcl : compartmentsOfEachDimension)
				for (PhysicalCompartment pc : pcl) {
					for (String label : equationDefinedHere.source.labels)
						if (pc.labels.contains(label))
							return i;
					for (String label : equationDefinedHere.sink.labels)
						if (pc.labels.contains(label))
							return i;
				}
		throw new RuntimeException("Failed to find which child the flow should be applied to");
	}
	
	// tested
	public List<FlowEquation> expand(FlowEquation eq, List<List<PhysicalCompartment>> compartmentsOfEachDimension) {
		
		int n = eq.equationCompartments.size();
		
		if (n == 0 || compartmentsOfEachDimension.size() == 0)
			return new ArrayList<>(Arrays.asList(eq));
		
		List<List<PhysicalCompartment>> specializations = CartesianProduct.cartesianProduct(compartmentsOfEachDimension);
		List<List<List<PhysicalCompartment>>> specializationsForEachEquationCompartment = 
				CartesianProduct.selfCartesianProduct(specializations, n);
		
		List<FlowEquation> expanded = new ArrayList<>();
		
		for (List<List<PhysicalCompartment>> spec : specializationsForEachEquationCompartment) {
			List<String> flatSpec = spec.stream().flatMap(List::stream).map(pc -> pc.labels).flatMap(List::stream).collect(Collectors.toList());
			for (int i = 0; i < n; ++i) {
				FlowEquation temp = eq.deepCopy();
				
				PhysicalCompartment pc = temp.equationCompartments.get(i);
				if (temp.source.equals(pc) || temp.sink.equals(pc)) {
					temp.source.labels.addAll(flatSpec);
					temp.sink.labels.addAll(flatSpec);
				}
				temp.equationCompartments.set(i, prependLabelsToPC(temp.equationCompartments.get(i), flatSpec));
				expanded.add(temp);
			}
		}
		
		return expanded;
	}
	
	// tested
	public List<PhysicalCompartment> expand(PhysicalCompartment pc, List<List<PhysicalCompartment>> compartmentsOfOtherDimensions) {
		List<List<PhysicalCompartment>> product = CartesianProduct.cartesianProduct(compartmentsOfOtherDimensions);
		
		return product
				.stream()
				.map(arrangement -> new PhysicalCompartment(
					arrangement.stream().map(a -> a.labels).flatMap(List::stream).collect(Collectors.toList())
				))
				.map(p -> prependLabelsToPC(p, pc.labels))
				.collect(Collectors.toList());
	}
	
	public FlowEquation prependSelf(FlowEquation eq) {
		return prepend(eq, getLabel());
	}
	
	public FlowEquation prepend(FlowEquation eq, List<String> labels) {
		eq.source.labels.addAll(labels);
		eq.sink.labels.addAll(labels);
		for (PhysicalCompartment pc : eq.equationCompartments)
			pc.labels.addAll(labels);
		return eq;
	}

	@Override
	public List<PhysicalCompartment> getSourcesFor(PhysicalCompartment child) {
		int n = getCompartment().size();
		for  (int i = 0; i < n; ++i) {
			int dim = i;
			Compartment c = getCompartment().get(i).getCompartment();
			if (c.getLabels().equals(child.labels))
				return c.getSources().stream().map(pc->expandPCByDimensionIndex(pc, dim)).flatMap(List::stream).collect(Collectors.toList());
			for (PhysicalCompartment pc : c.getPhysicalCompartments())
				if (pc.labels.containsAll(child.labels))
					return c.getSourcesFor(child).stream().map(pc2->expandPCByDimensionIndex(pc2, dim)).flatMap(List::stream).collect(Collectors.toList());
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
				return c.getSinks().stream().map(pc->expandPCByDimensionIndex(pc, dim)).flatMap(List::stream).collect(Collectors.toList());
			for (PhysicalCompartment pc : c.getPhysicalCompartments())
				if (pc.labels.containsAll(child.labels))
					return c.getSinksFor(child).stream().map(pc2->expandPCByDimensionIndex(pc2, dim)).flatMap(List::stream).collect(Collectors.toList());
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