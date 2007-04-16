/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Soultakov Maxim
 *
 */
public class BlockOutline extends Block implements IOutline
{

    protected static final BasicStroke SMALL_DASHED = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[] { 3.0f }, 1.0f);
    
    private final Block block;

	public BlockOutline(Diagram diagram, Block block)
	{
		super(diagram, block.getX(), block.getY());
		this.width = block.width;
		this.height = block.height;
        this.block = block;
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected Dimension getContentBounds(Graphics2D graphics)
	{
		return new Dimension(width, height);
	}

	/** 
	 * {@inheritDoc}
	 */
	public <R, D> R acceptVisitor(IVisitor<R, D> visitor, D data)
	{
		throw new UnsupportedOperationException();
	}

	/** 
	 * {@inheritDoc}
	 */
	public void paint(Graphics2D g)
	{
        Graphics2D graphics = (Graphics2D) g.create();
		graphics.setColor(Color.DARK_GRAY);
        graphics.setStroke(SMALL_DASHED);
		graphics.drawRect(x, y, width, height);
	}

    public IViewable getViewable() {
        return block;
    }

    public BlockOutline copy() {
        throw new UnsupportedOperationException();
    }



}
