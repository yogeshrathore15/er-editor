/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttributeTest {

    @Test
    public void testAttribute() {
        Attribute attribute = new Attribute("0", SimpleAttributeType.INTEGER, true, null);
        assertEquals(attribute.getName(), "0");
    }

    @Test
    public void testSetName() {
        Attribute attribute = new Attribute("0", SimpleAttributeType.CHAR, false, "abc");
        attribute.setName("new name");
        assertEquals(attribute.getName(), "new name");
    }
    
    @Test
    public void testSetType() {
        Attribute attribute = new Attribute("1", SimpleAttributeType.CHAR, false, "");
        assertEquals(attribute.getType(), SimpleAttributeType.CHAR);
        ArrayAttributeType arrayAttributeType = new ArrayAttributeType(SimpleAttributeType.INTEGER, 10);
        attribute.setType(arrayAttributeType);
        assertEquals(attribute.getType(), arrayAttributeType);
    }

}
