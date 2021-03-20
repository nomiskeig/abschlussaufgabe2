package edu.kit.informatik.logic;

public class FireEngine {

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


    public FireEngine(String id, int row, int column) {
        this.canMove = true;
        this.id = id;
        this.row = row;
        this.column = column;
        this.owningPlayer = Player.getByName(id.substring(0, 1));
        this.actions = INITIAL_ACTIONS;
        this.water = INITIAL_WATER;
    }

    public int getActions() {
        return actions;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public void setActions(int actions) {
        this.actions = actions;
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

    public void madeAction() {
        this.actions = this.actions - 1;
        this.canMove = false;
    }

    public void moved() {
        this.actions -= 1;
    }

    public void refill() {
        this.water = 3;
        this.actions -= 1;
    }

    public boolean enoughActionPoints() {
        return this.actions >= 1;
    }

    public void extinguished() {
        this.actions = this.actions - 1;
        this.water = this.water - 1;
        this.canMove = false;
    }

    @Override
    public String toString() {
        return id;
    }
}
