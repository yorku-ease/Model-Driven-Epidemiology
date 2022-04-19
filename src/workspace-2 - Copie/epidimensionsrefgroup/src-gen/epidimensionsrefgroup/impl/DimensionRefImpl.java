/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsbase.impl.DimensionImpl;
import epidimensionsrefgroup.DimensionRef;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.IDimension;
import epidimensionsrefgroup.IDimensionVisitor;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dimension Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.impl.DimensionRefImpl#getIdimension <em>Idimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionRefImpl extends DimensionImpl implements DimensionRef {

	@Override
	public void accept(IDimensionVisitor<?> v) {
		v.visit(this);
	}
	
	/**
	 * The cached value of the '{@link #getIdimension() <em>Idimension</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdimension()
	 * @generated
	 * @ordered
	 */
	protected IDimension idimension;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DimensionRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsrefgroupPackage.Literals.DIMENSION_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IDimension getIdimension() {
		if (idimension != null && idimension.eIsProxy()) {
			InternalEObject oldIdimension = (InternalEObject) idimension;
			idimension = (IDimension) eResolveProxy(oldIdimension);
			if (idimension != oldIdimension) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							EpidimensionsrefgroupPackage.DIMENSION_REF__IDIMENSION, oldIdimension, idimension));
			}
		}
		return idimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDimension basicGetIdimension() {
		return idimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setIdimension(IDimension newIdimension) {
		IDimension oldIdimension = idimension;
		idimension = newIdimension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					EpidimensionsrefgroupPackage.DIMENSION_REF__IDIMENSION, oldIdimension, idimension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpidimensionsrefgroupPackage.DIMENSION_REF__IDIMENSION:
			if (resolve)
				return getIdimension();
			return basicGetIdimension();
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
		case EpidimensionsrefgroupPackage.DIMENSION_REF__IDIMENSION:
			setIdimension((IDimension) newValue);
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
		case EpidimensionsrefgroupPackage.DIMENSION_REF__IDIMENSION:
			setIdimension((IDimension) null);
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
		case EpidimensionsrefgroupPackage.DIMENSION_REF__IDIMENSION:
			return idimension != null;
		}
		return super.eIsSet(featureID);
	}

} //DimensionRefImpl
