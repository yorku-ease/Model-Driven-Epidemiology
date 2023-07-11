package my.project.design;

import epimodel.Compartment;
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
	
	public String getLabel(Object o) {
		String t = typeOf(o);
		if (o instanceof Compartment) {
			return t + " " + ((Compartment) o).getLabels();
		} else if (o instanceof Flow) {
			return t + " " + ((Flow) o).getId();
		} else
			return t;
	}
	
	public String typeOf(Object o) {
		String temp = o.getClass().getSimpleName();
		if (temp.endsWith("Impl"))
			return temp.substring(0, temp.length() - 4);
		else
			return temp;
	}
}
