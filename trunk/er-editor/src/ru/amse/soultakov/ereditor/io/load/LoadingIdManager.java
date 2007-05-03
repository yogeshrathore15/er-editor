/**
 * 
 */
package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.util.Map;

/**
 * @author Soultakov Maxim
 * 
 */
public class LoadingIdManager {
    private final Map<String, Object> identifiers = newHashMap();

    public LoadingIdManager() {
        //
    }

    public Object getObject(String id) {
        return identifiers.get(id);
    }

    public Object putObject(String id, Object object) {
        return identifiers.put(id, object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return identifiers.toString();
    }

}
