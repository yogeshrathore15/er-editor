/*
 * Created on 05.03.2007
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author Soultakov Maxim
 */
public class Link {

    /**
     * 
     */
    private Entity entity;
    
    /**
     * 
     */
    private Comment comment;

    public Link(Entity entity, Comment comment) {
        if ((entity == null) || (comment == null)) {
            throw new IllegalArgumentException("Arguments must be non null values");
        }
        this.entity = entity;
        entity.addLink(this);
        this.comment = comment;
        comment.addLink(this);
    }

    /**
     * @return
     */
    public Comment getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

    /**
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @param entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }
    
    
}
