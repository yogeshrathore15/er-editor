/*
 * Created on 27.02.2007
 */
package ru.amse.soultakov.ereditor.controller;

/**
 * @author Soultakov Maxim
 */
public interface Selectable {

    /**
     * @param selected
     */
    void setSelected(boolean selected);
    
    /**
     * @return
     */
    boolean isSelected();
}
