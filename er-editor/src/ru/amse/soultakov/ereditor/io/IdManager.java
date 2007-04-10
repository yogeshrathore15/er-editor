/**
 * 
 */
package ru.amse.soultakov.ereditor.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.util.Map;

/**
 * @author Soultakov Maxim
 * 
 */
public class IdManager {
    private final Map<Object, Long> identifiers = newHashMap();

    /**
     * 
     */
    public IdManager() {
    }

    public Long getId(Object object) {
        if (!identifiers.containsKey(object)) {
            identifiers.put(object, System.nanoTime());
        }
        return identifiers.get(object);
    }

    public String getStringId(Object object) {
        return String.valueOf(getId(object));
    }
}
