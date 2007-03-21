/**
 * 
 */
package ru.amse.soultakov.ereditor;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.actions.DiagramEditorAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveSelectionAction;
import ru.amse.soultakov.ereditor.controller.tools.AddCommentTool;
import ru.amse.soultakov.ereditor.controller.tools.AddEntityTool;
import ru.amse.soultakov.ereditor.controller.tools.AddLinkTool;
import ru.amse.soultakov.ereditor.controller.tools.AddRelationshipTool;
import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;
import ru.amse.soultakov.ereditor.controller.tools.ToolListener;

/**
 * 
 * @author Soultakov Maxim
 */
public class Main {

    private static DiagramEditor diagramEditor;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stupid test");
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu.add(new JMenu("File"));
        menu.add(new JMenu("Edit"));
        
        JScrollPane scrollPane = new JScrollPane();
        diagramEditor = new DiagramEditor(scrollPane);
        scrollPane.setViewportView(diagramEditor);
        
		frame.add(scrollPane);
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();

        SelectElementTool selectElementTool = new SelectElementTool(diagramEditor);
        final JToggleButton defaultToolButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Default", selectElementTool));
        ToolListener toolListener = new ToolListener() {
            public void operationFinished() {
                defaultToolButton.doClick();
            }
        };

        AddEntityTool addEntityTool = new AddEntityTool(diagramEditor);
        addEntityTool.addListener(toolListener);
        JToggleButton addEntityButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add Entity", addEntityTool));

        AddRelationshipTool addRelationshipTool = new AddRelationshipTool(diagramEditor);
        addRelationshipTool.addListener(toolListener);
        JToggleButton addRelationshipButton = new JToggleButton(
                new DiagramEditorAction(diagramEditor, "Add relationship",
                        addRelationshipTool));

        AddCommentTool addCommentTool = new AddCommentTool(diagramEditor);
        addCommentTool.addListener(toolListener);
        JToggleButton addCommentButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add comment", addCommentTool));

        AddLinkTool addLinkTool = new AddLinkTool(diagramEditor);
        addLinkTool.addListener(toolListener);
        JToggleButton addLinkButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add link", addLinkTool));
        defaultToolButton.setSelected(true);

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

}
