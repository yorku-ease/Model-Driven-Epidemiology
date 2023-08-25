/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.FromToFlow;
import epimodel.Compartment;

import epimodel.impl.FlowImpl;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>From To Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getFrom <em>From</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getTo <em>To</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getSourceParameters <em>Source Parameters</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getSinkParameters <em>Sink Parameters</em>}</li>
 *   <li>{@link batchRateContactFlow.impl.FromToFlowImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FromToFlowImpl extends FlowImpl implements FromToFlow {
	
	String get_from_to_parameters() {
		String base_template = " (parameter {} " + getId() + " $)";
		String _parameters = "";
		{
			String parameter_template = base_template.replace("$", "");
			if (parameters != null)
				for (String p : parameters.split(","))
					_parameters += parameter_template.replace("{}", p);
		}
		{
			String source_parameter_template = base_template.replace("$", "$0");
			if (sourceParameters != null)
				for (String p : sourceParameters.split(","))
					_parameters += source_parameter_template.replace("{}", p);
		}
		{
			String sink_parameter_template = base_template.replace("$", "$1");
			if (sinkParameters != null)
				for (String p : sinkParameters.split(","))
					_parameters += sink_parameter_template.replace("{}", p);
		}
		if (_parameters.equals(""))
			throw new RuntimeException();
		return _parameters;
	}

	@Override
	public void edit(Shell shell, List<Control> controls) {
		System.out.println(getId());
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

	/**
	 * The cached value of the '{@link #getFrom() <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom()
	 * @generated
	 * @ordered
	 */
	protected Compartment from;

	/**
	 * The cached value of the '{@link #getTo() <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo()
	 * @generated
	 * @ordered
	 */
	protected Compartment to;

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
	 * The default value of the '{@link #getSinkParameters() <em>Sink Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSinkParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String SINK_PARAMETERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSinkParameters() <em>Sink Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSinkParameters()
	 * @generated
	 * @ordered
	 */
	protected String sinkParameters = SINK_PARAMETERS_EDEFAULT;

	/**
	 * The default value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected static final String PARAMETERS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected String parameters = PARAMETERS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FromToFlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.FROM_TO_FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getFrom() {
		if (from != null && from.eIsProxy()) {
			InternalEObject oldFrom = (InternalEObject) from;
			from = (Compartment) eResolveProxy(oldFrom);
			if (from != oldFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BatchRateContactFlowPackage.FROM_TO_FLOW__FROM, oldFrom, from));
			}
		}
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetFrom() {
		return from;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrom(Compartment newFrom) {
		Compartment oldFrom = from;
		from = newFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.FROM_TO_FLOW__FROM,
					oldFrom, from));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Compartment getTo() {
		if (to != null && to.eIsProxy()) {
			InternalEObject oldTo = (InternalEObject) to;
			to = (Compartment) eResolveProxy(oldTo);
			if (to != oldTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							BatchRateContactFlowPackage.FROM_TO_FLOW__TO, oldTo, to));
			}
		}
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Compartment basicGetTo() {
		return to;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTo(Compartment newTo) {
		Compartment oldTo = to;
		to = newTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.FROM_TO_FLOW__TO, oldTo,
					to));
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
			eNotify(new ENotificationImpl(this, Notification.SET,
					BatchRateContactFlowPackage.FROM_TO_FLOW__SOURCE_PARAMETERS, oldSourceParameters,
					sourceParameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSinkParameters() {
		return sinkParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSinkParameters(String newSinkParameters) {
		String oldSinkParameters = sinkParameters;
		sinkParameters = newSinkParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					BatchRateContactFlowPackage.FROM_TO_FLOW__SINK_PARAMETERS, oldSinkParameters, sinkParameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getParameters() {
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParameters(String newParameters) {
		String oldParameters = parameters;
		parameters = newParameters;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETERS,
					oldParameters, parameters));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			if (resolve)
				return getFrom();
			return basicGetFrom();
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			if (resolve)
				return getTo();
			return basicGetTo();
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SOURCE_PARAMETERS:
			return getSourceParameters();
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SINK_PARAMETERS:
			return getSinkParameters();
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETERS:
			return getParameters();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			setFrom((Compartment) newValue);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			setTo((Compartment) newValue);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SOURCE_PARAMETERS:
			setSourceParameters((String) newValue);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SINK_PARAMETERS:
			setSinkParameters((String) newValue);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETERS:
			setParameters((String) newValue);
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
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			setFrom((Compartment) null);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			setTo((Compartment) null);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SOURCE_PARAMETERS:
			setSourceParameters(SOURCE_PARAMETERS_EDEFAULT);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SINK_PARAMETERS:
			setSinkParameters(SINK_PARAMETERS_EDEFAULT);
			return;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETERS:
			setParameters(PARAMETERS_EDEFAULT);
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
		case BatchRateContactFlowPackage.FROM_TO_FLOW__FROM:
			return from != null;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__TO:
			return to != null;
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SOURCE_PARAMETERS:
			return SOURCE_PARAMETERS_EDEFAULT == null ? sourceParameters != null
					: !SOURCE_PARAMETERS_EDEFAULT.equals(sourceParameters);
		case BatchRateContactFlowPackage.FROM_TO_FLOW__SINK_PARAMETERS:
			return SINK_PARAMETERS_EDEFAULT == null ? sinkParameters != null
					: !SINK_PARAMETERS_EDEFAULT.equals(sinkParameters);
		case BatchRateContactFlowPackage.FROM_TO_FLOW__PARAMETERS:
			return PARAMETERS_EDEFAULT == null ? parameters != null : !PARAMETERS_EDEFAULT.equals(parameters);
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
		result.append(" (sourceParameters: ");
		result.append(sourceParameters);
		result.append(", sinkParameters: ");
		result.append(sinkParameters);
		result.append(", parameters: ");
		result.append(parameters);
		result.append(')');
		return result.toString();
	}

} //FromToFlowImpl
