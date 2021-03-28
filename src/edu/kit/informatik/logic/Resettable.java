package edu.kit.informatik.logic;

/**
 * This interface describes a class which can be reset to the initial state at the beginning of the game.
 *
 * @author Simon Giek
 * @version 1.0
 */
public interface Resettable {
    /**
     * Resets the object so it has the same state as the beginning of the game.
     */
    void reset();
}
