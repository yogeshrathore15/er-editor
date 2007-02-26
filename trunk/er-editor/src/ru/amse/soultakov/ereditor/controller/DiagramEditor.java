/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(300,300);
    
    private SelectedItems selectedItems = new SelectedItems();
    
    public DiagramEditor() {
	initMouseListener();
    }

    /**
     * 
     */
    private void initMouseListener() {
	this.addMouseListener(new MouseAdapter() {

	    @Override
	    public void mousePressed(MouseEvent e) {
	        selectedItems.clear();
	    }
	    
	});
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

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

}
