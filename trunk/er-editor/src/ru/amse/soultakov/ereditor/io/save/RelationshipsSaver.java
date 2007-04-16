package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_CONSTRAINT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_MULTIPLICITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_NAME;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_FK_RELATIONSHIP_END;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_PK_RELATIONSHIP_END;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIP;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIPS;
import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.FKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.PKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;

class RelationshipsSaver {

	private final SavingIdManager savingIdManager;

    private final Collection<Relationship> relationships;

    public RelationshipsSaver(final SavingIdManager savingIdManager,
            final Collection<Relationship> relationships) {
        this.savingIdManager = savingIdManager;
        this.relationships = relationships;
    }

    public Element save() {
        Element root = new Element(TAG_RELATIONSHIPS);
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
        Element root = new Element(TAG_RELATIONSHIP);
        root.setAttribute(ATTR_ID, savingIdManager.getId(r));
        root.addContent(getRelationshipEndElement(r.getFirstEnd()));
        root.addContent(getRelationshipEndElement(r.getSecondEnd()));
        return root;
    }

    private Content getRelationshipEndElement(RelationshipEnd end) {
        Element root = null;
        if (end instanceof FKRelationshipEnd) {
            root = new Element(TAG_FK_RELATIONSHIP_END);
        } else if (end instanceof PKRelationshipEnd) {
            root = new Element(TAG_PK_RELATIONSHIP_END);
        }
        root.setAttribute(ATTR_NAME, end.getName());
        root.setAttribute(ATTR_ENTITY, savingIdManager.getId(end.getEntity()));
        root.setAttribute(ATTR_MULTIPLICITY, end.getMultiplicity().name());
        root.setAttribute(ATTR_CONSTRAINT, savingIdManager.getId(end.getConstraint()));
        return root;
    }

}
