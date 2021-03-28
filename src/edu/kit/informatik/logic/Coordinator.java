package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.HashMap;
import java.util.Map;

public class Coordinator {

    private int activeRound;

    private boolean isOver;

    private Player firstPlayerThisRound;
    private Player firstPlayerNextRound;

    private Player activePlayer;

    private boolean removedPlayerThisRound;
    private boolean removedStartingPlayerThisRound;

    private Map<Player, Player> nextPlayer;

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

    public void reset() {
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

    public void setOver() {
        this.isOver = true;
    }

    public void validateNotOver() throws GameException {
        if (this.isOver) {
            throw new GameException(Errors.GAME_OVER);
        }
    }

    public boolean isNewRound() {
        return this.activeRound == 0;
    }


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

    public void validateCommandPossible() throws GameException {
        this.validateNotOver();
        if (this.activeRound == 0) {
            throw new GameException(Errors.FIRE_TO_ROLL_NEEDED);
        }
    }

    public void canRollFire() throws GameException {
        this.validateNotOver();
        if (this.activeRound != 0) {
            throw new GameException(Errors.NO_FIRE_TO_ROLL_POSSIBLE);
        }
    }

    public void rolledFire() {
        this.activeRound = 1;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

}
