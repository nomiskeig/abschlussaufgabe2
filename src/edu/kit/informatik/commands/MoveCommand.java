package edu.kit.informatik.commands;

import edu.kit.informatik.logic.FireBreaker;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.resources.Messages;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements the move command. It provides the regex to initially match the input against and is able to
 * execute the input of the terminal with respect to the instance of the the it is instantiated with.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class MoveCommand extends Command {
    private static final String BASE_REGEX = "^move.*$";

    private static final String EXACT_REGEX = "^move " + Command.ENGINE_REGEX + "," + Command.DOUBLE_INDEX_REGEX + "$";

    /**
     * The constructor.
     *
     * @param game the instance of the game on which the command is executed.
     */
    public MoveCommand(FireBreaker game) {
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
        int row;
        int column;
        if (m.matches()) {
            try {
                row = Integer.parseInt(m.group(Command.SECOND_GROUP));
                column = Integer.parseInt(m.group(Command.THIRD_GROUP));
            } catch (NumberFormatException e) {
                return new Result(ResultType.FAILURE, Errors.NO_INTEGER);
            }

            try {
                this.getGame().move(m.group(Command.FIRST_GROUP), row, column);
            } catch (GameException e) {
                return new Result(ResultType.FAILURE, e.getMessage());
            }

            return new Result(ResultType.SUCCESS, Messages.OK);


        } else {
            return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
        }
    }
}
