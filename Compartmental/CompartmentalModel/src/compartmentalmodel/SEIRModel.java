/**
 */
package compartmentalmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>SEIR Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getBirthSources <em>Birth Sources</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getDeathSinks <em>Death Sinks</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getGroups <em>Groups</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getProducts <em>Products</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getParameters <em>Parameters</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getTotalPopulation <em>Total Population</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getGlobalBirthRate <em>Global Birth Rate</em>}</li>
 *   <li>{@link compartmentalmodel.CompartmentalModel#getGlobalDeathRate <em>Global Death Rate</em>}</li>
 * </ul>
 *
 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel()
 * @model
 * @generated
 */
public interface CompartmentalModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_Compartments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartments();

	/**
	 * Returns the value of the '<em><b>Birth Sources</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.BirthSource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Birth Sources</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_BirthSources()
	 * @model containment="true"
	 * @generated
	 */
	EList<BirthSource> getBirthSources();

	/**
	 * Returns the value of the '<em><b>Death Sinks</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.DeathSink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Death Sinks</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_DeathSinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<DeathSink> getDeathSinks();

	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.Group}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_Groups()
	 * @model containment="true"
	 * @generated
	 */
	EList<Group> getGroups();

	/**
	 * Returns the value of the '<em><b>Products</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.Product}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Products</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_Products()
	 * @model containment="true"
	 * @generated
	 */
	EList<Product> getProducts();

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' containment reference list.
	 * The list contents are of type {@link compartmentalmodel.Parameter}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' containment reference list.
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_Parameters()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parameter> getParameters();

	/**
	 * Returns the value of the '<em><b>Total Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Population</em>' attribute.
	 * @see #setTotalPopulation(int)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_TotalPopulation()
	 * @model
	 * @generated
	 */
	int getTotalPopulation();

	/**
	 * Sets the value of the '{@link compartmentalmodel.CompartmentalModel#getTotalPopulation <em>Total Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Population</em>' attribute.
	 * @see #getTotalPopulation()
	 * @generated
	 */
	void setTotalPopulation(int value);

	/**
	 * Returns the value of the '<em><b>Global Birth Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Birth Rate</em>' attribute.
	 * @see #setGlobalBirthRate(double)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_GlobalBirthRate()
	 * @model
	 * @generated
	 */
	double getGlobalBirthRate();

	/**
	 * Sets the value of the '{@link compartmentalmodel.CompartmentalModel#getGlobalBirthRate <em>Global Birth Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Global Birth Rate</em>' attribute.
	 * @see #getGlobalBirthRate()
	 * @generated
	 */
	void setGlobalBirthRate(double value);

	/**
	 * Returns the value of the '<em><b>Global Death Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global Death Rate</em>' attribute.
	 * @see #setGlobalDeathRate(double)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getCompartmentalModel_GlobalDeathRate()
	 * @model
	 * @generated
	 */
	double getGlobalDeathRate();

	/**
	 * Sets the value of the '{@link compartmentalmodel.CompartmentalModel#getGlobalDeathRate <em>Global Death Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Global Death Rate</em>' attribute.
	 * @see #getGlobalDeathRate()
	 * @generated
	 */
	void setGlobalDeathRate(double value);

} // CompartmentalModel
