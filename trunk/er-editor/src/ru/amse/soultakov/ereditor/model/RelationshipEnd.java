/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 *
 */
public class RelationshipEnd
{
	/**
	 * 
	 */
	private Entity entity;
	
	/**
	 * 
	 */
	private RelationshipMultiplicity multiplicity;

	/**
	 * @param entity
	 * @param multiplicity
	 */
	public RelationshipEnd(Entity entity, RelationshipMultiplicity multiplicity)
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
	public RelationshipMultiplicity getMultiplicity()
	{
		return multiplicity;
	}

	/**
	 * @param multiplicity
	 */
	public void setMultiplicity(RelationshipMultiplicity multiplicity)
	{
		this.multiplicity = multiplicity;
	}
		
}
