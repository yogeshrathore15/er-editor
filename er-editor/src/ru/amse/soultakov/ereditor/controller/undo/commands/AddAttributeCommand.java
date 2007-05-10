package ru.amse.soultakov.ereditor.controller.undo.commands;

import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.view.AttributeView;
import ru.amse.soultakov.ereditor.view.EntityView;

public class AddAttributeCommand implements ICommand {


    private final EntityView entityView;

    private final Attribute attribute;

    private AttributeView attributeView;

    public AddAttributeCommand(EntityView entityView,
            Attribute attribute) {
        this.entityView = entityView;
        this.attribute = attribute;
    }

    public void doIt() {
        attributeView = entityView.addAttribute(attribute);
    }

    public void undoIt() {
        entityView.removeAttribute(attributeView);
    }

}
