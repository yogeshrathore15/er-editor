/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

/**
 * @author Soultakov Maxim
 */
public class RelationshipView extends Line {

    private static final int END_WIDTH = 10;

    private static final int END_LENGTH = 15;

    /**
     * 
     */
    private Relationship relationship;

    /**
     * @param relationship
     * @param firstEntity
     * @param secondEntity
     */
    public RelationshipView(Diagram diagram, Relationship relationship) {
        super(diagram);
        this.relationship = relationship;
    }

    /**
     * @param graphics
     */
    public void paint(Graphics2D graphics) {
        graphics.setColor(isSelected() ? Color.BLUE : Color.BLACK);

        EntityView fe = getFirstEntityView();
        EntityView se = getSecondEntityView();

        int firstY = fe.getY() + fe.getHeight() / 2;
        int secondY = se.getY() + se.getHeight() / 2;
        if ((fe.getX() <= se.getX() + se.getWidth())
                && (fe.getX() >= se.getX())) {
            draw3(graphics, firstY, secondY);
        } else if ((se.getX() >= fe.getX())
                && (se.getX() <= fe.getX() + fe.getWidth())) {
            draw4(graphics, firstY, secondY);
        } else if (fe.getX() > se.getX()) {
            draw1(graphics, firstY, secondY);
        } else {
            draw2(graphics, firstY, secondY);
        }
    }

    private void draw4(Graphics2D graphics, int firstY, int secondY) {
    	System.out.println("RelationshipView.draw4()");
        int firstX = getFirstEntityView().getX() + getFirstEntityView().getWidth();
        int secondX = getSecondEntityView().getX() + getFirstEntityView().getWidth();
        Point firstPoint = paintRelationshipEnd(graphics, firstX + END_LENGTH,
                firstY, firstX, firstY,
                relationship.getFirstEnd().getMultiplicity(), true);
        Point secondPoint = paintRelationshipEnd(graphics, secondX, secondY, secondX
                + END_LENGTH, secondY,
                relationship.getSecondEnd().getMultiplicity(), false);
        drawMainLine(graphics, firstPoint, secondPoint);
    }

    private void draw3(Graphics2D graphics, int firstY, int secondY) {
        int firstX = getFirstEntityView().getX();
        int secondX = getSecondEntityView().getX();
        Point firstPoint = paintRelationshipEnd(graphics, firstX - END_LENGTH,
                firstY, firstX, firstY,
                relationship.getFirstEnd().getMultiplicity(), true);
        Point secondPoint = paintRelationshipEnd(graphics, secondX, secondY, secondX
                - END_LENGTH, secondY,
                relationship.getSecondEnd().getMultiplicity(), false);
        drawMainLine(graphics, firstPoint, secondPoint);
    }

    private void draw2(Graphics2D graphics, int firstY, int secondY) {
        int firstX = getFirstEntityView().getX() + getFirstEntityView().getWidth();
        int secondX = getSecondEntityView().getX();
        Point firstPoint = paintRelationshipEnd(graphics, firstX, firstY, firstX
                + END_LENGTH, firstY, relationship.getFirstEnd().getMultiplicity(),
                false);
        Point secondPoint = paintRelationshipEnd(graphics, secondX - END_LENGTH,
                secondY, secondX, secondY, relationship.getSecondEnd()
                        .getMultiplicity(), true);
        drawMainLine(graphics, firstPoint, secondPoint);
    }

    private void draw1(Graphics2D graphics, int firstY, int secondY) {
        int firstX = getFirstEntityView().getX();
        int secondX = getSecondEntityView().getX()
                + getSecondEntityView().getWidth();
        Point firstPoint = paintRelationshipEnd(graphics, firstX - END_LENGTH,
                firstY, firstX, firstY,
                relationship.getFirstEnd().getMultiplicity(), true);
        Point secondPoint = paintRelationshipEnd(graphics, secondX, secondY, secondX
                + END_LENGTH, secondY,
                relationship.getSecondEnd().getMultiplicity(), false);
        drawMainLine(graphics, firstPoint, secondPoint);
    }

    private void drawMainLine(Graphics2D graphics, Point firstPoint,
            Point secondPoint) {
        int xCenter = Math.abs(firstPoint.x + secondPoint.x) / 2;
        int yCenter = Math.abs(firstPoint.y + secondPoint.y) / 2;
        drawLine(graphics, firstPoint.x, firstPoint.y, xCenter, yCenter,
                relationship.getSecondEnd().getMultiplicity());
        drawLine(graphics, secondPoint.x, secondPoint.y, xCenter, yCenter,
                relationship.getSecondEnd().getMultiplicity());
    }

    private Point paintRelationshipEnd(Graphics2D graphics2D, int x1, int y1,
            int x2, int y2, RelationshipMultiplicity mult, boolean left2Right) {
        graphics2D.drawLine(x1, y1, x2, y2);
        if (left2Right) {
            if (mult.isPlural()) {
                graphics2D.drawLine(x1, y1, x2, y2 - END_WIDTH);
                graphics2D.drawLine(x1, y1, x2, y2 + END_WIDTH);
            }
            return new Point(x1, y1);
        } else {
            if (mult.isPlural()) {
                graphics2D.drawLine(x1, y1 - END_WIDTH, x2, y2);
                graphics2D.drawLine(x1, y1 + END_WIDTH, x2, y2);
            }
            return new Point(x2, y2);
        }
    }

    private void drawLine(Graphics2D graphics2D, int x1, int y1, int x2, int y2,
            RelationshipMultiplicity mult) {
        Stroke old = graphics2D.getStroke();
        graphics2D.setStroke(mult.isObligatory() ? SIMPLE_STROKE : DASHED);
        graphics2D.drawLine(x1, y1, x2, y2);
        graphics2D.setStroke(old);
    }

    /**
     * @return the firstEntity
     */
    public EntityView getFirstEntityView() {
        return diagram.getEntityView(relationship.getFirstEnd().getEntity());
    }

    public EntityView getSecondEntityView() {
        return diagram.getEntityView(relationship.getSecondEnd().getEntity());
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

    public <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data) {
        return visitor.visit(this, data);
    }

}
