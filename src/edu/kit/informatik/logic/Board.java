package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.ArrayList;
import java.util.List;

/**
 * The class models the board of the fireBreaker game. It contains methods to interact with the board, as well as
 * checking if a field is even on the board.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class Board {
    /**
     * The array containing all the fields of the board.
     */
    private final Field[][] board;

    /**
     * The constructor.
     *
     * @param board two-dimensional array containing all the fields of the board.
     */
    public Board(Field[][] board) {
        this.board = board;
    }

    /**
     * Checks that the given indices is actually on the board, and returns the result of the toString method of the
     * specified field.
     *
     * @param row    the row of the field to show.
     * @param column the column of the field to show.
     * @return the result of the toString method of the specified field.
     * @throws GameException if the index is invalid.
     */
    public String showField(int row, int column) throws GameException {
        this.validateFieldIndex(row, column);
        return this.board[row][column].toString();
    }

    /**
     * Validates the given indices and tries to place the given fireEngine on the specified field.
     *
     * @param row        the row of the field to place the fireEngine on.
     * @param column     the column of the field to place the fireEngine on.
     * @param fireEngine the fireEngine to place on the field.
     * @throws GameException if the index is not valid or the fireEngine can't be placed on the field.
     */
    public void placeFireEngine(int row, int column, FireEngine fireEngine) throws GameException {
        this.validateFieldIndex(row, column);
        this.board[row][column].placeFireEngine(fireEngine);
    }


    /**
     * This method is used for placing new fireEngines when adding new fireEngines to the game, because of the
     * restrictions that are in place.
     *
     * @param fireEngine the fireEngine to place.
     * @throws GameException if any of the rules of the games are broken.
     */
    public void placeFireEngine(FireEngine fireEngine) throws GameException {
        int row = fireEngine.getRow();
        int column = fireEngine.getColumn();
        this.validateFieldIndex(row, column);
        if (this.board[row][column].getFireState() == FireState.LIGHT_FIRE
            || this.board[row][column].getFireState() == FireState.STRONG_FIRE) {
            throw new GameException(Errors.CANNOT_PLACE_NEW_ENGINE_ON_FIRE);
        }
        if (this.isAdjacentToFireStationOrOptionalPond(fireEngine, fireEngine.getOwningPlayer(), false)) {
            this.board[row][column].placeFireEngine(fireEngine);
        } else {
            throw new GameException(String.format(Errors.MISSING_FIRE_STATION, row, column));
        }
    }

    /**
     * Validates the indices and tries to extinguish the specified field.
     *
     * @param row    the row of the field to be extinguished.
     * @param column the column of the field to be extinguished.
     * @return a pair containing the initial fireState of the field as the first object, and the fireState of the field
     * after extinguishing as the second object.
     * @throws GameException if the indices are invalid or the specified field can't be extinguished.
     */
    public Pair<FireState, FireState> extinguish(int row, int column) throws GameException {
        this.validateFieldIndex(row, column);
        Pair<FireState, FireState> fireStates = new Pair<>(this.board[row][column].getFireState(), null);
        fireStates.setSecond(this.board[row][column].extinguish());
        return fireStates;
    }


    /**
     * Checks whether the given indices are indices on the board.
     *
     * @param row    the row to check for.
     * @param column the column to check for.
     * @throws GameException if either the row or the column is invalid.
     */
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

    /**
     * Checks whether the given indices are indices on the board.
     *
     * @param row    the row to chek for.
     * @param column the column to check for.
     * @return true if the field at the given indices is on the board, false if it is not.
     */
    private boolean isValidFieldIndex(int row, int column) {
        int rows = this.board.length;
        int columns = this.board[0].length;
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    /**
     * Checks whether the given fireEngine is directly adjacent to the field with the given row and column.
     *
     * @param fe     the fireEngine to check the adjacency of.
     * @param row    the row of the field to check.
     * @param column the column of the field to check.
     * @return true if the field is directly adjacent, false if it is not.
     */
    public boolean isDirectlyAdjacent(FireEngine fe, int row, int column) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j = j + 2) {
                int k = j;
                if (i != 0) {
                    k = 0;
                }
                int newRow = fe.getRow() + i;
                int newColumn = fe.getColumn() + k;
                if (isValidFieldIndex(newRow, newColumn) && newRow == row && newColumn == column) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the given fireEngine is adjacent to the fireStation of the specified player or if it is adjacent
     * to any of the fireStations if no player is specified. If the third argument is true, it takes the cooling ponds
     * as valid adjacent fields..
     *
     * @param fe       the fireEngine to check for.
     * @param player   the owner of the fireStation to check. If this is null, the method checks for either of the
     *                 stations.
     * @param withPond true if the method should take cooling ponds into account, false if it should not.
     * @return true if the fireEngine is adjacent to a valid field, false if it is not.
     */
    public boolean isAdjacentToFireStationOrOptionalPond(FireEngine fe, Player player, boolean withPond) {
        int row = fe.getRow();
        int column = fe.getColumn();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (isValidFieldIndex(i, j)) {
                    if (player == null) {
                        if (this.board[i][j].isFireStation() || (withPond && this.board[i][j].isPond())) {
                            return true;
                        }

                    }
                    if (this.board[i][j].isFireStation(player) || (withPond && this.board[i][j].isPond())) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Checks whether the game is won.
     *
     * @return true if the game is won, false if it is not.
     */
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
     * assignment. If a path is found, it places the fireEngine on it and removes it from the initial field.
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
                int k = j;
                if (i != 0) {
                    k = 0;
                }
                int newRow = fromRow + i;
                int newColumn = fromColumn + k;
                if (isValidFieldIndex(newRow, newColumn)) {
                    newField = this.board[newRow][newColumn];
                    // this check is required in order to get the correct error message
                    if (newRow == toRow && newColumn == toColumn) {
                        move(fe, newRow, newColumn, toRow, toColumn, remainingSteps - 1);
                    } else if (!(newField.getFireState() == FireState.NON_BURNABLE_FIELD
                        || newField.getFireState() == FireState.STRONG_FIRE)) {
                        move(fe, newRow, newColumn, toRow, toColumn, remainingSteps - 1);
                    }
                }
            }
        }
    }

    /**
     * Expands the fire in the direction specified by the parameters. If the withReset parameter is true, all the fields
     * are reset so they can be modified for the fire-to-roll command the next round. Each field can only be modified a
     * maximum of one for each fire-to-roll command, so not resetting enables the usage of this method for expanding the
     * fire in all directions and not just in one, that's why the third parameter is required.
     *
     * @param rowOffset    the offset of the row to expand to.
     * @param columnOffset the offset of the column to expand to.
     * @param withReset    the fields are reset for the next fire-to-roll command if true, and are not if false.
     */
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

    /**
     * Searches trough all the fireEngines of the specified player and returns the one matching the given id.
     *
     * @param player the player the fireEngine belongs to.
     * @param id     the id of the fireEngine.
     * @return the FireEngine matching the id, if there is one matching.
     * @throws GameException if there is no FireEngine of the specified player with the specified id.
     */
    public FireEngine getFireEngineOfPlayer(Player player, String id) throws GameException {
        for (FireEngine fe : this.getFireEngines(player)) {
            if (fe.getId().equals(id)) {
                return fe;
            }
        }
        throw new GameException(String.format(Errors.NO_FIRE_ENGINE, id));
    }

    /**
     * Searches all the fields for fireEngines belonging to the specified player and adds them to a list.
     *
     * @param player the player to get the fireEngines of.
     * @return a list containing all the fireEngines of the specified player still in the game.
     */
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

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Field[] row : board) {
            for (Field field : row) {
                FireState fs = field.getFireState();
                if (!(fs == FireState.DRY || fs == FireState.WET)) {
                    result.append(field.getFireState().getDisplayName());
                } else {
                    result.append(FireState.NON_BURNABLE_FIELD.getDisplayName());
                }
                result.append(",");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
            result.append("\n");
        }
        return result.substring(0, result.length() - 1);
    }

}
