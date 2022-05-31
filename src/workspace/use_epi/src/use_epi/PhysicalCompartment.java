package use_epi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epimodel.Compartment;

import java.util.List;
import java.util.stream.Collectors;

public class PhysicalCompartment {
	protected final List<Compartment> labels;
	protected final String name;
	public final JSONArray jsonArrayStateNames;
	protected List<CompartmentTransition> transitionsTo = new ArrayList<>();
	protected List<CompartmentTransition> transitionsFrom = new ArrayList<>();
	
	PhysicalCompartment(List<Compartment> labels) {
		this.labels = labels;
		name = doGetCompositeStateName();
		this.jsonArrayStateNames = new JSONArray();
		for (Compartment label : labels)
			jsonArrayStateNames.put(label.getId());
	}
	
	private String doGetCompositeStateName() {
		return labels
				.stream()
				.map(Compartment::getId)
				.collect(Collectors.toList())
				.toString();
	}
	
	public String getCompositeStateName() {
		return name;
	}
	
	void addIN(CompartmentTransition t) {
		transitionsTo.add(t);
	}
	
	void addOUT(CompartmentTransition t) {
		transitionsFrom.add(t);
	}
	
	public JSONObject getEquation() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("name", jsonArrayStateNames);
		json.put("+", asJson(transitionsTo));
		json.put("-", asJson(transitionsFrom));
		return json;
	}
	
	public JSONArray asJson(List<CompartmentTransition> transitions) {
		JSONArray out = new JSONArray();
		for (CompartmentTransition t : transitions)
			out.put(t.repr);
		return out;
	}
	
	static class CompartmentTransition {
		public final JSONObject repr;
		
		protected CompartmentTransition(JSONObject repr) {
			this.repr = repr;
		}
		
//		public static CompartmentTransition of(MetaBatch b, Compartment from, Flow flow) throws JSONException {
//			JSONObject json = new JSONObject();
//			json.put("type", "Batch");
//			json.put("name", flow.id + "[" + flow.n + "]");
//			json.put("from", from.jsonArrayStateNames);
//			return new CompartmentTransition(json);
//		}
//		
//		public static CompartmentTransition of(MetaRate r, Compartment from, Flow flow) throws JSONException {
//			JSONObject json = new JSONObject();
//			json.put("type", "Rate");
//			json.put("name", flow.id + "[" + flow.n + "]");
//			json.put("from", from.jsonArrayStateNames);
//			return new CompartmentTransition(json);
//		}
//		
//		public static CompartmentTransition of(MetaContact m, Compartment susceptible, Compartment infectious, Flow flow) throws JSONException {
//			JSONObject json = new JSONObject();
//			json.put("type", "Mix");
//			json.put("name", flow.id + "[" + flow.n + "]");
//			json.put("susceptible", susceptible.jsonArrayStateNames);
//			json.put("infectious", infectious.jsonArrayStateNames);
//			return new CompartmentTransition(json);
//		}
	}
}
