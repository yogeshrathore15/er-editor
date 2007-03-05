/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.model.Comment;

/**
 * @author Soultakov Maxim
 */
public class CommentView extends BlockView {

    private static final Color BACKGROUND_COLOR = new Color(247,240,187);
    private Comment comment;

    public CommentView(Comment comment, int x, int y) {
        super(x,y);
        this.comment = comment;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Rectangle2D bounds = getContentBounds(graphics);
        graphics.setColor(CommentView.BACKGROUND_COLOR);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setSize((int) bounds.getWidth() + getInsets().right + getInsets().left, 100);
        graphics.setColor(Color.BLACK);
        drawTitle(graphics);
        drawSelection(graphics);
    }
    
    private Rectangle2D drawTitle(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        Rectangle2D bounds = getStringBounds(graphics, comment.getComment());
        graphics.drawString(comment.getComment(), getInsets().left,
                (int) (getInsets().top + bounds.getHeight()));
        return bounds;
    }

    public Comment getComment() {
        return comment;
    }

    /**
     * @return
     */
    protected Rectangle2D getContentBounds(Graphics2D graphics) {
        return getStringBounds(graphics, comment.getComment());
    }
}
