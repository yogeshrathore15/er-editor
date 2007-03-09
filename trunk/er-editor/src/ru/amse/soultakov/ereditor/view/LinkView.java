/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;

import ru.amse.soultakov.ereditor.model.Link;

public class LinkView extends Line {

    private Link link;

    private EntityView entityView;

    private CommentView commentView;

    public LinkView(Link link, EntityView entityView, CommentView commentView) {
        super();
        this.link = link;
        this.entityView = entityView;
        this.commentView = commentView;
        recalculateEndPoints();
    }

    public void paint(Graphics2D graphics) {
        recalculateEndPoints();
        graphics.setStroke(SMALL_DASHED);
        graphics.setColor(isSelected() ? Color.BLUE : Color.BLACK);
        graphics.drawLine(firstCenterX, firstCenterY, secondCenterX, secondCenterY);
        graphics.setStroke(SIMPLE_STROKE);
    }

    @Override
    protected void recalculateEndPoints() {
        firstCenterX = getXCenter(entityView, 2);
        firstCenterY = getYCenter(entityView, 3);
        secondCenterX = getXCenter(commentView, 2);
        secondCenterY = getYCenter(commentView, 3);
    }

    public Block getCommentView() {
        return commentView;
    }

    public Link getLink() {
        return link;
    }

    public Block getEntityView() {
        return entityView;
    }

}
