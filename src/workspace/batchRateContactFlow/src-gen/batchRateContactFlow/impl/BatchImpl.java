/**
 */
package batchRateContactFlow.impl;

import batchRateContactFlow.Batch;
import batchRateContactFlow.BatchRateContactFlowPackage;
import epimodel.Epidemic;
import epimodel.util.PhysicalCompartment;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Batch</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class BatchImpl extends FromToFlowImpl implements Batch {
	
	public List<Object> getEquations(Epidemic epidemic) {
		List<PhysicalCompartment> froms = epidemic.getPhysicalSinksFor(getFrom());
		List<PhysicalCompartment> tos = epidemic.getPhysicalHeadsFor(getTo());
		List<Object> equations = new ArrayList<>();
		for (PhysicalCompartment f : froms)
			for (PhysicalCompartment t : tos)
				try {
					String s = "{\"type\":\"Batch\", \"from\": \"" + f.id + "\", \"to\": \"" + t.id + "\", \"id\": \"" + getId() + "\"}";
					equations.add(new JSONObject(s));
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
