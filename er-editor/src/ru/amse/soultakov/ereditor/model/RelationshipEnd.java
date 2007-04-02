/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author sma
 * 
 */
public abstract class RelationshipEnd {

    private String name;

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
    public RelationshipEnd(Entity entity, RelationshipMultiplicity multiplicity,
            String name) {
        super();
        if ((entity == null) || (multiplicity == null)) {
            throw new IllegalArgumentException("Arguments must be non-null values");
        } else if (!acceptMultiplicity(multiplicity)) {
            throw new IllegalArgumentException(
                    "This relationship doesn't accept this multiplicity");
        }
        this.name = name;
        this.entity = entity;
        this.multiplicity = multiplicity;
    }

    /**
     * @return
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * @param entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * @return
     */
    public RelationshipMultiplicity getMultiplicity() {
        return multiplicity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param multiplicity
     */
    public void setMultiplicity(RelationshipMultiplicity multiplicity) {
        if (!acceptMultiplicity(multiplicity)) {
            throw new IllegalArgumentException(
                    "This relationship doesn't accept this multiplicity");
        }
        this.multiplicity = multiplicity;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((entity == null) ? 0 : entity.hashCode());
        result = PRIME * result
                + ((multiplicity == null) ? 0 : multiplicity.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RelationshipEnd other = (RelationshipEnd) obj;
        if (entity == null) {
            if (other.entity != null) {
                return false;
            }
        } else if (!entity.equals(other.entity)) {
            return false;
        }
        if (multiplicity == null) {
            if (other.multiplicity != null) {
                return false;
            }
        } else if (!multiplicity.equals(other.multiplicity)) {
            return false;
        }
        return true;
    }

    public abstract boolean acceptMultiplicity(RelationshipMultiplicity multiplicity);

}
