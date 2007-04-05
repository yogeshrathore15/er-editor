/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;

/**
 * @author Soultakov Maxim
 * 
 */
class NonPkCompartment extends Compartment
{

	/**
     * @param x
     * @param y
     * @param entityView
     */
	public NonPkCompartment(int x, int y, EntityView entityView)
	{
		super(x, y, entityView);
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public void paint(Graphics2D graphics)
	{
		int curY = y;
		for (AttributeView av : attributes)
		{
			curY = av.paint(graphics, x, curY);
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public int getHeight()
	{
		return 0;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public int getWidth()
	{
		return 0;
	}

}
