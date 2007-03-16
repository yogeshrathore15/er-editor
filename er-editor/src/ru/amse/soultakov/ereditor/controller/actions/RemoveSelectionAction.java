/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;

/**
 * @author sma
 * 
 */
public class RemoveSelectionAction extends AbstractAction {

    private DiagramEditor diagramEditor;

    /**
     * @param diagramEditor
     * @param name
     */
    public RemoveSelectionAction(DiagramEditor diagramEditor, String name) {
        super(name);
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        diagramEditor.removeSelection();
    }

}
