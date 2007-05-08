package ru.amse.soultakov.ereditor;

import java.awt.Frame;

import javax.swing.SwingUtilities;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;

public class ERMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final DiagramEditorFrame def = new DiagramEditorFrame();
                def.setExtendedState(Frame.MAXIMIZED_BOTH);
                def.pack();
                def.setVisible(true);
            }
        });
    }

}
