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
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((firstEnd == null) ? 0 : firstEnd.hashCode());
        result = PRIME * result + ((secondEnd == null) ? 0 : secondEnd.hashCode());
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
        final Relationship other = (Relationship) obj;
        if (firstEnd == null) {
            if (other.firstEnd != null) {
                return false;
            }
        } else if (!firstEnd.equals(other.firstEnd)) {
            return false;
        }
        if (secondEnd == null) {
            if (other.secondEnd != null) {
                return false;
            }
        } else if (!secondEnd.equals(other.secondEnd)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstEnd.getEntity() + " " + secondEnd.getEntity().toString();
    }

}
