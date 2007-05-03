package ru.amse.soultakov.ereditor.controller.undo.commands;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.model.IAttributeType;
import ru.amse.soultakov.ereditor.view.AttributeView;

public class EditAttributeCommand implements ICommand {

    private final DiagramEditor diagramEditor;

    private final AttributeView attributeView;

    private final String oldName;

    private final String newName;

    private final IAttributeType newType;

    private final IAttributeType oldType;

    public EditAttributeCommand(DiagramEditor diagramEditor,
            AttributeView attributeView, String newName, IAttributeType newType) {
        this.diagramEditor = diagramEditor;
        this.attributeView = attributeView;
        this.oldName = attributeView.getAttribute().getName();
        this.oldType = attributeView.getAttribute().getType();
        this.newName = newName;
        this.newType = newType;
    }

    public void doIt() {
        attributeView.getAttribute().setType(newType);
        attributeView.getAttribute().setName(newName);
        diagramEditor.repaint();
    }

    public void undoIt() {
        attributeView.getAttribute().setType(oldType);
        attributeView.getAttribute().setName(oldName);
        diagramEditor.repaint();
    }

}
