/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import static ru.amse.soultakov.ereditor.util.Utils.hasNull;
import static ru.amse.soultakov.ereditor.util.Utils.newHashMap;
import static ru.amse.soultakov.ereditor.util.Utils.newLinkedHashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.Viewable;
import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.Diagram;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Link;
import ru.amse.soultakov.ereditor.model.Relationship;

public class DiagramPresentation {

    private final Diagram diagram = new Diagram();

    private final Set<EntityView> entityViews = newLinkedHashSet();

    private final Set<CommentView> commentViews = newLinkedHashSet();

    private final Set<RelationshipView> relationshipViews = newLinkedHashSet();

    private final Set<LinkView> linkViews = newLinkedHashSet();

    private final Map<Entity, EntityView> entityToView = newHashMap();

    private final Map<Comment, CommentView> commentToView = newHashMap();

    private final Map<Relationship, RelationshipView> relationshipToView = newHashMap();

    private final Map<Link, LinkView> linkToView = newHashMap();

    private final SelectedItems selectedItems = new SelectedItems();

    public DiagramPresentation() {
        ;
    }

    public EntityView addNewEntityView(int x, int y) {
        Entity entity = diagram.addNewEntity();
        EntityView entityView = new EntityView(entity, x, y);
        entityViews.add(entityView);
        entityToView.put(entity, entityView);
        return entityView;
    }

    public CommentView addNewCommentView(int x, int y) {
        Comment comment = diagram.addNewComment();
        CommentView commentView = new CommentView(comment, x, y);
        commentViews.add(commentView);
        commentToView.put(comment, commentView);
        return commentView;
    }

    public RelationshipView addNewRelationshipView(String name, EntityView first,
            EntityView second) {
        if (hasNull(first, second)) {
            throw new IllegalArgumentException("Both EntityViews must be non-null");
        } else if (!entityViews.contains(first) || !entityViews.contains(second)
                || first.equals(second)) {
            throw new IllegalArgumentException(
                    "Both EntityViews must present in diagram and be unequal");
        }
        Relationship relationship = diagram.addNewRealtionship(name, first
                .getEntity(), second.getEntity());
        RelationshipView relationshipView = new RelationshipView(relationship,
                first, second);
        relationshipViews.add(relationshipView);
        relationshipToView.put(relationship, relationshipView);
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
        Link link = diagram.addNewLink(entityView.getEntity(), commentView
                .getComment());
        LinkView linkView = new LinkView(link, entityView, commentView);
        linkViews.add(linkView);
        linkToView.put(link, linkView);
        return linkView;
    }

    public boolean removeEntityView(EntityView entityView) {
        if (diagram.removeEntity(entityView.getEntity())) {
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
            return entityViews.remove(entityToView.remove(entity));
        }
        return false;
    }

    public boolean removeRelationshipView(RelationshipView view) {
        if (diagram.removeRelationship(view.getRelationship())) {
            return relationshipViews.remove(relationshipToView.remove(view
                    .getRelationship()));
        }
        return false;
    }

    public boolean removeCommentView(CommentView commentView) {
        System.out.println(commentViews);
        System.out.println(commentView);
        if (diagram.removeComment(commentView.getComment())) {
            Comment comment = commentView.getComment();
            for (Iterator<Link> i = comment.linksIterator(); i.hasNext();) {
                linkViews.remove(linkToView.remove(i.next()));
            }
            return commentViews.remove(commentToView.remove(comment));
        }
        return false;
    }

    public boolean removeLinkView(LinkView linkView) {
        if (diagram.removeLink(linkView.getLink())) {
            return linkViews.remove(linkToView.remove(linkView.getLink()));
        }
        return false;
    }

    public Set<CommentView> getCommentViews() {
        return Collections.unmodifiableSet(commentViews);
    }

    public Set<EntityView> getEntityViews() {
        return Collections.unmodifiableSet(entityViews);
    }

    public Set<LinkView> getLinkViews() {
        return Collections.unmodifiableSet(linkViews);
    }

    public Set<RelationshipView> getRelationshipViews() {
        return Collections.unmodifiableSet(relationshipViews);
    }

    public SelectedItems getSelectedItems() {
        return selectedItems;
    }

    public EntityView getEntityView(int x, int y) {
        for (EntityView view : entityViews) {
            if (view.containsPoint(x, y)) {
                return view;
            }
        }
        return null;
    }

}
