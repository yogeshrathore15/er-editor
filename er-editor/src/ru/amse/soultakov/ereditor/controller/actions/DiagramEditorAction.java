/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.Tool;

public abstract class DiagramEditorAction extends AbstractAction {
    
    protected final DiagramEditor diagramEditor;
    
    protected Tool tool;

    public DiagramEditorAction(DiagramEditor diagramEditor, String name) {
        super(name);
        this.diagramEditor = diagramEditor;
    }
    
    public void actionPerformed(ActionEvent e) {
        diagramEditor.setTool(tool);
    }
    
}
