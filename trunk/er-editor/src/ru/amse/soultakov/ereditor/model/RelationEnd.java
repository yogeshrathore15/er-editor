/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 *
 */
public class RelationEnd
{
	/**
	 * 
	 */
	private Entity entity;
	
	/**
	 * 
	 */
	private RelationMultiplicity multiplicity;

	/**
	 * @param entity
	 * @param multiplicity
	 */
	public RelationEnd(Entity entity, RelationMultiplicity multiplicity)
	{
		super();
		this.entity = entity;
		this.multiplicity = multiplicity;
	}

	/**
	 * @return
	 */
	public Entity getEntity()
	{
		return entity;
	}

	/**
	 * @param entity
	 */
	public void setEntity(Entity entity)
	{
		this.entity = entity;
	}

	/**
	 * @return
	 */
	public RelationMultiplicity getMultiplicity()
	{
		return multiplicity;
	}

	/**
	 * @param multiplicity
	 */
	public void setMultiplicity(RelationMultiplicity multiplicity)
	{
		this.multiplicity = multiplicity;
	}
		
}
