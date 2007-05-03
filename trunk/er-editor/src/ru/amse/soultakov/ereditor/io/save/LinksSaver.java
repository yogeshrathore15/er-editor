package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_COMMENT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ENTITY;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINK;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINKS;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.Link;

class LinksSaver {

    private final SavingIdManager savingIdManager;

    private final Collection<Link> links;

    public LinksSaver(SavingIdManager savingIdManager, Collection<Link> links) {
        this.savingIdManager = savingIdManager;
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
        root.setAttribute(ATTR_ID, savingIdManager.getId(link));
        root.setAttribute(ATTR_ENTITY, savingIdManager.getId(link.getEntity()));
        root.setAttribute(ATTR_COMMENT, savingIdManager.getId(link.getComment()));
        return root;
    }

}
