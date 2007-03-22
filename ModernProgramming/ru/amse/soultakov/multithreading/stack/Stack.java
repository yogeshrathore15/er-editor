/*
 * Created on 02.12.2006
 */
package ru.amse.soultakov.multithreading.stack;

import java.util.ArrayList;

/**
 * The <code>Stack</code> class represents a last-in-first-out 
 * (LIFO) stack of objects. The usual<tt>push</tt> and <tt>pop</tt> 
 * operations are provided, as well as a method to <tt>peek</tt> at 
 * the top item on the stack.
 * When a stack is first created, it contains no items. 
 * All operations are thread-safe.
 *
 * @author Soultakov Maxim
 */
public class Stack<E> {

    /**
     * The capacity of Stack (the number of elements it may contain). 
     */
    private int capacity;
    
    /**
     * Elements of the Stack are stored in this <code>ArrayList</code>
     */
    private ArrayList<E> array;
    
    /**
     * Creates the stack with the specified capacity.
     * 
     * @param capacity the capacity to be set
     * @throws IllegalArgumentException if <code>capacity</code> isn't 
     * positive number
     */
    public Stack(int capacity) {
        synchronized(this) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Illegal capacity : " + capacity);
            }
            this.capacity = capacity;
            array = new ArrayList<E>(capacity);
        }
    }
    
    /**
     * Pushes an item onto the top of this stack. If stack is full 
     * method waits until at least one element will be removed from stack.
     * 
     * @param item  the item to be pushed onto this stack.
     * @return the <code>item</code> argument
     * @throws InterruptedException if the waiting process was interrupted 
     */
    public synchronized E push(E item) throws InterruptedException {
        while(array.size() == capacity) {
            wait();
        }
        array.add(item);
        notifyAll();
        return item;
    }
    
    /** 
     * Removes the object at the top of this stack and returns that 
     * object as the value of this function. If the stack is empty
     * method waits until at least one element will be pushed onto 
     * this stack
     *
     * @return The object at the top of this stack.
     * @throws InterruptedException if the waiting process was interrupted
     */
    public synchronized E pop() throws InterruptedException {
        while(array.size() == 0) {
            wait();
        }
        E item = array.remove(array.size() - 1);
        notifyAll();
        return item;
    }
    
    /**
     * Looks at the object at the top of this stack without removing it 
     * from the stack. If the stack is empty method waits until at 
     * least one element will be pushed onto this stack
     * 
     * @return The object at the top of this stack.
     * @throws InterruptedException if the waiting process was interrupted
     */
    public synchronized E peek() throws InterruptedException {
        while(array.size() == 0) {
            wait();
        }
        E item = array.get(array.size()-1);
        return item;
    }
    
    /**
     * Returns the capacity of Stack (the number of elements it may contain).
     * 
     * @return the capacity of Stack (the number of elements it may contain).
     */
    public int getCapacity() {
        return capacity;
    }
    
    /**
     * Returns the number of elements in this stack.
     * 
     * @return the size of the stack
     */
    public int getSize() {
        return array.size();
    }
    
}
