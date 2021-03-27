package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.List;

public class FireEngine implements Comparable<FireEngine> {

    public static final String INITIAL_TRUCK_REGEX = "^((A0)|(B0)|(C0)|(D0))$";


    private static final int INITIAL_ACTIONS = 3;
    private static final int INITIAL_WATER = 3;

    private final String id;
    private int actions;

    private int row;
    private int column;

    private Player owningPlayer;

    private boolean canMove;

    private int water;

    private List<Pair<Integer, Integer>> extinguishedFields;


    public FireEngine(String id, int row, int column) {
        this.canMove = true;
        this.id = id;
        this.row = row;
        this.column = column;
        this.owningPlayer = Player.getByName(id.substring(0, 1));
        this.actions = INITIAL_ACTIONS;
        this.water = INITIAL_WATER;
        this.extinguishedFields = new ArrayList<>();
    }

    public int getActions() {
        return actions;
    }

    public int getWater() {
        return water;
    }


    public String getId() {
        return id;
    }

    public Player getOwningPlayer() {
        return owningPlayer;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean canMove() {
        return (this.canMove);
    }
    

    public void moved() {
        this.actions -= 1;
    }

    public void refill() {
        this.water = 3;
        this.actions -= 1;
        this.canMove = false;
    }

    public boolean alreadyExtinguished(int row, int column) {
        if (this.extinguishedFields.contains(new Pair<>(row, column))) {
            return true;
        }
        return false;
    }

    public boolean enoughActionPoints() {
        return this.actions >= 1;
    }

    public void extinguished(int row, int column) {
        this.actions = this.actions - 1;
        this.water = this.water - 1;
        this.canMove = false;
        this.extinguishedFields.add(new Pair<>(row, column));
    }

    public void reset() {
        this.canMove = true;
        this.actions = INITIAL_ACTIONS;
        this.extinguishedFields.clear();
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
