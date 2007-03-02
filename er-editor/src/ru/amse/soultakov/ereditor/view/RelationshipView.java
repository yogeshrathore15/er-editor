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

	private static final BasicStroke SIMPLE_STROKE = new BasicStroke(1.0f);

	private static final BasicStroke DASHED = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
			new float[] { 10.0f }, 0.0f);

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
	public void paint(Graphics graphics)  {
		Graphics2D graphics2D = (Graphics2D) graphics;
		int firstCenterX = firstEntity.getX() + firstEntity.getWidth() / 2;
		int firstCenterY = firstEntity.getY() + firstEntity.getHeight() / 3;
		int secondCenterX = secondEntity.getX() + secondEntity.getWidth() / 2;
		int secondCenterY = secondEntity.getY() + secondEntity.getHeight() / 3;

		int xCenter = (firstCenterX + secondCenterX) / 2;
		int yCenter = (firstCenterY + secondCenterY) / 2;
		
		paintRelationshipEnd(graphics2D, firstCenterX, firstCenterY, xCenter,
				yCenter, relationship.getFirstEnd().getMultiplicity());
		paintRelationshipEnd(graphics2D, secondCenterX, secondCenterY, xCenter,
				yCenter, relationship.getSecondEnd().getMultiplicity());
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
		graphics2D.setColor(Color.BLACK);
		graphics2D.drawLine(x1, y1, xCenter, yCenter);
		graphics2D.setStroke(old);
	}

	/**
	 * @return the firstEntity
	 */
	public EntityView getFirstEntity() {
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
	public EntityView getSecondEntity() {
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
