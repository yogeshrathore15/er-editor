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

    private Comment comment;

    public CommentView(Comment comment, int x, int y) {
        super();
        this.comment = comment;
        setLocation(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Rectangle2D bounds = getContentBounds(graphics);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setSize((int) bounds.getWidth() + getInsets().right + getInsets().left, 100);
        int titleHeight = (int) (getInsets().top + bounds.getHeight() + MARGIN);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(getInsets().left, titleHeight, getWidth(), titleHeight);
        graphics.drawLine(getInsets().left, (titleHeight + MARGIN) * 2, getWidth()
                - getInsets().right, (titleHeight + MARGIN) * 2);
        drawSelection(graphics);
    }

    public Comment getComment() {
        return comment;
    }

    /**
     * @return
     */
    protected Rectangle2D getContentBounds(Graphics2D graphics) {
        return getStringBounds(graphics, "Comment");
    }
}
