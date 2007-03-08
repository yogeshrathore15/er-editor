/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.Viewable;

public class SelectElementTool extends ToolAdapter {
    private Viewable currentElement;

    private Point currentPoint;

    private DiagramEditor diagramEditor;

    public SelectElementTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        boolean nothingSelected = true;
        // && operator is used to prevent selecting more then one element
        nothingSelected = nothingSelected
                && selectViews(e, diagramEditor.getDiagram().getEntityViews());
        nothingSelected = nothingSelected
                && selectViews(e, diagramEditor.getDiagram().getCommentViews());
        nothingSelected = nothingSelected
                && selectViews(e, diagramEditor.getDiagram().getRelationshipViews());
        nothingSelected = nothingSelected
                && selectViews(e, diagramEditor.getDiagram().getLinkViews());

        if (nothingSelected && !e.isControlDown()) {
            diagramEditor.getDiagram().getSelectedItems().clear();
            this.currentElement = null;
        }
        currentPoint = e.getLocationOnScreen();
        diagramEditor.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentElement != null) {
            int xPos = e.getXOnScreen() - currentPoint.x + currentElement.getX();
            int yPos = e.getYOnScreen() - currentPoint.y + currentElement.getY();
            currentElement.setLocation(xPos >= 0 ? xPos : 0, yPos >= 0 ? yPos : 0);
            currentPoint = e.getLocationOnScreen();
            diagramEditor.repaint();
        }
    }

    private boolean selectViews(MouseEvent e, Set<? extends Viewable> views) {
        for (Viewable view : views) {
            if (view.containsPoint(e.getX(), e.getY())) {
                if (e.isControlDown()) {
                    if (view.isSelected()) {
                        diagramEditor.getDiagram().getSelectedItems().remove(view);
                    } else {
                        diagramEditor.getDiagram().getSelectedItems().add(view);
                    }
                } else {
                    diagramEditor.getDiagram().getSelectedItems().setSelection(view);
                }
                this.currentElement = view;
                return false;
            }
        }
        return true;
    }
}
