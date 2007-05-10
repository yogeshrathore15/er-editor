package ru.amse.soultakov.ereditor.view;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newArrayList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.ITool;
import ru.amse.soultakov.ereditor.controller.tools.ToolAdapter;
import ru.amse.soultakov.ereditor.controller.undo.ICommand;
import ru.amse.soultakov.ereditor.controller.undo.commands.EditEntityNameCommand;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.FKAttribute;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 */
public class EntityView extends Block {

    private final class MoveAttributeCommand implements ICommand {

        private final DiagramEditor editor;

        private final boolean pkContains;

        private final boolean nonPkContains;

        private final boolean isFirst;

        private final boolean isLast;

        private final int newIndex;

        private final int oldIndex;

        private final boolean inPkCompartment;

        private MoveAttributeCommand(int index, DiagramEditor editor) {
            this.editor = editor;
            oldIndex = selectedAttributeIndex;
            pkContains = pkCompartment.contains(oldIndex);
            nonPkContains = nonPkCompartment.contains(oldIndex);
            isFirst = index == FIRST;
            isLast = index == LAST;
            if (isFirst) {
                newIndex = 0;
            } else if (isLast) {
                newIndex = attributeViews.size();
            } else {
                this.newIndex = index;
            }
            inPkCompartment = pkCompartment.contains(newIndex) || isFirst;
        }

        public void doIt() {
            AttributeView temp = attributeViews.remove(oldIndex);
            attributeViews.add(newIndex == 0 ? newIndex : newIndex - 1, temp);
            if (inPkCompartment) {
                if (nonPkContains) {
                    entity.addToPrimaryKey(temp.getAttribute());
                }
            } else {
                if (pkContains) {
                    entity.removeFromPrimaryKey(temp.getAttribute());
                }
            }
            initialized = false;
            this.editor.getSelectedOutlines().clear();
            notifyListeners();
        }

        public void undoIt() {
            AttributeView temp = attributeViews.remove(newIndex == attributeViews
                    .size() ? newIndex - 1 : newIndex);
            attributeViews.add(oldIndex, temp);
            if (inPkCompartment) {
                if (nonPkContains) {
                    entity.removeFromPrimaryKey(temp.getAttribute());
                }
            } else {
                if (pkContains) {
                    entity.addToPrimaryKey(temp.getAttribute());
                }
            }
            initialized = false;
            this.editor.getSelectedOutlines().clear();
            notifyListeners();
        }
    }

    private static final int LAST = -3;

    private static final int FIRST = -2;

    private static final Color BACKGROUND_COLOR = new Color(210, 210, 210);

    protected Entity entity;

    protected List<AttributeView> attributeViews = newArrayList();

    protected TitleCompartment titleCompartment;

    protected AttributesCompartment pkCompartment;

    protected AttributesCompartment nonPkCompartment;

    private boolean initialized = false;

    private int selectedAttributeIndex = -1;

    public EntityView(Diagram diagram, Entity entity, int x, int y) {
        super(diagram, x, y);
        if (entity == null) {
            throw new IllegalArgumentException("Entity must be non-null");
        }
        this.entity = entity;
        initAttributes();
    }

    private void initCompartments(Graphics2D graphics) {
        titleCompartment = new TitleCompartment(MARGIN, this, entity.getName());
        pkCompartment = new PrimaryKeyCompartment(titleCompartment
                .getHeight(graphics)
                + MARGIN * 2, this);
        Constraint<AbstractAttribute> primaryKey = entity.getPrimaryKey();
        pkCompartment.setStartIndex(0);
        pkCompartment.setEndIndex(primaryKey.size());
        nonPkCompartment = new NonPrimaryKeyCompartment(pkCompartment
                .getHeight(graphics)
                + titleCompartment.getHeight(graphics) + MARGIN * 4, this);
        Collection<AbstractAttribute> attributesExceptPK = entity
                .getAttributesExceptPK();
        nonPkCompartment.setStartIndex(primaryKey.size());
        nonPkCompartment.setEndIndex(primaryKey.size() + attributesExceptPK.size());
    }

    final void initAttributes() {
        attributeViews = new ArrayList<AttributeView>(entity.getAttributes().size());
        for (AbstractAttribute a : entity.getPrimaryKey()) {
            AttributeView attributeView = new AttributeView(a, this);
            attributeViews.add(attributeView);
        }
        for (AbstractAttribute a : entity.getAttributesExceptPK()) {
            AttributeView attributeView = new AttributeView(a, this);
            attributeViews.add(attributeView);
        }
        initialized = false;
    }

