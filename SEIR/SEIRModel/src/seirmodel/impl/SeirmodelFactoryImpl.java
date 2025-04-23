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
			case SeirmodelPackage.EXPOSED_NON_ISOLATED: return createExposedNonIsolated();
			case SeirmodelPackage.SYMPTOMATIC: return createSymptomatic();
			case SeirmodelPackage.ASYMPTOMATIC: return createAsymptomatic();
			case SeirmodelPackage.RECOVERED: return createRecovered();
			case SeirmodelPackage.EXPOSED_ISOLATED: return createExposedIsolated();
			case SeirmodelPackage.SEIR_MODEL: return createSEIRModel();
			case SeirmodelPackage.PRECLINICAL: return createPreclinical();
			case SeirmodelPackage.MILD: return createMild();
			case SeirmodelPackage.SEVERE: return createSevere();
			case SeirmodelPackage.HOSPITALIZED: return createHospitalized();
			case SeirmodelPackage.DEATHS: return createDeaths();
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
	public ExposedNonIsolated createExposedNonIsolated() {
		ExposedNonIsolatedImpl exposedNonIsolated = new ExposedNonIsolatedImpl();
		return exposedNonIsolated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Symptomatic createSymptomatic() {
		SymptomaticImpl symptomatic = new SymptomaticImpl();
		return symptomatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Asymptomatic createAsymptomatic() {
		AsymptomaticImpl asymptomatic = new AsymptomaticImpl();
		return asymptomatic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Recovered createRecovered() {
		RecoveredImpl recovered = new RecoveredImpl();
		return recovered;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExposedIsolated createExposedIsolated() {
		ExposedIsolatedImpl exposedIsolated = new ExposedIsolatedImpl();
		return exposedIsolated;
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
	public Preclinical createPreclinical() {
		PreclinicalImpl preclinical = new PreclinicalImpl();
		return preclinical;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Mild createMild() {
		MildImpl mild = new MildImpl();
		return mild;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Severe createSevere() {
		SevereImpl severe = new SevereImpl();
		return severe;
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
