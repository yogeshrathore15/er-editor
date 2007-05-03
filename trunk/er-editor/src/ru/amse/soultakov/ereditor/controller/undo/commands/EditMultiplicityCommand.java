package ru.amse.soultakov.ereditor.controller.undo.commands;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

public class EditMultiplicityCommand implements ICommand {

    private final DiagramEditor diagramEditor;

    private final RelationshipEnd relationshipEnd;

    private final RelationshipMultiplicity oldMultiplicity;

    private final RelationshipMultiplicity newMultiplicity;

    public EditMultiplicityCommand(final DiagramEditor diagramEditor,
            final RelationshipEnd relationshipEnd,
            final RelationshipMultiplicity newMultiplicity) {
        this.diagramEditor = diagramEditor;
        this.relationshipEnd = relationshipEnd;
        this.oldMultiplicity = this.relationshipEnd.getMultiplicity();
        this.newMultiplicity = newMultiplicity;
    }

    public void doIt() {
        relationshipEnd.setMultiplicity(newMultiplicity);
        diagramEditor.repaint();
    }

    public void undoIt() {
        relationshipEnd.setMultiplicity(oldMultiplicity);
        diagramEditor.repaint();
    }

}
