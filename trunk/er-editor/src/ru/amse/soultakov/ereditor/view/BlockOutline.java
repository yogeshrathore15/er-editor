/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Soultakov Maxim
 *
 */
public class BlockOutline extends Block implements IViewable
{

	/**
	 * @param diagram
	 * @param x
	 * @param y
	 */
	public BlockOutline(Diagram diagram, Block block)
	{
		super(diagram, block.getX(), block.getY());
		this.width = block.width;
		this.height = block.height;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected Dimension getContentBounds(Graphics2D graphics)
	{
		return new Dimension(width, height);
	}

	/** 
	 * {@inheritDoc}
	 */
	public <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data)
	{
		throw new UnsupportedOperationException();
	}

	/** 
	 * {@inheritDoc}
	 */
	public void paint(Graphics2D graphics)
	{
		graphics.setColor(Color.BLACK);
		graphics.drawRect(x, y, width, height);
	}



}
