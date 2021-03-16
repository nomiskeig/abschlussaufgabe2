package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.*;
import edu.kit.informatik.resources.Errors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //TODO: create better regex
    private final static String ARG_REGEX = "^(?:([^,]+),)+([^,]+)$";
    private final static String SINGLE_ARG_REGEX = "[^,]+";

    private static boolean isRunning = true;


    /**
     * Private constructor to prevent instantiation.
     */
    private Main() {

    }

    public static void main(String[] args) {
        boolean isRunning = true;
        Board board;
        try {
            board = parseArguments(args[0]);
        } catch (ParseException e) {
            Terminal.printError(e.getMessage());
            return;
        }
        Game game = new Game(board);
        Command[] commands = Command.getCommands(game);
        while (isRunning) {
            executeCommand(commands);
        }

    }

    //TODO: MAKE PRIVATE
    public static void executeCommand(Command[] commands) {
        boolean found = false;
        String input = Terminal.readLine();
        for (Command command : commands) {
            if (input.matches(command.getPattern())) {
                found = true;
                Result result = command.executeCommand(input);
                switch (result.getType()) {
                    case SUCCESS:
                        Terminal.printLine(result.getMessage());
                        break;
                    case FAILURE:
                        Terminal.printError(result.getMessage());
                        break;
                    case QUIT:
                        isRunning = false;
                        break;
                    default:
                        Terminal.printError("error");
                        break;
                }
            }

        }
        if (!found) {
            Terminal.printError(Errors.NO_SUCH_COMMAND);
        }
    }

    public static Board parseArguments(String args) throws ParseException {
        if (!args.matches(ARG_REGEX)) {
            throw new ParseException(Errors.INVALID_ARGS);
        }
        int rows;
        int columns;
        int counter = 0;
        Pattern p = Pattern.compile(SINGLE_ARG_REGEX);
        Matcher m = p.matcher(args);
        m.find();
        rows = getInteger(m.group());
        m.find();
        columns = getInteger(m.group());
        Field[][] board = new Field[rows][columns];
        while (m.find()) {
            counter++;
            int currentRow = (counter - 1) / rows;
            int currentColumn = ((counter - 1) % columns);
            if (shouldBeStation(currentRow, currentColumn, rows, columns)) {
                board[currentRow][currentColumn] = parseFireStation(m.group());

            } else if (m.group().equals("L")) {
                board[currentRow][currentColumn] = new CoolingPondField();
            } else if (m.group().matches(FireEngine.INITIAL_TRUCK_REGEX)) {
                board[currentRow][currentColumn] = new ForestField(
                    new FireEngine(m.group(), currentRow, currentColumn));
            } else {
                board[currentRow][currentColumn] = parseForestField(m.group());
            }
        }

        return new Board(board);

    }

    private static int getInteger(String s) throws ParseException {
        int result;
        try {
            result = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(Errors.INVALID_ROW_OR_COLUMN, s));
        }
        return result;

    }

    private static boolean shouldBeStation(int row, int column, int columns, int rows) {
        return (row == 0 && column == 0) || (row == 0 && column == columns - 1) || (row == rows - 1 && column == 0) || (
            row == rows - 1 && column == columns - 1);
    }

    private static FireStationField parseFireStation(String s) {
        return new FireStationField(s);
    }

    private static ForestField parseForestField(String s) throws ParseException {
        FireState fs = FireState.getByName(s);
        if (fs == null) {
            throw new ParseException(String.format(Errors.INVALID_FIELD, s));
        }
        return new ForestField(fs);
    }

}
