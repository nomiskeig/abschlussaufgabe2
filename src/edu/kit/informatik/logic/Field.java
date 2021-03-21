package edu.kit.informatik.logic;


import java.util.List;

public interface Field {

    public static final String FIELD_REGEX = "";
    //public void extinguish();

    FireState getFireState();

    void setFireState(FireState fs);

    void placeFireEngine(FireEngine fireEngine) throws GameException;

    void removeFireEngine(FireEngine fireEngine);

    List<FireEngine> getFireEngines(Player player);

    boolean isPond();

    boolean isFireStation(Player player);
}
