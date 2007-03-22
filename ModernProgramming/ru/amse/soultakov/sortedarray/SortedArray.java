package ru.amse.soultakov.sortedarray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * �����, �������� ��������������� ������� ���� <code>Comparable</code>. 
 * @author Soultakov Maxim
 */
public class SortedArray<E extends Comparable<? super E>> 
	implements Iterable<E> {
	
    private ArrayList<E> array;
		
	/**
     * ������� ������ ������. 
	 */
    public SortedArray() {
		array = new ArrayList<E>();
	}
	
    /**
     * ������� ������ � �������� �������� ������������
     * @param initialCapacity
     */
	public SortedArray(int initialCapacity) {
		array = new ArrayList<E>(initialCapacity);
	}
    
	/**
     * ���������� �������� �� �������������� ������� 
	 */
	public Iterator<E> iterator() {
		return array.iterator();
	}
	
    /**
     * ���������� ������� �� �������� �������.
     * @param index - ������ ��������
     * @throws <code>IndexOutOfBoundsException</code> ���� ������ ��������� ��� ������
     * �������, �.�. <code>(index < 0) || (index >= size) </code>
     * @return ������� � ��������� ��������
     */
	public E get(int index) {
		return array.get(index);
	}
	
    /**
     * ���������� ���������� ��������� � �������
     * @return ���������� ��������� � �������
     */
	public int size() {
		return array.size();
	}
	
	/**
     * ��������� ������� � ������. ����� ���������� ������ �������� ���������������
     * @param element - ����������� �������. 
	 */
    public void add(E element) {
		int i = 0;
		while( (i<size()) && (element.compareTo(array.get(i))>0) ) {
			i++;
		}
		array.add(i, element);
	}
	
    /**
     * ���������� ������ ��������� ����������� � ���������� ������ 
     * <code>SortedArray</code>
     * @param a - ������, � ������� ����� �������� ��������
     * @return ������, ����������� ��� �������� �������
     */
	public E[] toArray(E[] a) {
		return array.toArray(a);
	}
    
    @Override
    public String toString() {
        return array.toString();
    }
	
    /**
     * ����������� �����, ����������� �� ���� ������ � ����������� ���
     * @param list - ������, ������� ������ ���� ������������
     */
	public static <T extends Comparable<? super T>> void sortList(List<T> list) {
		Collections.sort(list);
	}

}
