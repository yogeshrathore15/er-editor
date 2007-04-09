package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;
import java.util.Set;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author Soultakov Maxim
 *
 */
public class ERModelSaver {

    private final ERModel erModel;
    
    private final IdManager idManager;
    
    private final Set<Object> storedElements = newLinkedHashSet();
    
    public ERModelSaver(ERModel erModel, IdManager idManager) {
        this.erModel = erModel;
        this.idManager = idManager;
    }
    
    public Element save() {
        Element element = new Element("model");
        element.addContent(getEntityElements());
        return element;
    }

    private Collection<Element> getEntityElements() {
        Collection<Entity> entities = erModel.getEntities();
        Collection<Element> elements = newLinkedHashSet(entities.size());
        for(Entity entity : entities) {
            elements.add(getEntityElement(entity));
        }
        return elements;
    }

    private Element getEntityElement(Entity entity) {
    	storedElements.add(entity);
    	idManager.putObject(entity);
        Element element = new Element("entity");
        element.setAttribute("name", entity.getName());
        element.addContent(getAttributesElement(entity));
        return element;
    }

    /**
	 * @param entity
	 * @return
	 */
	private Content getAttributesElement(Entity entity)
	{
		Element element = new Element("attributes");
		element.addContent(getPkElement());
		return element;
	}

	/**
	 * @return
	 */
	private Content getPkElement()
	{
		return null;
	}

	public ERModel getERModel() {
        return this.erModel;
    }
    
    /**
	 * @return the idManager
	 */
	public IdManager getIdManager()
	{
		return this.idManager;
	}

}
