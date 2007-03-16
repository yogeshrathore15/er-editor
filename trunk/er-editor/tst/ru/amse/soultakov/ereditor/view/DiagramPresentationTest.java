/*
 * Created on 13.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DiagramPresentationTest {

    Diagram diagram;
    
    @Before
    public void setUp() throws Exception {
        diagram = new Diagram();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddNewEntityView() {
        EntityView ev1 = diagram.addNewEntityView(0, 0);
        EntityView ev2 = diagram.addNewEntityView(0, 0);
        assertFalse(ev1.equals(ev2));
        assertNotNull(ev2);
        assertNotNull(ev1);
        assertTrue(diagram.getEntityViews().contains(ev1));
        assertTrue(diagram.getEntityViews().contains(ev2));
        assertTrue(diagram.getEntityViews().size() == 1);
        assertTrue(diagram.getCommentViews().size() == 0);
        assertTrue(diagram.getLinkViews().size() == 0);
        assertTrue(diagram.getRelationshipViews().size() == 0);
    }

    @Test
    public void testAddNewCommentView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddNewRelationshipView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAddNewLinkView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveEntityView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveRelationshipView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveCommentView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testRemoveLinkView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetEntityView() {
//        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetCommentView() {
//        fail("Not yet implemented"); // TODO
    }

}
