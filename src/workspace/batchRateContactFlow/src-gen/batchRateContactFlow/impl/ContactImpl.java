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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.impl.ContactImpl#getContact <em>Contact</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.ContactImpl#getContactParameters <em>Contact Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContactImpl extends FromToFlowImpl implements Contact {

	@Override
	public List<PhysicalFlow> getEquations() {
		String base_template = " (parameter {} " + getId() + " $)";
		String _parameters = get_from_to_parameters();
		String _contactParameters = "";
		{
			String contact_parameter_template = base_template.replace("$", contact.getLabels().toString());
			if (contactParameters != null)
				for (String p : contactParameters.split(","))
					_contactParameters += contact_parameter_template.replace("{}", p);
		}
		String sumproduct_of_contacts_times_coefs = " (sumproduct " + contact.getLabels() + " " + _contactParameters + ")";
		String equation = "(* $0" + sumproduct_of_contacts_times_coefs + _parameters + ")";
		return new ArrayList<>(
			Arrays.asList(
					new PhysicalFlow(
						new PhysicalCompartment(from.getLabels()),
						new PhysicalCompartment(to.getLabels()),
						equation
					)
				)
		);
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
	 * The default value of the '{@link #getContactParameters() <em>Contact Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTACT_PARAMETERS_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getContactParameters() <em>Contact Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactParameters()
	 * @generated
	 * @ordered
	 */
	protected String contactParameters = CONTACT_PARAMETERS_EDEFAULT;

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
	public String getContactParameters() {
		return contactParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContactParameters(String newContactParameters) {
		String oldContactParameters = contactParameters;
		contactParameters = newContactParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BatchRateContactFlowPackage.CONTACT__CONTACT_PARAMETERS, oldContactParameters, contactParameters));
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT_PARAMETERS:
			return getContactParameters();
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT_PARAMETERS:
			setContactParameters((String) newValue);
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT_PARAMETERS:
			setContactParameters(CONTACT_PARAMETERS_EDEFAULT);
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
		case BatchRateContactFlowPackage.CONTACT__CONTACT_PARAMETERS:
			return CONTACT_PARAMETERS_EDEFAULT == null ? contactParameters != null
					: !CONTACT_PARAMETERS_EDEFAULT.equals(contactParameters);
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
		result.append(" (contactParameters: ");
		result.append(contactParameters);
		result.append(')');
		return result.toString();
	}

} //ContactImpl
