package edu.kit.informatik.logic;

import edu.kit.informatik.resources.Errors;

import java.util.List;

/**
 * This class models the fireStation field implementing the Field interface.
 *
 * @author Simon Giek
 * @version 1.0
 */
public class FireStationField implements Field {
    private final String id;
    private final Player owner;

    /**
     * The constructor.
     *
     * @param id the id of the fireStation.
     */
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
        return FireState.NON_BURNABLE_FIELD;

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
        if (player == null) {
            return true;
        }
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
    public void reset() {

    }

    @Override
    public String toString() {
        return id;
    }
}
