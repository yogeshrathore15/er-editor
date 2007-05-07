package ru.amse.soultakov.ereditor;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;

public class ERMain {

    public static void main(String[] args) {
        final DiagramEditorFrame def = new DiagramEditorFrame();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                def.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                def.setExtendedState(Frame.MAXIMIZED_BOTH);
                def.pack();
                def.setVisible(true);
            }
        });
    }
    
}
