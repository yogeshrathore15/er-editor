/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.MouseInputAdapter;

import ru.amse.soultakov.ereditor.controller.tools.ElementSelectingTool;
import ru.amse.soultakov.ereditor.controller.tools.EntityAddingTool;
import ru.amse.soultakov.ereditor.view.Block;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.EntityView;

/**
 * 
 * @author Soultakov Maxim
 */
public class Main {

    private static final class CommentAdder extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            diagramEditor.addComment(e.getX(), e.getY());
        }
    }

    private static final class RelationshipAdder extends MouseInputAdapter {

        private Block firstEntity = null;

        @Override
        public void mousePressed(MouseEvent e) {
            if (firstEntity == null) {

            }
            throw new UnsupportedOperationException("");
        }

    }

    private static DiagramEditor diagramEditor = new DiagramEditor(); ;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stupid test");
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu.add(new JMenu("File"));
        menu.add(new JMenu("Edit"));
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();
        JToggleButton addEntityButton = new JToggleButton(getAddEntityAction());
        JToggleButton addRelationshipButton = new JToggleButton(
                getAddRelationshipAction());
        JToggleButton addCommentButton = new JToggleButton(getAddCommentAction());
        JToggleButton defaultToolButton = new JToggleButton(new AbstractAction("Default") {
                    private ElementSelectingTool elementSelectingTool = new ElementSelectingTool(diagramEditor);
                    
                    public void actionPerformed(ActionEvent e) {
                        diagramEditor.setTool(elementSelectingTool);
                    }
                });
        defaultToolButton.setSelected(true);
        toolBar.add(defaultToolButton);
        toolBar.add(addEntityButton);
        toolBar.add(addRelationshipButton);
        toolBar.add(addCommentButton);
        toolBar.add(new JButton(getRemoveEntityAction()));

        buttonsGroup.add(defaultToolButton);
        buttonsGroup.add(addEntityButton);
        buttonsGroup.add(addCommentButton);
        buttonsGroup.add(addRelationshipButton);

        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(createDiagramEditor());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static Action getAddCommentAction() {
        return new AbstractAction("Add comment") {

            public void actionPerformed(ActionEvent e) {
                if (!((JToggleButton) e.getSource()).isSelected()) {
                    diagramEditor.setTool(diagramEditor.getDefaultTool());
                } else {
                    //diagramEditor.setTool(new CommentAdder());
                }
            }

        };
    }

    private static Action getAddRelationshipAction() {
        return new AbstractAction("Add relationship") {
            public void actionPerformed(ActionEvent e) {
                if (!((JToggleButton) e.getSource()).isSelected()) {
                    diagramEditor.setTool(diagramEditor.getDefaultTool());
                } else {
                    //diagramEditor.setMouseInputAdapter(new RelationshipAdder());
                }
            }
        };
    }

    private static JComponent createDiagramEditor() {
        EntityView v1 = diagramEditor.addEntity(20, 20);
        EntityView v2 = diagramEditor.addEntity(150, 10);
        diagramEditor.addEntity(260, 10);
        diagramEditor.addEntity(260, 200);
        diagramEditor.addRelationship("", v1, v2);
        CommentView c1 = diagramEditor.addComment(100, 300);
        diagramEditor.addLink(v2, c1);
        return diagramEditor;
    }

    private static Action getAddEntityAction() {
        return new AbstractAction("Add entity") {
            private EntityAddingTool tool = new EntityAddingTool(diagramEditor);

            public void actionPerformed(ActionEvent e) {
                if (!((JToggleButton) e.getSource()).isSelected()) {
                    diagramEditor.setTool(diagramEditor.getDefaultTool());
                } else {
                    diagramEditor.setTool(tool);
                }
            }

        };
    }

    private static Action getRemoveEntityAction() {
        return new AbstractAction("Remove") {

            public void actionPerformed(ActionEvent e) {
                diagramEditor.removeSelection();
            }

        };
    }

}
