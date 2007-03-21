/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.model.Comment;

/**
 * @author Soultakov Maxim
 */
public class CommentView extends Block {

    private static final Color BACKGROUND_COLOR = new Color(247, 240, 187);

    private Comment comment;

    public CommentView(Comment comment, int x, int y) {
        super(x, y);
        this.comment = comment;
    }

    public void paint(Graphics2D graphics) {
        Rectangle2D bounds = getContentBounds(graphics);
        //$ANALYSIS-IGNORE,codereview.java.rules.casting.RuleCastingPrimitives
        setSize((int)bounds.getWidth(),100);
        
        graphics.setColor(CommentView.BACKGROUND_COLOR);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
        graphics.setColor(Color.BLACK);
        drawBorder(graphics);
        drawTitle(graphics);
        drawSelection(graphics);
    }

    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        Rectangle2D bounds = getStringBounds(graphics, comment.getComment());
        //$ANALYSIS-IGNORE,codereview.java.rules.casting.RuleCastingPrimitives
        graphics.drawString(comment.getComment(),getX(),getY() + (int)bounds.getHeight());
        
        return bounds;
    }

    public Comment getComment() {
        return comment;
    }

    public boolean acceptLinkWith(EntityView entityView) {
        return comment.acceptLinkWith(entityView.getEntity());
    }

    /**
     * @return
     */
    @Override
    protected Rectangle2D getContentBounds(Graphics2D graphics) {
        return getStringBounds(graphics, comment.getComment());
    }

    public <R, D> R acceptVisitor(Visitor<R, D> visitor, D data) {
        return visitor.visit(this, data);
    }

}
