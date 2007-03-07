/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import java.awt.BasicStroke;

import ru.amse.soultakov.ereditor.controller.Viewable;

public abstract class Line implements Viewable {

    protected static final BasicStroke SIMPLE_STROKE = new BasicStroke(1.0f);

    protected static final BasicStroke DASHED = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
            new float[] { 10.0f }, 0.0f);

    private static final double MIN_DISTANCE = 12.0;

    /**
     * 
     */
    private boolean selected;

    protected int firstCenterX;

    protected int firstCenterY;

    protected int secondCenterX;

    protected int secondCenterY;

    /**
     * @param block
     * @param n
     * @return
     */
    protected static int getXCenter(Block block, int n) {
        return block.getX() + block.getWidth() / n;
    }

    /**
     * @param block
     * @param n
     * @return
     */
    protected static int getYCenter(Block block, int n) {
        return block.getY() + block.getHeight() / n;
    }

    protected abstract void recalculateEndPoints();

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static double[] getLinePrms(int x1, int y1, int x2, int y2) {
        int dy = y2 - y1;
        int dx = x2 - x1;
        double[] prms = new double[3]; // a, b, c

        if (dx == 0) {
            prms[1] = 0.0;
            prms[0] = 1.0;
        } else if (dy == 0) {
            prms[1] = 1.0;
            prms[0] = 0.0;
        } else {
            prms[1] = 1.0;
            prms[0] = prms[1] * ((double) (-dy) / (double) (dx));
        }
        prms[2] = -(prms[0] * x1 + prms[1] * y1);
        return prms;
    }

    /**
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean trySelect(int x, int y) {
        boolean b = containsPoint(x, y);
        if (b) {
            setSelected(true);
        }
        return b;
    }

    public boolean containsPoint(int x, int y) {
        if (firstCenterX == secondCenterX) {
            if (y < Math.min(firstCenterY, secondCenterY)
                    || Math.max(firstCenterY, secondCenterY) < y) {
                return false;
            }
            return (Math.abs(x - firstCenterX) < MIN_DISTANCE);
        } else if (firstCenterY == secondCenterY) {
            if (x < Math.min(firstCenterX, secondCenterX)
                    || Math.max(firstCenterX, secondCenterX) < x)
                return false;
            return (Math.abs(y - firstCenterY) < MIN_DISTANCE);
        }
        if (firstCenterX < secondCenterX) {
            if (x < firstCenterX || secondCenterX < x)
                return false;
        } else {
            if (x < secondCenterX || firstCenterX < x)
                return false;
        }
        if (firstCenterY < secondCenterY) {
            if (y < firstCenterY || secondCenterY < y)
                return false;
        } else {
            if (y < secondCenterY || firstCenterY < y)
                return false;
        }

        double[] prms = getLinePrms(firstCenterX, firstCenterY, secondCenterX,
                secondCenterY);
        double a = prms[0];
        double b = prms[1];
        double c = prms[2];

        double c2 = -(b * x - a * y);

        double yt = (a * c2 - b * c) / (a * a + b * b);
        double xt = -(b * yt + c) / a;

        return (Math.sqrt((xt - x) * (xt - x) + (yt - y) * (yt - y)) < MIN_DISTANCE);
    }

}