/**
 */
package epidimensionsbase.impl;

import epidimensionsbase.Contact;
import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsbase.FlowVisitor;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsbase.impl.ContactImpl#getFrom <em>From</em>}</li>
 *   <li>{@link epidimensionsbase.impl.ContactImpl#getTo <em>To</em>}</li>
 *   <li>{@link epidimensionsbase.impl.ContactImpl#getContact <em>Contact</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContactImpl extends MinimalEObjectImpl.Container implements Contact {

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected epibase.Compartment from;
	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected epibase.Compartment to;

	@Override
	public void accept(Object visitor) throws Exception {
		((FlowVisitor<?>) visitor).visit(this);
	}

	/**
	 * The cached value of the '{@link #getContact() <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact()
	 * @generated
	 * @ordered
	 */
	protected epibase.Compartment contact;

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
		return EpidimensionsbasePackage.Literals.CONTACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public epibase.Compartment getFrom() {
		if (from != null && ((EObject) from).eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject) from;
			from = (epibase.Compartment) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EpidimensionsbasePackage.CONTACT__FROM,
							oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public epibase.Compartment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrom(epibase.Compartment newFrom) {
		epibase.Compartment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidimensionsbasePackage.CONTACT__FROM, oldFrom,
					from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public epibase.Compartment getTo() {
		if (to != null && ((EObject) to).eIsProxy()) {
			InternalEObject oldTo = (InternalEObject) to;
			to = (epibase.Compartment) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EpidimensionsbasePackage.CONTACT__TO,
							oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public epibase.Compartment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTo(epibase.Compartment newTo) {
		epibase.Compartment oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidimensionsbasePackage.CONTACT__TO, oldTo, to));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public epibase.Compartment getContact() {
		if (contact != null && ((EObject) contact).eIsProxy()) {
			InternalEObject oldContact = (InternalEObject) contact;
			contact = (epibase.Compartment) eResolveProxy(oldContact);
			if (contact != oldContact) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, EpidimensionsbasePackage.CONTACT__CONTACT,
							oldContact, contact));
			}
		}
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public epibase.Compartment basicGetContact() {
		return contact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContact(epibase.Compartment newContact) {
		epibase.Compartment oldContact = contact;
		contact = newContact;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidimensionsbasePackage.CONTACT__CONTACT, oldContact,
					contact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpidimensionsbasePackage.CONTACT__FROM:
			if (resolve)
				return getFrom();
			return basicGetFrom();
		case EpidimensionsbasePackage.CONTACT__TO:
			if (resolve)
				return getTo();
			return basicGetTo();
		case EpidimensionsbasePackage.CONTACT__CONTACT:
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
		case EpidimensionsbasePackage.CONTACT__FROM:
			setFrom((epibase.Compartment) newValue);
			return;
		case EpidimensionsbasePackage.CONTACT__TO:
			setTo((epibase.Compartment) newValue);
			return;
		case EpidimensionsbasePackage.CONTACT__CONTACT:
			setContact((epibase.Compartment) newValue);
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
		case EpidimensionsbasePackage.CONTACT__FROM:
			setFrom((epibase.Compartment) null);
			return;
		case EpidimensionsbasePackage.CONTACT__TO:
			setTo((epibase.Compartment) null);
			return;
		case EpidimensionsbasePackage.CONTACT__CONTACT:
			setContact((epibase.Compartment) null);
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
		case EpidimensionsbasePackage.CONTACT__FROM:
			return from != null;
		case EpidimensionsbasePackage.CONTACT__TO:
			return to != null;
		case EpidimensionsbasePackage.CONTACT__CONTACT:
			return contact != null;
		}
		return super.eIsSet(featureID);
	}

} //ContactImpl
