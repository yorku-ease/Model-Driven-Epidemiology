/**
 */
package batchRateContactFlow;

import epimodel.Compartment;
import epimodel.Flow;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>From To Flow</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link batchRateContactFlow.FromToFlow#getFrom <em>From</em>}</li>
 *   <li>{@link batchRateContactFlow.FromToFlow#getTo <em>To</em>}</li>
 *   <li>{@link batchRateContactFlow.FromToFlow#getSourceParameters <em>Source Parameters</em>}</li>
 *   <li>{@link batchRateContactFlow.FromToFlow#getSinkParameters <em>Sink Parameters</em>}</li>
 *   <li>{@link batchRateContactFlow.FromToFlow#getParameters <em>Parameters</em>}</li>
 * </ul>
 *
 * @see batchRateContactFlow.BatchRateContactFlowPackage#getFromToFlow()
 * @model abstract="true"
 * @generated
 */
public interface FromToFlow extends Flow {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(Compartment)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getFromToFlow_From()
	 * @model
	 * @generated
	 */
	Compartment getFrom();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.FromToFlow#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(Compartment value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(Compartment)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getFromToFlow_To()
	 * @model
	 * @generated
	 */
	Compartment getTo();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.FromToFlow#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(Compartment value);

	/**
	 * Returns the value of the '<em><b>Source Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Parameters</em>' attribute.
	 * @see #setSourceParameters(String)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getFromToFlow_SourceParameters()
	 * @model
	 * @generated
	 */
	String getSourceParameters();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.FromToFlow#getSourceParameters <em>Source Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Parameters</em>' attribute.
	 * @see #getSourceParameters()
	 * @generated
	 */
	void setSourceParameters(String value);

	/**
	 * Returns the value of the '<em><b>Sink Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sink Parameters</em>' attribute.
	 * @see #setSinkParameters(String)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getFromToFlow_SinkParameters()
	 * @model
	 * @generated
	 */
	String getSinkParameters();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.FromToFlow#getSinkParameters <em>Sink Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sink Parameters</em>' attribute.
	 * @see #getSinkParameters()
	 * @generated
	 */
	void setSinkParameters(String value);

	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' attribute.
	 * @see #setParameters(String)
	 * @see batchRateContactFlow.BatchRateContactFlowPackage#getFromToFlow_Parameters()
	 * @model
	 * @generated
	 */
	String getParameters();

	/**
	 * Sets the value of the '{@link batchRateContactFlow.FromToFlow#getParameters <em>Parameters</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameters</em>' attribute.
	 * @see #getParameters()
	 * @generated
	 */
	void setParameters(String value);

} // FromToFlow
