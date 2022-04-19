/**
 */
package epimodelgroup.util;

import epimodelgroup.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see epimodelgroup.EpimodelgroupPackage
 * @generated
 */
public class EpimodelgroupAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EpimodelgroupPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpimodelgroupAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EpimodelgroupPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EpimodelgroupSwitch<Adapter> modelSwitch = new EpimodelgroupSwitch<Adapter>() {
		@Override
		public Adapter caseEpidemic(Epidemic object) {
			return createEpidemicAdapter();
		}

		@Override
		public Adapter caseIDimension(IDimension object) {
			return createIDimensionAdapter();
		}

		@Override
		public Adapter caseDimension(Dimension object) {
			return createDimensionAdapter();
		}

		@Override
		public Adapter caseMetaCompartment(MetaCompartment object) {
			return createMetaCompartmentAdapter();
		}

		@Override
		public Adapter caseMetaFlow(MetaFlow object) {
			return createMetaFlowAdapter();
		}

		@Override
		public Adapter caseDimensionRef(DimensionRef object) {
			return createDimensionRefAdapter();
		}

		@Override
		public Adapter caseMetaContact(MetaContact object) {
			return createMetaContactAdapter();
		}

		@Override
		public Adapter caseMetaRate(MetaRate object) {
			return createMetaRateAdapter();
		}

		@Override
		public Adapter caseMetaBatch(MetaBatch object) {
			return createMetaBatchAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.Epidemic
	 * @generated
	 */
	public Adapter createEpidemicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.IDimension <em>IDimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.IDimension
	 * @generated
	 */
	public Adapter createIDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.MetaCompartment <em>Meta Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.MetaCompartment
	 * @generated
	 */
	public Adapter createMetaCompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.MetaFlow <em>Meta Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.MetaFlow
	 * @generated
	 */
	public Adapter createMetaFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.DimensionRef <em>Dimension Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.DimensionRef
	 * @generated
	 */
	public Adapter createDimensionRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.MetaContact <em>Meta Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.MetaContact
	 * @generated
	 */
	public Adapter createMetaContactAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.MetaRate <em>Meta Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.MetaRate
	 * @generated
	 */
	public Adapter createMetaRateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodelgroup.MetaBatch <em>Meta Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodelgroup.MetaBatch
	 * @generated
	 */
	public Adapter createMetaBatchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //EpimodelgroupAdapterFactory
