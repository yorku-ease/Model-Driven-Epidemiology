/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.DimensionEpidemicPackage;
import dimensionEpidemic.Product;
import dimensionEpidemic.util.CartesianProduct;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Composable;
import epimodel.Epidemic;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.impl.CompartmentImpl;
import epimodel.impl.EpidemicImpl;
import epimodel.impl.FlowImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;
import epimodel.util.Comparison.ChildrenDiffResult;
import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.Match;
import epimodel.util.Comparison.MatchResult;

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
	public Difference compareWithSameClass(Composable other, MatchResult matches) {
		Match match = matches.find(this, other);
		
		List<Compartment> myCompartments = getDimensions().stream().map(w -> w.getCompartment()).collect(Collectors.toList());
		List<Compartment> otherCompartments = ((ProductImpl) other).getDimensions().stream().map(w -> w.getCompartment()).collect(Collectors.toList());
		
		ChildrenDiffResult childrenDiffs = new ChildrenDiffResult(myCompartments, otherCompartments, matches);
		
		List<Match> accountedForMatches = new ArrayList<>(childrenDiffs.accountsForMatches);
		accountedForMatches.add(match);
		
		boolean isSame = childrenDiffs.isSame && getLabels().equals(other.getLabels());
		
		String baseDescription = super.compareWithDifferentClass(other, matches).getSimpleDescription();
		
		return new Difference(accountedForMatches, new ArrayList<>(childrenDiffs.myUnMatchedCompartments), new ArrayList<>(childrenDiffs.otherUnMatchedCompartments), isSame) {
			@Override public String getSimpleDescription() {
				StringBuilder sb = new StringBuilder(baseDescription);
				if (!isSame)
					sb.append(childrenDiffs.getSimpleDescription());
				return sb.toString();
			}
		};
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

	@Override
	public void edit(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Edit Product " + getLabel());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Labels", () -> {
			controls.forEach(c -> c.dispose());
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
		controls.forEach(c -> c.dispose());
		controls.clear();
		shell.setLayout(new GridLayout(2, false));
		List<Compartment> l = getDimensions().stream().map(CompartmentWrapper::getCompartment)
				.collect(Collectors.toList());

		for (Compartment e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getLabel().toString());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getLabel(), () -> {
				epimodel.util.Edit.transact(dom, () -> {
					controls.forEach(c -> c.dispose());
					controls.clear();
					epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getLabel());
					epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
						epimodel.util.Edit.transact(dom, () -> getDimensions().remove(e.eContainer()));
						shell.close();
					});
					shell.pack(true);
				});
			});
		}
		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Dimension", () -> epimodel.util.Edit.addCompartmentWindow(dom,
				shell, controls, (w) -> epimodel.util.Edit.transact(dom, () -> getDimensions().add(w))));
		shell.pack(true);
	}

	void editFlows(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(c -> c.dispose());
		controls.clear();
		shell.setLayout(new GridLayout(2, false));
		List<Flow> l = getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList());

		for (Flow e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getId().toString());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getId(), () -> {
				epimodel.util.Edit.transact(dom, () -> {
					controls.forEach(c -> c.dispose());
					controls.clear();
					epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getId());
					epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
						epimodel.util.Edit.transact(dom, () -> getDimensions().remove(e.eContainer()));
						shell.close();
					});
					shell.pack(true);
				});
			});
		}

		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Flow", () -> epimodel.util.Edit.addFlowWindow(shell, controls,
				(w) -> epimodel.util.Edit.transact(dom, () -> getFlow().add(w))));
		shell.pack(true);
	}

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return CartesianProduct
				.cartesianProduct(getDimensions().stream().map(CompartmentWrapper::getCompartment)
						.map(Compartment::getPhysicalCompartments).collect(Collectors.toList()))
				.stream().map(ps -> combinePhysicalCompartmentsIntoOne(ps)).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	protected PhysicalCompartment prependSelf(PhysicalCompartment p) {
		PhysicalCompartment p2 = new PhysicalCompartment(new ArrayList<>(p.labels));
		p2.labels.addAll(0, getLabel());
		return p2;
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

		/*
		 * This terrible piece of code is in charge of hiding parts of the epidemic
		 * to flows based on the dimension they are in (so flows dont take from one hyperplane
		 * to another when they shouldn't) essentially flows see the hyperplane they are in
		 * and there exists copies of the flow for each existing hyperplane, unaware of eachother
		 * 
		 * EXAMPLE:
		 * notation: compartment -> [{compartments},{flows}]
		 * 
		 * product of group S,I and group A,B
		 * 	[{S,I}, {S*I->I}]*[{A,B}, {}]
		 * 	produces:
		 * 	    SA*IA->IA
		 * 	    SB*IB->IB
		 * 	    
		 * which is different from if the flow was outside the group S,I
		 * (in this case in the product containing the groups)
		 * 	[{[{S,I}, {}]*[{A,B}, {}]}, {S*I->I}]
		 * 	produces:
		 * 	    SA*IA->IA
		 * 	    SA*IB->IA
		 * 	    SB*IA->IB
		 * 	    SB*IB->IB
		 */

		List<Compartment> dims = getDimensions().stream().map(CompartmentWrapper::getCompartment)
				.collect(Collectors.toList());
		List<Flow> res = new ArrayList<>();

		for (int i = 0; i < dims.size(); ++i) {
			Compartment dimension = dims.get(i);
			List<Flow> flowsOfDim = dimension.getFlows();
			List<Compartment> otherDimensions = getDimensionsExceptOne(dimension);
			List<String> ids = otherDimensions.stream().map(d -> d.getLabel()).flatMap(List::stream)
					.collect(Collectors.toList());
			List<List<PhysicalCompartment>> whatFlowsWillSee = CartesianProduct.cartesianProduct(
					otherDimensions.stream().map(d -> d.getPhysicalCompartments()).collect(Collectors.toList()));

			for (Flow f : flowsOfDim) {
				for (List<PhysicalCompartment> specifications : whatFlowsWillSee) {
					res.add(new FlowImpl() {
						@Override
						public List<PhysicalFlow> getPhysicalFlows(Epidemic epidemic) {
							// this is where it gets complicated
							return f.getPhysicalFlows((Epidemic) new EpidemicImpl() {

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

								// this is where the hyperplane is selected
								List<PhysicalCompartment> matchingSpecification(List<PhysicalCompartment> l) {
									return l.stream().filter(pc -> {
										for (int j = 0; j < ids.size(); ++j)
											// if you match a specification        you need all of its labels
											if (pc.labels.contains(ids.get(j))
													&& !pc.labels.containsAll(specifications.get(j).labels))
												return false;
										// no incorrect specifications
										return true;
									}).collect(Collectors.toList());
								}
								
								// methods to override to instantiate EpidemicImpl, they won't be called
								@Override
								public List<PhysicalCompartment> getPhysicalCompartments() {
									throw new NullPointerException();
								}

								@Override
								public List<PhysicalFlow> getPhysicalFlows() {
									throw new NullPointerException();
								}

								@Override
								public List<PhysicalCompartment> getSources() {
									throw new NullPointerException();
								}

								@Override
								public List<PhysicalCompartment> getSinks() {
									throw new NullPointerException();
								}

								@Override
								public List<Flow> getFlows() {
									throw new NullPointerException();
								}

								@Override
								public Difference compareWithSameClass(Composable other, MatchResult matches) {
									throw new NullPointerException();
								}
							}).stream()
									.map(p -> new PhysicalFlow(p.equations.stream()
											.map(e -> new PhysicalFlowEquation(e.equationCompartments,
													e.affectedCompartments, e.coefficients,
													e.equation.replace(f.getId(), getId()), e.requiredOperators))
											.collect(Collectors.toList())))
									.collect(Collectors.toList());
						}

						@Override
						public String getId() {
							String res = f.getId();
							for (int j = 0; j < ids.size(); ++j)
								res += specifications.get(j).labels;
							res = res.replace("][", ", ");
							return res;
						}
					});
				}
			}
		}

		res.addAll(getFlow().stream().map(FlowWrapper::getFlow).collect(Collectors.toList()));
		return res;
	}

	protected List<Compartment> getDimensionsExceptOne(Compartment dimensionToIgnore) {
		return getDimensions().stream().map(CompartmentWrapper::getCompartment).filter(c -> c != dimensionToIgnore)
				.collect(Collectors.toList());
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
