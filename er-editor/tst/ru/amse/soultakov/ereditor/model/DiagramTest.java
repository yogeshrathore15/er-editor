/*
 * Created on 12.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DiagramTest {


    private Diagram diagram = new Diagram();

    @Before
    public void setUp() throws Exception {
        diagram = new Diagram();
    }

    @After
    public void tearDown() throws Exception {
        diagram = null;
    }

    @Test
    public void testAddNewEntity() {
        Entity e1 = diagram.addNewEntity();
        Entity e2 = diagram.addNewEntity();
        assertFalse(e1.equals(e2));
        assertTrue(diagram.getEntities().contains(e2));
        assertTrue(diagram.getEntities().contains(e1));
        assertTrue(diagram.getEntities().size() == 2);
        assertTrue(diagram.getComments().size() == 0);
        assertTrue(diagram.getLinks().size() == 0);
        assertTrue(diagram.getRelationships().size() == 0);
    }
    
    @Test
    public void testAddNewComment() {
        Comment c1 = diagram.addNewComment();
        Comment c2 = diagram.addNewComment();
        assertFalse(c1.equals(c2));
        assertTrue(diagram.getComments().contains(c1));
        assertTrue(diagram.getComments().contains(c2));
        assertTrue(diagram.getComments().size() == 2);
        assertTrue(diagram.getEntities().size() == 0);
        assertTrue(diagram.getLinks().size() == 0);
        assertTrue(diagram.getRelationships().size() == 0);
    }

    @Test
    public void testAddNewRealtionship() {
        Entity e1 = diagram.addNewEntity();
        Entity e2 = diagram.addNewEntity();
        Relationship r = diagram.addNewRealtionship(e1, e2);
        assertFalse(e1.acceptRelationshipWith(e2));
        assertFalse(e2.acceptRelationshipWith(e1));
        assertTrue(diagram.getRelationships().contains(r));
        assertTrue(diagram.getRelationships().size() == 1);
        assertTrue(diagram.getComments().size() == 0);
        assertTrue(diagram.getEntities().size() == 2);
        assertTrue(diagram.getLinks().size() == 0);
        try {
            diagram.addNewRealtionship(e1, e2);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }
    
    @Test
    public void testAddNewRelationshipWithIllegalArgument() {
        try {
            diagram.addNewRealtionship(null, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        Entity e1 = new Entity("");
        Entity e2 = new Entity(" ");
        try {
            diagram.addNewRealtionship(e1, e2);
            fail();
        } catch (IllegalArgumentException e) {
        }
        Entity e3 = diagram.addNewEntity();
        Entity e4 = diagram.addNewEntity();
        try {
            diagram.addNewRealtionship(e3, e3);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testAddNewLink() {
        Entity e = diagram.addNewEntity();
        Comment c = diagram.addNewComment();
        Link l = diagram.addNewLink(e, c);
        assertFalse(e.acceptLinkWith(c));
        assertFalse(c.acceptLinkWith(e));
        assertTrue(diagram.getLinks().contains(l));
        assertTrue(diagram.getLinks().size() == 1);
        assertTrue(diagram.getRelationships().size() == 0);
        assertTrue(diagram.getComments().size() == 1);
        assertTrue(diagram.getEntities().size() == 1);
        try {
            diagram.addNewLink(e, c);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testRemoveEntity() {
        Entity e = diagram.addNewEntity();
        assertTrue(diagram.removeEntity(e));
        assertTrue(diagram.getEntities().size() == 0);
        assertFalse(diagram.removeEntity(e));
        
        Entity e1 = diagram.addNewEntity();
        Entity e2 = diagram.addNewEntity();
        Comment c = diagram.addNewComment();
        diagram.addNewRealtionship(e2, e1);
        diagram.addNewLink(e1, c);
        
        assertTrue(diagram.removeEntity(e1));
        assertTrue(diagram.getEntities().size() == 1);
        assertTrue(diagram.getComments().size() == 1);
        assertTrue(diagram.getRelationships().size() == 0);
        assertTrue(diagram.getLinks().size() == 0);
    }

    @Test
    public void testRemoveComment() {
        Comment c = diagram.addNewComment();
        assertTrue(diagram.removeComment(c));
        assertTrue(diagram.getComments().size() == 0);
        assertFalse(diagram.removeComment(c));
        
        Comment c1 = diagram.addNewComment();
        Entity e = diagram.addNewEntity();
        diagram.addNewLink(e, c1);
        
        assertTrue(diagram.removeComment(c1));
        
        assertTrue(diagram.getEntities().size() == 1);
        assertTrue(diagram.getComments().size() == 0);
        assertTrue(diagram.getLinks().size() == 0);
        assertTrue(diagram.getRelationships().size() == 0);
    }

    @Test
    public void testRemoveRelationship() {
        Entity e1 = diagram.addNewEntity();
        Entity e2 = diagram.addNewEntity();
        Relationship r = diagram.addNewRealtionship(e2, e1);
        assertTrue(diagram.removeRelationship(r));
        assertTrue(diagram.getRelationships().size() == 0);
        assertTrue(diagram.getEntities().size() == 2);
        assertTrue(diagram.getComments().size() == 0);
        assertTrue(diagram.getLinks().size() == 0);        
        assertFalse(diagram.removeRelationship(r));
    }

    @Test
    public void testRemoveLink() {
        Entity e = diagram.addNewEntity();
        Comment c = diagram.addNewComment();
        Link l = diagram.addNewLink(e, c);
        assertTrue(diagram.removeLink(l));
        assertTrue(diagram.getLinks().size() == 0);
        assertTrue(diagram.getEntities().size() == 1);
        assertTrue(diagram.getComments().size() == 1);
        assertTrue(diagram.getRelationships().size() == 0);
        assertFalse(diagram.removeLink(l));
    }

}
