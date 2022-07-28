/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Rate;
import epimodel.Epidemic;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;
import epimodel.util.PhysicalFlowEquation;

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
	public List<PhysicalFlow> getPhysicalFlows(Epidemic epidemic) {
		List<PhysicalFlow> res = new ArrayList<>();

		int index = 0;
		for (PhysicalCompartment from : epidemic.getPhysicalSinksFor(from))
			for (PhysicalCompartment to : epidemic.getPhysicalSourcesFor(to)) {
				
				List<PhysicalCompartment> equationCompartments = Arrays.asList(from);
				List<PhysicalCompartment> affectedCompartments = Arrays.asList(from, to);
				List<Float> coefficients = Arrays.asList(-1f, 1f);
				String flowParameter = "(get " + getId() + " " + index++ +")";
				String equation = "(* " + flowParameter + " $0)";
				List<String> requiredOperators = Arrays.asList("*", "get");
				
				res.add(
					new PhysicalFlow(
						Arrays.asList(
							new PhysicalFlowEquation(
								equationCompartments,
								affectedCompartments,
								coefficients,
								equation,
								requiredOperators
							))));
			}
		return res;
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
