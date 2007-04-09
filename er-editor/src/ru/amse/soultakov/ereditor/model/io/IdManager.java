/**
 * 
 */
package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.util.Map;

/**
 * @author Soultakov Maxim
 * 
 */
public class IdManager
{
	private final Map<Object, Long> identifiers = newHashMap();

	/**
     * 
     */
	public IdManager()
	{
	}

	public Long getId(Object object)
	{
		putObject(object);
		return identifiers.get(object);
	}

	public void putObject(Object object)
	{
		if (!identifiers.containsKey(object))
		{
			identifiers.put(object, System.nanoTime());
		}
	}
}
