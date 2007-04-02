/**
 * 
 */
package ru.amse.soultakov.ereditor.model;

/**
 * @author Soultakov Maxim
 */
public enum RelationshipMultiplicity {
    /**
     * 
     */
    ONE_ONLY(true, false),
    
    /**
     * 
     */
    ONE_OR_MORE(true, true),
    
    /**
     * 
     */
    ZERO_OR_ONE(false, false),


    /**
     * 
     */
    ZERO_OR_MORE(false, true);

    /**
     * 
     */
    boolean obligatory;

    /**
     * 
     */
    boolean plural;

    /**
     * @param obligatory
     * @param plural
     */
    private RelationshipMultiplicity(boolean obligatory, boolean plural) {
        this.obligatory = obligatory;
        this.plural = plural;
    }

    /**
     * @return
     */
    public boolean isObligatory() {
        return obligatory;
    }

    /**
     * @return
     */
    public boolean isPlural() {
        return plural;
    }
}
