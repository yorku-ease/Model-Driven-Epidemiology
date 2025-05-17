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
			case SeirmodelPackage.TREATED: {
				Treated treated = (Treated)theEObject;
				T result = caseTreated(treated);
				if (result == null) result = caseCompartment(treated);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.SEIR_MODEL: {
				SEIRModel seirModel = (SEIRModel)theEObject;
				T result = caseSEIRModel(seirModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.AIDS: {
				AIDS aids = (AIDS)theEObject;
				T result = caseAIDS(aids);
				if (result == null) result = caseCompartment(aids);
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
			case SeirmodelPackage.UNTREATED_INFECTIOUS: {
				UntreatedInfectious untreatedInfectious = (UntreatedInfectious)theEObject;
				T result = caseUntreatedInfectious(untreatedInfectious);
				if (result == null) result = caseCompartment(untreatedInfectious);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.VACCINATED: {
				Vaccinated vaccinated = (Vaccinated)theEObject;
				T result = caseVaccinated(vaccinated);
				if (result == null) result = caseCompartment(vaccinated);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.NO_ACCESS: {
				NoAccess noAccess = (NoAccess)theEObject;
				T result = caseNoAccess(noAccess);
				if (result == null) result = caseCompartment(noAccess);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SeirmodelPackage.ISOLATED_AFTER_TESTING_POSITIVE: {
				IsolatedAfterTestingPositive isolatedAfterTestingPositive = (IsolatedAfterTestingPositive)theEObject;
				T result = caseIsolatedAfterTestingPositive(isolatedAfterTestingPositive);
				if (result == null) result = caseCompartment(isolatedAfterTestingPositive);
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
			case SeirmodelPackage.POST_ACUTE: {
				PostAcute postAcute = (PostAcute)theEObject;
				T result = casePostAcute(postAcute);
				if (result == null) result = caseCompartment(postAcute);
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
	 * Returns the result of interpreting the object as an instance of '<em>Treated</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Treated</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTreated(Treated object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Post Acute</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Post Acute</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePostAcute(PostAcute object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Vaccinated</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vaccinated</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVaccinated(Vaccinated object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>No Access</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>No Access</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNoAccess(NoAccess object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Isolated After Testing Positive</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Isolated After Testing Positive</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIsolatedAfterTestingPositive(IsolatedAfterTestingPositive object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>AIDS</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>AIDS</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAIDS(AIDS object) {
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
	 * Returns the result of interpreting the object as an instance of '<em>Untreated Infectious</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Untreated Infectious</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUntreatedInfectious(UntreatedInfectious object) {
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
