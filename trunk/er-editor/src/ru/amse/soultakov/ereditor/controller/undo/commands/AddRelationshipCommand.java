package ru.amse.soultakov.ereditor.controller.undo.commands;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

public class AddRelationshipCommand implements ICommand {

    private final DiagramEditor diagramEditor;

    private RelationshipView relationshipView;

    private final EntityView entityView1;

    private final EntityView entityView2;

    public AddRelationshipCommand(final DiagramEditor diagramEditor, EntityView ev1,
            EntityView ev2) {
        this.diagramEditor = diagramEditor;
        entityView1 = ev1;
        entityView2 = ev2;
    }

    public void doIt() {
        if (relationshipView == null) {
            relationshipView = diagramEditor.addRelationship(entityView1,
                    entityView2);
        } else {
            diagramEditor.getDiagram().addRelationshipView(relationshipView);
        }
    }

    public void undoIt() {
        diagramEditor.getDiagram().removeRelationshipView(relationshipView);
    }

}
