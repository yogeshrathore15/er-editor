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
    }

    @Test
    public void testAddNewComment() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddNewRealtionship() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddNewLink() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveEntity() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveComment() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveRelationship() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveLink() {
        fail("Not yet implemented");
    }

}
