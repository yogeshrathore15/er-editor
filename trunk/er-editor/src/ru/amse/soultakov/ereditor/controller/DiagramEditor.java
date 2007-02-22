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
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(300,300);
        
    public DiagramEditor() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(getX(), getY(), getWidth(), getHeight());
    }
    
    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }
}
