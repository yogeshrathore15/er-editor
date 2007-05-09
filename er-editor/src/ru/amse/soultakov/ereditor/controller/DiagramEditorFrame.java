package ru.amse.soultakov.ereditor.controller;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import ru.amse.soultakov.ereditor.controller.actions.AboutAction;
import ru.amse.soultakov.ereditor.controller.actions.AddAttributeAction;
import ru.amse.soultakov.ereditor.controller.actions.DiagramEditorAction;
import ru.amse.soultakov.ereditor.controller.actions.LoadDiagramAction;
import ru.amse.soultakov.ereditor.controller.actions.NewDiagramAction;
import ru.amse.soultakov.ereditor.controller.actions.RedoAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveAttributeAction;
import ru.amse.soultakov.ereditor.controller.actions.RemoveSelectionAction;
import ru.amse.soultakov.ereditor.controller.actions.SaveDiagramAction;
import ru.amse.soultakov.ereditor.controller.actions.UndoAction;
import ru.amse.soultakov.ereditor.controller.tools.AddCommentTool;
import ru.amse.soultakov.ereditor.controller.tools.AddEntityTool;
import ru.amse.soultakov.ereditor.controller.tools.AddLinkTool;
import ru.amse.soultakov.ereditor.controller.tools.AddRelationshipTool;
import ru.amse.soultakov.ereditor.controller.tools.ITool;
import ru.amse.soultakov.ereditor.controller.tools.IToolListener;
import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;

@SuppressWarnings("serial")
public class DiagramEditorFrame extends JFrame {

    private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

    public static final String ERD = "erd";

    public static final String ERD_EXTENSION = "." + ERD;

    private static final ImageIcon TOOL_SELECTING_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/tool_select_pressed.png"));

    private static final ImageIcon TOOL_SELECTING_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/tool_select.png"));

    private static final ImageIcon TOOL_ADDING_ENTITY_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/tool_add_entity.png"));

