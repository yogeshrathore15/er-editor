/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.EntityView;

public class AddRelationshipTool extends ToolAdapter {

    private DiagramEditor diagramEditor;

    private EntityView first;

    private EntityView second;

    public AddRelationshipTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (first == null) {
            first = diagramEditor.getDiagram().getEntityView(e.getX(),
                    e.getY());
            diagramEditor.getDiagram().getSelectedItems().add(first);
        } else {
            second = diagramEditor.getDiagram().getEntityView(e.getX(),
                    e.getY());
            second = second == first ? null : second;
            if (second != null) {
                diagramEditor.addRelationship("", first, second);
                diagramEditor.getDiagram().getSelectedItems().remove(first);
                diagramEditor.getDiagram().getSelectedItems().remove(second);
                first = null;
                second = null;
            }
        }
        diagramEditor.repaint();
    }
    
}
