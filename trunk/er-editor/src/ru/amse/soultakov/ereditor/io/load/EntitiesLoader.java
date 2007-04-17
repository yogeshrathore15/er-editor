/**
 * 
 */
package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_DEFAULT_VALUE;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_NAME;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_NOTNULL;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_TYPE;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ATTRIBUTE;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ATTRIBUTES;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_PRIMARY_KEY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_UNIQUE;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newLinkedHashSet;

import java.util.List;
import java.util.Set;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.AbstractAttribute;
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
class EntitiesLoader {
	private LoadingIdManager loadingIdManager;

	private ERModel erModel;

	private Element entitiesElement;

	public EntitiesLoader(LoadingIdManager loadingIdManager, ERModel erModel,
			Element entitiesElement) {
		this.loadingIdManager = loadingIdManager;
		this.erModel = erModel;
		this.entitiesElement = entitiesElement;
	}

	public void load() {
		List children = entitiesElement.getChildren(TAG_ENTITY);
		for (Object element : children) {
			if (element instanceof Element) {
				erModel.addEntity(loadEntityFirst((Element) element));
			}
		}
		for (Object element : children) {
			if (element instanceof Element) {
				loadEntitySecond((Element) element);
			}
		}
	}

	private void loadEntitySecond(Element entityElement) {
		Entity entity = (Entity) loadingIdManager.getObject(entityElement
				.getAttributeValue(ATTR_ID));
		loadPrimaryKey(entity, entityElement.getChild(TAG_ATTRIBUTES));
		loadUniques(entity, entityElement.getChild(TAG_ATTRIBUTES));
	}

	private void loadUniques(Entity entity, Element child) {
		if (child != null) {
			List children = child.getChildren(TAG_UNIQUE);
			if (children != null) {
				for (Object unique : children) {
					if (unique instanceof Element) {
						Element uniqueElement = (Element) unique;
						Set<AbstractAttribute> set = newLinkedHashSet();
						for (Object attr : uniqueElement
								.getChildren(TAG_ATTRIBUTE)) {
							Element attrElement = (Element) attr;
							AbstractAttribute aa = (AbstractAttribute) loadingIdManager
									.getObject(attrElement
											.getAttributeValue(ATTR_ID));
							if (aa != null) {
								set.add(aa);
							}
						}
						entity.addToUniqueAttributes(set);
					}
				}
			}
		}
	}

	private void loadPrimaryKey(Entity entity, Element attributes) {
		if (attributes != null) {
			Element pk = attributes.getChild(TAG_PRIMARY_KEY);
			if (pk != null) {
				for (Object attr : pk.getChildren(TAG_ATTRIBUTE)) {
					if (attr instanceof Element) {
						Element attrElement = (Element) attr;
						AbstractAttribute attribute = (AbstractAttribute) loadingIdManager
								.getObject(attrElement
										.getAttributeValue(ATTR_ID));
						if (attribute != null) {
							entity.addToPrimaryKey(attribute);
						}
					}
				}
			}
		}
	}

	/**
	 * @param entityElement
	 */
	private Entity loadEntityFirst(Element entityElement) {
		Entity entity = new Entity(entityElement.getAttributeValue(ATTR_NAME));
		loadingIdManager.putObject(entityElement.getAttributeValue(ATTR_ID),
				entity);
		loadAttributes(entity, entityElement.getChild(TAG_ATTRIBUTES));
		return entity;
	}

	private void loadAttributes(Entity entity, Element attributesRoot) {
		for (Object object : attributesRoot.getChildren(TAG_ATTRIBUTE)) {
			if (object instanceof Element) {
				entity.addAttribute(loadAttribute((Element) object));
			}
		}
	}

	private Attribute loadAttribute(Element element) {
		String name = element.getAttributeValue(ATTR_NAME);
		IAttributeType type = getTypeFromString(element
				.getAttributeValue(ATTR_TYPE));
		boolean notNull = Boolean.parseBoolean(element
				.getAttributeValue(ATTR_NOTNULL));
		String defaultValue = element.getAttributeValue(ATTR_DEFAULT_VALUE);
		Attribute attribute = new Attribute(name, type, notNull, defaultValue);
		loadingIdManager.putObject(element.getAttributeValue(ATTR_ID),
				attribute);
		return attribute;
	}

	private IAttributeType getTypeFromString(String attributeValue) {
		int bracketIndex = attributeValue.indexOf('[');
		if (bracketIndex != -1) {
			SimpleAttributeType type = SimpleAttributeType
					.valueOf(attributeValue.substring(0, bracketIndex)
							.toUpperCase());
			int size = Integer.parseInt(attributeValue.substring(
					bracketIndex + 1, attributeValue.length() - 1));
			return new ArrayAttributeType(type, size);
		} else {
			return SimpleAttributeType.valueOf(attributeValue.toUpperCase());
		}
	}

}
