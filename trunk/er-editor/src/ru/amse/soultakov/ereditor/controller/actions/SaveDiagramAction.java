/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.controller.ProgressMonitorAdapter;
import ru.amse.soultakov.ereditor.io.save.XmlDiagramSaver;
import ru.amse.soultakov.ereditor.view.DiagramSavingException;

@SuppressWarnings("serial")
public final class SaveDiagramAction extends AbstractAction {
    private DiagramEditorFrame diagramEditorFrame;

    private final JFileChooser fileChooser = new JFileChooser();

    public SaveDiagramAction(String name, DiagramEditorFrame diagramEditorFrame, ImageIcon icon) {
        super(name, icon);
        this.diagramEditorFrame = diagramEditorFrame;
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Diagrams",
                DiagramEditorFrame.ERD);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
    }

    public void actionPerformed(ActionEvent e) {
        if (fileChooser.showSaveDialog(diagramEditorFrame) == JFileChooser.APPROVE_OPTION) {
            final IProgressMonitor monitor = new ProgressMonitorAdapter(null);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        String fileName = fileChooser.getSelectedFile()
                                .getAbsolutePath();
                        if (!fileName.endsWith(DiagramEditorFrame.ERD_EXTENSION)) {
                            fileName += DiagramEditorFrame.ERD_EXTENSION;
                        }
                        XmlDiagramSaver xds = new XmlDiagramSaver(
                                new FileOutputStream(fileName));
                        diagramEditorFrame.getDiagramEditor().getDiagram().save(xds,
                                monitor);
                        diagramEditorFrame.getDiagramEditor().setDiagramChanged(false);
                    } catch (DiagramSavingException ex) {
                        JOptionPane.showMessageDialog(diagramEditorFrame,
                                "Ошибка при сохранении диаграммы");
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(diagramEditorFrame,
                                "Невозможна запись в указанный файл");
                    }
                }
            }).start();
        }
    }
}