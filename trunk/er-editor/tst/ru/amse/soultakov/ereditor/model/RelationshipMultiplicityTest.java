/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;
import static ru.amse.soultakov.ereditor.model.RelationshipMultiplicity.*;

import org.junit.Test;

/**
 * @author Soultakov Maxim
 *
 */
public class RelationshipMultiplicityTest
{

	/**
	 * Test method for {@link ru.amse.soultakov.ereditor.model.RelationshipMultiplicity#isObligatory()}.
	 */
	@Test
	public void testIsObligatory()
	{
		assertTrue(ONE_ONLY.isObligatory());
		assertTrue(ONE_OR_MORE.isObligatory());
		assertFalse(ZERO_OR_MORE.isObligatory());
		assertFalse(ZERO_OR_ONE.isObligatory());
	}

	/**
	 * Test method for {@link ru.amse.soultakov.ereditor.model.RelationshipMultiplicity#isPlural()}.
	 */
	@Test
	public void testIsPlural()
	{
		assertTrue(ONE_ONLY.isObligatory());
		assertTrue(ZERO_OR_MORE.isObligatory());
		assertTrue(ONE_ONLY.isObligatory());
		assertTrue(ONE_ONLY.isObligatory());
	}

}
