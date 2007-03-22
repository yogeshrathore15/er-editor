package ru.amse.soultakov.softwaredesign.binarytree;

import java.util.Arrays;

public class TreeTest {
    
    private enum BinaryTreeType {
        LINKED { public <T extends Comparable<? super T>> BinaryTree<T> 
                getBinaryTree() {
            return new LinkedBinaryTree<T>();
        }},
        ARRAY  { public <T extends Comparable<? super T>> BinaryTree<T> 
                getBinaryTree() {
            return new ArrayBinaryTree<T>();
        }};

        public abstract <T extends Comparable<? super T>> BinaryTree<T> 
                getBinaryTree();
    }; 

    private static <T extends Comparable<? super T>> 
            BinaryTree<T> getBinaryTree(BinaryTreeType type) {
        return type.getBinaryTree();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        BinaryTree<Integer> bt = 
            TreeTest.<Integer>getBinaryTree(BinaryTreeType.LINKED);
        bt.add(1);
        bt.add(2);
        bt.add(0);
        bt.addAll(Arrays.asList(new Integer[] {-10,20,30}));
        System.out.println(bt.getMaximum());
        System.out.println(bt.getMinimum());
        System.out.println(bt.contains(0));
        System.out.println(bt.contains(-1));
        System.out.println(bt.contains(2));
    }

}
