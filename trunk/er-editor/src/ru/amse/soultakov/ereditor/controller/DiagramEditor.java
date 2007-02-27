/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(300, 300);

    private SelectedItems selectedItems = new SelectedItems();
    
    private Set<RelationshipView> relationshipViews = new HashSet<RelationshipView>();

    public DiagramEditor() {
        initMouseListener();
    }
    
    public void add(RelationshipView view) {
        relationshipViews.add(view);
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
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    @Override
    protected void paintChildren(Graphics g) {
        for(RelationshipView view : relationshipViews) {
            view.paint(g);
        }
        super.paintChildren(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

}
