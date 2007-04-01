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
        Point firstPoint = null;
        Point secondPoint = null;
        
        EntityView firstEntity = getFirstEntityView();
        EntityView secondEntity = getSecondEntityView();
        
        int firstY = firstEntity.getY() + firstEntity.getHeight() / 2;
        int secondY = secondEntity.getY() + secondEntity.getHeight() / 2;
        if (firstEntity.getX() > secondEntity.getX()) {
            int firstX = firstEntity.getX();
            int secondX = secondEntity.getX() + secondEntity.getWidth();
            firstPoint = paintRelationshipEnd(graphics, firstX - END_LENGTH, firstY, firstX,
                    firstY, relationship.getFirstEnd().getMultiplicity(), true);
            secondPoint = paintRelationshipEnd(graphics, secondX, secondY,
                    secondX + 15, secondY, relationship.getSecondEnd()
                            .getMultiplicity(), false);
            firstPoint = new Point(firstX - END_LENGTH, firstY);
            secondPoint = new Point(secondX + END_LENGTH, secondY);
        } else {
            int firstX = firstEntity.getX() + firstEntity.getWidth();
            int secondX = secondEntity.getX();
            firstPoint = paintRelationshipEnd(graphics, firstX, firstY, firstX + RelationshipView.END_LENGTH,
                    firstY, relationship.getFirstEnd().getMultiplicity(), false);
            secondPoint = paintRelationshipEnd(graphics, secondX - END_LENGTH, secondY,
                    secondX, secondY, relationship.getSecondEnd()
                            .getMultiplicity(), true);
            firstPoint = new Point(firstX + END_LENGTH, firstY);
            secondPoint = new Point(secondX - END_LENGTH, secondY);
        }
        int xCenter = Math.abs(firstPoint.x + secondPoint.x)/2;
        int yCenter = Math.abs(firstPoint.y + secondPoint.y)/2;
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
            return new Point(x2, y2);
        } else {
            if (mult.isPlural()) {
                graphics2D.drawLine(x1, y1 - END_WIDTH, x2, y2);
                graphics2D.drawLine(x1, y1 + END_WIDTH, x2, y2);
            }
            return new Point(x1, y1);
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
