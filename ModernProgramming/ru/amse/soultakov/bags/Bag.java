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
 * Класс, реализующий мультимножество(множество, элементы в котором могут 
 * повторяться). Реализует интерфейс <code>Set</code>. Реализован с помощью
 * хэш-таблицы. Не гарантирует определенный порядок итерирования. 
 * @author Soultakov Maxim
 */
public class Bag<E> extends AbstractCollection<E> {

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    
    private Map<E, List<E>> map;
    
    private int size = 0;
    
    /**
     * Конструктор, создающий новое пустое мультимножество. 
     */
    public Bag() {
        map = new HashMap<E,List<E>>();
    }
    
    /**
     * Конструктор, создающий мультимножество, содержащее все элементы 
     * указанной коллекции
     * @param c - коллекция, элементы которой будут добавлены в мультимножествоы
     * @throws NullPointerException если указанная коллекция равна <code>null</code>
     */
    public Bag(Collection<? extends E> c) throws NullPointerException {
        map = new HashMap<E,List<E>>(Math.max((int) (c.size()/DEFAULT_LOAD_FACTOR) 
                + 1, DEFAULT_INITIAL_CAPACITY));
        addAll(c);
        size = c.size();
    }
    
    /**
     * Конструктор, создающий пустое мультимножество с заданной исходной 
     * размерностью и загруженностью 
     * @param initialCapacity - исходная размерность множества
     * @param loadFactor      - загруженность 
     * @throws     IllegalArgumentException если <code>initialCapacity</code> 
     * меньше нуля, или если <code>loadFactor</code> неположительный
     */
    public Bag(int initialCapacity, float loadFactor) {
        map = new HashMap<E,List<E>>(initialCapacity, loadFactor);
    }

    /**
     * Конструктор, создающий пустое мультимножество с заданной исходной 
     * размерностью 
     * @param initialCapacity - исходная размерность множества
     * @throws     IllegalArgumentException если <code>initialCapacity</code> 
     * меньше нуля
     */
    public Bag(int initialCapacity) {
        map = new HashMap<E,List<E>>(initialCapacity);
    }  
  
    /**
     * Добавляет указанный элемент в мультимножество вне зависимости от того, 
     * присутствует ли уже в нем равный элемент.
     * @param element
     * @return <tt>true</tt> в любом случае
     */
    @Override
    public boolean add(E element) {
        List<E> list = map.get(element);
        if(list==null) {//первое вхождение элемента
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
     * Возвращает итератор по мультимножеству. Элементы возвращаются в произвольном
     * порядке
     * @return итератор по мультимножеству
     */
    @Override
    public Iterator<E> iterator() {
        return new BagIterator<E>(map.values().iterator());
    }

    /**
     * Возвращает количество элементов в этом мультимножестве.
     * @return размер мультимножества
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * Удаляет указанный элемент из мультимножества. Если существует несколько 
     * равных элементов то <b>все</b> они удаляются 
     * @param o - элемент, который будет удален из мультимножества, если он 
     * присутствует в нем 
     * @return <code>true</code> если объект <code>o</code> присутствовал в 
     * множестве иначе - <code>false</code>
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
     * Возвращает <code>true</code>, если указанный элемент присутствовал в 
     * множестве, иначе - <code>false</code> 
     * @param o - элемент, чье наличие в коллекции проверяется
     * @return <code>true</code>, если указанный элемент присутствовал в 
     * множестве, иначе - <code>false</code>
     */
    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }
    
    /**
     * Удаляет все элементы множества
     */
    @Override
    public void clear() {
        map.clear();
        size = 0;
    }
    /**
     * Метод для получения хэш-кода объекта
     * @return хэш-код для объекта
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
     * Метод для опредедения равенства объектов типа <code>Bag</code>
     * @return <code>true</code>, если объекты равны и <code>false</code> 
     * в обратном случае 
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
     * Проверяет, содержит ли мультимножество все элементы указанного 
     * мультимножества
     * @param bag мультимножество
     * @return <code>true</code>, если мультимножество содержит все элементы 
     * указанного мультимножества
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
     * Сохраняет в мультимножестве только те элементы, которые присутствуют в 
     * указанной коллекции
     * @param c коллекция, элементы из которой будут сохранены
     * @return <code>true</code> если коллекция была изменена
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
