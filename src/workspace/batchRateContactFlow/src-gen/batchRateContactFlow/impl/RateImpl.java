/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Rate;
import epimodel.util.PhysicalFlow;
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
	public List<PhysicalFlow> getEquations() {
		String _parameters = get_from_to_parameters();
		String equation = "(* $0" + _parameters + ")";
		return new ArrayList<>(
			Arrays.asList(
					new PhysicalFlow(
						new PhysicalCompartment(from.getLabels()),
						new PhysicalCompartment(to.getLabels()),
						equation
					)
				)
		);
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
