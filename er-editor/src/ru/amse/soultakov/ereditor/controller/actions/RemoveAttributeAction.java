package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.IViewable;
import ru.amse.soultakov.ereditor.view.SelectedItems;
import ru.amse.soultakov.ereditor.view.SelectedItemsListener;

@SuppressWarnings("serial")
public class RemoveAttributeAction extends AbstractAction {

    private final DiagramEditor diagramEditor;

    private EntityView entityView;

    public RemoveAttributeAction(final DiagramEditor diagramEditor, String name,
            Icon icon) {
        super(name, icon);
        this.diagramEditor = diagramEditor;
        setEnabled(false);
        this.diagramEditor.getSelectedItems().addListener(
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
        putValue(MNEMONIC_KEY, KeyEvent.VK_M);
        putValue(ACCELERATOR_KEY,KeyStroke.getKeyStroke("shift alt M"));
    }

    public void actionPerformed(ActionEvent e) {
        if (isEnabled()) {
            entityView.removeSelectedAttribute();
        }
    }

}
