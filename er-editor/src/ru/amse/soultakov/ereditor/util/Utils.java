/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Utils {
    
    public static boolean hasNull(Object... arguments) {
        for(Object o : arguments) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }
    
    public static <T> LinkedHashSet<T> newLinkedHashSet() {
        return new LinkedHashSet<T>();
    }
    
    public static <T,E> HashMap<T,E> newHashMap() {
        return new HashMap<T,E>();
    }
    
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }
    
    public static <T> void printIterator(Iterator<T> iterator) {
        System.out.println("{");
        for(;iterator.hasNext();) {
            System.out.println(iterator.next());
        }
        System.out.println("}");
    }
    
}
