package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.controller.undo.commands.AddAttributeCommand;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.model.SimpleAttributeType;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.IViewable;
import ru.amse.soultakov.ereditor.view.SelectedItems;
import ru.amse.soultakov.ereditor.view.SelectedItemsListener;

@SuppressWarnings("serial")
public class AddAttributeAction extends AbstractAction {

    private final DiagramEditorFrame diagramEditorFrame;

    private EntityView entityView;

    public AddAttributeAction(final DiagramEditorFrame diagramEditorFrame,
            String name, Icon icon) {
        super(name, icon);
        this.diagramEditorFrame = diagramEditorFrame;
        setEnabled(false);
        diagramEditorFrame.getDiagramEditor().getSelectedItems().addListener(
                new SelectedItemsListener() {
                    public void selectionChanged(
                            SelectedItems<? extends IViewable> selection) {
                        setEnabled(selection.size() == 1
                                && selection.getFirst().acceptVisitor(
                                        new EntityChecker(), null));
                        if (isEnabled()) {
                            entityView = (EntityView) selection.getFirst();
                        }
                    }
                });
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift alt A"));
    }

    private boolean hasAttributeName(String full) {
        for (AbstractAttribute aa : entityView.getEntity()) {
            if (aa.getName().equals(full)) {
                return true;
            }
        }
        return false;
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            String base = "NewAttr";
            String full = base;
            int i = 0;
            while (hasAttributeName(full)) {
                full = base + i++;
            }
            Attribute attribute = new Attribute(full, SimpleAttributeType.INTEGER,
                    false, "");
            diagramEditorFrame.getDiagramEditor().getSelectedItems().getFirst()
                    .stopProcessing(diagramEditorFrame.getDiagramEditor());
            diagramEditorFrame.getDiagramEditor().setTool(diagramEditorFrame.getSelectingTool());
            diagramEditorFrame.getDiagramEditor().getCommandManager()
                    .executeCommand(new AddAttributeCommand(entityView, attribute));
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    entityView.editAttribute(entityView.getEntity().getAttributes()
                            .size() - 1, diagramEditorFrame.getDiagramEditor());
                }
            });
        }
    }

}
