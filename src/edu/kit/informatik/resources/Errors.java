package edu.kit.informatik.resources;

/**
 * This class contains the error messages of the program. I modeled this class after the example solution of assignment
 * 5. The usage with String.format is also taken from there.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class Errors {
    /**
     * Error if the program arguments are invalid.
     */
    public static final String INVALID_ARGS = "the program arguments are invalid.";

    /**
     * Error if the either the amount of rows or the amount of columns is invalid. Format with the invalid number.
     */
    public static final String INVALID_ROW_OR_COLUMN = "the provided numbers for the amount of rows or columns in the "
        + "program arguments could not be parsed. You provided %s, which is invalid.";

    /**
     * Error if the amount of program arguments does not match that required for the field. Format with the actual
     * number of fields provided and the amount actually needed.
     */
    public static final String INVALID_AMOUNT_OF_ARGUMENTS
        = "the program arguments provided contain information for %s fields, but there are %s needed.";

    /**
     * Error if the program arguments provide either not enough rows or columns. Format with the provided amount of rows
     * and columns.
     */
    public static final String AT_LEAST_FIVE_ROWS_AND_COLUMNS = "at least five rows and columns "
        + "are required for the game. There are only %s rows and %s columns provided in the program arguments";

    /**
     * Error if the program arguments provide either an even number of rows or an even number of columns. Format with
     * the amount of rows and columns provided.
     */
    public static final String EVEN_NUMBER_ROWS_OR_COLUMNS = "an uneven number the rows and "
        + "columns respectively is required. There are %s rows and %s columns provided in the program arguments.";

    /**
     * Error if the program arguments are missing a field with light fire.
     */
    public static final String BOARD_MISSING_LIGHT_FIRE
        = "the board provided does not contain at least on field with light fire.";

    /**
     * Error if the program arguments are missing a field with strong fire.
     */
    public static final String BOARD_MISSING_STRONG_FIRE
        = "the board provided does not contain at least on field with strong fire.";

    /**
     * Error if the program arguments are missing a cooling pond. Format with the row and column of the field where the
     * pond is missing.
     */
    public static final String BOARD_MISSING_POND = "there is a pond missing at row %s and column %s.";

    /**
     * Error if the program arguments are missing a fire station. Format with the id of the player whose station is
     * missing.
     */
    public static final String BOARD_MISSING_FIRE_STATION = "the fire station of player %s is missing.";

    /**
     * Error if the program arguments are missing a fire engine. Format with the id of the player whose fire engine is
     * missing.
     */
    public static final String BOARD_MISSING_FIRE_ENGINE = "there is no fire engine of player %s on the correct field.";
    /**
     * Error if the integer could not be parsed.
     */
    public static final String NO_INTEGER = "the game only supports numbers up to 2147483647.";

    /**
     * Error if the textual representation contains an invalid field. Format with the invalid field.
     */
    public static final String INVALID_FIELD = "the field '%s', provided in the program arguments, is invalid.";

    /**
     * Error if there is no such command.
     */
    public static final String NO_SUCH_COMMAND = "there is no such command.";

    /**
     * Error if the provided input is incorrect.
     */
    public static final String INVALID_INPUT = "the provided input is incorrect.";

    /**
     * Error if the provided row is invalid. Format with the maximum possible row.
     */
    public static final String INVALID_ROW
        = "the row is invalid. The row has to be an integer between 0 and %s. You provided %s.";

    /**
     * Error if the provided column is invalid. Format with the maximum possible column.
     */
    public static final String INVALID_COLUMN
        = "the column is invalid. The column has to be an integer between 0 and %s. You provided %s.";

    /**
     * Error if the player does not have enough reputation points to buy a new fire engine. Format with the id of the
     * active player and their current reputation points.
     */
    public static final String NOT_ENOUGH_REPUTATION = "can't buy a new fire engine. The currently active "
        + "player %s only has %s reputation points, but 5 are required to buy a new fire engine.";

    /**
     * Error if the player tries to place a fire engine on a cooling pond.
     */
    public static final String INVALID_PLACEMENT_POND = "can't place the fire engine on a cooling pond field.";

    /**
     * Error if the player tries to place a fire engine on a fire station.
     */
    public static final String INVALID_PLACEMENT_FIRE_STATION = "can't place the fire engine on a fire station field.";

    /**
     * Error if the player tries to place a newly bought engine on an invalid field. Format with the row and column of
     * the field.
     */
    public static final String MISSING_FIRE_STATION = "can't place the new fire engine on field %s %s, "
        + "as the fire station of the currently active player is not adjacent.";

    /**
     * Error if the player tries to extinguish a non forest field.
     */
    public static final String CAN_ONLY_EXTINGUISH_FOREST = "only forest fields can be extinguished.";

    /**
     * Error if the player tries to extinguish a wet forest, which is not possible.
     */
    public static final String CANNOT_EXTINGUISH_WET_FOREST = "a wet forest can not be extinguished.";

    /**
     * Error if the fire engine is missing an action point to extinguish.
     */
    public static final String ACTION_POINT_NEEDED_TO_EXTINGUISH
        = "the fire engine is missing an action point, and thus can currently not extinguish.";

    /**
     * Error if the fire engine is missing an action point to move.
     */
    public static final String ACTION_POINT_NEEDED_TO_MOVE
        = "the fire engine is missing an action point, and thus can currently not move.";

    /**
     * Error if the fire engine is missing water to extinguish.
     */
    public static final String WATER_NEEDED_TO_EXTINGUISH
        = "the fire engine is missing water, and thus can currently not extinguish.";

    /**
     * Error if the player tries to access a fire engine he does currently not have on the board. Format with the
     * invalid id.
     */
    public static final String NO_FIRE_ENGINE = "the currently active player does not own a fire engine with id %s.";

    /**
     * Error if the fire engine is not able to extinguish a field because the field is not adjacent. Format with the row
     * and column of the field to extinguish and the row and column of the field the fire engine is currently placed
     * on.
     */
    public static final String FIRE_ENGINE_NOT_NEARBY = "the fire engine can not extinguish the field %s %s, "
        + "because the fire engine is currently placed on field %s %s, which is not adjacent";

    /**
     * Error if the player tries to refill an fire engine which is already full.
     */
    public static final String ALREADY_FULL
        = "the fire engine with id %s can currently not refill, as it already has the maximum capacity of water.";

    /**
     * Error if the fire engine has no action points left to refill. Format with the current action points of the
     * engine.
     */
    public static final String NO_ACTION_TO_REFILL
        = "the fire engine with id %s has no actions left, this can currently not refill.";

    /**
     * Error if the fire engine can not be refilled at it's current position. Format with the row and column the fire
     * engine is currently placed on.
     */
    public static final String NOT_ADJACENT_REFILL = "the engine is not adjacent to a pond or the "
        + "fire station of the active player, thus the fire engine can not refill when on field %s %s";

    /**
     * Error if the player tries to move the fire engine onto a field that has light fire.
     */
    public static final String NO_ENGINE_ON_LIGHT
        = "the fire engine cannot be moved, because engines can't be moved to fields with light fire.";

    /**
     * Error if the player tries to move the fire engine onto a field that has strong fire.
     */
    public static final String NO_ENGINE_ON_STRONG
        = "the fire engine cannot be moved, because engines can't be moved to fields with strong fire.";

    /**
     * Error if the player tries to move the fire engine to the field it is already placed on.
     */
    public static final String ENGINE_CAN_NOT_STAY_ON_SAME_FIELD
        = "fire engines can only move to fields other than their initial position.";

    /**
     * Error if there is no valid path to the field the player tries to move the fire engine to. Format with the row and
     * column of the field the engine is currently on and the row and column of the field the player tries to move the
     * engine to.
     */
    public static final String NO_PATH
        = "there is no valid path from field %s %s to field %s %s, so the fire engine cannot move there.";

    /**
     * Error if the fire engine can no longer move this turn because it already extinguished or refilled. Format with
     * the id of the fire engine.
     */
    public static final String ALREADY_MADE_ACTION = "the fire engine with id %s can"
        + " no longer move this turn because it already extinguished a field or refilled.";

    /**
     * Error if fire-to-roll is required.
     */
    public static final String FIRE_TO_ROLL_NEEDED = "the fire-to-roll command has to be "
        + "used at the start of each round before the first player of the round can do something.";

    /**
     * Error if something very unexpected goes wrong.
     */
    public static final String SOMETHING_WRONG = "something internally went wrong";
    /**
     * Error if the provided dice number is not actually a dice number. Format with the provided number.
     */
    public static final String NO_DICE_NUMBER = "the supplied number '%s' is not a number from a six sided dice.";

    /**
     * Error if a command can't be used because the game is already over.
     */
    public static final String GAME_OVER = "the game has already ended, thus this command cannot be used.";

    /**
     * Error if it is currently not possible to use the fire-to-roll command.
     */
    public static final String NO_FIRE_TO_ROLL_POSSIBLE
        = "can not roll fire at the moment, as it is not the beginning of a round.";

    /**
     * Error if a fire engine tries to extinguish the field it is placed on.
     */
    public static final String CANNOT_EXTINGUISH_OWN_FIELD
        = "a fire engine can not extinguish the field it's currently placed on";

    /**
     * Error if the same fire engine tries to extinguish more than once in the same turn.
     */
    public static final String CANNOT_EXTINGUISH_SAME_FIELD_TWICE
        = "a fire engine can not extinguish the same field twice in one turn.";


    /**
     * Error if the player tries to place the new engine on a field with burning forest.
     */
    public static final String CANNOT_PLACE_NEW_ENGINE_ON_FIRE
        = "a new fire engine can't be placed on a burning forest";
}