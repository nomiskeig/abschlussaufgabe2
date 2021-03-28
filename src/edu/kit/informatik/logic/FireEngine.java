package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models a fireEngine used by the game. It keeps track of the current water and action points of the engine.
 * Does also keep track of what the engine can currently do.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class FireEngine implements Comparable<FireEngine>, Resettable {
    /**
     * The initial amount of actions the fireEngine has each turn.
     */
    private static final int INITIAL_ACTIONS = 3;

    /**
     * The initial amount of water the fireEngine has.
     */
    private static final int INITIAL_WATER = 3;
    private static final int WATER_AND_ACTION_TO_REMOVE = 1;
    private final String id;
    private int actions;
    private int row;
    private int column;
    private final int initialRow;
    private final int initialColumn;
    private final Player owningPlayer;
    private boolean canMove;
    private int water;

    /**
     * A list of pairs containing integers, keeping track of all the fields the fireEngine has already extinguished.
     */
    private final List<Pair<Integer, Integer>> extinguishedFields;


    /**
     * The constructor.
     *
     * @param id     the id of the fireEngine.
     * @param row    the row of the field the engine is initially placed on.
     * @param column the column of the field the engine is initially placed on.
     */
    public FireEngine(String id, int row, int column) {
        this.canMove = true;
        this.id = id;
        this.row = row;
        this.initialRow = row;
        this.column = column;
        this.initialColumn = column;
        this.owningPlayer = Player.getByName(id.substring(0, 1));
        this.actions = INITIAL_ACTIONS;
        this.water = INITIAL_WATER;
        this.extinguishedFields = new ArrayList<>();
    }

    /**
     * Getter for the actions the fireEngine has remaining.
     *
     * @return the amount of actions the fireEngine currently has.
     */
    public int getActions() {
        return actions;
    }

    /**
     * Getter for the water the fireEngine the fireEngine currently has.
     *
     * @return the amount of water the fireEngine currently has.
     */
    public int getWater() {
        return water;
    }


    /**
     * Getter for the id of the engine.
     *
     * @return the id of the fireEngine.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the player who owns the fireEngine.
     *
     * @return the owning Player of the engine.
     */
    public Player getOwningPlayer() {
        return owningPlayer;
    }

    /**
     * Getter for the row the engine is currently placed in.
     *
     * @return the index of the row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter for the row of the engine.
     *
     * @param row the new row of the engine.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter for the column the engine is currently placed in.
     *
     * @return the index of the column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setter for the column of the engine.
     *
     * @param column the new column of the engine.
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Getter canMove field.
     *
     * @return true if the engine can still move, false if it can not.
     */
    public boolean canMove() {
        return this.canMove;
    }


    /**
     * Removes an action point, because the engine moves.
     */
    public void moved() {
        this.actions -= WATER_AND_ACTION_TO_REMOVE;
    }

    /**
     * Removes an action point, "refills" the water and sets canMove to false.
     */
    public void refill() {
        this.water = INITIAL_WATER;
        this.actions -= WATER_AND_ACTION_TO_REMOVE;
        this.canMove = false;
    }

    /**
     * Checks whether the specified field was already extinguished this turn by this engine.
     *
     * @param row    the row of the field to check.
     * @param column the column of the field to check.
     * @return true if the engine did already extinguish that field, false if it did not.
     */
    public boolean alreadyExtinguished(int row, int column) {
        return this.extinguishedFields.contains(new Pair<>(row, column));
    }


    /**
     * Checks whether the engine has enough actions points left to do an action.
     *
     * @return true if the engine has enough action points, false if it does not.
     */
    public boolean enoughActionPoints() {
        return this.actions >= 1;
    }

    /**
     * Removes an action and a water point, sets canMove to false and adds the specified field to the already
     * extinguished Fields
     *
     * @param row    the row of the field extinguished.
     * @param column the column of the field extinguished.
     */
    public void extinguished(int row, int column) {
        this.actions -= WATER_AND_ACTION_TO_REMOVE;
        this.water -= WATER_AND_ACTION_TO_REMOVE;
        this.canMove = false;
        this.extinguishedFields.add(new Pair<>(row, column));
    }

    /**
     * Resets the engine for the next turn.
     */
    public void turnReset() {
        this.canMove = true;
        this.actions = INITIAL_ACTIONS;
        this.extinguishedFields.clear();
    }

    @Override
    public void reset() {
        this.turnReset();
        this.water = INITIAL_WATER;
        this.row = this.initialRow;
        this.column = this.initialColumn;

    }


    @Override
    public int compareTo(FireEngine o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getWater() + "," + this.getActions() + "," + this.getRow() + "," + this
            .getColumn();
    }
}
