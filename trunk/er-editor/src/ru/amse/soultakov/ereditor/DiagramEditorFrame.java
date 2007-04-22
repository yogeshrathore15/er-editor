package ru.amse.soultakov.ereditor;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.ICurrentToolListener;
import ru.amse.soultakov.ereditor.controller.actions.DiagramEditorAction;
import ru.amse.soultakov.ereditor.controller.actions.DiagramLoadingAction;
import ru.amse.soultakov.ereditor.controller.actions.DiagramSavingAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveSelectionAction;
import ru.amse.soultakov.ereditor.controller.tools.AddCommentTool;
import ru.amse.soultakov.ereditor.controller.tools.AddEntityTool;
import ru.amse.soultakov.ereditor.controller.tools.AddLinkTool;
import ru.amse.soultakov.ereditor.controller.tools.AddRelationshipTool;
import ru.amse.soultakov.ereditor.controller.tools.IToolListener;
import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;
import ru.amse.soultakov.ereditor.controller.tools.Tool;

public class DiagramEditorFrame extends JFrame {

    public static final String ERD = "erd";

    public static final String ERD_EXTENSION = "." + ERD;

    private static final ImageIcon TOOL_SELECTING_PRESSED_ICON = new ImageIcon(
            "./images/tool_selecting_pressed.png");

    private static final ImageIcon TOOL_SELECTING_ICON = new ImageIcon(
            "./images/tool_selecting.png");

    private final DiagramEditor diagramEditor = new DiagramEditor();

    private final Map<Tool, AbstractButton> toolToButton = newHashMap();

    final SelectElementTool selectElementTool = new SelectElementTool(diagramEditor);

    private IToolListener toolListener = new IToolListener() {
        @SuppressWarnings("synthetic-access")
        public void operationFinished() {
            diagramEditor.setTool(selectElementTool);
        }
    };

    public DiagramEditorFrame() {
        super("ER-diagram editor");
        this.setJMenuBar(createMenu());
        this.add(createToolbar(), BorderLayout.NORTH);
    }

    private Component createToolbar() {
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();
        toolBar.setRollover(true);
        toolBar.add(createDefaultButton(buttonsGroup));
        ;
        toolBar.add(createAddEntityButton(buttonsGroup));
        toolBar.add(createAddRelationshipButton(buttonsGroup));
        toolBar.add(createAddCommentButton(buttonsGroup));
        toolBar.add(createAddLinkButton(buttonsGroup));
        toolBar.add(new JButton(new RemoveSelectionAction(diagramEditor, "Remove")));
        return toolBar;
    }

    private JToggleButton createAddLinkButton(ButtonGroup buttonsGroup) {
        AddLinkTool addLinkTool = new AddLinkTool(diagramEditor);
        addLinkTool.addListener(toolListener);
        JToggleButton addLinkButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add link", addLinkTool));
        toolToButton.put(addLinkTool, addLinkButton);
        buttonsGroup.add(addLinkButton);
        return addLinkButton;
    }

    private JToggleButton createAddCommentButton(ButtonGroup buttonsGroup) {
        AddCommentTool addCommentTool = new AddCommentTool(diagramEditor);
        addCommentTool.addListener(toolListener);
        JToggleButton addCommentButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add comment", addCommentTool));
        toolToButton.put(addCommentTool, addCommentButton);
        buttonsGroup.add(addCommentButton);
        return addCommentButton;
    }

    private JToggleButton createAddRelationshipButton(ButtonGroup buttonsGroup) {
        AddRelationshipTool addRelationshipTool = new AddRelationshipTool(
                diagramEditor);
        addRelationshipTool.addListener(toolListener);
        JToggleButton addRelationshipButton = new JToggleButton(
                new DiagramEditorAction(diagramEditor, "Add relationship",
                        addRelationshipTool));
        toolToButton.put(addRelationshipTool, addRelationshipButton);
        buttonsGroup.add(addRelationshipButton);
        return addRelationshipButton;
    }

    private JToggleButton createAddEntityButton(ButtonGroup buttonsGroup) {
        AddEntityTool addEntityTool = new AddEntityTool(diagramEditor);
        addEntityTool.addListener(toolListener);
        JToggleButton addEntityButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add Entity", addEntityTool));
        toolToButton.put(addEntityTool, addEntityButton);
        buttonsGroup.add(addEntityButton);
        return addEntityButton;
    }

    private JToggleButton createDefaultButton(ButtonGroup buttonsGroup) {
        final JToggleButton defaultToolButton = new JToggleButton(
                new DiagramEditorAction(diagramEditor, "Selecting elements",
                        TOOL_SELECTING_ICON, selectElementTool));
        defaultToolButton.setPressedIcon(TOOL_SELECTING_PRESSED_ICON);
        defaultToolButton.setSelectedIcon(TOOL_SELECTING_PRESSED_ICON);
        defaultToolButton.setText(null);
        defaultToolButton.setMargin(new Insets(0, 0, 0, 0));
        toolToButton.put(selectElementTool, defaultToolButton);
        buttonsGroup.add(defaultToolButton);
        diagramEditor.addToolChangeListener(new ICurrentToolListener() {
            @SuppressWarnings("synthetic-access")
            public void currentToolChanged(Tool oldTool, Tool newTool) {
                if (toolToButton.containsKey(newTool)) {
                    toolToButton.get(newTool).setSelected(true);
                }
            }
        });
        defaultToolButton.setSelected(true);
        return defaultToolButton;
    }

    @SuppressWarnings("synthetic-access")
    private JMenuBar createMenu() {
        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menu.add(fileMenu);
        fileMenu.add(new DiagramSavingAction("Save", this));
        fileMenu.add(new DiagramLoadingAction("Open", this));
        menu.add(new JMenu("Edit"));
        return menu;
    }

    public DiagramEditor getDiagramEditor() {
        return diagramEditor;
    }

}
