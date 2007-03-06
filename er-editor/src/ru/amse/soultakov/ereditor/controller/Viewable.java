/*
 * Created on 27.02.2007
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Graphics2D;

/**
 * @author Soultakov Maxim
 */
public interface Viewable {

    /**
     * @param selected
     */
    void setSelected(boolean selected);
    
    /**
     * @return
     */
    boolean isSelected();
        
    void paint(Graphics2D graphics);
}
