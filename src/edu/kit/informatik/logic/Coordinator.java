package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.HashMap;
import java.util.Map;

public class Coordinator {

    private int activeRound;
    private int turnsThisRound;

    private int playersAlive;

    private Player firstPlayerThisRound;

    private Player activePlayer;

    private Map<Player, Player> nextPlayer;

    public Coordinator() {
        this.activePlayer = Player.A;
        this.firstPlayerThisRound = Player.A;
        this.playersAlive = 4;
        this.activeRound = 1;
        this.turnsThisRound = 4;
        this.nextPlayer = new HashMap<>();
        this.nextPlayer.put(Player.A, Player.B);
        this.nextPlayer.put(Player.B, Player.C);
        this.nextPlayer.put(Player.C, Player.D);
        this.nextPlayer.put(Player.D, Player.A);
    }


    public Player turn() throws GameException {
        this.activeRound++;
        //TODO: enable this.
        //this.checkForFireToRoll();
        // round over
        if (activeRound == this.nextPlayer.size() + 1) {
            this.firstPlayerThisRound = this.nextPlayer.get(firstPlayerThisRound);
            this.activePlayer = firstPlayerThisRound;
            this.activeRound = 1;
            return firstPlayerThisRound;
        }
        activePlayer = this.nextPlayer.get(activePlayer);
        return activePlayer;
    }


    public void removePlayer(Player player) {
        for (Player playerFromSet : nextPlayer.keySet()) {
            if (nextPlayer.get(playerFromSet) == player) {
                nextPlayer.put(playerFromSet, nextPlayer.get(player));
            }
        }
        nextPlayer.remove(player);
    }

    public void checkForFireToRoll() throws GameException {
        if (this.activeRound == this.nextPlayer.size() + 2) {
            throw new GameException(Errors.FIRE_TO_ROLL_NEEDED);
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

}
