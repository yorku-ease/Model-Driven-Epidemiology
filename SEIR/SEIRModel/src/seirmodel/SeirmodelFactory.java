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
	 * Returns a new object of class '<em>Exposed Non Isolated</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exposed Non Isolated</em>'.
	 * @generated
	 */
	ExposedNonIsolated createExposedNonIsolated();

	/**
	 * Returns a new object of class '<em>Symptomatic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Symptomatic</em>'.
	 * @generated
	 */
	Symptomatic createSymptomatic();

	/**
	 * Returns a new object of class '<em>Asymptomatic</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Asymptomatic</em>'.
	 * @generated
	 */
	Asymptomatic createAsymptomatic();

	/**
	 * Returns a new object of class '<em>Recovered</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Recovered</em>'.
	 * @generated
	 */
	Recovered createRecovered();

	/**
	 * Returns a new object of class '<em>Exposed Isolated</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Exposed Isolated</em>'.
	 * @generated
	 */
	ExposedIsolated createExposedIsolated();

	/**
	 * Returns a new object of class '<em>SEIR Model</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>SEIR Model</em>'.
	 * @generated
	 */
	SEIRModel createSEIRModel();

	/**
	 * Returns a new object of class '<em>Preclinical</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Preclinical</em>'.
	 * @generated
	 */
	Preclinical createPreclinical();

	/**
	 * Returns a new object of class '<em>Mild</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mild</em>'.
	 * @generated
	 */
	Mild createMild();

	/**
	 * Returns a new object of class '<em>Severe</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Severe</em>'.
	 * @generated
	 */
	Severe createSevere();

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
	 * Returns a new object of class '<em>Test</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test</em>'.
	 * @generated
	 */
	Test createTest();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SeirmodelPackage getSeirmodelPackage();

} //SeirmodelFactory
