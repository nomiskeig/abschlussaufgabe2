package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.List;

public class CoolingPondField implements Field {
    private static final String COOLING_POND_DISPLAY_NAME = "L";

    @Override
    public boolean alreadyModified() {
        return false;
    }

    @Override
    public void resetModified() {

    }

    @Override
    public FireState getFireState() {
        return FireState.NON_BURNABLE_FIELD;
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
    public List<FireEngine> getFireEngines(Player player) {
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
    public boolean isFireStation() {
        return false;
    }

    @Override
    public void burn() {

    }

    @Override
    public FireState extinguish() throws GameException {
        throw new GameException(Errors.CAN_ONLY_EXTINGUISH_FOREST);
    }

    @Override
    public String toString() {
        return COOLING_POND_DISPLAY_NAME;
    }
}
