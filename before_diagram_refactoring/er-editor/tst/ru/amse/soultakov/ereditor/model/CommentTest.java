/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class CommentTest {

    @Test
    public void testComment() {
        Comment comment = new Comment("comment");
        assertEquals(comment.getComment(), "comment");
    }

    @Test
    public void testSetComment() {
        Comment comment = new Comment("comment");
        comment.setComment("comment1");
        assertEquals(comment.getComment(), "comment1");
    }

    @Test
    public void testAddLink() {
        Comment comment = new Comment("comment");
        Link link = createLink(comment);
        assertTrue(hasLink(comment, link));
    }
    
    public static boolean hasLink(Comment comment, Link link) {
        for (Iterator<Link> i = comment.linksIterator(); i.hasNext();) {
            if (i.next().equals(link)) {
                return true;
            }
        }
        return false;
    }
    
    private static Link createLink(Comment comment) {
        final Entity entity = new Entity("");
        return new Link(entity, comment);
    }

    @Test
    public void testRemoveLink() {
        Comment comment = new Comment("comment");
        Link link = createLink(comment);
        comment.removeLink(link);
        assertFalse(hasLink(comment, link));
    }

}
