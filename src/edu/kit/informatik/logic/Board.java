package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.ArrayList;
import java.util.Collection;

public class Board {

    private Field[][] board;

    public Board(Field[][] fields) {
        this.board = fields;
    }

    public String getFireRepresentation() {
        StringBuilder result = new StringBuilder();
        for (Field[] row : board) {
            for (Field field : row) {
                FireState fs = field.getFireState();
                if (!(fs == FireState.DRY || fs == FireState.WET)) {
                    result.append(field.getFireState().getDisplayName());
                } else {
                    result.append(FireState.NON_FIRE_FIELD.getDisplayName());
                }
                result.append(",");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
            result.append(System.lineSeparator());
        }
        return result.substring(0, result.length() - 1);
    }

    public String showField(int row, int column) throws GameException {
        this.validateFieldIndex(row, column);
        return this.board[row][column].toString();
    }


    public void placeFireEngine(int row, int column, FireEngine fireEngine) throws GameException {
        this.validateFieldIndex(row, column);
        this.board[row][column].placeFireEngine(fireEngine);
    }


    //TODO: FireEngine has to be diagonal not adjacent at start;

    /**
     * Used for placing new fireEngines when adding new fireEngines to the game, because of the restriction.
     *
     * @param fireEngine
     * @param player
     * @throws GameException
     */
    public void placeFireEngine(FireEngine fireEngine, Player player) throws GameException {
        int row = fireEngine.getRow();
        int column = fireEngine.getColumn();
        this.validateFieldIndex(row, column);
        if (this.isAdjacentToFireStation(fireEngine, player)) {
            this.board[row][column].placeFireEngine(fireEngine);
        } else {
            throw new GameException(String.format(Errors.MISSING_FIRE_STATION, row, column));
        }


    }

    public FireState extinguish(int row, int column) throws GameException {
        this.validateFieldIndex(row, column);
        FireState fs = board[row][column].getFireState();
        switch (fs) {
            case NON_FIRE_FIELD:
                throw new GameException(Errors.CAN_ONLY_EXTINGUISH_FOREST);
            case DRY:
            case LIGHT_FIRE:
                board[row][column].setFireState(FireState.WET);
                return FireState.WET;
            case STRONG_FIRE:
                board[row][column].setFireState(FireState.LIGHT_FIRE);
                return FireState.LIGHT_FIRE;
            case WET:
                throw new GameException(String.format(Errors.CANNOT_EXTINGUISH_WET_FOREST, row, column));
        }
        return null;
    }


    // TODO: make this method private again, it's temporally for tests
    public void validateFieldIndex(int row, int column) throws GameException {
        int rows = this.board.length;
        int columns = this.board[0].length;
        if (row < 0 || row >= rows) {
            throw new GameException(String.format(Errors.INVALID_ROW, rows - 1, row));
        }
        if (column < 0 || column >= columns) {
            throw new GameException(String.format(Errors.INVALID_COLUMN, columns - 1, column));
        }
    }

    private boolean isValidFieldIndex(int row, int column) {
        int rows = this.board.length;
        int columns = this.board[0].length;
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }


    public boolean isAdjacent(FireEngine fe, int row, int column) {
        return row >= fe.getRow() - 1 && row <= fe.getRow() + 1 && column >= fe.getColumn() - 1
            && column <= fe.getColumn() + 1;
    }

    public boolean isAdjacentToFireStation(FireEngine fe, Player player) {
        int row = fe.getRow();
        int column = fe.getColumn();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < this.board.length && j >= 0 && j < this.board[0].length) {
                    if (this.board[i][j].isFireStation(player)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean isAdjacentToPond(FireEngine fe, Player player) {
        int row = fe.getRow();
        int column = fe.getColumn();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < this.board.length && j >= 0 && j < this.board[0].length) {
                    if (this.board[i][j].isPond()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Searches recursively for a path from the start to the goal field. Acts according to the move rules of the
     * assignment. .
     *
     * @param fe             the fire engine to move.
     * @param fromRow        the row the move is performed from.
     * @param fromColumn     the column the move is performed from.
     * @param toRow          the row of the goal.
     * @param toColumn       the column of the goal.
     * @param remainingSteps the remaining steps the fire engine has.
     * @throws GameException if an error occurs during execution, meaning rules are broken.
     */
    public void move(FireEngine fe, int fromRow, int fromColumn, int toRow, int toColumn, int remainingSteps)
        throws GameException {
        Field newField;
        // check if on right field
        if (fromRow == toRow && fromColumn == toColumn) {
            this.placeFireEngine(toRow, toColumn, fe);
            this.board[fe.getRow()][fe.getColumn()].removeFireEngine(fe);
            fe.setRow(toRow);
            fe.setColumn(toColumn);
            fe.moved();

        }

        if (remainingSteps == 0) {
            return;
        }
        // check the four fields to which the engine can theoretically move in one step
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (i == 0 && j == 0) {
                    break;
                }
                if (i != 0) {
                    j = 0;
                }

                int newRow = fromRow + i;
                int newColumn = fromColumn + j;
                if (isValidFieldIndex(newRow, newColumn)) {
                    newField = this.board[newRow][newColumn];
                    // this check (remainingSteps == 1) is required in order to get the correct error message
                    if (remainingSteps == 1) {
                        move(fe, newRow, newColumn, toRow, toColumn, remainingSteps - 1);
                    } else if (remainingSteps > 1 && !(newField.getFireState() == FireState.NON_FIRE_FIELD
                        || newField.getFireState() == FireState.STRONG_FIRE)) {
                        move(fe, newRow, newColumn, toRow, toColumn, remainingSteps - 1);
                    }
                }
            }
        }

    }

    public FireEngine getFireEngineOfPlayer(Player player, String id) throws GameException {
        for (FireEngine fe : this.getFireEngines(player)) {
            if (fe.getId().equals(id)) {
                return fe;
            }
        }
        throw new GameException(String.format(Errors.NO_FIRE_ENGINE, id));
    }

    public Collection<FireEngine> getFireEngines(Player player) {
        Collection<FireEngine> fireEnginesOfPlayer = new ArrayList<>();
        for (Field[] row : this.board) {
            for (Field field : row) {
                Collection<FireEngine> fes = field.getFireEngines(player);
                if (fes != null) {
                    fireEnginesOfPlayer.addAll(fes);
                }

            }
        }

        return fireEnginesOfPlayer;
    }

}
