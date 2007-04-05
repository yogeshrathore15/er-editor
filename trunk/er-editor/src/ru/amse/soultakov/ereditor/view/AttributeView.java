package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Comparator;

import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class AttributeView
{

	private AbstractAttribute attribute;

	private EntityView entityView;

	private Compartment compartment;

	private int index;

	public AttributeView(AbstractAttribute attribute, EntityView entityView,
			Compartment compartment, int index)
	{
		this.attribute = attribute;
		this.entityView = entityView;
		this.compartment = compartment;
		this.index = index;
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

	public void setAttribute(AbstractAttribute attribute)
	{
		this.attribute = attribute;
	}

	public EntityView getEntityView()
	{
		return this.entityView;
	}

	public void setEntityView(EntityView entityView)
	{
		this.entityView = entityView;
	}

	public int getIndex()
	{
		return this.index;
	}

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int paint(Graphics2D graphics)
	{
		String attrString = getAttributeStringPresentation();
		Rectangle2D bounds = GraphicsUtils.getStringBounds(graphics, attrString);
		int newCurY = getTopByIndex() + (int) bounds.getHeight();
		graphics.drawString(attrString, Block.MARGIN * 2 + entityView.getX(), newCurY);
		return newCurY + Block.MARGIN;
	}

	private int getTopByIndex()
	{
		return 0;
	}

	// TODO: добавить возможность менять представление в зависимости от внешних
	// настроек
	public String getAttributeStringPresentation()
	{
		return this.attribute.getName();
	}

}
