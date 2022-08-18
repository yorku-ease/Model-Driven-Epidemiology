package org.epimodel;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.swt.SWT;
import org.epimodel.natures.EpimodelProjectNature;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.tools.api.command.semantic.AddSemanticResourceCommand;
import org.eclipse.sirius.ui.tools.api.project.ModelingProjectManager;

import epimodel.impl.EpimodelFactoryImpl;

public class CustomProjectSupport {
    
    public static IProject createProject(
		String projectName,
		java.net.URI location,
		List<String> availableExtensions,
		List<Boolean> extensionTruthValues
    ) {
        Assert.isNotNull(projectName);
        Assert.isTrue(projectName.trim().length() > 0);
        IProject project = createBaseProject(projectName, location);
        try {
            addNature(project, "org.eclipse.sirius.nature.modelingproject");
            addNature(project, EpimodelProjectNature.NATURE_ID);

//            List<String> folder_paths = Arrays.asList("modeling");
//            List<String> file_paths = Arrays.asList("modeling/model.epimodel", "extensions.txt");
            List<String> folder_paths = Arrays.asList();
            List<String> file_paths = Arrays.asList("extensions.txt");
            List<IFile> files = addToProjectStructure(project, folder_paths, file_paths);

			for (int i = 0; i < availableExtensions.size(); ++i)
				files.get(file_paths.indexOf("extensions.txt")).appendContents(
						new ByteArrayInputStream((availableExtensions.get(i) + ": " + extensionTruthValues.get(i) + "\n").getBytes()),
						SWT.NONE,
						null);
			
			String model_fn = projectName + ".epimodel";
			String model_fn_path = project.getFile(model_fn).getLocationURI().toString().substring(6);
			
			createEpimodel(model_fn_path);
			
			Session newSession = ModelingProjectManager.INSTANCE.createLocalRepresentationsFile(project, new NullProgressMonitor());
			AddSemanticResourceCommand addSemCommand = new AddSemanticResourceCommand(newSession, URI.createFileURI(model_fn_path), new NullProgressMonitor());
			newSession.getTransactionalEditingDomain().getCommandStack().execute(addSemCommand);
			
//			new AddSemanticResourceCommand(newSession, null, null);
//			Set<ISessionFileLoadingListener> sessionFileLoadingListeners = SiriusEditPlugin.getPlugin().getSessionFileLoadingListeners();
//            for (ISessionFileLoadingListener sessionFileLoadingListener : sessionFileLoadingListeners) {
//                sessionFileLoadingListener.notifySessionLoadedFromModelingProject(newSession);
//            }
//			files.get(file_paths.indexOf("representations.aird")).appendContents(new ByteArrayInputStream("".getBytes()), SWT.NONE, null);

			
            try {
    			PrintWriter writer = new PrintWriter("extensions.txt", "UTF-8");
    			writer.close();
    		} catch (FileNotFoundException | UnsupportedEncodingException e) {
    			e.printStackTrace();
                project = null;
    		}
            
        } catch (CoreException | IOException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            project = null;
        }
 
        return project;
    }
 
    private static IProject createBaseProject(String projectName, java.net.URI location) {
        IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        if (newProject.exists())
        	return newProject;
        
        IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
        if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location))
            desc.setLocationURI(null);
        else
        	desc.setLocationURI(location);
        
        try {
            newProject.create(desc, null);
            if (!newProject.isOpen())
                newProject.open(null);
        } catch (CoreException e) {
            e.printStackTrace();
        }
        return newProject;
    }
 
    private static List<IFile> addToProjectStructure(IProject newProject, List<String> folders, List<String> files) throws CoreException {
        for (String path : folders)
            createFolder(newProject.getFolder(path));
        
        List<IFile> l = new ArrayList<>(files.size());
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
 
	private static void addNature(IProject project, String nature) throws CoreException {
    	if (project.hasNature(nature))
    		return;
    	
        IProjectDescription description = project.getDescription();
        {
            String[] prevNatures = description.getNatureIds();
            String[] newNatures = new String[prevNatures.length + 1];
            System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
            newNatures[prevNatures.length] = nature;
            description.setNatureIds(newNatures);
        }
        project.setDescription(description, null);
    }
	
//	private static void printNatures(String projectName) {
//        IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
//        if (newProject.exists()) {
//        	try {
//        		for (String nature : newProject.getDescription().getNatureIds())
//        			System.out.println(nature);
//			} catch (CoreException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//	}
	
	private static void createEpimodel(String model_fn) throws IOException {
		Resource.Factory.Registry factoryRegistry = new ResourceFactoryRegistryImpl(); {
			factoryRegistry.getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());
		}
		
        EPackage.Registry pkgRegistry = new EPackageRegistryImpl(); {
	        pkgRegistry.put(epimodel.EpimodelPackage.eNS_URI, epimodel.EpimodelPackage.eINSTANCE);
		}
        
        ResourceSet resSet = new ResourceSetImpl(); {
	        resSet.setPackageRegistry(pkgRegistry);
	        resSet.setResourceFactoryRegistry(factoryRegistry);
        }
        
		{
			org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI.createFileURI(model_fn);
	        Resource resource = resSet.createResource(uri);
	        resource.getContents().add(EpimodelFactoryImpl.eINSTANCE.createEpidemicWrapper());
	        resource.save(null);
			System.out.println(uri.toFileString());
		}
	}
}