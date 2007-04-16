package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_PK_RELATIONSHIP_END;

import java.util.List;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.save.SavingIdManager;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Relationship;

public class RelationshipsLoader {

    private SavingIdManager savingIdManager;

    private ERModel erModel;

    private Element relationshipsElement;

    public RelationshipsLoader(SavingIdManager savingIdManager, ERModel erModel, Element entitiesElement) {
        this.savingIdManager = savingIdManager;
        this.erModel = erModel;
        this.relationshipsElement = entitiesElement;
    }
    
    public void loadFirst() {
        for (Object element : relationshipsElement.getChildren(TAG_ENTITY)) {
            if (element instanceof Element) {
                erModel.addRelationship(loadRelationshipFirst((Element) element));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Relationship loadRelationshipFirst(Element element) {
        List<Element> pkEnds = element.getChildren();
        
        return null;
    }
}
