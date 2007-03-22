/*
 * Created on 13.11.2006
 */
package ru.amse.soultakov.swingtasks.keymapstest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

public class TestFrame extends JFrame {
    
    private JTextField textField = new JTextField();
    private JButton caseButton = new JButton("Case");
    private JButton sortButton = new JButton("Sort");
    
    private JRadioButton ctrlRadio = new JRadioButton("Ctrl", true);
    private JRadioButton altCtrlRadio = new JRadioButton("Alt + Ctrl", false);
    
    private Actions actions = new Actions();
    
    private JCheckBox appendCheckBox = new JCheckBox(actions.synchronizeAppending);
    private JCheckBoxMenuItem appendMenuItem = 
        new JCheckBoxMenuItem(actions.synchronizeAppending);
    
    private InputMap ctrlInputMap = new InputMap();
    private InputMap altCtrlInputMap = new InputMap();
    
    
    
    public TestFrame() {
        super("Test");
        this.setLayout(new BorderLayout());
        setupMenu();
        setupToolBar();
        setupMain();
        bindKeys();
    }

    private void setupMain() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(textField, BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0,2));
        buttonsPanel.add(caseButton);
        buttonsPanel.add(sortButton);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        JPanel radioPanel = new JPanel();
        radioPanel.add(ctrlRadio);
        radioPanel.add(altCtrlRadio);
        ctrlRadio.addActionListener(actions.updateInputMaps);
        altCtrlRadio.addActionListener(actions.updateInputMaps);
        ButtonGroup rg = new ButtonGroup();
        rg.add(ctrlRadio);
        rg.add(altCtrlRadio);
        mainPanel.add(radioPanel, BorderLayout.SOUTH);
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private void setupToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(actions.convertToLowerCase);
        toolBar.add(actions.convertToUpperCase);
        toolBar.add(actions.ascendingSort);
        toolBar.add(actions.descendingSort);
        toolBar.add(appendCheckBox);
        this.add(toolBar, BorderLayout.NORTH);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.add(actions.exit);
        menuBar.add(menu);
        menu = new JMenu("Case");
        menu.setMnemonic(KeyEvent.VK_C);
        menu.add(actions.convertToLowerCase);
        menu.add(actions.convertToUpperCase);
        menuBar.add(menu);
        menu = new JMenu("Sort");
        menu.setMnemonic(KeyEvent.VK_S);
        menu.add(actions.ascendingSort);
        menu.add(actions.descendingSort);
        menuBar.add(menu);
        menu = new JMenu("Settings");
        menu.setMnemonic(KeyEvent.VK_T);
        appendMenuItem.setMnemonic(KeyEvent.VK_A);
        menu.add(appendMenuItem);
        menuBar.add(menu);
        this.setJMenuBar(menuBar);
    }
    
    private void bindKeys() {
        ctrlInputMap.put(KeyStroke.getKeyStroke("control 1"), "first action");
        ctrlInputMap.put(KeyStroke.getKeyStroke("control 2"), "second action");
        altCtrlInputMap.put(KeyStroke.getKeyStroke("alt control 1"), "first action");
        altCtrlInputMap.put(KeyStroke.getKeyStroke("alt control 2"), "second action");
        caseButton.getActionMap().put("first action", actions.convertToLowerCase);
        caseButton.getActionMap().put("second action", actions.convertToUpperCase);
        sortButton.getActionMap().put("first action", actions.ascendingSort);
        sortButton.getActionMap().put("second action", actions.descendingSort);
        updateInputMaps();
    }

    private void updateInputMaps() {
        if (altCtrlRadio.isSelected()) {
            caseButton.setInputMap(JComponent.WHEN_FOCUSED, altCtrlInputMap);
            sortButton.setInputMap(JComponent.WHEN_FOCUSED, altCtrlInputMap);
        } else {
            caseButton.setInputMap(JComponent.WHEN_FOCUSED, ctrlInputMap);
            sortButton.setInputMap(JComponent.WHEN_FOCUSED, ctrlInputMap);
        }
    }

    private void updateTextField(String s) {
        if (appendCheckBox.isSelected()) {
            textField.setText(textField.getText() + s);
        } else {
            textField.setText(s);
        }
    }
    
    public class Actions {
        
        public final Action synchronizeAppending = new AbstractAction() {
            {
                this.putValue(NAME, "Append");
                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control P"));
            }
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == appendCheckBox) {
                    appendMenuItem.setSelected(appendCheckBox.isSelected());
                } else {
                    appendCheckBox.setSelected(appendMenuItem.isSelected());
                }
            }
        };
        
        public final Action updateInputMaps = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                updateInputMaps();
            }
        };
        
        public final Action exit = new AbstractAction() {
            {
                this.putValue(NAME, "Exit");
                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
            }
            public void actionPerformed(ActionEvent e) {
                TestFrame.this.dispose();
            }
        };

        public final Action convertToLowerCase = new AbstractAction() {
            {
                this.putValue(NAME, "Lower");
                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
                this.putValue(LONG_DESCRIPTION, "AAAAAAAa");
            }
            public void actionPerformed(ActionEvent e) {
                updateTextField(textField.getText().toLowerCase());
            }
        };
        
        public final Action convertToUpperCase = new AbstractAction() {
            {
                this.putValue(NAME, "Upper");
                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
            }
            public void actionPerformed(ActionEvent e) {
                updateTextField(textField.getText().toUpperCase());
            }
        };
        
        public Action ascendingSort = new AbstractAction() {
            {
                this.putValue(NAME, "Asc");
                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
            }
            public void actionPerformed(ActionEvent e) {
                updateTextField(new String(getSortedArray(textField.getText())));
            }
        };
               
        public Action descendingSort = new AbstractAction() {
            {
                this.putValue(NAME, "Desc");
                this.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
            }
            public void actionPerformed(ActionEvent e) {
                char[] chars = getSortedArray(textField.getText());
                for(int i = 0, n = chars.length/2; i < n; i++) {
                    char t = chars[i];
                    chars[i] = chars[chars.length - i - 1];
                    chars[chars.length - i - 1] = t;
                }
                updateTextField(new String(chars));
            }
        };
        
        public char[] getSortedArray(String string) {
            char[] chars = new char[string.length()];
            string.getChars(0, string.length(), chars, 0);
            Arrays.sort(chars);
            return chars;
        }
        
    }
    
    public static void main(String[] args) {
        TestFrame tst = new TestFrame();
        tst.setDefaultCloseOperation(EXIT_ON_CLOSE);
        tst.setSize(new Dimension(400,300));
        tst.setLocation(200,300);
        tst.setVisible(true);
    }
    
}
