package ru.amse.soultakov.ereditor.controller.undo.commands;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Map;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.FKAttribute;
import ru.amse.soultakov.ereditor.model.FKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.IViewable;
import ru.amse.soultakov.ereditor.view.RelationshipView;

public class RemoveViewableCommand implements ICommand {

    private final DiagramEditor diagramEditor;

    private final Set<IViewable> viewables;

    private final Map<IViewable, Set<RelationshipView>> viewableToRelationships = newHashMap();

    private final Set<RelationshipView> allRelationships = newLinkedHashSet();

    private final Map<IViewable, Constraint<? extends AbstractAttribute>> constraints = newHashMap();

    public RemoveViewableCommand(final DiagramEditor diagramEditor,
            final Set<IViewable> viewables) {
        this.diagramEditor = diagramEditor;
        this.viewables = newLinkedHashSet(viewables);
    }

    public void doIt() {
        allRelationships.clear();
        viewableToRelationships.clear();
        constraints.clear();
        for (IViewable v : viewables) {
            if (v instanceof EntityView) {
                removeEntity(v);
            } else if (v instanceof RelationshipView) {
                removeRelationship(v);
            }
        }
        for (IViewable v : viewables) {
            diagramEditor.removeViewable(v);
        }
    }

    private void removeRelationship(IViewable v) {
        RelationshipView rv = (RelationshipView) v;
        if (rv.getRelationship().getFirstEnd() instanceof FKRelationshipEnd) {
            removeFKRelationshipEnd(v, (FKRelationshipEnd) rv.getRelationship()
                    .getFirstEnd());
        } else if (rv.getRelationship().getSecondEnd() instanceof FKRelationshipEnd) {
            removeFKRelationshipEnd(v, (FKRelationshipEnd) rv.getRelationship()
                    .getSecondEnd());
        }
    }

    private void removeFKRelationshipEnd(IViewable v, FKRelationshipEnd end) {
        Entity e = end.getEntity();
        e.removeForeignKey(end.getConstraint());
        constraints.put(v, end.getConstraint());
        for (AbstractAttribute aa : end.getConstraint()) {
            e.removeAttribute(aa);
        }
        diagramEditor.getDiagram().getEntityView(e).reset();
    }

    private void removeEntity(IViewable v) {
        EntityView ev = (EntityView) v;
        Set<RelationshipView> relations = newLinkedHashSet();
        for (Relationship r : ev.getEntity().getRelationships()) {
            RelationshipView relationshipView = diagramEditor.getDiagram()
                    .getRelationshipView(r);
            if (allRelationships.add(relationshipView)) {
                relations.add(relationshipView);
            }
            removeRelationship(relationshipView);
        }
        viewableToRelationships.put(ev, relations);
    }

    public void undoIt() {
        for (IViewable v : viewables) {
            diagramEditor.addViewable(v);
        }
        for (IViewable v : viewables) {
            restoreEntityRelations(v);
            restoreEntityAttributes(v);
        }
    }

    /**
     * @param v
     */
    @SuppressWarnings("unchecked")
    private void restoreEntityAttributes(IViewable v) {
        if (v instanceof RelationshipView) {
            RelationshipView rv = (RelationshipView) v;
            Entity entity;
            if (rv.getRelationship().getFirstEnd() instanceof FKRelationshipEnd) {
                entity = ((FKRelationshipEnd) rv.getRelationship().getFirstEnd())
                        .getEntity();
            } else if (rv.getRelationship().getSecondEnd() instanceof FKRelationshipEnd) {
                entity = ((FKRelationshipEnd) rv.getRelationship().getSecondEnd())
                        .getEntity();
            } else {
                return;
            }
            for (AbstractAttribute aa : constraints.get(v)) {
                entity.addAttribute(aa);
            }
            entity.addForeignKey((Constraint<FKAttribute>) constraints.get(v));
            diagramEditor.getDiagram().getEntityView(entity).reset();
        }
    }

    private void restoreEntityRelations(IViewable v) {
        Set<RelationshipView> relations = viewableToRelationships.get(v);
        if (relations != null) {
            for (RelationshipView rv : relations) {
                diagramEditor.addViewable(rv);
                restoreEntityAttributes(rv);
            }
        }
    }

}
