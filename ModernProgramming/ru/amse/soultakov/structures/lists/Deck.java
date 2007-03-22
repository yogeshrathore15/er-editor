/*
 * Created on 19.10.2006
 */
package ru.amse.soultakov.structures.lists;

public class Deck<E> {
	
	private Vector<E> vector;
	
	private int head = 1;
	private int tail = 0;
	private int size = 0;
	
	public Deck() {
		vector = new Vector<E>(4);
	}
	
	public E pushHead(E element) {
		size++;
		int oldArraySize = vector.data.length;
		vector.ensureCapacity(size);
		int newArraySize = vector.data.length;		
		if (oldArraySize != newArraySize) {
			/*Pred : tail == getPrevIndex(head) */
			if (head > tail) {
				int length = oldArraySize - head; 
				System.arraycopy(vector.data, head, vector.data, 
					newArraySize - length, length);
				head = newArraySize - length - 1;	
			} else {
				head = newArraySize - 1;
			}
		} else {
			head = getPrevIndex(head);
		}
		vector.data[head] = element;
		return element;
	}
	
	public E pushTail(E element) {
		size++;
		int oldArraySize = vector.data.length;
		vector.ensureCapacity(size);
		int newArraySize = vector.data.length;		
		if (oldArraySize != newArraySize) {
			/*Pred : tail == getPrevIndex(head) */
			if (head > tail) {
				int length = oldArraySize - head; 
				System.arraycopy(vector.data, head, vector.data,
						newArraySize - length, length);
				head = newArraySize - length;
			}
		}
		tail = getNextIndex(tail);
		vector.data[tail] = element;
		return element;
	}
	
	public E popTail() throws IllegalStateException {
		checkSize();
		size--;
		E elementToReturn = vector.data[tail];
		tail = getPrevIndex(tail);
		return elementToReturn;
	}

	private void checkSize() throws IllegalStateException {
		if (size <= 0) {
			throw new IllegalStateException("Чтение из пустого дека");
		}
	}
	
	public E popHead() throws IllegalStateException{
		checkSize();
		size--;
		E elementToReturn = vector.data[head];
		head = getNextIndex(head);
		return elementToReturn;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public String toString() {
		String s = "[";
		s += vector.data[head] + ", ";
		for(int i = getNextIndex(head); i != getNextIndex(tail); i = getNextIndex(i)) {
			s += vector.data[i] + ", ";
		}
		s += "]";
		s += " [";
		for(E element : vector.data) {
			s += element + ", ";
		}
		s += "] ";
		s += head + " ";
		s += tail + " ";
		return s;
	}
	
	private int getNextIndex(int i) {
		return (i + 1) % vector.data.length;
	}
	
	private int getPrevIndex(int i) {
		return i == 0 ? vector.data.length - 1 : i - 1;
	}
}
