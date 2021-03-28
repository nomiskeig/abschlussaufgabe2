package edu.kit.informatik.logic;

/**
 * This enum describes the FireState of a field.
 *
 * @author Simon Giek
 * @version 1.0
 */
public enum FireState {
    /**
     * The dry state of a field.
     */
    DRY("d"),
    /**
     * The wet state of a field.
     */
    WET("w"),
    /**
     * The light fire state of a field.
     */
    LIGHT_FIRE("+"),
    /**
     * The strong fire state of a field.
     */
    STRONG_FIRE("*"),
    /**
     * The fireState fields that can't burn.
     */
    NON_BURNABLE_FIELD("x");


    private final String displayName;

    /**
     * The constructor.
     *
     * @param displayName the text to display for the fireState.
     */
    FireState(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Getter for the display name of the fireState.
     *
     * @return the display name of the fireState.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Searches all FireStates for the one with the matching name and returns it.
     *
     * @param name the display name of the fireState to search.
     * @return the fireState with the specified display name, null if there is no such state.
     */
    public static FireState getByName(String name) {
        for (FireState fs : FireState.values()) {
            if (fs.displayName.equals(name)) {
                return fs;
            }
        }
        return null;
    }
}
