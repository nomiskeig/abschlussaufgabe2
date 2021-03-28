package edu.kit.informatik.ui;


import edu.kit.informatik.logic.*;
import edu.kit.informatik.resources.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class models a parser, which is used to create a board of the fireBreaker game based on the program arguments.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class BoardParser {
    private static final String POND_REGEX = "^L$";
    private static final String ARG_REGEX = "^(?:([^,]+),)+([^,]+)$";
    private static final String SINGLE_ARG_REGEX = "[^,]+";
    private final String boardString;
    private boolean hasLightBurningField;
    private boolean hasStrongBurningField;
    private int rows;
    private int columns;

    /**
     * The constructor.
     *
     * @param boardString the textual representation of the board.
     */
    public BoardParser(String boardString) {
        this.boardString = boardString;
    }

    /**
     * Parses boardString and validates it. If the textual representation is valid, creates a new Board instance with
     * based on the textual representation.
     *
     * @return the board based on the textual representation.
     * @throws ParseException if any of the rules for the board specified by the assignment are broken.
     */
    public Board parseAndGetBoard() throws ParseException {
        if (!this.boardString.matches(ARG_REGEX)) {
            throw new ParseException(Errors.INVALID_ARGS);
        }
        int counter = 0;
        Pattern p = Pattern.compile(SINGLE_ARG_REGEX);
        Matcher m = p.matcher(boardString);
        m.find();
        this.rows = getInteger(m.group());
        m.find();
        this.columns = getInteger(m.group());
        if (this.columns < 5 || this.rows < 5) {
            throw new ParseException(String.format(Errors.AT_LEAST_FIVE_ROWS_AND_COLUMNS, this.rows, this.columns));
        }
        if (this.columns % 2 == 0 || this.rows % 2 == 0) {
            throw new ParseException(String.format(Errors.EVEN_NUMBER_ROWS_OR_COLUMNS, this.rows, this.columns));
        }
        Field[][] board = new Field[this.rows][this.columns];
        while (m.find()) {
            counter++;
            if (counter > rows * columns) {
                break;
            }
            int currentRow = (counter - 1) / this.columns;
            int currentColumn = ((counter - 1) % this.columns);
            if (currentRow == 0 && currentColumn == 0) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.A);
            } else if (currentRow == 0 && currentColumn == columns - 1) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.D);
            } else if (currentRow == rows - 1 && currentColumn == 0) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.C);
            } else if (currentRow == rows - 1 && currentColumn == columns - 1) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.B);
            } else if (currentRow == 1 && currentColumn == 1) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.A, currentRow, currentColumn);
            } else if (currentRow == 1 && currentColumn == columns - 2) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.D, currentRow, currentColumn);
            } else if (currentRow == rows - 2 && currentColumn == 1) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.C, currentRow, currentColumn);
            } else if (currentRow == rows - 2 && currentColumn == columns - 2) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.B, currentRow, currentColumn);
            } else if (shouldBePond(currentRow, currentColumn)) {
                if (!m.group().matches(POND_REGEX)) {
                    throw new ParseException(String.format(Errors.BOARD_MISSING_POND, currentRow, currentRow));
                }
                board[currentRow][currentColumn] = new CoolingPondField();


            } else {
                board[currentRow][currentColumn] = parseForestField(m.group());
            }
        }

        if (counter != rows * columns) {
            throw new ParseException(String.format(Errors.INVALID_AMOUNT_OF_ARGUMENTS, counter, rows * columns));
        }
        if (!this.hasStrongBurningField) {
            throw new ParseException(Errors.BOARD_MISSING_STRONG_FIRE);
        }
        if (!this.hasLightBurningField) {
            throw new ParseException(Errors.BOARD_MISSING_LIGHT_FIRE);
        }

        return new Board(board);

    }

    private int getInteger(String s) throws ParseException {
        int result;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Errors.INVALID_ROW_OR_COLUMN, s));
        }
        return result;

    }

    private boolean shouldBePond(int row, int column) {
        return (row == 0 && column == this.columns / 2) || (row == rows / 2 && column == 0) || (row == rows - 1
            && column == this.columns / 2) || (row == rows / 2 && column == columns - 1);
    }

    private FireStationField parseFireStation(String s, Player player) throws ParseException {
        if (s.equals(player.getName())) {
            return new FireStationField(s);
        }
        throw new ParseException(String.format(Errors.BOARD_MISSING_FIRE_STATION, player.getName()));

    }

    private ForestField parseForestField(String s) throws ParseException {
        FireState fs = FireState.getByName(s);
        if (fs == null) {
            throw new ParseException(String.format(Errors.INVALID_FIELD, s));
        }
        if (fs == FireState.STRONG_FIRE) {
            this.hasStrongBurningField = true;
        }
        if (fs == FireState.LIGHT_FIRE) {
            this.hasLightBurningField = true;
        }
        return new ForestField(fs);
    }

    private ForestField parseForestField(String s, Player player, int row, int column) throws ParseException {
        String initialFireEngineId = player.getName() + "0";
        if (s.equals(initialFireEngineId)) {
            FireEngine fe = new FireEngine(initialFireEngineId, row, column);
            return new ForestField(fe);
        }
        throw new ParseException(String.format(Errors.BOARD_MISSING_FIRE_ENGINE, player.getName()));
    }


}
