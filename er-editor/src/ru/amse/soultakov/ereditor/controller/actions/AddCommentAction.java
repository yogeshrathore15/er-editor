/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.actions;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.AddCommentTool;

public class AddCommentAction extends DiagramEditorAction {

    public AddCommentAction(DiagramEditor diagramEditor, String name) {
        super(diagramEditor, name);
        tool = new AddCommentTool(diagramEditor);
    }
    
}
