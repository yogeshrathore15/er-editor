package ru.amse.soultakov.ereditor.io;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.Link;

public class CommentsSaver {

    private final IdManager idManager;

    private final Collection<Comment> comments;

    public CommentsSaver(final IdManager idManager,
            final Collection<Comment> comments) {
        this.idManager = idManager;
        this.comments = comments;
    }

    public Element save() {
        Element root = new Element("comments");
        for (Comment comment : comments) {
            root.addContent(getCommentElement(comment));
        }
        return root;
    }

    private Element getCommentElement(Comment comment) {
        Element element = new Element("comment");
        element.setAttribute("id", idManager.getStringId(comment));
        element.setText(comment.getComment());        
        element.addContent(getLinksElement(comment));
        return element;
    }

    private Content getLinksElement(Comment comment) {
        Element root = new Element("links");
        for(Link link : comment) {
            Element element = new Element("link");
            element.setAttribute("id", idManager.getStringId(link));
            root.addContent(element);
        }
        return root;
    }

}
