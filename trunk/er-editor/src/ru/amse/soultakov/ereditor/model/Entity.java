/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import java.util.Iterator;
import java.util.List;

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
	private List<Attribute> attributes;

	/**
	 * @param name
	 */
	public Entity(String name) {
		super();
		this.name = name;
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

	public void addAttribute(Attribute attribute) {
		attributes.add(attribute);
	}

	public boolean removeAttribute(Attribute attribute) {
		return attributes.remove(attribute);
	}

	public void removeAttribute(int index) {
		attributes.remove(index);
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