package ru.amse.soultakov.ereditor.controller.undo.commands;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.view.EntityView;

public class AddEntityCommand implements ICommand {

    private EntityView entityView;

    private final DiagramEditor diagramEditor;

    private final int x;

    private final int y;

    public AddEntityCommand(final DiagramEditor diagramEditor, int x, int y) {
        this.diagramEditor = diagramEditor;
        this.x = x;
        this.y = y;
    }

    public void doIt() {
        if (entityView == null) {
            entityView = diagramEditor.addEntity(x, y);
        } else {
            diagramEditor.getDiagram().addEntityView(entityView);
        }
    }

    public void undoIt() {
        diagramEditor.getDiagram().removeEntityView(entityView);
    }

}
