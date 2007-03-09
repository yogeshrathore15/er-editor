/*
 * Created on 27.02.2007
 */
package ru.amse.soultakov.ereditor.view;

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
    
    int getX();
    
    int getY();
    
    void setLocation(int x, int y);

    public boolean containsPoint(int x, int y);
}
