package epi;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import epi.NamedCompartments.Flow;

import java.util.List;
import java.util.stream.Collectors;

import epimodel.MetaBatch;
import epimodel.MetaCompartment;
import epimodel.MetaContact;
import epimodel.MetaRate;

public class Compartment {
	protected final List<MetaCompartment> metaCompartments;
	protected final String id;
	public final JSONArray jsonArrayStateNames;
	protected List<CompartmentTransition> transitionsTo = new ArrayList<>();
	protected List<CompartmentTransition> transitionsFrom = new ArrayList<>();
	
	Compartment(List<MetaCompartment> metaCompartments) {
		this.metaCompartments = metaCompartments;
		id = doGetCompositeStateName();
		this.jsonArrayStateNames = new JSONArray();
		for (MetaCompartment s : metaCompartments)
			jsonArrayStateNames.put(s.getId());
	}
	
	private String doGetCompositeStateName() {
		return metaCompartments
				.stream()
				.map(MetaCompartment::getId)
				.collect(Collectors.toList())
				.toString();
	}
	
	public String getCompositeStateName() {
		return id;
	}
	
	void addIN(CompartmentTransition t) {
		transitionsTo.add(t);
	}
	
	void addOUT(CompartmentTransition t) {
		transitionsFrom.add(t);
	}
	
	public JSONObject getEquation() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("id", jsonArrayStateNames);
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
		
		public static CompartmentTransition of(MetaBatch b, Compartment from, Flow flow) throws JSONException {
			JSONObject json = new JSONObject();
			json.put("type", "Batch");
			json.put("id", flow.id + "[" + flow.n + "]");
			json.put("from", from.jsonArrayStateNames);
			return new CompartmentTransition(json);
		}
		
		public static CompartmentTransition of(MetaRate r, Compartment from, Flow flow) throws JSONException {
			JSONObject json = new JSONObject();
			json.put("type", "Rate");
			json.put("id", flow.id + "[" + flow.n + "]");
			json.put("from", from.jsonArrayStateNames);
			return new CompartmentTransition(json);
		}
		
		public static CompartmentTransition of(MetaContact m, Compartment susceptible, Compartment infectious, Flow flow) throws JSONException {
			JSONObject json = new JSONObject();
			json.put("type", "Mix");
			json.put("id", flow.id + "[" + flow.n + "]");
			json.put("susceptible", susceptible.jsonArrayStateNames);
			json.put("infectious", infectious.jsonArrayStateNames);
			return new CompartmentTransition(json);
		}
	}
}
