/**
 */
package epimodel.util;

import epimodel.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see epimodel.EpimodelPackage
 * @generated
 */
public class EpimodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static EpimodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EpimodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = EpimodelPackage.eINSTANCE;
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
	protected EpimodelSwitch<Adapter> modelSwitch = new EpimodelSwitch<Adapter>() {
		@Override
		public Adapter caseEpidemic(Epidemic object) {
			return createEpidemicAdapter();
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
	 * Creates a new adapter for an object of class '{@link epimodel.Epidemic <em>Epidemic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.Epidemic
	 * @generated
	 */
	public Adapter createEpidemicAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodel.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.Dimension
	 * @generated
	 */
	public Adapter createDimensionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodel.MetaCompartment <em>Meta Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.MetaCompartment
	 * @generated
	 */
	public Adapter createMetaCompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodel.MetaFlow <em>Meta Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.MetaFlow
	 * @generated
	 */
	public Adapter createMetaFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodel.MetaContact <em>Meta Contact</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.MetaContact
	 * @generated
	 */
	public Adapter createMetaContactAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodel.MetaRate <em>Meta Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.MetaRate
	 * @generated
	 */
	public Adapter createMetaRateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link epimodel.MetaBatch <em>Meta Batch</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see epimodel.MetaBatch
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

} //EpimodelAdapterFactory
