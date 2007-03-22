/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;

public class AddEntityTool extends ToolAdapter {

    private DiagramEditor diagramEditor;

    public AddEntityTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        diagramEditor.addEntity(e.getX(), e.getY());
        diagramEditor.revalidate();
        Dimension ps = diagramEditor.getPreferredSize();
        diagramEditor.scrollRectToVisible(new Rectangle(ps.width ,ps.height,0,0));
        operationFinished();
    }

}
