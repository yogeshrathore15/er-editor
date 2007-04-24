package ru.amse.soultakov.ereditor.controller;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        DiagramEditorFrame def = new DiagramEditorFrame();
        def.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        def.pack();
        def.setVisible(true);
    }
}
