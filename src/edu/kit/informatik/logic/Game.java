package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;


public class Game implements FireBreaker {

    private static final int REPUTATION_TO_BUY_ENGINE = 5;


    private final Board board;


    private final Coordinator coordinator;


    public Game(Board board) {
        this.board = board;
        this.coordinator = new Coordinator();
    }


    @Override
    public String move(String id, int row, int column) {
        return null;
    }

    @Override
    public String extinguish(String id, int row, int column) throws GameException {
        this.board.validateFieldIndex(row, column);
        boolean foundEngine = false;
        FireState newFireState = FireState.NON_FIRE_FIELD;
        for (FireEngine fe : board.getFireEngines(this.coordinator.getActivePlayer())) {
            if (fe.getId().equals(id)) {
                foundEngine = true;
                if (fe.getActions() < 1) {
                    throw new GameException(Errors.ACTION_POINT_NEEDED_TO_EXTINGUISH);
                }
                if (fe.getWater() < 1) {
                    throw new GameException(Errors.WATER_NEEDED_TO_EXTINGUISH);
                }
                if (row >= fe.getRow() - 1 && row <= fe.getRow() + 1 && column >= fe.getColumn() - 1
                    && column <= fe.getColumn() + 1) {
                    newFireState = this.board.extinguish(row, column);
                    fe.extinguished();
                } else {
                    throw new GameException(
                        String.format(Errors.FIRE_ENGINE_NOT_NEARBY, row, column, fe.getRow(), fe.getColumn()));
                }
            }
        }
        if (!foundEngine) {
            throw new GameException(String.format(Errors.NO_FIRE_ENGINE, id));
        }
        return newFireState.getDisplayName();
    }

    @Override
    public int refill(String id) {
        return 0;
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
        board.placeFireEngine(row, column, newFireEngine, activePlayer);
        activePlayer.setReputationPoints(reputation - REPUTATION_TO_BUY_ENGINE);
        return activePlayer.getReputationPoints();

    }

    @Override
    public String turn() {
        return null;
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
    public String showPlayer(String id) {
        return null;
    }


}