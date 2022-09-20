package my.project.design;

import epimodel.Flow;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class Services {
	public List<EObject> flowTargets(Flow flow) {
    	return flow.getTargetObjects();
    }
    
    // to understand services that require parameters
    // https://www.eclipse.org/forums/index.php/m/1854385/#msg_1854385
	public String flowLabel(Flow flow, EObject target) {
    	return "label"; //  flow.getTargetRelation(target)
    }
}
