package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.ui.Result;

public class ResetCommand extends Command {
    private static final String BASE_REGEX = "^reset.*$";

    public ResetCommand(Game game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return BASE_REGEX;
    }

    @Override
    public Result executeCommand(String command) {
        return null;
    }
}
