/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.view.Diagram;

@SuppressWarnings("serial")
public final class NewDiagramAction extends AbstractAction {

    private DiagramEditorFrame diagramEditorFrame;

    public NewDiagramAction(String name, DiagramEditorFrame diagramEditorFrame,
            ImageIcon icon) {
        super(name, icon);
        this.diagramEditorFrame = diagramEditorFrame;
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
    }

    public void actionPerformed(ActionEvent e) {
        if (diagramEditorFrame.getDiagramEditor().isDiagramChanged()) {
            int res = JOptionPane.showConfirmDialog(diagramEditorFrame,
                    "Diagram has been modified. Save changes?", "Save Resource",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                diagramEditorFrame.getSaveDiagramAction().actionPerformed(null);
                createNewDiagram();
            } else if (res == JOptionPane.NO_OPTION) {
                createNewDiagram();
            }
        } else {
            createNewDiagram();
        }
    }

    private void createNewDiagram() {
        DiagramEditor diagramEditor = diagramEditorFrame.getDiagramEditor();
        diagramEditor.setDiagram(new Diagram());
        diagramEditor.setCurrentFile(null);
        diagramEditor.getCommandManager().reset();
    }

}