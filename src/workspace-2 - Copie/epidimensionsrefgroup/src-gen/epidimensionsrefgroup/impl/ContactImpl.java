/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsbase.Compartment;
import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsrefgroup.Contact;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.IDimension;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import epidimensionsbase.FlowVisitor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.impl.ContactImpl#getContact <em>Contact</em>}</li>
 *   <li>{@link epidimensionsrefgroup.impl.ContactImpl#getContact_dimension <em>Contact dimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ContactImpl extends FlowImpl implements Contact {
	
	/**
	 * The cached value of the '{@link #getContact() <em>Contact</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact()
	 * @generated
	 * @ordered
	 */
	protected Compartment contact;

	@Override
	public void accept(Object visitor) throws Exception {
		((FlowVisitor<?>) visitor).visit((epidimensionsbase.Contact) this);
	}

	/**
	 * The cached value of the '{@link #getContact_dimension() <em>Contact dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContact_dimension()
	 * @generated
	 * @ordered
	 */
	protected IDimension contact_dimension;

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
		return EpidimensionsrefgroupPackage.Literals.CONTACT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public Compartment getContact() {
		return getContact_dimension();
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
			eNotify(new ENotificationImpl(this, Notification.SET, EpidimensionsrefgroupPackage.CONTACT__CONTACT,
					oldContact, contact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IDimension getContact_dimension() {
		if (contact_dimension != null && contact_dimension.eIsProxy()) {
			InternalEObject oldContact_dimension = (InternalEObject) contact_dimension;
			contact_dimension = (IDimension) eResolveProxy(oldContact_dimension);
			if (contact_dimension != oldContact_dimension) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EpidimensionsrefgroupPackage.CONTACT__CONTACT_DIMENSION, oldContact_dimension,
							contact_dimension));
			}
		}
		return contact_dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDimension basicGetContact_dimension() {
		return contact_dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContact_dimension(IDimension newContact_dimension) {
		IDimension oldContact_dimension = contact_dimension;
		contact_dimension = newContact_dimension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EpidimensionsrefgroupPackage.CONTACT__CONTACT_DIMENSION, oldContact_dimension, contact_dimension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT:
			if (resolve)
				return getContact();
			return basicGetContact();
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT_DIMENSION:
			if (resolve)
				return getContact_dimension();
			return basicGetContact_dimension();
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
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT:
			setContact((Compartment) newValue);
			return;
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT_DIMENSION:
			setContact_dimension((IDimension) newValue);
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
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT:
			setContact((Compartment) null);
			return;
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT_DIMENSION:
			setContact_dimension((IDimension) null);
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
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT:
			return contact != null;
		case EpidimensionsrefgroupPackage.CONTACT__CONTACT_DIMENSION:
			return contact_dimension != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == epidimensionsbase.Contact.class) {
			switch (derivedFeatureID) {
			case EpidimensionsrefgroupPackage.CONTACT__CONTACT:
				return EpidimensionsbasePackage.CONTACT__CONTACT;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == epidimensionsbase.Contact.class) {
			switch (baseFeatureID) {
			case EpidimensionsbasePackage.CONTACT__CONTACT:
				return EpidimensionsrefgroupPackage.CONTACT__CONTACT;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ContactImpl
