package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.resources.Messages;

import java.util.Collections;
import java.util.List;


public class Game implements FireBreaker {

    private static final int REPUTATION_TO_BUY_ENGINE = 5;


    private final Board board;


    private final Coordinator coordinator;


    public Game(Board board) {
        this.board = board;
        this.coordinator = new Coordinator();
        for (Player player : Player.values()) {
            player.reset();
        }
    }


    @Override
    public void move(String id, int row, int column) throws GameException {
        this.coordinator.validateCommandPossible();
        this.board.validateFieldIndex(row, column);
        FireEngine fe = this.board.getFireEngineOfPlayer(this.coordinator.getActivePlayer(), id);
        if (!fe.canMove()) {
            throw new GameException(String.format(Errors.ALREADY_MADE_ACTION, id));
        }
        if (fe.getActions() < 1) {
            throw new GameException(Errors.ACTION_POINT_NEEDED_TO_MOVE);
        }
        int initialRow = fe.getRow();
        int initialColumn = fe.getColumn();
        if (initialRow == row && initialColumn == column) {
            throw new GameException(Errors.ENGINE_CAN_NOT_STAY_ON_SAME_FIELD);
        }
        this.board.move(fe, initialRow, initialColumn, row, column, 2);
        if (initialRow == fe.getRow() && initialColumn == fe.getColumn()) {
            throw new GameException(String.format(Errors.NO_PATH, initialRow, initialColumn, row, column));
        }

    }

    @Override
    public String extinguish(String id, int row, int column) throws GameException {
        this.coordinator.validateCommandPossible();
        this.board.validateFieldIndex(row, column);
        Pair<FireState, FireState> fireStates;
        FireEngine fe = this.board.getFireEngineOfPlayer(this.coordinator.getActivePlayer(), id);
        if (fe.alreadyExtinguished(row, column)) {
            throw new GameException(Errors.CANNOT_EXTINGUISH_SAME_FIELD_TWICE);
        }
        if (fe.getRow() == row && fe.getColumn() == column) {
            throw new GameException(Errors.CANNOT_EXTINGUISH_OWN_FIELD);
        }
        if (fe.getActions() < 1) {
            throw new GameException(Errors.ACTION_POINT_NEEDED_TO_EXTINGUISH);
        }
        if (fe.getWater() < 1) {
            throw new GameException(Errors.WATER_NEEDED_TO_EXTINGUISH);
        }
        if (board.isDirectlyAdjacent(fe, row, column)) {
            fireStates = this.board.extinguish(row, column);
            fe.extinguished(row, column);
            if (fireStates.getFirst() == FireState.LIGHT_FIRE || fireStates.getFirst() == FireState.STRONG_FIRE) {
                this.coordinator.getActivePlayer().addReputationPoint();
            }
        } else {
            throw new GameException(
                String.format(Errors.FIRE_ENGINE_NOT_NEARBY, row, column, fe.getRow(), fe.getColumn()));
        }
        if (this.board.isWon()) {
            this.coordinator.setOver();
            return Messages.WIN;
        }
        return fireStates.getSecond().getDisplayName() + "," + fe.getActions();
    }


    @Override
    public int refill(String id) throws GameException {
        this.coordinator.validateCommandPossible();
        Player player = this.coordinator.getActivePlayer();
        FireEngine fe = this.board.getFireEngineOfPlayer(this.coordinator.getActivePlayer(), id);
        if (!fe.enoughActionPoints()) {
            throw new GameException(String.format(Errors.NO_ACTION_TO_REFILL, id));
        }
        if (fe.getWater() == 3) {
            throw new GameException(String.format(Errors.ALREADY_FULL, id));
        }
        if (this.board.isAdjacentToFireStationOrOptionalPond(fe, player, true)) {
            fe.refill();
        } else {
            throw new GameException(String.format(Errors.NOT_ADJACENT_REFILL, fe.getRow(), fe.getColumn()));
        }
        return fe.getActions();
    }

    @Override
    public int buyFireEngine(int row, int column) throws GameException {
        this.coordinator.validateCommandPossible();
        Player activePlayer = this.coordinator.getActivePlayer();
        int reputation = activePlayer.getReputationPoints();

        if (reputation < REPUTATION_TO_BUY_ENGINE) {
            throw new GameException(String.format(Errors.NOT_ENOUGH_REPUTATION, activePlayer.getName(), reputation));
        }
        FireEngine newFireEngine = new FireEngine(activePlayer.getName() + activePlayer.getAmountOfFireEngines(), row,
            column);
        board.placeFireEngine(newFireEngine, activePlayer);
        activePlayer.boughtEngine();
        return activePlayer.getReputationPoints();

    }

    @Override
    public String turn() throws GameException {
        this.coordinator.validateCommandPossible();
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
        this.coordinator.validateNotOver();
        Player player = this.coordinator.getActivePlayer();
        List<FireEngine> fes = this.board.getFireEngines(player);
        StringBuilder result = new StringBuilder();
        result.append(player.getName()).append(",").append(player.getReputationPoints());
        Collections.sort(fes);
        for (FireEngine fe : fes) {
            result.append("\n");
            result.append(fe.toString());
        }
        return result.toString();
    }

    @Override
    public String fireToRoll(int dice) throws GameException {
        this.coordinator.canRollFire();

        switch (dice) {
            case 1:
                board.expandFire(-1, 0, false);
                board.expandFire(0, 1, false);
                board.expandFire(1, 0, false);
                board.expandFire(0, -1, true);
                break;
            case 2:
                board.expandFire(-1, 0, true);
                break;
            case 3:
                board.expandFire(0, 1, true);
                break;
            case 4:
                board.expandFire(1, 0, true);
                break;
            case 5:
                board.expandFire(0, -1, true);
                break;
            case 6:
                break;
            default:
                throw new GameException(String.format(Errors.NO_DICE_NUMBER, dice));
        }
        this.coordinator.rolledFire();
        boolean fireEnginesLeft = false;
        Player nextPlayerDecidedByFireToRoll = null;
        for (Player player : Player.values()) {
            for (FireEngine fe : this.board.getFireEngines(player)) {
                fe.reset();
            }

            if (board.getFireEngines(player).isEmpty()) {
                nextPlayerDecidedByFireToRoll = coordinator.removePlayer(player);

            } else {
                fireEnginesLeft = true;

            }
        }

        if (!fireEnginesLeft) {
            return Messages.LOSE;
        }
        if (nextPlayerDecidedByFireToRoll != null) {
            return nextPlayerDecidedByFireToRoll.getName();
        }
        return Messages.OK;
    }


}