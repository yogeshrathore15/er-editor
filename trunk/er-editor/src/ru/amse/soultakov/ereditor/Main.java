
package ru.amse.soultakov.ereditor;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

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

/**
 * @author Soultakov Maxim
 *
 */
public class Main {

    private static DiagramEditor diagramEditor = new DiagramEditor();

    private static Map<Tool, AbstractButton> toolToButton = newHashMap();
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stupid test");
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        menu.add(new JMenu("File"));
        menu.add(new JMenu("Edit"));
        
        JScrollPane scrollPane = new JScrollPane(diagramEditor);        
		frame.add(scrollPane);        
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();

        final SelectElementTool selectElementTool = new SelectElementTool(diagramEditor);
        final JToggleButton defaultToolButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Default", selectElementTool));
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

        AddRelationshipTool addRelationshipTool = new AddRelationshipTool(diagramEditor);
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

}
