package use_epi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

import epimodel.util.PhysicalCompartment;

public class Compare {
	public static void main(String[] args) throws Exception {
		String model1 = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR_copy.epimodel";
		String model2 = "../../runtime-EclipseApplication/modeling/DEPGG_COVID_INF_VAR_SEIR.epimodel";
		compare(model1, model2);
	}
	
	private static void compare(String model1, String model2) throws Exception {
		System.out.println("Comparing " + model1 + " and " + model2);
		compare((epimodel.EpidemicWrapper) loadModel(model1), (epimodel.EpidemicWrapper) loadModel(model2));
	}
	
	private static EObject loadModel(String model_fn) throws Exception {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl();
        factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		
        ResourceSet resSet = new ResourceSetImpl();
        resSet.setPackageRegistry(EPackage.Registry.INSTANCE);
        resSet.setResourceFactoryRegistry(factoryRegistry);
        
        EPackage.Registry.INSTANCE.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
        
        URI uri = URI.createFileURI(model_fn);
        Resource resource = resSet.getResource(uri, true);
        
        return resource.getContents().get(0);
	}
	
	private static void compare(epimodel.EpidemicWrapper model1, epimodel.EpidemicWrapper model2) throws Exception {
		System.out.println("Comparing Model 1 (Left):");
		for (PhysicalCompartment pc : model1.getEpidemic().getPhysicalCompartments())
			System.out.println("\t" + pc.labels);
		System.out.println("With Model 2 (Right):");
		for (PhysicalCompartment pc : model2.getEpidemic().getPhysicalCompartments())
			System.out.println("\t" + pc.labels);
		physicalMatch(
			model1
				.getEpidemic()
				.getPhysicalCompartments(),
			model2
				.getEpidemic()
				.getPhysicalCompartments());
	}
	
	private static PhysicalMatchResult physicalMatch(List<PhysicalCompartment> left, List<PhysicalCompartment> right) throws Exception {
		return physicalMatchLists(
			left.stream()
				.map(pc -> pc.labels)
				.collect(Collectors.toList()),
			right.stream()
				.map(pc -> pc.labels)
				.collect(Collectors.toList()));
	}
	
	static PhysicalMatchResult physicalMatchLists(List<List<String>> left, List<List<String>> right) {
		PhysicalMatchResult res = new PhysicalMatchResult(left, right);

		for (List<String> l : left) {
			List<List<String>> val = right.stream().filter(u -> u.containsAll(l)).collect(Collectors.toList());
			if (val.size() != 0)
				if (val.size() == 1) {
					res.mappingsLeft.add(l);
					res.mappingsRight.add(val.get(0));
				} else
					res.specializations.put(l, val);
			else
				res.substractions.add(l);
		}
		
		for (List<String> l : right) {
			List<List<String>> val = left.stream().filter(u -> u.containsAll(l)).collect(Collectors.toList());
			if (val.size() != 0)
				if (val.size() == 1) {
					boolean duplicate = l.equals(val.get(0));
					if (duplicate)
						continue;
					res.mappingsLeft.add(val.get(0));
					res.mappingsRight.add(l);
				} else
					res.generalizations.put(l, val);
			else
				res.additions.add(l);
		}
		
		for (List<String> m : res.mappingsLeft)
			res.substractions.remove(m);
		
		for (List<String> m : res.mappingsRight)
			res.additions.remove(m);

		for (List<List<String>> ll : res.specializations.values())
			for (List<String> l : ll)
				res.additions.remove(l);

		for (List<List<String>> ll : res.generalizations.values())
			for (List<String> l : ll)
				res.substractions.remove(l);
		
//		System.out.println(res);
		
		return res;
	}
	
	static class PhysicalMatchResult {
		public final List<List<String>> leftModel;
		public final List<List<String>> rightModel;
		public final Map<List<String>, List<List<String>>> specializations;
		public final Map<List<String>, List<List<String>>> generalizations;
		public final List<List<String>> mappingsLeft;
		public final List<List<String>> mappingsRight;
		public final List<List<String>> additions;
		public final List<List<String>> substractions;
		
		public PhysicalMatchResult(
			List<List<String>> leftModel,
			List<List<String>> rightModel,
			Map<List<String>, List<List<String>>> specializations,
			Map<List<String>, List<List<String>>> generalizations,
			List<List<String>> mappingsLeft,
			List<List<String>> mappingsRight,
			List<List<String>> additions,
			List<List<String>> substractions
		) {
			this.leftModel = leftModel;
			this.rightModel = rightModel;
			this.specializations = specializations;
			this.generalizations = generalizations;
			this.mappingsLeft = mappingsLeft;
			this.mappingsRight = mappingsRight;
			this.additions = additions;
			this.substractions = substractions;
		}
		
		public PhysicalMatchResult(
			List<List<String>> leftModel,
			List<List<String>> rightModel
		) {
			this.leftModel = leftModel;
			this.rightModel = rightModel;
			this.specializations = new HashMap<>();
			this.generalizations = new HashMap<>();
			this.mappingsLeft = new ArrayList<>();
			this.mappingsRight = new ArrayList<>();
			this.additions = new ArrayList<>();
			this.substractions = new ArrayList<>();
		}
		
		boolean isValidResult() {
			int computedLeftModelSize = specializations.size() +
					mappingsLeft.size() +
					generalizations.values().stream().map(List::size).reduce(0, Integer::sum) +
					substractions.size();
			int computedRightModelSize = generalizations.size() +
					mappingsRight.size() +
					specializations.values().stream().map(List::size).reduce(0, Integer::sum) +
					additions.size();
			int ls = leftModel.size();
			int rs = rightModel.size();
			return computedLeftModelSize == ls && computedRightModelSize == rs;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Additions:\n");
			for (int i = 0; i < additions.size(); ++i) {
				sb.append(additions.get(i));
				sb.append("\n");
			}
			sb.append("Substractions:\n");
			for (int i = 0; i < substractions.size(); ++i) {
				sb.append(substractions.get(i));
				sb.append("\n");
			}
			sb.append("Mappings:\n");
			for (int i = 0; i < mappingsLeft.size(); ++i) {
				sb.append(mappingsLeft.get(i));
				sb.append(" -> ");
				sb.append(mappingsRight.get(i));
				sb.append("\n");
			}
			sb.append("Specializations:\n");
			for (Entry<List<String>, List<List<String>>> x : specializations.entrySet()) {
				sb.append(x.getKey());
				sb.append(" -> ");
				sb.append(x.getValue());
				sb.append("\n");
			}
			sb.append("Generalizations:\n");
			for (Entry<List<String>, List<List<String>>> x : generalizations.entrySet()) {
				sb.append(x.getValue());
				sb.append(" -> ");
				sb.append(x.getKey());
				sb.append("\n");
			}
			return sb.toString();
		}
	}
}