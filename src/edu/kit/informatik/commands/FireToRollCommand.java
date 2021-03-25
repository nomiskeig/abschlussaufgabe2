package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FireToRollCommand extends Command {
    private static final String BASE_REGEX = "^fire-to-roll.*$";
    private static final String EXACT_REGEX = "^fire-to-roll (\\d)$";

    public FireToRollCommand(Game game) {
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
            String result;
            try {
                int dice = Integer.parseInt(m.group(1));
                result = this.getGame().fireToRoll(dice);
            } catch (NumberFormatException e) {
                return new Result(ResultType.FAILURE, Errors.NO_INTEGER);
            } catch (GameException e) {
                return new Result(ResultType.FAILURE, e.getMessage());
            }
            return new Result(ResultType.SUCCESS, result);
        }
        return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
    }
}