    public void paint(Graphics2D graphics) {
        lazyInitCompartments(graphics);
        recalculateSize(graphics);

        drawBackground(graphics);
        drawBorder(graphics);

        int curY = titleCompartment.paint(graphics);
        drawHorizontalLine(graphics, curY + MARGIN);
        curY = pkCompartment.paint(graphics);
        drawHorizontalLine(graphics, curY + MARGIN);
        curY = nonPkCompartment.paint(graphics);
        drawSelection(graphics);
    }

    private void lazyInitCompartments(Graphics2D graphics) {
        if (!initialized) {
            initCompartments(graphics);
            initialized = true;
        }
    }

    private void drawHorizontalLine(Graphics2D graphics, int newCurY) {
        graphics.drawLine(getX(), newCurY, getX() + getWidth(), newCurY);
    }

    boolean isUnique(AbstractAttribute a) {
        for (Constraint<AbstractAttribute> index : entity.getUniqueAttributes()) {
            if (index.contains(a)) {
                return true;
            }
        }
        return false;
    }

    boolean isForeignKey(AbstractAttribute a) {
        for (Constraint<FKAttribute> index : entity.getForeignKeys()) {
            if (index.contains(a)) {
                return true;
            }
        }
        return false;
    }

    private void drawBackground(Graphics2D graphics) {
        drawShadow(graphics);
        graphics.setColor(BACKGROUND_COLOR);
        graphics.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 10, 10);
    }

    @Override
    protected Dimension getContentBounds(Graphics2D graphics) {
        lazyInitCompartments(graphics);
        List<Rectangle2D> bounds = new ArrayList<Rectangle2D>(3);
        bounds.add(titleCompartment.getContentBounds(graphics));
        bounds.add(pkCompartment.getContentBounds(graphics));
        bounds.add(nonPkCompartment.getContentBounds(graphics));
        int height = 0;
        for (Rectangle2D r : bounds) {
            height += r.getHeight();
        }
        Rectangle2D withMaxWidth = Collections.max(bounds,
                GraphicsUtils.WIDTH_COMPARATOR);
        return new Dimension((int) withMaxWidth.getWidth() + MARGIN * 2, height
                + MARGIN * 5);
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean acceptRelationshipWith(EntityView entityView) {
        return entity.acceptRelationshipWith(entityView.getEntity());
    }

    public boolean acceptLinkWith(CommentView commentView) {
        return entity.acceptLinkWith(commentView.getComment());
    }

    public <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data) {
        return visitor.visit(this, data);
    }

    public EntityView copy() {
        EntityView entityView = new EntityView(diagram, entity.copy(), x, y);
        diagram.addEntityView(entityView);
        return entityView;
    }

    @Override
    public void processClick(MouseEvent mouseEvent, final DiagramEditor editor) {
        if (mouseEvent.getButton() == MouseEvent.BUTTON1
                && !mouseEvent.isShiftDown()) {
            if (mouseEvent.getClickCount() == 1) {
                selectAttribute(mouseEvent.getY());
            } else if (mouseEvent.getClickCount() == 2) {
                selectAttribute(mouseEvent.getY());
                if (selectedAttributeIndex != -1) {
                    editAttribute(editor);
                } else {
                    editTitle(editor);
                }
            }
        }
    }

    private void editTitle(final DiagramEditor editor) {
        final JTextField tf = new JTextField(entity.getName());
        tf.setBounds(getX() + Block.MARGIN, getY() + titleCompartment.getY(), tf
                .getPreferredSize().width, tf.getPreferredSize().height);
        final ITool oldTool = editor.getTool();
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    stopEditing(editor, tf, oldTool);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!tf.getText().isEmpty()) {
                        editor.getCommandManager().executeCommand(
                                new EditEntityNameCommand(editor, EntityView.this,
                                        tf.getText()));
                        stopEditing(editor, tf, oldTool);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Синтаксическая ошибка!", "Ошибка!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        editor.add(tf);
        tf.requestFocus();
        editor.setTool(new ToolAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stopEditing(editor, tf, oldTool);
            }
        });
    }

    private void editAttribute(final DiagramEditor editor) {
        final JTextField tf = new JTextField(attributeViews.get(
                selectedAttributeIndex).getStringPresentation());
        tf.setBounds(getX() + Block.MARGIN, attributeViews.get(
                selectedAttributeIndex).getLastPaintedY() - 15, tf
                .getPreferredSize().width, tf.getPreferredSize().height);
        final ITool oldTool = editor.getTool();
        tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    stopEditing(editor, tf, oldTool);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (attributeViews.get(selectedAttributeIndex)
                            .tryToSetAttribute(tf.getText(), editor)) {
                        stopEditing(editor, tf, oldTool);
                    }
                }
            }
        });
        editor.setTool(new ToolAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stopEditing(editor, tf, oldTool);
            }
        });
        editor.add(tf);
        tf.requestFocus();
    }

    private void selectAttribute(int y) {
        for (int i = 0; i < attributeViews.size(); i++) {
            if (attributeViews.get(i).getLastPaintedY() >= y
                    && attributeViews.get(i).getLastPaintedY() <= y + 15) {
                attributeViews.get(i).setSelected(true);
                selectedAttributeIndex = i;
            } else {
                attributeViews.get(i).setSelected(false);
            }
        }
    }

    private int getAttributeIndex(int y) {
        if (attributeViews.size() > 0) {
            if (y < attributeViews.get(0).getLastPaintedY() - 15) {
                return EntityView.FIRST;
            } else if (y > attributeViews.get(attributeViews.size() - 1)
                    .getLastPaintedY()) {
                return EntityView.LAST;
            }
            for (int i = 0; i < attributeViews.size(); i++) {
                if (attributeViews.get(i).getLastPaintedY() >= y
                        && attributeViews.get(i).getLastPaintedY() <= y + 15) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelected(boolean selected) {
        if (selectedAttributeIndex != -1 && !selected) {
            if (selectedAttributeIndex < attributeViews.size()) {
                attributeViews.get(selectedAttributeIndex).setSelected(false);
            }
            selectedAttributeIndex = -1;
        }
        super.setSelected(selected);
    }

    private void stopEditing(final DiagramEditor editor, final JTextField tf,
            ITool oldTool) {
        editor.remove(tf);
        editor.setTool(oldTool);
        editor.getSelectedOutlines().clear();
        editor.repaint();
        selectedAttributeIndex = -1;
    }

    public AttributeView addAttribute(AbstractAttribute attribute) {
        entity.addAttribute(attribute);
        AttributeView attributeView = new AttributeView(attribute, this);
        attributeViews.add(attributeView);
        nonPkCompartment.setEndIndex(attributeViews.size());
        initialized = false;
        notifyListeners();
        return attributeView;
    }

    public void addAttributeView(int index, AttributeView attributeView) {
        entity.addAttribute(attributeView.getAttribute());
        attributeViews.add(index, attributeView);
        nonPkCompartment.setEndIndex(attributeViews.size());
        initialized = false;
        notifyListeners();
    }

    public void editAttribute(int index, DiagramEditor editor) {
        if (index < 0 || index >= attributeViews.size()) {
            throw new IndexOutOfBoundsException(
                    "The index is out of bounds. Index = " + index + " size = "
                            + attributeViews.size());
        }
        selectedAttributeIndex = index;
        editAttribute(editor);
    }

    public boolean isAttributeSelected() {
        return selectedAttributeIndex != -1
                && selectedAttributeIndex < attributeViews.size();
    }

    public void removeSelectedAttribute() {
        if (isAttributeSelected()) {
            removeAttribute(attributeViews.get(selectedAttributeIndex));
        }
    }

    public void removeAttribute(AttributeView attributeView) {
        entity.removeAttribute(attributeView.getAttribute());
        attributeViews.remove(attributeView);
        initialized = false;
        notifyListeners();
    }

    private boolean dragStarted;

    @Override
    public void processDrag(MouseEvent mouseEvent, DiagramEditor editor) {
        if (!dragStarted) {
            selectAttribute(mouseEvent.getY());
            notifyListeners();
            dragStarted = true;
        } else {

        }
    }

    @Override
    public void processRelease(MouseEvent mouseEvent, final DiagramEditor editor) {
        if (dragStarted) {
            final int index = getAttributeIndex(mouseEvent.getY());
            if (index != -1 && selectedAttributeIndex != -1) {
                editor.getCommandManager().executeCommand(
                        new MoveAttributeCommand(index, editor));
            }
        }
        dragStarted = false;
    }

    public List<AttributeView> getAttributes() {
        return attributeViews;
    }

    public int getSelectedAttributeIndex() {
        return selectedAttributeIndex;
    }

}
