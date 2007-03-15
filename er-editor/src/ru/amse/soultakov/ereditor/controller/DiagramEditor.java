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
import ru.amse.soultakov.ereditor.view.Line;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;
import ru.amse.soultakov.ereditor.view.SelectedItems;
import ru.amse.soultakov.ereditor.view.Viewable;
import ru.amse.soultakov.ereditor.view.Visitor;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private final DiagramPresentation diagramPresentation = new DiagramPresentation();

    private Tool currentTool;
    
    private RemoveItemsVisitor itemsRemover = new RemoveItemsVisitor();

    public DiagramEditor() {
        initMouseListener();
    }

    public EntityView addEntity(int x, int y) {
        return diagramPresentation.addNewEntityView(x, y);
    }

    public CommentView addComment(int x, int y) {
        return diagramPresentation.addNewCommentView(x, y);
    }

    public Line addLink(EntityView entityView, CommentView commentView) {
        return diagramPresentation.addNewLinkView(entityView, commentView);
    }

    public RelationshipView addRelationship(EntityView first,
            EntityView second) {
        return diagramPresentation.addNewRelationshipView(first, second);
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
            removeSelectable(s);
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
        recalculateSize(diagramPresentation.getCommentViews(), graphics);
        recalculateSize(diagramPresentation.getEntityViews(), graphics);
        paintSet(diagramPresentation.getLinkViews(), graphics);
        paintSet(diagramPresentation.getRelationshipViews(), graphics);
        paintSet(diagramPresentation.getCommentViews(), graphics);
        paintSet(diagramPresentation.getEntityViews(), graphics);
        
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
        return diagramPresentation;
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
        return diagramPresentation.removeEntityView(view);
    }

    public boolean removeComment(CommentView view) {
        return diagramPresentation.removeCommentView(view);
    }

    public boolean removeRelationship(RelationshipView view) {
        return diagramPresentation.removeRelationshipView(view);
    }

    public boolean removeLink(LinkView view) {
        return diagramPresentation.removeLinkView(view);
    }

    private boolean removeSelectable(Viewable s) {
        return s.acceptVisitor(itemsRemover, null);
    }

    /**
     * @return
     */
    private SelectedItems getSelectedItems() {
        return diagramPresentation.getSelectedItems();
    }
    
    private class RemoveItemsVisitor implements Visitor<Boolean, Void> {

    	public RemoveItemsVisitor() {
    	}
    	
    	public Boolean visit(CommentView commentView, Void data)
		{
			return removeComment(commentView);
		}

		public Boolean visit(EntityView entityView, Void data)
		{
			return removeEntity(entityView);
		}

		public Boolean visit(LinkView linkView, Void data)
		{
			return removeLink(linkView);
		}

		public Boolean visit(RelationshipView relationshipView, Void data)
		{
			return removeRelationship(relationshipView);
		}

    }

}
