/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class LinkTest {

    @Test
    public void testLink() {
        Entity entity = new Entity("");
        Comment comment = new Comment("");
        Link link = new Link(entity, comment);
        assertTrue(EntityTest.hasLink(entity, link));
        assertTrue(CommentTest.hasLink(comment, link));
    }

    @Test
    public void testGetCommentAndEntity() {
        Entity entity = new Entity("");
        Comment comment = new Comment("");
        Link link = new Link(entity, comment);
        assertSame(link.getComment(), comment);
        assertSame(link.getEntity(), entity);
    }

    @Test
    public void testSetCommentAndEntity() {
        Link link = new Link(new Entity("1"), new Comment(""));
        Entity entity = new Entity("new");
        Comment comment = new Comment("new");
        link.setComment(comment);
        link.setEntity(entity);
        assertSame(link.getComment(), comment);
        assertSame(link.getEntity(), entity);
    }
}
