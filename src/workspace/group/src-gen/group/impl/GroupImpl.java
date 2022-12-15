/**
 */
package group.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;

import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import group.Group;
import group.GroupPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link group.impl.GroupImpl#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link group.impl.GroupImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link group.impl.GroupImpl#getSource <em>Source</em>}</li>
 *   <li>{@link group.impl.GroupImpl#getSink <em>Sink</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupImpl extends CompartmentImpl implements Group {

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
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> source;

	/**
	 * The cached value of the '{@link #getSink() <em>Sink</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSink()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> sink;

	public void edit(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Edit Group Epidemic " + getLabel());
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
		epimodel.util.Edit.addBtn(shell, controls, "Modify flows", () -> {
			editFlows(dom, shell, controls);
		});
	}

	void editCompartments(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(Control::dispose);
		controls.clear();
		int nCol = 4;
		shell.setLayout(new GridLayout(nCol, false));
		List<Compartment> l = getCompartment().stream().map(CompartmentWrapper::getCompartment)
				.collect(Collectors.toList());

		epimodel.util.Edit.addText(shell, controls, "Compartment");
		epimodel.util.Edit.addText(shell, controls, "Is Source");
		epimodel.util.Edit.addText(shell, controls, "Is Sink");
		epimodel.util.Edit.addText(shell, controls, "Delete");

		for (Compartment e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getLabel().toString());
			//			addSourceSinkCheckbox(dom, shell, controls, e, CompartmentGroupPackage.Literals.GROUP__GROUP_SOURCES);
			//			addSourceSinkCheckbox(dom, shell, controls, e, CompartmentGroupPackage.Literals.GROUP__GROUP_SINKS);
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
		for (int i = 0; i < nCol - 1; ++i)
			epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Child", () -> {
			epimodel.util.Edit.addCompartmentWindow(dom, shell, controls, w -> {
				epimodel.util.Edit.transact(dom, () -> getCompartment().add(w));
			});
		});
		shell.pack(true);
	}

	void addSourceSinkCheckbox(EObject dom, Shell shell, List<Control> controls, Compartment target,
			EReference sourceOrSinkRef) {
		final Button checkbox = new Button(shell, SWT.CHECK);
		//		EObject l = (EObject) eGet(sourceOrSinkRef);
		//		final EList<Link> links = l == null ? null
		//				: l instanceof GroupSources ? ((GroupSources) l).getLink() : ((GroupSinks) l).getLink();
		//
		//		if (l != null && links != null)
		//			for (Link link : links)
		//				if (link.getCompartment().equals(target)) {
		//					checkbox.setSelection(true);
		//					break;
		//				}
		//
		//		checkbox.addSelectionListener(new SelectionAdapter() {
		//			@SuppressWarnings("unchecked")
		//			@Override
		//			public void widgetSelected(SelectionEvent e) {
		//				if (l == null)
		//					eSet(sourceOrSinkRef, EcoreUtil.create(sourceOrSinkRef.getEReferenceType()));
		//				EList<Link> links = (EList<Link>) ((EObject) eGet(sourceOrSinkRef))
		//						.eGet(CompartmentGroupPackage.Literals.END__LINK);
		//				if (checkbox.getSelection()) {
		//					Link l = (Link) EcoreUtil.create(CompartmentGroupPackage.Literals.LINK);
		//					l.setCompartment(target);
		//					epimodel.util.Edit.transact(dom, () -> {
		//						links.add(l);
		//					});
		//				} else {
		//					for (Link link : links)
		//						if (link.getCompartment().equals(target)) {
		//							epimodel.util.Edit.transact(dom, () -> {
		//								links.remove(link);
		//							});
		//							break;
		//						}
		//				}
		//			}
		//		});

		controls.add(checkbox);
	}

	void editFlows(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(Control::dispose);
		controls.clear();
		shell.setLayout(new GridLayout(2, false));
		List<Flow> l = getFlow().stream().map(FlowWrapper::getFlow).filter(f -> f != null).collect(Collectors.toList());
		for (Flow e : l) {
			epimodel.util.Edit.addText(shell, controls, e.getId());
			epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getId(), () -> {
				epimodel.util.Edit.transact(dom, () -> {
					controls.forEach(Control::dispose);
					controls.clear();
					epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getId());
					epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
						getFlow().remove(e.eContainer());
						shell.close();
					});
					shell.pack(true);
				});
			});
		}
		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Child", () -> {
			epimodel.util.Edit.addFlowWindow(shell, controls, w -> {
				epimodel.util.Edit.transact(dom, () -> getFlow().add(w));
			});
		});
		shell.pack(true);
	}

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		return getCompartment().stream().map(CompartmentWrapper::getCompartment)
				.map(Compartment::getPhysicalCompartments).flatMap(List::stream).map(p -> prependSelf(p))
				.collect(Collectors.toList());
	}

	//	@Override
	//	public List<PhysicalCompartment> getSources() {
	//		GroupSinks sources = getGroupSinks();
	//		Stream<Compartment> l = (sources != null && sources.getLink().size() > 0)
	//				? sources.getLink().stream().map(Link::getCompartment)
	//				: getCompartment().stream().map(CompartmentWrapper::getCompartment);
	//
	//		return l.map(Compartment::getSinks).flatMap(List::stream).collect(Collectors.toList());
	//	}
	//
	//	@Override
	//	public List<PhysicalCompartment> getSinks() {
	//		GroupSinks sinks = getGroupSinks();
	//		Stream<Compartment> l = (sinks != null && sinks.getLink().size() > 0)
	//				? sinks.getLink().stream().map(Link::getCompartment)
	//				: getCompartment().stream().map(CompartmentWrapper::getCompartment);
	//
	//		return l.map(Compartment::getSinks).flatMap(List::stream).collect(Collectors.toList());
	//	}

	@Override
	public List<PhysicalFlow> getPhysicalFlows() {
		List<PhysicalFlow> res = new ArrayList<>();
		//		res.addAll(getFlow().stream().map(FlowWrapper::getFlow).map(Flow::getPhysicalFlow).collect(Collectors.toList()));
		res.addAll(getCompartment().stream().map(CompartmentWrapper::getCompartment).map(Compartment::getPhysicalFlows)
				.flatMap(List::stream).collect(Collectors.toList()));
		return res;
	}

	protected PhysicalCompartment prependSelf(PhysicalCompartment p) {
		PhysicalCompartment p2 = new PhysicalCompartment(new ArrayList<>(p.labels));
		p2.labels.addAll(0, getLabel());
		return p2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GroupImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GroupPackage.Literals.GROUP;
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
					GroupPackage.GROUP__COMPARTMENT);
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
			flow = new EObjectContainmentEList<FlowWrapper>(FlowWrapper.class, this, GroupPackage.GROUP__FLOW);
		}
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CompartmentWrapper> getSource() {
		if (source == null) {
			source = new EObjectResolvingEList<CompartmentWrapper>(CompartmentWrapper.class, this,
					GroupPackage.GROUP__SOURCE);
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CompartmentWrapper> getSink() {
		if (sink == null) {
			sink = new EObjectResolvingEList<CompartmentWrapper>(CompartmentWrapper.class, this,
					GroupPackage.GROUP__SINK);
		}
		return sink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GroupPackage.GROUP__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
		case GroupPackage.GROUP__FLOW:
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
		case GroupPackage.GROUP__COMPARTMENT:
			return getCompartment();
		case GroupPackage.GROUP__FLOW:
			return getFlow();
		case GroupPackage.GROUP__SOURCE:
			return getSource();
		case GroupPackage.GROUP__SINK:
			return getSink();
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
		case GroupPackage.GROUP__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends CompartmentWrapper>) newValue);
			return;
		case GroupPackage.GROUP__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
			return;
		case GroupPackage.GROUP__SOURCE:
			getSource().clear();
			getSource().addAll((Collection<? extends CompartmentWrapper>) newValue);
			return;
		case GroupPackage.GROUP__SINK:
			getSink().clear();
			getSink().addAll((Collection<? extends CompartmentWrapper>) newValue);
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
		case GroupPackage.GROUP__COMPARTMENT:
			getCompartment().clear();
			return;
		case GroupPackage.GROUP__FLOW:
			getFlow().clear();
			return;
		case GroupPackage.GROUP__SOURCE:
			getSource().clear();
			return;
		case GroupPackage.GROUP__SINK:
			getSink().clear();
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
		case GroupPackage.GROUP__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		case GroupPackage.GROUP__FLOW:
			return flow != null && !flow.isEmpty();
		case GroupPackage.GROUP__SOURCE:
			return source != null && !source.isEmpty();
		case GroupPackage.GROUP__SINK:
			return sink != null && !sink.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GroupImpl
