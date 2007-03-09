/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.Viewable;

/**
 * @author Soultakov Maxim
 */
public class SelectedItems implements Iterable<Viewable> {

    /**
     * 
     */
    private Set<Viewable> selectedViews = new HashSet<Viewable>();

    /**
     * 
     */
    public SelectedItems() {
    }

    /**
     * @param item
     * @return
     */
    public boolean add(Viewable item) {
        item.setSelected(true);
        return selectedViews.add(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(Viewable item) {
        item.setSelected(false);
        return selectedViews.remove(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean contains(Viewable item) {
        return selectedViews.contains(item);
    }

    /**
     * 
     */
    public void clear() {
        for (Viewable item : selectedViews) {
            item.setSelected(false);
        }
        selectedViews.clear();
    }
    
    public void setSelection(Viewable viewable) {
        clear();
        add(viewable);
    }

    public Iterator<Viewable> iterator() {
        return selectedViews.iterator();
    }

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return selectedViews.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return selectedViews.toString();
	}

}
