/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract <code>Tool</code> class. All its methods are empty.
 * 
 * @author Soultakov Maxim
 */
public abstract class ToolAdapter implements Tool {

    private final List<IToolListener> iToolListeners = new ArrayList<IToolListener>();

    public void addListener(IToolListener listener) {
        iToolListeners.add(listener);
    }

    public boolean removeListener(IToolListener listener) {
        return iToolListeners.remove(listener);
    }

    protected void operationFinished() {
        for (IToolListener tl : iToolListeners) {
            tl.operationFinished();
        }
    }

    public void keyPressed(KeyEvent e) {
    	//empty
    }

    public void keyReleased(KeyEvent e) {
    	//empty
    }

    public void keyTyped(KeyEvent e) {
    	//empty
    }

    public void mouseClicked(MouseEvent e) {
    	//empty
    }

    public void mouseDragged(MouseEvent e) {
    	//empty
    }

    public void mouseEntered(MouseEvent e) {
    	//empty
    }

    public void mouseExited(MouseEvent e) {
    	//empty
    }

    public void mouseMoved(MouseEvent e) {
    	//empty
    }

    public void mousePressed(MouseEvent e) {
    	//empty
    }

    public void mouseReleased(MouseEvent e) {
    	//empty
    }

    public void paintAfter(Graphics2D graphics) {
    	//empty
    }

    public void paintBefore(Graphics2D graphics) {
    	//empty
    }

}
