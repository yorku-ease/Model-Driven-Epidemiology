/**
 */
package seirmodel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see seirmodel.SeirmodelPackage
 * @generated
 */
public interface SeirmodelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SeirmodelFactory eINSTANCE = seirmodel.impl.SeirmodelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Flow</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Flow</em>'.
	 * @generated
	 */
	Flow createFlow();

	/**
	 * Returns a new object of class '<em>Susceptible</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Susceptible</em>'.
	 * @generated
	 */
	Susceptible createSusceptible();

	/**
	 * Returns a new object of class '<em>Treated</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Treated</em>'.
	 * @generated
	 */
	Treated createTreated();

	/**
	 * Returns a new object of class '<em>SEIR Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>SEIR Model</em>'.
	 * @generated
	 */
	SEIRModel createSEIRModel();

	/**
	 * Returns a new object of class '<em>AIDS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>AIDS</em>'.
	 * @generated
	 */
	AIDS createAIDS();

	/**
	 * Returns a new object of class '<em>Hospitalized</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Hospitalized</em>'.
	 * @generated
	 */
	Hospitalized createHospitalized();

	/**
	 * Returns a new object of class '<em>Deaths</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deaths</em>'.
	 * @generated
	 */
	Deaths createDeaths();

	/**
	 * Returns a new object of class '<em>Untreated Infectious</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Untreated Infectious</em>'.
	 * @generated
	 */
	UntreatedInfectious createUntreatedInfectious();

	/**
	 * Returns a new object of class '<em>Vaccinated</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Vaccinated</em>'.
	 * @generated
	 */
	Vaccinated createVaccinated();

	/**
	 * Returns a new object of class '<em>No Access</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>No Access</em>'.
	 * @generated
	 */
	NoAccess createNoAccess();

	/**
	 * Returns a new object of class '<em>Isolated After Testing Positive</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Isolated After Testing Positive</em>'.
	 * @generated
	 */
	IsolatedAfterTestingPositive createIsolatedAfterTestingPositive();

	/**
	 * Returns a new object of class '<em>Exposed</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exposed</em>'.
	 * @generated
	 */
	Exposed createExposed();

	/**
	 * Returns a new object of class '<em>Post Acute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Post Acute</em>'.
	 * @generated
	 */
	PostAcute createPostAcute();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SeirmodelPackage getSeirmodelPackage();

} //SeirmodelFactory
