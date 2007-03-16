/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import static ru.amse.soultakov.ereditor.util.Utils.newArrayList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import ru.amse.soultakov.ereditor.controller.tools.SelectElementTool;
import ru.amse.soultakov.ereditor.controller.tools.Tool;
import ru.amse.soultakov.ereditor.view.Block;
import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.Diagram;
import ru.amse.soultakov.ereditor.view.DiagramListener;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.Line;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;
import ru.amse.soultakov.ereditor.view.SelectedItems;
import ru.amse.soultakov.ereditor.view.Viewable;
import ru.amse.soultakov.ereditor.view.ViewableListener;
import ru.amse.soultakov.ereditor.view.Visitor;

/**
 * @author sma
 * 
 */
public class DiagramEditor extends JComponent {

    /**
	 * 
	 */
	private static final Dimension PREFERRED_SIZE = new Dimension(800, 600);

    private final Diagram diagram = new Diagram();
    
    private final SelectedItems selectedItems = new SelectedItems();

    private Tool currentTool;
    
    private final RemoveItemsVisitor itemsRemover = new RemoveItemsVisitor();
    
    private final ViewableListener viewableListener = new MyViewableListener();

    private final List<CurrentToolListener> listeners = newArrayList();
    
    public DiagramEditor() {
        initMouseListener();
        diagram.addDiagramListener(new DiagramListener() {
			public void diagramModified(Diagram diagram) {
				repaint();
			}
        });
    }

    public EntityView addEntity(int x, int y) {
        EntityView entity = diagram.addNewEntityView(x, y);
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

    public RelationshipView addRelationship(EntityView first,
            EntityView second) {
        RelationshipView relationship = diagram.addNewRelationshipView(first, second);
        relationship.addListener(viewableListener);
		return relationship;
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
        notifyListeners(currentTool, tool);
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
    
    public Diagram getDiagram() {
        return diagram;
    }
    
    /**
	 * @return the selectedItems
	 */
	public SelectedItems getSelectedItems()
	{
		return selectedItems;
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

    private boolean removeSelectable(Viewable s) {
        return s.acceptVisitor(itemsRemover, null);
    }
    
    public void addToolChangeListener(CurrentToolListener ctl) {
        listeners.add(ctl);
    }
    
    public boolean removeToolChangeListener(CurrentToolListener ctl) {
        return listeners.remove(ctl);
    }
    
    protected void notifyListeners(Tool oldTool, Tool newTool) {
        for (CurrentToolListener ctl : listeners) {
            ctl.currentToolChanged(oldTool, newTool);
        }
    }
        
    /**
	 * @author sma
	 *
	 */
	private final class MyViewableListener implements ViewableListener
	{
		public void notify(Viewable viewable) {
			repaint();
		}
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
