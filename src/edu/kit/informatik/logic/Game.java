package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Game implements FireBreaker {

    private static final int REPUTATION_TO_BUY_ENGINE = 5;


    private final Board board;


    private final Coordinator coordinator;


    public Game(Board board) {
        this.board = board;
        this.coordinator = new Coordinator();
    }


    @Override
    public void move(String id, int row, int column) throws GameException {
        this.board.validateFieldIndex(row, column);
        FireEngine fe = this.board.getFireEngineOfPlayer(this.coordinator.getActivePlayer(), id);
        if (!fe.canMove()) {
            throw new GameException(String.format(Errors.ALREADY_MADE_ACTION, id));
        }
        int initialRow = fe.getRow();
        int initialColumn = fe.getColumn();
        if (initialRow == row && initialColumn == column) {
            throw new GameException(Errors.ENGINE_CAN_NOT_STAY_ON_SAME_FIELD);
        }
        this.board.move(fe, initialRow, initialColumn, row, column, 2);
        if (initialColumn == fe.getRow() && initialColumn == fe.getColumn()) {
            throw new GameException(String.format(Errors.NO_PATH, initialRow, initialColumn, row, column));
        }

    }

    @Override
    public String extinguish(String id, int row, int column) throws GameException {
        this.board.validateFieldIndex(row, column);
        FireState newFireState;
        FireEngine fe = this.board.getFireEngineOfPlayer(this.coordinator.getActivePlayer(), id);
        if (fe.getActions() < 1) {
            throw new GameException(Errors.ACTION_POINT_NEEDED_TO_EXTINGUISH);
        }
        if (fe.getWater() < 1) {
            throw new GameException(Errors.WATER_NEEDED_TO_EXTINGUISH);
        }
        if (board.isAdjacent(fe, row, column)) {
            newFireState = this.board.extinguish(row, column);
            fe.extinguished();
            this.coordinator.getActivePlayer().addReputationPoint();
        } else {
            throw new GameException(
                String.format(Errors.FIRE_ENGINE_NOT_NEARBY, row, column, fe.getRow(), fe.getColumn()));
        }
        return newFireState.getDisplayName() + "," + fe.getActions();
    }


    @Override
    public int refill(String id) throws GameException {
        Player player = this.coordinator.getActivePlayer();
        FireEngine fe = this.board.getFireEngineOfPlayer(this.coordinator.getActivePlayer(), id);
        if (!fe.enoughActionPoints()) {
            throw new GameException(String.format(Errors.NO_ACTION_TO_REFILL, id));
        }
        if (fe.getWater() == 3) {
            throw new GameException(String.format(Errors.ALREADY_FULL, id));
        }
        if (this.board.isAdjacentToPond(fe, player) || this.board.isAdjacentToPond(fe, player)) {
            fe.refill();
        } else {
            throw new GameException(String.format(Errors.NOT_ADJACENT_REFILL, fe.getRow(), fe.getColumn()));
        }
        return fe.getActions();
    }

    @Override
    public int buyFireEngine(int row, int column) throws GameException {
        Player activePlayer = this.coordinator.getActivePlayer();
        int reputation = activePlayer.getReputationPoints();

        if (reputation < REPUTATION_TO_BUY_ENGINE) {
            throw new GameException(String.format(Errors.NOT_ENOUGH_REPUTATION, activePlayer.getName(), reputation));
        }
        FireEngine newFireEngine = new FireEngine(activePlayer.getName() + activePlayer.getAmountOfFireEngines(), row,
            column);
        board.placeFireEngine(newFireEngine, activePlayer);
        activePlayer.setReputationPoints(reputation - REPUTATION_TO_BUY_ENGINE);
        return activePlayer.getReputationPoints();

    }

    @Override
    public String turn() throws GameException {
        return this.coordinator.turn().getName();
    }

    @Override
    public String reset() {
        return null;
    }

    @Override
    public String showBoard() {
        return board.getFireRepresentation();
    }

    @Override
    public String showField(int row, int column) throws GameException {
        return board.showField(row, column);

    }

    @Override
    public String showPlayer() throws GameException {
        if (1 == 0) {
            throw new GameException("wtf");
        }
        Player player = this.coordinator.getActivePlayer();
        List<FireEngine> fes = this.board.getFireEngines(player);
        StringBuilder result = new StringBuilder();
        result.append(player.getName() + "," + player.getReputationPoints());
        Collections.sort(fes);
        for (FireEngine fe : fes) {
            result.append("\n");
            result.append(fe.toString());
        }
        return result.toString();
    }


}