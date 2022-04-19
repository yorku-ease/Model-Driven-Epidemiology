/**
 */
package epidimensionsrefgroup.util;

import epidimensionsrefgroup.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see epidimensionsrefgroup.EpidimensionsrefgroupPackage
 * @generated
 */
public class EpidimensionsrefgroupAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EpidimensionsrefgroupPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpidimensionsrefgroupAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EpidimensionsrefgroupPackage.eINSTANCE;
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
	protected EpidimensionsrefgroupSwitch<Adapter> modelSwitch = new EpidimensionsrefgroupSwitch<Adapter>() {
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
		public Adapter caseDimensionRef(DimensionRef object) {
			return createDimensionRefAdapter();
		}

		@Override
		public Adapter caseCompartment(Compartment object) {
			return createCompartmentAdapter();
		}

		@Override
		public Adapter caseFlow(Flow object) {
			return createFlowAdapter();
		}

		@Override
		public Adapter caseRate(Rate object) {
			return createRateAdapter();
		}

		@Override
		public Adapter caseBatch(Batch object) {
			return createBatchAdapter();
		}

		@Override
		public Adapter caseContact(Contact object) {
			return createContactAdapter();
		}

		@Override
		public Adapter caseEpibase_Epidemic(epibase.Epidemic object) {
			return createEpibase_EpidemicAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Epidemic(epidimensionsbase.Epidemic object) {
			return createEpidimensionsbase_EpidemicAdapter();
		}

		@Override
		public Adapter caseEpibase_Compartment(epibase.Compartment object) {
			return createEpibase_CompartmentAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Dimension(epidimensionsbase.Dimension object) {
			return createEpidimensionsbase_DimensionAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Compartment(epidimensionsbase.Compartment object) {
			return createEpidimensionsbase_CompartmentAdapter();
		}

		@Override
		public Adapter caseEpibase_Flow(epibase.Flow object) {
			return createEpibase_FlowAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Flow(epidimensionsbase.Flow object) {
			return createEpidimensionsbase_FlowAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Rate(epidimensionsbase.Rate object) {
			return createEpidimensionsbase_RateAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Batch(epidimensionsbase.Batch object) {
			return createEpidimensionsbase_BatchAdapter();
		}

		@Override
		public Adapter caseEpidimensionsbase_Contact(epidimensionsbase.Contact object) {
			return createEpidimensionsbase_ContactAdapter();
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
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Epidemic
	 * @generated
	 */
	public Adapter createEpidemicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.IDimension <em>IDimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.IDimension
	 * @generated
	 */
	public Adapter createIDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.DimensionRef <em>Dimension Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.DimensionRef
	 * @generated
	 */
	public Adapter createDimensionRefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epibase.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epibase.Epidemic
	 * @generated
	 */
	public Adapter createEpibase_EpidemicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Epidemic
	 * @generated
	 */
	public Adapter createEpidimensionsbase_EpidemicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epibase.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epibase.Compartment
	 * @generated
	 */
	public Adapter createEpibase_CompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Dimension
	 * @generated
	 */
	public Adapter createEpidimensionsbase_DimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Compartment
	 * @generated
	 */
	public Adapter createEpidimensionsbase_CompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epibase.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epibase.Flow
	 * @generated
	 */
	public Adapter createEpibase_FlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Flow
	 * @generated
	 */
	public Adapter createEpidimensionsbase_FlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Rate
	 * @generated
	 */
	public Adapter createEpidimensionsbase_RateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Batch
	 * @generated
	 */
	public Adapter createEpidimensionsbase_BatchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsbase.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsbase.Contact
	 * @generated
	 */
	public Adapter createEpidimensionsbase_ContactAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Compartment
	 * @generated
	 */
	public Adapter createCompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Flow
	 * @generated
	 */
	public Adapter createFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Rate <em>Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Rate
	 * @generated
	 */
	public Adapter createRateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Batch <em>Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Batch
	 * @generated
	 */
	public Adapter createBatchAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epidimensionsrefgroup.Contact <em>Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epidimensionsrefgroup.Contact
	 * @generated
	 */
	public Adapter createContactAdapter() {
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

} //EpidimensionsrefgroupAdapterFactory
