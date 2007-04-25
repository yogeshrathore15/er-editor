/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Collection;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.util.CommonUtils;
import ru.amse.soultakov.ereditor.view.IOutline;
import ru.amse.soultakov.ereditor.view.IViewable;
import ru.amse.soultakov.ereditor.view.SelectedItems;

public class SelectElementTool extends ToolAdapter {

    /**
     * 
     */
    private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);

    /**
     * 
     */
    private static final Cursor MOVE_CURSOR = new Cursor(Cursor.MOVE_CURSOR);

    protected static final BasicStroke DASHED = new BasicStroke(1.0f,
            BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f,
            new float[] { 4.0f }, 0.0f);

    private Point currentPoint;

    private Point startPoint;

    private Point endPoint;

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

        if (nothingSelected) {
            getSelectedItems().clear();
        } else {
            getSelectedOutlines().clear();
            for (IViewable v : getSelectedItems()) {
                IOutline outline = v.getOutline();
                if (v.getOutline() != null) {
                    getSelectedOutlines().add(outline);
                }
            }
        }
        currentPoint = e.getLocationOnScreen();
        startPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!getSelectedOutlines().isEmpty() && endPoint == null) {
            diagramEditor.setCursor(MOVE_CURSOR);
            dragSelection(e);//перетаскиваем выделение
        } else {
            //пытаемся выделить с помощью прямоугольника
            diagramEditor.setCursor(DEFAULT_CURSOR);
            endPoint = e.getPoint();
            tryToSelectAllViews();
        }
        currentPoint = e.getLocationOnScreen();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        diagramEditor.setCursor(DEFAULT_CURSOR);
        if (endPoint != null && startPoint != null) {
            tryToSelectAllViews();
            endPoint = null;
            startPoint = null;
        } else if (startPoint != null && endPoint == null) {
            if (e.isControlDown()) {
                for(IOutline o : getSelectedOutlines() ) {
                    IViewable copy = o.getViewable().copy();
                    copy.setLocation(o.getX(), o.getY());
                }                
            } else {
                for(IOutline o : getSelectedOutlines() ) {
                    o.getViewable().setLocation(o.getX(), o.getY());
                }
            }
            getSelectedOutlines().clear();
            diagramEditor.repaint();
            endPoint = null;
            startPoint = null;
        }
    }

    /**
     * 
     */
    private void tryToSelectAllViews() {
        tryToSelectViews(diagramEditor.getDiagram().getEntityViews());
        tryToSelectViews(diagramEditor.getDiagram().getCommentViews());
        tryToSelectViews(diagramEditor.getDiagram().getRelationshipViews());
        tryToSelectViews(diagramEditor.getDiagram().getLinkViews());
        diagramEditor.repaint();
    }

    /**
     * @param e
     */
    private void dragSelection(MouseEvent e) {
        if (canDragSelection(e)) {
            for (IViewable v : getSelectedOutlines()) {
                int xPos = e.getXOnScreen() - currentPoint.x + v.getX();
                int yPos = e.getYOnScreen() - currentPoint.y + v.getY();
                v.setLocation(xPos >= 0 ? xPos : 0, yPos >= 0 ? yPos : 0);
            }
            diagramEditor.revalidate();
            Point p = CommonUtils.getRightBottomPoint(diagramEditor
                    .getSelectedItems().toSet());
            diagramEditor.scrollRectToVisible(new Rectangle(p.x, p.y, 0, 0));
            diagramEditor.repaint();
        }
    }

    private boolean canDragSelection(MouseEvent e) {
        for (IViewable v : getSelectedOutlines()) {
            int xPos = e.getXOnScreen() - currentPoint.x + v.getX();
            int yPos = e.getYOnScreen() - currentPoint.y + v.getY();
            if (xPos < 0 || yPos < 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void paintAfter(Graphics2D graphics) {
        if (endPoint != null && startPoint != null) {
            drawRectangle(graphics, startPoint.x, startPoint.y, endPoint.x,
                    endPoint.y);
        }
    }

    private void drawRectangle(Graphics2D graphics, int x1, int y1, int x2, int y2) {
        graphics.setColor(Color.BLACK);
        Stroke stroke = graphics.getStroke();
        graphics.setStroke(DASHED);
        graphics.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1),
                Math.abs(y2 - y1));
        graphics.setStroke(stroke);
    }

    /**
     * @return
     */
    private SelectedItems<IViewable> getSelectedItems() {
        return diagramEditor.getSelectedItems();
    }

    private void tryToSelectViews(Collection<? extends IViewable> views) {
        if (startPoint == null) {
            return;
        }
        for (IViewable view : views) {
            if (view.isInsideRectangle(startPoint.x, startPoint.y, endPoint.x,
                    endPoint.y)) {
                getSelectedItems().add(view);
            } else {
                getSelectedItems().remove(view);
            }
        }
    }

    private boolean selectViews(MouseEvent e, Collection<? extends IViewable> views) {
        boolean result = true;
        for (IViewable view : views) {
            if (view.containsPoint(e.getX(), e.getY())) {
                if (e.isControlDown()) {
                    if (view.isSelected()) {
                        getSelectedItems().remove(view);
                    } else {
                        getSelectedItems().add(view);
                    }
                } else {
                    if (!view.isSelected()) {
                        getSelectedItems().setSelection(view);
                    } else {
                        view.processClick(e);
                    }
                }
                result = false;
            }
        }
        return result;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            diagramEditor.removeSelection();
        }
    }

    /**
     * @return the selectedOutlines
     */
    private SelectedItems<IOutline> getSelectedOutlines() {
        return diagramEditor.getSelectedOutlines();
    }

}
