/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 *
 */
public class Relation
{
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private RelationEnd firstEnd;
	
	/**
	 * 
	 */
	private RelationEnd secondEnd;

	/**
	 * @param name
	 * @param firstEnd
	 * @param secondEnd
	 */
	public Relation(String name, RelationEnd firstEnd, RelationEnd secondEnd)
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
	public RelationEnd getFirstEnd()
	{
		return firstEnd;
	}

	/**
	 * @param firstEnd
	 */
	public void setFirstEnd(RelationEnd firstEnd)
	{
		this.firstEnd = firstEnd;
	}

	/**
	 * @return
	 */
	public RelationEnd getSecondEnd()
	{
		return secondEnd;
	}

	/**
	 * @param secondEnd
	 */
	public void setSecondEnd(RelationEnd secondEnd)
	{
		this.secondEnd = secondEnd;
	}
	
}
