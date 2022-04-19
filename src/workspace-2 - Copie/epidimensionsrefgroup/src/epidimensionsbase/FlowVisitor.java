package epidimensionsbase;

public interface FlowVisitor<T> {
	T data();
	void visit(Batch b) throws Exception;
	void visit(Rate r) throws Exception;
	void visit(Contact c) throws Exception;
}
