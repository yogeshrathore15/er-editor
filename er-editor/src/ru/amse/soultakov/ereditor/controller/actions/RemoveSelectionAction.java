/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.SelectedItems;
import ru.amse.soultakov.ereditor.view.SelectedItemsListener;

/**
 * @author sma
 * 
 */
public class RemoveSelectionAction extends AbstractAction {

    static final long serialVersionUID = 1L;

    private DiagramEditor diagramEditor;

    /**
     * @param diagramEditor
     * @param name
     * @param removeIcon 
     */
    public RemoveSelectionAction(DiagramEditor diagramEditor, String name, ImageIcon removeIcon) {
        super(name, removeIcon);
        this.diagramEditor = diagramEditor;
        setEnabled(false);
        this.diagramEditor.getSelectedItems().addListener(
                new SelectedItemsListener() {
                    public void selectionChanged(SelectedItems selection) {
                        setEnabled(!selection.isEmpty());
                    }
                });
    }

    public void actionPerformed(ActionEvent e) {
        diagramEditor.removeSelection();
    }

}
