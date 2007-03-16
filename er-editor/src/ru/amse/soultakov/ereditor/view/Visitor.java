/**
 * 
 */
package ru.amse.soultakov.ereditor.view;

/**
 * @author sma
 * 
 */
public interface Visitor<R, D> {

    R visit(CommentView commentView, D data);

    R visit(EntityView entityView, D data);

    R visit(LinkView linkView, D data);

    R visit(RelationshipView relationshipView, D data);

}
