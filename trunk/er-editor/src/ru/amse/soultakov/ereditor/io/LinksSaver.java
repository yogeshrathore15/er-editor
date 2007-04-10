package ru.amse.soultakov.ereditor.io;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.Link;

public class LinksSaver {
    private final IdManager idManager;

    private final Collection<Link> links;

    public LinksSaver(IdManager idManager, Collection<Link> links) {
        this.idManager = idManager;
        this.links = links;
    }

    public Content save() {
        Element root = new Element("links");
        for (Link link : links) {
            root.addContent(getLinkElement(link));
        }
        return root;
    }

    private Content getLinkElement(Link link) {
        Element root = new Element("link");
        root.setAttribute("id", idManager.getStringId(link));
        root.setAttribute("entity", idManager.getStringId(link.getEntity()));
        root.setAttribute("comment", idManager.getStringId(link.getComment()));
        return root;
    }

}
