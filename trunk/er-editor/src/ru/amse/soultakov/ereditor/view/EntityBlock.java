/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author sma
 *
 */
public class EntityBlock extends JComponent
{
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		g.drawRect(2, 2, 40, 40);
	}
}
