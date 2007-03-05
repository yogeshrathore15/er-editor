/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.border.LineBorder;

import ru.amse.soultakov.ereditor.model.Comment;

/**
 * @author Soultakov Maxim
 */
public class CommentView extends BlockView {

    private Comment comment;

    public CommentView(Comment comment, int x, int y) {
        super();
        this.comment = comment;
        setSize(1,1);
        setOpaque(true);
        setLocation(x, y);
        setBorder(new LineBorder(Color.BLACK, 1, true));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        Rectangle2D bounds = getContentBounds(graphics);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        setSize((int) bounds.getWidth() + getInsets().right + getInsets().left, 100);
        graphics.setColor(Color.BLACK);
        drawSelection(graphics);
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
