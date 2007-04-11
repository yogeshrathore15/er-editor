/**
 * 
 */
package ru.amse.soultakov.ereditor.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.util.Map;

import ru.amse.soultakov.ereditor.util.AutoincrementGenerator;

/**
 * @author Soultakov Maxim
 * 
 */
public class IdManager {
    private final Map<Object, Long> identifiers = newHashMap();

    private final AutoincrementGenerator generator = new AutoincrementGenerator();
    /**
     * 
     */
    public IdManager() {
    }

    public Long getId(Object object) {
        if (!identifiers.containsKey(object)) {
            identifiers.put(object, generator.getNextNumber());
        }
        return identifiers.get(object);
    }

    public String getStringId(Object object) {
        return String.valueOf(getId(object));
    }
}
