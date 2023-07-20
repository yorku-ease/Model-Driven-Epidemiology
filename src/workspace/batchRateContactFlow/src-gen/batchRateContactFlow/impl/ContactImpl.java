/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Contact;

import epimodel.Compartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.impl.ContactImpl#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContactImpl extends FromToFlowImpl implements Contact {

	@Override
	public void edit(Shell shell, List<Control> controls, Compartment target) {
//		shell.setText("Edit Flow " + getId() + " for compartment " + target.getLabel());
//		shell.setLayout(new GridLayout(2, false));
//		for (EReference ref : flowRefs()) {
//			epimodel.util.Edit.addText(shell, controls, ref.getName());
//			epimodel.util.Edit.addBtn(shell, controls, "Set '" + ref.getName() + "' to " + target.getLabel(), () -> {
//				epimodel.util.Edit.transact(this, () -> eSet(ref, target));
//				shell.close();
//			});
//		}
	}

	@Override
	public List<PhysicalFlow> getEquations() {
		String basic_coefficient = "(parameter " + getId() + " " + from.getLabels() + ") ";
		String from_compartment_value = from.getLabels().toString();
		String contact_compartments = contact.getLabels().toString();
		String sumproduct_of_contacts_times_coefs = "(sumproduct " + contact_compartments + ")";
		// todo other coefficients susceptibility (from), infectiousness (contact), contact-mixing (from * contact)
		String equation = "(* " + basic_coefficient + from_compartment_value + sumproduct_of_contacts_times_coefs + ")";
		List<String> requiredOperators = Arrays.asList("*", "sumproduct");
		return new ArrayList<>(Arrays.asList(new PhysicalFlow(new PhysicalCompartment(from.getLabels()),
				new PhysicalCompartment(to.getLabels()), equation, requiredOperators)));
	}

	/**
	 * The cached value of the '{@link #getContact() <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact()
	 * @generated
	 * @ordered
	 */
	protected Compartment contact;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ContactImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.CONTACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getContact() {
		if (contact != null && contact.eIsProxy()) {
			InternalEObject oldContact = (InternalEObject) contact;
			contact = (Compartment) eResolveProxy(oldContact);
			if (contact != oldContact) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BatchRateContactFlowPackage.CONTACT__CONTACT, oldContact, contact));
			}
		}
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetContact() {
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContact(Compartment newContact) {
		Compartment oldContact = contact;
		contact = newContact;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.CONTACT__CONTACT,
					oldContact, contact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BatchRateContactFlowPackage.CONTACT__CONTACT:
			if (resolve)
				return getContact();
			return basicGetContact();
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT:
			setContact((Compartment) newValue);
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT:
			setContact((Compartment) null);
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT:
			return contact != null;
		}
		return super.eIsSet(featureID);
	}

} //ContactImpl
