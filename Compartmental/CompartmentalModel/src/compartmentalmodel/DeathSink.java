/**
 */
package compartmentalmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Death Sink</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link compartmentalmodel.DeathSink#getName <em>Name</em>}</li>
 *   <li>{@link compartmentalmodel.DeathSink#getRate <em>Rate</em>}</li>
 *   <li>{@link compartmentalmodel.DeathSink#getRateParameter <em>Rate Parameter</em>}</li>
 *   <li>{@link compartmentalmodel.DeathSink#getSourceCompartment <em>Source Compartment</em>}</li>
 *   <li>{@link compartmentalmodel.DeathSink#getSourceStratum <em>Source Stratum</em>}</li>
 * </ul>
 *
 * @see compartmentalmodel.CompartmentalmodelPackage#getDeathSink()
 * @model
 * @generated
 */
public interface DeathSink extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getDeathSink_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link compartmentalmodel.DeathSink#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rate</em>' attribute.
	 * @see #setRate(double)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getDeathSink_Rate()
	 * @model
	 * @generated
	 */
	double getRate();

	/**
	 * Sets the value of the '{@link compartmentalmodel.DeathSink#getRate <em>Rate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate</em>' attribute.
	 * @see #getRate()
	 * @generated
	 */
	void setRate(double value);

	/**
	 * Returns the value of the '<em><b>Rate Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rate Parameter</em>' reference.
	 * @see #setRateParameter(Parameter)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getDeathSink_RateParameter()
	 * @model
	 * @generated
	 */
	Parameter getRateParameter();

	/**
	 * Sets the value of the '{@link compartmentalmodel.DeathSink#getRateParameter <em>Rate Parameter</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate Parameter</em>' reference.
	 * @see #getRateParameter()
	 * @generated
	 */
	void setRateParameter(Parameter value);

	/**
	 * Returns the value of the '<em><b>Source Compartment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Compartment</em>' reference.
	 * @see #setSourceCompartment(Compartment)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getDeathSink_SourceCompartment()
	 * @model
	 * @generated
	 */
	Compartment getSourceCompartment();

	/**
	 * Sets the value of the '{@link compartmentalmodel.DeathSink#getSourceCompartment <em>Source Compartment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Compartment</em>' reference.
	 * @see #getSourceCompartment()
	 * @generated
	 */
	void setSourceCompartment(Compartment value);

	/**
	 * Returns the value of the '<em><b>Source Stratum</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Stratum</em>' attribute.
	 * @see #setSourceStratum(String)
	 * @see compartmentalmodel.CompartmentalmodelPackage#getDeathSink_SourceStratum()
	 * @model
	 * @generated
	 */
	String getSourceStratum();

	/**
	 * Sets the value of the '{@link compartmentalmodel.DeathSink#getSourceStratum <em>Source Stratum</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Stratum</em>' attribute.
	 * @see #getSourceStratum()
	 * @generated
	 */
	void setSourceStratum(String value);


} // DeathSink
