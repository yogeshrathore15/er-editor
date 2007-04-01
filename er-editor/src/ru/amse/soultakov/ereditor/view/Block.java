/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public abstract class Block implements IViewable {

    private static final int SELECTION_SQUARE_SIZE = 5;

    public static final int MARGIN = 3;

    private final List<IViewableListener> listeners = new ArrayList<IViewableListener>();

    private boolean selected;

    private int x;

    private int y;

    private int height;

    private int width;
    
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
            drawSelectionSquare(graphics, 0, 0);
            // left middle
            drawSelectionSquare(graphics, 0, getHeight() / 2 - SELECTION_SQUARE_SIZE
                    / 2);
            // left down
            drawSelectionSquare(graphics, 0, getHeight() - SELECTION_SQUARE_SIZE);
            // down
            drawSelectionSquare(graphics,
                    getWidth() / 2 - SELECTION_SQUARE_SIZE / 2, getHeight()
                            - SELECTION_SQUARE_SIZE);
            // right down
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE,
                    getHeight() - SELECTION_SQUARE_SIZE);
            // right middle
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE,
                    getHeight() / 2 - SELECTION_SQUARE_SIZE / 2);
            // right up
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE, 0);
            // up
            drawSelectionSquare(graphics,
                    getWidth() / 2 - SELECTION_SQUARE_SIZE / 2, 0);
        }
    }

    /**
     * @param graphics
     * @param x
     * @param y
     */
    private void drawSelectionSquare(Graphics2D graphics, int x, int y) {
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
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
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

    protected abstract Dimension getContentBounds(Graphics2D graphics);

    @Override
    public String toString() {
        return "[X = " + getX() + ", Y = " + getY() + ", W = " + getWidth()
                + ", H = " + getHeight() + "]";
    }

}