/*
 * Created on 24.11.2006
 */
package ru.amse.soultakov.swingtasks.wordpad;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class MainFrame extends JFrame {

    private JTextPane textPane = new JTextPane();
    
    public MainFrame() {
        super("WordPad");
        this.setLayout(new GridLayout(1,1));
        this.add(setupTextPane());
    }

    private Component setupTextPane() {
        JScrollPane editorScrollPane = new JScrollPane(textPane);
        editorScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        editorScrollPane.setPreferredSize(new Dimension(450, 445));
        editorScrollPane.setMinimumSize(new Dimension(10, 10));
        return editorScrollPane;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame mf = new MainFrame();
                mf.pack();
                mf.setVisible(true);
            }
        });
    }

}
