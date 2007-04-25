/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;
import ru.amse.soultakov.ereditor.controller.IProgressMonitor;
import ru.amse.soultakov.ereditor.controller.ProgressMonitorAdapter;
import ru.amse.soultakov.ereditor.io.load.XmlDiagramLoader;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.DiagramLoadingException;

@SuppressWarnings("serial")
public final class DiagramLoadingAction extends AbstractAction {
    private DiagramEditorFrame diagramEditorFrame;

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
            final IProgressMonitor monitor = new ProgressMonitorAdapter(
                    diagramEditorFrame);
            new Thread(new Runnable() {
                @SuppressWarnings("synthetic-access")
                public void run() {
                    try {
                        XmlDiagramLoader xdl = new XmlDiagramLoader(
                                new FileInputStream(fc.getSelectedFile()));
                        diagramEditorFrame.getDiagramEditor().setDiagram(
                                Diagram.load(xdl, monitor));
                    } catch (DiagramLoadingException ex) {
                        JOptionPane.showMessageDialog(diagramEditorFrame,
                                "������ ��� �������� ���������");
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(diagramEditorFrame,
                            "���������� ������ �� ���������� �����");
                    }
                }
            }).start();
        }
    }
}