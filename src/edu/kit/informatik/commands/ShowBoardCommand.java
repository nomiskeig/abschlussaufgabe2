package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.ui.Result;

public class ShowBoardCommand extends Command {
    public ShowBoardCommand(Game game) {
        super(game);
    }

    @Override
    public String getPattern() {
        return null;
    }

    @Override
    public Result executeCommand(String command) {
        return null;
    }
}
