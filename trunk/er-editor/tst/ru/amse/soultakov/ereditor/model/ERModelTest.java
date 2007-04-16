/*
 * Created on 12.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ERModelTest {

    private ERModel erModel;

    @Before
    public void setUp() throws Exception {
        erModel = new ERModel();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddNewEntity() {
        Entity e1 = erModel.addNewEntity();
        Entity e2 = erModel.addNewEntity();
        assertFalse(e1.equals(e2));
        assertTrue(erModel.getEntities().contains(e2));
        assertTrue(erModel.getEntities().contains(e1));
        assertTrue(erModel.getEntities().size() == 2);
        assertTrue(erModel.getComments().size() == 0);
        assertTrue(erModel.getLinks().size() == 0);
        assertTrue(erModel.getRelationships().size() == 0);
    }

    @Test
    public void testAddNewComment() {
        Comment c1 = erModel.addNewComment();
        Comment c2 = erModel.addNewComment();
        assertFalse(c1.equals(c2));
        assertTrue(erModel.getComments().contains(c1));
        assertTrue(erModel.getComments().contains(c2));
        assertTrue(erModel.getComments().size() == 2);
        assertTrue(erModel.getEntities().size() == 0);
        assertTrue(erModel.getLinks().size() == 0);
        assertTrue(erModel.getRelationships().size() == 0);
    }

    @Test
    public void testAddNewRealtionship() {
        Entity e1 = erModel.addNewEntity();
        Entity e2 = erModel.addNewEntity();
        Relationship r = erModel.addNewRealtionship(e1, e2);
        assertFalse(e1.acceptRelationshipWith(e2));
        assertFalse(e2.acceptRelationshipWith(e1));
        assertTrue(erModel.getRelationships().contains(r));
        assertTrue(erModel.getRelationships().size() == 1);
        assertTrue(erModel.getComments().size() == 0);
        assertTrue(erModel.getEntities().size() == 2);
        assertTrue(erModel.getLinks().size() == 0);
        try {
            erModel.addNewRealtionship(e1, e2);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testAddNewRelationshipWithIllegalArgument() {
        try {
            erModel.addNewRealtionship(null, null);
            fail();
        } catch (IllegalArgumentException e) {
        }
        Entity e1 = new Entity("");
        Entity e2 = new Entity(" ");
        try {
            erModel.addNewRealtionship(e1, e2);
            fail();
        } catch (IllegalArgumentException e) {
        }
        Entity e3 = erModel.addNewEntity();
        try {
            erModel.addNewRealtionship(e3, e3);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void testAddNewLink() {
        Entity e = erModel.addNewEntity();
        Comment c = erModel.addNewComment();
        Link l = erModel.addNewLink(e, c);
        assertFalse(e.acceptLinkWith(c));
        assertFalse(c.acceptLinkWith(e));
        assertTrue(erModel.getLinks().contains(l));
        assertTrue(erModel.getLinks().size() == 1);
        assertTrue(erModel.getRelationships().size() == 0);
        assertTrue(erModel.getComments().size() == 1);
        assertTrue(erModel.getEntities().size() == 1);
        try {
            erModel.addNewLink(e, c);
            fail();
        } catch (IllegalArgumentException ex) {
        }
    }

    @Test
    public void testRemoveEntity() {
        Entity e = erModel.addNewEntity();
        assertTrue(erModel.removeEntity(e));
        assertTrue(erModel.getEntities().size() == 0);
        assertFalse(erModel.removeEntity(e));

        Entity e1 = erModel.addNewEntity();
        Entity e2 = erModel.addNewEntity();
        Comment c = erModel.addNewComment();
        erModel.addNewRealtionship(e2, e1);
        erModel.addNewLink(e1, c);

        assertTrue(erModel.removeEntity(e1));
        assertTrue(erModel.getEntities().size() == 1);
        assertTrue(erModel.getComments().size() == 1);
        assertEquals("Wrong relationships size.", erModel.getRelationships().size(),
                0);
        assertTrue(erModel.getLinks().size() == 0);
    }

    @Test
    public void testRemoveComment() {
        Comment c = erModel.addNewComment();
        assertTrue(erModel.removeComment(c));
        assertTrue(erModel.getComments().size() == 0);
        assertFalse(erModel.removeComment(c));

        Comment c1 = erModel.addNewComment();
        Entity e = erModel.addNewEntity();
        erModel.addNewLink(e, c1);

        assertTrue(erModel.removeComment(c1));

        assertTrue(erModel.getEntities().size() == 1);
        assertTrue(erModel.getComments().size() == 0);
        assertTrue(erModel.getLinks().size() == 0);
        assertTrue(erModel.getRelationships().size() == 0);
    }

    @Test
    public void testRemoveRelationship() {
        Entity e1 = erModel.addNewEntity();
        Entity e2 = erModel.addNewEntity();
        Relationship r = erModel.addNewRealtionship(e2, e1);
        assertTrue(erModel.removeRelationship(r));
        assertTrue(erModel.getRelationships().size() == 0);
        assertTrue(erModel.getEntities().size() == 2);
        assertTrue(erModel.getComments().size() == 0);
        assertTrue(erModel.getLinks().size() == 0);
        assertFalse(erModel.removeRelationship(r));
    }

    @Test
    public void testRemoveLink() {
        Entity e = erModel.addNewEntity();
        Comment c = erModel.addNewComment();
        Link l = erModel.addNewLink(e, c);
        assertTrue(erModel.removeLink(l));
        assertTrue(erModel.getLinks().size() == 0);
        assertTrue(erModel.getEntities().size() == 1);
        assertTrue(erModel.getComments().size() == 1);
        assertTrue(erModel.getRelationships().size() == 0);
        assertFalse(erModel.removeLink(l));
    }

}
