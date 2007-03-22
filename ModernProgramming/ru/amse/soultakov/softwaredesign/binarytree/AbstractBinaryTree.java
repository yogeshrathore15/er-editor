/*
 * Created on 13.12.2006
 */
package ru.amse.soultakov.softwaredesign.binarytree;

import java.util.Collection;

public abstract class AbstractBinaryTree<T extends Comparable<? super T>> 
        implements BinaryTree<T> {

    public AbstractBinaryTree() {
        super();
    }

    public boolean addAll(Collection<T> collection) {
        boolean result = false;
        for (T element : collection) {
            result = result | add(element);
        }
        return result;
    }

    protected void ensureNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException(
                    "Can't get min or max from empty tree!");
        }
    }

}