package epidimensionsrefgroup;

import epidimensionsbase.FlowVisitor;

public interface ComplexFlowVisitor<T> extends FlowVisitor<T> {
	void visit(epidimensionsrefgroup.Batch b);
	void visit(epidimensionsrefgroup.Rate r);
	void visit(epidimensionsrefgroup.Contact c);
}
