/**
 * 
 */
package ru.amse.soultakov.ereditor.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Soultakov Maxim
 * 
 */
public class BoundedStack<T> implements Iterable<T> {

    private static final int DEFAULT_MAX_SIZE = 100;

    private final LinkedList<T> list = new LinkedList<T>();

    private final int maxSize;

    public BoundedStack() {
        this(DEFAULT_MAX_SIZE);
    }

    public BoundedStack(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * @return the maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }

    public void push(T element) {
        list.addLast(element);
        if (size() > maxSize) {
            list.removeFirst();
        }
    }

    public T peek() {
        if (size() == 0) {
            throw new IllegalStateException("The stack is empty!");
        }
        return list.getLast();
    }

    public T pop() {
        if (size() == 0) {
            throw new IllegalStateException("The stack is empty!");
        }
        return list.removeLast();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        list.clear();
    }

    public Iterator<T> iterator() {
        return Collections.unmodifiableList(list).iterator();
    }

}
