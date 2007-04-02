package ru.amse.soultakov.ereditor.model;

public class FKRelationshipEnd extends RelationshipEnd {

    public FKRelationshipEnd(Entity entity, RelationshipMultiplicity multiplicity, String name) {
        super(entity, multiplicity, name);
    }

    @Override
    public boolean acceptMultiplicity(RelationshipMultiplicity multiplicity) {
        return multiplicity == RelationshipMultiplicity.ONE_ONLY || 
               multiplicity == RelationshipMultiplicity.ONE_OR_MORE;
    }

}
