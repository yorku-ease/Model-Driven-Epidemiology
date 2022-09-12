package org.epimodel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;

import epimodel.impl.EpimodelPackageImpl;

public class ExternalJavaActionEditFlow implements IExternalJavaAction {

	@Override
	public boolean canExecute(Collection<? extends EObject> selections) {
		// we don't use this selection anyway
		return true;
	}

	@Override
	public void execute(Collection<? extends EObject> selections, Map<String, Object> parameters) {
		EObject source = (EObject) parameters.get("source");
		EObject target = (EObject) parameters.get("target");
		if (source == null || target == null)
			return;
		
		List<EClass> eclasses = EpimodelPackageImpl.collectEClasses();
		System.out.println(selections);
		System.out.println(parameters);
	}
}
