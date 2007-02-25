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
import javax.swing.JPanel;
import javax.swing.JToolBar;

import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.view.EntityView;

/**
 * @author sma
 * 
 */
public class Main {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
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
        JComponent diagramEditor = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400,400);
            }
        };
        diagramEditor.setLayout(null);
        diagramEditor.add(new EntityView(new Entity("Foo entity "), 20, 20));
        diagramEditor.add(new EntityView(new Entity("Bar entity"),  150, 10));
        EntityView entityView = new EntityView(new Entity("Buzz entity"), 260, 10);
        diagramEditor.add(entityView);
        entityView.setSelected(true);
        diagramEditor.add(new EntityView(new Entity("Long named entity"), 260, 200));
        //JLabel label = new JLabel("Just a label");
       // label.setSize(40, 40);
        //diagramEditor.add(label);
        return diagramEditor;
    }
}
