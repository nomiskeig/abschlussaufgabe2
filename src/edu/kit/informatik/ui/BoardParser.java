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
    private static final int NUMBER_FIRST_ENGINE = 0;
    /**
     * The minimum rows and columns required for a valid board.
     */
    private static final int MIN_ROWS_AND_COLUMNS = 5;
    private static final int INITIAL_COUNTER = 0;
    private static final int OFFSET_1 = 1;
    private static final int OFFSET_2 = 2;
    private static final int INDEX_0 = 0;
    private static final int INDEX_1 = 1;
    private static final int VALID_REMAINDER = 0;
    private static final int DIVIDER = 2;
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
        int counter = INITIAL_COUNTER;
        Pattern p = Pattern.compile(SINGLE_ARG_REGEX);
        Matcher m = p.matcher(boardString);
        m.find();
        this.rows = getInteger(m.group());
        m.find();
        this.columns = getInteger(m.group());
        if (this.columns < MIN_ROWS_AND_COLUMNS || this.rows < MIN_ROWS_AND_COLUMNS) {
            throw new ParseException(String.format(Errors.AT_LEAST_FIVE_ROWS_AND_COLUMNS, this.rows, this.columns));
        }
        if (this.columns % DIVIDER == VALID_REMAINDER || this.rows % DIVIDER == VALID_REMAINDER) {
            throw new ParseException(String.format(Errors.EVEN_NUMBER_ROWS_OR_COLUMNS, this.rows, this.columns));
        }
        Field[][] board = new Field[this.rows][this.columns];
        while (m.find()) {
            counter++;
            if (counter > rows * columns) {
                break;
            }
            int currentRow = (counter - OFFSET_1) / this.columns;
            int currentColumn = ((counter - OFFSET_1) % this.columns);
            if (currentRow == INDEX_0 && currentColumn == INDEX_0) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.A);
            } else if (currentRow == INDEX_0 && currentColumn == columns - OFFSET_1) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.D);
            } else if (currentRow == rows - OFFSET_1 && currentColumn == 0) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.C);
            } else if (currentRow == rows - OFFSET_1 && currentColumn == columns - OFFSET_1) {
                board[currentRow][currentColumn] = parseFireStation(m.group(), Player.B);
            } else if (currentRow == INDEX_1 && currentColumn == INDEX_1) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.A, currentRow, currentColumn);
            } else if (currentRow == INDEX_1 && currentColumn == columns - OFFSET_2) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.D, currentRow, currentColumn);
            } else if (currentRow == rows - OFFSET_2 && currentColumn == INDEX_1) {
                board[currentRow][currentColumn] = parseForestField(m.group(), Player.C, currentRow, currentColumn);
            } else if (currentRow == rows - OFFSET_2 && currentColumn == columns - OFFSET_2) {
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

    /**
     * Tries to parse the given String into an integer.
     *
     * @param s the string to parse.
     * @return the integer which was parsed from the string.
     * @throws ParseException if the string can't be parsed.
     */
    private int getInteger(String s) throws ParseException {
        int result;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Errors.INVALID_ROW_OR_COLUMN, s));
        }
        return result;

    }

    /**
     * Checks whether there has to be a cooling pond on the board at the specified indices.
     *
     * @param row    the row to check.
     * @param column the column to check.
     * @return true if the field at the indices has to be a pond, false if not.
     */
    private boolean shouldBePond(int row, int column) {
        return (row == INDEX_0 && column == this.columns / DIVIDER) || (row == rows / DIVIDER && column == INDEX_0) || (
            row == rows - OFFSET_1 && column == this.columns / DIVIDER) || (row == rows / DIVIDER
            && column == columns - OFFSET_1);
    }

    /**
     * Creates a new fireStation field if the string matches
     *
     * @param s      the string from the program arguments
     * @param player the player the fireStation belongs to
     * @return a new fireStationField which belongs to the correct player.
     * @throws ParseException if the string cannot be parsed into a fireStation of the required player.
     */
    private FireStationField parseFireStation(String s, Player player) throws ParseException {
        if (s.equals(player.getName())) {
            return new FireStationField(s);
        }
        throw new ParseException(String.format(Errors.BOARD_MISSING_FIRE_STATION, player.getName()));

    }

    /**
     * Parses a normal forestField. Checks that the board has at least a light and strong burning field.
     *
     * @param s the string given by the program arguments.
     * @return the newly created forestField with the correct FireState.
     * @throws ParseException if the given string is not valid for a forest field.
     */
    private ForestField parseForestField(String s) throws ParseException {
        FireState fs = FireState.getByName(s);

        if (fs == null || fs == FireState.NON_BURNABLE_FIELD) {
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

    /**
     * Tries to create a new ForestField with a fireEngine on it based on the value from the program arguments
     *
     * @param s      the string given by the arguments
     * @param player the player to which the fire engine has to belong
     * @param row    the row of the field
     * @param column the column of the field
     * @return the created ForestField with the correct fire engine on it.
     * @throws ParseException if the string given by the arguments does not match the requirements.
     */
    private ForestField parseForestField(String s, Player player, int row, int column) throws ParseException {
        String initialFireEngineId = player.getName() + NUMBER_FIRST_ENGINE;
        if (s.equals(initialFireEngineId)) {
            FireEngine fe = new FireEngine(initialFireEngineId, row, column);
            return new ForestField(fe);
        }
        throw new ParseException(String.format(Errors.BOARD_MISSING_FIRE_ENGINE, player.getName()));
    }


}
