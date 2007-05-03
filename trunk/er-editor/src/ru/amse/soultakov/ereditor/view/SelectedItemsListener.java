/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

/**
 * @author Soultakov Maxim
 * 
 */
public interface SelectedItemsListener {
    void selectionChanged(SelectedItems<? extends IViewable> selection);
}
