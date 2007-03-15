/*
 * Created on 10.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.SelectedItems;

public class AddLinkTool extends ToolAdapter {

    protected static final BasicStroke DASHED = new BasicStroke(1.0f,
            BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
            new float[] { 10.0f }, 0.0f);

    private DiagramEditor diagramEditor;

    private EntityView entity;

    private CommentView comment;

    private boolean entityIsSource = false;

    private Point current;

    public AddLinkTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (comment == null && entity == null) {
            entity = diagramEditor.getDiagram().getEntityView(e.getX(), e.getY());
            if (entity != null) {
                entityIsSource = true;
                getSelectedItems().setSelection(entity);
                comment = null;
            } else {
                comment = diagramEditor.getDiagram().getCommentView(e.getX(),
                        e.getY());
                if (comment != null) {
                    entityIsSource = false;
                    getSelectedItems().setSelection(comment);
                }
            }
        }
        current = e.getPoint();
        diagramEditor.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (entity != null && comment != null) {
            diagramEditor.addLink(entity, comment);
        }
        reset();
        diagramEditor.repaint();
    }

    /**
     * 
     */
    private void reset() {
        entity = null;
        comment = null;
        getSelectedItems().clear();
    }

    /**
     * @return
     */
    private SelectedItems getSelectedItems() {
        return diagramEditor.getSelectedItems();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (entity != null || comment != null) {
            if (entityIsSource) {
                getSelectedItems().remove(comment);
                comment = diagramEditor.getDiagram().getCommentView(e.getX(),
                        e.getY());
                if (comment != null) {
                    comment = entity.acceptLinkWith(comment) ? comment : null;
                    if (comment != null) {
                        getSelectedItems().add(comment);
                    }
                }
            } else {
                getSelectedItems().remove(entity);
                entity = diagramEditor.getDiagram()
                        .getEntityView(e.getX(), e.getY());
                if (entity != null) {
                    entity = comment.acceptLinkWith(entity) ? entity : null;
                    if (entity != null) {
                        getSelectedItems().add(entity);
                    }
                }
            }
        }
        current = e.getPoint();
        diagramEditor.repaint();
    }

    @Override
    public void paintBefore(Graphics2D graphics) {
        boolean fromEntity = entity != null && entityIsSource;
        boolean fromComment = comment != null && !entityIsSource;
        if (fromEntity || fromComment) {
            graphics.setColor(Color.BLACK);
            Stroke stroke = graphics.getStroke();
            graphics.setStroke(DASHED);
            if (fromEntity) {
                graphics.drawLine(entity.getX() + entity.getWidth() / 2, entity
                        .getY()
                        + entity.getHeight() / 3, current.x, current.y);
            } else if (fromComment) {
                graphics.drawLine(comment.getX() + comment.getWidth() / 2, comment
                        .getY()
                        + comment.getHeight() / 3, current.x, current.y);
            }
            graphics.setStroke(stroke);
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
