/**
 */
package couplingFlow.impl;

import couplingFlow.CouplingFlow;
import couplingFlow.CouplingFlowPackage;
import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Epidemic;
import epimodel.impl.FlowImpl;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Coupling Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link couplingFlow.impl.CouplingFlowImpl#getFirstFrom <em>First From</em>}</li>
 *   <li>{@link couplingFlow.impl.CouplingFlowImpl#getSecondFrom <em>Second From</em>}</li>
 *   <li>{@link couplingFlow.impl.CouplingFlowImpl#getSecondTo <em>Second To</em>}</li>
 *   <li>{@link couplingFlow.impl.CouplingFlowImpl#getFirstTo <em>First To</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CouplingFlowImpl extends FlowImpl implements CouplingFlow {

	@Override
	public List<PhysicalFlow> getPhysicalFlows(Epidemic epidemic) {
		List<PhysicalCompartment> equationCompartments = 
				Arrays.asList(firstFrom, secondFrom)
					.stream()
					.map(CompartmentWrapper::getCompartment)
					.map(Compartment::getPhysicalCompartments)
					.flatMap(List::stream)
					.collect(Collectors.toList());
		List<PhysicalCompartment> affectedCompartments = 
				Arrays.asList(firstFrom, secondFrom, firstTo, secondTo)
					.stream()
					.map(CompartmentWrapper::getCompartment)
					.map(Compartment::getPhysicalCompartments)
					.flatMap(List::stream)
					.collect(Collectors.toList());
		List<Float> coefficients = Arrays.asList(-1f, -1f, 1f, 1f);
		String equation = "(* $0 $1)";
		List<String> requiredOperators = Arrays.asList("*");
		return Arrays.asList(
				new PhysicalFlow(
						Arrays.asList(
								new PhysicalFlowEquation(
									equationCompartments,
									affectedCompartments,
									coefficients,
									equation,
									requiredOperators
							))));
	}
	
	/**
	 * The cached value of the '{@link #getFirstFrom() <em>First From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstFrom()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper firstFrom;

	/**
	 * The cached value of the '{@link #getSecondFrom() <em>Second From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondFrom()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper secondFrom;

	/**
	 * The cached value of the '{@link #getSecondTo() <em>Second To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondTo()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper secondTo;

	/**
	 * The cached value of the '{@link #getFirstTo() <em>First To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFirstTo()
	 * @generated
	 * @ordered
	 */
	protected CompartmentWrapper firstTo;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CouplingFlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CouplingFlowPackage.Literals.COUPLING_FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getFirstFrom() {
		if (firstFrom != null && firstFrom.eIsProxy()) {
			InternalEObject oldFirstFrom = (InternalEObject) firstFrom;
			firstFrom = (CompartmentWrapper) eResolveProxy(oldFirstFrom);
			if (firstFrom != oldFirstFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CouplingFlowPackage.COUPLING_FLOW__FIRST_FROM, oldFirstFrom, firstFrom));
			}
		}
		return firstFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentWrapper basicGetFirstFrom() {
		return firstFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFirstFrom(CompartmentWrapper newFirstFrom) {
		CompartmentWrapper oldFirstFrom = firstFrom;
		firstFrom = newFirstFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CouplingFlowPackage.COUPLING_FLOW__FIRST_FROM,
					oldFirstFrom, firstFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getSecondFrom() {
		if (secondFrom != null && secondFrom.eIsProxy()) {
			InternalEObject oldSecondFrom = (InternalEObject) secondFrom;
			secondFrom = (CompartmentWrapper) eResolveProxy(oldSecondFrom);
			if (secondFrom != oldSecondFrom) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CouplingFlowPackage.COUPLING_FLOW__SECOND_FROM, oldSecondFrom, secondFrom));
			}
		}
		return secondFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentWrapper basicGetSecondFrom() {
		return secondFrom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondFrom(CompartmentWrapper newSecondFrom) {
		CompartmentWrapper oldSecondFrom = secondFrom;
		secondFrom = newSecondFrom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CouplingFlowPackage.COUPLING_FLOW__SECOND_FROM,
					oldSecondFrom, secondFrom));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getSecondTo() {
		if (secondTo != null && secondTo.eIsProxy()) {
			InternalEObject oldSecondTo = (InternalEObject) secondTo;
			secondTo = (CompartmentWrapper) eResolveProxy(oldSecondTo);
			if (secondTo != oldSecondTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CouplingFlowPackage.COUPLING_FLOW__SECOND_TO, oldSecondTo, secondTo));
			}
		}
		return secondTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentWrapper basicGetSecondTo() {
		return secondTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecondTo(CompartmentWrapper newSecondTo) {
		CompartmentWrapper oldSecondTo = secondTo;
		secondTo = newSecondTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CouplingFlowPackage.COUPLING_FLOW__SECOND_TO,
					oldSecondTo, secondTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CompartmentWrapper getFirstTo() {
		if (firstTo != null && firstTo.eIsProxy()) {
			InternalEObject oldFirstTo = (InternalEObject) firstTo;
			firstTo = (CompartmentWrapper) eResolveProxy(oldFirstTo);
			if (firstTo != oldFirstTo) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							CouplingFlowPackage.COUPLING_FLOW__FIRST_TO, oldFirstTo, firstTo));
			}
		}
		return firstTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompartmentWrapper basicGetFirstTo() {
		return firstTo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFirstTo(CompartmentWrapper newFirstTo) {
		CompartmentWrapper oldFirstTo = firstTo;
		firstTo = newFirstTo;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CouplingFlowPackage.COUPLING_FLOW__FIRST_TO,
					oldFirstTo, firstTo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_FROM:
			if (resolve)
				return getFirstFrom();
			return basicGetFirstFrom();
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_FROM:
			if (resolve)
				return getSecondFrom();
			return basicGetSecondFrom();
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_TO:
			if (resolve)
				return getSecondTo();
			return basicGetSecondTo();
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_TO:
			if (resolve)
				return getFirstTo();
			return basicGetFirstTo();
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
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_FROM:
			setFirstFrom((CompartmentWrapper) newValue);
			return;
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_FROM:
			setSecondFrom((CompartmentWrapper) newValue);
			return;
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_TO:
			setSecondTo((CompartmentWrapper) newValue);
			return;
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_TO:
			setFirstTo((CompartmentWrapper) newValue);
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
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_FROM:
			setFirstFrom((CompartmentWrapper) null);
			return;
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_FROM:
			setSecondFrom((CompartmentWrapper) null);
			return;
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_TO:
			setSecondTo((CompartmentWrapper) null);
			return;
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_TO:
			setFirstTo((CompartmentWrapper) null);
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
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_FROM:
			return firstFrom != null;
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_FROM:
			return secondFrom != null;
		case CouplingFlowPackage.COUPLING_FLOW__SECOND_TO:
			return secondTo != null;
		case CouplingFlowPackage.COUPLING_FLOW__FIRST_TO:
			return firstTo != null;
		}
		return super.eIsSet(featureID);
	}

} //CouplingFlowImpl
