/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author sma
 * 
 */
public class Comment {
    
    /**
     * 
     */
    private String comment;

    private Set<Link> links = new HashSet<Link>();
    
    /**
     * @param comment
     */
    public Comment(String comment) {
        super();
        this.comment = comment;
    }

    /**
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public boolean addLink(Link link) {
        return links.add(link);
    }
    
    public boolean removeLink(Link link) {
        return links.remove(link);
    }
    
    public Iterator<Link> linksIterator() {
        return links.iterator();
    }

}