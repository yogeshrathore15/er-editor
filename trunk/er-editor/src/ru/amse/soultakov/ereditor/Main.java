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
import ru.amse.soultakov.ereditor.controller.actions.AddCommentAction;
import ru.amse.soultakov.ereditor.controller.actions.AddEntityAction;
import ru.amse.soultakov.ereditor.controller.actions.AddLinkAction;
import ru.amse.soultakov.ereditor.controller.actions.AddRelationshipAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveSelectionAction;
import ru.amse.soultakov.ereditor.controller.actions.SelectElementAction;

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
        frame.add(new JScrollPane(diagramEditor));
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
        JToggleButton addLinkButton = new JToggleButton(new AddLinkAction(diagramEditor, "Add link"));
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