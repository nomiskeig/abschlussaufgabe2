package edu.kit.informatik.commands;

import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.ui.Result;

/**
 * This class implements the base class for all the commands.
 *
 * @author Simon Giek
 * @version 1.0
 */
public abstract class Command {

    /**
     * The index of the first group of a matched matcher.
     */
    public static final int FIRST_GROUP = 1;

    /**
     * The index of the second group of a matched matcher.
     */
    public static final int SECOND_GROUP = 2;

    /**
     * The index of the third group of a matched matcher.
     */
    public static final int THIRD_GROUP = 3;

    /**
     * The regex for two indices in a row.
     */
    public static final String DOUBLE_INDEX_REGEX = "(-?[0-9]+),(-?[0-9]+)";

    /**
     * The regex for a single fire engine.
     */
    public static final String ENGINE_REGEX = "([^\\s]+)";

    /**
     * The instance of the game the command is instantiated with.
     */
    private final FireBreaker game;

    /**
     * The constructor.
     *
     * @param game the instance of the game.
     */
    public Command(FireBreaker game) {
        this.game = game;
    }

    /**
     * Getter for the instance of the game the command was instantiated with.
     *
     * @return the game.
     */
    public FireBreaker getGame() {
        return game;
    }

    /**
     * Getter for the pattern of the command, against which the input is initially matched.
     *
     * @return the pattern of the command.
     */
    public abstract String getPattern();

    /**
     * Executes the command based on the given string. The command is executed on the game the instance of the command
     * was created with.
     *
     * @param command the input from the terminal.
     * @return a result containing the information the be printed out to the terminal.
     */
    public abstract Result executeCommand(String command);

    /**
     * Creates an array containing an instance of each of the available commands and returns it.
     *
     * @param game the instance of the game the commands are instantiated with.
     * @return an array containing one instance of each command.
     */
    public static Command[] getCommands(FireBreaker game) {
        return new Command[] {new MoveCommand(game), new ExtinguishCommand(game), new RefillCommand(game),
            new BuyFireEngineCommand(game), new FireToRollCommand(game), new TurnCommand(game), new ResetCommand(game),
            new ShowBoardCommand(game), new ShowFieldCommand(game), new ShowPlayerCommand(game), new QuitCommand(game)};
    }
}
