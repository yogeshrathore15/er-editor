package ru.amse.soultakov.structures.lists;

import java.util.Iterator;

public class Vector<E> implements Iterable<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    E[] data;
    private int size;
    
    public Vector() {
        this(DEFAULT_INITIAL_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
	public Vector(int initialCapacity) {
        data = (E[]) new Object[initialCapacity];
    }
    
    public void add(E element) {
        ensureCapacity(size + 1);
        data[size++] = element;
    }
    
    public E get(int index) {
        checkRange(index);
        return data[index];
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            
            int current = 0;
            
            public boolean hasNext() {
                return current < size;
            }

            public E next() {
                return data[current++];
            }

            public void remove() {
                Vector.this.remove(current);
            }
            
        };
    }
    
    public boolean remove(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    fastRemove(i);
                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(data[i])) {
                    fastRemove(i);
                    return true;
                }
            }
        }
        return false;
    }
    
    public E remove(int index) {
        checkRange(index);
        return fastRemove(index);
    }
   
    public E set(int index, E element) {
        checkRange(index);
        E oldValue = data[index];
        data[index] = element;
        return oldValue;
    }
    
    public int size() {
        return size;
    }
    
    private void checkRange(int index) {
        if ( (index < 0) || (index >= size) ) {
            throw new IndexOutOfBoundsException("Index = " + index 
                    + ", Size = " + size);
        }
    }
    
    void ensureCapacity(int elementsCount) {
        int oldCapacity = data.length;
        if (elementsCount > oldCapacity) {
            Object oldData[] = data;
            int newCapacity = (oldCapacity * 2) + 1;
            if (newCapacity < elementsCount) {
                newCapacity = elementsCount;
            }
            data = (E[]) new Object[newCapacity];
            System.arraycopy(oldData, 0, data, 0, oldCapacity);
        }
    }
    
    private E fastRemove(int index) {
        E oldValue = data[index];
        System.arraycopy(data, index + 1, data, index, data.length 
                - index - 1);
        data[--size] = null;
        return oldValue;
    }
    
}

