package edu.kit.informatik.commands;

import edu.kit.informatik.logic.FireEngine;
import edu.kit.informatik.logic.Game;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RefillCommand extends Command {
    private static final String BASE_REGEX = "^refill.*$";

    private static final String EXACT_REGEX = "^refill " + Command.ENGINE_REGEX + "$";

    public RefillCommand(Game game) {
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
                result = this.getGame().refill(m.group(1));
            } catch (GameException e) {
                return new Result(ResultType.FAILURE, e.getMessage());
            }
            return new Result(ResultType.SUCCESS, String.valueOf(result));

        } else {
            return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
        }
    }
}
