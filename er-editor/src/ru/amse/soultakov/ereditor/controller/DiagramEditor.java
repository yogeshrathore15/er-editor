/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Set;

import javax.swing.JComponent;

import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;
import ru.amse.soultakov.ereditor.controller.tools.Tool;
import ru.amse.soultakov.ereditor.view.Block;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.DiagramPresentation;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;
import ru.amse.soultakov.ereditor.view.SelectedItems;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private final DiagramPresentation diagram = new DiagramPresentation();

    private Tool currentTool;

    public DiagramEditor() {
        initMouseListener();
    }

    public EntityView addEntity(int x, int y) {
        return diagram.addNewEntityView(x, y);
    }

    public CommentView addComment(int x, int y) {
        return diagram.addNewCommentView(x, y);
    }

    public LinkView addLink(EntityView entityView, CommentView commentView) {
        return diagram.addNewLinkView(entityView, commentView);
    }

    public RelationshipView addRelationship(String name, EntityView first,
            EntityView second) {
        return diagram.addNewRelationshipView(name, first, second);
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    /**
     * 
     */
    public void removeSelection() {
        for (Viewable s : getSelectedItems()) {
            System.out.println(removeSelectable(s));
        }
        repaint();
    }

    public void setTool(Tool tool) {
        getSelectedItems().clear();
        repaint();
        currentTool = tool;
    }

    @Override
    protected void paintChildren(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        currentTool.paintBefore(graphics);
        // for correct relations painting we should recalculate the size of
        // blocks
        recalculateSize(diagram.getCommentViews(), graphics);
        recalculateSize(diagram.getEntityViews(), graphics);
        paintSet(diagram.getLinkViews(), graphics);
        paintSet(diagram.getRelationshipViews(), graphics);
        paintSet(diagram.getCommentViews(), graphics);
        paintSet(diagram.getEntityViews(), graphics);
        
        currentTool.paintAfter(graphics);
    }

    private void recalculateSize(Set<? extends Block> set, Graphics2D graphics) {
        for (Block b : set) {
            b.recalculateSize(graphics);
        }
    }

    private void paintSet(Set<? extends Viewable> setToPaint, Graphics2D graphics) {
        for (Viewable v : setToPaint) {
            v.paint(graphics);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    public DiagramPresentation getDiagram() {
        return diagram;
    }
    
    /**
     * 
     */
    private void initMouseListener() {
        currentTool = getDefaultTool();
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                requestFocusInWindow();
                currentTool.mouseClicked(e);
            }

            public void mouseEntered(MouseEvent e) {
                currentTool.mouseEntered(e);
            }

            public void mouseExited(MouseEvent e) {
                currentTool.mouseExited(e);
            }

            public void mousePressed(MouseEvent e) {
                currentTool.mousePressed(e);
            }

            public void mouseReleased(MouseEvent e) {
                currentTool.mouseReleased(e);
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                currentTool.mouseDragged(e);
            }

            public void mouseMoved(MouseEvent e) {
                currentTool.mouseMoved(e);
            }
        });
        this.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                currentTool.keyPressed(e);
            }

            public void keyReleased(KeyEvent e) {
                currentTool.keyReleased(e);
            }

            public void keyTyped(KeyEvent e) {
                currentTool.keyTyped(e);
            }
            
        });
    }

    public SelectElementTool getDefaultTool() {
        return new SelectElementTool(this);
    }

    public boolean removeEntity(EntityView view) {
        return diagram.removeEntityView(view);
    }

    public boolean removeComment(CommentView view) {
        return diagram.removeCommentView(view);
    }

    public boolean removeRelationship(RelationshipView view) {
        return diagram.removeRelationshipView(view);
    }

    public boolean removeLink(LinkView view) {
        return diagram.removeLinkView(view);
    }

    private boolean removeSelectable(Viewable s) {
        // this awful code will be refactored of course
        // visitor rules?
        if (s instanceof EntityView) {
            return removeEntity((EntityView) s);
        } else if (s instanceof RelationshipView) {
            return removeRelationship((RelationshipView) s);
        } else if (s instanceof CommentView) {
            return removeComment((CommentView) s);
        } else if (s instanceof LinkView) {
            return removeLink((LinkView) s);
        }
        return false;
    }

    /**
     * @return
     */
    private SelectedItems getSelectedItems() {
        return diagram.getSelectedItems();
    }
    
    @Override
    public boolean isFocusable() {
        return true;
    }

}
