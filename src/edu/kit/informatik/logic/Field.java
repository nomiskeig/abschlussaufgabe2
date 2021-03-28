package edu.kit.informatik.logic;


import java.util.List;

/**
 * This interface describes a field within the game.
 *
 * @author Simon Giek
 * @version 1.0
 */
public interface Field extends Resettable {
    /**
     * Indicates whether the field was already modified by the fire-to-roll command.
     *
     * @return true if the field was modifier, false if it was not.
     */
    boolean alreadyModified();

    /**
     * Resets the alreadyModified field of the field, so that it can be modified once again.
     */
    void resetModified();

    /**
     * Getter for the fireState of the field.
     *
     * @return the fireState of the field.
     */
    FireState getFireState();

    /**
     * Tries to place the specified fireEngine to the field.
     *
     * @param fireEngine the fireEngine to add.
     * @throws GameException if the fireEngine can't be placed on the field, so if either the field is not forest or the
     *                       forest field is burning.
     */
    void placeFireEngine(FireEngine fireEngine) throws GameException;

    /**
     * Removes the specified fireEngine from the field.
     *
     * @param fireEngine the fireEngine to remove.
     */
    void removeFireEngine(FireEngine fireEngine);

    /**
     * Creates a list of all the fireEngines of the specified player currently on the field.
     *
     * @param player the player to get the fireEngines of.
     * @return a list of the fireEngines of the player on this field, null if there aren't any.
     */
    List<FireEngine> getFireEngines(Player player);

    /**
     * Checks whether the field is a cooling pond.
     *
     * @return true if the field is a pond, false if it is not.
     */
    boolean isPond();

    /**
     * Checks whether the field is a fireStation of the player. If the player is null, the method just checks for a
     * fireStation of any player.
     *
     * @param player the player the fireStation belongs to.
     * @return true if the field matches the criteria, false if it does not.
     */
    boolean isFireStation(Player player);

    /**
     * Changes the FireState of the field so it is burning more, as specified in the assignment.
     */
    void burn();

    /**
     * Changes the FireState of the field so it is burning less, as specified in the assignment.
     *
     * @return the new fireState of the field.
     * @throws GameException if the field cannot be extinguished.
     */
    FireState extinguish() throws GameException;


}
