/*
 * Created on 28.10.2006
 */
package ru.amse.soultakov.structures.priorityqueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriorityQueue<E extends Comparable<? super E>>{

    //для упрощения вычислений используем one-based array
    private List<E> contents = new ArrayList<E>();
    
    private Map<Integer, Integer> numbersToIndices = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> indicesToNumbers = new HashMap<Integer, Integer>();
    private int currentNumber = 0;
    
    public PriorityQueue() {
        contents.add(null);//первый элемент - фиктивный
    }
    
    public void insert(E element) {
        contents.add(element);
        System.out.println("**before inserting " + element);
        showMaps();
        updateMaps(++currentNumber, contents.size() - 1);
        System.out.println("--");
        showMaps();
        System.out.println("**after inserting " + element);
        siftUp(contents.size() - 1);
    }
    
    private void updateMaps(Integer number, Integer index) {
        numbersToIndices.put(number, index);
        indicesToNumbers.put(index, number);
    }
    
    public int size() {
        return contents.size() - 1;
    }
    
    public E extractMin() {
        currentNumber++;
        if (size() == 0) {
            return null;
        }
        updateMaps(indicesToNumbers.get(1), size() - 1);
        E result = contents.get(1);
        contents.set(1, contents.get(size()));
        contents.remove(size());
        if (size() > 1) {
            siftDown(1);
        }
        return result;  
    }
    
    public void decreaseKey(Integer number, E newValue) {
        currentNumber++;
        Integer index = numbersToIndices.get(number);
        System.out.println("**before decreasing");
        showMaps();
        contents.set(index, newValue);
        siftUp(index);
    }

    private void showMaps() {
        System.out.println("Numbers to indices :" + numbersToIndices);
        System.out.println("Indices to numbers :" + indicesToNumbers);
    }

    private int findIndex(E oldValue, int curIndex) {
        if (contents.get(curIndex) == oldValue) {
            return curIndex;
        }
        int index = -1;
        if (curIndex*2 <= size() && contents.get(curIndex*2).compareTo(oldValue) <= 0) {
            index = findIndex(oldValue, curIndex*2);
        }
        if (index == -1) { 
            if (curIndex*2 + 1 <= size() && 
                    contents.get(curIndex*2 + 1).compareTo(oldValue) <= 0) {
                index = findIndex(oldValue, curIndex*2 + 1);
            }
        }
        return index;
    }
    
    private void siftDown(int num) {
        int k = num;
        int j;
        while ((j = k << 1) <= size() && (j > 0)) {
            if ( j < size() && (contents.get(j).compareTo(contents.get(j+1)) > 0)) {
                j++; // j indexes smallest kid
            }
            if ((contents.get(k).compareTo(contents.get(j)) <= 0)) {
                break;
            }
            E tmp = contents.get(j);  
            contents.set(j, contents.get(k));
            contents.set(k, tmp);
            Integer b = indicesToNumbers.get(k);
            updateMaps(indicesToNumbers.get(j), k);
            updateMaps(b, j);
            k = j;
        }
    }

    private void siftUp(int num) {
        int k = num;
        while (k > 1) {
            int j = k >> 1;
            if (contents.get(j).compareTo(contents.get(k)) <= 0) {
                break;
            }
            E tmp = contents.get(j);  
            contents.set(j, contents.get(k)); 
            contents.set(k, tmp);
            Integer b = indicesToNumbers.get(k);
            updateMaps(indicesToNumbers.get(j), k);
            updateMaps(b, j);
            k = j;
        }
    }
    
}
