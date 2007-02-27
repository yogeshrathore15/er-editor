/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.util.HashSet;
import java.util.Set;


/**
 * @author Soultakov Maxim
 */
public class SelectedItems {

    /**
     * 
     */
    private Set<Selectable> selectedEntities = new HashSet<Selectable>();

    /**
     * 
     */
    public SelectedItems() {
    }

    /**
     * @param item
     * @return
     */
    public boolean add(Selectable item) {
        item.setSelected(true);
        return selectedEntities.add(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(Selectable item) {
        item.setSelected(false);
        return selectedEntities.remove(item);
    }

    /**
     * @param item
     * @return
     */
    public boolean contains(Selectable item) {
        return selectedEntities.contains(item);
    }

    /**
     * 
     */
    public void clear() {
        for (Selectable item : selectedEntities) {
            item.setSelected(false);
        }
        selectedEntities.clear();
    }

}
