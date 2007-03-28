/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * @author sma
 * 
 */
public class Entity implements Iterable<Attribute> {

    private static final String PARAM_CANT_BE_NULL = "Param must be non null value"; 

    private static final String RELATIONSHIP_CANT_BE_NULL = "Relationship can't be null"; 

    private final Set<Attribute> attributes = newLinkedHashSet();;
    
    private final Index<Attribute> primaryKey = new Index<Attribute>();
    
    private final Set<Index<FKAttribute>> foreignKey = newLinkedHashSet();
    
    private final Set<Index<Attribute>> uniqueAttributes = newLinkedHashSet();

    private final Set<Relationship> relationships = newLinkedHashSet();

    private final Set<Link> links = newLinkedHashSet();
    
    private String name;

    /**
     * @param name
     */
    public Entity(String name) {
        setName(name);
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

    public Collection<Attribute> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    public Collection<Link> getLinks() {
        return Collections.unmodifiableSet(links);
    }

    public Collection<Relationship> getRelationships() {
        return Collections.unmodifiableSet(relationships);
    }

    public boolean acceptRelationshipWith(Entity entity) {
        for (Relationship r : relationships) {
            if ((r.getFirstEnd().getEntity().equals(entity))
                    || (r.getSecondEnd().getEntity().equals(entity))) {
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
    
    public void addToPrimaryKey(Attribute attribute) {
        primaryKey.add(attribute);
        attributes.add(attribute);
    }
    
    public Index<Attribute> getPrimaryKey() {
        return primaryKey;
    }
    
    public boolean removeFromPrimaryKey(Attribute attribute) {
    	return primaryKey.remove(attribute);
    }
    
    public Collection<Index<Attribute>> getUniqueAttributes() {
        return Collections.unmodifiableSet(uniqueAttributes);
    }
    
    public void addToUniqueAttributes(Set<Attribute> set) {
    	attributes.addAll(set);
    	uniqueAttributes.add(new Index<Attribute>(set));
    }
    
    public boolean removeFromUniqueAttributes(Set<Attribute> set) {
    	return uniqueAttributes.remove(set);
    }
    
    public Collection<Index<FKAttribute>> getForeignKey() {
        return Collections.unmodifiableSet(foreignKey);
    }

}
