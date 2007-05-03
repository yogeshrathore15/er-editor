/*
 * Created on 29.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public class Constraint<T extends AbstractAttribute> implements Iterable<T> {

    private final Set<T> attributes;

    public Constraint() {
        this.attributes = newLinkedHashSet();
    }

    public Constraint(Set<T> attributes) {
        this.attributes = newLinkedHashSet(attributes);
    }

    public Set<T> getAttributes() {
        return Collections.unmodifiableSet(attributes);
    }

    public boolean add(T attribute) {
        return attributes.add(attribute);
    }

    public boolean remove(AbstractAttribute attribute) {
        return attributes.remove(attribute);
    }

    public Iterator<T> iterator() {
        return attributes.iterator();
    }

    public boolean contains(AbstractAttribute o) {
        return this.attributes.contains(o);
    }

    public int size() {
        return attributes.size();
    }

}
