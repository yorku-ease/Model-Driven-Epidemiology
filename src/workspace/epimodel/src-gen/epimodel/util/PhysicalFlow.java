package epimodel.util;

import java.util.List;

public class PhysicalFlow {
	public final List<PhysicalFlowEquation> equations;
	
	public PhysicalFlow(List<PhysicalFlowEquation> equations) {
		this.equations = equations;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof PhysicalFlow && ((PhysicalFlow) other).equations.equals(equations));
	}
}
