/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;

/**
 * @author Soultakov Maxim
 *
 */
public class NonPkCompartment extends Compartment
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
	public void paint(Graphics2D g)
	{
		
	}

}
