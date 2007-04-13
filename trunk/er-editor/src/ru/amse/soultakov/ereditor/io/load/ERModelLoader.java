package ru.amse.soultakov.ereditor.io.load;

import org.jdom.Element;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;
import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;

public class ERModelLoader {

    private final IdManager idManager;
    
    private final Element erModelElement;
    
    private ERModel erModel;

    public ERModelLoader(IdManager idManager, Element erModelElement) {
        this.idManager = idManager;
        this.erModelElement = erModelElement;
    }
    
    public ERModel load() {
    	erModel = new ERModel();
    	loadEntities(erModelElement.getChild(TAG_ENTITIES));    	
    	return erModel;
    }

	/**
	 * @param entities
	 */
	private void loadEntities(Element entities)
	{
		for(Object object : entities.getChildren()) {
			if (object instanceof Element) {
				Element element = (Element) object;
				if (TAG_ENTITY.equals(element.getName())) {
					loadEntity(element);
				}
			}
		}
	}

	/**
	 * @param element
	 */
	private void loadEntity(Element element)
	{
		Entity entity = new Entity(element.getAttributeValue(ATTR_NAME));
		idManager.putId(entity, element.getAttributeValue(ATTR_ID));
	}
    
}
