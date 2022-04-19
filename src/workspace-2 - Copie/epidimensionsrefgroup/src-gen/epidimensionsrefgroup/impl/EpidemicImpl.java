/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsrefgroup.Epidemic;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
import epidimensionsrefgroup.IDimension;
import epidimensionsrefgroup.IDimensionRecursiveCompartmentAppenderVisitor;
import epidimensionsrefgroup.IDimensionRecursiveFlowAppenderVisitor;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.common.util.BasicEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.impl.EpidemicImpl#getIdimension <em>Idimension</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EpidemicImpl extends epidimensionsbase.impl.EpidemicImpl implements Epidemic {

	@Override
	public EList<epidimensionsbase.Dimension> getDimension() {
		EList<epidimensionsbase.Dimension> l = new BasicEList<epidimensionsbase.Dimension>();

		IDimensionRecursiveCompartmentAppenderVisitor cv = new IDimensionRecursiveCompartmentAppenderVisitor();
		IDimensionRecursiveFlowAppenderVisitor fv = new IDimensionRecursiveFlowAppenderVisitor();
		
		for (epidimensionsrefgroup.IDimension d : getIdimension()) {
			epidimensionsrefgroup.IDimension cp = EcoreUtil.copy(d);
			
			cp.accept(cv);
			EList<epidimensionsbase.Compartment> cl = cv.data();
			cp.getCompartment().removeAll(cp.getCompartment());
			for (epidimensionsbase.Compartment c : cl)
				cp.getCompartment().add(EcoreUtil.copy(c));
			
			cp.accept(fv);
			EList<epidimensionsbase.Flow> fl = fv.data();
			cp.getFlow().removeAll(cp.getFlow());
			for (epidimensionsbase.Flow f : fl)
				cp.getFlow().add(EcoreUtil.copy(f));
			
			l.add(cp);
		}
		return l;
	}

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
	protected EpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsrefgroupPackage.Literals.EPIDEMIC;
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
					EpidimensionsrefgroupPackage.EPIDEMIC__IDIMENSION);
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
		case EpidimensionsrefgroupPackage.EPIDEMIC__IDIMENSION:
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
		case EpidimensionsrefgroupPackage.EPIDEMIC__IDIMENSION:
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
		case EpidimensionsrefgroupPackage.EPIDEMIC__IDIMENSION:
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
		case EpidimensionsrefgroupPackage.EPIDEMIC__IDIMENSION:
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
		case EpidimensionsrefgroupPackage.EPIDEMIC__IDIMENSION:
			return idimension != null && !idimension.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //EpidemicImpl
