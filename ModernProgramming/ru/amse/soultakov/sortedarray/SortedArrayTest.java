package ru.amse.soultakov.sortedarray;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;
import static java.lang.Math.random;

public class SortedArrayTest {
	
    private static final int NUM_ELEMENTS = 15;
    
    private static class MyLong implements Comparable<MyLong> {

        private long value;
        
        public MyLong(long value) {
            this.value = value;
        }
        
        public int compareTo(MyLong o) {
            return (this.value<o.value ? -1 : (this.value==o.value ? 0 : 1));
        }
        
        public String toString() {
            return String.valueOf(value);
        }
        
    }
    
    private static class MyLong2 extends MyLong {

        public MyLong2(long value) {
            super(value);
        }
        
    }
    
    private static List<Long> getList() {
        ArrayList<Long> list = new ArrayList<Long>();
        for(int i = 0; i<NUM_ELEMENTS; i++) {
            list.add(round(random()*NUM_ELEMENTS));
        }
        return list;
    }
    
    private static SortedArray<MyLong2> getSortedArray() {
        SortedArray<MyLong2> list = new SortedArray<MyLong2>();
        for(int i = 0; i<NUM_ELEMENTS; i++) {
            list.add(new MyLong2(round(random()*NUM_ELEMENTS)));
        }
        return list;
    }
    
    private static void printArray(MyLong[] realArray) {
        System.out.print("Array        : [");
        for(int i = 0; i < realArray.length; i++) {
            System.out.print(realArray[i] + ((i == realArray.length - 1) ? (""):(", ")));
        }
        System.out.println("]");
    }
	
    public static void main(String[] args) {
		SortedArray<MyLong2> array = getSortedArray();
		System.out.println("Sorted array : " + array);
        
		MyLong[] realArray = array.toArray(new MyLong2[array.size()]);
		printArray(realArray);
        System.out.println("-----sortList()------");
        List<Long> list = getList();
        System.out.println("Random : " + list);
        SortedArray.<Long>sortList(list);
        System.out.println("Sorted : " + list);
	}
}
