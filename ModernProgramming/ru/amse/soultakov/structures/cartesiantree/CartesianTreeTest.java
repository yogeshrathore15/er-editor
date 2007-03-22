package ru.amse.soultakov.structures.cartesiantree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CartesianTreeTest {
    
    
    /*4debug
    static void recShowTree(CartesianTree<Integer>.TreeNode node, String shift) {
        if (node == null) return;
        System.out.println(shift + node);
        recShowTree(node.getLeft(), shift + "-" );
        recShowTree(node.getRight(), shift + "+");
    }
    
    static void showTree(CartesianTree<Integer> ct) {
        CartesianTree<Integer>.TreeNode root = ct.root.getRight();
        recShowTree(root, "");
    }
    */

    private static final String INPUT = "bst.in";
    private static final String OUTPUT = "bst.out";
    
    private static final String CMD_EXISTS = "exists";
    private static final String CMD_INSERT = "insert";
    private static final String CMD_DELETE = "delete";
    private static final String CMD_NEXT   = "next";
    private static final String CMD_PREV   = "prev";
    
    static CartesianTree<Integer> ct = new CartesianTree<Integer>();
    
    private static void process() throws NumberFormatException, IOException {
        BufferedReader in = new BufferedReader(new FileReader(INPUT));
        BufferedWriter out = new BufferedWriter(new FileWriter(OUTPUT));
        String line;
        Integer value = null;
        String command;
        while( (line = in.readLine()) != null) {
            String[] strings = line.split("\\s");
            command = strings[0];
            value = Integer.valueOf(strings[1]);
            if(command.compareToIgnoreCase(CMD_INSERT) == 0) {
                ct.insert(value);
            } else if(command.compareToIgnoreCase(CMD_DELETE) == 0) {
                ct.remove(value);
            } else if(command.compareToIgnoreCase(CMD_EXISTS) == 0) {
                out.write(ct.exists(value) + "\n");
            } else if(command.compareToIgnoreCase(CMD_NEXT) == 0) {
                Integer i = ct.next(value);
                String strToWrite = i != null ? i.toString() : "none";
                out.write(strToWrite + "\n");
            } else if(command.compareToIgnoreCase(CMD_PREV) == 0) {
                Integer i = ct.previous(value);
                String strToWrite = i != null ? i.toString() : "none";
                out.write(strToWrite + "\n");
            }
        }
        out.close();
        in.close();
    }
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        process();
    }
}
