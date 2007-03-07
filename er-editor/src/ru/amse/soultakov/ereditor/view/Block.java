/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.controller.Viewable;

public abstract class Block implements Viewable {

    private static final int SELECTION_SQUARE_SIZE = 5;

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(
            null, false, false);

    protected static final int MARGIN = 3;

    private boolean selected;

    private int x;

    private int y;

    private int height;

    private int width;

    public Block(int x, int y) {
        super();
        setLocation(x, y);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
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

    public void setLocation(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**
     * @param graphics
     * @return
     */
    protected Rectangle2D getStringBounds(Graphics2D graphics, String string) {
        return graphics.getFont().getStringBounds(string, FONT_RENDER_CONTEXT);
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
        graphics.drawRect(x, y, SELECTION_SQUARE_SIZE, SELECTION_SQUARE_SIZE);
    }

    public void setSelected(boolean selected) {
        boolean oldSelected = this.selected;
        if (oldSelected != selected) {
            this.selected = selected;
            // paint();
        }
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * @param x the x to set
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
     * @param y the y to set
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
        graphics.drawRect(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * @param graphics
     * @return
     */
    public void recalculateSize(Graphics2D graphics) {
        Rectangle2D bounds = getContentBounds(graphics);
        setSize((int) bounds.getWidth(), 100);
    }
    
    protected abstract Rectangle2D getContentBounds(Graphics2D graphics);

}