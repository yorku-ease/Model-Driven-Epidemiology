/**
 */
package seirmodel.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import seirmodel.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see seirmodel.SeirmodelPackage
 * @generated
 */
public class SeirmodelSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SeirmodelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeirmodelSwitch() {
		if (modelPackage == null) {
			modelPackage = SeirmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SeirmodelPackage.COMPARTMENT: {
				Compartment compartment = (Compartment)theEObject;
				T result = caseCompartment(compartment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.FLOW: {
				Flow flow = (Flow)theEObject;
				T result = caseFlow(flow);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.SUSCEPTIBLE: {
				Susceptible susceptible = (Susceptible)theEObject;
				T result = caseSusceptible(susceptible);
				if (result == null) result = caseCompartment(susceptible);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.EXPOSED: {
				Exposed exposed = (Exposed)theEObject;
				T result = caseExposed(exposed);
				if (result == null) result = caseCompartment(exposed);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.EXPOSED_NON_ISOLATED: {
				ExposedNonIsolated exposedNonIsolated = (ExposedNonIsolated)theEObject;
				T result = caseExposedNonIsolated(exposedNonIsolated);
				if (result == null) result = caseExposed(exposedNonIsolated);
				if (result == null) result = caseCompartment(exposedNonIsolated);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.SYMPTOMATIC: {
				Symptomatic symptomatic = (Symptomatic)theEObject;
				T result = caseSymptomatic(symptomatic);
				if (result == null) result = caseInfectious(symptomatic);
				if (result == null) result = caseCompartment(symptomatic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.ASYMPTOMATIC: {
				Asymptomatic asymptomatic = (Asymptomatic)theEObject;
				T result = caseAsymptomatic(asymptomatic);
				if (result == null) result = caseInfectious(asymptomatic);
				if (result == null) result = caseCompartment(asymptomatic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.RECOVERED: {
				Recovered recovered = (Recovered)theEObject;
				T result = caseRecovered(recovered);
				if (result == null) result = caseCompartment(recovered);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.EXPOSED_ISOLATED: {
				ExposedIsolated exposedIsolated = (ExposedIsolated)theEObject;
				T result = caseExposedIsolated(exposedIsolated);
				if (result == null) result = caseExposed(exposedIsolated);
				if (result == null) result = caseCompartment(exposedIsolated);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.INFECTIOUS: {
				Infectious infectious = (Infectious)theEObject;
				T result = caseInfectious(infectious);
				if (result == null) result = caseCompartment(infectious);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.SEIR_MODEL: {
				SEIRModel seirModel = (SEIRModel)theEObject;
				T result = caseSEIRModel(seirModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.PRECLINICAL: {
				Preclinical preclinical = (Preclinical)theEObject;
				T result = casePreclinical(preclinical);
				if (result == null) result = caseCompartment(preclinical);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.MILD: {
				Mild mild = (Mild)theEObject;
				T result = caseMild(mild);
				if (result == null) result = caseSymptomatic(mild);
				if (result == null) result = caseInfectious(mild);
				if (result == null) result = caseCompartment(mild);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.SEVERE: {
				Severe severe = (Severe)theEObject;
				T result = caseSevere(severe);
				if (result == null) result = caseSymptomatic(severe);
				if (result == null) result = caseInfectious(severe);
				if (result == null) result = caseCompartment(severe);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.HOSPITALIZED: {
				Hospitalized hospitalized = (Hospitalized)theEObject;
				T result = caseHospitalized(hospitalized);
				if (result == null) result = caseCompartment(hospitalized);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.DEATHS: {
				Deaths deaths = (Deaths)theEObject;
				T result = caseDeaths(deaths);
				if (result == null) result = caseCompartment(deaths);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compartment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compartment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompartment(Compartment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Flow</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Flow</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFlow(Flow object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Susceptible</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Susceptible</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSusceptible(Susceptible object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exposed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exposed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExposed(Exposed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exposed Non Isolated</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exposed Non Isolated</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExposedNonIsolated(ExposedNonIsolated object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Symptomatic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Symptomatic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSymptomatic(Symptomatic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Asymptomatic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Asymptomatic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAsymptomatic(Asymptomatic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Recovered</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Recovered</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRecovered(Recovered object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Exposed Isolated</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Exposed Isolated</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseExposedIsolated(ExposedIsolated object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Infectious</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Infectious</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInfectious(Infectious object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SEIR Model</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SEIR Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSEIRModel(SEIRModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Preclinical</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Preclinical</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreclinical(Preclinical object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mild</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mild</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMild(Mild object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Severe</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Severe</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSevere(Severe object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Hospitalized</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Hospitalized</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseHospitalized(Hospitalized object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Deaths</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Deaths</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDeaths(Deaths object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //SeirmodelSwitch
