package ru.amse.soultakov.ereditor.model;

public class PKRelationshipEnd extends RelationshipEnd {

    public PKRelationshipEnd(Entity entity, RelationshipMultiplicity multiplicity,
            String name) {
        super(entity, multiplicity, name);
    }

    @Override
    public boolean acceptMultiplicity(RelationshipMultiplicity multiplicity) {
        return multiplicity == RelationshipMultiplicity.ZERO_OR_MORE
                || multiplicity == RelationshipMultiplicity.ZERO_OR_ONE;
    }
    
    @Override
    public Constraint<? extends AbstractAttribute> getConstraint() {
        return getEntity().getPrimaryKey();
    }

}
