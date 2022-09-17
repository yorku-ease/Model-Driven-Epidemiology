/**
 */
package compartmentGroup.impl;

import compartmentGroup.CompartmentGroupPackage;
import compartmentGroup.GroupEpidemic;
import compartmentGroup.GroupSinks;
import compartmentGroup.GroupSources;
import compartmentGroup.Link;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Flow;
import epimodel.FlowWrapper;

import epimodel.impl.EpidemicImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Group Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link compartmentGroup.impl.GroupEpidemicImpl#getGroupSinks <em>Group Sinks</em>}</li>
 *   <li>{@link compartmentGroup.impl.GroupEpidemicImpl#getGroupSources <em>Group Sources</em>}</li>
 *   <li>{@link compartmentGroup.impl.GroupEpidemicImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link compartmentGroup.impl.GroupEpidemicImpl#getCompartment <em>Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GroupEpidemicImpl extends EpidemicImpl implements GroupEpidemic {

	public void edit(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Edit Group " + getId());
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
		epimodel.util.Edit.addBtn(shell, controls, "Modify flows", () -> {
			editFlows(dom, shell, controls);
		});
	}
	
	void editCompartments(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(c -> c.dispose());
		controls.clear();
        shell.setLayout(new GridLayout(4, false));
        List<Compartment> l = getCompartment()
			.stream()
			.map(CompartmentWrapper::getCompartment)
			.collect(Collectors.toList());

        epimodel.util.Edit.addText(shell, controls, "Compartment");
        epimodel.util.Edit.addText(shell, controls, "Is Source");
        epimodel.util.Edit.addText(shell, controls, "Is Sink");
        epimodel.util.Edit.addText(shell, controls, "Delete");
        
        for (Compartment e : l) {
        	epimodel.util.Edit.addText(shell, controls, e.getLabel().toString());
        	addSourceSinkCheckbox(dom, shell, controls, e, CompartmentGroupPackage.Literals.GROUP__GROUP_SOURCES);
        	addSourceSinkCheckbox(dom, shell, controls, e, CompartmentGroupPackage.Literals.GROUP__GROUP_SINKS);
    		epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getLabel(), () -> {
    			epimodel.util.Edit.transact(dom, () -> {
    				controls.forEach(c -> c.dispose());
    				controls.clear();
    		    	epimodel.util.Edit.addText(shell, controls, "Confirm Deletion of " + e.getLabel());
    				epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
    					epimodel.util.Edit.transact(dom,  ()-> getCompartment().remove(e.eContainer()));
        				shell.close();
    				});
    				shell.pack(true);
    			});
    		});
        }
    	epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Add Child", () -> {
			epimodel.util.Edit.addCompartmentWindow(dom, shell, controls, (w) -> {
				epimodel.util.Edit.transact(dom, () -> getCompartment().add(w));
			});
		});
		shell.pack(true);
	}
	
	void addSourceSinkCheckbox(EObject dom, Shell shell, List<Control> controls, Compartment target, EReference sourceOrSinkRef) {
		final Button checkbox = new Button(shell, SWT.CHECK);
		EObject l = (EObject) eGet(sourceOrSinkRef);
		final EList<Link> links = l == null ? null : l instanceof GroupSources ?
				((GroupSources) l).getLink() :
				((GroupSinks) l).getLink();
		
		if (l != null && links != null)
			for (Link link : links)
				if (link.getCompartment().equals(target)) {
					checkbox.setSelection(true);
					break;
				}
		
		checkbox.addSelectionListener(new SelectionAdapter() {
		    @SuppressWarnings("unchecked")
			@Override
		    public void widgetSelected(SelectionEvent e) {
				if (l == null)
					eSet(sourceOrSinkRef, EcoreUtil.create(sourceOrSinkRef.getEReferenceType()));
    			EList<Link> links = (EList<Link>) ((EObject) eGet(sourceOrSinkRef)).eGet(
						CompartmentGroupPackage.Literals.END__LINK);
	    		if (checkbox.getSelection()) {
    				Link l = (Link) EcoreUtil.create(CompartmentGroupPackage.Literals.LINK);
    				l.setCompartment(target);
	    			epimodel.util.Edit.transact(dom, () -> {
	    				links.add(l);
	    			});
	    		}
	    		else {
	    			for (Link link : links)
	    				if (link.getCompartment().equals(target)) {
			    			epimodel.util.Edit.transact(dom, () -> {
			    				links.remove(link);
			    			});
	    					break;
	    				}
	    		}
		    }
	    });
	}
	
	void editFlows(EObject dom, Shell shell, List<Control> controls) {
		controls.forEach(c -> c.dispose());
		controls.clear();
        shell.setLayout(new GridLayout(2, false));
        List<Flow> l = getFlow()
			.stream()
			.map(FlowWrapper::getFlow)
			.filter(f -> f != null)
			.collect(Collectors.toList());
        for (Flow e : l) {
        	epimodel.util.Edit.addText(shell, controls, e.getId());
    		epimodel.util.Edit.addBtn(shell, controls, "Delete " + e.getId(), () -> {
    			epimodel.util.Edit.transact(dom, () -> {
    				controls.forEach(c -> c.dispose());
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
			epimodel.util.Edit.addFlowWindow(shell, controls, (w) -> {
				epimodel.util.Edit.transact(dom, () -> getFlow().add(w));
				shell.close();
			});
		});
		shell.pack(true);
	}

	public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
		return getPhysicalCompartments().stream().filter(pc -> pc.labels.containsAll(c.getLabel())).collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSourcesFor(Compartment c) {
		return getPhysicalCompartments().stream().filter(pc -> {
			for (PhysicalCompartment filter : c.getSources())
				if (pc.labels.containsAll(filter.labels))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSinksFor(Compartment c) {
		return getPhysicalCompartments().stream().filter(pc -> {
			for (PhysicalCompartment filter : c.getSinks())
				if (pc.labels.containsAll(filter.labels))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		final EList<CompartmentWrapper> l = getCompartment();
		return new GroupImpl() {
			@Override
			public EList<CompartmentWrapper> getCompartment() {
				return l;
			}
		}.getPhysicalCompartments();
	}

	@Override
	public List<PhysicalFlow> getPhysicalFlows() {
		final EList<CompartmentWrapper> l = getCompartment();
		return new GroupImpl() {
			@Override
			public EList<CompartmentWrapper> getCompartment() {
				return l;
			}
		}.getFlows().stream().map(f -> f.getPhysicalFlows(this)).flatMap(List::stream).collect(Collectors.toList());
	}
	/**
	 * The cached value of the '{@link #getGroupSinks() <em>Group Sinks</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupSinks()
	 * @generated
	 * @ordered
	 */
	protected GroupSinks groupSinks;

	/**
	 * The cached value of the '{@link #getGroupSources() <em>Group Sources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupSources()
	 * @generated
	 * @ordered
	 */
	protected GroupSources groupSources;

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
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> compartment;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GroupEpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CompartmentGroupPackage.Literals.GROUP_EPIDEMIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroupSinks getGroupSinks() {
		return groupSinks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupSinks(GroupSinks newGroupSinks, NotificationChain msgs) {
		GroupSinks oldGroupSinks = groupSinks;
		groupSinks = newGroupSinks;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS, oldGroupSinks, newGroupSinks);
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
	public void setGroupSinks(GroupSinks newGroupSinks) {
		if (newGroupSinks != groupSinks) {
			NotificationChain msgs = null;
			if (groupSinks != null)
				msgs = ((InternalEObject) groupSinks).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS, null, msgs);
			if (newGroupSinks != null)
				msgs = ((InternalEObject) newGroupSinks).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS, null, msgs);
			msgs = basicSetGroupSinks(newGroupSinks, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS,
					newGroupSinks, newGroupSinks));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GroupSources getGroupSources() {
		return groupSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGroupSources(GroupSources newGroupSources, NotificationChain msgs) {
		GroupSources oldGroupSources = groupSources;
		groupSources = newGroupSources;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES, oldGroupSources, newGroupSources);
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
	public void setGroupSources(GroupSources newGroupSources) {
		if (newGroupSources != groupSources) {
			NotificationChain msgs = null;
			if (groupSources != null)
				msgs = ((InternalEObject) groupSources).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES, null, msgs);
			if (newGroupSources != null)
				msgs = ((InternalEObject) newGroupSources).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES, null, msgs);
			msgs = basicSetGroupSources(newGroupSources, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES,
					newGroupSources, newGroupSources));
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
					CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW);
		}
		return flow;
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
					CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT);
		}
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS:
			return basicSetGroupSinks(null, msgs);
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES:
			return basicSetGroupSources(null, msgs);
		case CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW:
			return ((InternalEList<?>) getFlow()).basicRemove(otherEnd, msgs);
		case CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
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
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS:
			return getGroupSinks();
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES:
			return getGroupSources();
		case CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW:
			return getFlow();
		case CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT:
			return getCompartment();
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
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS:
			setGroupSinks((GroupSinks) newValue);
			return;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES:
			setGroupSources((GroupSources) newValue);
			return;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
			return;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends CompartmentWrapper>) newValue);
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
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS:
			setGroupSinks((GroupSinks) null);
			return;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES:
			setGroupSources((GroupSources) null);
			return;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW:
			getFlow().clear();
			return;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT:
			getCompartment().clear();
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
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SINKS:
			return groupSinks != null;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__GROUP_SOURCES:
			return groupSources != null;
		case CompartmentGroupPackage.GROUP_EPIDEMIC__FLOW:
			return flow != null && !flow.isEmpty();
		case CompartmentGroupPackage.GROUP_EPIDEMIC__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GroupEpidemicImpl
