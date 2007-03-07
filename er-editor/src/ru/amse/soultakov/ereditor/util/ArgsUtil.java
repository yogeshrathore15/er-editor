/*
 * Created on 08.03.2007
 */
package ru.amse.soultakov.ereditor.util;

public class ArgsUtil {
    
    public static boolean hasNull(Object... arguments) {
        for(Object o : arguments) {
            if (o == null) {
                return true;
            }
        }
        return false;
    }
    
}
