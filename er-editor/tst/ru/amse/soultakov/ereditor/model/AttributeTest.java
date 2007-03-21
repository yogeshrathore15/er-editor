/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttributeTest {

    @Test
    public void testAttribute() {
        Attribute attribute = new Attribute("0", AttributeType.INTEGER, true);
        assertEquals(attribute.getName(), "0");
    }

    @Test
    public void testSetName() {
        Attribute attribute = new Attribute("0", AttributeType.CHAR, false);
        attribute.setName("new name");
        assertEquals(attribute.getName(), "new name");
    }

}
