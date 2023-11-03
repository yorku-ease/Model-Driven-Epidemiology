/**
 */
package agingcompartment.impl;

import agingcompartment.Aging;
import agingcompartment.agingcompartmentPackage;
import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Aging</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link agingcompartment.impl.AgingImpl#getAgeGroups <em>Age Groups</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AgingImpl extends CompartmentImpl implements Aging {

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		List<PhysicalCompartment> res = new ArrayList<>();
		for (String ages : getAgeGroups().split(","))
			res.add(prependSelf(new PhysicalCompartment(Arrays.asList(ages))));
		return res;
	}

	PhysicalCompartment prependSelf(PhysicalCompartment pc) {
		List<String> labels = new ArrayList<>(pc.labels);
		labels.addAll(0, getLabels());
		return new PhysicalCompartment(labels);
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		return new ArrayList<>(Arrays.asList(getPhysicalCompartments().get(0)));
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		return new ArrayList<>(Arrays.asList(getPhysicalCompartments().get(getPhysicalCompartments().size() - 1)));
	}

	@Override
	public List<PhysicalFlow> getEquations() {
		List<PhysicalFlow> res = new ArrayList<>();
		List<PhysicalCompartment> pcs = getPhysicalCompartments();
		for (int i = 0; i < pcs.size() - 1; ++i) {
			res.add(new PhysicalFlow(
				pcs.get(i),
				pcs.get(i + 1),
				"(* $0 (parameter normalizing Aging) (parameter Aging $0))",
				"Aging"
			));
		}
		return res;
	}

	@Override
	public void edit(Shell shell, List<Control> controls) {
		shell.setText("Edit Aging " + getLabel());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Labels", () -> {
			controls.forEach(Control::dispose);
			controls.clear();
			super.edit(shell, controls); // labels window
			shell.pack(true);
		});
	}
	
	/**
	 * The default value of the '{@link #getAgeGroups() <em>Age Groups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgeGroups()
	 * @generated
	 * @ordered
	 */
	protected static final String AGE_GROUPS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAgeGroups() <em>Age Groups</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgeGroups()
	 * @generated
	 * @ordered
	 */
	protected String ageGroups = AGE_GROUPS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return agingcompartmentPackage.Literals.AGING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAgeGroups() {
		return ageGroups;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgeGroups(String newAgeGroups) {
		String oldAgeGroups = ageGroups;
		ageGroups = newAgeGroups;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, agingcompartmentPackage.AGING__AGE_GROUPS,
					oldAgeGroups, ageGroups));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case agingcompartmentPackage.AGING__AGE_GROUPS:
			return getAgeGroups();
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
		case agingcompartmentPackage.AGING__AGE_GROUPS:
			setAgeGroups((String) newValue);
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
		case agingcompartmentPackage.AGING__AGE_GROUPS:
			setAgeGroups(AGE_GROUPS_EDEFAULT);
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
		case agingcompartmentPackage.AGING__AGE_GROUPS:
			return AGE_GROUPS_EDEFAULT == null ? ageGroups != null : !AGE_GROUPS_EDEFAULT.equals(ageGroups);
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
		result.append(" (AgeGroups: ");
		result.append(ageGroups);
		result.append(')');
		return result.toString();
	}

} //AgingImpl
