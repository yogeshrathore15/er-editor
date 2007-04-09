/**
 * 
 */
package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newHashMap;

import java.util.Map;

import org.jdom.Element;

/**
 * @author Soultakov Maxim
 *
 */
public class EntitySaver
{
	private IdManager idManager; 
	
	/**
	 * 
	 */
	public EntitySaver(IdManager idManager)
	{
		this.idManager = idManager;
	}
	
	public Element save() {
		return null;
	}
}
