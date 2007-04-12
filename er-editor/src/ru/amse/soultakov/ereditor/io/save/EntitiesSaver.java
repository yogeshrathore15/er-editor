package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

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
        element.setAttribute("id", idManager.getId(entity));
        element.setAttribute("name", entity.getName());
        element.addContent(getAttributesElement(entity));
        element.addContent(getRelationshipsElement(entity));
        element.addContent(getLinksElement(entity));
        return element;
    }

    private Content getLinksElement(Entity entity) {
        Element root = new Element("links");
        for (Link link : entity.getLinks()) {
            Element element = new Element("link");
            element.setAttribute("id", idManager.getId(link));
            root.addContent(element);
        }
        return root;
    }

    private Element getRelationshipsElement(Entity entity) {
        Element root = new Element("relationships");
        for (Relationship r : entity.getRelationships()) {
            Element element = new Element("relationship");
            element.setAttribute("id", idManager.getId(r));
            root.addContent(element);
        }
        return root;
    }

    private Collection<Element> getFkElements(Entity entity) {
        Collection<Element> elements = newLinkedHashSet();
        for (Constraint<FKAttribute> col : entity.getForeignKey()) {
            Element fkRoot = new Element("foreign_key");
            fkRoot.setAttribute("id", idManager.getId(fkRoot));
            for (AbstractAttribute aa : col) {
                fkRoot.addContent(new Element("attribute").setAttribute("id",
                        idManager.getId(aa)));
            }
            elements.add(fkRoot);
        }
        return elements;
    }

    private Collection<Element> getUniqueElements(Entity entity) {
        Collection<Element> elements = newLinkedHashSet();
        for (Constraint<AbstractAttribute> col : entity.getUniqueAttributes()) {
            Element uniqueRoot = new Element("unique");
            uniqueRoot.setAttribute("id", idManager.getId(uniqueRoot));
            for (AbstractAttribute aa : col) {
                uniqueRoot.addContent(new Element("attribute").setAttribute("id",
                        idManager.getId(aa)));
            }
            elements.add(uniqueRoot);
        }
        return elements;
    }

    private Content getPkElement(Entity entity) {
        Element root = new Element("primary_key");
        root.setAttribute("id", idManager.getId(entity.getPrimaryKey()));
        for (AbstractAttribute aa : entity.getPrimaryKey()) {
            Element element = new Element("attribute");
            element.setAttribute("id", idManager.getId(aa));
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
                element.setAttribute("id", idManager.getId(fka));
                element.setAttribute("name", fka.getName());
                element.setAttribute("type", fka.getType().getName());
                element.setAttribute("not_null", String.valueOf(fka.isNotNull()));
                element.setAttribute("default_value", fka.getDefaultValue());
                element.setAttribute("foreign", idManager.getId(fka
                        .getAttribute()));
                element.setAttribute("foreign_entity", idManager.getId(fka
                        .getEntity()));
                root.addContent(element);
            } else if (aa instanceof Attribute) {
                Attribute a = (Attribute) aa;
                Element element = new Element("attribute");
                element.setAttribute("id", idManager.getId(a));
                element.setAttribute("name", a.getName());
                element.setAttribute("type", a.getType().getName());
                element.setAttribute("not_null", String.valueOf(a.isNotNull()));
                element.setAttribute("default_value", a.getDefaultValue());
                root.addContent(element);
            }
        }
        root.addContent(getPkElement(entity));
        root.addContent(getUniqueElements(entity));
        root.addContent(getFkElements(entity));
        return root;
    }

}
