package my.project.design;

import epimodel.Flow;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;

public class Services {
	public List<EObject> flowTargets(Flow flow) {
    	return flow.getTargetObjects();
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
    
    // to understand services that require parameters
    // https://www.eclipse.org/forums/index.php/m/1854385/#msg_1854385
	public String flowLabel(Flow flow, EObject target) {
    	return flow.getTargetRelation(target);
    }
}
