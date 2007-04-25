/*
 * Created on 16.03.2007
 */
package ru.amse.soultakov.ereditor.controller;

import ru.amse.soultakov.ereditor.controller.tools.ITool;

public interface ICurrentToolListener {

    void currentToolChanged(ITool oldTool, ITool newTool);

}
