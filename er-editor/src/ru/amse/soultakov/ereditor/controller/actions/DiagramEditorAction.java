/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.Tool;

public class DiagramEditorAction extends AbstractAction {

    static final long serialVersionUID = 1L;

    private final DiagramEditor diagramEditor;

    private final Tool tool;

    public DiagramEditorAction(DiagramEditor diagramEditor, String name, Icon icon, Tool tool) {
        super(name, icon);
        this.diagramEditor = diagramEditor;
        this.tool = tool;
    }
    
    public DiagramEditorAction(DiagramEditor diagramEditor, String name, Tool tool) {
        super(name);
        this.diagramEditor = diagramEditor;
        this.tool = tool;
    }

    public void actionPerformed(ActionEvent e) {
        diagramEditor.setTool(tool);
    }

}
