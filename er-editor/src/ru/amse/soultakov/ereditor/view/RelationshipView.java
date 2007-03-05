/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;

import ru.amse.soultakov.ereditor.controller.Selectable;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

/**
 * @author Soultakov Maxim
 */
public class RelationshipView implements Selectable {

    private static final BasicStroke SIMPLE_STROKE = new BasicStroke(1.0f);

    private static final BasicStroke DASHED = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[] { 10.0f }, 0.0f);

    private static final double MIN_DISTANCE = 12.0;

    /**
     * 
     */
    private Relationship relationship;

    /**
     * 
     */
    private EntityView firstEntity;

    /**
     * 
     */
    private EntityView secondEntity;

    private boolean selected;

    /**
     * @param relationship
     * @param firstEntity
     * @param secondEntity
     */
    public RelationshipView(Relationship relationship, EntityView firstEntity,
            EntityView secondEntity) {
        this.relationship = relationship;
        this.firstEntity = firstEntity;
        this.secondEntity = secondEntity;
    }

    /**
     * @param graphics
     */
    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int firstCenterX = getXCenter(firstEntity, 2);
        int firstCenterY = getYCenter(firstEntity, 3);
        int secondCenterX = getXCenter(secondEntity, 2);
        int secondCenterY = getYCenter(secondEntity, 3);

        int xCenter = (firstCenterX + secondCenterX) / 2;
        int yCenter = (firstCenterY + secondCenterY) / 2;

        graphics2D.setColor(isSelected() ? Color.BLUE : Color.BLACK);
        paintRelationshipEnd(graphics2D, firstCenterX, firstCenterY, xCenter,
                yCenter, relationship.getFirstEnd().getMultiplicity());
        paintRelationshipEnd(graphics2D, secondCenterX, secondCenterY, xCenter,
                yCenter, relationship.getSecondEnd().getMultiplicity());
    }

    private static int getXCenter(JComponent component, int n) {
        return component.getX() + component.getWidth() / n;
    }

    private static int getYCenter(JComponent component, int n) {
        return component.getY() + component.getHeight() / n;
    }

    /**
     * @param graphics2D
     * @param x1
     * @param y1
     * @param xCenter
     * @param yCenter
     * @param multiplicity
     */
    private void paintRelationshipEnd(Graphics2D graphics2D, int x1, int y1,
            int xCenter, int yCenter, RelationshipMultiplicity multiplicity) {
        Stroke old = graphics2D.getStroke();
        graphics2D.setStroke(multiplicity.isObligatory() ? SIMPLE_STROKE
                : DASHED);
        graphics2D.drawLine(x1, y1, xCenter, yCenter);
        graphics2D.setStroke(old);
    }

    /**
     * @return the firstEntity
     */
    public EntityView getFirstEntityView() {
        return firstEntity;
    }

    /**
     * @param firstEntity
     *            the firstEntity to set
     */
    public void setFirstEntity(EntityView firstEntity) {
        this.firstEntity = firstEntity;
    }

    /**
     * @return the relationship
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * @param relationship
     *            the relationship to set
     */
    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    /**
     * @return the secondEntity
     */
    public EntityView getSecondEntityView() {
        return secondEntity;
    }

    /**
     * @param secondEntity
     *            the secondEntity to set
     */
    public void setSecondEntity(EntityView secondEntity) {
        this.secondEntity = secondEntity;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean trySelect(int x, int y) {
        boolean b = contains(x, y);
        if (b) {
            setSelected(true);
        }
        return b;
    }

    public boolean contains(int x, int y) {
        int firstX = getXCenter(firstEntity, 2);
        int firstY = getYCenter(firstEntity, 3);
        int secondX = getXCenter(secondEntity, 2);
        int secondY = getYCenter(secondEntity, 3);

        if (firstX == secondX) {
            if (y < Math.min(firstY, secondY) || Math.max(firstY, secondY) < y) {
                return false;
            }
            return (Math.abs(x - firstX) < MIN_DISTANCE);
        } else if (firstY == secondY) {
            if (x < Math.min(firstX, secondX) || Math.max(firstX, secondX) < x)
                return false;
            return (Math.abs(y - firstY) < MIN_DISTANCE);
        }
        if (firstX < secondX) {
            if (x < firstX || secondX < x)
                return false;
        } else {
            if (x < secondX || firstX < x)
                return false;
        }
        if (firstY < secondY) {
            if (y < firstY || secondY < y)
                return false;
        } else {
            if (y < secondY || firstY < y)
                return false;
        }

        double[] prms = getLinePrms(firstX, firstY, secondX, secondY);
        double a = prms[0];
        double b = prms[1];
        double c = prms[2];

        double c2 = -(b * x - a * y);

        double yt = (a * c2 - b * c) / (a * a + b * b);
        double xt = -(b * yt + c) / a;

        return (Math.sqrt((xt - x) * (xt - x) + (yt - y) * (yt - y)) < MIN_DISTANCE);
    }

    private static double[] getLinePrms(int x1, int y1, int x2, int y2) {
        int dy = y2 - y1;
        int dx = x2 - x1;
        double[] prms = new double[3]; // a, b, c

        if (dx == 0) {
            prms[1] = 0.0;
            prms[0] = 1.0;
        } else if (dy == 0) {
            prms[1] = 1.0;
            prms[0] = 0.0;
        } else {
            prms[1] = 1.0;
            prms[0] = prms[1] * ((double) (-dy) / (double) (dx));
        }
        prms[2] = -(prms[0] * x1 + prms[1] * y1);
        return prms;
    }

}
