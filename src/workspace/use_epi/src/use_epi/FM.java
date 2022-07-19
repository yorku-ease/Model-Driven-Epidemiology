package use_epi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.ovgu.featureide.fm.core.analysis.cnf.IVariables;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.ACreator;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IConstraint;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.IFeatureModelElement;
import de.ovgu.featureide.fm.core.base.IFeatureModelStructure;
import de.ovgu.featureide.fm.core.base.IFeatureStructure;
import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.FMFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.Feature;
import de.ovgu.featureide.fm.core.base.impl.FeatureModel;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.ConfigurationAnalyzer;
import de.ovgu.featureide.fm.core.configuration.SelectableFeature;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.init.FMCoreLibrary;
import de.ovgu.featureide.fm.core.init.LibraryManager;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;

public class FM {
	public static void main(String[] args) throws Exception {
		
		LibraryManager.registerLibrary(FMCoreLibrary.getInstance());
		IFeatureModel fm = FeatureModelManager.load(Paths.get("C:/Users/Bruno/Desktop/feature-model.xml"));
		FeatureModelFormula fmf = new FeatureModelFormula(fm);
		Configuration conf = new Configuration(fmf);
//		List<String> selected = conf.getSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
//		List<String> unselected = conf.getUnSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
//		List<String> undefined = conf.getUndefinedSelectedFeatures().stream().map(IFeatureModelElement::getName).collect(Collectors.toList());
		
		ConfigurationAnalyzer ca = new ConfigurationAnalyzer(fmf, conf);
		boolean v0 = ca.canBeValid();
		conf.setManual("Vaccination", Selection.SELECTED);
		boolean v1 = ca.canBeValid();
		conf.setManual("VaccinationCompartment", Selection.UNSELECTED);
		
		for (List<IConstraint> bad : getErrors(fm, conf, 10))
			System.out.println(new FeatureModelConfigurationError(bad));
		
		boolean v2 = ca.canBeValid();
		return;
	}
	
	public static boolean isValid(IFeatureModel fm, List<IConstraint> constraints) {
		List<IConstraint> saved = fm.getConstraints();
		
		fm.setConstraints(constraints);
		FeatureModelFormula fmf = new FeatureModelFormula(fm);
		Configuration conf = new Configuration(fmf);
		ConfigurationAnalyzer ca = new ConfigurationAnalyzer(fmf, conf);
		boolean res = ca.isValid();
		
		fm.setConstraints(saved);
		
		return res;
	}
	
	public static List<List<IConstraint>> getErrors(IFeatureModel fm, Configuration conf, int maxSearchComplexity) {
		return getErrors(fm, conf, maxSearchComplexity, fm.getConstraints(), new ArrayList<>());
	}
	
	public static List<List<IConstraint>> getErrors(
			IFeatureModel fm,
			Configuration conf,
			int maxSearchComplexity,
			List<IConstraint> allConstraints,
			List<IConstraint> alreadyPresentConstraints) {
		
		List<List<IConstraint>> potential = allConstraints
			.stream()
			.filter(c-> !alreadyPresentConstraints.contains(c))
			.map(c -> newListFromListAndNewElement(alreadyPresentConstraints, c))
			.collect(Collectors.toList());
		
		List<List<IConstraint>> errorsAtThisLevel = potential
			.stream()
			.filter(l -> !isValid(fm, l))
			.collect(Collectors.toList());

		if (!errorsAtThisLevel.isEmpty())
			return errorsAtThisLevel;
		else
			return potential
					.stream()
					.map(l -> getErrors(fm, conf, maxSearchComplexity - 1, allConstraints, l))
					.flatMap(List::stream)
					.collect(Collectors.toList());
	}
	
	public static <T> List<T> newListFromListAndNewElement(List<T> l, T elem) {
		List<T> l2 = new ArrayList<>(l);
		l2.add(elem);
		return l2;
	}
	
	public static class FeatureModelConfigurationError {
		final List<IConstraint> errors;
		
		public FeatureModelConfigurationError(List<IConstraint> errors) {
			this.errors = errors;
		}
		
		@Override
		public String toString() {
			return errors
					.stream()
					.map(e -> e.getDisplayName() + ": " + e.getDescription())
					.collect(Collectors.toList())
					.toString();
		}
	}
}