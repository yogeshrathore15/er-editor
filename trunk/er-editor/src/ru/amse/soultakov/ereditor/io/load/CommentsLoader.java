package ru.amse.soultakov.ereditor.io.load;

import static ru.amse.soultakov.ereditor.io.XmlTagConstants.ATTR_ID;
import static ru.amse.soultakov.ereditor.io.XmlTagConstants.*;

import org.jdom.Element;

import ru.amse.soultakov.ereditor.io.save.SavingIdManager;
import ru.amse.soultakov.ereditor.model.Comment;
import ru.amse.soultakov.ereditor.model.ERModel;

public class CommentsLoader {

    private LoadingIdManager loadingIdManager;

    private ERModel erModel;

    private Element commentsElement;

    public CommentsLoader(LoadingIdManager loadingIdManager, ERModel erModel,
            Element commentsElement) {
        this.loadingIdManager = loadingIdManager;
        this.erModel = erModel;
        this.commentsElement = commentsElement;
    }

    public void load() {
        for (Object element : commentsElement.getChildren(TAG_COMMENT)) {
            if (element instanceof Element) {
                erModel.addComment(loadCommentFirst((Element) element));
            }
        }
    }

    private Comment loadCommentFirst(Element element) {
        Comment comment = new Comment(element.getText());
        loadingIdManager.putObject(element.getAttributeValue(ATTR_ID), comment);
        return comment;
    }

}
