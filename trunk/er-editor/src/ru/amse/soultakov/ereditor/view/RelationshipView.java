/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics;

import ru.amse.soultakov.ereditor.model.Relationship;

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
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x1, y1, x2, y2);
    }
}
