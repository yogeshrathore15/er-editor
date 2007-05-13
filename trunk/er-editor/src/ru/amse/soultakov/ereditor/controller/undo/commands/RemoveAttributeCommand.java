/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.undo.commands;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.FKAttribute;
import ru.amse.soultakov.ereditor.model.FKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;
import ru.amse.soultakov.ereditor.view.AttributeView;
import ru.amse.soultakov.ereditor.view.EntityView;

public final class RemoveAttributeCommand implements ICommand {

    private final DiagramEditor diagramEditor;
    
    private final EntityView entityView;

    private Integer selectedIndex;

    private AttributeView attributeView;

    private boolean wasInPk = false;

    private Constraint<AbstractAttribute> unique;

    private Constraint<FKAttribute> foreignKey;

    public RemoveAttributeCommand(DiagramEditor diagramEditor, EntityView entityView) {
        this.diagramEditor = diagramEditor;
        this.entityView = entityView;
    }

    @SuppressWarnings("unchecked")
    public void doIt() {
        if (selectedIndex == null) {
            selectedIndex = entityView.getSelectedAttributeIndex();
            if (selectedIndex >= 0 && selectedIndex < entityView.getAttributes().size()) {
                attributeView = entityView.getAttributes().get(selectedIndex);
            }
        }
        if (attributeView != null) {
            wasInPk = entityView.getEntity().getPrimaryKey().contains(
                    attributeView.getAttribute());
            unique = entityView.getEntity().getUniqueConstraintFor(
                    attributeView.getAttribute());
            foreignKey = entityView.getEntity().getForeignKeyConstraintFor(
                    attributeView.getAttribute());
            entityView.removeAttribute(attributeView);
            for(Relationship r : entityView.getEntity().getRelationships()) {
                RelationshipEnd re = r.getOppositeRelationshipEndFor(entityView.getEntity());
                if (re instanceof FKRelationshipEnd) {
                    FKRelationshipEnd fkre = (FKRelationshipEnd) re;
                    Constraint<FKAttribute> fkConstraint = (Constraint<FKAttribute>) fkre.getConstraint();
                    FKAttribute toRemove = null;
                    for(FKAttribute fka : fkConstraint) {
                        if (fka.getAttribute() == attributeView.getAttribute()) {
                            toRemove = fka;
                            break;
                        }
                    }
                    fkConstraint.remove(toRemove);
                    re.getEntity().removeAttribute(toRemove);
                    diagramEditor.getDiagram().getEntityView(re.getEntity()).reset();
                }
            }
        }
    }

    public void undoIt() {
        if (wasInPk) {
            entityView.getEntity().addToPrimaryKey(attributeView.getAttribute());
        }
        if (unique != null) {
            unique.add(attributeView.getAttribute());
        }
        if (foreignKey != null) {
            foreignKey.add((FKAttribute) attributeView.getAttribute());
        }
        entityView.addAttributeView(selectedIndex, attributeView);
    }
}