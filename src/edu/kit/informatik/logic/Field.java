package edu.kit.informatik.logic;


import java.util.List;

public interface Field {


    boolean alreadyModified();

    void resetModified();

    FireState getFireState();

    void setFireState(FireState fs);

    void placeFireEngine(FireEngine fireEngine) throws GameException;

    void removeFireEngine(FireEngine fireEngine);

    List<FireEngine> getFireEngines(Player player);

    boolean isPond();

    boolean isFireStation(Player player);

    boolean isFireStation();

    void burn();

    FireState extinguish() throws GameException;

    void reset();
}
