/*
 * Created on 10.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.SelectedItems;

public class AddLinkTool extends ToolAdapter {

    private DiagramEditor diagramEditor;

    private EntityView entity;

    private CommentView comment;
    
    private Point current;

    public AddLinkTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (comment == null && entity == null) {
            entity = diagramEditor.getDiagram().getEntityView(e.getX(), e.getY());
            if (entity != null) {
                getSelectedItems().setSelection(entity);
                comment = null;
            } else {
                comment = diagramEditor.getDiagram().getCommentVeiw(e.getX(),
                        e.getY());
                if (comment != null) {
                    getSelectedItems().setSelection(comment);
                }
            }
        }
        current = e.getPoint();
        diagramEditor.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        second = diagramEditor.getDiagram().getEntityView(e.getX(), e.getY());
        second = second == first ? null : second;
        if (second != null && first != null && first.acceptRelationshipWith(second)) {
            diagramEditor.addRelationship(first, second);
            getSelectedItems().remove(first);
            getSelectedItems().remove(second);
            first = null;
            second = null;
        } else {
            first = null;
            getSelectedItems().clear();
        }
        diagramEditor.repaint();
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
            first = null;
            second = null;
            getSelectedItems().clear();
            diagramEditor.repaint();
        }
    }

}
