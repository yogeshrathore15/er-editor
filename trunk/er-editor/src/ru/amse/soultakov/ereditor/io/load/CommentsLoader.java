package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.IdManager;
import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.ERModel;

public class CommentsLoader {

    private IdManager idManager;

    private ERModel erModel;

    private Element commentsElement;

    public CommentsLoader(IdManager idManager, ERModel erModel,
            Element commentsElement) {
        this.idManager = idManager;
        this.erModel = erModel;
        this.commentsElement = commentsElement;
    }

    public void loadFirst() {
        for (Object element : commentsElement.getChildren(TAG_COMMENT)) {
            if (element instanceof Element) {
                erModel.addComment(loadCommentFirst((Element) element));
            }
        }
    }

    private Comment loadCommentFirst(Element element) {
        Comment comment = new Comment(element.getText());
        idManager.putId(comment, element.getAttributeValue(ATTR_ID));
        return comment;
    }

}
