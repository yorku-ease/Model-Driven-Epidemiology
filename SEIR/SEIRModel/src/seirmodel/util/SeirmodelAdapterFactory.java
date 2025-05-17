/**
 */
package seirmodel.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import seirmodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see seirmodel.SeirmodelPackage
 * @generated
 */
public class SeirmodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SeirmodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeirmodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SeirmodelPackage.eINSTANCE;
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
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SeirmodelSwitch<Adapter> modelSwitch =
		new SeirmodelSwitch<Adapter>() {
			@Override
			public Adapter caseCompartment(Compartment object) {
				return createCompartmentAdapter();
			}
			@Override
			public Adapter caseFlow(Flow object) {
				return createFlowAdapter();
			}
			@Override
			public Adapter caseSusceptible(Susceptible object) {
				return createSusceptibleAdapter();
			}
			@Override
			public Adapter caseTreated(Treated object) {
				return createTreatedAdapter();
			}
			@Override
			public Adapter caseSEIRModel(SEIRModel object) {
				return createSEIRModelAdapter();
			}
			@Override
			public Adapter caseAIDS(AIDS object) {
				return createAIDSAdapter();
			}
			@Override
			public Adapter caseHospitalized(Hospitalized object) {
				return createHospitalizedAdapter();
			}
			@Override
			public Adapter caseDeaths(Deaths object) {
				return createDeathsAdapter();
			}
			@Override
			public Adapter caseUntreatedInfectious(UntreatedInfectious object) {
				return createUntreatedInfectiousAdapter();
			}
			@Override
			public Adapter caseVaccinated(Vaccinated object) {
				return createVaccinatedAdapter();
			}
			@Override
			public Adapter caseNoAccess(NoAccess object) {
				return createNoAccessAdapter();
			}
			@Override
			public Adapter caseIsolatedAfterTestingPositive(IsolatedAfterTestingPositive object) {
				return createIsolatedAfterTestingPositiveAdapter();
			}
			@Override
			public Adapter caseExposed(Exposed object) {
				return createExposedAdapter();
			}
			@Override
			public Adapter casePostAcute(PostAcute object) {
				return createPostAcuteAdapter();
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
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Compartment <em>Compartment</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Compartment
	 * @generated
	 */
	public Adapter createCompartmentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Flow <em>Flow</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Flow
	 * @generated
	 */
	public Adapter createFlowAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Susceptible <em>Susceptible</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Susceptible
	 * @generated
	 */
	public Adapter createSusceptibleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Treated <em>Treated</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Treated
	 * @generated
	 */
	public Adapter createTreatedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Exposed <em>Exposed</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Exposed
	 * @generated
	 */
	public Adapter createExposedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.PostAcute <em>Post Acute</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.PostAcute
	 * @generated
	 */
	public Adapter createPostAcuteAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Vaccinated <em>Vaccinated</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Vaccinated
	 * @generated
	 */
	public Adapter createVaccinatedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.NoAccess <em>No Access</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.NoAccess
	 * @generated
	 */
	public Adapter createNoAccessAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.IsolatedAfterTestingPositive <em>Isolated After Testing Positive</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.IsolatedAfterTestingPositive
	 * @generated
	 */
	public Adapter createIsolatedAfterTestingPositiveAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.SEIRModel <em>SEIR Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.SEIRModel
	 * @generated
	 */
	public Adapter createSEIRModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.AIDS <em>AIDS</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.AIDS
	 * @generated
	 */
	public Adapter createAIDSAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Hospitalized <em>Hospitalized</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Hospitalized
	 * @generated
	 */
	public Adapter createHospitalizedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Deaths <em>Deaths</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Deaths
	 * @generated
	 */
	public Adapter createDeathsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.UntreatedInfectious <em>Untreated Infectious</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.UntreatedInfectious
	 * @generated
	 */
	public Adapter createUntreatedInfectiousAdapter() {
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

} //SeirmodelAdapterFactory
