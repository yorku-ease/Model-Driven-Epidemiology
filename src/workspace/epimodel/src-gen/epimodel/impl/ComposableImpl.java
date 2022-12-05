/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Composable;
import epimodel.EpimodelPackage;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.util.Comparison;
import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.Match;
import epimodel.util.Comparison.MatchResult;
import epimodel.util.PhysicalCompartment;
import epimodel.util.PhysicalFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composable</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ComposableImpl extends MinimalEObjectImpl.Container implements Composable {

	@Override
	public final Difference compare(Composable other, MatchResult matches) {
		return getClass().equals(other.getClass()) ?
				compareWithSameClass(other, matches) :
				compareWithDifferentClass(other, matches);
	}
	
	
	/*
	 * When we know 2 composables are of the same class, we
	 * can try to find the composability attributes ourselves
	 * by making use of the metamodel.
	 * 
	 * Classes can override this method to call `defaultSameClassCompare(Composable other, MatchResult matches, EReferenceImpl compartmentsFeature, EReferenceImpl flowsFeature)`
	 * directly with the correct features but if they have a single Compartment feature and one or none Flow feature this method will find them
	 */
	@Override
	public Difference compareWithSameClass(Composable other, MatchResult matches) {
		EReferenceImpl compartmentsFeature = null;
		EReferenceImpl flowsFeature = null;
		
		// identify the compartment and flow features, making sure there is at most 1 of each
		for (EStructuralFeature feature : eClass().getEAllStructuralFeatures()) {
			if (feature instanceof EReferenceImpl) {
				EReferenceImpl eref = (EReferenceImpl) feature;
				if (eref.getEReferenceType().equals(epimodel.EpimodelPackage.Literals.COMPARTMENT_WRAPPER)) {
					if (compartmentsFeature != null)
						throw new RuntimeException(
							"Found two features for compartments in class " + 
							getClass().getSimpleName() + ": " + 
							compartmentsFeature.getName() + " & " + eref.getName());
					else
						compartmentsFeature = eref;
				}
				else if (eref.getEReferenceType().equals(epimodel.EpimodelPackage.Literals.FLOW_WRAPPER)) {
					if (flowsFeature != null)
						throw new RuntimeException(
							"Found two features for flows in class " + 
							getClass().getSimpleName() + ": " + 
							flowsFeature.getName() + " & " + eref.getName());
					else
						flowsFeature = eref;
				}
			}
		}
		
		// at this point we haven't checked if the features are null but a correct metamodel shouldn't yield null features
		// the only problem might be that a composability tool offers no support for flows, in which case we can apply
		// the comparison to the compartments only
		return defaultSameClassCompare(other, matches, compartmentsFeature, flowsFeature);
	}
	
	public Difference defaultSameClassCompare(Composable other, MatchResult matches, EReferenceImpl compartmentsFeature, EReferenceImpl flowsFeature) {
		@SuppressWarnings("unchecked")
		List<CompartmentWrapper> l1 = (List<CompartmentWrapper>) eGet(compartmentsFeature);
		@SuppressWarnings("unchecked")
		List<CompartmentWrapper> l2 = (List<CompartmentWrapper>) other.eGet(compartmentsFeature);
		List<Compartment> myCompartments = l1.stream().map(w -> w.getCompartment()).collect(Collectors.toList());
		List<Compartment> otherCompartments = l2.stream().map(w -> w.getCompartment()).collect(Collectors.toList());
		
		if (flowsFeature == null)
			return Comparison.createDifference(this, other, matches, myCompartments, otherCompartments, null, null);

		@SuppressWarnings("unchecked")
		List<FlowWrapper> lf1 = (List<FlowWrapper>) eGet(flowsFeature);
		@SuppressWarnings("unchecked")
		List<FlowWrapper> lf2 = (List<FlowWrapper>) other.eGet(flowsFeature);
		List<Flow> myFlows = lf1.stream().map(w -> w.getFlow()).collect(Collectors.toList());
		List<Flow> otherFlows = lf2.stream().map(w -> w.getFlow()).collect(Collectors.toList());
		
		return Comparison.createDifference(this, other, matches, myCompartments, otherCompartments, myFlows, otherFlows);
	}
	
	@Override
	public Difference compareWithDifferentClass(Composable other, MatchResult matches) {
		Match match = null;
		
		try {
			match = matches.find(this, other);
		} catch (Exception e) {
			// Comparing unmatched objects, result may be slightly innacurate
			match = new Match(this, other);
		}
		
		boolean sameClass = getClass().equals(other.getClass()); // in case same class comparison was deferred
		
		List<PhysicalCompartment> l1 = getPhysicalCompartments();
		List<PhysicalCompartment> l2 = other.getPhysicalCompartments();
		boolean sameCompartments = l1.equals(l2);

		List<PhysicalFlow> lf1 = getPhysicalFlows();
		List<PhysicalFlow> lf2 = other.getPhysicalFlows();
		boolean sameFlows = lf1.equals(lf2);
		
		StringBuilder sb = new StringBuilder();
		{
			sb.append(getClass().getSimpleName() + " " + getLabels());
			if (!sameClass)
				sb.append(" was retyped and ");
			if (sameCompartments)
				sb.append(" exactly matches ");
			else
				sb.append(" does not exactly match ");
			sb.append(other.getClass().getSimpleName() + " " + other.getLabels());
			if (!sameCompartments)
				diffCompartments(sb, l1, l2);
			if (!sameFlows)
				diffFlows(sb, lf1, lf2);
		}
		String description = sb.toString();
		
		return new Difference(Arrays.asList(match), new ArrayList<>(), new ArrayList<>(), false, description);
	}
	
	void diffCompartments(StringBuilder sb, List<PhysicalCompartment> l1, List<PhysicalCompartment> l2) {
		int addedCompartments = l2.size() - l2.stream().filter(l1::contains).collect(Collectors.toList()).size();
		int removedCompartments = l1.size() + addedCompartments - l2.size();
		sb.append(" original model produces " + l1.size() + " physical compartments and other model produces " + l2.size());
		sb.append(": " + (l1.size() - removedCompartments) + " matched, ");
		sb.append(addedCompartments + " added and " + removedCompartments + " removed.");
	}
	
	void diffFlows(StringBuilder sb, List<PhysicalFlow> l1, List<PhysicalFlow> l2) {
		int addedFlows = l2.size() - l2.stream().filter(l1::contains).collect(Collectors.toList()).size();
		int removedFlows = l1.size() + addedFlows - l2.size();
		sb.append(" original model produces " + l1.size() + " physical flows and other model produces " + l2.size());
		sb.append(": " + (l1.size() - removedFlows) + " matched, ");
		sb.append(addedFlows + " added and " + removedFlows + " removed.");
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.COMPOSABLE;
	}

} //ComposableImpl
