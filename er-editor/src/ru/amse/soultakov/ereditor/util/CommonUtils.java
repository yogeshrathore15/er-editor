/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.util;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import ru.amse.soultakov.ereditor.view.Viewable;

public class CommonUtils {

    public static boolean hasNull(Object... arguments) {
        for (Object o : arguments) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }

    public static <T> LinkedHashSet<T> newLinkedHashSet() {
        return new LinkedHashSet<T>();
    }
    
    public static <T> LinkedHashSet<T> newLinkedHashSet(Set<T> set) {
        return new LinkedHashSet<T>(set);
    }    

    public static <T, E> HashMap<T, E> newHashMap() {
        return new HashMap<T, E>();
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> void printIterator(Iterator<T> iterator) {
        System.out.println("{");
        for (; iterator.hasNext();) {
            System.out.println(iterator.next());
        }
        System.out.println("}");
    }
    
    public static Point getRightBottomPoint(Set<? extends Viewable> set) {
        return getRightBottomPoint(set, new Point(0,0));
    }
    
    public static Point getRightBottomPoint(Set<? extends Viewable> set, Point currentMax) {
        for(Viewable v : set) {
            if (v.getX() + v.getWidth() > currentMax.x) {
                currentMax.x = v.getX() + v.getWidth();
            }
            if (v.getY() + v.getHeight() > currentMax.y) {
                currentMax.y = v.getY() + v.getHeight();
            }
        }
        return currentMax;
    }

}
