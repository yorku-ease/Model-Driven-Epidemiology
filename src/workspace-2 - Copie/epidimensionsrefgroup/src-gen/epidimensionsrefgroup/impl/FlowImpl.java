/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.Flow;
import epidimensionsrefgroup.IDimension;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.impl.FlowImpl#getFrom_dimension <em>From dimension</em>}</li>
 *   <li>{@link epidimensionsrefgroup.impl.FlowImpl#getTo_dimension <em>To dimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class FlowImpl extends epidimensionsbase.impl.FlowImpl implements Flow {
	
	@Override
	public epidimensionsbase.Compartment getFrom() {
		return getFrom_dimension();
	}

	
	@Override
	public epidimensionsbase.Compartment getTo() {
		return getTo_dimension();
	}
	
	/**
	 * The cached value of the '{@link #getFrom_dimension() <em>From dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrom_dimension()
	 * @generated
	 * @ordered
	 */
	protected IDimension from_dimension;

	/**
	 * The cached value of the '{@link #getTo_dimension() <em>To dimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTo_dimension()
	 * @generated
	 * @ordered
	 */
	protected IDimension to_dimension;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsrefgroupPackage.Literals.FLOW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IDimension getFrom_dimension() {
		if (from_dimension != null && from_dimension.eIsProxy()) {
			InternalEObject oldFrom_dimension = (InternalEObject) from_dimension;
			from_dimension = (IDimension) eResolveProxy(oldFrom_dimension);
			if (from_dimension != oldFrom_dimension) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EpidimensionsrefgroupPackage.FLOW__FROM_DIMENSION, oldFrom_dimension, from_dimension));
			}
		}
		return from_dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDimension basicGetFrom_dimension() {
		return from_dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setFrom_dimension(IDimension newFrom_dimension) {
		IDimension oldFrom_dimension = from_dimension;
		from_dimension = newFrom_dimension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidimensionsrefgroupPackage.FLOW__FROM_DIMENSION,
					oldFrom_dimension, from_dimension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IDimension getTo_dimension() {
		if (to_dimension != null && to_dimension.eIsProxy()) {
			InternalEObject oldTo_dimension = (InternalEObject) to_dimension;
			to_dimension = (IDimension) eResolveProxy(oldTo_dimension);
			if (to_dimension != oldTo_dimension) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EpidimensionsrefgroupPackage.FLOW__TO_DIMENSION, oldTo_dimension, to_dimension));
			}
		}
		return to_dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDimension basicGetTo_dimension() {
		return to_dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTo_dimension(IDimension newTo_dimension) {
		IDimension oldTo_dimension = to_dimension;
		to_dimension = newTo_dimension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EpidimensionsrefgroupPackage.FLOW__TO_DIMENSION,
					oldTo_dimension, to_dimension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpidimensionsrefgroupPackage.FLOW__FROM_DIMENSION:
			if (resolve)
				return getFrom_dimension();
			return basicGetFrom_dimension();
		case EpidimensionsrefgroupPackage.FLOW__TO_DIMENSION:
			if (resolve)
				return getTo_dimension();
			return basicGetTo_dimension();
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
		case EpidimensionsrefgroupPackage.FLOW__FROM_DIMENSION:
			setFrom_dimension((IDimension) newValue);
			return;
		case EpidimensionsrefgroupPackage.FLOW__TO_DIMENSION:
			setTo_dimension((IDimension) newValue);
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
		case EpidimensionsrefgroupPackage.FLOW__FROM_DIMENSION:
			setFrom_dimension((IDimension) null);
			return;
		case EpidimensionsrefgroupPackage.FLOW__TO_DIMENSION:
			setTo_dimension((IDimension) null);
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
		case EpidimensionsrefgroupPackage.FLOW__FROM_DIMENSION:
			return from_dimension != null;
		case EpidimensionsrefgroupPackage.FLOW__TO_DIMENSION:
			return to_dimension != null;
		}
		return super.eIsSet(featureID);
	}

} //FlowImpl
