/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;

public abstract class Block implements IViewable {

    private static final int SELECTION_SQUARE_SIZE = 7;

    public static final int MARGIN = 3;

    private final List<IViewableListener> listeners = new ArrayList<IViewableListener>();

    private boolean selected;

    protected int x;

    protected int y;

    protected int height;

    protected int width;

    protected Diagram diagram;

    public Block(Diagram diagram, int x, int y) {
        this.diagram = diagram;
        setLocation(x, y);
    }

    public void setSize(int width, int height) {
        if (width != getWidth() || height != getHeight()) {
            this.width = width;
            this.height = height;
            notifyListeners();
        }
    }

    /**
     * @return the widt
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    public final void setLocation(int x, int y) {
        if (getX() != x || getY() != y) {
            notifyListeners();
            this.setX(x);
            this.setY(y);
        }
    }

    protected void drawSelection(Graphics2D graphics) {
        if (isSelected()) {
            // left up
            drawSelectionSquare(graphics, -SELECTION_SQUARE_SIZE / 2,
                    -SELECTION_SQUARE_SIZE / 2);
            // left middle
            drawSelectionSquare(graphics, -SELECTION_SQUARE_SIZE / 2, getHeight()
                    / 2 - SELECTION_SQUARE_SIZE / 2);
            // left down
            drawSelectionSquare(graphics, -SELECTION_SQUARE_SIZE / 2, getHeight()
                    - SELECTION_SQUARE_SIZE / 2);
            // down
            drawSelectionSquare(graphics,
                    getWidth() / 2 - SELECTION_SQUARE_SIZE / 2, getHeight()
                            - SELECTION_SQUARE_SIZE / 2);
            // right down
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE / 2,
                    getHeight() - SELECTION_SQUARE_SIZE / 2);
            // right middle
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE / 2,
                    getHeight() / 2 - SELECTION_SQUARE_SIZE / 2);
            // right up
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE / 2,
                    -SELECTION_SQUARE_SIZE / 2);
            // up
            drawSelectionSquare(graphics,
                    getWidth() / 2 - SELECTION_SQUARE_SIZE / 2,
                    -SELECTION_SQUARE_SIZE / 2);
        }
    }

    /**
     * @param graphics
     * @param x
     * @param y
     */
    private void drawSelectionSquare(Graphics2D graphics, int x, int y) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(getX() + x, getY() + y, SELECTION_SQUARE_SIZE,
                SELECTION_SQUARE_SIZE);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(getX() + x, getY() + y, SELECTION_SQUARE_SIZE,
                SELECTION_SQUARE_SIZE);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyListeners();
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * @param x
     *            the x to set
     */
    protected void setX(int x) {
        this.x = x;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param y
     *            the y to set
     */
    protected void setY(int y) {
        this.y = y;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param graphics
     */
    protected void drawBorder(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
    }

    /**
     * @param graphics
     * @return
     */
    public void recalculateSize(Graphics2D graphics) {
        Dimension bounds = getContentBounds(graphics);
        setSize(bounds.width, bounds.height);
    }

    public boolean containsPoint(int x, int y) {
        return (getX() <= x) && (getY() <= y) && (getX() + getWidth() >= x)
                && (getY() + getHeight() >= y);
    }

    public boolean isInsideRectangle(int x1, int y1, int x2, int y2) {
        int left = Math.min(x1, x2);
        int top = Math.min(y1, y2);
        int right = Math.max(x1, x2);
        int bottom = Math.max(y1, y2);
        return (left < x) && (top < y) && (right > x + width)
                && (bottom > y + height);
    }

    protected void notifyListeners() {
        for (IViewableListener vl : listeners) {
            vl.notify(this);
        }
    }

    public void addListener(IViewableListener iViewableListener) {
        listeners.add(iViewableListener);
    }

    public boolean removeListener(IViewableListener iViewableListener) {
        return listeners.remove(iViewableListener);
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    protected abstract Dimension getContentBounds(Graphics2D graphics);

    @Override
    public String toString() {
        return "[X = " + getX() + ", Y = " + getY() + ", W = " + getWidth()
                + ", H = " + getHeight() + "]";
    }

    protected void drawShadow(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRoundRect(getX() + 2, getY() + 2, getWidth(), getHeight(), 10,
                10);
    }

    public IOutline getOutline() {
        return new BlockOutline(diagram, this);
    }

    public void processClick(MouseEvent mouseEvent, DiagramEditor editor) {
    }

    public void exitProcessing() {
    }

    public void processDrag(MouseEvent mouseEvent, DiagramEditor editor) {
    }

    public void processRelease(MouseEvent mouseEvent, DiagramEditor editor) {
    }

}