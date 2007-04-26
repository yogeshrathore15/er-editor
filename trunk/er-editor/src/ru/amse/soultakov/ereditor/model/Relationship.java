package ru.amse.soultakov.ereditor.model;


/**
 * @author Soultakov Maxim
 */
public class Relationship {

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
    public Relationship(RelationshipEnd firstEnd, RelationshipEnd secondEnd) {
        if ((firstEnd == null) || (secondEnd == null)) {
            throw new IllegalArgumentException("Arguments must be non null values");
        }
        this.firstEnd = firstEnd;
        this.secondEnd = secondEnd;
    }

    /**
     * @return
     */
    public RelationshipEnd getFirstEnd() {
        return firstEnd;
    }

    /**
     * @param firstEnd
     */
    public void setFirstEnd(RelationshipEnd firstEnd) {
        this.firstEnd = firstEnd;
    }

    /**
     * @return
     */
    public RelationshipEnd getSecondEnd() {
        return secondEnd;
    }

    /**
     * @param secondEnd
     */
    public void setSecondEnd(RelationshipEnd secondEnd) {
        this.secondEnd = secondEnd;
    }

    @Override
    public String toString() {
        return firstEnd.getEntity() + " " + secondEnd.getEntity().toString();
    }

}
