package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

public class TurnCommand extends Command {
    private static final String BASE_REGEX = "^turn.*$";
    private static final String EXACT_COMMAND = "^turn$";

    public TurnCommand(Game game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return BASE_REGEX;
    }

    @Override
    public Result executeCommand(String command) {
        if (!command.matches(EXACT_COMMAND)) {
            return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
        }
        String result;
        try {
            result = this.getGame().turn();
        } catch (GameException e) {
            return new Result(ResultType.FAILURE, e.getMessage());
        }
        return new Result(ResultType.SUCCESS, result);
    }
}