    private static final ImageIcon TOOL_ADDING_ENTITY_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class
                    .getResource("/images/tool_add_entity_pressed.png"));

    private static final ImageIcon TOOL_ADDING_RELATIONSHIP_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/tool_add_relation.png"));

    private static final ImageIcon TOOL_ADDING_RELATIONHIP_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class
                    .getResource("/images/tool_add_relation_pressed.png"));

    private static final ImageIcon REMOVE_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/remove.png"));

    private static final ImageIcon REMOVE_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/remove_pressed.png"));

    private static final ImageIcon UNDO_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/undo.png"));

    private static final ImageIcon UNDO_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/undo_pressed.png"));

    private static final ImageIcon REDO_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/redo.png"));

    private static final ImageIcon REDO_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/redo_pressed.png"));

    private static final ImageIcon ATTRIBUTE_REMOVE_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/attribute_remove.png"));

    private static final ImageIcon ATTRIBUTE_REMOVE_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class
                    .getResource("/images/attribute_remove_pressed.png"));

    private static final ImageIcon ATTRIBUTE_ADD_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/attribute_add.png"));

    private static final ImageIcon ATTRIBUTE_ADD_PRESSED_ICON = new ImageIcon(
            DiagramEditorFrame.class
                    .getResource("/images/attribute_add_pressed.png"));

    private static final ImageIcon APPLICATION_ICON = new ImageIcon(
            DiagramEditorFrame.class.getResource("/images/application.png"));

    private final DiagramEditor diagramEditor = new DiagramEditor();

    private final Map<ITool, AbstractButton> toolToButton = newHashMap();

    private final IToolListener toolListener = new IToolListener() {
        @SuppressWarnings("synthetic-access")
        public void operationFinished() {
            diagramEditor.setTool(selectElementTool);
        }
    };

    private final ITool selectElementTool = new SelectElementTool(diagramEditor);

    private final ITool addRelationshipTool = new AddRelationshipTool(diagramEditor);

    private final ITool addEntityTool = new AddEntityTool(diagramEditor);

    private final Action newDiagramAction = new NewDiagramAction("New", this);

    private final Action saveDiagramAction = new SaveDiagramAction("Save", this);

    private final Action loadDiagramAction = new LoadDiagramAction("Open...", this);

    private final Action exitAction = new AbstractAction("Exit") {
        {
            putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
        }

        public void actionPerformed(ActionEvent e) {
            DiagramEditorFrame.this.processWindowEvent(new WindowEvent(
                    DiagramEditorFrame.this, WindowEvent.WINDOW_CLOSING));
        }
    };

    private final Action redoAction = new RedoAction(diagramEditor, "Redo",
            REDO_ICON);

    private final Action undoAction = new UndoAction(diagramEditor, "Undo",
            UNDO_ICON);;

    private final Action removeAttributeAction = new RemoveAttributeAction(
            diagramEditor, "Remove attribute", ATTRIBUTE_REMOVE_ICON);

    private final Action addAttributeAction = new AddAttributeAction(diagramEditor,
            "Add attribute", ATTRIBUTE_ADD_ICON);

    private final Action removeSelectionAction = new RemoveSelectionAction(
            diagramEditor, "Remove", REMOVE_ICON);

    private final Action enableSelectingAction = new DiagramEditorAction(
            diagramEditor, "Select", TOOL_SELECTING_ICON, selectElementTool) {
        {
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift alt S"));
        }
    };

    private final Action enableAddingRelationshipAction = new DiagramEditorAction(
            diagramEditor, "Add relationship", TOOL_ADDING_RELATIONSHIP_ICON,
            addRelationshipTool) {
        {
            putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift alt R"));
        }
    };

    private final Action enableAddingEntityAction = new DiagramEditorAction(
            diagramEditor, "Add Entity", TOOL_ADDING_ENTITY_ICON, addEntityTool) {
        {
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift alt E"));
        }
    };

    public DiagramEditorFrame() {
        super("ER Diagram Editor");
        this.setJMenuBar(createMenu());
        this.add(createToolbar(), BorderLayout.NORTH);
        this.add(createDiagramEditorPanel(), BorderLayout.CENTER);
        this.setIconImage(APPLICATION_ICON.getImage());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (diagramEditor.isDiagramChanged()) {
                    int res = JOptionPane.showConfirmDialog(DiagramEditorFrame.this,
                            "Diagram has been modified. Save changes?",
                            "Save Resource", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (res == JOptionPane.YES_OPTION) {
                        saveDiagramAction.actionPerformed(null);
                    } else if (res == JOptionPane.CANCEL_OPTION) {
                        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                    } else {
                        setDefaultCloseOperation(EXIT_ON_CLOSE);
                    }
                } else {
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
        });
    }

    private Component createDiagramEditorPanel() {
        return new JScrollPane(diagramEditor);
    }

    private Component createToolbar() {
        JToolBar toolBar = new JToolBar();
        ButtonGroup buttonsGroup = new ButtonGroup();
        toolBar.setRollover(true);
        toolBar.add(createDefaultButton(buttonsGroup));
        toolBar.add(createAddEntityButton(buttonsGroup));
        toolBar.add(createAddRelationshipButton(buttonsGroup));
        // toolBar.add(createAddCommentButton(buttonsGroup));
        // toolBar.add(createAddLinkButton(buttonsGroup));
        toolBar.add(createRemoveButton());
        toolBar.add(createUndoButton());
        toolBar.add(createRedoButton());
        toolBar.add(createAddAttributeButton());
        toolBar.add(createRemoveAttributeButton());
        return toolBar;
    }

    private JButton createRemoveAttributeButton() {
        JButton button = new JButton(removeAttributeAction);
        button.setSelectedIcon(ATTRIBUTE_REMOVE_PRESSED_ICON);
        button.setPressedIcon(ATTRIBUTE_REMOVE_PRESSED_ICON);
        button.setMargin(NO_INSETS);
        button.setText(null);
        button.setToolTipText("Remove attribute");
        return button;
    }

    private JButton createAddAttributeButton() {
        JButton button = new JButton(addAttributeAction);
        button.setSelectedIcon(ATTRIBUTE_ADD_PRESSED_ICON);
        button.setPressedIcon(ATTRIBUTE_ADD_PRESSED_ICON);
        button.setMargin(NO_INSETS);
        button.setText(null);
        button.setToolTipText("Add attribute");
        return button;
    }

    private JButton createRedoButton() {
        JButton button = new JButton(redoAction);
        button.setSelectedIcon(REDO_PRESSED_ICON);
        button.setPressedIcon(REDO_PRESSED_ICON);
        button.setMargin(NO_INSETS);
        button.setText(null);
        button.setToolTipText("Redo");
        return button;
    }

    private JButton createUndoButton() {
        JButton button = new JButton(undoAction);
        button.setSelectedIcon(UNDO_PRESSED_ICON);
        button.setPressedIcon(UNDO_PRESSED_ICON);
        button.setMargin(NO_INSETS);
        button.setText(null);
        button.setToolTipText("Undo");
        return button;
    }

    private JButton createRemoveButton() {
        JButton button = new JButton(removeSelectionAction);
        button.setSelectedIcon(REMOVE_PRESSED_ICON);
        button.setPressedIcon(REMOVE_PRESSED_ICON);
        button.setText(null);
        button.setMargin(NO_INSETS);
        button.setToolTipText("Remove");
        return button;
    }

    @SuppressWarnings("unused")
    private JToggleButton createAddLinkButton(ButtonGroup buttonsGroup) {
        AddLinkTool addLinkTool = new AddLinkTool(diagramEditor);
        addLinkTool.addListener(toolListener);
        JToggleButton addLinkButton = new JToggleButton(new DiagramEditorAction(
                diagramEditor, "Add link", addLinkTool));
        toolToButton.put(addLinkTool, addLinkButton);
        buttonsGroup.add(addLinkButton);
        return addLinkButton;
    }

    @SuppressWarnings("unused")
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
        addRelationshipTool.addListener(toolListener);
        JToggleButton addRelationshipButton = new JToggleButton(
                enableAddingRelationshipAction);
        addRelationshipButton.setSelectedIcon(TOOL_ADDING_RELATIONHIP_PRESSED_ICON);
        addRelationshipButton.setPressedIcon(TOOL_ADDING_RELATIONHIP_PRESSED_ICON);
        addRelationshipButton.setText(null);
        addRelationshipButton.setMargin(NO_INSETS);
        addRelationshipButton.setToolTipText("Add relationship");
        toolToButton.put(addRelationshipTool, addRelationshipButton);
        buttonsGroup.add(addRelationshipButton);
        return addRelationshipButton;
    }

    private JToggleButton createAddEntityButton(ButtonGroup buttonsGroup) {
        addEntityTool.addListener(toolListener);
        JToggleButton addEntityButton = new JToggleButton(enableAddingEntityAction);
        addEntityButton.setSelectedIcon(TOOL_ADDING_ENTITY_PRESSED_ICON);
        addEntityButton.setPressedIcon(TOOL_ADDING_ENTITY_PRESSED_ICON);
        addEntityButton.setText(null);
        addEntityButton.setMargin(NO_INSETS);
        addEntityButton.setToolTipText("Add entity");
        toolToButton.put(addEntityTool, addEntityButton);
        buttonsGroup.add(addEntityButton);
        return addEntityButton;
    }

    private JToggleButton createDefaultButton(ButtonGroup buttonsGroup) {
        final JToggleButton defaultToolButton = new JToggleButton(
                enableSelectingAction);
        defaultToolButton.setPressedIcon(TOOL_SELECTING_PRESSED_ICON);
        defaultToolButton.setSelectedIcon(TOOL_SELECTING_PRESSED_ICON);
        defaultToolButton.setText(null);
        defaultToolButton.setMargin(NO_INSETS);
        defaultToolButton.setToolTipText("Select");
        toolToButton.put(selectElementTool, defaultToolButton);
        buttonsGroup.add(defaultToolButton);
        diagramEditor.addToolChangeListener(new ICurrentToolListener() {
            @SuppressWarnings("synthetic-access")
            public void currentToolChanged(ITool oldTool, ITool newTool) {
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
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        fileMenu.setMnemonic(KeyEvent.VK_F);
        fileMenu.add(disableIcon(newDiagramAction));
        fileMenu.add(disableIcon(loadDiagramAction));
        fileMenu.addSeparator();
        fileMenu.add(disableIcon(saveDiagramAction));
        fileMenu.addSeparator();
        fileMenu.add(disableIcon(exitAction));
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(disableIcon(undoAction));
        editMenu.add(disableIcon(redoAction));
        editMenu.addSeparator();
        editMenu.add(disableIcon(removeSelectionAction));
        JMenu toolMenu = new JMenu("Tool");
        menuBar.add(toolMenu);
        toolMenu.setMnemonic(KeyEvent.VK_T);
        toolMenu.add(disableIcon(enableSelectingAction));
        toolMenu.add(disableIcon(enableAddingEntityAction));
        toolMenu.add(disableIcon(enableAddingRelationshipAction));
        toolMenu.addSeparator();
        toolMenu.add(disableIcon(addAttributeAction));
        toolMenu.add(disableIcon(removeAttributeAction));
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.add(new AboutAction(this));
        return menuBar;
    }

    private static JMenuItem disableIcon(Action action) {
        JMenuItem item = new JMenuItem(action);
        item.setIcon(null);
        return item;
    }

    public DiagramEditor getDiagramEditor() {
        return diagramEditor;
    }

}
