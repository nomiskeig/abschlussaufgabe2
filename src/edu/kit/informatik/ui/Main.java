package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.commands.Command;
import edu.kit.informatik.logic.*;
import edu.kit.informatik.resources.Errors;

/**
 * This class is the main class the the program.
 *
 * @author Simon Giek
 * @version 1.0
 */
public final class Main {
    private static boolean isRunning = true;
    private static boolean reset = false;

    /**
     * Private constructor to prevent instantiation.
     */
    private Main() {

    }

    /**
     * The main method of the program. Creates a boardParser and instantiates new game with the board the parser parses.
     * Invokes the executeCommand method as long as the program is running.
     *
     * @param args the arguments of the program.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            Terminal.printError("the amount of program arguments is invalid.");
            return;
        }
        BoardParser parser = new BoardParser(args[0]);
        Board board;
        try {
            board = parser.parseAndGetBoard();
        } catch (ParseException e) {
            Terminal.printError(e.getMessage());
            return;
        }

        FireBreaker game = new FireBreakerGame(board);
        Command[] commands = Command.getCommands(game);
        while (isRunning) {
            executeCommand(commands);
        }

    }

    /**
     * Takes the input from the terminal and tries to match any of the commands against it. If one matches, it executes
     * the command with respect to the input.
     *
     * @param commands an array containing an instance of each of the commands.
     */
    private static void executeCommand(Command[] commands) {
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
                        Terminal.printError("an unexpected error occurred");
                        break;
                }
            }

        }
        if (!found) {
            Terminal.printError(Errors.NO_SUCH_COMMAND);
        }

    }


}
