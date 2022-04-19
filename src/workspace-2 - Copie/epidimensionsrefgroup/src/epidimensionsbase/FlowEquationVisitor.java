package epidimensionsbase;

import org.json.JSONException;
import org.json.JSONObject;

public class FlowEquationVisitor implements FlowVisitor<JSONObject> {
	
	protected JSONObject compartments;
	protected JSONObject _data;
	
	public FlowEquationVisitor(JSONObject compartments) {
		this.compartments = compartments;
	}
	
	JSONObject generateEquation(Flow f) throws Exception {
		f.accept(this);
		return (JSONObject) data();
	}

	@Override
	public JSONObject data() {
		return _data;
	}

	@Override
	public void visit(Batch flow) throws JSONException {
		_data = new JSONObject();
		_data.put("type", "Batch");
		_data.put("from", flow.getFrom().getId());
		_data.put("to", flow.getTo().getId());
		_data.put("id", flow.getId());
	}

	@Override
	public void visit(Rate flow) throws JSONException {
		_data = new JSONObject();
		_data.put("type", "Rate");
		_data.put("from", flow.getFrom().getId());
		_data.put("to", flow.getTo().getId());
		_data.put("id", flow.getId());
	}

	@Override
	public void visit(Contact flow) throws JSONException {
		_data = new JSONObject();
		_data.put("type", "Contact");
		_data.put("from", flow.getFrom().getId());
		_data.put("to", flow.getTo().getId());
		_data.put("contact", flow.getContact().getId());
		_data.put("id", flow.getId());
	}

}
