/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public final class AboutAction extends AbstractAction {
    
    private final JFrame diagramEditorFrame;
    
    private final JPanel aboutPanel = new JPanel(new FlowLayout());
    
    public AboutAction(final JFrame diagramEditorFrame) {
        super("About...");
        this.diagramEditorFrame = diagramEditorFrame;
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("F1"));
        aboutPanel.add(new JLabel(
                "<html><center><h2>ER Diagram Editor 1.0</h2>" +
                "<p>Program for editing entity-relationship diagrams." +
                "</p><p>Written by Maxim Soultakov,</p><p>2007</p>" 
                + "</center></html>"));
        aboutPanel.setBounds(250, 270, 400, 140);
    }

    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(diagramEditorFrame, aboutPanel, "About...", JOptionPane.PLAIN_MESSAGE);
    }
    
}