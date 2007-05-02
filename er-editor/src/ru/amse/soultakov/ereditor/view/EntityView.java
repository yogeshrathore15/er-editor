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
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.controller.tools.ITool;
import ru.amse.soultakov.ereditor.controller.tools.ToolAdapter;
import ru.amse.soultakov.ereditor.model.AbstractAttribute;
import ru.amse.soultakov.ereditor.model.Constraint;
import ru.amse.soultakov.ereditor.model.Entity;
import ru.amse.soultakov.ereditor.model.FKAttribute;
import ru.amse.soultakov.ereditor.util.GraphicsUtils;

/**
 * @author Soultakov Maxim
 */
public class EntityView extends Block {

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
    }

    private void initCompartments(Graphics2D graphics) {
        titleCompartment = new TitleCompartment(MARGIN, this, entity.getName());
        pkCompartment = new PrimaryKeyCompartment(titleCompartment
                .getHeight(graphics)
                + MARGIN * 2, this);
        initCompartment(pkCompartment, entity.getPrimaryKey());
        nonPkCompartment = new NonPrimaryKeyCompartment(pkCompartment
                .getHeight(graphics)
                + MARGIN * 4, this);
        initCompartment(nonPkCompartment, entity.getAttributesExceptPK());
    }

    private void initCompartment(AttributesCompartment compartment,
            Iterable<AbstractAttribute> attrs) {
        for (AbstractAttribute a : attrs) {
            AttributeView attributeView = new AttributeView(a, this, compartment);
            attributeViews.add(attributeView);
            compartment.getAttributes().add(attributeView);
        }
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
        bounds.add(GraphicsUtils.getStringBounds(graphics, entity.getName()));
        bounds.add(pkCompartment.getContentBounds(graphics));
        bounds.add(nonPkCompartment.getContentBounds(graphics));
        int height = 0;
        for (Rectangle2D r : bounds) {
            height += r.getHeight();
        }
        Rectangle2D withMaxWidth = Collections.max(bounds,
                GraphicsUtils.WIDTH_COMPARATOR);
        return new Dimension((int) withMaxWidth.getWidth() + MARGIN * 2, height);
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
        if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
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
                        entity.setName(tf.getText());
                        stopEditing(editor, tf, oldTool);
                        System.out.println(entity.getName());
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
                            .tryToSetAttribute(tf.getText())) {
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

//    @Override
//    public void exitProcessing() {
//        attributeViews.get(selectedAttributeIndex).setSelected(false);
//        selectedAttributeIndex = -1;
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelected(boolean selected) {
        if (selectedAttributeIndex != -1 && !selected) {
            attributeViews.get(selectedAttributeIndex).setSelected(false);
            selectedAttributeIndex = -1;
        }
        super.setSelected(selected);
    }

    private void stopEditing(final DiagramEditor editor, final JTextField tf, ITool oldTool) {
        editor.remove(tf);
        editor.setTool(oldTool);
        editor.repaint();
        selectedAttributeIndex = -1;
    }

}
