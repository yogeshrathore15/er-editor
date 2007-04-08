/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class AttributesCompartment extends Compartment {

    protected static final Dimension MIN_SIZE = new Dimension(70, 50);

    protected final List<AttributeView> attributes = new ArrayList<AttributeView>();

    /**
     * @param x
     * @param y
     * @param entityView
     */
    public AttributesCompartment(int y, EntityView entityView) {
        super(y, entityView);
    }

    public List<AttributeView> getAttributes() {
        return this.attributes;
    }

    @Override
    public Rectangle2D getContentBounds(Graphics2D graphics) {
        List<Rectangle2D> bounds = new ArrayList<Rectangle2D>(attributes.size() + 1);
        int height = 0;
        for (AttributeView a : attributes) {
            Rectangle2D stringBounds = GraphicsUtils.getStringBounds(graphics, a
                    .getAttributeStringPresentation());
            bounds.add(stringBounds);
            height += stringBounds.getHeight() + MARGIN * 2;
        }
        Rectangle2D withMaxWidth = null;
        if (!bounds.isEmpty()) {
            withMaxWidth = Collections.max(bounds, GraphicsUtils.WIDTH_COMPARATOR);
            height = (int) (height < MIN_SIZE.getHeight() ? MIN_SIZE.getHeight()
                    : height);
            int width = (int) (withMaxWidth.getWidth() < MIN_SIZE.getWidth() ? MIN_SIZE
                    .getWidth()
                    : withMaxWidth.getWidth());
            return new Rectangle2D.Double(0, 0, width, height);
        } else {
            return new Rectangle2D.Double(0, 0, MIN_SIZE.width, MIN_SIZE.height);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int paint(Graphics2D graphics) {
        int curY = getAbsoluteY();
        for (AttributeView av : attributes) {
            curY = av.paint(graphics, getX(), curY);
        }
        return curY;
    }

}
