/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 *
 */
public class Relationship
{
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private RelationshipEnd firstEnd;
	
	/**
	 * 
	 */
	private RelationshipEnd secondEnd;

	/**
	 * @param name
	 * @param firstEnd
	 * @param secondEnd
	 */
	public Relationship(String name, RelationshipEnd firstEnd, RelationshipEnd secondEnd)
	{
		super();
		this.name = name;
		this.firstEnd = firstEnd;
		this.secondEnd = secondEnd;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return
	 */
	public RelationshipEnd getFirstEnd()
	{
		return firstEnd;
	}

	/**
	 * @param firstEnd
	 */
	public void setFirstEnd(RelationshipEnd firstEnd)
	{
		this.firstEnd = firstEnd;
	}

	/**
	 * @return
	 */
	public RelationshipEnd getSecondEnd()
	{
		return secondEnd;
	}

	/**
	 * @param secondEnd
	 */
	public void setSecondEnd(RelationshipEnd secondEnd)
	{
		this.secondEnd = secondEnd;
	}
	
}
