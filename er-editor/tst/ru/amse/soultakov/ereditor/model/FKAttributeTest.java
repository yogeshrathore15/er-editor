/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Soultakov Maxim
 *
 */
public class FKAttributeTest
{

	/**
	 * Test method for {@link ru.amse.soultakov.ereditor.model.FKAttribute#getType()}.
	 */
	@Test
	public void testGetType()
	{
		Entity entity = new Entity("name2");
		Attribute attr = new Attribute("name3", SimpleAttributeType.INTEGER, false, "def_val");
		entity.addAttribute(attr);
		FKAttribute fka = new FKAttribute("name", false, "no", entity, attr);
		assertSame(fka.getType(), attr.getType());
	}

	/**
	 * Test method for {@link ru.amse.soultakov.ereditor.model.FKAttribute#isNotNull()}.
	 */
	@Test
	public void testIsNotNull()
	{
		Entity entity = new Entity("name2");
		Attribute attr = new Attribute("name3", SimpleAttributeType.INTEGER, false, "def_val");
		entity.addAttribute(attr);
		FKAttribute fka = new FKAttribute("name", false, "no", entity, attr);
		assertEquals(fka.isNotNull(), true);
	}

	/**
	 * Test method for {@link ru.amse.soultakov.ereditor.model.FKAttribute#getEntity()}.
	 */
	@Test
	public void testGetEntity()
	{
		Entity entity = new Entity("name2");
		Attribute attr = new Attribute("name3", SimpleAttributeType.INTEGER, false, "def_val");
		entity.addAttribute(attr);
		FKAttribute fka = new FKAttribute("name", false, "no", entity, attr);
		assertEquals(fka.getEntity(), entity);
	}

	/**
	 * Test method for {@link ru.amse.soultakov.ereditor.model.FKAttribute#getAttribute()}.
	 */
	@Test
	public void testGetAttribute()
	{
		Entity entity = new Entity("name2");
		Attribute attr = new Attribute("name3", SimpleAttributeType.INTEGER, false, "def_val");
		entity.addAttribute(attr);
		FKAttribute fka = new FKAttribute("name", false, "no", entity, attr);
		assertEquals(fka.getAttribute(), attr);
	}

}
