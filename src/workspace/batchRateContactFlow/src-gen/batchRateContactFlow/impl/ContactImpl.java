/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Contact;

import epimodel.Compartment;
import epimodel.Epidemic;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;

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
 * </ul>
 *
 * @generated
 */
public class ContactImpl extends FromToFlowImpl implements Contact {
	
	@Override
	public List<PhysicalFlow> getPhysicalFlows(Epidemic epidemic) {
		List<PhysicalFlow> res = new ArrayList<>();
		
		int index = 0;
		for (PhysicalCompartment from : epidemic.getPhysicalSinksFor(from))
			for (PhysicalCompartment to : epidemic.getPhysicalSourcesFor(to))
				for (PhysicalCompartment contact : epidemic.getPhysicalFor(contact)) {
				
				List<PhysicalCompartment> equationCompartments = Arrays.asList(from, contact);
				List<PhysicalCompartment> affectedCompartments = Arrays.asList(from, to);
				List<Float> coefficients = Arrays.asList(-1f, 1f);
				String flowParameter = "(get " + getId() + " " + index++ +")";
				String equation = "(* " + flowParameter + " $0 $1)";
				List<String> requiredOperators = Arrays.asList("*", "get");
				
				res.add(new PhysicalFlow(
						Arrays.asList(
								new PhysicalFlowEquation(
									equationCompartments,
									affectedCompartments,
									coefficients,
									equation,
									requiredOperators
							))));
			}
		return res;
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
