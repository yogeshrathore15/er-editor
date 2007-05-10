/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import static ru.amse.soultakov.ereditor.util.CommonUtils.hasNull;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Link;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.util.CommonUtils;

public class Diagram {

    private ERModel erModel;

    private final Set<EntityView> entityViews = newLinkedHashSet();

    private final Set<CommentView> commentViews = newLinkedHashSet();

    private final Set<RelationshipView> relationshipViews = newLinkedHashSet();

    private final Set<LinkView> linkViews = newLinkedHashSet();

    private final Map<Entity, EntityView> entityToView = newHashMap();

    private final Map<Comment, CommentView> commentToView = newHashMap();

    private final Map<Relationship, RelationshipView> relationshipToView = newHashMap();

    private final Map<Link, LinkView> linkToView = newHashMap();

    private final List<IDiagramListener> listeners = new ArrayList<IDiagramListener>();

    public Diagram() {
        erModel = new ERModel();
    }

    public static Diagram load(IDiagramLoader loader, IProgressMonitor monitor)
            throws DiagramLoadingException {
        ERModel model = loader.loadModel(monitor);
        Diagram diagram = loader.loadDiagram(monitor);
        diagram.erModel = model;
        return diagram;
    }

    public EntityView addNewEntityView(int x, int y) {
        Entity entity = erModel.addNewEntity();
        EntityView entityView = new EntityView(this, entity, x, y);
        entityViews.add(entityView);
        entityToView.put(entity, entityView);
        notifyListeners();
        return entityView;
    }

    public EntityView addEntityView(EntityView entityView) {
        if (entityView == null || entityView.getDiagram() != this) {
            throw new IllegalArgumentException();
        }
        erModel.addEntity(entityView.getEntity());
        entityViews.add(entityView);
        entityToView.put(entityView.getEntity(), entityView);
        notifyListeners();
        return entityView;
    }

    public CommentView addNewCommentView(int x, int y) {
        Comment comment = erModel.addNewComment();
        CommentView commentView = new CommentView(this, comment, x, y);
        commentViews.add(commentView);
        commentToView.put(comment, commentView);
        notifyListeners();
        return commentView;
    }

    public CommentView addCommentView(CommentView commentView) {
        if (commentView == null || commentView.getDiagram() != this) {
            throw new IllegalArgumentException();
        }
        erModel.addComment(commentView.getComment());
        commentViews.add(commentView);
        commentToView.put(commentView.getComment(), commentView);
        notifyListeners();
        return commentView;
    }

    public RelationshipView addNewRelationshipView(EntityView first,
            EntityView second) {
        if (hasNull(first, second)) {
            throw new IllegalArgumentException("Both EntityViews must be non-null");
        } else if (!entityViews.contains(first) || !entityViews.contains(second)
                || first.equals(second)) {
            throw new IllegalArgumentException(
                    "Both EntityViews must present in diagram and be unequal");
        }
        Relationship relationship = erModel.addNewRelationship(first.getEntity(),
                second.getEntity());
        RelationshipView relationshipView = new RelationshipView(this, relationship);
        relationshipViews.add(relationshipView);
        relationshipToView.put(relationship, relationshipView);
        notifyListeners();
        return relationshipView;
    }

    public RelationshipView addRelationshipView(RelationshipView relationshipView) {
        if (relationshipView == null || relationshipView.getDiagram() != this) {
            throw new IllegalArgumentException();
        }
        erModel.addRelationship(relationshipView.getRelationship());
        relationshipViews.add(relationshipView);
        relationshipToView.put(relationshipView.getRelationship(), relationshipView);
        notifyListeners();
        return relationshipView;
    }
    
    public void removeFKRelationshipView(RelationshipView rv) {
        
    }
    
    public RelationshipView addFKRelationship(EntityView first, EntityView second) {
        if (hasNull(first, second)) {
            throw new IllegalArgumentException("Both EntityViews must be non-null");
        } else if (!entityViews.contains(first) || !entityViews.contains(second)
                || first.equals(second)) {
            throw new IllegalArgumentException(
                    "Both EntityViews must present in diagram and be unequal");
        }
        Relationship relationship = erModel.addNewFKRelationship(first.getEntity(),
                second.getEntity());
        second.initAttributes();
        RelationshipView relationshipView = new RelationshipView(this, relationship);
        relationshipViews.add(relationshipView);
        relationshipToView.put(relationship, relationshipView);
        notifyListeners();
        return relationshipView;
    }

