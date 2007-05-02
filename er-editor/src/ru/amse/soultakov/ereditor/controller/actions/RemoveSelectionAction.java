/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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
     */
    public RemoveSelectionAction(DiagramEditor diagramEditor, String name) {
        super(name);
        this.diagramEditor = diagramEditor;
        setEnabled(false);
        this.diagramEditor.getSelectedItems().addListener(new SelectedItemsListener() {
            public void selectionChanged(SelectedItems selection) {
                setEnabled(!selection.isEmpty());
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        diagramEditor.removeSelection();
    }

}
