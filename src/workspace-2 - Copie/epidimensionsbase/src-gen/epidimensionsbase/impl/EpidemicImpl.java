/**
 */
package epidimensionsbase.impl;

import epidimensionsbase.Dimension;
import epidimensionsbase.Epidemic;
import epidimensionsbase.EpidimensionsbasePackage;
import epidimensionsbase.FlowEquationVisitor;
import org.eclipse.emf.ecore.EClass;
import org.json.JSONArray;
import org.json.JSONObject;

import epibase.Flow;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Epidemic</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class EpidemicImpl extends epibase.impl.EpidemicImpl implements Epidemic {

	JSONObject compartments;

	@Override
	public JSONObject compileCompartments() throws Exception {
		JSONObject res = new JSONObject();
		JSONArray dims = new JSONArray();

		for (Dimension dim : getDimension()) {
			JSONObject dimJSON = new JSONObject();
			dimJSON.put("size", dim.getCompartment().size());
			dimJSON.put("id", dim.getId());
			JSONArray compartments = new JSONArray();
			for (epibase.Compartment compartment : dim.getCompartment()) {
				JSONObject compartmentJSON = new JSONObject();
				compartmentJSON.put("id", compartment.getId());
				compartments.put(compartmentJSON);
			}
			dimJSON.put("compartments", compartments);
			dims.put(dimJSON);
		}

		res.put("dimensions", dims);
		compartments = res;
		return res;
	}

	@Override
	public JSONObject compileFlows() throws Exception {
		JSONObject res = new JSONObject();
		JSONArray flows = new JSONArray();
		FlowEquationVisitor v = new FlowEquationVisitor(compartments);
		for (Dimension dim : getDimension()) {
			JSONArray dimensionflows = new JSONArray();
			for (Flow f : dim.getFlow()) {
				f.accept(v);
				dimensionflows.put(v.data());
			}
			flows.put(dimensionflows);
		}
		res.put("flows", flows);
		return res;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EpidemicImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpidimensionsbasePackage.Literals.EPIDEMIC;
	}

} //EpidemicImpl
