/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import java.awt.event.ActionEvent;

import ru.amse.soultakov.ereditor.controller.DiagramEditor;

/**
 * @author sma
 * 
 */
public class RemoveSelectionAction extends DiagramEditorAction {

	/**
	 * @param diagramEditor
	 * @param name
	 */
	public RemoveSelectionAction(DiagramEditor diagramEditor, String name) {
		super(diagramEditor, name);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		diagramEditor.removeSelection();
	}

}
