/*
 * Created on 13.03.2007
 */
package ru.amse.soultakov.ereditor.view;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DiagramPresentationTest {

    DiagramPresentation dp;
    
    @Before
    public void setUp() throws Exception {
        dp = new DiagramPresentation();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddNewEntityView() {
        EntityView ev1 = dp.addNewEntityView(0, 0);
        EntityView ev2 = dp.addNewEntityView(0, 0);
        assertFalse(ev1.equals(ev2));
        assertNotNull(ev2);
        assertNotNull(ev1);
        assertTrue(dp.getEntityViews().contains(ev1));
        assertTrue(dp.getEntityViews().contains(ev2));
        assertTrue(dp.getEntityViews().size() == 1);
        assertTrue(dp.getCommentViews().size() == 0);
        assertTrue(dp.getLinkViews().size() == 0);
        assertTrue(dp.getRelationshipViews().size() == 0);
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
