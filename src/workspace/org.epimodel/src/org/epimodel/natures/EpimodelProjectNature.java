package org.epimodel.natures;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class EpimodelProjectNature implements IProjectNature {
	
	public static final String NATURE_ID = "org.epimodel.EpimodelProjectNature";
	
	protected IProject project;
	
	@Override
	public void configure() throws CoreException {
		System.out.println("configure");
	}

	@Override
	public void deconfigure() throws CoreException {
		System.out.println("deconfigure");
	}

	@Override
	public IProject getProject() {
		System.out.println("getProject");
		return project;
	}

	@Override
	public void setProject(IProject project) {
		System.out.println("setProject");
		this.project = project;
	}
}
