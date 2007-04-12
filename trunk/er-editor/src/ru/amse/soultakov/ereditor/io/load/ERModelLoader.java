package ru.amse.soultakov.ereditor.io.load;

import org.jdom.Element;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;
import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.ERModel;

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
		
	}
    
}
