package edu.kit.informatik.logic;

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


    public Player turn() {
        activePlayer = this.nextPlayer.get(activePlayer);
        return activePlayer;
    }

    private void newRound() {
        this.firstPlayerThisRound = this.nextPlayer.get(firstPlayerThisRound);
    }

    public void removePlayer(Player player) {
        for (Player playerFromSet : nextPlayer.keySet()) {
            if (nextPlayer.get(playerFromSet) == player) {
                nextPlayer.put(playerFromSet, nextPlayer.get(player));
            }
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

}
