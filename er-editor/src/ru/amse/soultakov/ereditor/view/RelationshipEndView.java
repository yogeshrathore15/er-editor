/*
 * Created on 22.02.2007
 */
package ru.amse.soultakov.ereditor.view;

import ru.amse.soultakov.ereditor.model.RelationshipEnd;

/**
 * @author Soultakov Maxim
 */
public class RelationshipEndView {

    /**
     * 
     */
    private RelationshipEnd relationshipEnd;

    /**
     * 
     */
    private EntityView attachedEntity;

    /**
     * @param relationshipEnd
     * @param attachedEntity
     */
    public RelationshipEndView(RelationshipEnd relationshipEnd,
            EntityView attachedEntity) {
        this.relationshipEnd = relationshipEnd;
        this.attachedEntity = attachedEntity;
    }

    /**
     * @return
     */
    public EntityView getAttachedEntity() {
        return attachedEntity;
    }

    /**
     * @param attachedEntity
     */
    public void setAttachedEntity(EntityView attachedEntity) {
        this.attachedEntity = attachedEntity;
    }

    /**
     * @return
     */
    public RelationshipEnd getRelationshipEnd() {
        return relationshipEnd;
    }

    /**
     * @param relationshipEnd
     */
    public void setRelationshipEnd(RelationshipEnd relationshipEnd) {
        this.relationshipEnd = relationshipEnd;
    }

}
