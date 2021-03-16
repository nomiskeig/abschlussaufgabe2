package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.Collection;


public class FireStationField implements Field {

    private final String id;

    public FireStationField(String id) {
        this.id = id;
    }

    @Override
    public FireState getFireState() {
        return FireState.NON_FIRE_FIELD;

    }

    @Override
    public void setFireState(FireState fs) {

    }

    @Override
    public void placeFireEngine(FireEngine fireEngine) throws GameException {
        throw new GameException(Errors.INVALID_PLACEMENT_FIRE_STATION);
    }

    @Override
    public Collection<FireEngine> getFireEngines(Player player) {
        return null;
    }

    @Override
    public String toString() {
        return id;
    }
}
