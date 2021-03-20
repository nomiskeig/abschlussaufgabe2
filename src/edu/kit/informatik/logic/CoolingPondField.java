package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.Collection;

public class CoolingPondField implements Field {
    private static final String COOLING_POND_DISPLAY_NAME = "L";

    @Override
    public FireState getFireState() {
        return FireState.NON_FIRE_FIELD;
    }

    @Override
    public void setFireState(FireState fs) {

    }

    @Override
    public void placeFireEngine(FireEngine fireEngine) throws GameException {
        throw new GameException(Errors.INVALID_PLACEMENT_POND);
    }

    @Override
    public void removeFireEngine(FireEngine fireEngine) {

    }

    @Override
    public Collection<FireEngine> getFireEngines(Player player) {
        return null;
    }

    @Override
    public boolean isPond() {
        return true;
    }

    @Override
    public boolean isFireStation(Player player) {
        return false;
    }

    @Override
    public String toString() {
        return COOLING_POND_DISPLAY_NAME;
    }
}
