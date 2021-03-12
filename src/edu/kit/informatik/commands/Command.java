package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.ui.Result;

public abstract class Command {

    private Game game;

    public Command(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public abstract String getPattern();

    public abstract Result executeCommand(String command);

    public static Command[] getCommands(Game game) {
        return new Command[] {new MoveCommand(game), new ExtinguishCommand(game), new RefillCommand(game),
            new BuyFireEngineCommand(game), new FireToRollCommand(game), new TurnCommand(game), new ResetCommand(game),
            new ShowBoardCommand(game), new ShowBoardCommand(game), new ShowFieldCommand(game),
            new ShowPlayerCommand(game), new QuitCommand(game)};
    }
}
