/*
 * Created on 16.03.2007
 */
package ru.amse.soultakov.ereditor.controller;

import ru.amse.soultakov.ereditor.controller.tools.Tool;

public interface CurrentToolListener {
    
    void currentToolChanged(Tool oldTool, Tool newTool);
    
}
