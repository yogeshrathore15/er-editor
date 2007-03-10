/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import ru.amse.soultakov.ereditor.controller.actions.AddCommentAction;
import ru.amse.soultakov.ereditor.controller.actions.AddEntityAction;
import ru.amse.soultakov.ereditor.controller.actions.AddRelationshipAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveSelectionAction;
import ru.amse.soultakov.ereditor.controller.actions.SelectElementAction;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.EntityView;

/**
 * 
 * @author Soultakov Maxim
 */
public class Main {

    private static DiagramEditor diagramEditor = new DiagramEditor();;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stupid test");
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu.add(new JMenu("File"));
        menu.add(new JMenu("Edit"));
        frame.add(new JScrollPane(createDiagramEditor()));
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();

        JToggleButton addEntityButton = new JToggleButton(new AddEntityAction(
                diagramEditor, "Add Entity"));

        JToggleButton addRelationshipButton = new JToggleButton(
                new AddRelationshipAction(diagramEditor, "Add relationship"));

        JToggleButton addCommentButton = new JToggleButton(new AddCommentAction(
                diagramEditor, "Add comment"));

        JToggleButton defaultToolButton = new JToggleButton(new SelectElementAction(
                diagramEditor, "Default"));
        defaultToolButton.setSelected(true);

        toolBar.add(defaultToolButton);
        toolBar.add(addEntityButton);
        toolBar.add(addRelationshipButton);
        toolBar.add(addCommentButton);
        toolBar.add(new JButton(new RemoveSelectionAction(diagramEditor, "Remove")));

        buttonsGroup.add(defaultToolButton);
        buttonsGroup.add(addEntityButton);
        buttonsGroup.add(addCommentButton);
        buttonsGroup.add(addRelationshipButton);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JComponent createDiagramEditor() {
        EntityView v1 = diagramEditor.addEntity(20, 20);
        EntityView v2 = diagramEditor.addEntity(150, 10);
        diagramEditor.addEntity(260, 10);
        diagramEditor.addEntity(260, 200);
        diagramEditor.addRelationship(v1, v2);
        CommentView c1 = diagramEditor.addComment(100, 300);
        diagramEditor.addLink(v2, c1);
        return diagramEditor;
    }

}
