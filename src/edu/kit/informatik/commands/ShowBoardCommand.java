package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

public class ShowBoardCommand extends Command {
    private static final String BASE_REGEX = "^show-board.*$";

    private static final String EXACT_REGEX = "^show-board$";

    public ShowBoardCommand(Game game) {
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
        
        String result = this.getGame().showBoard();
        return new Result(ResultType.SUCCESS, result);
    }
}
