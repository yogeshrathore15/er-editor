/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

/**
 * @author Soultakov Maxim
 */
public class RelationshipView extends Line {

    /**
     * 
     */
    private Relationship relationship;

    /**
     * 
     */
    protected EntityView firstEntity;

    /**
     * 
     */
    protected EntityView secondEntity;

    /**
     * @param relationship
     * @param firstEntity
     * @param secondEntity
     */
    public RelationshipView(Relationship relationship, EntityView firstEntity,
            EntityView secondEntity) {
        super();
        this.relationship = relationship;
        this.firstEntity = firstEntity;
        this.secondEntity = secondEntity;
    }

    /**
     * @param graphics
     */
    public void paint(Graphics2D graphics) {
        recalculateEndPoints();

        int xCenter = (firstCenterX + secondCenterX) / 2;
        int yCenter = (firstCenterY + secondCenterY) / 2;

        graphics.setColor(isSelected() ? Color.BLUE : Color.BLACK);
        paintRelationshipEnd(graphics, firstCenterX, firstCenterY, xCenter, yCenter,
                relationship.getFirstEnd().getMultiplicity());
        paintRelationshipEnd(graphics, secondCenterX, secondCenterY, xCenter,
                yCenter, relationship.getSecondEnd().getMultiplicity());
    }

    @Override
    protected void recalculateEndPoints() {
        firstCenterX = getXCenter(firstEntity, 2);
        firstCenterY = getYCenter(firstEntity, 3);
        secondCenterX = getXCenter(secondEntity, 2);
        secondCenterY = getYCenter(secondEntity, 3);
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
        graphics2D.setStroke(multiplicity.isObligatory() ? SIMPLE_STROKE : DASHED);
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

}
