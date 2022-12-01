/**
 */
package epimodel.impl;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.Composable;
import epimodel.EpimodelPackage;
import epimodel.util.Comparison.ChildrenDiffResult;
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
	
	@Override
	public Difference compareWithSameClass(Composable other, MatchResult matches) {
		EReferenceImpl compartmentsFeature = null;
		EReferenceImpl flowsFeature = null;
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
		
		return defaultSameClassCompare(other, matches, compartmentsFeature, flowsFeature);
	}
	
	public Difference defaultSameClassCompare(Composable other, MatchResult matches, EReferenceImpl compartmentsFeature, EReferenceImpl flowsFeature) {
		Match match = matches.find(this, other);

		@SuppressWarnings("unchecked")
		List<CompartmentWrapper> l1 = (List<CompartmentWrapper>) eGet(compartmentsFeature);
		@SuppressWarnings("unchecked")
		List<CompartmentWrapper> l2 = (List<CompartmentWrapper>) other.eGet(compartmentsFeature);
		
		// TODO flows
		
		List<Compartment> myCompartments = l1.stream().map(w -> w.getCompartment()).collect(Collectors.toList());
		List<Compartment> otherCompartments = l2.stream().map(w -> w.getCompartment()).collect(Collectors.toList());
		
		ChildrenDiffResult childrenDiffs = new ChildrenDiffResult(myCompartments, otherCompartments, matches);
		
		List<Match> accountedForMatches = new ArrayList<>(childrenDiffs.accountsForMatches);
		accountedForMatches.add(match);
		
		boolean isSame = childrenDiffs.isSame && getLabels().equals(other.getLabels());
		
		String baseDescription = compareWithDifferentClass(other, matches).getSimpleDescription();
		
		return new Difference(accountedForMatches, new ArrayList<>(childrenDiffs.myUnMatchedCompartments), new ArrayList<>(childrenDiffs.otherUnMatchedCompartments), isSame) {
			@Override public String getSimpleDescription() {
				StringBuilder sb = new StringBuilder(baseDescription);
				if (!isSame)
					sb.append(childrenDiffs.getSimpleDescription());
				return sb.toString();
			}
		};
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
		
		boolean sameClass = getClass().equals(other.getClass()); // in case same class was deffered
		
		List<PhysicalCompartment> l1 = getPhysicalCompartments();
		List<PhysicalCompartment> l2 = other.getPhysicalCompartments();
		
		boolean sameCompartments = l1.equals(l2);
		int addedCompartments = sameCompartments ? 0 : l2.size() - l2.stream().filter(l1::contains).collect(Collectors.toList()).size();
		int removedCompartments = l1.size() + addedCompartments - l2.size();

		List<PhysicalFlow> lf1 = getPhysicalFlows();
		List<PhysicalFlow> lf2 = other.getPhysicalFlows();
		boolean sameFlows = lf1.equals(lf2);
		int addedFlows = sameFlows ? 0 : lf2.size() - lf2.stream().filter(lf1::contains).collect(Collectors.toList()).size();
		int removedFlows = lf1.size() + addedFlows - lf2.size();
		
		// as you can see we can't get any useful information out of the default implementation
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
			if (!sameCompartments) {
				sb.append(" original model produces " + l1.size() + " physical compartments and other model produces " + l2.size());
				sb.append(": " + (l1.size() - removedCompartments) + " matched, ");
				sb.append(addedCompartments + " added and " + removedCompartments + " removed.");
			}
			if (!sameFlows) {
				sb.append(" original model produces " + lf1.size() + " physical flows and other model produces " + l2.size());
				sb.append(": " + (lf1.size() - removedFlows) + " matched, ");
				sb.append(addedCompartments + " added and " + removedFlows + " removed.");
			}
		}
		String description = sb.toString();
		
		return new Difference(Arrays.asList(match), new ArrayList<>(), new ArrayList<>(), false) {
			@Override public String getSimpleDescription() {
				return description;
			}
		};
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
