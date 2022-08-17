package org.epimodel;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.SWT;
import org.epimodel.natures.EpimodelProjectNature;


/*
 * Copied from:
 * 
 * - Writing an Eclipse Plug-in (Part 4): Create a Custom Project in Eclipse – New Project Wizard: the Behavior
 * - July 26, 2009, cvalcarcel
 * - https://cvalcarcel.wordpress.com/2009/07/26/writing-an-eclipse-plug-in-part-4-create-a-custom-project-in-eclipse-new-project-wizard-the-behavior/
 * 
 */


public class CustomProjectSupport {
    
    public static IProject createProject(
		String projectName,
		URI location,
		List<String> availableExtensions,
		List<Boolean> extensionTruthValues
    ) {
        Assert.isNotNull(projectName);
        Assert.isTrue(projectName.trim().length() > 0);
 
        IProject project = createBaseProject(projectName, location);
        try {
            addNature(project);

            String[] folder_paths = { "parent/child1-1/child2", "parent/child1-2/child2/child3" };
            String[] file_paths = { "parent/child1-1/child2/test.xml", "parent/child1-2/child2/child3/file.txt", "extensions.txt" };
            List<IFile> files = addToProjectStructure(project, folder_paths, file_paths);
            
            files.get(2);

			for (int i = 0; i < availableExtensions.size(); ++i)
				files.get(2).appendContents(
						new ByteArrayInputStream((availableExtensions.get(i) + ": " + extensionTruthValues.get(i) + "\n").getBytes()),
						SWT.NONE,
						null);
            
            try {
    			PrintWriter writer = new PrintWriter("extensions.txt", "UTF-8");
    			writer.close();
    		} catch (FileNotFoundException | UnsupportedEncodingException e) {
    			e.printStackTrace();
                project = null;
    		}
            
        } catch (CoreException e) {
            e.printStackTrace();
            project = null;
        }
 
        return project;
    }
 
    private static IProject createBaseProject(String projectName, URI location) {
        // it is acceptable to use the ResourcesPlugin class
        IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
 
        if (!newProject.exists()) {
            URI projectLocation = location;
            IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
            if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location))
                projectLocation = null;
 
            desc.setLocationURI(projectLocation);
            try {
                newProject.create(desc, null);
                if (!newProject.isOpen()) {
                    newProject.open(null);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
 
        return newProject;
    }
 
    private static List<IFile> addToProjectStructure(IProject newProject, String[] folders, String[] files) throws CoreException {
        for (String path : folders)
            createFolder(newProject.getFolder(path));
        List<IFile> l = new ArrayList<>(files.length);
        for (String path : files) {
        	IFile f = newProject.getFile(path);
        	createFile(f);
        	l.add(f);
        }
        return l;
    }
 
    private static void createFolder(IFolder folder) throws CoreException {
        IContainer parent = folder.getParent();
        if (parent instanceof IFolder)
            createFolder((IFolder) parent);
        if (!folder.exists())
            folder.create(false, true, null);
    }
    
    private static void createFile(IFile file) throws CoreException {
    	file.create(
    		new ByteArrayInputStream("".getBytes()),
    		IResource.ALLOW_MISSING_LOCAL | IResource.FILE,
    		null);
    }
 
    private static void addNature(IProject project) throws CoreException {
    	if (project.hasNature(EpimodelProjectNature.NATURE_ID))
    		return;
        IProjectDescription description = project.getDescription();
        {
            String[] prevNatures = description.getNatureIds();
            String[] newNatures = new String[prevNatures.length + 1];
            System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
            newNatures[prevNatures.length] = EpimodelProjectNature.NATURE_ID;
            description.setNatureIds(newNatures);
        }
        project.setDescription(description, null);
    }
}