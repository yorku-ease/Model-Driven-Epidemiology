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
			public Adapter caseExposed(Exposed object) {
				return createExposedAdapter();
			}
			@Override
			public Adapter caseExposedNonIsolated(ExposedNonIsolated object) {
				return createExposedNonIsolatedAdapter();
			}
			@Override
			public Adapter caseSymptomatic(Symptomatic object) {
				return createSymptomaticAdapter();
			}
			@Override
			public Adapter caseAsymptomatic(Asymptomatic object) {
				return createAsymptomaticAdapter();
			}
			@Override
			public Adapter caseRecovered(Recovered object) {
				return createRecoveredAdapter();
			}
			@Override
			public Adapter caseExposedIsolated(ExposedIsolated object) {
				return createExposedIsolatedAdapter();
			}
			@Override
			public Adapter caseInfectious(Infectious object) {
				return createInfectiousAdapter();
			}
			@Override
			public Adapter caseSEIRModel(SEIRModel object) {
				return createSEIRModelAdapter();
			}
			@Override
			public Adapter casePreclinical(Preclinical object) {
				return createPreclinicalAdapter();
			}
			@Override
			public Adapter caseMild(Mild object) {
				return createMildAdapter();
			}
			@Override
			public Adapter caseSevere(Severe object) {
				return createSevereAdapter();
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
			public Adapter caseTest(Test object) {
				return createTestAdapter();
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
	 * Creates a new adapter for an object of class '{@link seirmodel.ExposedNonIsolated <em>Exposed Non Isolated</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.ExposedNonIsolated
	 * @generated
	 */
	public Adapter createExposedNonIsolatedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Symptomatic <em>Symptomatic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Symptomatic
	 * @generated
	 */
	public Adapter createSymptomaticAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Asymptomatic <em>Asymptomatic</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Asymptomatic
	 * @generated
	 */
	public Adapter createAsymptomaticAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Recovered <em>Recovered</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Recovered
	 * @generated
	 */
	public Adapter createRecoveredAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.ExposedIsolated <em>Exposed Isolated</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.ExposedIsolated
	 * @generated
	 */
	public Adapter createExposedIsolatedAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Infectious <em>Infectious</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Infectious
	 * @generated
	 */
	public Adapter createInfectiousAdapter() {
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
	 * Creates a new adapter for an object of class '{@link seirmodel.Preclinical <em>Preclinical</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Preclinical
	 * @generated
	 */
	public Adapter createPreclinicalAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Mild <em>Mild</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Mild
	 * @generated
	 */
	public Adapter createMildAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link seirmodel.Severe <em>Severe</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Severe
	 * @generated
	 */
	public Adapter createSevereAdapter() {
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
	 * Creates a new adapter for an object of class '{@link seirmodel.Test <em>Test</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see seirmodel.Test
	 * @generated
	 */
	public Adapter createTestAdapter() {
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
