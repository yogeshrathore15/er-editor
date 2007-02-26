/**
 * 
 */
package ru.amse.soultakov.ereditor.controller;

import java.util.HashSet;
import java.util.Set;

import ru.amse.soultakov.ereditor.view.EntityView;

/**
 * @author sma
 * 
 */
public class SelectedItems {

	private Set<EntityView> selectedEntities = new HashSet<EntityView>();

	public SelectedItems() {
	}

	public boolean addEntity(EntityView entityView) {
		entityView.setSelected(true);
		return selectedEntities.add(entityView);
	}

	public boolean removeEntity(EntityView entityView) {
		entityView.setSelected(false);
		return selectedEntities.remove(entityView);
	}

	public boolean containsEntity(EntityView entityView) {
		return selectedEntities.contains(entityView);
	}

	public void clear() {
		for (EntityView entityView : selectedEntities) {
			entityView.setSelected(false);
		}
		selectedEntities.clear();
	}

}
