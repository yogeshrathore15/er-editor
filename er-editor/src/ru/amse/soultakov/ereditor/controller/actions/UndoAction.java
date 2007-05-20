package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.CommandManagerListener;

@SuppressWarnings("serial")
public class UndoAction extends AbstractAction {

    private final DiagramEditor diagramEditor;

    public UndoAction(DiagramEditor editor, String name, Icon icon) {
        super(name, icon);
        this.diagramEditor = editor;
        setEnabled(false);
        this.diagramEditor.getCommandManager().addListener(
                new CommandManagerListener() {
                    public void commandInvoked() {
                        UndoAction.this.setEnabled(diagramEditor.getCommandManager()
                                .canUndoCommand());
                    }

                    public void commandRedone() {
                        UndoAction.this.setEnabled(diagramEditor.getCommandManager()
                                .canUndoCommand());
                    }

                    public void commandUndone() {
                        UndoAction.this.setEnabled(diagramEditor.getCommandManager()
                                .canUndoCommand());
                    }
                });
        putValue(MNEMONIC_KEY, KeyEvent.VK_U);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
    }

    public void actionPerformed(ActionEvent e) {
        if (!diagramEditor.getSelectedItems().isEmpty()) {
            diagramEditor.getSelectedItems().getFirst().stopProcessing(diagramEditor);
        }
        diagramEditor.getCommandManager().undoCommand();
    }

}
