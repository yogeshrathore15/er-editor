package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;
import static ru.amse.soultakov.ereditor.model.SimpleAttributeType.*;

import org.junit.Test;

public class SimpleAttributeTypeTest {

    @Test
    public void testGetName() {
        assertEquals(INTEGER.getName(), "Integer");
        assertEquals(CHAR.getName(), "Char");
        assertEquals(DOUBLE.getName(), "Double");
    }

    @Test
    public void testIsCorrectString() {
        assertTrue(INTEGER.isCorrectString("1"));
        assertFalse(INTEGER.isCorrectString(""));
        assertFalse(INTEGER.isCorrectString("abc"));
        assertFalse(INTEGER.isCorrectString(null));

        assertTrue(CHAR.isCorrectString("a"));
        assertFalse(CHAR.isCorrectString(""));
        assertFalse(CHAR.isCorrectString("abc"));
        assertFalse(CHAR.isCorrectString(null));

        assertTrue(DOUBLE.isCorrectString("1"));
        assertTrue(DOUBLE.isCorrectString("1.0"));
        assertFalse(DOUBLE.isCorrectString(""));
        assertFalse(DOUBLE.isCorrectString("abc"));
        assertFalse(DOUBLE.isCorrectString(null));
    }

    @Test
    public void testMakeCorrectLiteral() {

    }

}
