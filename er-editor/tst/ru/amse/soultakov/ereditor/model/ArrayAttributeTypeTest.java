package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ArrayAttributeTypeTest {

    private ArrayAttributeType aat;

    @Before
    public void createArrayAttributeType() {
        aat = new ArrayAttributeType(SimpleAttributeType.INTEGER, 1);
    }

    @Test
    public void testArrayAttributeType() {
        assertSame(aat.getSize(), 1);
        assertSame(aat.getArrayType(), SimpleAttributeType.INTEGER);
    }

    @Test
    public void testSetSize() {
        aat.setSize(5);
        assertSame(aat.getSize(), 5);
    }

    @Test
    public void testSetArrayType() {
        aat.setArrayType(SimpleAttributeType.DOUBLE);
        assertSame(aat.getArrayType(), SimpleAttributeType.DOUBLE);
    }

    @Test
    public void testGetName() {
        assertEquals(aat.getName(), "Integer[1]");
    }

    @Test
    public void testIsCorrectString() {
        assertFalse(aat.isCorrectString(""));
        assertFalse(aat.isCorrectString(null));
    }

    @Test
    public void testMakeCorrectLiteral() {

    }

}
