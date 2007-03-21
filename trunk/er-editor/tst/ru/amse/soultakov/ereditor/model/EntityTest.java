/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static ru.amse.soultakov.ereditor.model.RelationshipMultiplicity.ONE_ONLY;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.sun.org.apache.bcel.internal.classfile.Attribute;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.AttributeType;

public class EntityTest {

    private static final String NAME = "name";

    private static final String NAME1 = NAME + "1";

    @Test
    public void testHashCode() {
        Entity entity1 = new Entity(NAME);
        Entity entity2 = new Entity(NAME);
        assertEquals(entity1, entity2);
    }

    @Test
    public void testEntityString() {
        Entity entity1 = new Entity(NAME);
        assertEquals(entity1.getName(), NAME);
    }

    @Test
    public void testEntityStringListOfAttribute() {
        List<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(new Attribute("Attribute1", AttributeType.INTEGER));
        Entity entity = new Entity(NAME, attributes);
        assertEquals(entity.iterator().next().getName(), "Attribute1");
    }

    @Test
    public void testGetName() {
        Entity entity = new Entity(NAME);
        assertEquals(entity.getName(), NAME);
        entity.setName(NAME1);
        assertEquals(entity.getName(), NAME1);
    }

    @Test
    public void testSetName() {
        Entity entity = new Entity(NAME);
        entity.setName(NAME1);
        assertEquals(entity.getName(), NAME1);
    }

    @Test
    public void testIterator() {
        Entity entity = new Entity(NAME);
        entity.addAttribute(new Attribute("1"));
        entity.addAttribute(new Attribute("2"));
        Attribute attribute = new Attribute("3");
        entity.addAttribute(attribute);
        int count = 0;
        for (Attribute a : entity) {
            count++;
            if (count == 3) {
                assertSame(a, attribute);
            }
        }
        assertTrue("Count != 2",count == 3);
        assertTrue("AttributeCount != 3",entity.getAttributes().size() == 3);
    }

    @Test
    public void testRemoveAttributeAttribute() {
        Entity entity = new Entity(NAME);
        Attribute attribute = new Attribute("1");
        entity.addAttribute(attribute);
        entity.addAttribute(new Attribute("2"));
        assertTrue(entity.getAttributes().size() == 2);
        entity.removeAttribute(attribute);
        assertTrue(entity.getAttributes().size() == 1);
        assertTrue(entity.iterator().next() != attribute);
    }

    @Test
    public void testAddRelationship() {
        Entity entity = new Entity(NAME);
        Relationship relationship = createRelationship(entity);
        assertFalse(hasRelationship(entity, relationship));
    }

    private static Relationship createRelationship(Entity entity) {
        final Entity another = new Entity("abc");
        return new Relationship("", new RelationshipEnd(entity,
                        ONE_ONLY), new RelationshipEnd(another, ONE_ONLY));
    }
    
    public static Link createLink(Entity entity) {
        final Comment comment = new Comment("");
        return new Link(entity, comment);
    }

    public static boolean hasRelationship(Entity entity, Relationship relationship) {
        for (Iterator<Relationship> i = entity.relationshipsIterator(); i.hasNext();) {
            if (i.next().equals(relationship)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean hasLink(Entity entity, Link link) {
        for (Iterator<Link> i = entity.linksIterator(); i.hasNext();) {
            if (i.next().equals(link)) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void testRemoveRelationship() {
        Entity entity = new Entity(NAME);
        Relationship relationship = createRelationship(entity);
        entity.removeRelationship(relationship);
        assertFalse(entity.relationshipsIterator().hasNext());
        assertFalse(hasRelationship(entity, relationship));
    }

    @Test
    public void testAddLink() {
        Entity entity = new Entity(NAME);
        Link link = createLink(entity);
        assertTrue(hasLink(entity, link));
    }

    @Test
    public void testRemoveLink() {
        Entity entity = new Entity(NAME);
        Link link = createLink(entity);
        entity.removeLink(link);
        assertFalse("Has link", hasLink(entity, link));
    }

    @Test
    public void testEqualsObject() {
        Entity entity1 = new Entity(NAME);
        Attribute attribute = new Attribute("1");
        entity1.addAttribute(attribute);
        Entity entity2 = new Entity(NAME);
        entity2.addAttribute(attribute);
        assertTrue(entity1.equals(entity2));
    }

}
