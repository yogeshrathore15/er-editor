/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;

/**
 * @author Soultakov Maxim
 *
 */
public class PrimaryKeyCompartment extends Compartment
{

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param entityView
	 */
	public PrimaryKeyCompartment(int x, int y, EntityView entityView)
	{
		super(x, y, entityView);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void paint(Graphics2D g)
	{
		
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
