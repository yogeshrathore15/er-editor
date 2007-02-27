/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

	private static final Dimension PREFERRED_SIZE = new Dimension(300, 300);

    private final SelectedItems selectedItems = new SelectedItems();
    
    private final Set<RelationshipView> relationshipViews = new HashSet<RelationshipView>();
    
    private final Map<Entity, EntityView> entityToView = new HashMap<Entity, EntityView>();

    public DiagramEditor() {
        initMouseListener();
    }
    
    /**
     * @param view
     */
    public void add(RelationshipView view) {
        if(!contains(view.getFirstEntity()) || !contains(view.getSecondEntity())) {
            throw new IllegalArgumentException("Entities must belong to DiagramEditor");
        }
        relationshipViews.add(view);
    }
    
    public EntityView addEntity(Entity entity, int x, int y) {
		if (entityToView.containsKey(entity)) {
			throw new IllegalArgumentException("This entity is already in map");
		}
    	EntityView entityView = new EntityView(entity, x, y);
    	MyMouseInputAdapter listener = new MyMouseInputAdapter(entityView);
    	entityView.addMouseListener(listener);
    	entityView.addMouseMotionListener(listener);
		entityToView.put(entity, entityView);
    	return entityView;
	}
    
    public boolean contains(EntityView entityView) {
        for (Component component : getComponents()) {
            if (component == entityView) {
                return true;
            }
        }
        return false;
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
    
    /**
	 * @author sma
	 *
	 */
	private class MyMouseInputAdapter extends MouseInputAdapter {
		private Point current;
		private EntityView entityView;
		
		/**
		 * 
		 */
		public MyMouseInputAdapter(EntityView entityView) {
			this.entityView = entityView;
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
		    if (e.isControlDown()) {
		    	if (entityView.isSelected()) {
		    		getSelectedItems().remove(entityView);
		    	} else {
		    		getSelectedItems().add(entityView);
		    	}
		    } else {
		    	getSelectedItems().clear();
		    	getSelectedItems().add(entityView);
		    }
		    current = e.getLocationOnScreen();
		}
	
		@Override
		public void mouseDragged(MouseEvent e) {
		    entityView.shift(e.getXOnScreen() - current.x, e.getYOnScreen()
		            - current.y);
		    repaint();
		    current = e.getLocationOnScreen();
		}
	}

}
