package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

public class QuitCommand extends Command {
    private static final String BASE_REGEX = "^quit.*$";
    private static final String EXACT_REGEX = "^quit$";

    public QuitCommand(Game game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return BASE_REGEX;
    }

    @Override
    public Result executeCommand(String command) {
        if (command.matches(EXACT_REGEX)) {
            return new Result(ResultType.QUIT);
        }
        return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
    }
}
