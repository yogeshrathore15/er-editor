/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @author sma
 *
 */
public class DiagramEditor extends JComponent
{

	public DiagramEditor()
	{
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(getX(), getY(), getWidth(), getHeight());		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(200,100);
	}
}
