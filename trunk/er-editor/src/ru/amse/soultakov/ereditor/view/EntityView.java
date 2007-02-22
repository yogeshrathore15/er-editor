/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author sma
 * 
 */
public class EntityView extends JComponent {
    
    private static final FontRenderContext 
                        FONT_RENDER_CONTEXT = new FontRenderContext(null, false, false); 
    private static final int MARGIN = 3;
    
    private Entity entity;
    
    private boolean selected;
    
    private EntityColorDeterminant colorDeterminant = new EntityColorDeterminant();
    
    public EntityView(Entity entity, int x, int y) {
        super();
        this.entity = entity;
        setLocation(x, y);
        setSize(1,1);
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Rectangle2D titleBounds = drawTitle(graphics);
        setSize((int) titleBounds.getWidth() + getInsets().right + getInsets().left, 100);
        int titleHeight = (int) (getInsets().top + titleBounds.getHeight() + MARGIN);
        graphics.setColor(colorDeterminant.getLineColor());
        graphics.drawLine(getInsets().left, 
                          titleHeight, 
                          getWidth(), 
                          titleHeight);
        graphics.drawLine(getInsets().left, 
                          (titleHeight + MARGIN)*2, 
                          getWidth() - getInsets().right, 
                          (titleHeight + MARGIN)*2);
        setBorder(BorderFactory.createLineBorder(colorDeterminant.getLineColor()));
    }

    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(colorDeterminant.getTitleColor());
        Rectangle2D bounds = getStringBounds(graphics);
        graphics.drawString(entity.getName(), getInsets().left, 
                            (int) (getInsets().top + bounds.getHeight()));
        return bounds;
    }

    private Rectangle2D getStringBounds(Graphics2D graphics) {
        return graphics.getFont().getStringBounds(entity.getName(), FONT_RENDER_CONTEXT);
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }
    
    private class EntityColorDeterminant {
        
        public Color getTitleColor() {
            return Color.BLACK;
        }
        
        public Color getLineColor() {
            return selected ? Color.BLUE : Color.BLACK;
        }
        
        public Color getBackgroundColor() {
            return Color.LIGHT_GRAY; 
        }
    };
   
}
