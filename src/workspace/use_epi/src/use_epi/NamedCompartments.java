package use_epi;

import java.util.ArrayList;
import java.util.List;

import epimodel.Compartment;

public class NamedCompartments {
	public final List<Compartment> compartments = null;
	public final List<Flow> flows = new ArrayList<>();
	
//	NamedCompartments(List<Dimension> dimensions) throws JSONException {
//		List<List<MetaCompartment>> metaCompartments = dimensions.stream().map(Dimension::getCompartment).collect(Collectors.toList());
//		List<List<MetaCompartment>> products = cartesianProduct(metaCompartments);
//		compartments = products.stream().map(l -> new Compartment(l)).collect(Collectors.toList());
//		
//		for (int i = 0; i < dimensions.size(); ++i)
//			for (MetaFlow flow : dimensions.get(i).getFlow())
//				if (flow instanceof MetaContact)
//					addMetaContact((MetaContact) flow, i);
//				else if (flow instanceof MetaBatch)
//					addMetaBatch((MetaBatch) flow, i);
//				else if (flow instanceof MetaRate)
//					addMetaRate((MetaRate) flow, i);
//	}
	
//	protected void addMetaBatch(MetaBatch batch, int dim) throws JSONException {
//		List<Compartment> from = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(batch.getFrom())).collect(Collectors.toList());
//		List<Compartment> to = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(batch.getTo())).collect(Collectors.toList());
//		for (int i = 0; i < from.size(); ++i) {
//			Flow flow = new Flow(batch.getId(), i);
//			flows.add(flow);
//			Compartment f = from.get(i);
//			Compartment t = to.get(i);
//			CompartmentTransition transition = CompartmentTransition.of(batch, f, flow);
//			f.addOUT(transition);
//			t.addIN(transition);
//		}
//	}
//	
//	protected void addMetaRate(MetaRate rate, int dim) throws JSONException {
//		List<Compartment> from = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(rate.getFrom())).collect(Collectors.toList());
//		List<Compartment> to = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(rate.getTo())).collect(Collectors.toList());
//		for (int i = 0; i < from.size(); ++i) {
//			Flow flow = new Flow(rate.getId(), i);
//			flows.add(flow);
//			Compartment cf = from.get(i);
//			Compartment ct = to.get(i);
//			CompartmentTransition transition = CompartmentTransition.of(rate, cf, flow);
//			cf.addOUT(transition);
//			ct.addIN(transition);
//		}
//	}
//	
//	protected void addMetaContact(MetaContact contact, int dim) throws JSONException {
//		List<Compartment> susceptibles = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(contact.getFrom())).collect(Collectors.toList());
//		List<Compartment> exposed = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(contact.getTo())).collect(Collectors.toList());
//		List<Compartment> infectious = compartments.stream().filter(c -> c.metaCompartments.get(dim).equals(contact.getContact())).collect(Collectors.toList());
//		
//		for (int i = 0; i < susceptibles.size(); ++i) {
//			Compartment sus = susceptibles.get(i);
//			Compartment exp = exposed.get(i);
//			for (Compartment inf : infectious) {
//				Flow flow = new Flow(contact.getId(), i);
//				flows.add(flow);
//				CompartmentTransition transition = CompartmentTransition.of(contact, sus, inf, flow);
//				sus.addOUT(transition);
//				exp.addIN(transition);
//			};
//		}
//	}
	
	public class Flow {
		public final String id;
		public final int n;
		
		Flow(String id, int n) {
			this.id = id;
			this.n = n;
		}
	}
}
