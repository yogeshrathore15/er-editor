/*
 * Created on 05.10.2006
 */
package ru.amse.soultakov.bags;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BagTest {

    private static final int ELEMENTS_COUNT = 5;
    
    private static Bag<Integer> bag = new Bag<Integer>();
   
    private static void initBag() {
        for(int i = 0; i<ELEMENTS_COUNT; i++) {
            bag.add(i);
        }
        //Добавление повторяющихся элементов
        bag.add(1);
        bag.add(1);
        bag.add(0);
        bag.add(0);
        bag.add(null);
    }
    
    private static void showBag(String title) {
        System.out.println(title);
        for(Integer i : bag) {
            System.out.println(i);
        }
    }
    
    private static void addAllCollectionToBag() {
        List<Integer> list = new ArrayList<Integer>(ELEMENTS_COUNT);
        for(int i = ELEMENTS_COUNT; i < ELEMENTS_COUNT*2; i++) {
            list.add(i);
        }
        bag.addAll(list);
        showBag("-----После добавления коллекции-----");
        
        bag.retainAll(list);
        showBag("-----После retainAll()-----");
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {              
        initBag();
        showBag("-----После инициализации-----");
       
        bag.remove(1);
        bag.remove(4);
        bag.remove("There is no such element in the bag");
        showBag("-----После удаления-----");  
        
        addAllCollectionToBag();

        
        
        System.out.println("-----Использование метода toArray-----");
        Integer[] numbers = bag.toArray(new Integer[bag.size()]);
        for(Integer i : numbers) {
            System.out.println(i);
        }
        
        System.out.println();
        System.out.println("Equals = " + bag.equals(bag)); 
    }
}
