/**
 * 
 */
package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.ERModel;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.Link;

/**
 * @author Soultakov Maxim
 *
 */
public class LinksLoader {

	private LoadingIdManager idManager;
	
	private ERModel erModel;
	
	private Element linksElement;

	public LinksLoader(LoadingIdManager idManager, ERModel erModel, Element linksElement) {
		this.idManager = idManager;
		this.erModel = erModel;
		this.linksElement = linksElement;
	}
	
	public void load() {
		for (Object element : linksElement.getChildren(TAG_LINK)) {
            if (element instanceof Element) {
                erModel.addLink(loadLink((Element) element));
            }
        }
	}

	private Link loadLink(Element element) {
		Entity entity = (Entity) idManager.getObject(element.getAttributeValue(ATTR_ENTITY));
		Comment comment = (Comment) idManager.getObject(element.getAttributeValue(ATTR_COMMENT));
		Link link = new Link(entity, comment);
		idManager.putObject(element.getAttributeValue(ATTR_ID), link);
		return link;
	}
	
}
