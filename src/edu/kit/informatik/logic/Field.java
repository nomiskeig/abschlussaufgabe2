package edu.kit.informatik.logic;

import java.util.Collection;


public interface Field {
    //public void extinguish();

    FireState getFireState();

    void setFireState(FireState fs);

    void placeFireEngine(FireEngine fireEngine) throws GameException;

    Collection<FireEngine> getFireEngines(Player player);
}
