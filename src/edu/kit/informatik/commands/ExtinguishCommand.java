package edu.kit.informatik.commands;

import edu.kit.informatik.logic.Game;
import edu.kit.informatik.logic.GameException;
import edu.kit.informatik.resources.Errors;
import edu.kit.informatik.ui.Result;
import edu.kit.informatik.ui.ResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtinguishCommand extends Command {
    private static final String BASE_REGEX = "^extinguish.*$";

    private static final String EXACT_REGEX = "^extinguish ([^\\s]+)," + Command.DOUBLE_INDEX_REGEX + "$";

    public ExtinguishCommand(Game game) {
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
                row = Integer.parseInt(m.group(2));
                column = Integer.parseInt(m.group(3));
            } catch (NumberFormatException e) {
                return new Result(ResultType.FAILURE, Errors.NO_INTEGER);
            }
            String result;
            try {
                result = this.getGame().extinguish(m.group(1), row, column);
            } catch (GameException e) {
                return new Result(ResultType.FAILURE, e.getMessage());
            }

            return new Result(ResultType.SUCCESS, result);


        } else {
            return new Result(ResultType.FAILURE, Errors.INVALID_INPUT);
        }
    }
}
