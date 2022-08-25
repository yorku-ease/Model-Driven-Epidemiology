import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.commands.IParameter;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.business.internal.metamodel.spec.DSemanticDiagramSpec;
import org.eclipse.sirius.diagram.ui.tools.internal.graphical.edit.part.DDiagramHelper;

//import org.eclipse.sirius.diagram.ui.tools.internal.actions.refresh.RefreshDiagramAction;

import com.google.common.collect.Iterables;

public class CreateEpidemicCommandHandler implements IHandler {

	public CreateEpidemicCommandHandler() {
		System.out.println("CreateEpidemicCommandHandler");
	}
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		System.out.println("ADD");
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("EXECUTE");
        ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();
		
        DDiagram diagram = null;
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            final Collection<EditPart> minimizedSelection = minimizeSelection(Arrays.asList(structuredSelection.toArray()));
            if (!minimizedSelection.isEmpty())
                diagram = DDiagramHelper.findParentDDiagram(
                	Iterables.filter(minimizedSelection, IGraphicalEditPart.class).iterator().next());
        }
        
        DSemanticDiagramSpec d = (DSemanticDiagramSpec) diagram;
        EObject model = (EObject) d.getTarget();
        
        String T = (String) event.getParameters().get("org.epimodel.commandParameter3");
        System.out.println("org.epimodel.commandParameter3: " + T);
        
        try {
            Class<?> c = Class.forName("epimodel.impl.EpidemicWrapperImpl");
            System.out.println(c);
         } catch (ClassNotFoundException e) {
                e.printStackTrace();
        }
//      IEvaluationContext context = (IEvaluationContext) event.getApplicationContext();
//	    IWorkbench workbench = (IWorkbench) context.getVariable(IWorkbench.class.getName());
//	    Object a = workbench.getActiveWorkbenchWindow();
//	    Object b = workbench.getActiveWorkbenchWindow().getActivePage();
        
		return null;
	}
    
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public boolean isHandled() {
		return true;
	}
	
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		System.out.println("REMOVE");
	}
	
	
	
	
	
	
	
	
	
	// SEE SIRIUS: public class RefreshDiagramAction extends RetargetAction
    static Collection<EditPart> minimizeSelection(final List<?> selection) {
        final Collection<EditPart> result = new ArrayList<EditPart>(selection.size());
        final Iterator<?> iterSelection = new ArrayList<Object>(selection).iterator();
        while (iterSelection.hasNext()) {
            final Object next = iterSelection.next();
            if (next instanceof EditPart) {
                final EditPart editPart = (EditPart) next;
                if (isNotAChild(editPart, selection)) {
                    result.add(editPart);
                } else {
                    iterSelection.remove();
                }
            } else {
                iterSelection.remove();
            }
        }
        return result;
    }

	// SEE SIRIUS: public class RefreshDiagramAction extends RetargetAction
    private static boolean isNotAChild(final EditPart editPart, final Collection<?> selection) {
        final Iterator<?> iterEditParts = selection.iterator();
        while (iterEditParts.hasNext())
            if ((EditPart) iterEditParts.next() != editPart
        		&& iterEditParts.hasNext()
        		&& isAChild(editPart, (EditPart) iterEditParts.next()))
                	return false;
        return true;
    }

	// SEE SIRIUS: public class RefreshDiagramAction extends RetargetAction
    private static boolean isAChild(final EditPart mayBeChild, final EditPart editPart) {
        if (editPart.getChildren().contains(mayBeChild))
            return true;
        final Iterator<EditPart> iterChildren = editPart.getChildren().iterator();
        while (iterChildren.hasNext()) {
            final EditPart currentEditPart = iterChildren.next();
            if (isAChild(mayBeChild, currentEditPart))
            	return true;
        }
        return false;
    }
}
