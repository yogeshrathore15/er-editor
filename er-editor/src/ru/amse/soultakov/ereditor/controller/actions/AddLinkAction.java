/*
 * Created on 10.03.2007
 */
package ru.amse.soultakov.ereditor.controller.actions;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.AddLinkTool;

public class AddLinkAction extends DiagramEditorAction {

    public AddLinkAction(DiagramEditor diagramEditor, String name) {
        super(diagramEditor, name);
        tool = new AddLinkTool(diagramEditor);
    }

}
