/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Rate;
import epimodel.util.FlowEquation;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rate</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RateImpl extends FromToFlowImpl implements Rate {

	@Override
	public List<FlowEquation> getEquations() {
		List<PhysicalCompartment> equationCompartments = Arrays.asList(new PhysicalCompartment(from.getLabels()));
		List<PhysicalCompartment> affectedCompartments = Arrays.asList(new PhysicalCompartment(from.getLabels()), new PhysicalCompartment(to.getLabels()));
		List<Float> coefficients = Arrays.asList(-1f, 1f);
		String equation = "(get " + getId() + " 0)";
		List<String> requiredOperators = Arrays.asList("get");
		return new ArrayList<>(Arrays.asList(new FlowEquation(equationCompartments, affectedCompartments, coefficients, equation, requiredOperators)));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchRateContactFlowPackage.Literals.RATE;
	}

} //RateImpl
