package ru.amse.soultakov.ereditor.model;

public class FKRelationshipEnd extends RelationshipEnd {

    private Constraint<FKAttribute> foreignKey;

    public FKRelationshipEnd(Entity entity, RelationshipMultiplicity multiplicity,
            String name, Constraint<FKAttribute> foreignKey) {
        super(entity, multiplicity, name);
        this.foreignKey = foreignKey;
    }

    @Override
    public boolean acceptMultiplicity(RelationshipMultiplicity multiplicity) {
        return multiplicity == RelationshipMultiplicity.ONE_ONLY
                || multiplicity == RelationshipMultiplicity.ONE_OR_MORE;
    }

    @Override
    public Constraint<? extends AbstractAttribute> getConstraint() {
        return foreignKey;
    }

}
