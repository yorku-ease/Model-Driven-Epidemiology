/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.Batch;
import batchRateContactFlow.BatchRateContactFlowPackage;
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
 * An implementation of the model object '<em><b>Batch</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BatchImpl extends FromToFlowImpl implements Batch {
	
	@Override
	public List<PhysicalFlow> getPhysicalFlows(Epidemic epidemic) {
		List<PhysicalFlow> res = new ArrayList<>();

		int index = 0;
		for (PhysicalCompartment from : epidemic.getPhysicalSinksFor(from))
			for (PhysicalCompartment to : epidemic.getPhysicalSourcesFor(to)) {
				
				List<PhysicalCompartment> equationCompartments = new ArrayList<>();
				List<PhysicalCompartment> affectedCompartments = Arrays.asList(from, to);
				List<Float> coefficients = Arrays.asList(-1f, 1f);
				String equation = "(get " + getId() + " " + index++ +")";
				List<String> requiredOperators = Arrays.asList("get");
				
				res.add(new PhysicalFlow(
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
