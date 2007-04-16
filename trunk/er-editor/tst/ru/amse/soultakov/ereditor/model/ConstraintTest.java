/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

/**
 * @author Soultakov Maxim
 * 
 */
public class ConstraintTest
{

	/**
     * Test method for
     * {@link ru.amse.soultakov.ereditor.model.Constraint#Constraint(java.util.Set)}.
     */
	@Test
	public void testConstraintSetOfT()
	{
		HashSet<AbstractAttribute> set = new HashSet<AbstractAttribute>(Arrays.asList(new Attribute("1", null, false, null),
								new Attribute("2", null, false, null),
								new Attribute("3", null, false, null)));
		Constraint<AbstractAttribute> c = new Constraint<AbstractAttribute>(
				set);
		assertTrue(set != c.getAttributes());
		assertTrue(c.getAttributes().containsAll(set));
	}

	/**
     * Test method for
     * {@link ru.amse.soultakov.ereditor.model.Constraint#add(ru.amse.soultakov.ereditor.model.AbstractAttribute)}.
     */
	@Test
	public void testAdd()
	{
		Constraint<Attribute> c = new Constraint<Attribute>();
		Attribute attribute = new Attribute(null, null, false, null);
		c.add(attribute);
		assertTrue(c.contains(attribute));
	}

	/**
     * Test method for
     * {@link ru.amse.soultakov.ereditor.model.Constraint#remove(ru.amse.soultakov.ereditor.model.AbstractAttribute)}.
     */
	@Test
	public void testRemove()
	{
		Constraint<Attribute> c = new Constraint<Attribute>();
		Attribute attribute = new Attribute(null, null, false, null);
		c.add(attribute);
		assertSame(c.getAttributes().size(), 1);
		c.remove(attribute);
		assertSame(c.getAttributes().size(), 0);
	}

	/**
     * Test method for
     * {@link ru.amse.soultakov.ereditor.model.Constraint#contains(ru.amse.soultakov.ereditor.model.AbstractAttribute)}.
     */
	@Test
	public void testContains()
	{
		Constraint<Attribute> c = new Constraint<Attribute>();
		Attribute attribute = new Attribute(null, null, false, null);
		c.add(attribute);
		assertTrue(c.contains(attribute));
	}

}
