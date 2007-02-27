/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

/**
 * @author Soultakov Maxim
 */
public class RelationshipView {

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

    final static BasicStroke stroke = new BasicStroke(1.0f);

    final static BasicStroke wideStroke = new BasicStroke(8.0f);

    final static float dash1[] = { 10.0f };

    final static BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

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

    public void paint(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;
        int x1, x2, y1, y2;
        if (firstEntity.getX() > secondEntity.getX()) {
            x1 = firstEntity.getX();
            x2 = secondEntity.getX() + secondEntity.getWidth();
        } else {
            x1 = firstEntity.getX() + firstEntity.getWidth();
            x2 = secondEntity.getX();
        }
        if (firstEntity.getY() > secondEntity.getY()) {
            y1 = firstEntity.getY();
            y2 = secondEntity.getY() + secondEntity.getHeight();
        } else {
            y1 = firstEntity.getY() + firstEntity.getHeight();
            y2 = secondEntity.getY();
        }
        int xCenter = (x1 + x2) / 2;
        int yCenter = (y1 + y2) / 2;
        drawRelationshipEnd(graphics2D, x1, y1, xCenter, yCenter, relationship.getFirstEnd()
                .getMultiplicity());
        drawRelationshipEnd(graphics2D, x2, y2, xCenter, yCenter, relationship
                .getSecondEnd().getMultiplicity());
        //graphics2D.drawLine(x1, y1, x2, y2);
    }

    private void drawRelationshipEnd(Graphics2D graphics2D, int x1, int y1, int xCenter,
            int yCenter, RelationshipMultiplicity multiplicity) {
        Stroke old = graphics2D.getStroke();
        graphics2D.setStroke(multiplicity.isObligatory() ? stroke : dashed);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawLine(x1, y1, xCenter, yCenter);
        graphics2D.setStroke(old);
    }
}
