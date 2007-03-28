package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author Soultakov Maxim
 */
public class EntityView extends Block {

    private static final Comparator<Rectangle2D> HEIGHT_COMPARATOR = new Comparator<Rectangle2D>() {
        public int compare(Rectangle2D o1, Rectangle2D o2) {
            return (int) o1.getHeight() - (int) o2.getHeight();
        }
    };

    private static final Comparator<Rectangle2D> WIDTH_COMPARATOR = new Comparator<Rectangle2D>() {
        public int compare(Rectangle2D o1, Rectangle2D o2) {
            return (int) o1.getWidth() - (int) o2.getWidth();
        }
    };
    
    protected static final Dimension MIN_SIZE = new Dimension(50,100);

    protected Entity entity;

    public EntityView(Entity entity, int x, int y) {
        super(x, y);
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be non-null");
        }
        this.entity = entity;
    }

    public void paint(Graphics2D graphics) {
        recalculateSize(graphics);
        
        drawBackground(graphics);
        drawBorder(graphics);
        
        drawTitle(graphics);                
        drawSelection(graphics);
    }

    private void drawBackground(Graphics2D graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        graphics.setColor(Color.BLACK);
    }

    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        Rectangle2D bounds = getStringBounds(graphics, entity.getName());
        graphics.drawString(entity.getName(), getX(), getY()
                + (int) bounds.getHeight());
        return bounds;
    }

    @Override
    protected Dimension getContentBounds(Graphics2D graphics) {
        List<Rectangle2D> bounds = new ArrayList<Rectangle2D>(entity.getAttributes()
                .size() + 1);
        bounds.add(getStringBounds(graphics, entity.getName()));
        for (Attribute a : entity) {
            bounds.add(getStringBounds(graphics, a.getName()));
        }
        Rectangle2D withMaxHeight = Collections.max(bounds, HEIGHT_COMPARATOR);
        Rectangle2D withMaxWidth = Collections.max(bounds, WIDTH_COMPARATOR);
        return new Dimension((int)withMaxWidth.getWidth(), (int)withMaxHeight
                .getHeight());
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean acceptRelationshipWith(EntityView entityView) {
        return entity.acceptRelationshipWith(entityView.getEntity());
    }

    public boolean acceptLinkWith(CommentView commentView) {
        return entity.acceptLinkWith(commentView.getComment());
    }

    public <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data) {
        return visitor.visit(this, data);
    }

}
