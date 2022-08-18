package my.project.design;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    public Collection<EObject> childrenOfChildrenAndChildrenOfChildrenOfChildren(EObject self) {
    	Stream<EObject> l1 = self
    			.eContents()
    			.stream()
    			.map(EObject::eContents)
    			.flatMap(List::stream);
    	Stream<EObject> l2 = self
    			.eContents()
    			.stream()
    			.map(EObject::eContents)
    			.flatMap(List::stream)
    			.map(EObject::eContents)
    			.flatMap(List::stream);
    	return Stream.concat(l1, l2).collect(Collectors.toList());
    }
    
    // https://www.eclipse.org/forums/index.php/m/1854385/#msg_1854385
	public String flowLabel(EObject self, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
    	target = invoke(target, "getTargetNode");
    	target = invoke(target, "getTarget");
        return (String) self.getClass().getMethod("getTargetRelation", EObject.class).invoke(self, target);
    }
	
	Object invoke(Object obj, String method, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return obj.getClass().getMethod(method).invoke(obj, args);
	}
}
