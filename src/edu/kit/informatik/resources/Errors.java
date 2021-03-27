package edu.kit.informatik.resources;

public class Errors {
    public static final String INVALID_ARGS = "the program arguments are invalid.";

    public static final String INVALID_ROW_OR_COLUMN =
        "the provided numbers for the amount in the program arguments could not be parsed. You provided %s, which "
            + "is invalid.";

    public static final String NO_INTEGER = "the game only supports numbers up to 2147483647.";

    public static final String INVALID_FIELD = "the field '%s', provided in the program arguments, is invalid.";


    public static final String NO_SUCH_COMMAND = "there is no such command.";

    public static final String INVALID_INPUT = "the provided input is incorrect.";

    public static final String INVALID_ROW
        = "the row is invalid. The row has to be an integer between 0 and %s. You provided %s.";

    public static final String INVALID_COLUMN
        = "the column is invalid. The column has to be an integer between 0 and %s. You provided %s.";

    public static final String NOT_ENOUGH_REPUTATION =
        "can't buy a new fire engine. The currently active player %s only has %s reputation points, but 5 are "
            + "required to buy a new fire engine.";

    public static final String INVALID_PLACEMENT_POND = "can't place the fire engine on a cooling pond field.";

    public static final String INVALID_PLACEMENT_FIRE_STATION = "can't place the fire engine on a fire station field.";

    public static final String MISSING_FIRE_STATION =
        "can't place the new fire engine on field %s %s, as the fire station of the currently active player is not "
            + "adjacent.";

    public static final String CAN_ONLY_EXTINGUISH_FOREST = "only forest fields can be extinguished.";

    public static final String CANNOT_EXTINGUISH_WET_FOREST = "a wet forest can not be extinguished.";

    public static final String ACTION_POINT_NEEDED_TO_EXTINGUISH
        = "the fire engine is missing an action point, and thus can currently not extinguish.";

    public static final String ACTION_POINT_NEEDED_TO_MOVE
        = "the fire engine is missing an action point, and thus can currently not move.";
    public static final String WATER_NEEDED_TO_EXTINGUISH
        = "the fire engine is missing water, and thus can currently not extinguish.";

    public static final String NO_FIRE_ENGINE = "the currently active player does not own a fire engine with id %s.";

    public static final String FIRE_ENGINE_NOT_NEARBY =
        "the fire engine can not extinguish the field %s %s, because the fire engine is currently placed on field "
            + "%s %s, which is not adjacent";

    public static final String ALREADY_FULL
        = "the fire engine with id %s can currently not refill, as it already has the maximum capacity of water.";

    public static final String NO_ACTION_TO_REFILL
        = "the fire engine with id %s has no actions left, this can currently not refill.";

    public static final String NOT_ADJACENT_REFILL =
        "the engine is not adjacent to a pond or the fire station of the active player, thus the fire engine can "
            + "not refill when on field %s %s";

    public static final String NO_ENGINE_ON_LIGHT
        = "the fire engine cannot be moved, because engines can't be moved to fields with light fire.";

    public static final String NO_ENGINE_ON_STRONG
        = "the fire engine cannot be moved, because engines can't be moved to fields with strong fire.";

    public static final String ENGINE_CAN_NOT_STAY_ON_SAME_FIELD
        = "fire engines can only move to fields other than their initial position.";

    public static final String NO_PATH
        = "there is no valid path from field %s %s to field %s %s, so the fire engine cannot move there.";

    public static final String ALREADY_MADE_ACTION =
        "the fire engine with id %s can no longer move this turn because it already extinguished a field or "
            + "refilled.";

    public static final String FIRE_TO_ROLL_NEEDED =
        "the fire-to-roll command has to be used at the start of each round before the first player of the round "
            + "can do something.";

    public static final String SOMETHING_WRONG = "something internally went wrong";
    public static final String NO_DICE_NUMBER = "the supplied number '%s' is not a number from a six sided dice.";

    public static final String GAME_OVER = "the game has already ended, thus this command cannot be used.";

    public static final String NO_FIRE_TO_ROLL_POSSIBLE
        = "can not roll fire at the moment, as it is not the beginning of a round.";

    public static final String CANNOT_EXTINGUISH_OWN_FIELD
        = "a fire engine can not extinguish the field it's currently placed on";

    public static final String CANNOT_EXTINGUISH_SAME_FIELD_TWICE
        = "a fire engine can not extinguish the same field twice in one turn.";

    public static final String AT_LEAST_FIVE_ROWS_AND_COLUMNS =
        "at least five rows and columns are required for the game. There are only %s rows and %s columns provided "
            + "in the program arguments";

    public static final String EVEN_NUMBER_ROWS_OR_COLUMNS =
        "an uneven number the rows and columns respectively is required. There are %s rows and %s columns provided "
            + "in the program arguments.";

    public static final String BOARD_MISSING_LIGHT_FIRE
        = "the board provided does not contain at least on field with light fire.";

    public static final String BOARD_MISSING_STRONG_FIRE
        = "the board provided does not contain at least on field with strong fire.";

    public static final String BOARD_MISSING_POND = "there is a pond missing at row %s and column %s.";

    public static final String BOARD_MISSING_FIRE_STATION = "the fire station of player %s is missing.";

    public static final String BOARD_MISSING_FIRE_ENGINE = "there is no fire engine of player %s on the correct field.";

    public static final String CANNOT_PLACE_NEW_ENGINE_ON_FIRE
        = "a new fire engine can't be placed on a burning forest";
}