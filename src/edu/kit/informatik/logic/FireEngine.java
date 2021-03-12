package edu.kit.informatik.logic;

public class FireEngine {

    public static String INITIAL_TRUCK_REGEX = "^((A0)|(B0)|(C0)|(D0))$";

    private final String id;
    private int actionPoints;


    public FireEngine(String id) {
        this.id = id;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    public String getId() {
        return id;
    }

}
