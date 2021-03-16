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

    /**
     * Used for placing new fireEngines when adding new fireEngines to the game, because of the restriction.
     *
     * @param row
     * @param column
     * @param fireEngine
     * @param pn
     * @throws GameException
     */
    public void placeFireEngine(int row, int column, FireEngine fireEngine, Player pn) throws GameException {
        this.validateFieldIndex(row, column);
        boolean foundStation = false;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && i < this.board.length && j >= 0 && j < this.board[0].length) {
                    if (this.board[i][j].toString().equals(pn.getName())) {
                        foundStation = true;
                    }
                }
            }
        }
        if (foundStation) {
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
