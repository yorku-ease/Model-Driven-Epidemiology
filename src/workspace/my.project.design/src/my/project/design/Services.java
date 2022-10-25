package my.project.design;

import epimodel.Flow;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class Services {
	public List<EObject> flowTargets(Flow flow) {
        return flow.getTargetObjects();
    }
    
    // to understand services that require parameters: https://www.eclipse.org/forums/index.php/m/1854385/#msg_1854385
	public String flowLabel(Flow flow, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// target is a dnode something which getTarget points to the eobject compartment
    	return flow.getTargetRelation((EObject) target.getClass().getMethod("getTarget").invoke(target));
    }
}
