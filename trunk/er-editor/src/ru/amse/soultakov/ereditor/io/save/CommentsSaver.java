package ru.amse.soultakov.ereditor.io.save;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENT;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_COMMENTS;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINK;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.TAG_LINKS;

import java.util.Collection;

import org.jdom.Content;
import org.jdom.Element;

import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.Link;

class CommentsSaver {

	private final SavingIdManager savingIdManager;

    private final Collection<Comment> comments;

    public CommentsSaver(final SavingIdManager savingIdManager,
            final Collection<Comment> comments) {
        this.savingIdManager = savingIdManager;
        this.comments = comments;
    }

    public Element save() {
        Element root = new Element(TAG_COMMENTS);
        for (Comment comment : comments) {
            root.addContent(getCommentElement(comment));
        }
        return root;
    }

    private Element getCommentElement(Comment comment) {
        Element element = new Element(TAG_COMMENT);
        element.setAttribute(ATTR_ID, savingIdManager.getId(comment));
        element.setText(comment.getComment());
        element.addContent(getLinksElement(comment));
        return element;
    }

    private Content getLinksElement(Comment comment) {
        Element root = new Element(TAG_LINKS);
        for (Link link : comment) {
            Element element = new Element(TAG_LINK);
            element.setAttribute(ATTR_ID, savingIdManager.getId(link));
            root.addContent(element);
        }
        return root;
    }

}
