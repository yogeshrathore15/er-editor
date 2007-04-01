package ru.amse.soultakov.ereditor.view;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newArrayList;

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
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 */
public class EntityView extends Block {

    private static final Color BACKGROUND_COLOR = new Color(210, 210, 210);

    private static final Comparator<Rectangle2D> WIDTH_COMPARATOR = new Comparator<Rectangle2D>() {
        public int compare(Rectangle2D o1, Rectangle2D o2) {
            return (int) o1.getWidth() - (int) o2.getWidth();
        }
    };

    protected static final Dimension MIN_SIZE = new Dimension(50, 100);

    protected Entity entity;
    
    protected List<AttributeView> attributeViews = newArrayList();
    
    protected List<AttributeView> pkAttributesViews = newArrayList(); 

    public EntityView(Diagram diagram, Entity entity, int x, int y) {
        super(diagram, x, y);
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be non-null");
        }
        this.entity = entity;
        int counter = 0;
        for(Attribute a : entity.getPrimaryKey()) {
            pkAttributesViews.add(new AttributeView(a, this, counter++));
        }
        counter = 0;
        for(Attribute a : entity.getAttributesExceptPK()) {
            attributeViews.add(new AttributeView(a, this, counter++));
        }
    }

    public void paint(Graphics2D graphics) {
        recalculateSize(graphics);

        drawBackground(graphics);
        drawBorder(graphics);

        int curY = drawTitle(graphics);
        curY = drawPK(graphics, curY);
        drawAttributes(graphics, curY);
        drawSelection(graphics);
    }

    private int drawPK(Graphics2D graphics, int curY) {
        int newCurY = curY;
        for (Attribute a : entity.getPrimaryKey()) {
            newCurY = drawString(graphics, a.toString(), newCurY);
        }
        newCurY += MARGIN;
        drawHorizontalLine(graphics, newCurY);
        return newCurY;
    }

    private void drawHorizontalLine(Graphics2D graphics, int newCurY) {
        graphics.drawLine(getX(), newCurY, getX() + getWidth(), newCurY);
    }

    private int drawAttributes(Graphics2D graphics, int curY) {
        int newCurY = curY;
        for (Attribute a : entity.getAttributesExceptPK()) {
            newCurY = drawString(graphics, a.toString(), newCurY);
        }
        return newCurY + MARGIN;
    }

    private void drawBackground(Graphics2D graphics) {
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
    }

    private int drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        int titleY = drawString(graphics, entity.getName(), getY()) + MARGIN;
        graphics.drawLine(getX(), titleY, getX() + getWidth(), titleY);
        return titleY;
    }

    private int drawString(Graphics2D graphics, String string, int curY) {
        Rectangle2D bounds = GraphicsUtils.getStringBounds(graphics, string);
        int newCurY = curY + (int) bounds.getHeight();
        graphics.drawString(string, MARGIN * 2 + getX(), newCurY);
        return newCurY;
    }

    @Override
    protected Dimension getContentBounds(Graphics2D graphics) {
        List<Rectangle2D> bounds = new ArrayList<Rectangle2D>(entity.getAttributes()
                .size() + 1);
        bounds.add(GraphicsUtils.getStringBounds(graphics, entity.getName()));
        for (Attribute a : entity) {
            bounds.add(GraphicsUtils.getStringBounds(graphics, a.toString()));
        }
        int height = 0;
        for (Rectangle2D r : bounds) {
            height += r.getHeight() + MARGIN * 2;
        }
        Rectangle2D withMaxWidth = Collections.max(bounds, WIDTH_COMPARATOR);
        height = (int) (height < MIN_SIZE.getHeight() ? MIN_SIZE.getHeight()
                : height);
        int width = (int) (withMaxWidth.getWidth() < MIN_SIZE.getWidth() ? MIN_SIZE
                .getWidth() : withMaxWidth.getWidth());
        return new Dimension(width + MARGIN * 4, height);
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
