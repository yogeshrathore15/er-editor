package ru.amse.soultakov.ereditor.controller.undo.commands;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Map;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.IViewable;
import ru.amse.soultakov.ereditor.view.RelationshipView;

public class RemoveViewableCommand implements ICommand {

    private final DiagramEditor diagramEditor;

    private final Set<IViewable> viewables;
    
    private final Map<IViewable, Set<RelationshipView>> viewableToRelationships = newHashMap();
    
    private final Set<RelationshipView> allRelationships = newLinkedHashSet();

    public RemoveViewableCommand(final DiagramEditor diagramEditor,
            final Set<IViewable> viewables) {
        this.diagramEditor = diagramEditor;
        this.viewables = newLinkedHashSet(viewables);
    }

    public void doIt() {
        allRelationships.clear();
        viewableToRelationships.clear();
        for (IViewable v : viewables) {
            if (v instanceof EntityView) {
                EntityView ev = (EntityView) v;
                Set<RelationshipView> relations = newLinkedHashSet();
                for (Relationship r : ev.getEntity().getRelationships()) {
                    RelationshipView relationshipView = diagramEditor.getDiagram().getRelationshipView(r);
                    if (allRelationships.add(relationshipView)) {
                        relations.add(relationshipView);
                    }
                }
                viewableToRelationships.put(ev, relations);
            }
            diagramEditor.removeViewable(v);
        }
    }

    public void undoIt() {
        for (IViewable v : viewables) {
            diagramEditor.addViewable(v);
        }
        for (IViewable v : viewables) {
            Set<RelationshipView> relations = viewableToRelationships.get(v);
            if (relations != null) {
                for(RelationshipView rv : relations) {
                    diagramEditor.addViewable(rv);
                }
            }
        }
    }

}
