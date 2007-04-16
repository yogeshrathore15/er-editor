package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_CONSTRAINT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_MULTIPLICITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_NAME;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_PK_RELATIONSHIP_END;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_RELATIONSHIP;

import java.util.List;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.PKRelationshipEnd;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

public class RelationshipsLoader {

	private LoadingIdManager loadingIdManager;

	private ERModel erModel;

	private Element relationshipsElement;

	public RelationshipsLoader(LoadingIdManager loadingIdManager,
			ERModel erModel, Element entitiesElement) {
		this.loadingIdManager = loadingIdManager;
		this.erModel = erModel;
		this.relationshipsElement = entitiesElement;
	}

	public void load() {
		for (Object element : relationshipsElement
				.getChildren(TAG_RELATIONSHIP)) {
			if (element instanceof Element) {
				erModel
						.addRelationship(loadRelationshipFirst((Element) element));
			}
		}
	}

	private Relationship loadRelationshipFirst(Element element) {
		List list = element.getChildren(TAG_PK_RELATIONSHIP_END);
		Relationship r = new Relationship(loadRelationshipEnd((Element) list
				.get(0)), loadRelationshipEnd((Element) list.get(1)));
		return r;
	}

	/**
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private RelationshipEnd loadRelationshipEnd(Element element) {
		String name = element.getAttributeValue(ATTR_NAME);
		Entity entity = (Entity) loadingIdManager.getObject(element
				.getAttributeValue(ATTR_ENTITY));
		RelationshipMultiplicity rm = RelationshipMultiplicity.valueOf(element
				.getAttributeValue(ATTR_MULTIPLICITY));
		PKRelationshipEnd end = new PKRelationshipEnd(entity, rm, name);
		return end;
	}
}
