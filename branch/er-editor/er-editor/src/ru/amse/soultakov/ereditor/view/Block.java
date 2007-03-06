/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;

import ru.amse.soultakov.ereditor.controller.Selectable;

public abstract class Block extends JComponent implements Selectable {

    private static final int SELECTION_SQUARE_SIZE = 5;

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(
            null, false, false);

    protected static final int MARGIN = 3;

    private boolean selected;

    public Block(int x, int y) {
        super();
        setSize(1,1);
        setOpaque(true);
        setLocation(x, y);
        setBorder(new LineBorder(Color.BLACK, 1, true));
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
            repaint();
        }
    }

    public boolean isSelected() {
        return selected;
    }

}