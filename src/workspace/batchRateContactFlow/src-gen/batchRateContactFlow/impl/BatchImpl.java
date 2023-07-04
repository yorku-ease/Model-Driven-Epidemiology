/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.Batch;
import batchRateContactFlow.BatchRateContactFlowPackage;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Batch</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BatchImpl extends FromToFlowImpl implements Batch {

	@Override
	public List<PhysicalFlow> getEquations() {
		List<PhysicalCompartment> equationCompartments = new ArrayList<>();
		String equation = "(get " + getId() + " 0)";
		List<String> requiredOperators = Arrays.asList("get");
		return new ArrayList<>(Arrays.asList(new PhysicalFlow(equationCompartments, new PhysicalCompartment(from.getLabels()), new PhysicalCompartment(to.getLabels()), equation, requiredOperators)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BatchImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.BATCH;
	}
} //BatchImpl
