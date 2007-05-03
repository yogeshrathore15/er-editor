/**
 * 
 */
package ru.amse.soultakov.ereditor.controller.actions;

import ru.amse.soultakov.ereditor.view.CommentView;
import ru.amse.soultakov.ereditor.view.EntityView;
import ru.amse.soultakov.ereditor.view.IVisitor;
import ru.amse.soultakov.ereditor.view.LinkView;
import ru.amse.soultakov.ereditor.view.RelationshipView;

class EntityChecker implements IVisitor<Boolean, Void> {

    public Boolean visit(CommentView commentView, Void data) {
        return false;
    }

    public Boolean visit(EntityView entityView, Void data) {
        return true;
    }

    public Boolean visit(LinkView linkView, Void data) {
        return false;
    }

    public Boolean visit(RelationshipView relationshipView, Void data) {
        return false;
    }

}