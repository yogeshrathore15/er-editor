/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

/**
 * @author sma
 * 
 */
public class Main {
    public static void main(String[] args) {
        // JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Stupid test");
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu.add(new JMenu("File"));
        menu.add(new JMenu("Edit"));
        JToolBar toolBar = new JToolBar();
        toolBar.add(new JLabel("ToolBar"));
        frame.add(toolBar, BorderLayout.NORTH);
        frame.add(createDiagramEditor());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static JComponent createDiagramEditor() {
        DiagramEditor diagramEditor = new DiagramEditor() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 400);
            }
        };
        EntityView entityView1 = new EntityView(diagramEditor, new Entity("Foo entity "),
                20, 20);
        diagramEditor.add(entityView1);
        EntityView entityView2 = new EntityView(diagramEditor, new Entity("Bar entity"),
                150, 10);
        diagramEditor.add(entityView2);
        diagramEditor.add(new EntityView(diagramEditor, new Entity("Buzz entity"), 260,
                10));
        diagramEditor.add(new EntityView(diagramEditor, new Entity("Long named entity"),
                260, 200));
        diagramEditor //O_O
                .add(new RelationshipView(new Relationship("", new RelationshipEnd(
                        entityView1.getEntity(), RelationshipMultiplicity.ONE_ONLY),
                        new RelationshipEnd(entityView2.getEntity(),
                                RelationshipMultiplicity.ZERO_OR_ONE)), entityView1,
                        entityView2));
        return diagramEditor;
    }
}
