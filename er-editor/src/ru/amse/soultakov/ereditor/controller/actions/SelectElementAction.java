/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.actions;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;

public class SelectElementAction extends DiagramEditorAction {
    
    public SelectElementAction(DiagramEditor diagramEditor, String name) {
        super(diagramEditor, name);
        tool = new SelectElementTool(
                diagramEditor);
    }

}
