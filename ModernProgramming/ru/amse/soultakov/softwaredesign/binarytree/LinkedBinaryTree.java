package ru.amse.soultakov.softwaredesign.binarytree;

import java.util.Collection;

public class LinkedBinaryTree<T extends Comparable<? super T>> extends AbstractBinaryTree<T> implements
        BinaryTree<T> {

    private Node head = null;

    private int size = 0;

    public LinkedBinaryTree() {
        //empty constructor
    }

    public LinkedBinaryTree(Collection<T> collection) {
        addAll(collection);
    }

    public boolean add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element must be non-null");
        }
        if (isEmpty()) {
            addFirst(element);
            return true;
        } else {
            return addElement(head, element);
        }
    }

    private void addFirst(T element) {
        head = createNewNode(element);
        size = 1;
    }

    private boolean addElement(Node current, T element) {
        int result = current.getElement().compareTo(element);
        if (result > 0) {
            if (current.getLeft() != null) {
                return addElement(current.getLeft(), element);
            } else {
                current.setLeft(createNewNode(element));
                size++;
                return true;
            }
        } else if (result < 0) {
            if (current.getRight() != null) {
                return addElement(current.getRight(), element);
            } else {
                current.setRight(createNewNode(element));
                size++;
                return true;
            }
        } else {
            return false;
        }
    }

    private Node createNewNode(T element) {
        return new Node(element);
    }

    public boolean contains(T element) {
        Node current = head;
        while (current != null) {
            int result = current.getElement().compareTo(element);
            if (result > 0) {
                current = current.getLeft();
            } else if (result < 0) {
                current = current.getRight();
            } else {
                return true;
            }
        }
        return false;
    }

    public T getMinimum() {
        ensureNotEmpty();
        Node current = head;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current.getElement();
    }

    public T getMaximum() {
        ensureNotEmpty();
        Node current = head;
        while (current.getRight() != null) {
            current = current.getRight();
        }
        return current.getElement();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private class Node {
        private final T element;

        private Node left;

        private Node right;

        public Node(T element) {
            this.element = element;
        }

        public T getElement() {
            return element;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

    }

}
