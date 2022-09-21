/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.EpimodelPackage;
import epimodel.Flow;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.FlowImpl#getId <em>Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FlowImpl extends MinimalEObjectImpl.Container implements Flow {
	
	@Override
	public void edit(Shell shell, List<Control> controls, Compartment target) {
		shell.setText("Edit Flow " + getId() + " for compartment " + target.getLabel());
        shell.setLayout(new GridLayout(2, false));
		for (EReference ref : flowRefs()) {
			epimodel.util.Edit.addText(shell, controls, ref.getName());
			epimodel.util.Edit.addBtn(shell, controls, "Set to " + target.getLabel(), () -> {
				epimodel.util.Edit.transact(this, () -> eSet(ref, target));
				shell.close();
			});
		}
	}
	
	@Override
	public List<PhysicalCompartment> getPhysicalFor(Epidemic epidemic, Compartment c) {
		return epidemic.getPhysicalFor(c);
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSourcesFor(Epidemic epidemic, Compartment c) {
		return epidemic.getPhysicalSourcesFor(c);
	}

	@Override
	public List<PhysicalCompartment> getPhysicalSinksFor(Epidemic epidemic, Compartment c) {
		return epidemic.getPhysicalSinksFor(c);
	}
	
	final List<EReference> flowRefs() {
		List<EClass> eclasses = new ArrayList<>(eClass().getEAllSuperTypes());
		eclasses.add(eClass());
		return eclasses.stream().map(c -> c.getEReferences().stream().filter(ref -> {
			return ref.getEReferenceType().equals(EpimodelPackage.Literals.COMPARTMENT);
		}).collect(Collectors.toList())).flatMap(List::stream).collect(Collectors.toList());
	}

	@Override
	public final List<EObject> getTargetObjects() {
		return flowRefs().stream().map(ref -> (EObject) eGet(ref)).collect(Collectors.toList());
	}
	
	@Override
	public final String getTargetRelation(EObject target) {
		return flowRefs().stream().filter(ref -> eGet(ref).equals(target)).map(ref -> ref.getName()).collect(Collectors.joining(", "));
	}

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpimodelPackage.FLOW__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpimodelPackage.FLOW__ID:
			return getId();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EpimodelPackage.FLOW__ID:
			setId((String) newValue);
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
		case EpimodelPackage.FLOW__ID:
			setId(ID_EDEFAULT);
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
		case EpimodelPackage.FLOW__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(')');
		return result.toString();
	}

} //FlowImpl
