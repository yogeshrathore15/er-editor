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

    private final List<ToolListener> toolListeners = new ArrayList<ToolListener>();
    
    public void addListener(ToolListener listener) {
        toolListeners.add(listener);
    }
    
    public boolean removeListener(ToolListener listener) {
        return toolListeners.remove(listener);
    }
    
    protected void notifyListeners() {
        for(ToolListener tl : toolListeners) {
            tl.operationFinished();
        }
    }
    
    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
    
    public void paintAfter(Graphics2D graphics) {
    }
    
    public void paintBefore(Graphics2D graphics) {
    }

}
