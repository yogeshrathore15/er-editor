/**
 * 
 */
package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.util.Map;

import ru.amse.soultakov.ereditor.util.AutoincrementGenerator;

/**
 * @author Soultakov Maxim
 * 
 */
public class SavingIdManager {
    private final Map<Object, String> identifiers = newHashMap();

    private final AutoincrementGenerator generator = new AutoincrementGenerator();
    /**
     * 
     */
    public SavingIdManager() {
    }

    public String getId(Object object) {
        if (!identifiers.containsKey(object)) {
            identifiers.put(object, String.valueOf(generator.getNextNumber()));
        }
        return identifiers.get(object);
    }
    
    public Object getObject(String id) {
        return identifiers.get(id);
    }

    public String putId(Object object, String id) {
    	return identifiers.put(object, id);
    }
    
    /** 
     * {@inheritDoc}
     */
    @Override
    public String toString() {
    	return identifiers.toString();
    }

}
