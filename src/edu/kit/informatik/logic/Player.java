package edu.kit.informatik.logic;

/**
 * This enum models the players who are playing the game.
 *
 * @author Simon Giek
 * @version 1.0
 */
public enum Player implements Resettable {
    /**
     * Player A
     */
    A("A"),
    /**
     * Player B
     */
    B("B"),
    /**
     * Player C
     */
    C("C"),
    /**
     * Player D
     */
    D("D");
    private static final int AMOUNT_NEW_FIRE_ENGINE = 1;

    private static final int AMOUNT_NEW_REP_POINT = 1;

    private static final int INITIAL_REPUTATION_POINTS = 0;
    private static final int INITIAL_AMOUNT_FIRE_ENGINES = 1;
    private final String name;
    private int reputationPoints;
    private int amountOfFireEngines;

    /**
     * The constructor.
     *
     * @param name the name of the player.
     */
    Player(String name) {
        this.name = name;
        this.amountOfFireEngines = INITIAL_AMOUNT_FIRE_ENGINES;
        this.reputationPoints = INITIAL_REPUTATION_POINTS;
    }

    /**
     * Searches all the players for the one with the matching name and returns it.
     *
     * @param name the of the player to search.
     * @return the player with the matching name, null if there is no such player.
     */
    public static Player getByName(String name) {
        for (Player player : Player.values()) {
            if (player.name.equals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Getter for the name of the player.
     *
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Removes five reputation points from the player and increases the amount of fireEngines of this player by one.
     */
    public void boughtEngine() {
        this.reputationPoints -= FireBreakerGame.REPUTATION_TO_BUY_ENGINE;
        this.amountOfFireEngines += AMOUNT_NEW_FIRE_ENGINE;
    }

    /**
     * Getter for the reputation points the player currently has.
     *
     * @return the amount of reputation points of the player.
     */
    public int getReputationPoints() {
        return reputationPoints;
    }

    /**
     * Getter for the amount of fireEngines the player currently has.
     *
     * @return the amount of fire engines of the player.
     */
    public int getAmountOfFireEngines() {
        return amountOfFireEngines;
    }

    /**
     * Adds an reputation point.
     */
    public void addReputationPoint() {
        this.reputationPoints += AMOUNT_NEW_REP_POINT;
    }

    @Override
    public void reset() {
        this.reputationPoints = INITIAL_REPUTATION_POINTS;
        this.amountOfFireEngines = INITIAL_AMOUNT_FIRE_ENGINES;
    }
}
