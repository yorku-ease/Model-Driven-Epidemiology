/**
 */
package dimensionEpidemic.impl;

import dimensionEpidemic.DimensionEpidemic;
import dimensionEpidemic.DimensionEpidemicPackage;
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
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dimension Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link dimensionEpidemic.impl.DimensionEpidemicImpl#getFlow <em>Flow</em>}</li>
 *   <li>{@link dimensionEpidemic.impl.DimensionEpidemicImpl#getDimension <em>Dimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionEpidemicImpl extends EpidemicImpl implements DimensionEpidemic {

	@Override
	public List<PhysicalCompartment> getSources() {
		return asProduct().getSources();
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return asProduct().getSinks();
	}

	@Override
	public List<Flow> getFlows() {
		return asProduct().getFlows();
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

		shell.setText("Edit Group " + getId());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Id", () -> {
			controls.forEach(c -> c.dispose());
			controls.clear();
			super.edit(dom, shell, controls); // Id window
			shell.pack(true);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Modify Compartments", () -> {
			asProduct().editCompartments(dom, shell, controls);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Modify Flows", () -> {
			asProduct().editFlows(dom, shell, controls);
		});
	}

	List<PhysicalCompartment> physicalCompartments = null;
	List<PhysicalFlow> physicalFlows = null;

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {

		if (physicalCompartments == null)
			physicalCompartments = asProduct().getPhysicalCompartments();

		return physicalCompartments;
	}

	@Override
	public List<PhysicalFlow> getPhysicalFlows() {

		if (physicalFlows == null) {

		    DimensionEpidemicImpl de = this;

			physicalFlows = new ProductImpl() {
				@Override
				public EList<CompartmentWrapper> getDimensions() {
					return de.getDimension();
				}

				@Override
				public EList<FlowWrapper> getFlow() {
					return de.getFlow();
				}
			}.getFlows().stream().map(f -> f.getPhysicalFlows(this)).flatMap(List::stream).collect(Collectors.toList());
		}

		return physicalFlows;
	}

	public List<PhysicalCompartment> getPhysicalFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> pc.labels.containsAll(c.getLabel()))
				.collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSourcesFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> {
			for (PhysicalCompartment filter : c.getSources())
				if (pc.labels.containsAll(filter.labels))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSinksFor(Compartment c) {
		return physicalCompartments.stream().filter(pc -> {
			for (PhysicalCompartment filter : c.getSinks())
				if (pc.labels.containsAll(filter.labels))
					return true;
			return false;
		}).collect(Collectors.toList());
	}

    ProductImpl asProduct() {
        DimensionEpidemicImpl de = this;

		return new ProductImpl() {
            @Override
            public EList<CompartmentWrapper> getDimensions() {
                return de.getDimension();
            }

            @Override
            public EList<FlowWrapper> getFlow() {
                return de.getFlow();
            }
        };
    }

	/**
	 * The cached value of the '{@link #getDimension() <em>Dimension</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDimension()
	 * @generated
	 * @ordered
	 */
	protected EList<CompartmentWrapper> dimension;

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
	public EList<FlowWrapper> getFlow() {
		if (flow == null) {
			flow = new EObjectContainmentEList<FlowWrapper>(FlowWrapper.class, this,
					DimensionEpidemicPackage.DIMENSION_EPIDEMIC__FLOW);
		}
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CompartmentWrapper> getDimension() {
		if (dimension == null) {
			dimension = new EObjectContainmentEList<CompartmentWrapper>(CompartmentWrapper.class, this,
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__FLOW:
			return ((InternalEList<?>) getFlow()).basicRemove(otherEnd, msgs);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__FLOW:
			return getFlow();
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends FlowWrapper>) newValue);
			return;
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
			getDimension().clear();
			getDimension().addAll((Collection<? extends CompartmentWrapper>) newValue);
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__FLOW:
			getFlow().clear();
			return;
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
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__FLOW:
			return flow != null && !flow.isEmpty();
		case DimensionEpidemicPackage.DIMENSION_EPIDEMIC__DIMENSION:
			return dimension != null && !dimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}
} //DimensionEpidemicImpl
