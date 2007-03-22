package ru.amse.soultakov.sortedarray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, хранящий отсортированные объекты типа <code>Comparable</code>. 
 * @author Soultakov Maxim
 */
public class SortedArray<E extends Comparable<? super E>> 
	implements Iterable<E> {
	
    private ArrayList<E> array;
		
	/**
     * Создает пустой массив. 
	 */
    public SortedArray() {
		array = new ArrayList<E>();
	}
	
    /**
     * Создает массив с заданной исходной размерностью
     * @param initialCapacity
     */
	public SortedArray(int initialCapacity) {
		array = new ArrayList<E>(initialCapacity);
	}
    
	/**
     * Возвращает итератор по сортированному массиву 
	 */
	public Iterator<E> iterator() {
		return array.iterator();
	}
	
    /**
     * Возвращает элемент из заданной позиции.
     * @param index - индекс элемента
     * @throws <code>IndexOutOfBoundsException</code> если индекс находится вне границ
     * массива, т.е. <code>(index < 0) || (index >= size) </code>
     * @return элемент с указанным индексом
     */
	public E get(int index) {
		return array.get(index);
	}
	
    /**
     * Возвращает количество элементов в массиве
     * @return количество элементов в массиве
     */
	public int size() {
		return array.size();
	}
	
	/**
     * Добавляет элемент в массив. После добавления массив остается отсортированным
     * @param element - добавляемый элемент. 
	 */
    public void add(E element) {
		int i = 0;
		while( (i<size()) && (element.compareTo(array.get(i))>0) ) {
			i++;
		}
		array.add(i, element);
	}
	
    /**
     * Возвращает массив элементов хранившихся в экземпляре класса 
     * <code>SortedArray</code>
     * @param a - массив, в который будут записаны элементы
     * @return массив, создержащий все элементы массива
     */
	public E[] toArray(E[] a) {
		return array.toArray(a);
	}
    
    @Override
    public String toString() {
        return array.toString();
    }
	
    /**
     * Статический метод, принимающий на вход список и сортирующий его
     * @param list - список, который должен быть отсортирован
     */
	public static <T extends Comparable<? super T>> void sortList(List<T> list) {
		Collections.sort(list);
	}

}
