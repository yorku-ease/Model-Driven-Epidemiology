package epimodel.util;

import java.util.List;

public class PhysicalFlow {
	public final List<PhysicalFlowEquation> equations;
	
	public PhysicalFlow(List<PhysicalFlowEquation> equations) {
		this.equations = equations;
	}
}
