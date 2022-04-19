/**
 */
package epidimensionsrefgroup.impl;

import epidimensionsbase.Dimension;
import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsbase.Flow;

import epidimensionsrefgroup.Compartment;
import epidimensionsrefgroup.EpidimensionsrefgroupPackage;
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
 * An implementation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epidimensionsrefgroup.impl.CompartmentImpl#getCompartment <em>Compartment</em>}</li>
 *   <li>{@link epidimensionsrefgroup.impl.CompartmentImpl#getFlow <em>Flow</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentImpl extends epidimensionsbase.impl.CompartmentImpl implements Compartment {

	@Override
	public void accept(IDimensionVisitor<?> v) {
		v.visit(this);
	}
	
	/**
	 * The cached value of the '{@link #getCompartment() <em>Compartment</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartment()
	 * @generated
	 * @ordered
	 */
	protected EList<epidimensionsbase.Compartment> compartment;

	/**
	 * The cached value of the '{@link #getFlow() <em>Flow</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFlow()
	 * @generated
	 * @ordered
	 */
	protected EList<Flow> flow;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsrefgroupPackage.Literals.COMPARTMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<epidimensionsbase.Compartment> getCompartment() {
		if (compartment == null) {
			compartment = new EObjectContainmentEList<epidimensionsbase.Compartment>(
					epidimensionsbase.Compartment.class, this, EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT);
		}
		return compartment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Flow> getFlow() {
		if (flow == null) {
			flow = new EObjectContainmentEList<Flow>(Flow.class, this, EpidimensionsrefgroupPackage.COMPARTMENT__FLOW);
		}
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT:
			return ((InternalEList<?>) getCompartment()).basicRemove(otherEnd, msgs);
		case EpidimensionsrefgroupPackage.COMPARTMENT__FLOW:
			return ((InternalEList<?>) getFlow()).basicRemove(otherEnd, msgs);
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
		case EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT:
			return getCompartment();
		case EpidimensionsrefgroupPackage.COMPARTMENT__FLOW:
			return getFlow();
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
		case EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT:
			getCompartment().clear();
			getCompartment().addAll((Collection<? extends epidimensionsbase.Compartment>) newValue);
			return;
		case EpidimensionsrefgroupPackage.COMPARTMENT__FLOW:
			getFlow().clear();
			getFlow().addAll((Collection<? extends Flow>) newValue);
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
		case EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT:
			getCompartment().clear();
			return;
		case EpidimensionsrefgroupPackage.COMPARTMENT__FLOW:
			getFlow().clear();
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
		case EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT:
			return compartment != null && !compartment.isEmpty();
		case EpidimensionsrefgroupPackage.COMPARTMENT__FLOW:
			return flow != null && !flow.isEmpty();
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
		if (baseClass == Dimension.class) {
			switch (derivedFeatureID) {
			case EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT:
				return EpidimensionsbasePackage.DIMENSION__COMPARTMENT;
			case EpidimensionsrefgroupPackage.COMPARTMENT__FLOW:
				return EpidimensionsbasePackage.DIMENSION__FLOW;
			default:
				return -1;
			}
		}
		if (baseClass == IDimension.class) {
			switch (derivedFeatureID) {
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
		if (baseClass == Dimension.class) {
			switch (baseFeatureID) {
			case EpidimensionsbasePackage.DIMENSION__COMPARTMENT:
				return EpidimensionsrefgroupPackage.COMPARTMENT__COMPARTMENT;
			case EpidimensionsbasePackage.DIMENSION__FLOW:
				return EpidimensionsrefgroupPackage.COMPARTMENT__FLOW;
			default:
				return -1;
			}
		}
		if (baseClass == IDimension.class) {
			switch (baseFeatureID) {
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //CompartmentImpl
