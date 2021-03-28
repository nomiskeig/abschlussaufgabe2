package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.HashMap;
import java.util.Map;

/**
 * This class models the coordinator of the game. It contains all the logic for whose turn it currently is and who is
 * alive etc. It manages the turn and fire-to-roll command. Does also have the ability to remove a player from the
 * game.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class Coordinator implements Resettable {
    private int activeRound;
    private boolean isOver;
    private Player firstPlayerThisRound;
    private Player firstPlayerNextRound;
    private Player activePlayer;
    private boolean removedPlayerThisRound;
    private boolean removedStartingPlayerThisRound;
    private final Map<Player, Player> nextPlayer;

    /**
     * The constructor. Initialises all the values so they are correct for the start of the game.
     */
    public Coordinator() {
        this.removedStartingPlayerThisRound = false;
        this.isOver = false;
        this.activePlayer = Player.A;
        this.firstPlayerThisRound = Player.A;
        this.firstPlayerNextRound = Player.B;
        this.activeRound = 1;
        this.nextPlayer = new HashMap<>();
        this.nextPlayer.put(Player.A, Player.B);
        this.nextPlayer.put(Player.B, Player.C);
        this.nextPlayer.put(Player.C, Player.D);
        this.nextPlayer.put(Player.D, Player.A);
    }

    @Override
    public void reset() {
        this.removedStartingPlayerThisRound = false;
        this.isOver = false;
        this.activePlayer = Player.A;
        this.firstPlayerThisRound = Player.A;
        this.firstPlayerNextRound = Player.B;
        this.activeRound = 1;
        this.nextPlayer.clear();
        this.nextPlayer.put(Player.A, Player.B);
        this.nextPlayer.put(Player.B, Player.C);
        this.nextPlayer.put(Player.C, Player.D);
        this.nextPlayer.put(Player.D, Player.A);

    }

    /**
     * Manages the the turn command. Advances the current round, and resets the round correctly if the round is over.
     *
     * @return the next active player after the command.
     */
    public Player turn() {
        this.activeRound++;
        // round over
        if (activeRound == this.nextPlayer.size() + 1) {
            this.removedStartingPlayerThisRound = false;
            this.removedPlayerThisRound = false;
            if (this.nextPlayer.containsKey(firstPlayerThisRound)) {
                this.firstPlayerThisRound = this.nextPlayer.get(firstPlayerThisRound);
            } else {
                this.firstPlayerThisRound = this.firstPlayerNextRound;
            }
            this.activePlayer = firstPlayerThisRound;
            this.activeRound = 0;
            return firstPlayerThisRound;
        }
        if (this.nextPlayer.containsKey(activePlayer)) {
            activePlayer = this.nextPlayer.get(activePlayer);
        }
        return activePlayer;
    }

    /**
     * Setter for the isOver field. Doesn't take a parameter because the game can't be not done once it's done unless
     * it's reset.
     */
    public void setOver() {
        this.isOver = true;
    }

    /**
     * Checks whether the game has already ended.
     *
     * @throws GameException if the game has already ended.
     */
    public void validateNotOver() throws GameException {
        if (this.isOver) {
            throw new GameException(Errors.GAME_OVER);
        }
    }

    /**
     * Checks whether the active round is the first of a completely new round.
     *
     * @return true if the round new, false if it is not.
     */
    public boolean isNewRound() {
        return this.activeRound == 0;
    }

    /**
     * Removes the specified player from the game.
     *
     * @param player the player to remove from the game.
     * @return the player who is active immediately after the fire-to-roll-command.
     */
    public Player removePlayer(Player player) {
        if (!nextPlayer.containsKey(player)) {
            return this.removedPlayerThisRound ? this.activePlayer : null;
        }
        if (this.firstPlayerThisRound == player || this.removedStartingPlayerThisRound) {

            this.activePlayer = nextPlayer.get(activePlayer);
            if (!this.removedStartingPlayerThisRound) {
                this.firstPlayerNextRound = this.nextPlayer.get(this.firstPlayerThisRound);
            } else {
                this.firstPlayerNextRound = this.nextPlayer.get(this.firstPlayerNextRound);
            }
            this.removedStartingPlayerThisRound = true;

        }
        this.removedPlayerThisRound = true;
        for (Player playerFromSet : nextPlayer.keySet()) {
            if (nextPlayer.get(playerFromSet) == player) {
                nextPlayer.put(playerFromSet, nextPlayer.get(player));
            }
        }

        nextPlayer.remove(player);
        if (nextPlayer.isEmpty()) {
            this.setOver();
        }
        return this.activePlayer;
    }

    /**
     * Checks whether a command can currently be executed or if fire-to-roll is needed first.
     *
     * @throws GameException if the fire-to-roll command is required.
     */
    public void validateCommandPossible() throws GameException {
        this.validateNotOver();
        if (this.activeRound == 0) {
            throw new GameException(Errors.FIRE_TO_ROLL_NEEDED);
        }
    }

    /**
     * Checks if it is currently possible to execute the fire-to-roll command.
     *
     * @throws GameException if it is currently not possible to execute the fire-to-roll command.
     */
    public void canRollFire() throws GameException {
        this.validateNotOver();
        if (this.activeRound != 0) {
            throw new GameException(Errors.NO_FIRE_TO_ROLL_POSSIBLE);
        }
    }

    /**
     * Sets the currently active round to 1, which indicates that fire was rolled.
     */
    public void rolledFire() {
        this.activeRound = 1;
    }

    /**
     * Getter for the currently active player.
     *
     * @return the currently active player.
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

}
