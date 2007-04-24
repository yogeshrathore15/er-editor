package ru.amse.soultakov.ereditor;

import javax.swing.JFrame;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;

public class ERMain {

    public static void main(String[] args) {
        DiagramEditorFrame def = new DiagramEditorFrame();
        def.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        def.pack();
        def.setVisible(true);
    }
}
