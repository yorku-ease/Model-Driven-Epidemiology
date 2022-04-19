package epidimensionsrefgroup;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import epidimensionsbase.Batch;
import epidimensionsbase.Contact;
import epidimensionsbase.Flow;
import epidimensionsbase.Rate;

public class FlowExpansionVisitor implements ComplexFlowVisitor<EList<epidimensionsbase.Flow>> {
	
	protected EList<epidimensionsbase.Flow> _data = new org.eclipse.emf.common.util.BasicEList<>();
	protected IDimension d;
	
	FlowExpansionVisitor(IDimension d) {
		this.d = EcoreUtil.copy(d);
	}

	@Override
	public EList<Flow> data() {
		return _data;
	}

	@Override
	public void visit(Batch b) throws Exception {
		if (b instanceof epidimensionsrefgroup.Batch) {
			visit((epidimensionsrefgroup.Batch) b);
		} else {
			_data = new org.eclipse.emf.common.util.BasicEList<>();
			_data.add(EcoreUtil.copy(b));
		}
	}

	@Override
	public void visit(Rate r) throws Exception {
		if (r instanceof epidimensionsrefgroup.Rate) {
			visit((epidimensionsrefgroup.Rate) r);
		} else {
			_data = new org.eclipse.emf.common.util.BasicEList<>();
			_data.add(EcoreUtil.copy(r));
		}
	}

	@Override
	public void visit(Contact c) throws Exception {
		if (c instanceof epidimensionsrefgroup.Contact) {
			visit((epidimensionsrefgroup.Contact) c);
		} else {
			_data = new org.eclipse.emf.common.util.BasicEList<>();
			_data.add(EcoreUtil.copy(c));
		}
	}

	@Override
	public void visit(epidimensionsrefgroup.Batch f) {
		_data = new org.eclipse.emf.common.util.BasicEList<>();
		EList<epidimensionsbase.Compartment> fromList = getFromList(f);
		EList<epidimensionsbase.Compartment> toList = getToList(f);

		for (epidimensionsbase.Compartment c1 : fromList)
			for (epidimensionsbase.Compartment c2 : toList) {
				epidimensionsrefgroup.Batch cp = EcoreUtil.copy(f);
				cp.setFrom(c1);
				cp.setTo(c2);
				cp.setId(cp.getId() + ":" + c1.getId() + ":" + c2.getId());
				_data.add(cp);
			}
	}

	@Override
	public void visit(epidimensionsrefgroup.Rate f) {
		_data = new org.eclipse.emf.common.util.BasicEList<>();
		EList<epidimensionsbase.Compartment> fromList = getFromList(f);
		EList<epidimensionsbase.Compartment> toList = getToList(f);

		for (epidimensionsbase.Compartment c1 : fromList)
			for (epidimensionsbase.Compartment c2 : toList) {
				epidimensionsrefgroup.Rate cp = EcoreUtil.copy(f);
				cp.setFrom(c1);
				cp.setTo(c2);
				cp.setId(cp.getId() + ":" + c1.getId() + ":" + c2.getId());
				_data.add(cp);
			}
	}

	@Override
	public void visit(epidimensionsrefgroup.Contact f) {
		_data = new org.eclipse.emf.common.util.BasicEList<>();
		EList<epidimensionsbase.Compartment> fromList = getFromList(f);
		EList<epidimensionsbase.Compartment> toList = getToList(f);
		EList<epidimensionsbase.Compartment> contactList = getContactList(f);

		for (epidimensionsbase.Compartment c1 : fromList)
			for (epidimensionsbase.Compartment c2 : toList) 
				for (epidimensionsbase.Compartment c3 : contactList) {
				epidimensionsrefgroup.Contact cp = EcoreUtil.copy(f);
				cp.setFrom(c1);
				cp.setTo(c2);
				cp.setContact(c3);
				cp.setId(cp.getId() + ":" + c1.getId() + ":" + c2.getId() + ":" + c3.getId());
				_data.add(cp);
			}
	}
	
	protected EList<epidimensionsbase.Compartment> getToList(epidimensionsrefgroup.Flow f) {
		EList<epidimensionsbase.Compartment> toList = new org.eclipse.emf.common.util.BasicEList<>();
		boolean toOK = d.getCompartment().contains(f.getTo_dimension());
		System.out.println(f.getTo_dimension().getCompartment());

		if (toOK)
			toList.add(EcoreUtil.copy(f.getTo_dimension()));
		else {
			System.out.println(f.getTo_dimension().getCompartment());
			f.getTo_dimension().getCompartment().forEach(c -> {
				boolean hasTo = false;
				for (epidimensionsbase.Flow df : f.getTo_dimension().getFlow())
					if (df.getTo() == c) {
						hasTo = true;
						break;
					}
				if (!hasTo)
					toList.add(EcoreUtil.copy(c));
			});
		}
		
		return toList;
	}
	
	protected EList<epidimensionsbase.Compartment> getFromList(epidimensionsrefgroup.Flow f) {
		EList<epidimensionsbase.Compartment> fromList = new org.eclipse.emf.common.util.BasicEList<>();
		boolean fromOK = d.getCompartment().contains(f.getFrom_dimension());

		if (fromOK)
			fromList.add(EcoreUtil.copy(f.getFrom_dimension()));
		else {
			f.getFrom_dimension().getCompartment().forEach(c -> {
				boolean hasFrom = false;
				for (epidimensionsbase.Flow df : f.getFrom_dimension().getFlow())
					if (df.getFrom() == c) {
						hasFrom = true;
						break;
					}
				if (!hasFrom)
					fromList.add(EcoreUtil.copy(c));
			});
		}
		
		return fromList;
	}
	
	protected EList<epidimensionsbase.Compartment> getContactList(epidimensionsrefgroup.Contact f) {
		EList<epidimensionsbase.Compartment> contactList = new org.eclipse.emf.common.util.BasicEList<>();
		boolean fromOK = d.getCompartment().contains(f.getContact_dimension());

		if (fromOK)
			contactList.add(EcoreUtil.copy(f.getContact_dimension()));
		else {
			f.getContact_dimension().getCompartment().forEach(c -> {
				boolean hasFrom = false;
				for (epidimensionsbase.Flow df : f.getContact_dimension().getFlow())
					if (df instanceof epidimensionsbase.Contact)
					if (((epidimensionsbase.Contact) df).getContact() == c) {
						hasFrom = true;
						break;
					}
				if (!hasFrom)
					contactList.add(EcoreUtil.copy(c));
			});
		}
		
		return contactList;
	}
}
