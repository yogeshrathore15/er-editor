package ru.amse.soultakov.ereditor.util;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Comparator;

public class GraphicsUtils {

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(
            null, false, false);
	
    public static final Comparator<Rectangle2D> WIDTH_COMPARATOR = new Comparator<Rectangle2D>() {
        public int compare(Rectangle2D o1, Rectangle2D o2) {
            return (int) o1.getWidth() - (int) o2.getWidth();
        }
    };
    
    /**
     * @param graphics
     * @return
     */
    public static Rectangle2D getStringBounds(Graphics2D graphics, String string) {
        return graphics.getFont().getStringBounds(string, FONT_RENDER_CONTEXT);
    }
    
}
