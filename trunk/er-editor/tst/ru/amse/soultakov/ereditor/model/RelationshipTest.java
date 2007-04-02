/*
 * Created on 06.03.2007
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.Test;
import static ru.amse.soultakov.ereditor.model.RelationshipMultiplicity.*;

public class RelationshipTest {

    @Test
    public void testRelationship() {
        Entity entity1 = new Entity("1");
        Entity entity2 = new Entity("2");
        Relationship relationship = new Relationship(new FKRelationshipEnd(
                entity1, ONE_ONLY, ""), new FKRelationshipEnd(entity2, ONE_ONLY, ""));
        assertFalse(EntityTest.hasRelationship(entity1, relationship));
        assertFalse(EntityTest.hasRelationship(entity2, relationship));
    }

    @Test
    public void testGetFirstEnd() {
        Entity entity1 = new Entity("1");
        Entity entity2 = new Entity("2");
        Relationship relationship = new Relationship(new FKRelationshipEnd(
                entity1, ONE_ONLY,""), new FKRelationshipEnd(entity2, ONE_ONLY,""));
        assertSame(entity1,relationship.getFirstEnd().getEntity());
        assertSame(entity2,relationship.getSecondEnd().getEntity());
    }

    @Test
    public void testSetFirstEnd() {
        Relationship relationship = new Relationship(new FKRelationshipEnd(
                new Entity(""), ONE_ONLY, null), new FKRelationshipEnd(new Entity(""), ONE_ONLY, null));
        Entity entity1 = new Entity("1");
        Entity entity2 = new Entity("2");
        relationship.getFirstEnd().setEntity(entity1);
        relationship.getSecondEnd().setEntity(entity2);
        assertSame(entity1,relationship.getFirstEnd().getEntity());
        assertSame(entity2,relationship.getSecondEnd().getEntity());
    }

    @Test
    public void testEqualsObject() {
        Entity entity1 = new Entity("1");
        Entity entity2 = new Entity("2");
        Relationship relationship1 = new Relationship(new FKRelationshipEnd(
                entity1, ONE_ONLY, null), new FKRelationshipEnd(entity2, ONE_ONLY, null));
        Relationship relationship2 = new Relationship(new FKRelationshipEnd(
                entity1, ONE_ONLY, null), new FKRelationshipEnd(entity2, ONE_ONLY, null));
        assertTrue(relationship1.equals(relationship2));
    }

}
