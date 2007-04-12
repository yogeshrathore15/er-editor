/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;

import ru.amse.soultakov.ereditor.model.Link;

public class LinkView extends Line {

    private Link link;

    public LinkView(Diagram diagram, Link link) {
        super(diagram);
        this.link = link;
        recalculateEndPoints();
    }

    public void paint(Graphics2D graphics) {
        recalculateEndPoints();
        graphics.setStroke(SMALL_DASHED);
        graphics.setColor(isSelected() ? Color.BLUE : Color.BLACK);
        graphics.drawLine(firstCenterX, firstCenterY, secondCenterX, secondCenterY);
        graphics.setStroke(SIMPLE_STROKE);
    }

    protected void recalculateEndPoints() {
        Block entityView = getEntityView();
        CommentView commentView = getCommentView();
        firstCenterX = getXCenter(entityView, 2);
        firstCenterY = getYCenter(entityView, 3);
        secondCenterX = getXCenter(commentView, 2);
        secondCenterY = getYCenter(commentView, 3);
    }

    public CommentView getCommentView() {
        return diagram.getCommentView(link.getComment());
    }

    public Link getLink() {
        return link;
    }

    public Block getEntityView() {
        return diagram.getEntityView(link.getEntity());
    }

    public <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data) {
        return visitor.visit(this, data);
    }

}
