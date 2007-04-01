package ru.amse.soultakov.ereditor.util;

import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class GraphicsUtils {

    private static final FontRenderContext FONT_RENDER_CONTEXT = new FontRenderContext(
            null, false, false);
    
    /**
     * @param graphics
     * @return
     */
    public static Rectangle2D getStringBounds(Graphics2D graphics, String string) {
        return graphics.getFont().getStringBounds(string, FONT_RENDER_CONTEXT);
    }
    
}
