/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.controller.tools;

import java.awt.event.MouseEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;

public class AddCommentTool extends ToolAdapter {

    private DiagramEditor diagramEditor;

    public AddCommentTool(DiagramEditor diagramEditor) {
        this.diagramEditor = diagramEditor;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        diagramEditor.addComment(e.getX(), e.getY());
    }
}
