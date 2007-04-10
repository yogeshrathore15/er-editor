package ru.amse.soultakov.ereditor.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.FKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.PKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;

public class RelationshipsSaver {

    private final IdManager idManager;

    private final Collection<Relationship> relationships;

    public RelationshipsSaver(final IdManager idManager,
            final Collection<Relationship> relationships) {
        this.idManager = idManager;
        this.relationships = relationships;
    }

    public Element save() {
        Element root = new Element("relationships");
        root.addContent(getRelationshipElements());
        return root;
    }

    private Collection<Element> getRelationshipElements() {
        Collection<Element> elements = newLinkedHashSet(relationships.size());
        for (Relationship r : relationships) {
            elements.add(getRelationshipElement(r));
        }
        return elements;
    }

    private Element getRelationshipElement(Relationship r) {
        Element root = new Element("relationship");
        root.setAttribute("id", idManager.getStringId(r));
        root.addContent(getRelationshipEndElement(r.getFirstEnd()));
        root.addContent(getRelationshipEndElement(r.getSecondEnd()));
        return root;
    }

    private Content getRelationshipEndElement(RelationshipEnd end) {
        Element root = null;
        if (end instanceof FKRelationshipEnd) {
            root = new Element("fk_relationship_end");
        } else if (end instanceof PKRelationshipEnd) {
            root = new Element("pk_relationship_end");
        }
        root.setAttribute("name", end.getName());
        root.setAttribute("entity", idManager.getStringId(end.getEntity()));
        root.setAttribute("multiplicity", end.getMultiplicity().name());
        root.setAttribute("constraint", idManager.getStringId(end.getConstraint()));
        return root;
    }

}
