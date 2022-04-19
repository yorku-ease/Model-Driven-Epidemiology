/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsrefgroup.Dimension;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.Flow;
import epidimensionsrefgroup.IDimension;
import epidimensionsrefgroup.IDimensionVisitor;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.impl.DimensionImpl#getFlow_2 <em>Flow 2</em>}</li>
 *   <li>{@link epidimensionsrefgroup.impl.DimensionImpl#getIdimension <em>Idimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DimensionImpl extends epidimensionsbase.impl.DimensionImpl implements Dimension {

	@Override
	public void accept(IDimensionVisitor<?> v) {
		v.visit(this);
	}

	/**
	 * The cached value of the '{@link #getFlow_2() <em>Flow 2</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlow_2()
	 * @generated
	 * @ordered
	 */
	protected EList<Flow> flow_2;

	/**
	 * The cached value of the '{@link #getIdimension() <em>Idimension</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdimension()
	 * @generated
	 * @ordered
	 */
	protected EList<IDimension> idimension;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsrefgroupPackage.Literals.DIMENSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Flow> getFlow_2() {
		if (flow_2 == null) {
			flow_2 = new EObjectContainmentEList<Flow>(Flow.class, this,
					EpidimensionsrefgroupPackage.DIMENSION__FLOW_2);
		}
		return flow_2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IDimension> getIdimension() {
		if (idimension == null) {
			idimension = new EObjectContainmentEList<IDimension>(IDimension.class, this,
					EpidimensionsrefgroupPackage.DIMENSION__IDIMENSION);
		}
		return idimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EpidimensionsrefgroupPackage.DIMENSION__FLOW_2:
			return ((InternalEList<?>) getFlow_2()).basicRemove(otherEnd, msgs);
		case EpidimensionsrefgroupPackage.DIMENSION__IDIMENSION:
			return ((InternalEList<?>) getIdimension()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpidimensionsrefgroupPackage.DIMENSION__FLOW_2:
			return getFlow_2();
		case EpidimensionsrefgroupPackage.DIMENSION__IDIMENSION:
			return getIdimension();
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
		case EpidimensionsrefgroupPackage.DIMENSION__FLOW_2:
			getFlow_2().clear();
			getFlow_2().addAll((Collection<? extends Flow>) newValue);
			return;
		case EpidimensionsrefgroupPackage.DIMENSION__IDIMENSION:
			getIdimension().clear();
			getIdimension().addAll((Collection<? extends IDimension>) newValue);
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
		case EpidimensionsrefgroupPackage.DIMENSION__FLOW_2:
			getFlow_2().clear();
			return;
		case EpidimensionsrefgroupPackage.DIMENSION__IDIMENSION:
			getIdimension().clear();
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
		case EpidimensionsrefgroupPackage.DIMENSION__FLOW_2:
			return flow_2 != null && !flow_2.isEmpty();
		case EpidimensionsrefgroupPackage.DIMENSION__IDIMENSION:
			return idimension != null && !idimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DimensionImpl
