package edu.kit.informatik.commands;


import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the refill command. It provides the regex to initially match the input against and is able to
 * execute the input of the terminal with respect to the instance of the the it is instantiated with.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class RefillCommand extends Command {
    private static final String BASE_REGEX = "^refill.*$";

    private static final String EXACT_REGEX = "^refill " + Command.ENGINE_REGEX + "$";

    /**
     * The constructor.
     *
     * @param game the instance of the game on which the command is executed.
     */
    public RefillCommand(FireBreaker game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return BASE_REGEX;
    }

    @Override
    public Result executeCommand(String command) {
        Pattern p = Pattern.compile(EXACT_REGEX);
        Matcher m = p.matcher(command);

        if (m.matches()) {
            int result;
            try {
                result = this.getGame().refill(m.group(FIRST_GROUP));
            } catch (GameException e) {
                return new Result(ResultType.FAILURE, e.getMessage());
            }
            return new Result(ResultType.SUCCESS, String.valueOf(result));

        } else {
            return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
        }
    }
}
