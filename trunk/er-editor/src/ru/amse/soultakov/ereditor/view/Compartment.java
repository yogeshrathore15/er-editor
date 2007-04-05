/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

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
public abstract class Compartment
{

	public static final int MARGIN = 3;

	protected int x;

	protected int y;

	protected final List<AttributeView> attributes = new ArrayList<AttributeView>();

	protected final EntityView entityView;

	public Compartment(int x, int y, EntityView entityView)
	{
		this.x = x;
		this.y = y;
		this.entityView = entityView;
	}

	public int getX()
	{
		return this.x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public List<AttributeView> getAttributes()
	{
		return this.attributes;
	}

	public EntityView getEntityView()
	{
		return this.entityView;
	}

	public abstract void paint(Graphics2D g);

	public abstract int getHeight();

	public abstract int getWidth();

	public Rectangle2D getContentBounds(Graphics2D graphics)
	{
		List<Rectangle2D> bounds = new ArrayList<Rectangle2D>(attributes.size() + 1);
		int height = 0;
		for (AttributeView a : attributes)
		{
			Rectangle2D stringBounds = GraphicsUtils.getStringBounds(graphics, a.getAttributeStringPresentation());
			bounds.add(stringBounds);
			height += stringBounds.getHeight() + MARGIN * 2;
		}
		Rectangle2D withMaxWidth = Collections.max(bounds, GraphicsUtils.WIDTH_COMPARATOR);
		return new Rectangle2D.Double(0,0,withMaxWidth.getWidth(), height);
	}

}
