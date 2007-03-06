/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author Soultakov Maxim
 */
public class SelectedItems implements Iterable<Viewable>{

    /**
     * 
     */
    private Set<Viewable> selectedEntities = new HashSet<Viewable>();

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
        return selectedEntities.add(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(Viewable item) {
        item.setSelected(false);
        return selectedEntities.remove(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean contains(Viewable item) {
        return selectedEntities.contains(item);
    }
    
    

    /**
     * 
     */
    public void clear() {
        for (Viewable item : selectedEntities) {
            item.setSelected(false);
        }
        selectedEntities.clear();
    }

    public Iterator<Viewable> iterator() {
        return selectedEntities.iterator();
    }

}
