package ru.amse.soultakov.ereditor;

import java.awt.Frame;

import javax.swing.JFrame;

import ru.amse.soultakov.ereditor.controller.DiagramEditorFrame;

public class ERMain {

    public static void main(String[] args) {
        System.out.println(ERMain.class.getClassLoader()
                .getResource("./images/tool_selecting_pressed.png"));
        DiagramEditorFrame def = new DiagramEditorFrame();
        def.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        def.pack();
        def.setExtendedState(Frame.MAXIMIZED_BOTH);
        def.setVisible(true);

        System.out.println(ERMain.class.getClassLoader()
                .getResource("./images/tool_selecting_pressed.png"));
    }

}
