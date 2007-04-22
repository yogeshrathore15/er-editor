/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.amse.soultakov.ereditor.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.io.load.XmlDiagramLoader;
import ru.amse.soultakov.ereditor.view.DiagramLoadingException;

public final class DiagramLoadingAction extends AbstractAction {
    private XmlDiagramLoader xdl;

    private DiagramEditorFrame diagramEditorFrame;

    private final Runnable load = new Runnable() {
        @SuppressWarnings("synthetic-access")
        public void run() {
            try {
                diagramEditorFrame.getDiagramEditor().setDiagram(xdl.loadDiagram());
            } catch (DiagramLoadingException e) {
                e.printStackTrace();
            }
        }
    };

    private JFileChooser fc = new JFileChooser();

    public DiagramLoadingAction(String name, DiagramEditorFrame diagramEditorFrame) {
        super(name);
        this.diagramEditorFrame = diagramEditorFrame;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Diagrams",
                DiagramEditorFrame.ERD);
        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    public void actionPerformed(ActionEvent e) {
        if (fc.showOpenDialog(diagramEditorFrame) == JFileChooser.APPROVE_OPTION) {
            try {
                xdl = new XmlDiagramLoader(new FileInputStream(fc
                        .getSelectedFile()));
                new Thread(load).start();
            } catch (FileNotFoundException e1) {
                // не может произойти
            }
        }
    }
}