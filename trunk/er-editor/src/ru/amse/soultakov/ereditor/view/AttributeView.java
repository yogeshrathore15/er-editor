package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JOptionPane;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.undo.commands.EditAttributeCommand;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.ArrayAttributeType;
import ru.amse.soultakov.ereditor.model.IAttributeType;
import ru.amse.soultakov.ereditor.model.SimpleAttributeType;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 * 
 */
public class AttributeView {

    private AbstractAttribute attribute;

    private Block entityView;

    private boolean selected;

    private volatile int lastPaintedY;

    public AttributeView(AbstractAttribute attribute, Block entityView) {
        this.attribute = attribute;
        this.entityView = entityView;
    }

    public AbstractAttribute getAttribute() {
        return this.attribute;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setAttribute(AbstractAttribute attribute) {
        this.attribute = attribute;
    }

    public Block getEntityView() {
        return this.entityView;
    }

    public void setEntityView(Block entityView) {
        this.entityView = entityView;
    }

    /**
     * @return the lastPaintedY
     */
    public int getLastPaintedY() {
        return this.lastPaintedY;
    }

    public int paint(Graphics2D graphics, int x, int y) {
        String attrString = getStringPresentation();
        Rectangle2D bounds = GraphicsUtils.getStringBounds(graphics, attrString);
        this.lastPaintedY = (y + (int) bounds.getHeight());
        if (selected) {
            Color prev = graphics.getColor();
            graphics.setColor(Color.LIGHT_GRAY);
            graphics.fillRect(Block.MARGIN + this.entityView.getX(), lastPaintedY
                    - (int) bounds.getHeight() + 3, entityView.getWidth()
                    - Block.MARGIN * 2, (int) bounds.getHeight());
            graphics.setColor(prev);
        }
        graphics.drawString(attrString, Block.MARGIN + this.entityView.getX(),
                this.lastPaintedY);
        return lastPaintedY + Block.MARGIN;
    }

    // TODO: добавить возможность менять представление в зависимости от внешних
    // настроек
    public String getStringPresentation() {
        return this.attribute.getName() + " : " + this.attribute.getType().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.attribute.getName();
    }

    public boolean tryToSetAttribute(String attrString, DiagramEditor diagramEditor) {
        boolean matches = attrString.matches("\\w+\\s*:\\s*\\w+(\\[(\\d){1,5}\\])?");
        if (matches) {
            int colonIndex = attrString.lastIndexOf(':');
            String typeString = attrString.substring(colonIndex + 1).trim();
            IAttributeType type = getTypeFromString(typeString);
            if (type == null) {
                JOptionPane.showMessageDialog(null, "Некорректное имя типа!",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            String newName = attrString.substring(0, colonIndex - 1).trim();
            diagramEditor.getCommandManager().executeCommand(
                    new EditAttributeCommand(diagramEditor, this, newName, type));
        } else {
            JOptionPane.showMessageDialog(null, "Синтаксическая ошибка!", "Ошибка!",
                    JOptionPane.ERROR_MESSAGE);
        }
        return matches;
    }

    private IAttributeType getTypeFromString(String attributeValue) {
        int bracketIndex = attributeValue.indexOf('[');
        if (bracketIndex != -1) {
            SimpleAttributeType sat = getSimpleTypeFromString(attributeValue
                    .substring(0, bracketIndex).toUpperCase());
            if (sat != null) {
                int size = Integer.parseInt(attributeValue.substring(
                        bracketIndex + 1, attributeValue.length() - 1));
                return new ArrayAttributeType(sat, size);
            }
        } else {
            return getSimpleTypeFromString(attributeValue);
        }
        return null;
    }

    private SimpleAttributeType getSimpleTypeFromString(String attributeValue) {
        for (SimpleAttributeType sat : SimpleAttributeType.values()) {
            if (sat.name().equalsIgnoreCase(attributeValue)) {
                return SimpleAttributeType.valueOf(attributeValue.toUpperCase());
            }
        }
        return null;
    }

}
