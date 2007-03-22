/*
 * Created on 05.10.2006
 */
package ru.amse.soultakov.bags;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * �����, ����������� ���������������(���������, �������� � ������� ����� 
 * �����������). ��������� ��������� <code>Set</code>. ���������� � �������
 * ���-�������. �� ����������� ������������ ������� ������������. 
 * @author Soultakov Maxim
 */
public class Bag<E> extends AbstractCollection<E> {

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    private Map<E, List<E>> map;
    
    private int size = 0;
    
    /**
     * �����������, ��������� ����� ������ ���������������. 
     */
    public Bag() {
        map = new HashMap<E,List<E>>();
    }
    
    /**
     * �����������, ��������� ���������������, ���������� ��� �������� 
     * ��������� ���������
     * @param c - ���������, �������� ������� ����� ��������� � ����������������
     * @throws NullPointerException ���� ��������� ��������� ����� <code>null</code>
     */
    public Bag(Collection<? extends E> c) throws NullPointerException {
        map = new HashMap<E,List<E>>(Math.max((int) (c.size()/DEFAULT_LOAD_FACTOR) 
                + 1, DEFAULT_INITIAL_CAPACITY));
        addAll(c);
        size = c.size();
    }
    
    /**
     * �����������, ��������� ������ ��������������� � �������� �������� 
     * ������������ � �������������� 
     * @param initialCapacity - �������� ����������� ���������
     * @param loadFactor      - ������������� 
     * @throws     IllegalArgumentException ���� <code>initialCapacity</code> 
     * ������ ����, ��� ���� <code>loadFactor</code> ���������������
     */
    public Bag(int initialCapacity, float loadFactor) {
        map = new HashMap<E,List<E>>(initialCapacity, loadFactor);
    }

    /**
     * �����������, ��������� ������ ��������������� � �������� �������� 
     * ������������ 
     * @param initialCapacity - �������� ����������� ���������
     * @throws     IllegalArgumentException ���� <code>initialCapacity</code> 
     * ������ ����
     */
    public Bag(int initialCapacity) {
        map = new HashMap<E,List<E>>(initialCapacity);
    }  
  
    /**
     * ��������� ��������� ������� � ��������������� ��� ����������� �� ����, 
     * ������������ �� ��� � ��� ������ �������.
     * @param element
     * @return <tt>true</tt> � ����� ������
     */
    @Override
    public boolean add(E element) {
        List<E> list = map.get(element);
        if(list==null) {//������ ��������� ��������
            list = new LinkedList<E>();
        }
        boolean result = list.add(element);
        map.put(element, list);
        if(result) {
            size++;
        }
        return result;
    }
    /**
     * ���������� �������� �� ���������������. �������� ������������ � ������������
     * �������
     * @return �������� �� ���������������
     */
    @Override
    public Iterator<E> iterator() {
        return new BagIterator<E>(map.values().iterator());
    }

    /**
     * ���������� ���������� ��������� � ���� ���������������.
     * @return ������ ���������������
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * ������� ��������� ������� �� ���������������. ���� ���������� ��������� 
     * ������ ��������� �� <b>���</b> ��� ��������� 
     * @param o - �������, ������� ����� ������ �� ���������������, ���� �� 
     * ������������ � ��� 
     * @return <code>true</code> ���� ������ <code>o</code> ������������� � 
     * ��������� ����� - <code>false</code>
     */
    @Override
    public boolean remove(Object o) {
        List<E> list = map.remove(o);
        if(list!=null) {
            size -= list.size();
            return true;
        }
        return false;
    }
    
    /**
     * ���������� <code>true</code>, ���� ��������� ������� ������������� � 
     * ���������, ����� - <code>false</code> 
     * @param o - �������, ��� ������� � ��������� �����������
     * @return <code>true</code>, ���� ��������� ������� ������������� � 
     * ���������, ����� - <code>false</code>
     */
    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
    
    /**
     * ������� ��� �������� ���������
     */
    @Override
    public void clear() {
        map.clear();
        size = 0;
    }
    /**
     * ����� ��� ��������� ���-���� �������
     * @return ���-��� ��� �������
     */
    @Override
    public int hashCode() {
        int h = 0;
        Iterator<E> i = iterator();
        while (i.hasNext()) {
            E obj = i.next();
                if (obj != null)
                    h += obj.hashCode();
            }
        return h;
    }
    
    /**
     * ����� ��� ����������� ��������� �������� ���� <code>Bag</code>
     * @return <code>true</code>, ���� ������� ����� � <code>false</code> 
     * � �������� ������ 
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Bag))
        if (o == this) {
            return true;
        }
        Bag bag = (Bag) o;
        if (bag.size() != size()) {
            return false;
        }    
        try {
            return containsAll((Bag<E>) bag);
        } catch(ClassCastException unused)   {
            return false;
        } catch(NullPointerException unused) {
            return false;
        }
    }
    
    /**
     * ���������, �������� �� ��������������� ��� �������� ���������� 
     * ���������������
     * @param bag ���������������
     * @return <code>true</code>, ���� ��������������� �������� ��� �������� 
     * ���������� ���������������
     */
    public boolean containsAll(Bag<E> bag) {
        for(E element : bag.map.keySet()) {
            List<E> list = map.get(element);
            if((list==null)||(list.size()<bag.map.get(element).size())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * ��������� � ��������������� ������ �� ��������, ������� ������������ � 
     * ��������� ���������
     * @param c ���������, �������� �� ������� ����� ���������
     * @return <code>true</code> ���� ��������� ���� ��������
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Iterator<E> e = map.keySet().iterator();
        while (e.hasNext()) {
            E element = e.next();
            if (!c.contains(element)) {
                size -= map.get(element).size();
                e.remove();
                modified = true;
            }
        }
        return modified;
    }
    
    private class BagIterator<T> implements Iterator<T> {

        private Iterator<List<T>> listsIterator;
        private ListIterator<T> iterator;
        private final ListIterator<T> EMPTY_LIST_ITERATOR = 
            new LinkedList<T>().listIterator();
        
        public BagIterator(Iterator<List<T>> listsIterator) {
            this.listsIterator = listsIterator;
            if (listsIterator.hasNext()) {
                iterator = listsIterator.next().listIterator();
            } else {
                iterator = EMPTY_LIST_ITERATOR;
            }
        }
        
        public boolean hasNext() {
            return (listsIterator.hasNext())
            ||(iterator.hasNext());
        }
        
        public T next() {
            if(iterator.hasNext()) {
                return iterator.next();
            } else {
                iterator = listsIterator.next().listIterator();
                return iterator.next();
            }
        }

        public void remove() throws IllegalStateException {
            if(iterator==EMPTY_LIST_ITERATOR) {
                throw new IllegalStateException();
            } else {
                iterator.remove();
            }
            if(!iterator.hasNext()&&!iterator.hasPrevious()) {
                listsIterator.remove();
            }
            Bag.this.size--;
        }
        
    }
}
