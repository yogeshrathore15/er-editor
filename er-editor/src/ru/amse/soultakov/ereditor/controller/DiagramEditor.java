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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private final Map<Entity, EntityView> entityToView = new HashMap<Entity, EntityView>();

    private MouseInputAdapter mouseHandler;

    private final Map<Relationship, RelationshipView> relationshipToView = new HashMap<Relationship, RelationshipView>();

    private final Set<RelationshipView> relationshipViews = new HashSet<RelationshipView>();

    private final SelectedItems selectedItems = new SelectedItems();

    public DiagramEditor() {
        initMouseListener();
    }

    public EntityView addEntity(Entity entity, int x, int y) {
        if (entityToView.containsKey(entity)) {
            throw new IllegalArgumentException("This entity is already in map");
        }
        EntityView entityView = new EntityView(entity, x, y);
        EntityMouseInputAdapter listener = new EntityMouseInputAdapter(entityView);
        entityView.addMouseListener(listener);
        entityView.addMouseMotionListener(listener);
        entityToView.put(entity, entityView);
        add(entityView);
        this.repaint();
        return entityView;
    }

    public void addRelationship(Relationship relationship) {
        RelationshipView view = new RelationshipView(relationship, entityToView
                .get(relationship.getFirstEnd().getEntity()), entityToView
                .get(relationship.getSecondEnd().getEntity()));
        relationshipToView.put(relationship, view);
        addRelationshipView(view);
    }

    public boolean contains(EntityView entityView) {
        for (Component component : getComponents()) {
            if (component == entityView) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    /**
     * @return
     */
    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    /**
     * 
     */
    public void removeSelection() {
        for (Selectable s : getSelectedItems()) {
            removeSelectable(s);
        }
        repaint();
    }

    public void setMouseInputAdapter(MouseInputAdapter adapter) {
        this.removeMouseListener(mouseHandler);
        this.removeMouseMotionListener(mouseHandler);
        this.addMouseListener(adapter);
        this.addMouseMotionListener(adapter);
        mouseHandler = adapter;
    }

    @Override
    protected void paintChildren(Graphics g) {
        for (RelationshipView view : relationshipViews) {
            view.paint(g);
        }
        super.paintChildren(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * @param view
     */
    private void addRelationshipView(RelationshipView view) {
        if (!contains(view.getFirstEntityView())
                || !contains(view.getSecondEntityView())) {
            throw new IllegalArgumentException(
                    "Entities must belong to DiagramEditor");
        }
        relationshipViews.add(view);
    }

    /**
     * 
     */
    private void initMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getSource().getClass() == EntityView.class) {
                    return;
                }
                boolean clear = true;
                for (RelationshipView rv : relationshipViews) {
                    if (rv.contains(e.getX(), e.getY())) {
                        if (e.isControlDown()) {
                            selectedItems.add(rv);
                        } else {
                            selectedItems.clear();
                            selectedItems.add(rv);
                        }
                        clear = false;
                        break;
                    }
                }
                if (clear) {
                    selectedItems.clear();
                }
                repaint();
            }
        });
    }

    private void removeEntityView(EntityView e) {
        remove(e);
        Entity entity = e.getEntity();
        entityToView.remove(entity);
        for (Iterator<Relationship> i = entity.relationshipsIterator(); i.hasNext();) {
            Relationship relationship = i.next();
            relationshipViews.remove(relationshipToView.remove(relationship));
            if (relationship.getFirstEnd().getEntity() == entity) {
                relationship.getFirstEnd().getEntity().removeRelationship(
                        relationship);
            } else {
                i.remove();
            }
            if (relationship.getSecondEnd().getEntity() == entity) {
                relationship.getSecondEnd().getEntity().removeRelationship(
                        relationship);
            } else {
                i.remove();
            }
        }
    }

    private void removeRelationshipView(RelationshipView view) {
        view.getFirstEntityView().getEntity().removeRelationship(
                view.getRelationship());
        view.getSecondEntityView().getEntity().removeRelationship(
                view.getRelationship());
        relationshipToView.remove(view.getRelationship());
        relationshipViews.remove(view);
    }

    private void removeSelectable(Selectable s) {
        if (s instanceof EntityView) {
            removeEntityView((EntityView) s);
        } else if (s instanceof RelationshipView) {
            removeRelationshipView((RelationshipView) s);
        }
    }

    /**
     * @author sma
     * 
     */
    private class EntityMouseInputAdapter extends MouseInputAdapter {

        private Point current;

        private EntityView entityView;

        public EntityMouseInputAdapter(EntityView entityView) {
            this.entityView = entityView;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int xPos = e.getXOnScreen() - current.x + entityView.getX();
            int yPos = e.getYOnScreen() - current.y + entityView.getY();
            entityView.setLocation(xPos >= 0 ? xPos : 0, yPos >= 0 ? yPos : 0);
            current = e.getLocationOnScreen();
            repaint();
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
            e.translatePoint(-entityView.getX(), -entityView.getY());
            DiagramEditor.this.dispatchEvent(e);
            repaint();
        }
    }

}
