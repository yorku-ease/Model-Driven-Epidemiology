/**
 */
package epimodel.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EReferenceImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import epimodel.Compartment;
import epimodel.CompartmentWrapper;
import epimodel.EpimodelPackage;
import epimodel.Flow;
import epimodel.FlowWrapper;
import epimodel.util.Comparison.Difference;
import epimodel.util.Comparison.Match;
import epimodel.util.Comparison.MatchResult;
import epimodel.util.PhysicalFlow;
import epimodel.util.Comparison;
import epimodel.util.PhysicalCompartment;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link epimodel.impl.CompartmentImpl#getLabel <em>Label</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentImpl extends MinimalEObjectImpl.Container implements Compartment {

	@Override
	public final Difference compare(Compartment other, MatchResult matches) {
		return getClass().equals(other.getClass()) ? compareWithSameClass(other, matches)
				: compareWithDifferentClass(other, matches);
	}

	@Override
	public Difference compareWithSameClass(Compartment other, MatchResult matches) {
		if (getClass().equals(CompartmentImpl.class)) {
			return new Difference(matches.find(this, other).get(), Arrays.asList(matches.find(this, other).get()),
					new ArrayList<>(), new ArrayList<>(), Optional.empty(), getLabels().equals(other.getLabels()),
					"Compartment " + getLabels() + " matches Compartment " + other.getLabels());
		} else
			return compareWithSameClassNonCompartmentImpl(other, matches);
	}

	/*
	 * When we know 2 compartments are of the same class, we
	 * can try to find the composability attributes ourselves
	 * by making use of the metamodel.
	 * 
	 * Classes can override this method to call `defaultSameClassCompare(Compartment other, MatchResult matches, EReferenceImpl compartmentsFeature, EReferenceImpl flowsFeature)`
	 * directly with the correct features but if they have a single Compartment feature and one or none Flow feature this method will find them
	 */
	Difference compareWithSameClassNonCompartmentImpl(Compartment other, MatchResult matches) {
		EReferenceImpl compartmentsFeature = null;
		EReferenceImpl flowsFeature = null;

		// identify the compartment and flow features, making sure there is at most 1 of each
		for (EStructuralFeature feature : eClass().getEAllStructuralFeatures()) {
			if (feature instanceof EReferenceImpl) {
				EReferenceImpl eref = (EReferenceImpl) feature;
				if (eref.isContainment()
						&& eref.getEReferenceType().equals(epimodel.EpimodelPackage.Literals.COMPARTMENT_WRAPPER)) {
					if (compartmentsFeature != null)
						// developper error, if there are 2 features for compartments, the dev has to provide an implementation for same class compare
						throw new RuntimeException(
								"Found two features for compartments in class " + getClass().getSimpleName() + ": "
										+ compartmentsFeature.getName() + " & " + eref.getName());
					else
						compartmentsFeature = eref;
				} else if (eref.getEReferenceType().equals(epimodel.EpimodelPackage.Literals.FLOW_WRAPPER)) {
					if (flowsFeature != null)
						// developper error, if there are 2 features for flows, the dev has to provide an implementation for same class compare
						throw new RuntimeException("Found two features for flows in class " + getClass().getSimpleName()
								+ ": " + flowsFeature.getName() + " & " + eref.getName());
					else
						flowsFeature = eref;
				}
			}
		}

		// at this point we haven't checked if the features are null but a correct metamodel shouldn't yield null features
		// the following implementation assumes for sure that the compartmentsFeature is NOT null
		// flows can be null as it might not make sense for each metamodel class to own flows, such as a compartment link for example
		return defaultSameClassCompare(other, matches, compartmentsFeature, flowsFeature);
	}

	public Difference defaultSameClassCompare(Compartment other, MatchResult matches,
			EReferenceImpl compartmentsFeature, EReferenceImpl flowsFeature) {
		@SuppressWarnings("unchecked")
		List<CompartmentWrapper> l1 = (List<CompartmentWrapper>) eGet(compartmentsFeature);
		@SuppressWarnings("unchecked")
		List<CompartmentWrapper> l2 = (List<CompartmentWrapper>) other.eGet(compartmentsFeature);
		List<Compartment> myCompartments = l1.stream().map(CompartmentWrapper::getCompartment).toList();
		List<Compartment> otherCompartments = l2.stream().map(CompartmentWrapper::getCompartment).toList();

		if (flowsFeature == null)
			return Comparison.createDifference(this, other, matches, myCompartments, otherCompartments, null, null);

		@SuppressWarnings("unchecked")
		List<FlowWrapper> lf1 = (List<FlowWrapper>) eGet(flowsFeature);
		@SuppressWarnings("unchecked")
		List<FlowWrapper> lf2 = (List<FlowWrapper>) other.eGet(flowsFeature);
		List<Flow> myFlows = lf1.stream().map(FlowWrapper::getFlow).toList();
		List<Flow> otherFlows = lf2.stream().map(FlowWrapper::getFlow).toList();

		return Comparison.createDifference(this, other, matches, myCompartments, otherCompartments, myFlows,
				otherFlows);
	}

	@Override
	public Difference compareWithDifferentClass(Compartment other, MatchResult matches) {
		Match match = matches.find(this, other).orElse(new Match(this, other));

		boolean sameClass = getClass().equals(other.getClass()); // in case same class comparison was deferred

		List<PhysicalCompartment> l1 = getPhysicalCompartments();
		List<PhysicalCompartment> l2 = other.getPhysicalCompartments();
		boolean sameCompartments = l1.equals(l2);

		List<PhysicalFlow> lf1 = getEquations();
		List<PhysicalFlow> lf2 = other.getEquations();
		boolean sameFlows = lf1.equals(lf2);

		StringBuilder sb = new StringBuilder();
		{
			sb.append(getClass().getSimpleName() + " " + getLabels());
			if (!sameClass)
				sb.append(" was retyped and");
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

		return new Difference(match, Arrays.asList(match), new ArrayList<>(), new ArrayList<>(), Optional.empty(),
				false, description);
	}

	void diffCompartments(StringBuilder sb, List<PhysicalCompartment> l1, List<PhysicalCompartment> l2) {
		int addedCompartments = l2.size() - l2.stream().filter(l1::contains).toList().size();
		int removedCompartments = l1.size() + addedCompartments - l2.size();
		sb.append(" original model produces " + l1.size() + " physical compartments and other model produces "
				+ l2.size());
		sb.append(": " + (l1.size() - removedCompartments) + " matched, ");
		sb.append(addedCompartments + " added and " + removedCompartments + " removed.");
	}

	void diffFlows(StringBuilder sb, List<PhysicalFlow> l1, List<PhysicalFlow> l2) {
		int addedFlows = l2.size() - l2.stream().filter(l1::contains).toList().size();
		int removedFlows = l1.size() + addedFlows - l2.size();
		sb.append(" original model produces " + l1.size() + " physical flows and other model produces " + l2.size());
		sb.append(": " + (l1.size() - removedFlows) + " matched, ");
		sb.append(addedFlows + " added and " + removedFlows + " removed.");
	}

	@Override
	public List<PhysicalCompartment> getPhysicalCompartments() {
		if (getClass() != CompartmentImpl.class)
			throw new RuntimeException(
					"Method `getPhysicalCompartments` not implemented for derived class " + getClass().getSimpleName());
		return new ArrayList<>(Arrays.asList(new PhysicalCompartment(getLabel())));
	}

	@Override
	public List<PhysicalCompartment> getSources() {
		if (getClass() != CompartmentImpl.class)
			throw new RuntimeException("Not implemented");
		return getPhysicalCompartments();
	}

	@Override
	public List<PhysicalCompartment> getSinks() {
		if (getClass() != CompartmentImpl.class)
			throw new RuntimeException("Not implemented");
		return getPhysicalCompartments();
	}

	@Override
	public List<PhysicalFlow> getEquations() {
		if (getClass() != CompartmentImpl.class)
			throw new RuntimeException(
					"Method `getPhysicalFlows` not implemented for derived class " + getClass().getSimpleName());
		return new ArrayList<>();
	}

	@Override
	public List<String> getLabels() {
		return new ArrayList<>(getLabel());
	}

	@Override
	public List<PhysicalCompartment> getSourcesFor(PhysicalCompartment child) {
		throw new RuntimeException("Compartment has no children");
	}

	@Override
	public List<PhysicalCompartment> getSinksFor(PhysicalCompartment child) {
		throw new RuntimeException("Compartment has no children");
	}

	@Override
	public void edit(EObject dom, Shell shell, List<Control> controls) {
		shell.setText("Edit Compartment " + getLabel());
		shell.setLayout(new GridLayout(2, false));
		epimodel.util.Edit.addText(shell, controls, "Labels (comma sparated): ");
		// field you can type in
		Text t = new Text(shell, SWT.NONE);
		t.setText(getLabel().stream().collect(Collectors.joining(",")));
		t.setLayoutData(new GridData(300, 50));
		controls.add(t);
		epimodel.util.Edit.addText(shell, controls, "");
		epimodel.util.Edit.addBtn(shell, controls, "Confirm", () -> {
			epimodel.util.Edit.transact(dom, () -> {
				getLabel().clear();
				String labelsCSV = t.getText();
				for (String label : labelsCSV.split(","))
					if (label.length() > 0)
						getLabel().add(label.trim());
			});
			shell.close();
		});
	}

	// tested
	// create one copy per association of sink and source in the compartment
	public List<PhysicalFlow> replicateEquationToMatchPCs(PhysicalFlow eq, Compartment c) {
		List<PhysicalFlow> res = new ArrayList<>();
		// the source of the flow is a sink for c
		for (PhysicalCompartment source : c.getSinksFor(eq.source))
			// and the sink of the flow is a source for c
			for (PhysicalCompartment sink : c.getSourcesFor(eq.sink)) {
				PhysicalFlow cp = eq.deepCopy();
				for (PhysicalCompartment pc : cp.equationCompartments) {
					if (pc.equals(cp.source))
						pc.labels.addAll(0,
							source.labels.stream().filter(label -> !cp.source.labels.contains(label)).toList()
						);
					if (pc.equals(cp.sink))
						pc.labels.addAll(0,
							sink.labels.stream().filter(label -> !cp.sink.labels.contains(label)).toList()
						);
					if (!pc.labels.containsAll(c.getLabels()))
						pc.labels.addAll(0, c.getLabels());
				}
				cp.source.labels.addAll(0,
					source.labels.stream().filter(label -> !cp.source.labels.contains(label)).toList()
				);
				cp.sink.labels.addAll(0,
					sink.labels.stream().filter(label -> !cp.sink.labels.contains(label)).toList()
				);
				res.add(cp);
			}
		return res;
	}

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected EList<String> label;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EpimodelPackage.Literals.COMPARTMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getLabel() {
		if (label == null) {
			label = new EDataTypeUniqueEList<String>(String.class, this, EpimodelPackage.COMPARTMENT__LABEL);
		}
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			return getLabel();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			getLabel().clear();
			getLabel().addAll((Collection<? extends String>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			getLabel().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case EpimodelPackage.COMPARTMENT__LABEL:
			return label != null && !label.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (label: ");
		result.append(label);
		result.append(')');
		return result.toString();
	}
} //CompartmentImpl
