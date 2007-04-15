/**
 * 
 */
package ru.amse.soultakov.ereditor.io.load;

import java.util.List;

import org.jdom.Element;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;
import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.ArrayAttributeType;
import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.IAttributeType;
import ru.amse.soultakov.ereditor.model.SimpleAttributeType;

/**
 * @author Soultakov Maxim
 * 
 */
public class EntitiesLoader {
    private IdManager idManager;

    private ERModel erModel;

    private Element entitiesElement;
    
    public EntitiesLoader(IdManager idManager, ERModel erModel,
            Element entitiesElement) {
        this.idManager = idManager;
        this.erModel = erModel;
        this.entitiesElement = entitiesElement;
    }

    public void loadFirst() {
        List children = entitiesElement.getChildren(TAG_ENTITY);
        for (Object element : children) {
            if (element instanceof Element) {
                erModel.addEntity(loadEntityFirst((Element) element));
            }
        }
        for(Object element : children) {
            if(element instanceof Element) {
                loadEntitySecond((Element)element);
            }
        }
    }

    private void loadEntitySecond(Element entityElement) {
        Entity entity = (Entity) idManager.getObject(entityElement.getAttributeValue(ATTR_ID));
        loadForeignKey(entity, entityElement.getChildren(TAG_FOREIGN_KEY));
        loadPrimaryKey(entity, entityElement.getChild(TAG_PRIMARY_KEY));
    }

    private void loadForeignKey(Entity entity, List children) {
        for (Object object : children.getChildren(TAG_PRIMARY_KEY)) {
            
        }
    }

    private void loadPrimaryKey(Entity entity, Element child) {
        for (Object object : child.getChildren(TAG_PRIMARY_KEY)) {
            if (object instanceof Element) {
                entity.addToPrimaryKey(attribute);
            }
        }
    }
    
    /**
     * @param entityElement
     */
    private Entity loadEntityFirst(Element entityElement) {
        Entity entity = new Entity(entityElement.getAttributeValue(ATTR_NAME));
        idManager.putId(entity, entityElement.getAttributeValue(ATTR_ID));
        loadAttributes(entity, entityElement.getChild(TAG_ATTRIBUTES));
        return entity;
    }


    private void loadAttributes(Entity entity, Element attributesRoot) {
        for (Object object : attributesRoot.getChildren(TAG_ATTRIBUTE)) {
            if (object instanceof Element) {
                entity.addAttribute(loadAttribute((Element) object));
            }
        }
        for (Object object : attributesRoot.getChildren(TAG_FKATTRIBUTE)) {
            if (object instanceof Element) {
                entity.addAttribute(loadFKAttribute((Element) object));
            }
        }
    }

    private Attribute loadAttribute(Element element) {
        String name = element.getAttributeValue(ATTR_NAME);
        IAttributeType type = getTypeFromString(element.getAttributeValue(ATTR_TYPE));
        boolean notNull = Boolean.parseBoolean(element
                .getAttributeValue(ATTR_NOTNULL));
        String defaultValue = element.getAttributeValue(ATTR_DEFAULT_VALUE);
        Attribute attribute = new Attribute(name, type, notNull, defaultValue);
        idManager.putId(attribute, element.getAttributeValue(ATTR_ID));
        return attribute;
    }

    private IAttributeType getTypeFromString(String attributeValue) {
        int bracketIndex = attributeValue.indexOf('[');
        if (bracketIndex != -1) {
            SimpleAttributeType type = SimpleAttributeType.valueOf(attributeValue
                    .substring(0, bracketIndex).toUpperCase());
            int size = Integer.parseInt(attributeValue.substring(bracketIndex + 1,
                    attributeValue.length() - 1));
            return new ArrayAttributeType(type, size);
        } else {
            return SimpleAttributeType.valueOf(attributeValue.toUpperCase());
        }
    }

}
