package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.CommandManagerListener;

@SuppressWarnings("serial")
public class RedoAction extends AbstractAction {

    private final DiagramEditor diagramEditor;

    public RedoAction(DiagramEditor editor, String name, Icon icon) {
        super(name, icon);
        this.diagramEditor = editor;
        setEnabled(false);
        this.diagramEditor.getCommandManager().addListener(
                new CommandManagerListener() {
                    public void commandInvoked() {
                        RedoAction.this.setEnabled(diagramEditor.getCommandManager()
                                .canRedoCommand());
                    }

                    public void commandRedone() {
                        RedoAction.this.setEnabled(diagramEditor.getCommandManager()
                                .canRedoCommand());
                    }

                    public void commandUndone() {
                        RedoAction.this.setEnabled(diagramEditor.getCommandManager()
                                .canRedoCommand());
                    }
                });
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
    }

    public void actionPerformed(ActionEvent e) {
        if (!diagramEditor.getSelectedItems().isEmpty()) {
            diagramEditor.getSelectedItems().getFirst().stopProcessing(diagramEditor);
        }
        diagramEditor.getCommandManager().redoCommand();
    }

}
