package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;
import java.util.Set;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.FKAttribute;

/**
 * @author Soultakov Maxim
 * 
 */
public class EntitiesSaver {
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
        Element root = new Element("entities");
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
        Element element = new Element("entity");
        element.setAttribute("name", entity.getName());
        element.setAttribute("id", idManager.getStringId(entity));
        element.addContent(getAttributesElement(entity));
        element.addContent(getPkElement(entity));
        element.addContent(getUniqueElements(entity));
        element.addContent(getFkElements(entity));
        return element;
    }

    private Collection<Element> getFkElements(Entity entity) {
        Collection<Element> elements = newLinkedHashSet();
        for (Constraint<FKAttribute> col : entity.getForeignKey()) {
            Element uniqueRoot = new Element("foreign_key");
            for (AbstractAttribute aa : col) {
                uniqueRoot.addContent(new Element("attribute").setAttribute("id",
                        idManager.getStringId(aa)));
            }
            elements.add(uniqueRoot);
        }
        return elements;
    }

    private Collection<Element> getUniqueElements(Entity entity) {
        Collection<Element> elements = newLinkedHashSet();
        for (Constraint<AbstractAttribute> col : entity.getUniqueAttributes()) {
            Element uniqueRoot = new Element("unique");
            for (AbstractAttribute aa : col) {
                uniqueRoot.addContent(new Element("attribute").setAttribute("id",
                        idManager.getStringId(aa)));
            }
            elements.add(uniqueRoot);
        }
        return elements;
    }

    private Content getPkElement(Entity entity) {
        Element root = new Element("primary_key");
        for (AbstractAttribute aa : entity.getPrimaryKey()) {
            Element element = new Element("attribute");
            element.setAttribute("id", idManager.getStringId(aa));
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
        for (AbstractAttribute aa : entity.getAttributes()) {
            if (aa instanceof FKAttribute) {
                FKAttribute fka = (FKAttribute) aa;
                Element element = new Element("fkattribute");
                element.setAttribute("id", idManager.getStringId(fka));
                element.setAttribute("name", fka.getName());
                element.setAttribute("type", fka.getType().getName());
                element.setAttribute("not_null", String.valueOf(fka.isNotNull()));
                element.setAttribute("default_value", fka.getDefaultValue());
                element.setAttribute("foreign", idManager.getStringId(fka
                        .getAttribute()));
                element.setAttribute("foreign_entity", idManager.getStringId(fka
                        .getEntity()));
                root.addContent(element);
            } else if (aa instanceof Attribute) {
                Attribute a = (Attribute) aa;
                Element element = new Element("attribute");
                element.setAttribute("id", idManager.getStringId(a));
                element.setAttribute("name", a.getName());
                element.setAttribute("type", a.getType().getName());
                element.setAttribute("not_null", String.valueOf(a.isNotNull()));
                element.setAttribute("default_value", a.getDefaultValue());
                root.addContent(element);
            }
        }
        return root;
    }

}
