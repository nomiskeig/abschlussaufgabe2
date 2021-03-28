package edu.kit.informatik.commands;

import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.resources.Messages;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

/**
 * This class implements reset command. It provides the regex to initially match the input against and is able to
 * execute the input of the terminal.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class ResetCommand extends Command {
    private static final String BASE_REGEX = "^reset.*$";

    private static final String EXACT_REGEX = "^reset$";

    /**
     * The constructor.
     *
     * @param game the instance of the game on which the command is executed.
     */
    public ResetCommand(FireBreaker game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return BASE_REGEX;
    }

    @Override
    public Result executeCommand(String command) {
        if (command.matches(EXACT_REGEX)) {
            this.getGame().reset();
            return new Result(ResultType.SUCCESS, Messages.OK);
        }
        return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
    }
}
