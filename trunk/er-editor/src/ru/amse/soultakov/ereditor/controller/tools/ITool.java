/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface ITool {

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a component.
     */
    public void mouseClicked(MouseEvent e);

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e);

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e);

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e);

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e);

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     * <code>MOUSE_DRAGGED</code> events will continue to be delivered to the
     * component where the drag originated until the mouse button is released
     * (regardless of whether the mouse position is within the bounds of the
     * component).
     */
    public void mouseDragged(MouseEvent e);

    /**
     * Invoked when the mouse cursor has been moved onto a component but no
     * buttons have been pushed.
     */
    public void mouseMoved(MouseEvent e);

    /**
     * Invoked when a key has been typed. See the class description for
     * {@link KeyEvent} for a definition of a key typed event.
     */
    public void keyTyped(KeyEvent e);

    /**
     * Invoked when a key has been pressed. See the class description for
     * {@link KeyEvent} for a definition of a key pressed event.
     */
    public void keyPressed(KeyEvent e);

    /**
     * Invoked when a key has been released. See the class description for
     * {@link KeyEvent} for a definition of a key released event.
     */
    public void keyReleased(KeyEvent e);

    public void paintBefore(Graphics2D graphics);

    public void paintAfter(Graphics2D graphics);

    public void addListener(IToolListener listener);

    public boolean removeListener(IToolListener listener);

}
