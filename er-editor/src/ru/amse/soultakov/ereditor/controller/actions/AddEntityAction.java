/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.actions;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.AddEntityTool;

public class AddEntityAction extends DiagramEditorAction {

    public AddEntityAction(DiagramEditor diagramEditor, String name) {
        super(diagramEditor, name);
        tool = new AddEntityTool(diagramEditor);
    }
    
}
