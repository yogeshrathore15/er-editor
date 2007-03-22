package ru.amse.soultakov.softwaredesign.binarytree;

import java.util.ArrayList;
import java.util.Collection;

public class ArrayBinaryTree<T extends Comparable<? super T>> extends AbstractBinaryTree<T> implements
        BinaryTree<T> {

    private ArrayList<T> array = new ArrayList<T>();

    private int size = 0;

    public ArrayBinaryTree() {
        // empty body
    }

    public ArrayBinaryTree(Collection<T> collection) {
        addAll(collection);
    }

    public boolean add(T element) {
        if (isEmpty()) {
            array.add(element);
            size = 1;
            return true;
        } else {
            return addElement(element);
        }
    }

    private boolean addElement(T element) {
        int index = 0;
        while (isExistentElement(index)) {
            int result = element.compareTo(array.get(index));
            if (result == 0) {
                return false;
            } else {
                index = nextIndex(index, result);
            }
        }
        size++;
        ensureCapacity(index);
        array.set(index, element);
        return true;
    }

    private void ensureCapacity(int index) {
        int result = index - array.size() + 1;
        for (int i = 0; i < result; i++) {
            array.add(null);
        }
    }

    public boolean contains(T element) {
        int index = 0;
        while (isExistentElement(index)) {
            int result = element.compareTo(array.get(index));
            if (result == 0) {
                return true;
            } else {
                index = nextIndex(index, result);
            }
        }
        return false;
    }

    public T getMinimum() {
        ensureNotEmpty();
        int index = 0;
        T element = null;
        while (isExistentElement(index)) {
            element = array.get(index);    
            index = leftIndex(index);
        }
        return element;
    }

    public T getMaximum() {
        ensureNotEmpty();
        int index = 0;
        T element = null;
        while (isExistentElement(index)) {
            element = array.get(index);    
            index = rightIndex(index);
        }
        return element;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    private boolean isExistentElement(int index) {
        return (index < array.size()) && (array.get(index) != null);
    }
    
    private int nextIndex(int index, int result) {
        if (result < 0) {
            return leftIndex(index);
        } else {
            return rightIndex(index);
        } 
    }

    private int rightIndex(int index) {
        return index * 2 + 2;
    }

    private int leftIndex(int index) {
        return index * 2 + 1;
    }

}
