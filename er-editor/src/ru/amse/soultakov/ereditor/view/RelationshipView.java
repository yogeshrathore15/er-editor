/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.model.Relationship;
import ru.amse.soultakov.ereditor.model.RelationshipEnd;
import ru.amse.soultakov.ereditor.model.RelationshipMultiplicity;

/**
 * @author Soultakov Maxim
 */
public class RelationshipView extends Line {

    private static final Object[] COMBOBOX_VALUES = new Object[] { "ONE_ONLY",
            "ONE_OR_MORE", "ZERO_OR_ONE", "ZERO_OR_MORE" };

    private static final int END_WIDTH = 10;

    private static final int END_LENGTH = 15;

    private static final int SELECTION_RECTANGLE_SIZE = 7;

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
        graphics.setColor(Color.BLACK);
        Block fe = getFirstEntityView();
        Block se = getSecondEntityView();

        int firstY = fe.getY() + fe.getHeight() / 2;
        int secondY = se.getY() + se.getHeight() / 2;
        if ((fe.getX() <= se.getX() + se.getWidth()) && (fe.getX() >= se.getX())) {
            drawBothLeft(graphics, firstY, secondY);
        } else if ((se.getX() >= fe.getX())
                && (se.getX() <= fe.getX() + fe.getWidth())) {
            drawBothRight(graphics, firstY, secondY);
        } else if (fe.getX() > se.getX()) {
            drawFirstLeftSecondRight(graphics, firstY, secondY);
        } else {
            drawFirstRightSecondLeft(graphics, firstY, secondY);
        }
        drawSelection(graphics);
    }

    private void drawSelection(Graphics2D graphics) {
        if (isSelected()) {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(firstCenterX - SELECTION_RECTANGLE_SIZE / 2,
                    firstCenterY - SELECTION_RECTANGLE_SIZE / 2,
                    SELECTION_RECTANGLE_SIZE, SELECTION_RECTANGLE_SIZE);
            graphics.fillRect(secondCenterX - SELECTION_RECTANGLE_SIZE / 2,
                    secondCenterY - SELECTION_RECTANGLE_SIZE / 2,
                    SELECTION_RECTANGLE_SIZE, SELECTION_RECTANGLE_SIZE);
            graphics.setColor(Color.BLACK);
            graphics.drawRect(firstCenterX - SELECTION_RECTANGLE_SIZE / 2,
                    firstCenterY - SELECTION_RECTANGLE_SIZE / 2,
                    SELECTION_RECTANGLE_SIZE, SELECTION_RECTANGLE_SIZE);
            graphics.drawRect(secondCenterX - SELECTION_RECTANGLE_SIZE / 2,
                    secondCenterY - SELECTION_RECTANGLE_SIZE / 2,
                    SELECTION_RECTANGLE_SIZE, SELECTION_RECTANGLE_SIZE);
        }
    }

    private void drawBothRight(Graphics2D graphics, int firstY, int secondY) {
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

    private void drawBothLeft(Graphics2D graphics, int firstY, int secondY) {
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

    private void drawFirstRightSecondLeft(Graphics2D graphics, int firstY,
            int secondY) {
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

    private void drawFirstLeftSecondRight(Graphics2D graphics, int firstY,
            int secondY) {
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
        firstCenterX = firstPoint.x;
        firstCenterY = firstPoint.y;
        secondCenterX = secondPoint.x;
        secondCenterY = secondPoint.y;
        int xCenter = Math.abs(firstPoint.x + secondPoint.x) / 2;
        int yCenter = Math.abs(firstPoint.y + secondPoint.y) / 2;
        drawLine(graphics, firstPoint.x, firstPoint.y, xCenter, yCenter,
                relationship.getFirstEnd().getMultiplicity());
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
    public Block getFirstEntityView() {
        return diagram.getEntityView(relationship.getFirstEnd().getEntity());
    }

    public Block getSecondEntityView() {
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

    public double distance(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    @Override
    public void processClick(MouseEvent mouseEvent, final DiagramEditor editor) {
        if (mouseEvent.getClickCount() == 2) {
            final JComboBox cb = new JComboBox(COMBOBOX_VALUES);
            // cb.setLightWeightPopupEnabled(false);
            if (distance(mouseEvent.getX(), mouseEvent.getY(), firstCenterX,
                    firstCenterY) < distance(mouseEvent.getX(), mouseEvent.getY(),
                    secondCenterX, secondCenterY)) {
                showComboBox(editor, cb, firstCenterX, firstCenterY, relationship
                        .getFirstEnd());
            } else {
                showComboBox(editor, cb, secondCenterX, secondCenterY, relationship
                        .getSecondEnd());
            }
        }
    }

    private void showComboBox(final DiagramEditor editor, final JComboBox combo,
            int x, int y, final RelationshipEnd end) {
        combo.setSelectedItem(end.getMultiplicity().name());
        combo.setBounds(x, y, combo.getPreferredSize().width, combo
                .getPreferredSize().height);
        combo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                end.setMultiplicity(RelationshipMultiplicity.valueOf((String) combo
                        .getSelectedItem()));
                editor.remove(combo);
                editor.setCurrentToolEnabled(true);
                editor.repaint();
            }
        });
        combo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                editor.remove(combo);
                editor.setCurrentToolEnabled(true);
                editor.repaint();
            }
        });
        editor.add(combo);
        editor.revalidate();
        editor.repaint();
        editor.setCurrentToolEnabled(false);
        combo.requestFocus();
    }

    @Override
    public boolean containsPoint(int x, int y) {
        boolean contains = super.containsPoint(x, y);
        contains |= ((x >= firstCenterX - END_LENGTH)
                && (x <= firstCenterX + END_LENGTH)
                && (y >= firstCenterY - END_WIDTH) && (y <= firstCenterY + END_WIDTH));
        contains |= ((x >= secondCenterX - END_LENGTH)
                && (x <= secondCenterX + END_LENGTH)
                && (y >= secondCenterY - END_WIDTH) && (y <= secondCenterY
                + END_WIDTH));
        return contains;
    }

}
