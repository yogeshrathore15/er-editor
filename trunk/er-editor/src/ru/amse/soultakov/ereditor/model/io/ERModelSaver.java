package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;
import java.util.HashMap;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author Soultakov Maxim
 *
 */
public class ERModelSaver {

    private final ERModel erModel;
    
    private final HashMap<Object, Integer> identifiers;

    public ERModelSaver(ERModel erModel, HashMap<Object, Integer> identifiers) {
        this.erModel = erModel;
        this.identifiers = identifiers;
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
        Element element = new Element("entity");
        element.setAttribute("name", entity.getName());
        return element;
    }

    public ERModel getERModel() {
        return this.erModel;
    }

}
