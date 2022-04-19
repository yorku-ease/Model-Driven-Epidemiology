package epidimensionsrefgroup;

public interface IDimensionVisitor<T> {
	T data();
	void visit(epidimensionsrefgroup.DimensionRef d);
	void visit(epidimensionsrefgroup.Dimension d);
	void visit(epidimensionsrefgroup.Compartment d);
}
