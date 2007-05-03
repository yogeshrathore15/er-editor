package ru.amse.soultakov.ereditor.controller.undo.commands;

import java.util.LinkedHashSet;
import java.util.Set;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.view.IViewable;

public class RemoveViewableCommand implements ICommand {

    private final DiagramEditor diagramEditor;

    private final Set<IViewable> viewables;

    public RemoveViewableCommand(final DiagramEditor diagramEditor,
            final Set<IViewable> viewables) {
        this.diagramEditor = diagramEditor;
        this.viewables = new LinkedHashSet<IViewable>(viewables);
    }

    public void doIt() {
        for (IViewable v : viewables) {
            diagramEditor.removeViewable(v);
        }
    }

    public void undoIt() {
        for (IViewable v : viewables) {
            diagramEditor.addViewable(v);
        }
    }

}
