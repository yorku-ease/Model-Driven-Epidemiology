package org.epimodel;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class EpidemiologicalModelingPerspective implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, 0.25f, editorArea);
        topLeft.addPlaceholder(IPageLayout.ID_BOOKMARKS);
        topLeft.addView("org.eclipse.ui.navigator.ProjectExplorer");
        
        IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM, 0.50f, "topLeft");
        bottomLeft.addView(IPageLayout.ID_OUTLINE);
        bottomLeft.addView(IPageLayout.ID_PROP_SHEET);
        
        layout.addView(IPageLayout.ID_TASK_LIST, IPageLayout.BOTTOM, 0.66f, editorArea);
	}
}
