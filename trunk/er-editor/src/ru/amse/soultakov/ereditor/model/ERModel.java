/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static ru.amse.soultakov.ereditor.model.SimpleAttributeType.CHAR;
import static ru.amse.soultakov.ereditor.util.CommonUtils.hasNull;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import ru.amse.soultakov.ereditor.util.AutoincrementGenerator;

/**
 * @author sma
 * 
 */
public class ERModel {

    private final Set<Entity> entities = newLinkedHashSet();

    private final Set<Relationship> relationships = newLinkedHashSet();

    private final Set<Comment> comments = newLinkedHashSet();

    private final Set<Link> links = newLinkedHashSet();

    private final NamesGenerator generator = new NamesGenerator();

    public ERModel() {
    }

    public Entity addNewEntity() {
        Entity entity = new Entity(generator.getEntityName());
        entities.add(entity);

        Attribute pk1 = new Attribute("Attr1", SimpleAttributeType.INTEGER, false,
                null);
        entity.addAttribute(pk1);
        Attribute pk2 = new Attribute("Attr2", SimpleAttributeType.DOUBLE, false,
                null);
        entity.addAttribute(pk2);
        entity.addToPrimaryKey(pk2);
        entity.addToPrimaryKey(pk1);
        Attribute u1 = new Attribute("Attr3", CHAR, false, null);
        entity.addAttribute(u1);
        Attribute u2 = new Attribute("Attr4", new ArrayAttributeType(CHAR, 5),
                false, null);
        entity.addAttribute(u2);
        entity.addToUniqueAttributes(new HashSet<AbstractAttribute>(Arrays.asList(
                u1, u2)));
        return entity;
    }

    public Comment addNewComment() {
        Comment comment = new Comment(generator.getCommentName());
        comments.add(comment);
        return comment;
    }

    public Relationship addNewRealtionship(Entity first, Entity second) {
        if (hasNull(first, second)) {
            throw new IllegalArgumentException(
                    "Name and both entities must be non-null");
        } else if (first.equals(second) || !entities.contains(first)
                || !entities.contains(second)) {
            throw new IllegalArgumentException(
                    "Both entities must present in diagram and be unequal to each other");
        } else if (!first.acceptRelationshipWith(second)
                || !second.acceptRelationshipWith(first)) {
            throw new IllegalArgumentException("Entities don't accept relationhsip");
        }
        Random r = new Random();
        Relationship relationship = new Relationship(new PKRelationshipEnd(first,
                RelationshipMultiplicity.values()[r.nextInt(2) + 2], "End1"),
                new PKRelationshipEnd(second, RelationshipMultiplicity.values()[r
                        .nextInt(2) + 2], "End2"));
        relationships.add(relationship);
        first.addRelationship(relationship);
        second.addRelationship(relationship);
        return relationship;
    }

    public Link addNewLink(Entity entity, Comment comment) {
        if (hasNull(entity, comment)) {
            throw new IllegalArgumentException("Entity and comment must be non-null");
        } else if (!entities.contains(entity) || !comments.contains(comment)) {
            throw new IllegalArgumentException(
                    "Enity and comment must present in diagram");
        } else if (!entity.acceptLinkWith(comment)
                || !comment.acceptLinkWith(entity)) {
            throw new IllegalArgumentException(
                    "Entity or diagram doesn't accept link");
        }
        Link link = new Link(entity, comment);
        links.add(link);
        return link;
    }

    public boolean removeEntity(Entity entity) {
        if (entities.remove(entity)) {
            for (Iterator<Relationship> i = entity.relationshipsIterator(); i
                    .hasNext();) {
                Relationship relationship = i.next();
                Entity another = relationship.getFirstEnd().getEntity() == entity ? relationship
                        .getSecondEnd().getEntity()
                        : relationship.getFirstEnd().getEntity();
                another.removeRelationship(relationship);
                relationships.remove(relationship);
            }
            for (Iterator<Link> i = entity.linksIterator(); i.hasNext();) {
                Link link = i.next();
                link.getComment().removeLink(link);
                links.remove(link);
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
                links.remove(link);
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
    private static class NamesGenerator {
        private static final String NEW_ENTITY = "New Entity ";

        private static final String NEW_COMMENT = "New Comment ";

        private static final String NEW_RELATIONSHIP = "New relationship ";

        private AutoincrementGenerator entityGenerator = new AutoincrementGenerator();

        private AutoincrementGenerator commentGenerator = new AutoincrementGenerator();

        private AutoincrementGenerator relationshipGenerator = new AutoincrementGenerator();

        public NamesGenerator() {
        }

        public String getEntityName() {
            return NEW_ENTITY + entityGenerator.getNextInteger();
        }

        public String getCommentName() {
            return NEW_COMMENT + commentGenerator.getNextInteger();
        }

        public String getRelationshipName() {
            return NEW_RELATIONSHIP + relationshipGenerator.getNextInteger();
        }

    }

}
