/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;
import ru.amse.soultakov.ereditor.model.Entity;

/**
 * @author sma
 * 
 */
public class EntityView extends JComponent {

	private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(
			null, false, false);

	private static final int MARGIN = 3;

	private Entity entity;

	private boolean selected;

	private EntityColorDeterminant colorDeterminant = new EntityColorDeterminant();

	private DiagramEditor diagramEditor;

	public EntityView(DiagramEditor diagramEditor, Entity entity, int x, int y) {
		super();
		this.entity = entity;
		this.diagramEditor = diagramEditor;
		setLocation(x, y);
		setSize(1, 1);
		setOpaque(true);
		initMouseListener();
	}

	/**
	 * 
	 */
	private void initMouseListener() {
		MouseInputAdapter mouseInputAdapter = new MouseInputAdapter() {

			private Point current;
			private int dx = 0;
			private int dy = 0;
			
			@Override
			public void mousePressed(MouseEvent e) {
				diagramEditor.getSelectedItems().clear();
				diagramEditor.getSelectedItems().addEntity(EntityView.this);
				current = e.getPoint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				EntityView.this.shift(e.getX() - current.x , e.getY() - current.y );
				System.out.println(e.getSource());
				current = e.getPoint();
			}
			
		};
		this.addMouseListener(mouseInputAdapter);
		this.addMouseMotionListener(mouseInputAdapter);
		//this.addMouseWheelListener(mouseInputAdapter);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Rectangle2D titleBounds = drawTitle(graphics);
		setSize((int) titleBounds.getWidth() + getInsets().right
				+ getInsets().left, 100);
		int titleHeight = (int) (getInsets().top + titleBounds.getHeight() + MARGIN);
		graphics.setColor(colorDeterminant.getLineColor());
		graphics.drawLine(getInsets().left, titleHeight, getWidth(),
				titleHeight);
		graphics.drawLine(getInsets().left, (titleHeight + MARGIN) * 2,
				getWidth() - getInsets().right, (titleHeight + MARGIN) * 2);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createLineBorder(colorDeterminant.getLineColor()),
				BorderFactory.createLineBorder(Color.DARK_GRAY, 1)));//
	}

	private Rectangle2D drawTitle(Graphics2D graphics) {
		graphics.setColor(colorDeterminant.getTitleColor());
		Rectangle2D bounds = getStringBounds(graphics);
		graphics.drawString(entity.getName(), getInsets().left,
				(int) (getInsets().top + bounds.getHeight()));
		return bounds;
	}

	private Rectangle2D getStringBounds(Graphics2D graphics) {
		return graphics.getFont().getStringBounds(entity.getName(),
				FONT_RENDER_CONTEXT);
	}

	public void setSelected(boolean selected) {
		boolean oldSelected = this.selected;
		if (oldSelected != selected) {
			this.selected = selected;
			repaint();
		}
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	public Dimension getPreferredSize() {
		return getSize();
	}
	
	
	private void shift(int dx, int dy) {
		this.setLocation(getX() + dx , getY() + dy);
	}

	private class EntityColorDeterminant {

		public Color getTitleColor() {
			return Color.BLACK;
		}

		public Color getLineColor() {
			return isSelected() ? Color.RED : Color.BLACK;
		}

		public Color getBackgroundColor() {
			return Color.LIGHT_GRAY;
		}
	};

}
