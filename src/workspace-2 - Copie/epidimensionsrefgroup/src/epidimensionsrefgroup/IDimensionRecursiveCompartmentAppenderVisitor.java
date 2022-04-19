package epidimensionsrefgroup;

import org.eclipse.emf.common.util.EList;

public class IDimensionRecursiveCompartmentAppenderVisitor implements IDimensionVisitor<EList<epidimensionsbase.Compartment>> {
	
	protected EList<epidimensionsbase.Compartment> _data = null;

	@Override
	public void visit(DimensionRef d) {
		d.getIdimension().accept(this);
	}

	@Override
	public void visit(Dimension d) {
		EList<epidimensionsbase.Compartment> l = new org.eclipse.emf.common.util.BasicEList<>();
		for (epidimensionsrefgroup.IDimension _d : d.getIdimension()) {
			_d.accept(this);
			for (epidimensionsbase.Compartment c : _data)
				l.add(c);
		}
		_data = l;
	}

	@Override
	public void visit(Compartment d) {
		EList<epidimensionsbase.Compartment> l = new org.eclipse.emf.common.util.BasicEList<>();
		l.add(d);
		_data = l;
	}

	@Override
	public EList<epidimensionsbase.Compartment> data() {
		return _data;
	}
}
