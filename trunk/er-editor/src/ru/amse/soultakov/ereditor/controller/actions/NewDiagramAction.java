/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.view.Diagram;

@SuppressWarnings("serial")
public final class NewDiagramAction extends AbstractAction {
    
    private DiagramEditorFrame diagramEditorFrame;

    public NewDiagramAction(String name, DiagramEditorFrame diagramEditorFrame) {
        super(name);
        this.diagramEditorFrame = diagramEditorFrame;
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_N);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
    }

    public void actionPerformed(ActionEvent e) {
        diagramEditorFrame.getDiagramEditor().setDiagram(new Diagram());
    }
    
}