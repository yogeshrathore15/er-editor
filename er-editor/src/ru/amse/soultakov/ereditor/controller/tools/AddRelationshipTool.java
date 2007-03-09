/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.EntityView;

public class AddRelationshipTool extends ToolAdapter {

    private DiagramEditor diagramEditor;

    private EntityView first;

    private EntityView second;
    
    private Point current;

    public AddRelationshipTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (first == null) {
            first = diagramEditor.getDiagram().getEntityView(e.getX(),
                    e.getY());
            if (first != null) {
                diagramEditor.getDiagram().getSelectedItems().setSelection(first);
            } 
            second = null;
        } else {
            second = diagramEditor.getDiagram().getEntityView(e.getX(),
                    e.getY());
            second = second == first ? null : second;
            if (second != null) {
                diagramEditor.addRelationship("", first, second);
                diagramEditor.getDiagram().getSelectedItems().remove(first);
                diagramEditor.getDiagram().getSelectedItems().remove(second);
                first = null;
                second = null;
            }
        }
        current = e.getPoint();
        diagramEditor.repaint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    	second = diagramEditor.getDiagram().getEntityView(e.getX(),
                e.getY());
    	if (second != null) {
    		diagramEditor.addRelationship("", first, second);
            diagramEditor.getDiagram().getSelectedItems().remove(first);
            diagramEditor.getDiagram().getSelectedItems().remove(second);
            first = null;
            second = null;
    	} else {
    		first = null;
    	}
    	diagramEditor.repaint();
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	current = e.getPoint();
    	diagramEditor.repaint();
    }
    
    /* (non-Javadoc)
     * @see ru.amse.soultakov.ereditor.controller.tools.ToolAdapter#paintBefore(java.awt.Graphics2D)
     */
    @Override
    public void paintBefore(Graphics2D graphics) {
    	if (first != null && second == null) {
    		System.out.println("AddRelationshipTool.paintBefore()");
    		graphics.setColor(Color.BLACK);
    		graphics.drawLine(first.getX() + first.getWidth()/2, first.getY() + first.getHeight()/3, 
    						  current.x, current.y);    		
    	}
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            first = null;
            second = null;
            diagramEditor.getDiagram().getSelectedItems().clear();
            diagramEditor.repaint();
        }
    }
    
}
