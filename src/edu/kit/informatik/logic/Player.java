package edu.kit.informatik.logic;

public enum Player {
    A("A"), B("B"), C("C"), D("D");

    private static final int INITIAL_REPUTATION_POINTS = 0;
    private static final int INITIAL_AMOUNT_FIRE_ENGINES = 1;
    private final String name;
    private int reputationPoints;
    private int amountOfFireEngines;


    Player(String name) {
        this.name = name;
        this.amountOfFireEngines = INITIAL_AMOUNT_FIRE_ENGINES;
        this.reputationPoints = INITIAL_REPUTATION_POINTS;
    }

    public static Player getByName(String name) {
        for (Player player : Player.values()) {
            if (player.name.equals(name)) {
                return player;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }


    public void boughtEngine() {
        this.reputationPoints -= 5;
        this.amountOfFireEngines += 1;
    }

    public void reset() {
        this.reputationPoints = INITIAL_REPUTATION_POINTS;
        this.amountOfFireEngines = INITIAL_AMOUNT_FIRE_ENGINES;
    }

    public int getReputationPoints() {
        return reputationPoints;
    }

    public void setReputationPoints(int reputationPoints) {
        this.reputationPoints = reputationPoints;
    }

    public int getAmountOfFireEngines() {
        return amountOfFireEngines;
    }


    public void addReputationPoint() {
        this.reputationPoints += 1;
    }
}
