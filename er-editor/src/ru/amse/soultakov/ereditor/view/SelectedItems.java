/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newArrayList;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Soultakov Maxim
 */
public class SelectedItems<T extends IViewable> implements Iterable<T> {

    private Set<T> selectedViews = newLinkedHashSet();

    private List<SelectedItemsListener> listeners = newArrayList();

    public SelectedItems() {
        //
    }

    /**
     * @param item
     * @return
     */
    public boolean add(T item) {
        if (item != null) {
            item.setSelected(true);
            boolean result = selectedViews.add(item);
            notifyListeners();
            return result;
        }
        return false;
    }

    /**
     * @param item
     * @return
     */
    public boolean remove(T item) {
        if (item != null) {
            item.setSelected(false);
            boolean result = selectedViews.remove(item);
            notifyListeners();
            return result;
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
        notifyListeners();
    }

    public void setSelection(T viewable) {
        clear();
        add(viewable);
        notifyListeners();
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

    public int size() {
        return selectedViews.size();
    }

    public Set<T> asSet() {
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

    public boolean addListener(SelectedItemsListener listener) {
        return listeners.add(listener);
    }

    public boolean removeListener(SelectedItemsListener listener) {
        return listeners.remove(listener);
    }

    private void notifyListeners() {
        for (SelectedItemsListener sil : listeners) {
            sil.selectionChanged(this);
        }
    }

    public T getFirst() {
        return selectedViews.iterator().next();
    }

}
