package compartmentalmodel.presentation;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.ui.EclipseUIPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;

import compartmentalmodel.CompartmentalmodelPackage;

/**
 * This is the central singleton for the Seir editor plugin.
 */
public final class SeirEditorPlugin extends EMFPlugin {
  
    public static final SeirEditorPlugin INSTANCE = new SeirEditorPlugin();
    private static Implementation plugin;

    public SeirEditorPlugin() {
        super(new ResourceLocator[]{});
    }

    @Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    public static Implementation getPlugin() {
        return plugin;
    }

    public static class Implementation extends EclipseUIPlugin {
      
        public Implementation() {
            super();
            plugin = this;
        }

        @Override
        public void start(BundleContext context) throws Exception {
            super.start(context);
            
            // Explicitly register the EMF-generated model package
            CompartmentalmodelPackage.eINSTANCE.eClass();
        }
    }
}
