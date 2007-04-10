package ru.amse.soultakov.ereditor.model.io;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Relationship;

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
        
        return root;
    }

}
