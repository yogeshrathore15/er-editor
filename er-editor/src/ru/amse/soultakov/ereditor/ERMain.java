package ru.amse.soultakov.ereditor;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.JDOMException;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.ICurrentToolListener;
import ru.amse.soultakov.ereditor.controller.actions.DiagramEditorAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveSelectionAction;
import ru.amse.soultakov.ereditor.controller.tools.AddCommentTool;
import ru.amse.soultakov.ereditor.controller.tools.AddEntityTool;
import ru.amse.soultakov.ereditor.controller.tools.AddLinkTool;
import ru.amse.soultakov.ereditor.controller.tools.AddRelationshipTool;
import ru.amse.soultakov.ereditor.controller.tools.IToolListener;
import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;
import ru.amse.soultakov.ereditor.controller.tools.Tool;
import ru.amse.soultakov.ereditor.io.load.XmlDiagramLoader;
import ru.amse.soultakov.ereditor.io.save.XmlDiagramSaver;
import ru.amse.soultakov.ereditor.view.DiagramSavingException;

/**
 * @author Soultakov Maxim
 * 
 */
public class ERMain {

    static DiagramEditor diagramEditor = new DiagramEditor();

    static Map<Tool, AbstractButton> toolToButton = newHashMap();

    @SuppressWarnings("serial")
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Stupid test");
        setupMenu(frame);

        JScrollPane scrollPane = new JScrollPane(diagramEditor);
        frame.add(scrollPane);
        JToolBar toolBar = new JToolBar();
        toolBar.setRollover(true);
        ButtonGroup buttonsGroup = new ButtonGroup();
        final SelectElementTool selectElementTool = new SelectElementTool(
                diagramEditor);
        final JToggleButton defaultToolButton = new JToggleButton(
                new DiagramEditorAction(diagramEditor, "Default", new ImageIcon(
                        "./images/tool_selecting.png"), selectElementTool));
        defaultToolButton.setPressedIcon(new ImageIcon(
                "./images/tool_selecting_pressed.png"));
        defaultToolButton.setSelectedIcon(new ImageIcon(
                "./images/tool_selecting_pressed.png"));
        defaultToolButton.setText(null);
        defaultToolButton.setMargin(new Insets(0, 0, 0, 0));
        toolToButton.put(selectElementTool, defaultToolButton);
        IToolListener toolListener = new IToolListener() {
            public void operationFinished() {
                diagramEditor.setTool(selectElementTool);
            }
        };

        AddEntityTool addEntityTool = new AddEntityTool(diagramEditor);
        addEntityTool.addListener(toolListener);
        JToggleButton addEntityButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add Entity", addEntityTool));
        toolToButton.put(addEntityTool, addEntityButton);

        AddRelationshipTool addRelationshipTool = new AddRelationshipTool(
                diagramEditor);
        addRelationshipTool.addListener(toolListener);
        JToggleButton addRelationshipButton = new JToggleButton(
                new DiagramEditorAction(diagramEditor, "Add relationship",
                        addRelationshipTool));
        toolToButton.put(addRelationshipTool, addRelationshipButton);

        AddCommentTool addCommentTool = new AddCommentTool(diagramEditor);
        addCommentTool.addListener(toolListener);
        JToggleButton addCommentButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add comment", addCommentTool));
        toolToButton.put(addCommentTool, addCommentButton);

        AddLinkTool addLinkTool = new AddLinkTool(diagramEditor);
        addLinkTool.addListener(toolListener);
        JToggleButton addLinkButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add link", addLinkTool));
        toolToButton.put(addLinkTool, addLinkButton);

        defaultToolButton.setSelected(true);

        diagramEditor.addToolChangeListener(new ICurrentToolListener() {
            public void currentToolChanged(Tool oldTool, Tool newTool) {
                if (toolToButton.containsKey(newTool)) {
                    toolToButton.get(newTool).setSelected(true);
                }
            }
        });

        toolBar.add(defaultToolButton);
        toolBar.add(addEntityButton);
        toolBar.add(addRelationshipButton);
        toolBar.add(addCommentButton);
        toolBar.add(addLinkButton);
        toolBar.add(new JButton(new RemoveSelectionAction(diagramEditor, "Remove")));

        buttonsGroup.add(defaultToolButton);
        buttonsGroup.add(addEntityButton);
        buttonsGroup.add(addCommentButton);
        buttonsGroup.add(addRelationshipButton);
        buttonsGroup.add(addLinkButton);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @SuppressWarnings("serial")
	private static void setupMenu(final JFrame frame) {
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);
        fileMenu.add(new AbstractAction("Save") {

            XmlDiagramSaver xds;

            private final Runnable save = new Runnable() {
                public void run() {
                    try {
                        diagramEditor.getDiagram().save(xds);
                    } catch (DiagramSavingException e1) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка при сохранении диаграммы");
                    }
                }
            };

            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Diagrams", "xml");
                fc.setFileFilter(filter);
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File selectedFileName = fc.getSelectedFile();
						xds = new XmlDiagramSaver(new FileOutputStream(selectedFileName));
                        new Thread(save).start();
                    } catch (FileNotFoundException e1) {
//                         не может произойти
                    }
                }
            }
        });
        fileMenu.add(new AbstractAction("Open") {
            XmlDiagramLoader xdl;

            private final Runnable load = new Runnable() {
                public void run() {
                    try {
                        diagramEditor.setDiagram(xdl.load());
                    } catch (JDOMException e) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка в формате файла");
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(frame,
                                "Ошибка при чтении из файла");
                    }
                }
            };

            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Diagrams", "xml");
                fc.setFileFilter(filter);
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    try {
                        xdl = new XmlDiagramLoader(new FileInputStream(fc
                                .getSelectedFile()));
                        new Thread(load).start();
                    } catch (FileNotFoundException e1) {
                        // не может произойти
                    }
                }
            }
        });
        menu.add(new JMenu("Edit"));
    }

}
