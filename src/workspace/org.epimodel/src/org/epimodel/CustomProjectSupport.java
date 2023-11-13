package org.epimodel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.epimodel.natures.EpimodelProjectNature;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import epimodel.Epidemic;
import epimodel.impl.EpimodelFactoryImpl;

public class CustomProjectSupport {
    public static IProject createProject(
		String projectName,
		java.net.URI location,
		List<String> pluginsEnabledManually
    ) {
        Assert.isNotNull(projectName);
        Assert.isTrue(projectName.trim().length() > 0);
        IProject project = createBaseProject(projectName, location);
        try {
            addNature(project, "org.eclipse.sirius.nature.modelingproject");
            addNature(project, EpimodelProjectNature.NATURE_ID);
            
            {
                List<String> plugins = new ArrayList<>();
                plugins.add("epimodel");
                for (String plugin : pluginsEnabledManually)
                	if (!plugins.contains(plugin))
                		plugins.add(plugin);
                createFile(
                	project,
                	"extensions.txt",
                	plugins.stream().map(p -> p + "\n").collect(Collectors.joining())
                );
            }
            {
    			String model_fn = projectName + ".epimodel";
    			String model_fn_path = project.getFile(model_fn).getLocationURI().toString().substring("file:/".length());
    			
    			createEpimodel(model_fn_path);
            }
            {
                Bundle bundle = FrameworkUtil.getBundle(CustomProjectSupport.class);
        		URL url = FileLocator.find(bundle, new org.eclipse.core.runtime.Path("representations_template.aird"));
        		String representationsTemplate = readFile(url);
        		createFile(project, "representations.aird", representationsTemplate.replace("REPLACE_HERE", projectName));
            }
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
            
        } catch (CoreException | IOException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
            project = null;
        }
 
        return project;
    }

	private static String readFile(URL url) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
	    String i;
		while ((i = read.readLine()) != null)
			sb.append(i);
		read.close();
	    return sb.toString();
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
 
//    private static void createFolder(IFolder folder) throws CoreException {
//        IContainer parent = folder.getParent();
//        if (parent instanceof IFolder)
//            createFolder((IFolder) parent);
//        if (!folder.exists())
//            folder.create(false, true, new NullProgressMonitor());
//    }
    
    private static IFile createFile(IProject project, String fileName, String content) throws CoreException {
    	IFile f = project.getFile(fileName);
    	createFile(f, content);
    	return f;
    }
    
    private static void createFile(IFile file, String content) throws CoreException {
    	file.create(
    		new ByteArrayInputStream(content.getBytes()),
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
        project.setDescription(description, new NullProgressMonitor());
    }
	
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
	        Epidemic epi = EpimodelFactoryImpl.eINSTANCE.createEpidemic();
	        epi.setCompartmentwrapper(EpimodelFactoryImpl.eINSTANCE.createCompartmentWrapper());
	        resource.getContents().add(epi);
	        resource.save(null);
		}
	}
}