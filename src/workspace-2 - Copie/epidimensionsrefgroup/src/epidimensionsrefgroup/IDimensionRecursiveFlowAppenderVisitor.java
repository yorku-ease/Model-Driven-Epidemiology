package epidimensionsrefgroup;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class IDimensionRecursiveFlowAppenderVisitor implements IDimensionVisitor<EList<epidimensionsbase.Flow>> {
	
	protected EList<epidimensionsbase.Flow> _data = null;

	@Override
	public EList<epidimensionsbase.Flow> data() {
		return _data;
	}

	@Override
	public void visit(DimensionRef d) {
		d.getIdimension().accept(this);
	}

	@Override
	public void visit(Dimension d) {
		EList<epidimensionsbase.Flow> l = new org.eclipse.emf.common.util.BasicEList<>();
		for (epidimensionsbase.Flow f : d.getFlow_2()) {
//			System.out.println(f);
			l.add(EcoreUtil.copy(f));
			if (!d.getCompartment().contains(f.getTo())) {
				System.out.println(f);
			}
			if (!d.getCompartment().contains(f.getFrom())) {
				System.out.println(f);
			}
		}
		for (epidimensionsrefgroup.IDimension _d : d.getIdimension()) {
			_d.accept(this);
			FlowExpansionVisitor v = new FlowExpansionVisitor(_d);
			for (epidimensionsbase.Flow f : _data) {
				try {
					f.accept(v);
					l.addAll(v.data());
				} catch (Exception e) {
					e.printStackTrace();
					throw new NullPointerException();
				}
			}
		}
		
		_data = l;
	}

	@Override
	public void visit(Compartment d) {
		System.out.println(d);
	}
}
