/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.BatchRateContactFlowPackage;
import batchRateContactFlow.Rate;
import epimodel.Epidemic;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rate</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class RateImpl extends FromToFlowImpl implements Rate {
	
	public List<Object> getEquations(Epidemic epidemic) {
		List<PhysicalCompartment> froms = epidemic.getPhysicalSinksFor(getFrom());
		List<PhysicalCompartment> tos = epidemic.getPhysicalSourcesFor(getTo());
		List<Object> equations = new ArrayList<>();
		for (PhysicalCompartment f : froms)
			for (PhysicalCompartment t : tos)
				try {
					JSONObject res = new JSONObject();
					res.put("from", f.labels);
					res.put("to", t.labels);
					equations.add(res);
				} catch (JSONException e) {
					throw new NullPointerException(e.toString());
				}
		return equations;
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
