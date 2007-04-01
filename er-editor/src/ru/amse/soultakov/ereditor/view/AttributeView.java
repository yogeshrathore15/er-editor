package ru.amse.soultakov.ereditor.view;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import ru.amse.soultakov.ereditor.model.Attribute;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class AttributeView {

    private Attribute attribute;

    private EntityView entityView;

    private int index;

    public AttributeView(Attribute attribute, EntityView entityView, int index) {
        this.attribute = attribute;
        this.entityView = entityView;
        this.index = index;
    }

    public Attribute getAttribute() {
        return this.attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public EntityView getEntityView() {
        return this.entityView;
    }

    public void setEntityView(EntityView entityView) {
        this.entityView = entityView;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int paint(Graphics2D graphics) {
        String attrString = getAttributeStringPresentation();
        Rectangle2D bounds = GraphicsUtils.getStringBounds(graphics, attrString);
        int newCurY = getTopByIndex() + (int) bounds.getHeight();
        graphics.drawString(attrString, Block.MARGIN * 2 + entityView.getX(),
                newCurY);
        return newCurY + Block.MARGIN;
    }

    private int getTopByIndex() {
        int y = index * 0;
        return 0;
    }

    // TODO: �������� ����������� ������ ������������� � ����������� �� �������
    // ��������
    private String getAttributeStringPresentation() {
        return attribute.getName();
    }

}
