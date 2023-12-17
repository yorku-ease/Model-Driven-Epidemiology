/**
 */
package src_ph.PhysicalEpidemicRoot.impl;

import src_ph.PhysicalEpidemicRoot.EquationTemplate;
import src_ph.PhysicalEpidemicRoot.PhysicalEpidemicRootPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Equation Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link PhysicalEpidemicRoot.impl.EquationTemplateImpl#getSourceParameters <em>Source Parameters</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.impl.EquationTemplateImpl#getContactParameters <em>Contact Parameters</em>}</li>
 *   <li>{@link PhysicalEpidemicRoot.impl.EquationTemplateImpl#getContactCompartment <em>Contact Compartment</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EquationTemplateImpl extends EObjectImpl implements EquationTemplate {
	/**
	 * The default value of the '{@link #getSourceParameters() <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_PARAMETERS_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getSourceParameters() <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceParameters()
	 * @generated
	 * @ordered
	 */
	protected String sourceParameters = SOURCE_PARAMETERS_EDEFAULT;
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
	 * The default value of the '{@link #getContactCompartment() <em>Contact Compartment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactCompartment()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTACT_COMPARTMENT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getContactCompartment() <em>Contact Compartment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactCompartment()
	 * @generated
	 * @ordered
	 */
	protected String contactCompartment = CONTACT_COMPARTMENT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EquationTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PhysicalEpidemicRootPackage.Literals.EQUATION_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSourceParameters() {
		return sourceParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceParameters(String newSourceParameters) {
		String oldSourceParameters = sourceParameters;
		sourceParameters = newSourceParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__SOURCE_PARAMETERS, oldSourceParameters, sourceParameters));
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
			eNotify(new ENotificationImpl(this, Notification.SET, PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_PARAMETERS, oldContactParameters, contactParameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContactCompartment() {
		return contactCompartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContactCompartment(String newContactCompartment) {
		String oldContactCompartment = contactCompartment;
		contactCompartment = newContactCompartment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_COMPARTMENT, oldContactCompartment, contactCompartment));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__SOURCE_PARAMETERS:
				return getSourceParameters();
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_PARAMETERS:
				return getContactParameters();
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_COMPARTMENT:
				return getContactCompartment();
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
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__SOURCE_PARAMETERS:
				setSourceParameters((String)newValue);
				return;
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_PARAMETERS:
				setContactParameters((String)newValue);
				return;
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_COMPARTMENT:
				setContactCompartment((String)newValue);
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
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__SOURCE_PARAMETERS:
				setSourceParameters(SOURCE_PARAMETERS_EDEFAULT);
				return;
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_PARAMETERS:
				setContactParameters(CONTACT_PARAMETERS_EDEFAULT);
				return;
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_COMPARTMENT:
				setContactCompartment(CONTACT_COMPARTMENT_EDEFAULT);
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
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__SOURCE_PARAMETERS:
				return SOURCE_PARAMETERS_EDEFAULT == null ? sourceParameters != null : !SOURCE_PARAMETERS_EDEFAULT.equals(sourceParameters);
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_PARAMETERS:
				return CONTACT_PARAMETERS_EDEFAULT == null ? contactParameters != null : !CONTACT_PARAMETERS_EDEFAULT.equals(contactParameters);
			case PhysicalEpidemicRootPackage.EQUATION_TEMPLATE__CONTACT_COMPARTMENT:
				return CONTACT_COMPARTMENT_EDEFAULT == null ? contactCompartment != null : !CONTACT_COMPARTMENT_EDEFAULT.equals(contactCompartment);
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
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (sourceParameters: ");
		result.append(sourceParameters);
		result.append(", contactParameters: ");
		result.append(contactParameters);
		result.append(", contactCompartment: ");
		result.append(contactCompartment);
		result.append(')');
		return result.toString();
	}

} //EquationTemplateImpl
