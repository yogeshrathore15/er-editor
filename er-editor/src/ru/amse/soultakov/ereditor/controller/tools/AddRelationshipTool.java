/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.SelectedItems;

public class AddRelationshipTool extends ToolAdapter {

    private DiagramEditor diagramEditor;

    private EntityView first;

    private EntityView second;

    private Point current;

    public AddRelationshipTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (first == null) {
            first = diagramEditor.getDiagram().getEntityView(e.getX(), e.getY());
            if (first != null) {
                getSelectedItems().setSelection(first);
            }
            second = null;
        }
        current = e.getPoint();
        diagramEditor.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (second != null && first != null && first.acceptRelationshipWith(second)) {
            diagramEditor.addRelationship(first, second);
        }
        reset();
        diagramEditor.repaint();
    }

    /**
     * 
     */
    private void reset() {
        first = null;
        second = null;
        getSelectedItems().clear();
    }

    /**
     * @return
     */
    private SelectedItems getSelectedItems() {
        return diagramEditor.getDiagram().getSelectedItems();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (second != null && second != first) {
            getSelectedItems().remove(second);
        }
        second = diagramEditor.getDiagram().getEntityView(e.getX(), e.getY());
        second = second == first ? null : second;
        if (second != null && first != null && first.acceptRelationshipWith(second)) {
            getSelectedItems().add(second);
        }
        current = e.getPoint();
        diagramEditor.repaint();
    }

    @Override
    public void paintBefore(Graphics2D graphics) {
        if (first != null) {
            graphics.setColor(Color.BLACK);
            graphics.drawLine(first.getX() + first.getWidth() / 2, first.getY()
                    + first.getHeight() / 3, current.x, current.y);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            reset();
            diagramEditor.repaint();
        }
    }

}
