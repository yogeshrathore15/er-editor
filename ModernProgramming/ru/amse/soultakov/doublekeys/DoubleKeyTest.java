/*
 * Created on 05.10.2006
 */
package ru.amse.soultakov.doublekeys;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Пример использования класса <code>DoubleKey</code> 
 * @author Soultakov Maxim
 */
public class DoubleKeyTest {

    private static final int MAX_KEYS = 10;
    
    /**
     * @param args - параметры командной строки
     */  
    public static void main(String[] args) {
        HashMap<DoubleKey, String> hashMap = new HashMap<DoubleKey, String>();
        
        ArrayList<DoubleKey> keys = new ArrayList<DoubleKey>();
        for(int i = 0; i<MAX_KEYS; i++) {
            keys.add(new DoubleKey(new Object(), new Object()));
            hashMap.put(keys.get(i), "Value "+String.valueOf(i));
        }
        
        System.out.println(hashMap.get(keys.get(1)));
        System.out.println(hashMap.get(keys.get(5)));
        System.out.println(hashMap.get(keys.get(8)));
    }

}
