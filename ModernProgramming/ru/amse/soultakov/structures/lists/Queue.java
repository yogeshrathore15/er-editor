package ru.amse.soultakov.structures.lists;

public class Queue<E> {

	private static int MAX_SIZE = 100001;
	
	private final E[] data;
	
	private int front = 0;
	private int rear = MAX_SIZE - 1;
	
	@SuppressWarnings("unchecked")
	public Queue() {
		data = (E[]) new Object[MAX_SIZE];
	}
	
    public E push(E element) {
        if (addOne(addOne(rear)) == front) {
        	throw new IllegalStateException("The queue is full");
        } else {
        	rear = addOne(rear);
        	data[rear] = element;
        }
    	return element;
    }
    
    public E pop() {
        if (isEmpty()) {
        	throw new IllegalStateException("The queue is empty");
        } else {
        	E element = data[front];
        	front = addOne(front);
        	return element;
        }
    }
    
    private static int addOne(int i) {
    	return ((i + 1) % MAX_SIZE);
    }
    
    public boolean isEmpty() {
    	return front == addOne(rear);
    }
}
