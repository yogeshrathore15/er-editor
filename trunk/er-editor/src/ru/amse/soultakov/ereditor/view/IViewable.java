/*
 * Created on 27.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.model.ICopyable;

/**
 * @author Soultakov Maxim
 */
public interface IViewable extends ICopyable<IViewable>{

    void setSelected(boolean selected);

    boolean isSelected();

    void paint(Graphics2D graphics);

    int getX();

    int getY();

    int getWidth();

    int getHeight();

    void setLocation(int x, int y);

    boolean containsPoint(int x, int y);

    boolean isInsideRectangle(int x1, int y1, int x2, int y2);

    <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data);

    void addListener(IViewableListener viewableListener);

    boolean removeListener(IViewableListener viewableListener);
    
    IOutline getOutline();
    
    public void processClick(MouseEvent mouseEvent, DiagramEditor component);
    
    void exitProcessing();
    
}
