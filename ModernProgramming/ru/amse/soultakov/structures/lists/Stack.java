/*
 * Created on 19.10.2006
 */
package ru.amse.soultakov.structures.lists;

public class Stack<E> {
    
    private Vector<E> vector;
    
    public Stack() {
        vector = new Vector<E>(); 
    }
    
    public E push(E element) {
        vector.add(element);
        return element;
    }
    
    public E pop() {
        return vector.remove(vector.size() - 1);
    }
    
    public E peek() {
        return vector.get(vector.size() - 1);
    }
}
