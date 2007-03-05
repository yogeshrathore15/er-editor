/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author sma
 * 
 */
public class Entity implements Iterable<Attribute> {
	
    /**
	 * 
	 */
	private String name;

	/**
	 * 
	 */
	private final List<Attribute> attributes;

    /**
     * 
     */
    private final Set<Relationship> relationships = new HashSet<Relationship>();
    
    private final Set<Link> links = new HashSet<Link>();
	
    /**
	 * @param name
	 */
	public Entity(String name) {
		super();
		this.name = name;
        attributes = new ArrayList<Attribute>();
	}

	/**
	 * @param name
	 * @param attributes
	 */
	public Entity(String name, List<Attribute> attributes) {
		super();
		this.name = name;
		this.attributes = attributes;
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
		attributes.add(attribute);
	}

	/**
	 * @param attribute
	 * @return
	 */
	public boolean removeAttribute(Attribute attribute) {
		return attributes.remove(attribute);
	}

	/**
	 * @param index
	 */
	public void removeAttribute(int index) {
		attributes.remove(index);
	}
    
    public boolean addRelationship(Relationship relationship) {
        return relationships.add(relationship);
    }
    
    public boolean removeRelationship(Relationship relationship) {
    	return relationships.remove(relationship);
    }
    
    public Iterator<Relationship> relationshipsIterator() {
        return relationships.iterator();
    }
    
    public boolean addLink(Link link) {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Entity other = (Entity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
