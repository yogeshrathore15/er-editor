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
public class SelectedItems implements Iterable<IViewable> {

    /**
     * 
     */
    private Set<IViewable> selectedViews = new HashSet<IViewable>();

    /**
     * 
     */
    public SelectedItems() {
    }

    /**
     * @param item
     * @return
     */
    public boolean add(IViewable item) {
        item.setSelected(true);
        return selectedViews.add(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(IViewable item) {
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
    public boolean contains(IViewable item) {
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

    public void setSelection(IViewable viewable) {
        clear();
        add(viewable);
    }

    public Iterator<IViewable> iterator() {
        return selectedViews.iterator();
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return selectedViews.isEmpty();
    }

    public Set<IViewable> getAsSet() {
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
