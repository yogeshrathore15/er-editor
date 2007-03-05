/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics;

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

    @Override
    public void paint(Graphics g) {
        
    }

    @Override
    protected void recalculateEndPoints() {
        firstCenterX = getXCenter(entityView, 2);
        firstCenterY = getYCenter(entityView, 3);
        secondCenterX = getXCenter(commentView, 2);
        secondCenterY = getYCenter(commentView, 3);
    }

    
        
}
