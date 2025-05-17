/**
 */
package seirmodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import seirmodel.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SeirmodelFactoryImpl extends EFactoryImpl implements SeirmodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SeirmodelFactory init() {
		try {
			SeirmodelFactory theSeirmodelFactory = (SeirmodelFactory)EPackage.Registry.INSTANCE.getEFactory(SeirmodelPackage.eNS_URI);
			if (theSeirmodelFactory != null) {
				return theSeirmodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SeirmodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeirmodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SeirmodelPackage.FLOW: return createFlow();
			case SeirmodelPackage.SUSCEPTIBLE: return createSusceptible();
			case SeirmodelPackage.TREATED: return createTreated();
			case SeirmodelPackage.SEIR_MODEL: return createSEIRModel();
			case SeirmodelPackage.AIDS: return createAIDS();
			case SeirmodelPackage.HOSPITALIZED: return createHospitalized();
			case SeirmodelPackage.DEATHS: return createDeaths();
			case SeirmodelPackage.UNTREATED_INFECTIOUS: return createUntreatedInfectious();
			case SeirmodelPackage.VACCINATED: return createVaccinated();
			case SeirmodelPackage.NO_ACCESS: return createNoAccess();
			case SeirmodelPackage.ISOLATED_AFTER_TESTING_POSITIVE: return createIsolatedAfterTestingPositive();
			case SeirmodelPackage.EXPOSED: return createExposed();
			case SeirmodelPackage.POST_ACUTE: return createPostAcute();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Flow createFlow() {
		FlowImpl flow = new FlowImpl();
		return flow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Susceptible createSusceptible() {
		SusceptibleImpl susceptible = new SusceptibleImpl();
		return susceptible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Treated createTreated() {
		TreatedImpl treated = new TreatedImpl();
		return treated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SEIRModel createSEIRModel() {
		SEIRModelImpl seirModel = new SEIRModelImpl();
		return seirModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AIDS createAIDS() {
		AIDSImpl aids = new AIDSImpl();
		return aids;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Hospitalized createHospitalized() {
		HospitalizedImpl hospitalized = new HospitalizedImpl();
		return hospitalized;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Deaths createDeaths() {
		DeathsImpl deaths = new DeathsImpl();
		return deaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UntreatedInfectious createUntreatedInfectious() {
		UntreatedInfectiousImpl untreatedInfectious = new UntreatedInfectiousImpl();
		return untreatedInfectious;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Vaccinated createVaccinated() {
		VaccinatedImpl vaccinated = new VaccinatedImpl();
		return vaccinated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NoAccess createNoAccess() {
		NoAccessImpl noAccess = new NoAccessImpl();
		return noAccess;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IsolatedAfterTestingPositive createIsolatedAfterTestingPositive() {
		IsolatedAfterTestingPositiveImpl isolatedAfterTestingPositive = new IsolatedAfterTestingPositiveImpl();
		return isolatedAfterTestingPositive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Exposed createExposed() {
		ExposedImpl exposed = new ExposedImpl();
		return exposed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PostAcute createPostAcute() {
		PostAcuteImpl postAcute = new PostAcuteImpl();
		return postAcute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SeirmodelPackage getSeirmodelPackage() {
		return (SeirmodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SeirmodelPackage getPackage() {
		return SeirmodelPackage.eINSTANCE;
	}

} //SeirmodelFactoryImpl
