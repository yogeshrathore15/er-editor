/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.view.AttributeView;
import ru.amse.soultakov.ereditor.view.EntityView;

final class RemoveAttributeCommand implements ICommand {
    
    private EntityView entityView;
    
    private Integer selectedIndex;
    private AttributeView attributeView;

    public RemoveAttributeCommand(EntityView entityView) {
        this.entityView = entityView;
    }

    public void doIt() {
        if (selectedIndex == null) {
            selectedIndex = entityView.getSelectedAttributeIndex();
            attributeView = entityView.getAttributes().get(selectedIndex);
        }
        entityView.removeAttribute(attributeView);
    }

    public void undoIt() {
        entityView.addAttributeView(selectedIndex, attributeView);
    }
}