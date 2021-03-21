package edu.kit.informatik.logic;

public enum Player {
    A("A"), B("B"), C("C"), D("D"), NONE("");

    private static final int INITIAL_REPUTATION_POINTS = 5;
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

    public int getReputationPoints() {
        return reputationPoints;
    }

    public void setReputationPoints(int reputationPoints) {
        this.reputationPoints = reputationPoints;
    }

    public int getAmountOfFireEngines() {
        return amountOfFireEngines;
    }

    public void setAmountOfFireEngines(int amountOfFireEngines) {
        this.amountOfFireEngines = amountOfFireEngines;
    }

    public void addReputationPoint() {
        this.reputationPoints += 1;
    }
}
