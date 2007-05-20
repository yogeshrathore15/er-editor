/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.controller.IDiagramChangesListener;
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
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Diagrams (*.erd)",
                DiagramEditorFrame.ERD);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        this.putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        diagramEditorFrame.getDiagramEditor().addDiagramChangesListener(new IDiagramChangesListener() {
            public void diagramChangedSetTo(boolean newValue) {
                setEnabled(newValue);
            }
        });
        setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
        final IProgressMonitor monitor = new ProgressMonitorAdapter(diagramEditorFrame);
        final String fileName;
        if (diagramEditorFrame.getDiagramEditor().getCurrentFile() != null) {
            fileName = diagramEditorFrame.getDiagramEditor().getCurrentFile().getAbsolutePath(); 
        } else if (fileChooser.showSaveDialog(diagramEditorFrame) == JFileChooser.APPROVE_OPTION) {
            fileName = getFileNameFromFC();
        } else {
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    XmlDiagramSaver xds = new XmlDiagramSaver(
                            new FileOutputStream(fileName));
                    diagramEditorFrame.getDiagramEditor().getDiagram().save(xds,
                            monitor);
                    diagramEditorFrame.getDiagramEditor().setDiagramChanged(
                            false);
                    diagramEditorFrame.getDiagramEditor().setCurrentFile(
                            new File(fileName));
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

    private String getFileNameFromFC() {
        String fileName1 = fileChooser.getSelectedFile().getAbsolutePath();
        if (!fileName1.endsWith(DiagramEditorFrame.ERD_EXTENSION)) {
            fileName1 += DiagramEditorFrame.ERD_EXTENSION;
        }
        return fileName1;
    }
}