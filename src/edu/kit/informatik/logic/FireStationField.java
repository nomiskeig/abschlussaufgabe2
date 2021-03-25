package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.List;


public class FireStationField implements Field {

    private final String id;

    private final Player owner;

    public FireStationField(String id) {
        this.id = id;
        this.owner = Player.getByName(id);
    }

    @Override
    public boolean alreadyModified() {
        return false;
    }

    @Override
    public void resetModified() {

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
    public void removeFireEngine(FireEngine fireEngine) {

    }

    @Override
    public List<FireEngine> getFireEngines(Player player) {
        return null;
    }

    @Override
    public boolean isPond() {
        return false;
    }

    @Override
    public boolean isFireStation(Player player) {
        return this.owner == player;
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
        return id;
    }
}
