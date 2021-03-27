package edu.kit.informatik.commands;

import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

/**
 * This class implements the show-player command. It provides the regex to initially match the input against and is able
 * to execute the input of the terminal with respect to the instance of the the it is instantiated with.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class ShowPlayerCommand extends Command {
    private static final String BASE_REGEX = "^show-player.*$";
    private static final String EXACT_REGEX = "^show-player$";

    /**
     * The constructor.
     *
     * @param game the instance of the game on which the command is executed.
     */
    public ShowPlayerCommand(FireBreaker game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return BASE_REGEX;
    }

    @Override
    public Result executeCommand(String command) {
        if (!command.matches(EXACT_REGEX)) {
            return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
        }
        String result;
        try {
            result = this.getGame().showPlayer();
        } catch (GameException e) {
            return new Result(ResultType.FAILURE, e.getMessage());
        }
        return new Result(ResultType.SUCCESS, result);
    }
}
