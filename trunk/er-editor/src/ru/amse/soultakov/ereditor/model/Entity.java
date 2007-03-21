/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static ru.amse.soultakov.ereditor.util.Utils.newLinkedHashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author sma
 * 
 */
public class Entity implements Iterable<Attribute> {

    private static final String PARAM_CANT_BE_NULL = "Param must be non null value"; //$NON-NLS-1$

    private static final String ATTIBUTES_CANT_BE_NULL = "Attributes can't be null"; //$NON-NLS-1$

    private static final String RELATIONSHIP_CANT_BE_NULL = "Relationship can't be null"; //$NON-NLS-1$

    private String name;

    private final Set<Attribute> attributes;
    
    private final Set<Attribute> primaryKey = newLinkedHashSet();
    
    private final Set<Attribute> uniqueAttributes = newLinkedHashSet();

    private final Set<Relationship> relationships = newLinkedHashSet();

    private final Set<Link> links = newLinkedHashSet();

    /**
     * @param name
     */
    public Entity(String name) {
        setName(name);
        attributes = newLinkedHashSet();
    }

    /**
     * @param name
     * @param attributes
     */
    public Entity(String name, Set<Attribute> attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException(ATTIBUTES_CANT_BE_NULL);
        }
        setName(name);
        this.attributes = newLinkedHashSet(attributes);
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(PARAM_CANT_BE_NULL);
        }
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Iterable#iterator()
     */
    public Iterator<Attribute> iterator() {
        return attributes.iterator();
    }

    /**
     * @param attribute
     */
    public void addAttribute(Attribute attribute) {
        if (attribute == null) {
            throw new IllegalArgumentException("Attribute can't be null");
        }
        attributes.add(attribute);
    }

    /**
     * @param attribute
     * @return
     */
    public boolean removeAttribute(Attribute attribute) {
        return attributes.remove(attribute);
    }

    public boolean addRelationship(Relationship relationship) {
        if (relationship == null) {
            throw new IllegalArgumentException(RELATIONSHIP_CANT_BE_NULL);
        }
        return relationships.add(relationship);
    }

    public boolean removeRelationship(Relationship relationship) {
        return relationships.remove(relationship);
    }

    public Iterator<Relationship> relationshipsIterator() {
        return relationships.iterator();
    }

    public boolean addLink(Link link) {
        if (link == null) {
            throw new IllegalArgumentException(RELATIONSHIP_CANT_BE_NULL);
        }
        return links.add(link);
    }

    public boolean removeLink(Link link) {
        return links.remove(link);
    }

    public Iterator<Link> linksIterator() {
        return links.iterator();
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entity other = (Entity) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public Set<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    public Set<Link> getLinks() {
        return Collections.unmodifiableSet(links);
    }

    public Set<Relationship> getRelationships() {
        return Collections.unmodifiableSet(relationships);
    }

    public boolean acceptRelationshipWith(Entity entity) {
        for (Relationship r : relationships) {
            if ((r.getFirstEnd().getEntity() == entity)
                    || (r.getSecondEnd().getEntity() == entity)) {
                return false;
            }
        }
        return true;
    }

    public boolean acceptLinkWith(Comment comment) {
        for (Link l : links) {
            if (l.getComment() == comment) {
                return false;
            }
        }
        return true;
    }

}
