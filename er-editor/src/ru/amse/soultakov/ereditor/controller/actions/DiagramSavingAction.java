/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.amse.soultakov.ereditor.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.io.save.XmlDiagramSaver;
import ru.amse.soultakov.ereditor.view.DiagramSavingException;

public final class DiagramSavingAction extends AbstractAction {
    private XmlDiagramSaver xds;

    private DiagramEditorFrame diagramEditorFrame;

    private final Runnable save = new Runnable() {
        @SuppressWarnings("synthetic-access")
        public void run() {
            try {
                diagramEditorFrame.getDiagramEditor().getDiagram().save(xds);
            } catch (DiagramSavingException e1) {
                JOptionPane.showMessageDialog(diagramEditorFrame,
                        "Ошибка при сохранении диаграммы");
            }
        }
    };

    private final JFileChooser fileChooser = new JFileChooser();

    public DiagramSavingAction(String name, DiagramEditorFrame diagramEditorFrame) {
        super(name);
        this.diagramEditorFrame = diagramEditorFrame;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Diagrams",
                DiagramEditorFrame.ERD);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public void actionPerformed(ActionEvent e) {
        if (fileChooser.showSaveDialog(diagramEditorFrame) == JFileChooser.APPROVE_OPTION) {
            try {
                String selectedFileName = fileChooser.getSelectedFile()
                        .getAbsolutePath();
                if (!selectedFileName.endsWith(DiagramEditorFrame.ERD_EXTENSION)) {
                    selectedFileName += DiagramEditorFrame.ERD_EXTENSION;
                }
                xds = new XmlDiagramSaver(new FileOutputStream(selectedFileName));
                new Thread(save).start();
            } catch (FileNotFoundException e1) {
                // не может произойти
            }
        }
    }
}