package my.project.design;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

/**
 * The services class used by VSM.
 */
public class Services {
    
	@SuppressWarnings("unchecked")
	public List<EObject> flowTargets(EObject self) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	Class<?> objclass = self.getClass();
    	String methodName = "getTargetObjects";
        return (List<EObject>) objclass.getMethod(methodName).invoke(self);
    }
    
//    public Collection<EObject> childrenOfChildren(EObject self) {
//    	return self.eContents().stream().map(EObject::eContents).flatMap(List::stream).collect(Collectors.toList());
//    }
    
	public String flowLabel(EObject self, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	target = invoke(target, "getTargetNode");
    	target = invoke(target, "getTarget");
        return (String) self.getClass().getMethod("getTargetRelation", EObject.class).invoke(self, target);
    }
    
	public String flowLabel2(EObject self, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// view.oclAsType(DEdge).targetNode.oclAsType(DSemanticDecorator).target.toString()
    	Class<?> objclass = target.getClass();
    	Method[] ms = objclass.getMethods();
    	return Arrays.asList(ms).stream().map(m->m.getName()).collect(Collectors.joining(", "));
    }
	
	Object invoke(Object obj, String method, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return obj.getClass().getMethod(method).invoke(obj, args);
	}
}
