package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class AttributeView
{

	private AbstractAttribute attribute;

	private Block entityView;

	private Compartment compartment;

	private boolean selected;

	private volatile int lastPaintedY;

	public AttributeView(AbstractAttribute attribute, Block entityView, Compartment compartment)
	{
		this.attribute = attribute;
		this.entityView = entityView;
		this.compartment = compartment;
	}

	public AbstractAttribute getAttribute()
	{
		return this.attribute;
	}

	/**
     * @return the compartment
     */
	public Compartment getCompartment()
	{
		return this.compartment;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean isSelected()
	{
		return this.selected;
	}

	public void setAttribute(AbstractAttribute attribute)
	{
		this.attribute = attribute;
	}

	public Block getEntityView()
	{
		return this.entityView;
	}

	public void setEntityView(Block entityView)
	{
		this.entityView = entityView;
	}

	/**
     * @return the lastPaintedY
     */
	public int getLastPaintedY()
	{
		return this.lastPaintedY;
	}

	public int paint(Graphics2D graphics, int x, int y)
	{
		String attrString = getAttributeStringPresentation();
		Rectangle2D bounds = GraphicsUtils.getStringBounds(graphics, attrString);
		this.lastPaintedY = (y + (int) bounds.getHeight());
		if (selected)
		{
			Color prev = graphics.getColor();
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fillRect(Block.MARGIN + this.entityView.getX(), lastPaintedY
					- (int) bounds.getHeight() + 3, entityView.getWidth() - Block.MARGIN*2, (int) bounds.getHeight());
			graphics.setColor(prev);
		}
		graphics.drawString(attrString, Block.MARGIN + this.entityView.getX(), this.lastPaintedY);
		return lastPaintedY + Block.MARGIN;
	}

	// TODO: добавить возможность менять представление в зависимости от внешних
	// настроек
	public String getAttributeStringPresentation()
	{
		return this.attribute.getName() + " : " + this.attribute.getType().getName();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public String toString()
	{
		return this.attribute.getName();
	}

}
