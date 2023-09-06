/**
 */
package mortality.impl;

import epimodel.CompartmentWrapper;

import epimodel.impl.CompartmentImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import mortality.Mortality;
import mortality.MortalityPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mortality</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mortality.impl.MortalityImpl#getMortality <em>Mortality</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MortalityImpl extends CompartmentImpl implements Mortality {

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		List<PhysicalCompartment> res = new ArrayList<>();
		for (PhysicalCompartment pc : getMortality().getCompartment().getPhysicalCompartments()) {
			res.add(prepend("Alive", pc));
			res.add(prepend("Dead", pc));
		}
		return res;
	}
	
	PhysicalCompartment prepend(String label, PhysicalCompartment pc) {
		List<String> labels = new ArrayList<>(pc.labels);
		labels.add(0, label);
		return new PhysicalCompartment(labels);
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		List<PhysicalCompartment> res = new ArrayList<>();
		for (PhysicalCompartment pc : getMortality().getCompartment().getSources()) {
			res.add(prepend("Alive", pc));
			res.add(prepend("Dead", pc));
		}
		return res;
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		List<PhysicalCompartment> res = new ArrayList<>();
		for (PhysicalCompartment pc : getMortality().getCompartment().getSinks()) {
			res.add(prepend("Alive", pc));
			res.add(prepend("Dead", pc));
		}
		return res;
	}

	@Override
	public List<PhysicalFlow> getEquations() {
		List<PhysicalFlow> res = new ArrayList<>();
		for (PhysicalCompartment pc : getMortality().getCompartment().getPhysicalCompartments())
			res.add(
				new PhysicalFlow(
					prepend("Alive", pc),
					prepend("Dead", pc),
					"(* $0 (parameter normalizing mortality) (parameter mortality $0))",
					"Mortality"
				)
			);
		for (PhysicalFlow eq : getMortality().getCompartment().getEquations())
			res.add(
				new PhysicalFlow(
					prepend("Alive", eq.source),
					prepend("Alive", eq.sink),
					eq.equation,
					eq.name
				)
			);
		return res;
	}

	@Override
	public void edit(Shell shell, List<Control> controls) {
		shell.setText("Edit Group Epidemic " + getLabel());
		shell.setLayout(new GridLayout(1, false));
		epimodel.util.Edit.addBtn(shell, controls, "Modify Labels", () -> {
			controls.forEach(Control::dispose);
			controls.clear();
			super.edit(shell, controls); // labels window
			shell.pack(true);
		});
		epimodel.util.Edit.addBtn(shell, controls, "Add Child", () -> {
			epimodel.util.Edit.addCompartmentWindow(this, shell, controls, w -> setMortality(w));
		});
	}
	
	/**
	 * The cached value of the '{@link #getMortality() <em>Mortality</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMortality()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper mortality;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MortalityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MortalityPackage.Literals.MORTALITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getMortality() {
		return mortality;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMortality(CompartmentWrapper newMortality, NotificationChain msgs) {
		CompartmentWrapper oldMortality = mortality;
		mortality = newMortality;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MortalityPackage.MORTALITY__MORTALITY, oldMortality, newMortality);
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
	public void setMortality(CompartmentWrapper newMortality) {
		if (newMortality != mortality) {
			NotificationChain msgs = null;
			if (mortality != null)
				msgs = ((InternalEObject) mortality).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MortalityPackage.MORTALITY__MORTALITY, null, msgs);
			if (newMortality != null)
				msgs = ((InternalEObject) newMortality).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MortalityPackage.MORTALITY__MORTALITY, null, msgs);
			msgs = basicSetMortality(newMortality, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MortalityPackage.MORTALITY__MORTALITY, newMortality,
					newMortality));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case MortalityPackage.MORTALITY__MORTALITY:
			return basicSetMortality(null, msgs);
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
		case MortalityPackage.MORTALITY__MORTALITY:
			return getMortality();
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
		case MortalityPackage.MORTALITY__MORTALITY:
			setMortality((CompartmentWrapper) newValue);
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
		case MortalityPackage.MORTALITY__MORTALITY:
			setMortality((CompartmentWrapper) null);
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
		case MortalityPackage.MORTALITY__MORTALITY:
			return mortality != null;
		}
		return super.eIsSet(featureID);
	}

} //MortalityImpl
