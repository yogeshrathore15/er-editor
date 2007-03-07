/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Link;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.view.Block;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.DiagramPresentation;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);
    
    private DiagramPresentation diagram = new DiagramPresentation();

    private MouseInputAdapter mouseHandler;
    
    private final SelectedItems selectedItems = new SelectedItems();

    public DiagramEditor() {
        initMouseListener();
    }

    public EntityView addEntity(int x, int y) {
        return diagram.addNewEntityView(x, y);
    }

    public CommentView addComment(int x, int y) {
        return diagram.addNewCommentView(x, y);
    }

    public LinkView addLink(EntityView entityView, CommentView commentView) {
        return diagram.addNewLinkView(entityView, commentView);
    }

    public RelationshipView addRelationship(String name, EntityView first, EntityView second) {
        return diagram.addNewRelationshipView(getName(), first, second);
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
        for (Viewable s : getSelectedItems()) {
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
        Graphics2D graphics = (Graphics2D) g;
        //for correct relations painting we should recalculate the size of blocks
        recalculateSize(diagram.getCommentViews(), graphics);
        recalculateSize(diagram.getEntityViews(), graphics);
        paintSet(diagram.getLinkViews(), graphics);
        paintSet(diagram.getRelationshipViews(), graphics);
        paintSet(diagram.getCommentViews(), graphics);
        paintSet(diagram.getEntityViews(), graphics);
    }

    private void recalculateSize(Set<? extends Block> set, Graphics2D graphics) {
        for (Block b : set) {
            b.recalculateSize(graphics);
        }
    }

    private void paintSet(Set<? extends Viewable> setToPaint, Graphics2D graphics) {
        for (Viewable v : setToPaint) {
            v.paint(graphics);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    private final class DefaultTool extends MouseAdapter {
        private volatile Viewable currentElement;

        private volatile Point currentPoint;

        @Override
        public void mousePressed(MouseEvent e) {
            boolean nothingSelected = true;
            //&& operator is used to prevent selecting more then one element
            nothingSelected = nothingSelected && selectViews(e, diagram.getEntityViews());
            nothingSelected = nothingSelected && selectViews(e, diagram.getCommentViews());
            nothingSelected = nothingSelected && selectViews(e, diagram.getRelationshipViews());
            nothingSelected = nothingSelected && selectViews(e, diagram.getLinkViews());
            
            if (nothingSelected && !e.isControlDown()) {
                selectedItems.clear();
                this.currentElement = null;
            }
            currentPoint = e.getLocationOnScreen();
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (currentElement != null) {
                int xPos = e.getXOnScreen() - currentPoint.x + currentElement.getX();
                int yPos = e.getYOnScreen() - currentPoint.y + currentElement.getY();
                currentElement.setLocation(xPos >= 0 ? xPos : 0, yPos >= 0 ? yPos : 0);
                currentPoint = e.getLocationOnScreen();
                repaint();
            }
        }

        private boolean selectViews(MouseEvent e, Set<? extends Viewable> views) {
            for (Viewable view : views) {
                if (view.containsPoint(e.getX(), e.getY())) {
                    if (e.isControlDown()) {
                        if (view.isSelected()) {
                            selectedItems.remove(view);
                        } else {
                            selectedItems.add(view);
                        }
                    } else {
                        selectedItems.clear();
                        selectedItems.add(view);
                    }
                    this.currentElement = view;
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 
     */
    private void initMouseListener() {
        DefaultTool defaultTool = new DefaultTool();
        this.addMouseListener(defaultTool);
        this.addMouseMotionListener(defaultTool);
    }

    public boolean removeEntity(EntityView view) {
        return diagram.removeEntityView(view);
    }

    public boolean removeComment(CommentView view) {
        return diagram.removeCommentView(view);
    }

    public boolean removeRelationship(RelationshipView view) {
        return diagram.removeRelationshipView(view);
    }

    private void removeLink(LinkView view) {
        return diagram.removeLinkView(view);
    }

    private void removeSelectable(Viewable s) {
        // this awful code will be refactored of course
        // visitor rules?
        if (s instanceof EntityView) {
            removeEntityView((EntityView) s);
        } else if (s instanceof RelationshipView) {
            removeRelationshipView((RelationshipView) s);
        } else if (s instanceof CommentView) {
            removeCommentView((CommentView) s);
        } else if (s instanceof LinkView) {
            removeLinkView((LinkView) s);
        }
    }

    private static <E, T> HashMap<E, T> newHashMap() {
        return new HashMap<E, T>();
    }

}
