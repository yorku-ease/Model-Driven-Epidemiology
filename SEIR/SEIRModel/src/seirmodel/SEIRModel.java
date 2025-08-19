/**
 */
package seirmodel;

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
 *   <li>{@link seirmodel.SEIRModel#getCompartments <em>Compartments</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getBirthSources <em>Birth Sources</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getDeathSinks <em>Death Sinks</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getGroups <em>Groups</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getProducts <em>Products</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getTotalPopulation <em>Total Population</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getGlobalBirthRate <em>Global Birth Rate</em>}</li>
 *   <li>{@link seirmodel.SEIRModel#getGlobalDeathRate <em>Global Death Rate</em>}</li>
 * </ul>
 *
 * @see seirmodel.SeirmodelPackage#getSEIRModel()
 * @model
 * @generated
 */
public interface SEIRModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Compartments</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.Compartment}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Compartments</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_Compartments()
	 * @model containment="true"
	 * @generated
	 */
	EList<Compartment> getCompartments();

	/**
	 * Returns the value of the '<em><b>Birth Sources</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.BirthSource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Birth Sources</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_BirthSources()
	 * @model containment="true"
	 * @generated
	 */
	EList<BirthSource> getBirthSources();

	/**
	 * Returns the value of the '<em><b>Death Sinks</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.DeathSink}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Death Sinks</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_DeathSinks()
	 * @model containment="true"
	 * @generated
	 */
	EList<DeathSink> getDeathSinks();

	/**
	 * Returns the value of the '<em><b>Groups</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.Group}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Groups</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_Groups()
	 * @model containment="true"
	 * @generated
	 */
	EList<Group> getGroups();

	/**
	 * Returns the value of the '<em><b>Products</b></em>' containment reference list.
	 * The list contents are of type {@link seirmodel.Product}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Products</em>' containment reference list.
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_Products()
	 * @model containment="true"
	 * @generated
	 */
	EList<Product> getProducts();

	/**
	 * Returns the value of the '<em><b>Total Population</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Population</em>' attribute.
	 * @see #setTotalPopulation(int)
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_TotalPopulation()
	 * @model
	 * @generated
	 */
	int getTotalPopulation();

	/**
	 * Sets the value of the '{@link seirmodel.SEIRModel#getTotalPopulation <em>Total Population</em>}' attribute.
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
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_GlobalBirthRate()
	 * @model
	 * @generated
	 */
	double getGlobalBirthRate();

	/**
	 * Sets the value of the '{@link seirmodel.SEIRModel#getGlobalBirthRate <em>Global Birth Rate</em>}' attribute.
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
	 * @see seirmodel.SeirmodelPackage#getSEIRModel_GlobalDeathRate()
	 * @model
	 * @generated
	 */
	double getGlobalDeathRate();

	/**
	 * Sets the value of the '{@link seirmodel.SEIRModel#getGlobalDeathRate <em>Global Death Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Global Death Rate</em>' attribute.
	 * @see #getGlobalDeathRate()
	 * @generated
	 */
	void setGlobalDeathRate(double value);

} // SEIRModel
