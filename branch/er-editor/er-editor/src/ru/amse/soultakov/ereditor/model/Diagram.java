/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import java.util.List;

/**
 * @author sma
 * 
 */
public class Diagram {
    private List<Entity> entities;

    private List<Relationship> relationships;

    private List<Comment> comments;

    public Diagram(List<Entity> entities, List<Relationship> relationships,
            List<Comment> comments) {
        super();
        this.entities = entities;
        this.relationships = relationships;
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

}