    public LinkView addNewLinkView(EntityView entityView, CommentView commentView) {
        if (hasNull(entityView, commentView)) {
            throw new IllegalArgumentException(
                    "EntityView and CommentView must be non-null");
        } else if (!commentViews.contains(commentView)
                || !entityViews.contains(entityView)) {
            throw new IllegalArgumentException(
                    "EntityView and CommentView must present in diagram");
        }
        Link link = erModel.addNewLink(entityView.getEntity(), commentView
                .getComment());
        LinkView linkView = new LinkView(this, link);
        linkViews.add(linkView);
        linkToView.put(link, linkView);
        notifyListeners();
        return linkView;
    }

    public LinkView addLinkView(LinkView linkView) {
        if (linkView == null || linkView.getDiagram() != this) {
            throw new IllegalArgumentException();
        }
        erModel.addLink(linkView.getLink());
        linkViews.add(linkView);
        linkToView.put(linkView.getLink(), linkView);
        return linkView;
    }

    public boolean removeEntityView(EntityView entityView) {
        if (erModel.removeEntity(entityView.getEntity())) {
            Entity entity = entityView.getEntity();
            for (Iterator<Relationship> i = entity.relationshipsIterator(); i
                    .hasNext();) {
                Relationship relationship = i.next();
                relationshipViews.remove(relationshipToView.remove(relationship));
            }
            for (Iterator<Link> i = entity.linksIterator(); i.hasNext();) {
                Link link = i.next();
                linkViews.remove(linkToView.remove(link));
            }
            notifyListeners();
            return entityViews.remove(entityToView.remove(entity));
        }
        return false;
    }

    public boolean removeRelationshipView(RelationshipView view) {
        if (erModel.removeRelationship(view.getRelationship())) {
            notifyListeners();
            return relationshipViews.remove(relationshipToView.remove(view
                    .getRelationship()));
        }
        return false;
    }

    public boolean removeCommentView(CommentView commentView) {
        if (erModel.removeComment(commentView.getComment())) {
            Comment comment = commentView.getComment();
            for (Link link : comment) {
                linkViews.remove(linkToView.remove(link));
            }
            notifyListeners();
            return commentViews.remove(commentToView.remove(comment));
        }
        return false;
    }

    public boolean removeLinkView(LinkView linkView) {
        if (erModel.removeLink(linkView.getLink())) {
            notifyListeners();
            return linkViews.remove(linkToView.remove(linkView.getLink()));
        }
        return false;
    }

    public Collection<CommentView> getCommentViews() {
        return Collections.unmodifiableSet(commentViews);
    }

    public Collection<EntityView> getEntityViews() {
        return Collections.unmodifiableSet(entityViews);
    }

    public Collection<LinkView> getLinkViews() {
        return Collections.unmodifiableSet(linkViews);
    }

    public Collection<RelationshipView> getRelationshipViews() {
        return Collections.unmodifiableSet(relationshipViews);
    }

    public EntityView getEntityView(int x, int y) {
        return getViewInPoint(entityViews, x, y);
    }

    public CommentView getCommentView(int x, int y) {
        return getViewInPoint(commentViews, x, y);
    }

    private static <E extends IViewable> E getViewInPoint(Set<E> set, int x, int y) {
        for (E v : set) {
            if (v.containsPoint(x, y)) {
                return v;
            }
        }
        return null;
    }

    public Dimension getSize() {
        Point currentMax = new Point(0, 0);
        CommonUtils.getRightBottomPoint(entityViews, currentMax);
        CommonUtils.getRightBottomPoint(commentViews, currentMax);
        return new Dimension(currentMax.x, currentMax.y);
    }

    public void addDiagramListener(IDiagramListener iDiagramListener) {
        listeners.add(iDiagramListener);
    }

    public boolean removeListener(IDiagramListener iDiagramListener) {
        return listeners.remove(iDiagramListener);
    }

    private void notifyListeners() {
        for (IDiagramListener dl : listeners) {
            dl.diagramModified(this);
        }
    }

    public Block getEntityView(Entity entity) {
        return entityToView.get(entity);
    }

    public CommentView getCommentView(Comment comment) {
        return commentToView.get(comment);
    }

    public void save(IDiagramSaver saver, IProgressMonitor monitor)
            throws DiagramSavingException {
        saver.save(this, erModel, monitor);
    }
}
