/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static ru.amse.soultakov.ereditor.model.RelationshipMultiplicity.ONE_ONLY;
import static ru.amse.soultakov.ereditor.util.Utils.hasNull;
import static ru.amse.soultakov.ereditor.util.Utils.newLinkedHashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import ru.amse.soultakov.ereditor.util.AutoincrementGenerator;

/**
 * @author sma
 * 
 */
public class Diagram {

    private static final String NEW_ENTITY = "New Entity ";

    private static final String NEW_COMMENT = "New Comment ";

    private final Set<Entity> entities = newLinkedHashSet();

    private final Set<Relationship> relationships = newLinkedHashSet();

    private final Set<Comment> comments = newLinkedHashSet();

    private final Set<Link> links = newLinkedHashSet();

    private final NumbersGenerator generator = new NumbersGenerator();

    public Diagram() {
    }

    public Entity addNewEntity() {
        Entity entity = new Entity(NEW_ENTITY + generator.getEntityNumber());
        entities.add(entity);
        return entity;
    }

    public Comment addNewComment() {
        Comment comment = new Comment(NEW_COMMENT + generator.getCommentNumber());
        comments.add(comment);
        return comment;
    }

    public Relationship addNewRealtionship(String name, Entity first, Entity second) {
        if (hasNull(name, first, second)) {
            throw new IllegalArgumentException(
                    "Name and both entities must be non-null");
        } else if (first.equals(second) || !entities.contains(first)
                || !entities.contains(second)) {
            throw new IllegalArgumentException(
                    "Both entities must present in diagram and be unequal to each other");
        }
        Relationship relationship = new Relationship(name, new RelationshipEnd(
                first, ONE_ONLY), new RelationshipEnd(second, ONE_ONLY));
        relationships.add(relationship);
        return relationship;
    }
    
    public Link addNewLink(Entity entity, Comment comment) {
        if (hasNull(entity, comment)) {
            throw new IllegalArgumentException("Entity and comment must be non-null");
        } else if (!entities.contains(entity) || !comments.contains(comment)) {
            throw new IllegalArgumentException("Enity and comment must present in diagram");
        }
        Link link = new Link(entity, comment);
        links.add(link);
        return link;
    }
    
    public boolean removeEntity(Entity entity) {
        if (entities.remove(entity)) {
            for (Iterator<Relationship> i = entity.relationshipsIterator(); i.hasNext();) {
                Relationship relationship = i.next();
                Entity another = relationship.getFirstEnd().getEntity() == entity 
                        ? relationship.getSecondEnd().getEntity() 
                        : relationship.getFirstEnd().getEntity();
                another.removeRelationship(relationship);
            }
            for (Iterator<Link> i = entity.linksIterator(); i.hasNext();) {
                Link link = i.next();
                link.getComment().removeLink(link);
            }
            return true;
        }
        return false;
    }
    
    public boolean removeComment(Comment comment) {
        if (comments.remove(comment)) {
            for (Iterator<Link> i = comment.linksIterator(); i.hasNext();) {
                Link link = i.next();
                link.getEntity().removeLink(link);
            }
            return true;
        }
        return false;
    }
    
    public boolean removeRelationship(Relationship relationship) {
        if (relationships.remove(relationship)) {
            relationship.getFirstEnd().getEntity().removeRelationship(relationship);
            relationship.getSecondEnd().getEntity().removeRelationship(relationship);
            return true;
        }
        return false;
    }
    
    public boolean removeLink(Link link) {
        if (links.remove(link)) {
            link.getComment().removeLink(link);
            link.getEntity().removeLink(link);
            return true;
        }
        return false;
    }
    
    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    public Set<Entity> getEntities() {
        return Collections.unmodifiableSet(entities);
    }

    public Set<Link> getLinks() {
        return Collections.unmodifiableSet(links);
    }

    public Set<Relationship> getRelationships() {
        return Collections.unmodifiableSet(relationships);
    }

    /**
     * @author Soultakov Maxim
     */
    private static class NumbersGenerator {
        private AutoincrementGenerator entityGenerator = new AutoincrementGenerator();

        private AutoincrementGenerator commentGenerator = new AutoincrementGenerator();

        public NumbersGenerator() {
        }

        public int getEntityNumber() {
            return entityGenerator.getNextInteger();
        }

        public int getCommentNumber() {
            return commentGenerator.getNextInteger();
        }

    }

}
