package edu.kit.informatik.ui;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.commands.Command;
import edu.kit.informatik.commands.ParseException;
import edu.kit.informatik.logic.*;
import edu.kit.informatik.resources.Errors;


public class Main {


    private static boolean isRunning = true;
    private static boolean reset = false;


    /**
     * Private constructor to prevent instantiation.
     */
    private Main() {

    }

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
            if (reset) {
                try {
                    board = parser.parseAndGetBoard();
                } catch (ParseException e) {
                    Terminal.printError(e.getMessage());
                }
                game = new FireBreakerGame(board);
                commands = Command.getCommands(game);
                reset = false;
            }
            executeCommand(commands);
        }

    }

    //TODO PRIVATE
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
                        //return result.getMessage();
                        break;
                    case FAILURE:
                        Terminal.printError(result.getMessage());
                        //return result.getMessage();
                        break;
                    case QUIT:
                        isRunning = false;
                        break;
                    case RESET:
                        reset = true;
                        Terminal.printLine(result.getMessage());
                        break;
                    default:
                        Terminal.printError("Error");
                        break;
                }
            }

        }
        if (!found) {
            Terminal.printError(Errors.NO_SUCH_COMMAND);
        }

    }


}
