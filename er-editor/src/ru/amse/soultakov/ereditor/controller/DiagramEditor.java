/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import static ru.amse.soultakov.ereditor.util.CommonUtils.newArrayList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;

import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;
import ru.amse.soultakov.ereditor.controller.tools.Tool;
import ru.amse.soultakov.ereditor.view.Block;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.IDiagramListener;
import ru.amse.soultakov.ereditor.view.IOutline;
import ru.amse.soultakov.ereditor.view.IViewable;
import ru.amse.soultakov.ereditor.view.IViewableListener;
import ru.amse.soultakov.ereditor.view.IVisitor;
import ru.amse.soultakov.ereditor.view.Line;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;
import ru.amse.soultakov.ereditor.view.SelectedItems;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension MIN_SIZE = new Dimension(400, 400);

    private static final long serialVersionUID = 1L;

    private Diagram diagram = new Diagram();

    private final SelectedItems<IViewable> selectedItems = new SelectedItems<IViewable>();
    
    private final SelectedItems<IOutline> selectedOutlines = new SelectedItems<IOutline>();

    private Tool currentTool;

    private final RemoveItemsVisitor itemsRemover = new RemoveItemsVisitor();

    private final IViewableListener viewableListener = new IViewableListener() {
        public void notify(IViewable viewable) {
            repaint();
        }
    };

    private final List<ICurrentToolListener> listeners = newArrayList();

    public DiagramEditor() {
        initMouseListener();
        diagram.addDiagramListener(new IDiagramListener() {
            public void diagramModified(Diagram diagram) {
                repaint();
            }
        });
    }

    public Block addEntity(int x, int y) {
        Block entity = diagram.addNewEntityView(x, y);
        entity.addListener(viewableListener);
        return entity;
    }

    public CommentView addComment(int x, int y) {
        CommentView comment = diagram.addNewCommentView(x, y);
        comment.addListener(viewableListener);
        return comment;
    }

    public Line addLink(EntityView entityView, CommentView commentView) {
        Line link = diagram.addNewLinkView(entityView, commentView);
        link.addListener(viewableListener);
        return link;
    }

    public RelationshipView addRelationship(EntityView first, EntityView second) {
        RelationshipView relationship = diagram
                .addNewRelationshipView(first, second);
        relationship.addListener(viewableListener);
        return relationship;
    }

    /**
     * 
     */
    public void removeSelection() {
        for (IViewable s : getSelectedItems()) {
            removeSelectable(s);
        }
        repaint();
    }

    public void setTool(Tool tool) {
        getSelectedItems().clear();
        repaint();
        if (tool != null) {
            notifyListeners(getCurrentTool(), tool);
            setCurrentTool(tool);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        Dimension newDim = diagram.getSize();
        double height = newDim.getHeight() < MIN_SIZE.getHeight() ? MIN_SIZE
                .getHeight() : newDim.getHeight();
        double width = newDim.getWidth() < MIN_SIZE.getWidth() ? MIN_SIZE.getWidth()
                : newDim.getWidth();
        newDim.setSize(width, height);
        return newDim;
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        getCurrentTool().paintBefore(graphics);
        // for correct relations painting we should recalculate the size of
        // blocks
        recalculateSize(diagram.getCommentViews(), graphics);
        recalculateSize(diagram.getEntityViews(), graphics);
        paintSet(diagram.getLinkViews(), graphics);
        paintSet(diagram.getRelationshipViews(), graphics);
        paintSet(diagram.getCommentViews(), graphics);
        paintSet(diagram.getEntityViews(), graphics);
        paintSet(getSelectedOutlines().toSet(), graphics);

        getCurrentTool().paintAfter(graphics);
    }

    private void recalculateSize(Collection<? extends Block> set, Graphics2D graphics) {
        for (Block b : set) {
            b.recalculateSize(graphics);
        }
    }

    private void paintSet(Collection<? extends IViewable> setToPaint, Graphics2D graphics) {
        for (IViewable v : setToPaint) {
            v.paint(graphics);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public Diagram getDiagram() {
        return diagram;
    }

    /**
     * @return the selectedItems
     */
    public SelectedItems<IViewable> getSelectedItems() {
        return selectedItems;
    }

    /**
     * 
     */
    private void initMouseListener() {
        setCurrentTool(new SelectElementTool(this));
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
                getCurrentTool().mouseClicked(e);
            }

            public void mouseEntered(MouseEvent e) {
                getCurrentTool().mouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                getCurrentTool().mouseExited(e);
            }

            public void mousePressed(MouseEvent e) {
                getCurrentTool().mousePressed(e);
            }

            public void mouseReleased(MouseEvent e) {
                getCurrentTool().mouseReleased(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                getCurrentTool().mouseDragged(e);
            }

            public void mouseMoved(MouseEvent e) {
                getCurrentTool().mouseMoved(e);
            }
        });
        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                getCurrentTool().keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                getCurrentTool().keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                getCurrentTool().keyTyped(e);
            }

        });
    }

    public boolean removeEntity(EntityView view) {
        view.removeListener(viewableListener);
        return diagram.removeEntityView(view);
    }

    public boolean removeComment(CommentView view) {
        view.removeListener(viewableListener);
        return diagram.removeCommentView(view);
    }

    public boolean removeRelationship(RelationshipView view) {
        view.removeListener(viewableListener);
        return diagram.removeRelationshipView(view);
    }

    public boolean removeLink(LinkView view) {
        view.removeListener(viewableListener);
        return diagram.removeLinkView(view);
    }

    private boolean removeSelectable(IViewable s) {
        return s.acceptVisitor(itemsRemover, null);
    }

    public void addToolChangeListener(ICurrentToolListener ctl) {
        listeners.add(ctl);
    }

    public boolean removeToolChangeListener(ICurrentToolListener ctl) {
        return listeners.remove(ctl);
    }

    protected void notifyListeners(Tool oldTool, Tool newTool) {
        for (ICurrentToolListener ctl : listeners) {
            ctl.currentToolChanged(oldTool, newTool);
        }
    }

    /**
     * @param currentTool the currentTool to set
     */
    private void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    /**
     * @return the currentTool
     */
    Tool getCurrentTool() {
        return currentTool;
    }
    
    public SelectedItems<IOutline> getSelectedOutlines() {
        return selectedOutlines;
    }

    private class RemoveItemsVisitor implements IVisitor<Boolean, Void> {

        public RemoveItemsVisitor() {
        }

        public Boolean visit(CommentView commentView, Void data) {
            return removeComment(commentView);
        }

        public Boolean visit(EntityView entityView, Void data) {
            return removeEntity(entityView);
        }

        public Boolean visit(LinkView linkView, Void data) {
            return removeLink(linkView);
        }

        public Boolean visit(RelationshipView relationshipView, Void data) {
            return removeRelationship(relationshipView);
        }

    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
        repaint();
    }

}
