package edu.kit.informatik.logic;

/**
 * This interface contains all the methods required for an implementation of the fireBreaker game.
 *
 * @author Simon Giek
 * @version 1.0
 */
public interface FireBreaker extends Resettable {

    /**
     * Moves the fireEngine of the active player with the specified id to the specified field.
     *
     * @param id     the id of the fireEngine to move.
     * @param row    the row of the field to move the fireEngine to.
     * @param column the column of the field to move the fireEngine to.
     * @throws GameException if an error occurs during moving the engine, for example if there is no valid way to the
     *                       specified field.
     */
    void move(String id, int row, int column) throws GameException;

    /**
     * Extinguishes the specified field with the fireEngine which has the specified id.
     *
     * @param id     the id of the fireEngine to extinguish with.
     * @param row    the row of the field to extinguish.
     * @param column the column of the field to extinguish.
     * @return the output for the terminal.
     * @throws GameException if an error occurs during execution, for example the field is already wet.
     */
    String extinguish(String id, int row, int column) throws GameException;

    /**
     * Refills the fireEngine with the specified id. The fireEngine has to belong to the active player.
     *
     * @param id the id of the fireEngine to refill.
     * @return the remaining amount of actions of the fireEngine just refilled.
     * @throws GameException if an error occurs during execution.
     */
    int refill(String id) throws GameException;

    /**
     * Buys a new fireEngine for the currently active player and places it on the specified field.
     *
     * @param row    the row of the field to place the engine on.
     * @param column the column of the field to place the engine on.
     * @return the remaining amount of reputation points of the player.
     * @throws GameException if an error occurs during execution.
     */
    int buyFireEngine(int row, int column) throws GameException;

    /**
     * Advances the round of the game.
     *
     * @return the id of the player whose turn it is after the execution.
     * @throws GameException if an error occurs during execution.
     */
    String turn() throws GameException;

    /**
     * Calculates a textual representation of the board according to the assignment.
     *
     * @return the textual representation of the board.
     */
    String showBoard();

    /**
     * Calculates a textual representation of the specified field according to the assignment.
     *
     * @param row    the row of the field to show.
     * @param column the column of the field to show.
     * @return the textual representation of the specified field.
     * @throws GameException if either of the indices is invalid.
     */
    String showField(int row, int column) throws GameException;

    /**
     * Calculates a textual representation of the currently active player.
     *
     * @return the textual representation of the currently active player.
     * @throws GameException if the command cannot be used at the current state of the game.
     */
    String showPlayer() throws GameException;

    /**
     * Calculates the expansion of the fire on the board according to the assignment.
     *
     * @param dice the dice roll to expand the fire based on.
     * @return "lose" if the game is lost. "OK" if no player dies during execution. If any player dies, returns the id
     * of the player whose turn it is immediately after the execution,
     * @throws GameException if an error occurs during execution.
     */
    String fireToRoll(int dice) throws GameException;

}
