/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.Selectable;
import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author sma
 * 
 */
public class EntityView extends JComponent implements Selectable {

    private static final int SELECTION_SQUARE_SIZE = 5;

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(
            null, false, false);

    private static final int MARGIN = 3;

    private Entity entity;

    private boolean selected;

    private EntityColorDeterminant colorDeterminant = new EntityColorDeterminant();

    private DiagramEditor diagramEditor;

    /**
     * @param diagramEditor
     * @param entity
     * @param x
     * @param y
     */
    public EntityView(DiagramEditor diagramEditor, Entity entity, int x, int y) {
        super();
        this.entity = entity;
        this.diagramEditor = diagramEditor;
        setLocation(x, y);
        setSize(1, 1);
        setOpaque(true);
        initMouseListener();
        setBorder(new LineBorder(Color.BLACK, 1, true));
    }

    /**
     * 
     */
    private void initMouseListener() {
        MouseInputAdapter mouseInputAdapter = new MouseInputAdapter() {

            private Point current;

            @Override
            public void mousePressed(MouseEvent e) {
                diagramEditor.getSelectedItems().clear();
                diagramEditor.getSelectedItems().add(EntityView.this);
                current = e.getLocationOnScreen();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                EntityView.this.shift(e.getXOnScreen() - current.x, e.getYOnScreen()
                        - current.y);
                diagramEditor.repaint();
                current = e.getLocationOnScreen();
            }

        };
        this.addMouseListener(mouseInputAdapter);
        this.addMouseMotionListener(mouseInputAdapter);
        this.addMouseWheelListener(mouseInputAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Rectangle2D bounds = getContentBounds(graphics);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setSize((int) bounds.getWidth() + getInsets().right + getInsets().left, 100);
        drawTitle(graphics);
        int titleHeight = (int) (getInsets().top + bounds.getHeight() + MARGIN);
        graphics.setColor(colorDeterminant.getLineColor());
        graphics.drawLine(getInsets().left, titleHeight, getWidth(), titleHeight);
        graphics.drawLine(getInsets().left, (titleHeight + MARGIN) * 2, getWidth()
                - getInsets().right, (titleHeight + MARGIN) * 2);
        drawSelection(graphics);
    }

    /**
     * @return
     */
    private Rectangle2D getContentBounds(Graphics2D graphics) {
        return getStringBounds(graphics, entity.getName());
    }

    /**
     * @param graphics
     * @return
     */
    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(colorDeterminant.getTitleColor());
        Rectangle2D bounds = getStringBounds(graphics, entity.getName());
        graphics.drawString(entity.getName(), getInsets().left,
                (int) (getInsets().top + bounds.getHeight()));
        return bounds;
    }

    /**
     * @param graphics
     * @return
     */
    private Rectangle2D getStringBounds(Graphics2D graphics, String string) {
        return graphics.getFont().getStringBounds(string, FONT_RENDER_CONTEXT);
    }

    private void drawSelection(Graphics2D graphics) {
        if (isSelected()) {
            // left up
            drawSelectionSquare(graphics, 0, 0);
            // left middle
            drawSelectionSquare(graphics, 0, getHeight() / 2 - SELECTION_SQUARE_SIZE / 2);
            // left down
            drawSelectionSquare(graphics, 0, getHeight() - SELECTION_SQUARE_SIZE);
            // down
            drawSelectionSquare(graphics, getWidth() / 2 - SELECTION_SQUARE_SIZE / 2,
                    getHeight() - SELECTION_SQUARE_SIZE);
            // right down
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE, getHeight()
                    - SELECTION_SQUARE_SIZE);
            // right middle
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE, getHeight()
                    / 2 - SELECTION_SQUARE_SIZE / 2);
            // right up
            drawSelectionSquare(graphics, getWidth() - SELECTION_SQUARE_SIZE, 0);
            // up
            drawSelectionSquare(graphics, getWidth() / 2 - SELECTION_SQUARE_SIZE / 2, 0);
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

    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }

    private void shift(int dx, int dy) {
        this.setLocation(getX() + dx, getY() + dy);
    }
    
    public Entity getEntity() {
        return entity;
    }

    private class EntityColorDeterminant {

        public Color getTitleColor() {
            return Color.BLACK;
        }

        public Color getLineColor() {
            return Color.BLACK;
        }

        public Color getBackgroundColor() {
            return Color.LIGHT_GRAY;
        }
    };

}
