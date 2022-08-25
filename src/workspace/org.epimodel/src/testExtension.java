import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.sirius.business.api.session.SessionManager;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

public class testExtension extends ExtensionContributionFactory {

	@Override
	public void createContributionItems(IServiceLocator serviceLocator, IContributionRoot additions) {
		System.out.println("createContributionItems");
		
	    List<String> contents = new ArrayList<>();
	    
	    final EPackage.Registry reg = EPackage.Registry.INSTANCE;
	    List<EPackage> allPackages = reg
	    		.values()
	    		.stream()
	    		.filter(pkg -> pkg instanceof EPackage)
	    		.map(pkg -> (EPackage) pkg)
	    		.collect(Collectors.toList());
	    List<EPackage> epimodelPackages = new ArrayList<>();
	    
	    do {
	    	int size = epimodelPackages.size();
		    for (int i = allPackages.size() - 1; i  >= 0; --i) {
		    	EPackage pkg = allPackages.get(i);
		    	if (EPkgRefersToAtLeastOnePkgOrEpimodel(pkg, epimodelPackages)) {
		    		epimodelPackages.add(pkg);
		    		allPackages.remove(i);
		    	}
		    }
		    if (epimodelPackages.size() == size)
		    	break;
	    } while (true);
	    		
		String cmdId = "org.epimodel.createEpidemic";
		for (String s : contents) {
			Map<String, String> parameters = new HashMap<>();
			parameters.put("org.epimodel.commandParameter3",  s);
			CommandContributionItem c = new CommandContributionItem(new CommandContributionItemParameter(
					serviceLocator, // IServiceLocator serviceLocator
					cmdId, // String id
					cmdId, // String commandId
					parameters, // Map parameters
					null, // ImageDescriptor icon
					null, // ImageDescriptor disabledIcon
					null, // ImageDescriptor hoverIcon
					s, // String label
					null, // String mnemonic
					null, // String tooltip
					CommandContributionItem.STYLE_PUSH, // int style
					null, // String helpContextId
					true) // boolean visibleEnabled
			);
			
			additions.addContributionItem(c, new Expression() {
				@Override
				public EvaluationResult evaluate(IEvaluationContext context) throws CoreException {
					System.out.println("Evaluate");
					Object maybe_vars = context.getDefaultVariable();
					if (!(maybe_vars instanceof List<?>))
						return EvaluationResult.FALSE;
					List<String> l = ((List<?>) maybe_vars)
							.stream()
							.map(Object::getClass)
							.map(c->c.getName())
							.filter(s->s.contains("Node"))
							.collect(Collectors.toList());
					System.out.println(l);
					return EvaluationResult.valueOf(l.size() > 0);
				}
			});
		}
	}
	
	boolean EPkgRefersToAtLeastOnePkgOrEpimodel(EPackage pkg, List<EPackage> pkgs) {
    	OutputStream output = new OutputStream() {
    	    StringBuilder sb = new StringBuilder();

    	    @Override
    	    public void write(int b) throws IOException {
    	        sb.append((char) b);
    	    }

    	    @Override
    	    public String toString() {
    	        return sb.toString();
    	    }
    	};
    	try {
			pkg.eResource().save(output, null); // pkg.ecore as string
		} catch (Exception e) { }
    	
    	List<String> pkgsStrToFindInXMI = pkgs
    			.stream()
    			.map(p -> p.getName() + "#")
    			.collect(Collectors.toList());
    	
    	String ecoreStr = output.toString();
    	return ecoreStr
    			.contains("http://www.example.org/epimodel") 
    			|| pkgsStrToFindInXMI
    				.stream()
    				.filter(uri -> ecoreStr.contains(uri))
    				.findFirst()
    				.isPresent();
	}
}
