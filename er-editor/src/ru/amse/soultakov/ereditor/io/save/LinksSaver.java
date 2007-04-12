package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_COMMENT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINK;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINKS;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.Link;
class LinksSaver {

	private final IdManager idManager;

    private final Collection<Link> links;

    public LinksSaver(IdManager idManager, Collection<Link> links) {
        this.idManager = idManager;
        this.links = links;
    }

    public Content save() {
        Element root = new Element(TAG_LINKS);
        for (Link link : links) {
            root.addContent(getLinkElement(link));
        }
        return root;
    }

    private Content getLinkElement(Link link) {
        Element root = new Element(TAG_LINK);
        root.setAttribute(ATTR_ID, idManager.getId(link));
        root.setAttribute(ATTR_ENTITY, idManager.getId(link.getEntity()));
        root.setAttribute(ATTR_COMMENT, idManager.getId(link.getComment()));
        return root;
    }

}
