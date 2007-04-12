package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;
import java.util.Collection;
import java.util.Set;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.FKAttribute;
import ru.amse.soultakov.ereditor.model.Link;
import ru.amse.soultakov.ereditor.model.Relationship;

/**
 * @author Soultakov Maxim
 * 
 */
class EntitiesSaver {

	private final IdManager idManager;

    private final Collection<Entity> entities;

    private final Set<Object> storedElements = newLinkedHashSet();

    /**
     * 
     */
    public EntitiesSaver(IdManager idManager, Collection<Entity> entities) {
        this.idManager = idManager;
        this.entities = newLinkedHashSet(entities);
    }

    public Element save() {
        Element root = new Element(TAG_ENTITIES);
        root.addContent(getEntityElements());
        return root;
    }

    private Collection<Element> getEntityElements() {
        Collection<Element> elements = newLinkedHashSet(entities.size());
        for (Entity entity : entities) {
            elements.add(getEntityElement(entity));
        }
        return elements;
    }

    private Element getEntityElement(Entity entity) {
        storedElements.add(entity);
        Element element = new Element(TAG_ENTITY);
        element.setAttribute(ATTR_ID, idManager.getId(entity));
        element.setAttribute(ATTR_NAME, entity.getName());
        element.addContent(getAttributesElement(entity));
        element.addContent(getRelationshipsElement(entity));
        element.addContent(getLinksElement(entity));
        return element;
    }

    private Content getLinksElement(Entity entity) {
        Element root = new Element(TAG_LINKS);
        for (Link link : entity.getLinks()) {
            Element element = new Element(TAG_LINK);
            element.setAttribute(ATTR_ID, idManager.getId(link));
            root.addContent(element);
        }
        return root;
    }

    private Element getRelationshipsElement(Entity entity) {
        Element root = new Element(TAG_RELATIONSHIPS);
        for (Relationship r : entity.getRelationships()) {
            Element element = new Element(TAG_RELATIONSHIP);
            element.setAttribute(ATTR_ID, idManager.getId(r));
            root.addContent(element);
        }
        return root;
    }

    private Collection<Element> getFkElements(Entity entity) {
        Collection<Element> elements = newLinkedHashSet();
        for (Constraint<FKAttribute> col : entity.getForeignKey()) {
            Element fkRoot = new Element(TAG_FOREIGN_KEY);
            fkRoot.setAttribute(ATTR_ID, idManager.getId(fkRoot));
            for (AbstractAttribute aa : col) {
                fkRoot.addContent(new Element(ATTR_ID).setAttribute(ATTR_ID,
                        idManager.getId(aa)));
            }
            elements.add(fkRoot);
        }
        return elements;
    }

    private Collection<Element> getUniqueElements(Entity entity) {
        Collection<Element> elements = newLinkedHashSet();
        for (Constraint<AbstractAttribute> col : entity.getUniqueAttributes()) {
            Element uniqueRoot = new Element(TAG_UNIQUE);
            uniqueRoot.setAttribute(ATTR_ID, idManager.getId(uniqueRoot));
            for (AbstractAttribute aa : col) {
                uniqueRoot.addContent(new Element(ATTR_ID).setAttribute(ATTR_ID,
                        idManager.getId(aa)));
            }
            elements.add(uniqueRoot);
        }
        return elements;
    }

    private Content getPkElement(Entity entity) {
        Element root = new Element(TAG_PRIMARY_KEY);
        root.setAttribute(ATTR_ID, idManager.getId(entity.getPrimaryKey()));
        for (AbstractAttribute aa : entity.getPrimaryKey()) {
            Element element = new Element(ATTR_ID);
            element.setAttribute(ATTR_ID, idManager.getId(aa));
            root.addContent(element);
        }
        return root;
    }

    /**
     * @param entity
     * @return
     */
    private Content getAttributesElement(Entity entity) {
        Element root = new Element("attributes");
        for (AbstractAttribute aa : entity) {
            if (aa instanceof FKAttribute) {
                FKAttribute fka = (FKAttribute) aa;
                Element element = new Element("fkattribute");
                element.setAttribute(ATTR_ID, idManager.getId(fka));
                element.setAttribute(ATTR_NAME, fka.getName());
                element.setAttribute(ATTR_TYPE, fka.getType().getName());
                element.setAttribute(ATTR_NOTNULL, String.valueOf(fka.isNotNull()));
                element.setAttribute(ATTR_DEFAULT_VALUE, fka.getDefaultValue());
                element.setAttribute(ATTR_FOREIGN, idManager.getId(fka
                        .getAttribute()));
                element.setAttribute(ATTR_FOREIGN_ENTITY, idManager.getId(fka
                        .getEntity()));
                root.addContent(element);
            } else if (aa instanceof Attribute) {
                Attribute a = (Attribute) aa;
                Element element = new Element("attribute");
                element.setAttribute(ATTR_ID, idManager.getId(a));
                element.setAttribute(ATTR_NAME, a.getName());
                element.setAttribute(ATTR_TYPE, a.getType().getName());
                element.setAttribute(ATTR_NOTNULL, String.valueOf(a.isNotNull()));
                element.setAttribute(ATTR_DEFAULT_VALUE, a.getDefaultValue());
                root.addContent(element);
            }
        }
        root.addContent(getPkElement(entity));
        root.addContent(getUniqueElements(entity));
        root.addContent(getFkElements(entity));
        return root;
    }

}
