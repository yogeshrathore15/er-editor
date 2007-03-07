/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import static ru.amse.soultakov.ereditor.model.RelationshipMultiplicity.ONE_ONLY;
import static ru.amse.soultakov.ereditor.model.RelationshipMultiplicity.ZERO_OR_ONE;

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

import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Link;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;
import ru.amse.soultakov.ereditor.util.AutoincrementGenerator;
import ru.amse.soultakov.ereditor.view.Block;

/**
 * 
 * @author Soultakov Maxim
 */
public class Main {

    private static final class EntityAdder extends MouseInputAdapter {

        private AutoincrementGenerator generator = new AutoincrementGenerator();

        @Override
        public void mousePressed(MouseEvent e) {
            diagramEditor.addEntity(new Entity("New Entity "
                    + generator.getNextInteger()), e.getX(), e.getY());
        }
    }

    private static final class CommentAdder extends MouseInputAdapter {

        private AutoincrementGenerator generator = new AutoincrementGenerator();

        @Override
        public void mousePressed(MouseEvent e) {
            diagramEditor.addComment(new Comment("New Comment "
                    + generator.getNextInteger()), e.getX(), e.getY());
        }
    }

    private static final class RelationshipAdder extends MouseInputAdapter {

        private Block firstEntity = null;

        @Override
        public void mousePressed(MouseEvent e) {
            if (firstEntity == null) {

            }
            diagramEditor.addRelationship(new Relationship("name",
                    new RelationshipEnd(null, ONE_ONLY), new RelationshipEnd(null,
                            ZERO_OR_ONE)));
        }

    }

    private static DiagramEditor diagramEditor;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stupid test");
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu.add(new JMenu("File"));
        menu.add(new JMenu("Edit"));
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();
        JToggleButton addEntityButton = new JToggleButton(getAddEntityAction());
        JToggleButton addRelationshipButton = new JToggleButton(getAddRelationshipAction());
        JToggleButton addCommentButton = new JToggleButton(getAddCommentAction());
        toolBar.add(addEntityButton);
        toolBar.add(addRelationshipButton);
        toolBar.add(addCommentButton);
        toolBar.add(new JButton(getRemoveEntityAction()));
        
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
                    diagramEditor.setMouseInputAdapter(null);
                } else {
                    diagramEditor.setMouseInputAdapter(new CommentAdder());
                }
            }

        };
    }

    private static Action getAddRelationshipAction() {
        return new AbstractAction("Add relationship") {
            public void actionPerformed(ActionEvent e) {
                if (!((JToggleButton) e.getSource()).isSelected()) {
                    diagramEditor.setMouseInputAdapter(null);
                } else {
                    diagramEditor.setMouseInputAdapter(new RelationshipAdder());
                }
            }
        };
    }

    private static JComponent createDiagramEditor() {
        diagramEditor = new DiagramEditor();
        Entity fooEntity = new Entity("Foo entity");
        diagramEditor.addEntity(fooEntity, 20, 20);
        Entity barEntity = new Entity("Bar entity");
        diagramEditor.addEntity(barEntity, 150, 10);
        diagramEditor.addEntity(new Entity("Buzz entity"), 260, 10);
        diagramEditor.addEntity(new Entity("Long named entity"), 260, 200);
        diagramEditor.addRelationship(new Relationship("name", new RelationshipEnd(
                fooEntity, ONE_ONLY), new RelationshipEnd(barEntity, ZERO_OR_ONE)));
        Comment comment = new Comment("Stupid comment");
        diagramEditor.addComment(comment, 100, 300);
        diagramEditor.addLink(new Link(barEntity, comment));
        return diagramEditor;
    }

    private static Action getAddEntityAction() {
        return new AbstractAction("Add entity") {
            private EntityAdder entityAdder = new EntityAdder();
            public void actionPerformed(ActionEvent e) {
                if (!((JToggleButton) e.getSource()).isSelected()) {
                    diagramEditor.setMouseInputAdapter(null);
                } else {
                    diagramEditor.setMouseInputAdapter(entityAdder);
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
