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
import java.util.HashSet;
import java.util.Iterator;
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
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.Line;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private final Map<Entity, EntityView> entityToView = newHashMap();

    private final Map<Comment, CommentView> commentToView = newHashMap();

    private final Map<Relationship, RelationshipView> relationshipToView = newHashMap();
    
    private final Map<Link, LinkView> linkToView = newHashMap();

    private MouseInputAdapter mouseHandler;

    private final Set<RelationshipView> relationshipViews = new HashSet<RelationshipView>();

    private final SelectedItems selectedItems = new SelectedItems();

    private final Set<LinkView> linkViews = new HashSet<LinkView>();
    
    private final Set<EntityView> entityViews = new HashSet<EntityView>();
    
    private final Set<CommentView> commentViews = new HashSet<CommentView>();

    public DiagramEditor() {
        initMouseListener();
    }

    public EntityView addEntity(Entity entity, int x, int y) {
        if (entityToView.containsKey(entity)) {
            throw new IllegalArgumentException("This entity is already in diagram");
        }
        EntityView entityView = new EntityView(entity, x, y);
        entityToView.put(entity, entityView);
        entityViews.add(entityView);
        return entityView;
    }

    public CommentView addComment(Comment comment, int x, int y) {
        if (commentToView.containsKey(comment)) {
            throw new IllegalArgumentException("This comment is already in diagram");
        }
        CommentView commentView = new CommentView(comment, x, y);
        commentToView.put(comment, commentView);
        commentViews.add(commentView);
        return commentView;
    }

    public LinkView addLink(Link link) {
        LinkView linkView = new LinkView(link, entityToView.get(link.getEntity()),
                commentToView.get(link.getComment()));
        addLinkView(linkView);
        return linkView;
    }

    private void addLinkView(LinkView view) {
        if (!contains(view.getEntityView()) || !contains(view.getCommentView())) {
            throw new IllegalArgumentException(
                    "Entity and comment must belong to DiagramEditor");
        }
        linkViews.add(view);
        linkToView.put(view.getLink(), view);
    }

    public RelationshipView addRelationship(Relationship relationship) {
        RelationshipView view = new RelationshipView(relationship, entityToView
                .get(relationship.getFirstEnd().getEntity()), entityToView
                .get(relationship.getSecondEnd().getEntity()));
        addRelationshipView(view);
        return view;
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
        relationshipToView.put(view.getRelationship(), view);
    }

    public boolean contains(Block block) {
        return true;
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
    	paintSet(relationshipViews, graphics);
        paintSet(linkViews, graphics);
        paintSet(entityViews, graphics);
        paintSet(commentViews, graphics);
    }
    
    private void paintSet(Set<? extends Viewable> setToPaint, Graphics2D graphics) {
    	for(Viewable v : setToPaint) {
    		v.paint(graphics);
    	}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * 
     */
    private void initMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean clearSelection = selectLines(e, relationshipViews);
                clearSelection &= selectLines(e, linkViews);
                if (clearSelection && !e.isControlDown()) {
                    selectedItems.clear();
                }
                repaint();
            }

            private boolean selectLines(MouseEvent e, Set<? extends Line> set) {
                for (Line line : set) {
                    if (line.containsPoint(e.getX(), e.getY())) {
                        if (e.isControlDown()) {
                            selectedItems.add(line);
                        } else {
                            selectedItems.clear();
                            selectedItems.add(line);
                        }
                        return false;
                    }
                }
                return true;
            }
        });
    }

    private void removeEntityView(EntityView view) {
        entityViews.remove(view);
        Entity entity = view.getEntity();
        entityToView.remove(entity);
        for (Iterator<Relationship> i = entity.relationshipsIterator(); i.hasNext();) {
            Relationship relationship = i.next();
            relationshipViews.remove(relationshipToView.remove(relationship));
            if (relationship.getFirstEnd().getEntity() == entity) {
                relationship.getFirstEnd().getEntity().removeRelationship(
                        relationship);
            } else if (relationship.getSecondEnd().getEntity() == entity) {
                relationship.getSecondEnd().getEntity().removeRelationship(
                        relationship);
            } else {
                i.remove();
            }
        }
    }

    private void removeCommentView(CommentView view) {
        commentViews.remove(view);
        Comment comment = view.getComment();
        commentToView.remove(comment);
        for (Iterator<Link> i = comment.linksIterator(); i.hasNext();) {
            Link link = i.next();
            link.getEntity().removeLink(link);
            linkViews.remove(linkToView.remove(link));
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
    
    private void removeLinkView(LinkView view) {
        view.getLink().getEntity().removeLink(view.getLink());
        view.getLink().getComment().removeLink(view.getLink());
        linkToView.remove(view.getLink());
        linkViews.remove(view);
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

    /**
     * @author sma
     * 
     */
    private class BlockMouseInputAdapter extends MouseInputAdapter {

        private Point current;

        private Block block;

        public BlockMouseInputAdapter(Block block) {
            this.block = block;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int xPos = e.getXOnScreen() - current.x + block.getX();
            int yPos = e.getYOnScreen() - current.y + block.getY();
            block.setLocation(xPos >= 0 ? xPos : 0, yPos >= 0 ? yPos : 0);
            current = e.getLocationOnScreen();
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isControlDown()) {
                if (block.isSelected()) {
                    getSelectedItems().remove(block);
                } else {
                    getSelectedItems().add(block);
                }
            } else {
                getSelectedItems().clear();
                getSelectedItems().add(block);
            }
            current = e.getLocationOnScreen();
            repaint();
        }
    }

    private static <E, T> HashMap<E, T> newHashMap() {
        return new HashMap<E, T>();
    }

}
