package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Field[][] board;

    public Board(Field[][] board) {
        this.board = board;
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
            result.append("\n");
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
        if (this.isAdjacentToFireStationOrOptionalPond(fireEngine, player, false)) {
            this.board[row][column].placeFireEngine(fireEngine);
        } else {
            throw new GameException(String.format(Errors.MISSING_FIRE_STATION, row, column));
        }
    }

    public Pair<FireState, FireState> extinguish(int row, int column) throws GameException {
        Pair<FireState, FireState> fireStates = new Pair<>(this.board[row][column].getFireState(), null);
        this.validateFieldIndex(row, column);

        fireStates.setSecond(this.board[row][column].extinguish());
        return fireStates;
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


    public boolean isDirectlyAdjacent(FireEngine fe, int row, int column) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (i != 0) {
                    j = 0;
                }
                int newRow = fe.getRow() + i;
                int newColumn = fe.getColumn() + j;
                if (isValidFieldIndex(newRow, newColumn) && newRow == row && newColumn == column) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAdjacentToFireStationOrOptionalPond(FireEngine fe, Player player, boolean withPond) {
        int row = fe.getRow();
        int column = fe.getColumn();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < this.board.length && j >= 0 && j < this.board[0].length) {
                    if (this.board[i][j].isFireStation(player) || (withPond && this.board[i][j].isPond())) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean isWon() {
        boolean win = true;
        for (Field[] row : this.board) {
            for (Field field : row) {
                if (field.getFireState() == FireState.LIGHT_FIRE || field.getFireState() == FireState.STRONG_FIRE) {
                    win = false;
                }
            }
        }
        return win;
    }

    /**
     * Searches recursively for a path from the start to the goal field. Acts according to the move rules of the
     * assignment.
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
        // engine was already moved
        if (fe.getRow() == toRow && fe.getColumn() == toColumn) {
            return;
        }
        // check if on right field
        if (fromRow == toRow && fromColumn == toColumn) {
            this.placeFireEngine(toRow, toColumn, fe);
            this.board[fe.getRow()][fe.getColumn()].removeFireEngine(fe);
            fe.setRow(toRow);
            fe.setColumn(toColumn);
            fe.moved();
            return;
        }
        if (remainingSteps == 0) {
            return;
        }

        // check the four fields to which the engine can theoretically move in one step
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j = j + 2) {
                if (i != 0) {
                    j = 0;
                }

                int newRow = fromRow + i;
                int newColumn = fromColumn + j;
                if (isValidFieldIndex(newRow, newColumn)) {
                    newField = this.board[newRow][newColumn];
                    // this check is required in order to get the correct error message
                    if (newRow == toRow && newColumn == toColumn) {
                        move(fe, newRow, newColumn, toRow, toColumn, remainingSteps - 1);
                    } else if (!(newField.getFireState() == FireState.NON_FIRE_FIELD
                        || newField.getFireState() == FireState.STRONG_FIRE)) {
                        move(fe, newRow, newColumn, toRow, toColumn, remainingSteps - 1);
                    }
                }
            }
        }
    }

    public void expandFire(int rowOffset, int columnOffset, boolean withReset) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (board[i][j].getFireState() == FireState.STRONG_FIRE && !board[i][j].alreadyModified()) {
                    if (isValidFieldIndex(i + rowOffset, j + columnOffset) && !board[i + rowOffset][j + columnOffset]
                        .alreadyModified()
                        && board[i + rowOffset][j + columnOffset].getFireState() != FireState.STRONG_FIRE) {
                        board[i + rowOffset][j + columnOffset].burn();
                    }
                } else if (board[i][j].getFireState() == FireState.LIGHT_FIRE && !board[i][j].alreadyModified()) {
                    board[i][j].burn();
                }
            }
        }
        if (withReset) {
            for (Field[] row : this.board) {
                for (Field field : row) {
                    field.resetModified();
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

    public List<FireEngine> getFireEngines(Player player) {
        List<FireEngine> fireEnginesOfPlayer = new ArrayList<>();
        for (Field[] row : this.board) {
            for (Field field : row) {
                List<FireEngine> fes = field.getFireEngines(player);
                if (fes != null) {
                    fireEnginesOfPlayer.addAll(fes);
                }

            }
        }
        return fireEnginesOfPlayer;
    }

}
