/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Parameter;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.impl.ParameterImpl#getName <em>Name</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.ParameterImpl#isDepends_on_source <em>Depends on source</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.ParameterImpl#isDepends_on_sink <em>Depends on sink</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterImpl extends MinimalEObjectImpl.Container implements Parameter {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isDepends_on_source() <em>Depends on source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDepends_on_source()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPENDS_ON_SOURCE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDepends_on_source() <em>Depends on source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDepends_on_source()
	 * @generated
	 * @ordered
	 */
	protected boolean depends_on_source = DEPENDS_ON_SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDepends_on_sink() <em>Depends on sink</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDepends_on_sink()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEPENDS_ON_SINK_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDepends_on_sink() <em>Depends on sink</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDepends_on_sink()
	 * @generated
	 * @ordered
	 */
	protected boolean depends_on_sink = DEPENDS_ON_SINK_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.PARAMETER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.PARAMETER__NAME, oldName,
					name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDepends_on_source() {
		return depends_on_source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDepends_on_source(boolean newDepends_on_source) {
		boolean oldDepends_on_source = depends_on_source;
		depends_on_source = newDepends_on_source;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SOURCE, oldDepends_on_source, depends_on_source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDepends_on_sink() {
		return depends_on_sink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDepends_on_sink(boolean newDepends_on_sink) {
		boolean oldDepends_on_sink = depends_on_sink;
		depends_on_sink = newDepends_on_sink;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SINK, oldDepends_on_sink, depends_on_sink));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BatchRateContactFlowPackage.PARAMETER__NAME:
			return getName();
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SOURCE:
			return isDepends_on_source();
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SINK:
			return isDepends_on_sink();
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
		case BatchRateContactFlowPackage.PARAMETER__NAME:
			setName((String) newValue);
			return;
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SOURCE:
			setDepends_on_source((Boolean) newValue);
			return;
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SINK:
			setDepends_on_sink((Boolean) newValue);
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
		case BatchRateContactFlowPackage.PARAMETER__NAME:
			setName(NAME_EDEFAULT);
			return;
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SOURCE:
			setDepends_on_source(DEPENDS_ON_SOURCE_EDEFAULT);
			return;
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SINK:
			setDepends_on_sink(DEPENDS_ON_SINK_EDEFAULT);
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
		case BatchRateContactFlowPackage.PARAMETER__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SOURCE:
			return depends_on_source != DEPENDS_ON_SOURCE_EDEFAULT;
		case BatchRateContactFlowPackage.PARAMETER__DEPENDS_ON_SINK:
			return depends_on_sink != DEPENDS_ON_SINK_EDEFAULT;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", depends_on_source: ");
		result.append(depends_on_source);
		result.append(", depends_on_sink: ");
		result.append(depends_on_sink);
		result.append(')');
		return result.toString();
	}

} //ParameterImpl
