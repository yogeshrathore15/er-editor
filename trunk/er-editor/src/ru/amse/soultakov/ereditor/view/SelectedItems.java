/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Soultakov Maxim
 */
public class SelectedItems<T extends IViewable> implements Iterable<T> {

    /**
     * 
     */
    private Set<T> selectedViews = new HashSet<T>();

    /**
     * 
     */
    public SelectedItems() {
    	//
    }

    /**
     * @param item
     * @return
     */
    public boolean add(T item) {
        item.setSelected(true);
        return selectedViews.add(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(T item) {
        if (item != null) {
            item.setSelected(false);
            return selectedViews.remove(item);
        }
        return false;
    }

    /**
     * @param item
     * @return
     */
    public boolean contains(T item) {
        return selectedViews.contains(item);
    }

    /**
     * 
     */
    public void clear() {
        for (IViewable item : selectedViews) {
            item.setSelected(false);
        }
        selectedViews.clear();
    }

    public void setSelection(T viewable) {
        clear();
        add(viewable);
    }

    public Iterator<T> iterator() {
        return selectedViews.iterator();
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return selectedViews.isEmpty();
    }

    public Set<T> toSet() {
        return Collections.unmodifiableSet(selectedViews);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return selectedViews.toString();
    }

}
